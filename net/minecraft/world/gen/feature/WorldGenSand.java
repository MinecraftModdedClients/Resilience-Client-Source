package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenSand extends WorldGenerator
{
    private Block field_150517_a;

    /** The maximum radius used when generating a patch of blocks. */
    private int radius;
    private static final String __OBFID = "CL_00000431";

    public WorldGenSand(Block p_i45462_1_, int p_i45462_2_)
    {
        this.field_150517_a = p_i45462_1_;
        this.radius = p_i45462_2_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.getBlock(par3, par4, par5).getMaterial() != Material.water)
        {
            return false;
        }
        else
        {
            int var6 = par2Random.nextInt(this.radius - 2) + 2;
            byte var7 = 2;

            for (int var8 = par3 - var6; var8 <= par3 + var6; ++var8)
            {
                for (int var9 = par5 - var6; var9 <= par5 + var6; ++var9)
                {
                    int var10 = var8 - par3;
                    int var11 = var9 - par5;

                    if (var10 * var10 + var11 * var11 <= var6 * var6)
                    {
                        for (int var12 = par4 - var7; var12 <= par4 + var7; ++var12)
                        {
                            Block var13 = par1World.getBlock(var8, var12, var9);

                            if (var13 == Blocks.dirt || var13 == Blocks.grass)
                            {
                                par1World.setBlock(var8, var12, var9, this.field_150517_a, 0, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
