package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenBlockBlob extends WorldGenerator
{
    private Block field_150545_a;
    private int field_150544_b;
    private static final String __OBFID = "CL_00000402";

    public WorldGenBlockBlob(Block p_i45450_1_, int p_i45450_2_)
    {
        super(false);
        this.field_150545_a = p_i45450_1_;
        this.field_150544_b = p_i45450_2_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        while (true)
        {
            if (par4 > 3)
            {
                label63:
                {
                    if (!par1World.isAirBlock(par3, par4 - 1, par5))
                    {
                        Block var6 = par1World.getBlock(par3, par4 - 1, par5);

                        if (var6 == Blocks.grass || var6 == Blocks.dirt || var6 == Blocks.stone)
                        {
                            break label63;
                        }
                    }

                    --par4;
                    continue;
                }
            }

            if (par4 <= 3)
            {
                return false;
            }

            int var18 = this.field_150544_b;

            for (int var7 = 0; var18 >= 0 && var7 < 3; ++var7)
            {
                int var8 = var18 + par2Random.nextInt(2);
                int var9 = var18 + par2Random.nextInt(2);
                int var10 = var18 + par2Random.nextInt(2);
                float var11 = (float)(var8 + var9 + var10) * 0.333F + 0.5F;

                for (int var12 = par3 - var8; var12 <= par3 + var8; ++var12)
                {
                    for (int var13 = par5 - var10; var13 <= par5 + var10; ++var13)
                    {
                        for (int var14 = par4 - var9; var14 <= par4 + var9; ++var14)
                        {
                            float var15 = (float)(var12 - par3);
                            float var16 = (float)(var13 - par5);
                            float var17 = (float)(var14 - par4);

                            if (var15 * var15 + var16 * var16 + var17 * var17 <= var11 * var11)
                            {
                                par1World.setBlock(var12, var14, var13, this.field_150545_a, 0, 4);
                            }
                        }
                    }
                }

                par3 += -(var18 + 1) + par2Random.nextInt(2 + var18 * 2);
                par5 += -(var18 + 1) + par2Random.nextInt(2 + var18 * 2);
                par4 += 0 - par2Random.nextInt(2);
            }

            return true;
        }
    }
}
