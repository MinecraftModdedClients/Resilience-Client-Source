package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenForest extends WorldGenAbstractTree
{
    private boolean field_150531_a;
    private static final String __OBFID = "CL_00000401";

    public WorldGenForest(boolean p_i45449_1_, boolean p_i45449_2_)
    {
        super(p_i45449_1_);
        this.field_150531_a = p_i45449_2_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(3) + 5;

        if (this.field_150531_a)
        {
            var6 += par2Random.nextInt(7);
        }

        boolean var7 = true;

        if (par4 >= 1 && par4 + var6 + 1 <= 256)
        {
            int var10;
            int var11;

            for (int var8 = par4; var8 <= par4 + 1 + var6; ++var8)
            {
                byte var9 = 1;

                if (var8 == par4)
                {
                    var9 = 0;
                }

                if (var8 >= par4 + 1 + var6 - 2)
                {
                    var9 = 2;
                }

                for (var10 = par3 - var9; var10 <= par3 + var9 && var7; ++var10)
                {
                    for (var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                            Block var12 = par1World.getBlock(var10, var8, var11);

                            if (!this.func_150523_a(var12))
                            {
                                var7 = false;
                            }
                        }
                        else
                        {
                            var7 = false;
                        }
                    }
                }
            }

            if (!var7)
            {
                return false;
            }
            else
            {
                Block var17 = par1World.getBlock(par3, par4 - 1, par5);

                if ((var17 == Blocks.grass || var17 == Blocks.dirt || var17 == Blocks.farmland) && par4 < 256 - var6 - 1)
                {
                    this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
                    int var19;

                    for (var19 = par4 - 3 + var6; var19 <= par4 + var6; ++var19)
                    {
                        var10 = var19 - (par4 + var6);
                        var11 = 1 - var10 / 2;

                        for (int var20 = par3 - var11; var20 <= par3 + var11; ++var20)
                        {
                            int var13 = var20 - par3;

                            for (int var14 = par5 - var11; var14 <= par5 + var11; ++var14)
                            {
                                int var15 = var14 - par5;

                                if (Math.abs(var13) != var11 || Math.abs(var15) != var11 || par2Random.nextInt(2) != 0 && var10 != 0)
                                {
                                    Block var16 = par1World.getBlock(var20, var19, var14);

                                    if (var16.getMaterial() == Material.air || var16.getMaterial() == Material.leaves)
                                    {
                                        this.func_150516_a(par1World, var20, var19, var14, Blocks.leaves, 2);
                                    }
                                }
                            }
                        }
                    }

                    for (var19 = 0; var19 < var6; ++var19)
                    {
                        Block var18 = par1World.getBlock(par3, par4 + var19, par5);

                        if (var18.getMaterial() == Material.air || var18.getMaterial() == Material.leaves)
                        {
                            this.func_150516_a(par1World, par3, par4 + var19, par5, Blocks.log, 2);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
