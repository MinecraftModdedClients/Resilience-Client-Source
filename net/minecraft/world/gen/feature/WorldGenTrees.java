package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class WorldGenTrees extends WorldGenAbstractTree
{
    /** The minimum height of a generated tree. */
    private final int minTreeHeight;

    /** True if this tree should grow Vines. */
    private final boolean vinesGrow;

    /** The metadata value of the wood to use in tree generation. */
    private final int metaWood;

    /** The metadata value of the leaves to use in tree generation. */
    private final int metaLeaves;
    private static final String __OBFID = "CL_00000438";

    public WorldGenTrees(boolean par1)
    {
        this(par1, 4, 0, 0, false);
    }

    public WorldGenTrees(boolean par1, int par2, int par3, int par4, boolean par5)
    {
        super(par1);
        this.minTreeHeight = par2;
        this.metaWood = par3;
        this.metaLeaves = par4;
        this.vinesGrow = par5;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par2Random.nextInt(3) + this.minTreeHeight;
        boolean var7 = true;

        if (par4 >= 1 && par4 + var6 + 1 <= 256)
        {
            byte var9;
            int var11;
            Block var12;

            for (int var8 = par4; var8 <= par4 + 1 + var6; ++var8)
            {
                var9 = 1;

                if (var8 == par4)
                {
                    var9 = 0;
                }

                if (var8 >= par4 + 1 + var6 - 2)
                {
                    var9 = 2;
                }

                for (int var10 = par3 - var9; var10 <= par3 + var9 && var7; ++var10)
                {
                    for (var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                            var12 = par1World.getBlock(var10, var8, var11);

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
                Block var19 = par1World.getBlock(par3, par4 - 1, par5);

                if ((var19 == Blocks.grass || var19 == Blocks.dirt || var19 == Blocks.farmland) && par4 < 256 - var6 - 1)
                {
                    this.func_150515_a(par1World, par3, par4 - 1, par5, Blocks.dirt);
                    var9 = 3;
                    byte var20 = 0;
                    int var13;
                    int var14;
                    int var15;
                    int var21;

                    for (var11 = par4 - var9 + var6; var11 <= par4 + var6; ++var11)
                    {
                        var21 = var11 - (par4 + var6);
                        var13 = var20 + 1 - var21 / 2;

                        for (var14 = par3 - var13; var14 <= par3 + var13; ++var14)
                        {
                            var15 = var14 - par3;

                            for (int var16 = par5 - var13; var16 <= par5 + var13; ++var16)
                            {
                                int var17 = var16 - par5;

                                if (Math.abs(var15) != var13 || Math.abs(var17) != var13 || par2Random.nextInt(2) != 0 && var21 != 0)
                                {
                                    Block var18 = par1World.getBlock(var14, var11, var16);

                                    if (var18.getMaterial() == Material.air || var18.getMaterial() == Material.leaves)
                                    {
                                        this.func_150516_a(par1World, var14, var11, var16, Blocks.leaves, this.metaLeaves);
                                    }
                                }
                            }
                        }
                    }

                    for (var11 = 0; var11 < var6; ++var11)
                    {
                        var12 = par1World.getBlock(par3, par4 + var11, par5);

                        if (var12.getMaterial() == Material.air || var12.getMaterial() == Material.leaves)
                        {
                            this.func_150516_a(par1World, par3, par4 + var11, par5, Blocks.log, this.metaWood);

                            if (this.vinesGrow && var11 > 0)
                            {
                                if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + var11, par5))
                                {
                                    this.func_150516_a(par1World, par3 - 1, par4 + var11, par5, Blocks.vine, 8);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + var11, par5))
                                {
                                    this.func_150516_a(par1World, par3 + 1, par4 + var11, par5, Blocks.vine, 2);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 - 1))
                                {
                                    this.func_150516_a(par1World, par3, par4 + var11, par5 - 1, Blocks.vine, 1);
                                }

                                if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 + 1))
                                {
                                    this.func_150516_a(par1World, par3, par4 + var11, par5 + 1, Blocks.vine, 4);
                                }
                            }
                        }
                    }

                    if (this.vinesGrow)
                    {
                        for (var11 = par4 - 3 + var6; var11 <= par4 + var6; ++var11)
                        {
                            var21 = var11 - (par4 + var6);
                            var13 = 2 - var21 / 2;

                            for (var14 = par3 - var13; var14 <= par3 + var13; ++var14)
                            {
                                for (var15 = par5 - var13; var15 <= par5 + var13; ++var15)
                                {
                                    if (par1World.getBlock(var14, var11, var15).getMaterial() == Material.leaves)
                                    {
                                        if (par2Random.nextInt(4) == 0 && par1World.getBlock(var14 - 1, var11, var15).getMaterial() == Material.air)
                                        {
                                            this.growVines(par1World, var14 - 1, var11, var15, 8);
                                        }

                                        if (par2Random.nextInt(4) == 0 && par1World.getBlock(var14 + 1, var11, var15).getMaterial() == Material.air)
                                        {
                                            this.growVines(par1World, var14 + 1, var11, var15, 2);
                                        }

                                        if (par2Random.nextInt(4) == 0 && par1World.getBlock(var14, var11, var15 - 1).getMaterial() == Material.air)
                                        {
                                            this.growVines(par1World, var14, var11, var15 - 1, 1);
                                        }

                                        if (par2Random.nextInt(4) == 0 && par1World.getBlock(var14, var11, var15 + 1).getMaterial() == Material.air)
                                        {
                                            this.growVines(par1World, var14, var11, var15 + 1, 4);
                                        }
                                    }
                                }
                            }
                        }

                        if (par2Random.nextInt(5) == 0 && var6 > 5)
                        {
                            for (var11 = 0; var11 < 2; ++var11)
                            {
                                for (var21 = 0; var21 < 4; ++var21)
                                {
                                    if (par2Random.nextInt(4 - var11) == 0)
                                    {
                                        var13 = par2Random.nextInt(3);
                                        this.func_150516_a(par1World, par3 + Direction.offsetX[Direction.rotateOpposite[var21]], par4 + var6 - 5 + var11, par5 + Direction.offsetZ[Direction.rotateOpposite[var21]], Blocks.cocoa, var13 << 2 | var21);
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

    /**
     * Grows vines downward from the given block for a given length. Args: World, x, starty, z, vine-length
     */
    private void growVines(World par1World, int par2, int par3, int par4, int par5)
    {
        this.func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
        int var6 = 4;

        while (true)
        {
            --par3;

            if (par1World.getBlock(par2, par3, par4).getMaterial() != Material.air || var6 <= 0)
            {
                return;
            }

            this.func_150516_a(par1World, par2, par3, par4, Blocks.vine, par5);
            --var6;
        }
    }
}
