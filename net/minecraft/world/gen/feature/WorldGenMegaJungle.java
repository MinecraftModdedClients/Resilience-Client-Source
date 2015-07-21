package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenMegaJungle extends WorldGenHugeTrees
{
    private static final String __OBFID = "CL_00000420";

    public WorldGenMegaJungle(boolean p_i45456_1_, int p_i45456_2_, int p_i45456_3_, int p_i45456_4_, int p_i45456_5_)
    {
        super(p_i45456_1_, p_i45456_2_, p_i45456_3_, p_i45456_4_, p_i45456_5_);
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = this.func_150533_a(par2Random);

        if (!this.func_150537_a(par1World, par2Random, par3, par4, par5, var6))
        {
            return false;
        }
        else
        {
            this.func_150543_c(par1World, par3, par5, par4 + var6, 2, par2Random);

            for (int var7 = par4 + var6 - 2 - par2Random.nextInt(4); var7 > par4 + var6 / 2; var7 -= 2 + par2Random.nextInt(4))
            {
                float var8 = par2Random.nextFloat() * (float)Math.PI * 2.0F;
                int var9 = par3 + (int)(0.5F + MathHelper.cos(var8) * 4.0F);
                int var10 = par5 + (int)(0.5F + MathHelper.sin(var8) * 4.0F);
                int var11;

                for (var11 = 0; var11 < 5; ++var11)
                {
                    var9 = par3 + (int)(1.5F + MathHelper.cos(var8) * (float)var11);
                    var10 = par5 + (int)(1.5F + MathHelper.sin(var8) * (float)var11);
                    this.func_150516_a(par1World, var9, var7 - 3 + var11 / 2, var10, Blocks.log, this.woodMetadata);
                }

                var11 = 1 + par2Random.nextInt(2);
                int var12 = var7;

                for (int var13 = var7 - var11; var13 <= var12; ++var13)
                {
                    int var14 = var13 - var12;
                    this.func_150534_b(par1World, var9, var13, var10, 1 - var14, par2Random);
                }
            }

            for (int var15 = 0; var15 < var6; ++var15)
            {
                Block var16 = par1World.getBlock(par3, par4 + var15, par5);

                if (var16.getMaterial() == Material.air || var16.getMaterial() == Material.leaves)
                {
                    this.func_150516_a(par1World, par3, par4 + var15, par5, Blocks.log, this.woodMetadata);

                    if (var15 > 0)
                    {
                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + var15, par5))
                        {
                            this.func_150516_a(par1World, par3 - 1, par4 + var15, par5, Blocks.vine, 8);
                        }

                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var15, par5 - 1))
                        {
                            this.func_150516_a(par1World, par3, par4 + var15, par5 - 1, Blocks.vine, 1);
                        }
                    }
                }

                if (var15 < var6 - 1)
                {
                    var16 = par1World.getBlock(par3 + 1, par4 + var15, par5);

                    if (var16.getMaterial() == Material.air || var16.getMaterial() == Material.leaves)
                    {
                        this.func_150516_a(par1World, par3 + 1, par4 + var15, par5, Blocks.log, this.woodMetadata);

                        if (var15 > 0)
                        {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 2, par4 + var15, par5))
                            {
                                this.func_150516_a(par1World, par3 + 2, par4 + var15, par5, Blocks.vine, 2);
                            }

                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + var15, par5 - 1))
                            {
                                this.func_150516_a(par1World, par3 + 1, par4 + var15, par5 - 1, Blocks.vine, 1);
                            }
                        }
                    }

                    var16 = par1World.getBlock(par3 + 1, par4 + var15, par5 + 1);

                    if (var16.getMaterial() == Material.air || var16.getMaterial() == Material.leaves)
                    {
                        this.func_150516_a(par1World, par3 + 1, par4 + var15, par5 + 1, Blocks.log, this.woodMetadata);

                        if (var15 > 0)
                        {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 2, par4 + var15, par5 + 1))
                            {
                                this.func_150516_a(par1World, par3 + 2, par4 + var15, par5 + 1, Blocks.vine, 2);
                            }

                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + var15, par5 + 2))
                            {
                                this.func_150516_a(par1World, par3 + 1, par4 + var15, par5 + 2, Blocks.vine, 4);
                            }
                        }
                    }

                    var16 = par1World.getBlock(par3, par4 + var15, par5 + 1);

                    if (var16.getMaterial() == Material.air || var16.getMaterial() == Material.leaves)
                    {
                        this.func_150516_a(par1World, par3, par4 + var15, par5 + 1, Blocks.log, this.woodMetadata);

                        if (var15 > 0)
                        {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + var15, par5 + 1))
                            {
                                this.func_150516_a(par1World, par3 - 1, par4 + var15, par5 + 1, Blocks.vine, 8);
                            }

                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var15, par5 + 2))
                            {
                                this.func_150516_a(par1World, par3, par4 + var15, par5 + 2, Blocks.vine, 4);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }

    private void func_150543_c(World p_150543_1_, int p_150543_2_, int p_150543_3_, int p_150543_4_, int p_150543_5_, Random p_150543_6_)
    {
        byte var7 = 2;

        for (int var8 = p_150543_4_ - var7; var8 <= p_150543_4_; ++var8)
        {
            int var9 = var8 - p_150543_4_;
            this.func_150535_a(p_150543_1_, p_150543_2_, var8, p_150543_3_, p_150543_5_ + 1 - var9, p_150543_6_);
        }
    }
}
