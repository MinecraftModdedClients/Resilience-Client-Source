package net.minecraft.world.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveHandler implements ISaveHandler, IPlayerFileData
{
    private static final Logger logger = LogManager.getLogger();

    /** The directory in which to save world data. */
    private final File worldDirectory;

    /** The directory in which to save player data. */
    private final File playersDirectory;
    private final File mapDataDir;

    /**
     * The time in milliseconds when this field was initialized. Stored in the session lock file.
     */
    private final long initializationTime = MinecraftServer.getSystemTimeMillis();

    /** The directory name of the world */
    private final String saveDirectoryName;
    private static final String __OBFID = "CL_00000585";

    public SaveHandler(File par1File, String par2Str, boolean par3)
    {
        this.worldDirectory = new File(par1File, par2Str);
        this.worldDirectory.mkdirs();
        this.playersDirectory = new File(this.worldDirectory, "players");
        this.mapDataDir = new File(this.worldDirectory, "data");
        this.mapDataDir.mkdirs();
        this.saveDirectoryName = par2Str;

        if (par3)
        {
            this.playersDirectory.mkdirs();
        }

        this.setSessionLock();
    }

    /**
     * Creates a session lock file for this process
     */
    private void setSessionLock()
    {
        try
        {
            File var1 = new File(this.worldDirectory, "session.lock");
            DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));

            try
            {
                var2.writeLong(this.initializationTime);
            }
            finally
            {
                var2.close();
            }
        }
        catch (IOException var7)
        {
            var7.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    /**
     * Gets the File object corresponding to the base directory of this world.
     */
    public File getWorldDirectory()
    {
        return this.worldDirectory;
    }

    /**
     * Checks the session lock to prevent save collisions
     */
    public void checkSessionLock() throws MinecraftException
    {
        try
        {
            File var1 = new File(this.worldDirectory, "session.lock");
            DataInputStream var2 = new DataInputStream(new FileInputStream(var1));

            try
            {
                if (var2.readLong() != this.initializationTime)
                {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            }
            finally
            {
                var2.close();
            }
        }
        catch (IOException var7)
        {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }

    /**
     * Returns the chunk loader with the provided world provider
     */
    public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider)
    {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }

    /**
     * Loads and returns the world info
     */
    public WorldInfo loadWorldInfo()
    {
        File var1 = new File(this.worldDirectory, "level.dat");
        NBTTagCompound var2;
        NBTTagCompound var3;

        if (var1.exists())
        {
            try
            {
                var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
                var3 = var2.getCompoundTag("Data");
                return new WorldInfo(var3);
            }
            catch (Exception var5)
            {
                var5.printStackTrace();
            }
        }

        var1 = new File(this.worldDirectory, "level.dat_old");

        if (var1.exists())
        {
            try
            {
                var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
                var3 = var2.getCompoundTag("Data");
                return new WorldInfo(var3);
            }
            catch (Exception var4)
            {
                var4.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Saves the given World Info with the given NBTTagCompound as the Player.
     */
    public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound)
    {
        NBTTagCompound var3 = par1WorldInfo.cloneNBTCompound(par2NBTTagCompound);
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setTag("Data", var3);

        try
        {
            File var5 = new File(this.worldDirectory, "level.dat_new");
            File var6 = new File(this.worldDirectory, "level.dat_old");
            File var7 = new File(this.worldDirectory, "level.dat");
            CompressedStreamTools.writeCompressed(var4, new FileOutputStream(var5));

            if (var6.exists())
            {
                var6.delete();
            }

            var7.renameTo(var6);

            if (var7.exists())
            {
                var7.delete();
            }

            var5.renameTo(var7);

            if (var5.exists())
            {
                var5.delete();
            }
        }
        catch (Exception var8)
        {
            var8.printStackTrace();
        }
    }

    /**
     * Saves the passed in world info.
     */
    public void saveWorldInfo(WorldInfo par1WorldInfo)
    {
    	System.out.println("So I'm being called, but...");
        NBTTagCompound var2 = par1WorldInfo.getNBTTagCompound();
        NBTTagCompound var3 = new NBTTagCompound();
        var3.setTag("Data", var2);

        try
        {
            File var4 = new File(this.worldDirectory, "level.dat_new");
            File var5 = new File(this.worldDirectory, "level.dat_old");
            File var6 = new File(this.worldDirectory, "level.dat");
            CompressedStreamTools.writeCompressed(var3, new FileOutputStream(var4));

            if (var5.exists())
            {
                var5.delete();
            }

            var6.renameTo(var5);

            if (var6.exists())
            {
                var6.delete();
            }

            var4.renameTo(var6);

            if (var4.exists())
            {
                var4.delete();
            }
        }
        catch (Exception var7)
        {
            var7.printStackTrace();
        }
    }

    /**
     * Writes the player data to disk from the specified PlayerEntityMP.
     */
    public void writePlayerData(EntityPlayer par1EntityPlayer)
    {
        try
        {
            NBTTagCompound var2 = new NBTTagCompound();
            par1EntityPlayer.writeToNBT(var2);
            File var3 = new File(this.playersDirectory, par1EntityPlayer.getCommandSenderName() + ".dat.tmp");
            File var4 = new File(this.playersDirectory, par1EntityPlayer.getCommandSenderName() + ".dat");
            CompressedStreamTools.writeCompressed(var2, new FileOutputStream(var3));

            if (var4.exists())
            {
                var4.delete();
            }

            var3.renameTo(var4);
        }
        catch (Exception var5)
        {
            logger.warn("Failed to save player data for " + par1EntityPlayer.getCommandSenderName());
        }
    }

    /**
     * Reads the player data from disk into the specified PlayerEntityMP.
     */
    public NBTTagCompound readPlayerData(EntityPlayer par1EntityPlayer)
    {
        NBTTagCompound var2 = this.getPlayerData(par1EntityPlayer.getCommandSenderName());

        if (var2 != null)
        {
            par1EntityPlayer.readFromNBT(var2);
        }

        return var2;
    }

    /**
     * Gets the player data for the given playername as a NBTTagCompound.
     */
    public NBTTagCompound getPlayerData(String par1Str)
    {
        try
        {
            File var2 = new File(this.playersDirectory, par1Str + ".dat");

            if (var2.exists())
            {
                return CompressedStreamTools.readCompressed(new FileInputStream(var2));
            }
        }
        catch (Exception var3)
        {
            logger.warn("Failed to load player data for " + par1Str);
        }

        return null;
    }

    /**
     * returns null if no saveHandler is relevent (eg. SMP)
     */
    public IPlayerFileData getSaveHandler()
    {
        return this;
    }

    /**
     * Returns an array of usernames for which player.dat exists for.
     */
    public String[] getAvailablePlayerDat()
    {
        String[] var1 = this.playersDirectory.list();

        for (int var2 = 0; var2 < var1.length; ++var2)
        {
            if (var1[var2].endsWith(".dat"))
            {
                var1[var2] = var1[var2].substring(0, var1[var2].length() - 4);
            }
        }

        return var1;
    }

    /**
     * Called to flush all changes to disk, waiting for them to complete.
     */
    public void flush() {}

    /**
     * Gets the file location of the given map
     */
    public File getMapFileFromName(String par1Str)
    {
        return new File(this.mapDataDir, par1Str + ".dat");
    }

    /**
     * Returns the name of the directory where world information is saved.
     */
    public String getWorldDirectoryName()
    {
        return this.saveDirectoryName;
    }
}
