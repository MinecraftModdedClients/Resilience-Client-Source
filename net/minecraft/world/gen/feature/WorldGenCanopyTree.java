package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class WorldGenCanopyTree extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000430";

    public WorldGenCanopyTree(boolean p_i45461_1_)
    {
        super(p_i45461_1_);
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(3) + par2Random.nextInt(2) + 6;
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
                Block var20 = par1World.getBlock(par3, par4 - 1, par5);

                if ((var20 == Blocks.grass || var20 == Blocks.dirt) && par4 < 256 - var6 - 1)
                {
                    this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
                    this.func_150515_a(par1World, par3 + 1, par4 - 1, par5, Blocks.dirt);
                    this.func_150515_a(par1World, par3 + 1, par4 - 1, par5 + 1, Blocks.dirt);
                    this.func_150515_a(par1World, par3, par4 - 1, par5 + 1, Blocks.dirt);
                    int var21 = par2Random.nextInt(4);
                    var10 = var6 - par2Random.nextInt(4);
                    var11 = 2 - par2Random.nextInt(3);
                    int var22 = par3;
                    int var13 = par5;
                    int var14 = 0;
                    int var15;
                    int var16;

                    for (var15 = 0; var15 < var6; ++var15)
                    {
                        var16 = par4 + var15;

                        if (var15 >= var10 && var11 > 0)
                        {
                            var22 += Direction.offsetX[var21];
                            var13 += Direction.offsetZ[var21];
                            --var11;
                        }

                        Block var17 = par1World.getBlock(var22, var16, var13);

                        if (var17.getMaterial() == Material.air || var17.getMaterial() == Material.leaves)
                        {
                            this.func_150516_a(par1World, var22, var16, var13, Blocks.log2, 1);
                            this.func_150516_a(par1World, var22 + 1, var16, var13, Blocks.log2, 1);
                            this.func_150516_a(par1World, var22, var16, var13 + 1, Blocks.log2, 1);
                            this.func_150516_a(par1World, var22 + 1, var16, var13 + 1, Blocks.log2, 1);
                            var14 = var16;
                        }
                    }

                    for (var15 = -2; var15 <= 0; ++var15)
                    {
                        for (var16 = -2; var16 <= 0; ++var16)
                        {
                            byte var25 = -1;
                            this.func_150526_a(par1World, var22 + var15, var14 + var25, var13 + var16);
                            this.func_150526_a(par1World, 1 + var22 - var15, var14 + var25, var13 + var16);
                            this.func_150526_a(par1World, var22 + var15, var14 + var25, 1 + var13 - var16);
                            this.func_150526_a(par1World, 1 + var22 - var15, var14 + var25, 1 + var13 - var16);

                            if ((var15 > -2 || var16 > -1) && (var15 != -1 || var16 != -2))
                            {
                                byte var24 = 1;
                                this.func_150526_a(par1World, var22 + var15, var14 + var24, var13 + var16);
                                this.func_150526_a(par1World, 1 + var22 - var15, var14 + var24, var13 + var16);
                                this.func_150526_a(par1World, var22 + var15, var14 + var24, 1 + var13 - var16);
                                this.func_150526_a(par1World, 1 + var22 - var15, var14 + var24, 1 + var13 - var16);
                            }
                        }
                    }

                    if (par2Random.nextBoolean())
                    {
                        this.func_150526_a(par1World, var22, var14 + 2, var13);
                        this.func_150526_a(par1World, var22 + 1, var14 + 2, var13);
                        this.func_150526_a(par1World, var22 + 1, var14 + 2, var13 + 1);
                        this.func_150526_a(par1World, var22, var14 + 2, var13 + 1);
                    }

                    for (var15 = -3; var15 <= 4; ++var15)
                    {
                        for (var16 = -3; var16 <= 4; ++var16)
                        {
                            if ((var15 != -3 || var16 != -3) && (var15 != -3 || var16 != 4) && (var15 != 4 || var16 != -3) && (var15 != 4 || var16 != 4) && (Math.abs(var15) < 3 || Math.abs(var16) < 3))
                            {
                                this.func_150526_a(par1World, var22 + var15, var14, var13 + var16);
                            }
                        }
                    }

                    for (var15 = -1; var15 <= 2; ++var15)
                    {
                        for (var16 = -1; var16 <= 2; ++var16)
                        {
                            if ((var15 < 0 || var15 > 1 || var16 < 0 || var16 > 1) && par2Random.nextInt(3) <= 0)
                            {
                                int var23 = par2Random.nextInt(3) + 2;
                                int var18;

                                for (var18 = 0; var18 < var23; ++var18)
                                {
                                    this.func_150516_a(par1World, par3 + var15, var14 - var18 - 1, par5 + var16, Blocks.log2, 1);
                                }

                                int var19;

                                for (var18 = -1; var18 <= 1; ++var18)
                                {
                                    for (var19 = -1; var19 <= 1; ++var19)
                                    {
                                        this.func_150526_a(par1World, var22 + var15 + var18, var14 - 0, var13 + var16 + var19);
                                    }
                                }

                                for (var18 = -2; var18 <= 2; ++var18)
                                {
                                    for (var19 = -2; var19 <= 2; ++var19)
                                    {
                                        if (Math.abs(var18) != 2 || Math.abs(var19) != 2)
                                        {
                                            this.func_150526_a(par1World, var22 + var15 + var18, var14 - 1, var13 + var16 + var19);
                                        }
                                    }
                                }
                            }
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

    private void func_150526_a(World p_150526_1_, int p_150526_2_, int p_150526_3_, int p_150526_4_)
    {
        Block var5 = p_150526_1_.getBlock(p_150526_2_, p_150526_3_, p_150526_4_);

        if (var5.getMaterial() == Material.air)
        {
            this.func_150516_a(p_150526_1_, p_150526_2_, p_150526_3_, p_150526_4_, Blocks.leaves2, 1);
        }
    }
}
