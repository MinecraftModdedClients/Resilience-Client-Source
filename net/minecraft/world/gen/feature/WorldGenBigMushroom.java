package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenBigMushroom extends WorldGenerator
{
    /** The mushroom type. 0 for brown, 1 for red. */
    private int mushroomType = -1;
    private static final String __OBFID = "CL_00000415";

    public WorldGenBigMushroom(int par1)
    {
        super(true);
        this.mushroomType = par1;
    }

    public WorldGenBigMushroom()
    {
        super(false);
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(2);

        if (this.mushroomType >= 0)
        {
            var6 = this.mushroomType;
        }

        int var7 = par2Random.nextInt(3) + 4;
        boolean var8 = true;

        if (par4 >= 1 && par4 + var7 + 1 < 256)
        {
            int var11;
            int var12;

            for (int var9 = par4; var9 <= par4 + 1 + var7; ++var9)
            {
                byte var10 = 3;

                if (var9 <= par4 + 3)
                {
                    var10 = 0;
                }

                for (var11 = par3 - var10; var11 <= par3 + var10 && var8; ++var11)
                {
                    for (var12 = par5 - var10; var12 <= par5 + var10 && var8; ++var12)
                    {
                        if (var9 >= 0 && var9 < 256)
                        {
                            Block var13 = par1World.getBlock(var11, var9, var12);

                            if (var13.getMaterial() != Material.air && var13.getMaterial() != Material.leaves)
                            {
                                var8 = false;
                            }
                        }
                        else
                        {
                            var8 = false;
                        }
                    }
                }
            }

            if (!var8)
            {
                return false;
            }
            else
            {
                Block var16 = par1World.getBlock(par3, par4 - 1, par5);

                if (var16 != Blocks.dirt && var16 != Blocks.grass && var16 != Blocks.mycelium)
                {
                    return false;
                }
                else
                {
                    int var17 = par4 + var7;

                    if (var6 == 1)
                    {
                        var17 = par4 + var7 - 3;
                    }

                    for (var11 = var17; var11 <= par4 + var7; ++var11)
                    {
                        var12 = 1;

                        if (var11 < par4 + var7)
                        {
                            ++var12;
                        }

                        if (var6 == 0)
                        {
                            var12 = 3;
                        }

                        for (int var19 = par3 - var12; var19 <= par3 + var12; ++var19)
                        {
                            for (int var14 = par5 - var12; var14 <= par5 + var12; ++var14)
                            {
                                int var15 = 5;

                                if (var19 == par3 - var12)
                                {
                                    --var15;
                                }

                                if (var19 == par3 + var12)
                                {
                                    ++var15;
                                }

                                if (var14 == par5 - var12)
                                {
                                    var15 -= 3;
                                }

                                if (var14 == par5 + var12)
                                {
                                    var15 += 3;
                                }

                                if (var6 == 0 || var11 < par4 + var7)
                                {
                                    if ((var19 == par3 - var12 || var19 == par3 + var12) && (var14 == par5 - var12 || var14 == par5 + var12))
                                    {
                                        continue;
                                    }

                                    if (var19 == par3 - (var12 - 1) && var14 == par5 - var12)
                                    {
                                        var15 = 1;
                                    }

                                    if (var19 == par3 - var12 && var14 == par5 - (var12 - 1))
                                    {
                                        var15 = 1;
                                    }

                                    if (var19 == par3 + (var12 - 1) && var14 == par5 - var12)
                                    {
                                        var15 = 3;
                                    }

                                    if (var19 == par3 + var12 && var14 == par5 - (var12 - 1))
                                    {
                                        var15 = 3;
                                    }

                                    if (var19 == par3 - (var12 - 1) && var14 == par5 + var12)
                                    {
                                        var15 = 7;
                                    }

                                    if (var19 == par3 - var12 && var14 == par5 + (var12 - 1))
                                    {
                                        var15 = 7;
                                    }

                                    if (var19 == par3 + (var12 - 1) && var14 == par5 + var12)
                                    {
                                        var15 = 9;
                                    }

                                    if (var19 == par3 + var12 && var14 == par5 + (var12 - 1))
                                    {
                                        var15 = 9;
                                    }
                                }

                                if (var15 == 5 && var11 < par4 + var7)
                                {
                                    var15 = 0;
                                }

                                if ((var15 != 0 || par4 >= par4 + var7 - 1) && !par1World.getBlock(var19, var11, var14).func_149730_j())
                                {
                                    this.func_150516_a(par1World, var19, var11, var14, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var6), var15);
                                }
                            }
                        }
                    }

                    for (var11 = 0; var11 < var7; ++var11)
                    {
                        Block var18 = par1World.getBlock(par3, par4 + var11, par5);

                        if (!var18.func_149730_j())
                        {
                            this.func_150516_a(par1World, par3, par4 + var11, par5, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var6), 10);
                        }
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
