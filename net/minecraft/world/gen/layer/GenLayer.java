package net.minecraft.world.gen.layer;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class GenLayer
{
    /** seed from World#getWorldSeed that is used in the LCG prng */
    private long worldGenSeed;

    /** parent GenLayer that was provided via the constructor */
    protected GenLayer parent;

    /**
     * final part of the LCG prng that uses the chunk X, Z coords along with the other two seeds to generate
     * pseudorandom numbers
     */
    private long chunkSeed;

    /** base seed to the LCG prng provided via the constructor */
    protected long baseSeed;
    private static final String __OBFID = "CL_00000559";

    /**
     * the first array item is a linked list of the bioms, the second is the zoom function, the third is the same as the
     * first.
     */
    public static GenLayer[] initializeAllBiomeGenerators(long par0, WorldType par2WorldType)
    {
        boolean var3 = false;
        GenLayerIsland var4 = new GenLayerIsland(1L);
        GenLayerFuzzyZoom var11 = new GenLayerFuzzyZoom(2000L, var4);
        GenLayerAddIsland var12 = new GenLayerAddIsland(1L, var11);
        GenLayerZoom var13 = new GenLayerZoom(2001L, var12);
        var12 = new GenLayerAddIsland(2L, var13);
        var12 = new GenLayerAddIsland(50L, var12);
        var12 = new GenLayerAddIsland(70L, var12);
        GenLayerRemoveTooMuchOcean var16 = new GenLayerRemoveTooMuchOcean(2L, var12);
        GenLayerAddSnow var14 = new GenLayerAddSnow(2L, var16);
        var12 = new GenLayerAddIsland(3L, var14);
        GenLayerEdge var19 = new GenLayerEdge(2L, var12, GenLayerEdge.Mode.COOL_WARM);
        var19 = new GenLayerEdge(2L, var19, GenLayerEdge.Mode.HEAT_ICE);
        var19 = new GenLayerEdge(3L, var19, GenLayerEdge.Mode.SPECIAL);
        var13 = new GenLayerZoom(2002L, var19);
        var13 = new GenLayerZoom(2003L, var13);
        var12 = new GenLayerAddIsland(4L, var13);
        GenLayerAddMushroomIsland var22 = new GenLayerAddMushroomIsland(5L, var12);
        GenLayerDeepOcean var24 = new GenLayerDeepOcean(4L, var22);
        GenLayer var23 = GenLayerZoom.magnify(1000L, var24, 0);
        byte var5 = 4;

        if (par2WorldType == WorldType.LARGE_BIOMES)
        {
            var5 = 6;
        }

        if (var3)
        {
            var5 = 4;
        }

        GenLayer var6 = GenLayerZoom.magnify(1000L, var23, 0);
        GenLayerRiverInit var15 = new GenLayerRiverInit(100L, var6);
        Object var7 = new GenLayerBiome(200L, var23, par2WorldType);

        if (!var3)
        {
            GenLayer var18 = GenLayerZoom.magnify(1000L, (GenLayer)var7, 2);
            var7 = new GenLayerBiomeEdge(1000L, var18);
        }

        GenLayer var8 = GenLayerZoom.magnify(1000L, var15, 2);
        GenLayerHills var21 = new GenLayerHills(1000L, (GenLayer)var7, var8);
        var6 = GenLayerZoom.magnify(1000L, var15, 2);
        var6 = GenLayerZoom.magnify(1000L, var6, var5);
        GenLayerRiver var17 = new GenLayerRiver(1L, var6);
        GenLayerSmooth var20 = new GenLayerSmooth(1000L, var17);
        var7 = new GenLayerRareBiome(1001L, var21);

        for (int var9 = 0; var9 < var5; ++var9)
        {
            var7 = new GenLayerZoom((long)(1000 + var9), (GenLayer)var7);

            if (var9 == 0)
            {
                var7 = new GenLayerAddIsland(3L, (GenLayer)var7);
            }

            if (var9 == 1)
            {
                var7 = new GenLayerShore(1000L, (GenLayer)var7);
            }
        }

        GenLayerSmooth var25 = new GenLayerSmooth(1000L, (GenLayer)var7);
        GenLayerRiverMix var26 = new GenLayerRiverMix(100L, var25, var20);
        GenLayerVoronoiZoom var10 = new GenLayerVoronoiZoom(10L, var26);
        var26.initWorldGenSeed(par0);
        var10.initWorldGenSeed(par0);
        return new GenLayer[] {var26, var10, var26};
    }

    public GenLayer(long par1)
    {
        this.baseSeed = par1;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += par1;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += par1;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += par1;
    }

    /**
     * Initialize layer's local worldGenSeed based on its own baseSeed and the world's global seed (passed in as an
     * argument).
     */
    public void initWorldGenSeed(long par1)
    {
        this.worldGenSeed = par1;

        if (this.parent != null)
        {
            this.parent.initWorldGenSeed(par1);
        }

        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }

    /**
     * Initialize layer's current chunkSeed based on the local worldGenSeed and the (x,z) chunk coordinates.
     */
    public void initChunkSeed(long par1, long par3)
    {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par1;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par3;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par1;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par3;
    }

    /**
     * returns a LCG pseudo random number from [0, x). Args: int x
     */
    protected int nextInt(int par1)
    {
        int var2 = (int)((this.chunkSeed >> 24) % (long)par1);

        if (var2 < 0)
        {
            var2 += par1;
        }

        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return var2;
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public abstract int[] getInts(int var1, int var2, int var3, int var4);

    protected static boolean func_151616_a(final int p_151616_0_, final int p_151616_1_)
    {
        if (p_151616_0_ == p_151616_1_)
        {
            return true;
        }
        else if (p_151616_0_ != BiomeGenBase.field_150607_aa.biomeID && p_151616_0_ != BiomeGenBase.field_150608_ab.biomeID)
        {
            try
            {
                return BiomeGenBase.func_150568_d(p_151616_0_) != null && BiomeGenBase.func_150568_d(p_151616_1_) != null ? BiomeGenBase.func_150568_d(p_151616_0_).func_150569_a(BiomeGenBase.func_150568_d(p_151616_1_)) : false;
            }
            catch (Throwable var5)
            {
                CrashReport var3 = CrashReport.makeCrashReport(var5, "Comparing biomes");
                CrashReportCategory var4 = var3.makeCategory("Biomes being compared");
                var4.addCrashSection("Biome A ID", Integer.valueOf(p_151616_0_));
                var4.addCrashSection("Biome B ID", Integer.valueOf(p_151616_1_));
                var4.addCrashSectionCallable("Biome A", new Callable()
                {
                    private static final String __OBFID = "CL_00000560";
                    public String call()
                    {
                        return String.valueOf(BiomeGenBase.func_150568_d(p_151616_0_));
                    }
                });
                var4.addCrashSectionCallable("Biome B", new Callable()
                {
                    private static final String __OBFID = "CL_00000561";
                    public String call()
                    {
                        return String.valueOf(BiomeGenBase.func_150568_d(p_151616_1_));
                    }
                });
                throw new ReportedException(var3);
            }
        }
        else
        {
            return p_151616_1_ == BiomeGenBase.field_150607_aa.biomeID || p_151616_1_ == BiomeGenBase.field_150608_ab.biomeID;
        }
    }

    protected static boolean func_151618_b(int p_151618_0_)
    {
        return p_151618_0_ == BiomeGenBase.ocean.biomeID || p_151618_0_ == BiomeGenBase.field_150575_M.biomeID || p_151618_0_ == BiomeGenBase.frozenOcean.biomeID;
    }

    protected int func_151619_a(int ... p_151619_1_)
    {
        return p_151619_1_[this.nextInt(p_151619_1_.length)];
    }

    protected int func_151617_b(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_)
    {
        return p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_ ? p_151617_2_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_ ? p_151617_1_ : (p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_ ? p_151617_2_ : (p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_ ? p_151617_2_ : (p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_ ? p_151617_3_ : this.func_151619_a(new int[] {p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_}))))))))));
    }
}
