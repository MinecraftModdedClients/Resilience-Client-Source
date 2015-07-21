package net.minecraft.world;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public final class SpawnerAnimals
{
    /** The 17x17 area around the player where mobs can spawn */
    private HashMap eligibleChunksForSpawning = new HashMap();
    private Map mapSampleEntitiesByClass = new HashMap();
    private int lastPlayerChunkX = Integer.MAX_VALUE;
    private int lastPlayerChunkZ = Integer.MAX_VALUE;
    private static final String __OBFID = "CL_00000152";

    protected static ChunkPosition func_151350_a(World p_151350_0_, int p_151350_1_, int p_151350_2_)
    {
        Chunk var3 = p_151350_0_.getChunkFromChunkCoords(p_151350_1_, p_151350_2_);
        int var4 = p_151350_1_ * 16 + p_151350_0_.rand.nextInt(16);
        int var5 = p_151350_2_ * 16 + p_151350_0_.rand.nextInt(16);
        int var6 = p_151350_0_.rand.nextInt(var3 == null ? p_151350_0_.getActualHeight() : var3.getTopFilledSegment() + 16 - 1);
        return new ChunkPosition(var4, var6, var5);
    }

    /**
     * adds all chunks within the spawn radius of the players to eligibleChunksForSpawning. pars: the world,
     * hostileCreatures, passiveCreatures. returns number of eligible chunks.
     */
    public int findChunksForSpawning(WorldServer par1WorldServer, boolean par2, boolean par3, boolean par4)
    {
        if (!par2 && !par3)
        {
            return 0;
        }
        else
        {
            EntityPlayer player = null;

            if (par1WorldServer.playerEntities.size() == 1)
            {
                player = (EntityPlayer)par1WorldServer.playerEntities.get(0);
            }

            int var5;
            int var8;

            if (player == null || player.chunkCoordX != this.lastPlayerChunkX || player.chunkCoordZ != this.lastPlayerChunkZ || this.eligibleChunksForSpawning.size() <= 0)
            {
                this.eligibleChunksForSpawning.clear();

                for (var5 = 0; var5 < par1WorldServer.playerEntities.size(); ++var5)
                {
                    EntityPlayer var34 = (EntityPlayer)par1WorldServer.playerEntities.get(var5);
                    int var35 = MathHelper.floor_double(var34.posX / 16.0D);
                    var8 = MathHelper.floor_double(var34.posZ / 16.0D);
                    byte var36 = 8;

                    for (int var37 = -var36; var37 <= var36; ++var37)
                    {
                        for (int var39 = -var36; var39 <= var36; ++var39)
                        {
                            boolean var38 = var37 == -var36 || var37 == var36 || var39 == -var36 || var39 == var36;
                            ChunkCoordIntPair chunk = new ChunkCoordIntPair(var37 + var35, var39 + var8);

                            if (!var38)
                            {
                                this.eligibleChunksForSpawning.put(chunk, Boolean.valueOf(false));
                            }
                            else if (!this.eligibleChunksForSpawning.containsKey(chunk))
                            {
                                this.eligibleChunksForSpawning.put(chunk, Boolean.valueOf(true));
                            }
                        }
                    }
                }

                if (player != null)
                {
                    this.lastPlayerChunkX = player.chunkCoordX;
                    this.lastPlayerChunkZ = player.chunkCoordZ;
                }
            }

            var5 = 0;
            ChunkCoordinates var371 = par1WorldServer.getSpawnPoint();
            EnumCreatureType[] var381 = EnumCreatureType.values();
            var8 = var381.length;

            for (int var391 = 0; var391 < var8; ++var391)
            {
                EnumCreatureType var411 = var381[var391];

                if ((!var411.getPeacefulCreature() || par3) && (var411.getPeacefulCreature() || par2) && (!var411.getAnimal() || par4) && par1WorldServer.countEntities(var411.getCreatureClass()) <= var411.getMaxNumberOfCreature() * this.eligibleChunksForSpawning.size() / 256)
                {
                    Iterator var40 = this.eligibleChunksForSpawning.keySet().iterator();
                    label120:

                    while (var40.hasNext())
                    {
                        ChunkCoordIntPair var42 = (ChunkCoordIntPair)var40.next();

                        if (!((Boolean)this.eligibleChunksForSpawning.get(var42)).booleanValue())
                        {
                            Chunk var43 = par1WorldServer.getChunkFromChunkCoords(var42.chunkXPos, var42.chunkZPos);
                            int var14 = var42.chunkXPos * 16 + par1WorldServer.rand.nextInt(16);
                            int var16 = var42.chunkZPos * 16 + par1WorldServer.rand.nextInt(16);
                            int var15 = par1WorldServer.rand.nextInt(var43 == null ? par1WorldServer.getActualHeight() : var43.getTopFilledSegment() + 16 - 1);

                            if (!par1WorldServer.getBlock(var14, var15, var16).isNormalCube() && par1WorldServer.getBlock(var14, var15, var16).getMaterial() == var411.getCreatureMaterial())
                            {
                                int var17 = 0;
                                int var18 = 0;

                                while (var18 < 3)
                                {
                                    int var19 = var14;
                                    int var20 = var15;
                                    int var21 = var16;
                                    byte var22 = 6;
                                    BiomeGenBase.SpawnListEntry var23 = null;
                                    IEntityLivingData var24 = null;
                                    int var25 = 0;

                                    while (true)
                                    {
                                        if (var25 < 4)
                                        {
                                            label113:
                                            {
                                                var19 += par1WorldServer.rand.nextInt(var22) - par1WorldServer.rand.nextInt(var22);
                                                var20 += par1WorldServer.rand.nextInt(1) - par1WorldServer.rand.nextInt(1);
                                                var21 += par1WorldServer.rand.nextInt(var22) - par1WorldServer.rand.nextInt(var22);

                                                if (canCreatureTypeSpawnAtLocation(var411, par1WorldServer, var19, var20, var21))
                                                {
                                                    float var26 = (float)var19 + 0.5F;
                                                    float var27 = (float)var20;
                                                    float var28 = (float)var21 + 0.5F;

                                                    if (par1WorldServer.getClosestPlayer((double)var26, (double)var27, (double)var28, 24.0D) == null)
                                                    {
                                                        float var29 = var26 - (float)var371.posX;
                                                        float var30 = var27 - (float)var371.posY;
                                                        float var31 = var28 - (float)var371.posZ;
                                                        float var32 = var29 * var29 + var30 * var30 + var31 * var31;

                                                        if (var32 >= 576.0F)
                                                        {
                                                            if (var23 == null)
                                                            {
                                                                var23 = par1WorldServer.spawnRandomCreature(var411, var19, var20, var21);

                                                                if (var23 == null)
                                                                {
                                                                    break label113;
                                                                }
                                                            }

                                                            EntityLiving var41;

                                                            try
                                                            {
                                                                var41 = (EntityLiving)this.mapSampleEntitiesByClass.get(var23.entityClass);

                                                                if (var41 == null)
                                                                {
                                                                    var41 = (EntityLiving)var23.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par1WorldServer});
                                                                    this.mapSampleEntitiesByClass.put(var23.entityClass, var41);
                                                                }
                                                            }
                                                            catch (Exception var361)
                                                            {
                                                                var361.printStackTrace();
                                                                return var5;
                                                            }

                                                            var41.setLocationAndAngles((double)var26, (double)var27, (double)var28, par1WorldServer.rand.nextFloat() * 360.0F, 0.0F);

                                                            if (var41.getCanSpawnHere())
                                                            {
                                                                this.mapSampleEntitiesByClass.put(var23.entityClass, (Object)null);
                                                                ++var17;
                                                                par1WorldServer.spawnEntityInWorld(var41);
                                                                var24 = var41.onSpawnWithEgg(var24);

                                                                if (var17 >= var41.getMaxSpawnedInChunk())
                                                                {
                                                                    continue label120;
                                                                }
                                                            }

                                                            var5 += var17;
                                                        }
                                                    }
                                                }

                                                ++var25;
                                                continue;
                                            }
                                        }

                                        ++var18;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return var5;
        }
    }

    /**
     * Returns whether or not the specified creature type can spawn at the specified location.
     */
    public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType par0EnumCreatureType, World par1World, int par2, int par3, int par4)
    {
        if (par0EnumCreatureType.getCreatureMaterial() == Material.water)
        {
            return par1World.getBlock(par2, par3, par4).getMaterial().isLiquid() && par1World.getBlock(par2, par3 - 1, par4).getMaterial().isLiquid() && !par1World.getBlock(par2, par3 + 1, par4).isNormalCube();
        }
        else if (!World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4))
        {
            return false;
        }
        else
        {
            Block var5 = par1World.getBlock(par2, par3 - 1, par4);
            return var5 != Blocks.bedrock && !par1World.getBlock(par2, par3, par4).isNormalCube() && !par1World.getBlock(par2, par3, par4).getMaterial().isLiquid() && !par1World.getBlock(par2, par3 + 1, par4).isNormalCube();
        }
    }

    /**
     * Called during chunk generation to spawn initial creatures.
     */
    public static void performWorldGenSpawning(World par0World, BiomeGenBase par1BiomeGenBase, int par2, int par3, int par4, int par5, Random par6Random)
    {
        List var7 = par1BiomeGenBase.getSpawnableList(EnumCreatureType.creature);

        if (!var7.isEmpty())
        {
            while (par6Random.nextFloat() < par1BiomeGenBase.getSpawningChance())
            {
                BiomeGenBase.SpawnListEntry var8 = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(par0World.rand, var7);
                IEntityLivingData var9 = null;
                int var10 = var8.minGroupCount + par6Random.nextInt(1 + var8.maxGroupCount - var8.minGroupCount);
                int var11 = par2 + par6Random.nextInt(par4);
                int var12 = par3 + par6Random.nextInt(par5);
                int var13 = var11;
                int var14 = var12;

                for (int var15 = 0; var15 < var10; ++var15)
                {
                    boolean var16 = false;

                    for (int var17 = 0; !var16 && var17 < 4; ++var17)
                    {
                        int var18 = par0World.getTopSolidOrLiquidBlock(var11, var12);

                        if (canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, par0World, var11, var18, var12))
                        {
                            float var19 = (float)var11 + 0.5F;
                            float var20 = (float)var18;
                            float var21 = (float)var12 + 0.5F;
                            EntityLiving var22;

                            try
                            {
                                var22 = (EntityLiving)var8.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par0World});
                            }
                            catch (Exception var24)
                            {
                                var24.printStackTrace();
                                continue;
                            }

                            var22.setLocationAndAngles((double)var19, (double)var20, (double)var21, par6Random.nextFloat() * 360.0F, 0.0F);
                            par0World.spawnEntityInWorld(var22);
                            var9 = var22.onSpawnWithEgg(var9);
                            var16 = true;
                        }

                        var11 += par6Random.nextInt(5) - par6Random.nextInt(5);

                        for (var12 += par6Random.nextInt(5) - par6Random.nextInt(5); var11 < par2 || var11 >= par2 + par4 || var12 < par3 || var12 >= par3 + par4; var12 = var14 + par6Random.nextInt(5) - par6Random.nextInt(5))
                        {
                            var11 = var13 + par6Random.nextInt(5) - par6Random.nextInt(5);
                        }
                    }
                }
            }
        }
    }
}
