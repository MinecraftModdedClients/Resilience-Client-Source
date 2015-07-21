package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenTaiga1 extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000427";

    public WorldGenTaiga1()
    {
        super(false);
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(5) + 7;
        int var7 = var6 - par2Random.nextInt(2) - 3;
        int var8 = var6 - var7;
        int var9 = 1 + par2Random.nextInt(var8 + 1);
        boolean var10 = true;

        if (par4 >= 1 && par4 + var6 + 1 <= 256)
        {
            int var13;
            int var14;
            int var18;

            for (int var11 = par4; var11 <= par4 + 1 + var6 && var10; ++var11)
            {
                boolean var12 = true;

                if (var11 - par4 < var7)
                {
                    var18 = 0;
                }
                else
                {
                    var18 = var9;
                }

                for (var13 = par3 - var18; var13 <= par3 + var18 && var10; ++var13)
                {
                    for (var14 = par5 - var18; var14 <= par5 + var18 && var10; ++var14)
                    {
                        if (var11 >= 0 && var11 < 256)
                        {
                            Block var15 = par1World.getBlock(var13, var11, var14);

                            if (!this.func_150523_a(var15))
                            {
                                var10 = false;
                            }
                        }
                        else
                        {
                            var10 = false;
                        }
                    }
                }
            }

            if (!var10)
            {
                return false;
            }
            else
            {
                Block var19 = par1World.getBlock(par3, par4 - 1, par5);

                if ((var19 == Blocks.grass || var19 == Blocks.dirt) && par4 < 256 - var6 - 1)
                {
                    this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
                    var18 = 0;

                    for (var13 = par4 + var6; var13 >= par4 + var7; --var13)
                    {
                        for (var14 = par3 - var18; var14 <= par3 + var18; ++var14)
                        {
                            int var21 = var14 - par3;

                            for (int var16 = par5 - var18; var16 <= par5 + var18; ++var16)
                            {
                                int var17 = var16 - par5;

                                if ((Math.abs(var21) != var18 || Math.abs(var17) != var18 || var18 <= 0) && !par1World.getBlock(var14, var13, var16).func_149730_j())
                                {
                                    this.func_150516_a(par1World, var14, var13, var16, Blocks.leaves, 1);
                                }
                            }
                        }

                        if (var18 >= 1 && var13 == par4 + var7 + 1)
                        {
                            --var18;
                        }
                        else if (var18 < var9)
                        {
                            ++var18;
                        }
                    }

                    for (var13 = 0; var13 < var6 - 1; ++var13)
                    {
                        Block var20 = par1World.getBlock(par3, par4 + var13, par5);

                        if (var20.getMaterial() == Material.air || var20.getMaterial() == Material.leaves)
                        {
                            this.func_150516_a(par1World, par3, par4 + var13, par5, Blocks.log, 1);
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
