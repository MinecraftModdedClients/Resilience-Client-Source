package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenIceSpike extends WorldGenerator
{
    private static final String __OBFID = "CL_00000417";

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        while (par1World.isAirBlock(par3, par4, par5) && par4 > 2)
        {
            --par4;
        }

        if (par1World.getBlock(par3, par4, par5) != Blocks.snow)
        {
            return false;
        }
        else
        {
            par4 += par2Random.nextInt(4);
            int var6 = par2Random.nextInt(4) + 7;
            int var7 = var6 / 4 + par2Random.nextInt(2);

            if (var7 > 1 && par2Random.nextInt(60) == 0)
            {
                par4 += 10 + par2Random.nextInt(30);
            }

            int var8;
            int var10;
            int var11;

            for (var8 = 0; var8 < var6; ++var8)
            {
                float var9 = (1.0F - (float)var8 / (float)var6) * (float)var7;
                var10 = MathHelper.ceiling_float_int(var9);

                for (var11 = -var10; var11 <= var10; ++var11)
                {
                    float var12 = (float)MathHelper.abs_int(var11) - 0.25F;

                    for (int var13 = -var10; var13 <= var10; ++var13)
                    {
                        float var14 = (float)MathHelper.abs_int(var13) - 0.25F;

                        if ((var11 == 0 && var13 == 0 || var12 * var12 + var14 * var14 <= var9 * var9) && (var11 != -var10 && var11 != var10 && var13 != -var10 && var13 != var10 || par2Random.nextFloat() <= 0.75F))
                        {
                            Block var15 = par1World.getBlock(par3 + var11, par4 + var8, par5 + var13);

                            if (var15.getMaterial() == Material.air || var15 == Blocks.dirt || var15 == Blocks.snow || var15 == Blocks.ice)
                            {
                                this.func_150515_a(par1World, par3 + var11, par4 + var8, par5 + var13, Blocks.packed_ice);
                            }

                            if (var8 != 0 && var10 > 1)
                            {
                                var15 = par1World.getBlock(par3 + var11, par4 - var8, par5 + var13);

                                if (var15.getMaterial() == Material.air || var15 == Blocks.dirt || var15 == Blocks.snow || var15 == Blocks.ice)
                                {
                                    this.func_150515_a(par1World, par3 + var11, par4 - var8, par5 + var13, Blocks.packed_ice);
                                }
                            }
                        }
                    }
                }
            }

            var8 = var7 - 1;

            if (var8 < 0)
            {
                var8 = 0;
            }
            else if (var8 > 1)
            {
                var8 = 1;
            }

            for (int var16 = -var8; var16 <= var8; ++var16)
            {
                var10 = -var8;

                while (var10 <= var8)
                {
                    var11 = par4 - 1;
                    int var17 = 50;

                    if (Math.abs(var16) == 1 && Math.abs(var10) == 1)
                    {
                        var17 = par2Random.nextInt(5);
                    }

                    while (true)
                    {
                        if (var11 > 50)
                        {
                            Block var18 = par1World.getBlock(par3 + var16, var11, par5 + var10);

                            if (var18.getMaterial() == Material.air || var18 == Blocks.dirt || var18 == Blocks.snow || var18 == Blocks.ice || var18 == Blocks.packed_ice)
                            {
                                this.func_150515_a(par1World, par3 + var16, var11, par5 + var10, Blocks.packed_ice);
                                --var11;
                                --var17;

                                if (var17 <= 0)
                                {
                                    var11 -= par2Random.nextInt(5) + 1;
                                    var17 = par2Random.nextInt(5);
                                }

                                continue;
                            }
                        }

                        ++var10;
                        break;
                    }
                }
            }

            return true;
        }
    }
}
