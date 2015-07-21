package net.minecraft.world.chunk.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO
{
    private static final Logger logger = LogManager.getLogger();
    private List chunksToRemove = new ArrayList();
    private Set pendingAnvilChunksCoordinates = new HashSet();
    private Object syncLockObject = new Object();

    /** Save directory for chunks using the Anvil format */
    private final File chunkSaveLocation;
    private static final String __OBFID = "CL_00000384";

    public AnvilChunkLoader(File par1File)
    {
        this.chunkSaveLocation = par1File;
    }

    /**
     * Loads the specified(XZ) chunk into the specified world.
     */
    public Chunk loadChunk(World par1World, int par2, int par3) throws IOException
    {
        NBTTagCompound var4 = null;
        ChunkCoordIntPair var5 = new ChunkCoordIntPair(par2, par3);
        Object var6 = this.syncLockObject;

        synchronized (this.syncLockObject)
        {
            if (this.pendingAnvilChunksCoordinates.contains(var5))
            {
                for (int var7 = 0; var7 < this.chunksToRemove.size(); ++var7)
                {
                    if (((AnvilChunkLoader.PendingChunk)this.chunksToRemove.get(var7)).chunkCoordinate.equals(var5))
                    {
                        var4 = ((AnvilChunkLoader.PendingChunk)this.chunksToRemove.get(var7)).nbtTags;
                        break;
                    }
                }
            }
        }

        if (var4 == null)
        {
            DataInputStream var10 = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, par2, par3);

            if (var10 == null)
            {
                return null;
            }

            var4 = CompressedStreamTools.read(var10);
        }

        return this.checkedReadChunkFromNBT(par1World, par2, par3, var4);
    }

    /**
     * Wraps readChunkFromNBT. Checks the coordinates and several NBT tags.
     */
    protected Chunk checkedReadChunkFromNBT(World par1World, int par2, int par3, NBTTagCompound par4NBTTagCompound)
    {
        if (!par4NBTTagCompound.func_150297_b("Level", 10))
        {
            logger.error("Chunk file at " + par2 + "," + par3 + " is missing level data, skipping");
            return null;
        }
        else if (!par4NBTTagCompound.getCompoundTag("Level").func_150297_b("Sections", 9))
        {
            logger.error("Chunk file at " + par2 + "," + par3 + " is missing block data, skipping");
            return null;
        }
        else
        {
            Chunk var5 = this.readChunkFromNBT(par1World, par4NBTTagCompound.getCompoundTag("Level"));

            if (!var5.isAtLocation(par2, par3))
            {
                logger.error("Chunk file at " + par2 + "," + par3 + " is in the wrong location; relocating. (Expected " + par2 + ", " + par3 + ", got " + var5.xPosition + ", " + var5.zPosition + ")");
                par4NBTTagCompound.setInteger("xPos", par2);
                par4NBTTagCompound.setInteger("zPos", par3);
                var5 = this.readChunkFromNBT(par1World, par4NBTTagCompound.getCompoundTag("Level"));
            }

            return var5;
        }
    }

    public void saveChunk(World par1World, Chunk par2Chunk) throws MinecraftException, IOException
    {
        par1World.checkSessionLock();

        try
        {
            NBTTagCompound var3 = new NBTTagCompound();
            NBTTagCompound var4 = new NBTTagCompound();
            var3.setTag("Level", var4);
            this.writeChunkToNBT(par2Chunk, par1World, var4);
            this.addChunkToPending(par2Chunk.getChunkCoordIntPair(), var3);
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }
    }

    protected void addChunkToPending(ChunkCoordIntPair par1ChunkCoordIntPair, NBTTagCompound par2NBTTagCompound)
    {
        Object var3 = this.syncLockObject;

        synchronized (this.syncLockObject)
        {
            if (this.pendingAnvilChunksCoordinates.contains(par1ChunkCoordIntPair))
            {
                for (int var4 = 0; var4 < this.chunksToRemove.size(); ++var4)
                {
                    if (((AnvilChunkLoader.PendingChunk)this.chunksToRemove.get(var4)).chunkCoordinate.equals(par1ChunkCoordIntPair))
                    {
                        this.chunksToRemove.set(var4, new AnvilChunkLoader.PendingChunk(par1ChunkCoordIntPair, par2NBTTagCompound));
                        return;
                    }
                }
            }

            this.chunksToRemove.add(new AnvilChunkLoader.PendingChunk(par1ChunkCoordIntPair, par2NBTTagCompound));
            this.pendingAnvilChunksCoordinates.add(par1ChunkCoordIntPair);
            ThreadedFileIOBase.threadedIOInstance.queueIO(this);
        }
    }

    /**
     * Returns a boolean stating if the write was unsuccessful.
     */
    public boolean writeNextIO()
    {
        AnvilChunkLoader.PendingChunk var1 = null;
        Object var2 = this.syncLockObject;

        synchronized (this.syncLockObject)
        {
            if (this.chunksToRemove.isEmpty())
            {
                return false;
            }

            var1 = (AnvilChunkLoader.PendingChunk)this.chunksToRemove.remove(0);
            this.pendingAnvilChunksCoordinates.remove(var1.chunkCoordinate);
        }

        if (var1 != null)
        {
            try
            {
                this.writeChunkNBTTags(var1);
            }
            catch (Exception var4)
            {
                var4.printStackTrace();
            }
        }

        return true;
    }

    private void writeChunkNBTTags(AnvilChunkLoader.PendingChunk par1AnvilChunkLoaderPending) throws IOException
    {
        DataOutputStream var2 = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, par1AnvilChunkLoaderPending.chunkCoordinate.chunkXPos, par1AnvilChunkLoaderPending.chunkCoordinate.chunkZPos);
        CompressedStreamTools.write(par1AnvilChunkLoaderPending.nbtTags, var2);
    }

    /**
     * Save extra data associated with this Chunk not normally saved during autosave, only during chunk unload.
     * Currently unused.
     */
    public void saveExtraChunkData(World par1World, Chunk par2Chunk) {}

    /**
     * Called every World.tick()
     */
    public void chunkTick() {}

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unused.
     */
    public void saveExtraData()
    {
        while (this.writeNextIO())
        {
            ;
        }
    }

    /**
     * Writes the Chunk passed as an argument to the NBTTagCompound also passed, using the World argument to retrieve
     * the Chunk's last update time.
     */
    private void writeChunkToNBT(Chunk par1Chunk, World par2World, NBTTagCompound par3NBTTagCompound)
    {
        par3NBTTagCompound.setByte("V", (byte)1);
        par3NBTTagCompound.setInteger("xPos", par1Chunk.xPosition);
        par3NBTTagCompound.setInteger("zPos", par1Chunk.zPosition);
        par3NBTTagCompound.setLong("LastUpdate", par2World.getTotalWorldTime());
        par3NBTTagCompound.setIntArray("HeightMap", par1Chunk.heightMap);
        par3NBTTagCompound.setBoolean("TerrainPopulated", par1Chunk.isTerrainPopulated);
        par3NBTTagCompound.setBoolean("LightPopulated", par1Chunk.isLightPopulated);
        par3NBTTagCompound.setLong("InhabitedTime", par1Chunk.inhabitedTime);
        ExtendedBlockStorage[] var4 = par1Chunk.getBlockStorageArray();
        NBTTagList var5 = new NBTTagList();
        boolean var6 = !par2World.provider.hasNoSky;
        ExtendedBlockStorage[] var7 = var4;
        int var8 = var4.length;
        NBTTagCompound var11;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            ExtendedBlockStorage var10 = var7[var9];

            if (var10 != null)
            {
                var11 = new NBTTagCompound();
                var11.setByte("Y", (byte)(var10.getYLocation() >> 4 & 255));
                var11.setByteArray("Blocks", var10.getBlockLSBArray());

                if (var10.getBlockMSBArray() != null)
                {
                    var11.setByteArray("Add", var10.getBlockMSBArray().data);
                }

                var11.setByteArray("Data", var10.getMetadataArray().data);
                var11.setByteArray("BlockLight", var10.getBlocklightArray().data);

                if (var6)
                {
                    var11.setByteArray("SkyLight", var10.getSkylightArray().data);
                }
                else
                {
                    var11.setByteArray("SkyLight", new byte[var10.getBlocklightArray().data.length]);
                }

                var5.appendTag(var11);
            }
        }

        par3NBTTagCompound.setTag("Sections", var5);
        par3NBTTagCompound.setByteArray("Biomes", par1Chunk.getBiomeArray());
        par1Chunk.hasEntities = false;
        NBTTagList var16 = new NBTTagList();
        Iterator var18;

        for (var8 = 0; var8 < par1Chunk.entityLists.length; ++var8)
        {
            var18 = par1Chunk.entityLists[var8].iterator();

            while (var18.hasNext())
            {
                Entity var21 = (Entity)var18.next();
                var11 = new NBTTagCompound();

                if (var21.writeToNBTOptional(var11))
                {
                    par1Chunk.hasEntities = true;
                    var16.appendTag(var11);
                }
            }
        }

        par3NBTTagCompound.setTag("Entities", var16);
        NBTTagList var17 = new NBTTagList();
        var18 = par1Chunk.chunkTileEntityMap.values().iterator();

        while (var18.hasNext())
        {
            TileEntity var22 = (TileEntity)var18.next();
            var11 = new NBTTagCompound();
            var22.writeToNBT(var11);
            var17.appendTag(var11);
        }

        par3NBTTagCompound.setTag("TileEntities", var17);
        List var20 = par2World.getPendingBlockUpdates(par1Chunk, false);

        if (var20 != null)
        {
            long var19 = par2World.getTotalWorldTime();
            NBTTagList var12 = new NBTTagList();
            Iterator var13 = var20.iterator();

            while (var13.hasNext())
            {
                NextTickListEntry var14 = (NextTickListEntry)var13.next();
                NBTTagCompound var15 = new NBTTagCompound();
                var15.setInteger("i", Block.getIdFromBlock(var14.func_151351_a()));
                var15.setInteger("x", var14.xCoord);
                var15.setInteger("y", var14.yCoord);
                var15.setInteger("z", var14.zCoord);
                var15.setInteger("t", (int)(var14.scheduledTime - var19));
                var15.setInteger("p", var14.priority);
                var12.appendTag(var15);
            }

            par3NBTTagCompound.setTag("TileTicks", var12);
        }
    }

    /**
     * Reads the data stored in the passed NBTTagCompound and creates a Chunk with that data in the passed World.
     * Returns the created Chunk.
     */
    private Chunk readChunkFromNBT(World par1World, NBTTagCompound par2NBTTagCompound)
    {
        int var3 = par2NBTTagCompound.getInteger("xPos");
        int var4 = par2NBTTagCompound.getInteger("zPos");
        Chunk var5 = new Chunk(par1World, var3, var4);
        var5.heightMap = par2NBTTagCompound.getIntArray("HeightMap");
        var5.isTerrainPopulated = par2NBTTagCompound.getBoolean("TerrainPopulated");
        var5.isLightPopulated = par2NBTTagCompound.getBoolean("LightPopulated");
        var5.inhabitedTime = par2NBTTagCompound.getLong("InhabitedTime");
        NBTTagList var6 = par2NBTTagCompound.getTagList("Sections", 10);
        byte var7 = 16;
        ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
        boolean var9 = !par1World.provider.hasNoSky;

        for (int var10 = 0; var10 < var6.tagCount(); ++var10)
        {
            NBTTagCompound var11 = var6.getCompoundTagAt(var10);
            byte var12 = var11.getByte("Y");
            ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
            var13.setBlockLSBArray(var11.getByteArray("Blocks"));

            if (var11.func_150297_b("Add", 7))
            {
                var13.setBlockMSBArray(new NibbleArray(var11.getByteArray("Add"), 4));
            }

            var13.setBlockMetadataArray(new NibbleArray(var11.getByteArray("Data"), 4));
            var13.setBlocklightArray(new NibbleArray(var11.getByteArray("BlockLight"), 4));

            if (var9)
            {
                var13.setSkylightArray(new NibbleArray(var11.getByteArray("SkyLight"), 4));
            }

            var13.removeInvalidBlocks();
            var8[var12] = var13;
        }

        var5.setStorageArrays(var8);

        if (par2NBTTagCompound.func_150297_b("Biomes", 7))
        {
            var5.setBiomeArray(par2NBTTagCompound.getByteArray("Biomes"));
        }

        NBTTagList var18 = par2NBTTagCompound.getTagList("Entities", 10);

        if (var18 != null)
        {
            for (int var17 = 0; var17 < var18.tagCount(); ++var17)
            {
                NBTTagCompound var19 = var18.getCompoundTagAt(var17);
                Entity var25 = EntityList.createEntityFromNBT(var19, par1World);
                var5.hasEntities = true;

                if (var25 != null)
                {
                    var5.addEntity(var25);
                    Entity var14 = var25;

                    for (NBTTagCompound var15 = var19; var15.func_150297_b("Riding", 10); var15 = var15.getCompoundTag("Riding"))
                    {
                        Entity var16 = EntityList.createEntityFromNBT(var15.getCompoundTag("Riding"), par1World);

                        if (var16 != null)
                        {
                            var5.addEntity(var16);
                            var14.mountEntity(var16);
                        }

                        var14 = var16;
                    }
                }
            }
        }

        NBTTagList var21 = par2NBTTagCompound.getTagList("TileEntities", 10);

        if (var21 != null)
        {
            for (int var20 = 0; var20 < var21.tagCount(); ++var20)
            {
                NBTTagCompound var22 = var21.getCompoundTagAt(var20);
                TileEntity var27 = TileEntity.createAndLoadEntity(var22);

                if (var27 != null)
                {
                    var5.addTileEntity(var27);
                }
            }
        }

        if (par2NBTTagCompound.func_150297_b("TileTicks", 9))
        {
            NBTTagList var24 = par2NBTTagCompound.getTagList("TileTicks", 10);

            if (var24 != null)
            {
                for (int var23 = 0; var23 < var24.tagCount(); ++var23)
                {
                    NBTTagCompound var26 = var24.getCompoundTagAt(var23);
                    par1World.func_147446_b(var26.getInteger("x"), var26.getInteger("y"), var26.getInteger("z"), Block.getBlockById(var26.getInteger("i")), var26.getInteger("t"), var26.getInteger("p"));
                }
            }
        }

        return var5;
    }

    static class PendingChunk
    {
        public final ChunkCoordIntPair chunkCoordinate;
        public final NBTTagCompound nbtTags;
        private static final String __OBFID = "CL_00000385";

        public PendingChunk(ChunkCoordIntPair par1ChunkCoordIntPair, NBTTagCompound par2NBTTagCompound)
        {
            this.chunkCoordinate = par1ChunkCoordIntPair;
            this.nbtTags = par2NBTTagCompound;
        }
    }
}
