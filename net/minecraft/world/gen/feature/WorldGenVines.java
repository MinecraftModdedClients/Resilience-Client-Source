package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class WorldGenVines extends WorldGenerator
{
    private static final String __OBFID = "CL_00000439";

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int var6 = par3;

        for (int var7 = par5; par4 < 128; ++par4)
        {
            if (par1World.isAirBlock(par3, par4, par5))
            {
                for (int var8 = 2; var8 <= 5; ++var8)
                {
                    if (Blocks.vine.canPlaceBlockOnSide(par1World, par3, par4, par5, var8))
                    {
                        par1World.setBlock(par3, par4, par5, Blocks.vine, 1 << Direction.facingToDirection[Facing.oppositeSide[var8]], 2);
                        break;
                    }
                }
            }
            else
            {
                par3 = var6 + par2Random.nextInt(4) - par2Random.nextInt(4);
                par5 = var7 + par2Random.nextInt(4) - par2Random.nextInt(4);
            }
        }

        return true;
    }
}
