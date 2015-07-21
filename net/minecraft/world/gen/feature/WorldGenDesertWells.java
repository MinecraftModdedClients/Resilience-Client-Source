package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenDesertWells extends WorldGenerator
{
    private static final String __OBFID = "CL_00000407";

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        while (par1World.isAirBlock(par3, par4, par5) && par4 > 2)
        {
            --par4;
        }

        if (par1World.getBlock(par3, par4, par5) != Blocks.sand)
        {
            return false;
        }
        else
        {
            int var6;
            int var7;

            for (var6 = -2; var6 <= 2; ++var6)
            {
                for (var7 = -2; var7 <= 2; ++var7)
                {
                    if (par1World.isAirBlock(par3 + var6, par4 - 1, par5 + var7) && par1World.isAirBlock(par3 + var6, par4 - 2, par5 + var7))
                    {
                        return false;
                    }
                }
            }

            for (var6 = -1; var6 <= 0; ++var6)
            {
                for (var7 = -2; var7 <= 2; ++var7)
                {
                    for (int var8 = -2; var8 <= 2; ++var8)
                    {
                        par1World.setBlock(par3 + var7, par4 + var6, par5 + var8, Blocks.sandstone, 0, 2);
                    }
                }
            }

            par1World.setBlock(par3, par4, par5, Blocks.flowing_water, 0, 2);
            par1World.setBlock(par3 - 1, par4, par5, Blocks.flowing_water, 0, 2);
            par1World.setBlock(par3 + 1, par4, par5, Blocks.flowing_water, 0, 2);
            par1World.setBlock(par3, par4, par5 - 1, Blocks.flowing_water, 0, 2);
            par1World.setBlock(par3, par4, par5 + 1, Blocks.flowing_water, 0, 2);

            for (var6 = -2; var6 <= 2; ++var6)
            {
                for (var7 = -2; var7 <= 2; ++var7)
                {
                    if (var6 == -2 || var6 == 2 || var7 == -2 || var7 == 2)
                    {
                        par1World.setBlock(par3 + var6, par4 + 1, par5 + var7, Blocks.sandstone, 0, 2);
                    }
                }
            }

            par1World.setBlock(par3 + 2, par4 + 1, par5, Blocks.stone_slab, 1, 2);
            par1World.setBlock(par3 - 2, par4 + 1, par5, Blocks.stone_slab, 1, 2);
            par1World.setBlock(par3, par4 + 1, par5 + 2, Blocks.stone_slab, 1, 2);
            par1World.setBlock(par3, par4 + 1, par5 - 2, Blocks.stone_slab, 1, 2);

            for (var6 = -1; var6 <= 1; ++var6)
            {
                for (var7 = -1; var7 <= 1; ++var7)
                {
                    if (var6 == 0 && var7 == 0)
                    {
                        par1World.setBlock(par3 + var6, par4 + 4, par5 + var7, Blocks.sandstone, 0, 2);
                    }
                    else
                    {
                        par1World.setBlock(par3 + var6, par4 + 4, par5 + var7, Blocks.stone_slab, 1, 2);
                    }
                }
            }

            for (var6 = 1; var6 <= 3; ++var6)
            {
                par1World.setBlock(par3 - 1, par4 + var6, par5 - 1, Blocks.sandstone, 0, 2);
                par1World.setBlock(par3 - 1, par4 + var6, par5 + 1, Blocks.sandstone, 0, 2);
                par1World.setBlock(par3 + 1, par4 + var6, par5 - 1, Blocks.sandstone, 0, 2);
                par1World.setBlock(par3 + 1, par4 + var6, par5 + 1, Blocks.sandstone, 0, 2);
            }

            return true;
        }
    }
}
