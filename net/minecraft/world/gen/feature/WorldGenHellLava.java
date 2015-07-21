package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenHellLava extends WorldGenerator
{
    private Block field_150553_a;
    private boolean field_94524_b;
    private static final String __OBFID = "CL_00000414";

    public WorldGenHellLava(Block p_i45453_1_, boolean p_i45453_2_)
    {
        this.field_150553_a = p_i45453_1_;
        this.field_94524_b = p_i45453_2_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.getBlock(par3, par4 + 1, par5) != Blocks.netherrack)
        {
            return false;
        }
        else if (par1World.getBlock(par3, par4, par5).getMaterial() != Material.air && par1World.getBlock(par3, par4, par5) != Blocks.netherrack)
        {
            return false;
        }
        else
        {
            int var6 = 0;

            if (par1World.getBlock(par3 - 1, par4, par5) == Blocks.netherrack)
            {
                ++var6;
            }

            if (par1World.getBlock(par3 + 1, par4, par5) == Blocks.netherrack)
            {
                ++var6;
            }

            if (par1World.getBlock(par3, par4, par5 - 1) == Blocks.netherrack)
            {
                ++var6;
            }

            if (par1World.getBlock(par3, par4, par5 + 1) == Blocks.netherrack)
            {
                ++var6;
            }

            if (par1World.getBlock(par3, par4 - 1, par5) == Blocks.netherrack)
            {
                ++var6;
            }

            int var7 = 0;

            if (par1World.isAirBlock(par3 - 1, par4, par5))
            {
                ++var7;
            }

            if (par1World.isAirBlock(par3 + 1, par4, par5))
            {
                ++var7;
            }

            if (par1World.isAirBlock(par3, par4, par5 - 1))
            {
                ++var7;
            }

            if (par1World.isAirBlock(par3, par4, par5 + 1))
            {
                ++var7;
            }

            if (par1World.isAirBlock(par3, par4 - 1, par5))
            {
                ++var7;
            }

            if (!this.field_94524_b && var6 == 4 && var7 == 1 || var6 == 5)
            {
                par1World.setBlock(par3, par4, par5, this.field_150553_a, 0, 2);
                par1World.scheduledUpdatesAreImmediate = true;
                this.field_150553_a.updateTick(par1World, par3, par4, par5, par2Random);
                par1World.scheduledUpdatesAreImmediate = false;
            }

            return true;
        }
    }
}
