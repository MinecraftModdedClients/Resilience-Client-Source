package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class WorldGenTallGrass extends WorldGenerator
{
    private Block field_150522_a;
    private int tallGrassMetadata;
    private static final String __OBFID = "CL_00000437";

    public WorldGenTallGrass(Block p_i45466_1_, int p_i45466_2_)
    {
        this.field_150522_a = p_i45466_1_;
        this.tallGrassMetadata = p_i45466_2_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        Block var6;

        while (((var6 = par1World.getBlock(par3, par4, par5)).getMaterial() == Material.air || var6.getMaterial() == Material.leaves) && par4 > 0)
        {
            --par4;
        }

        for (int var7 = 0; var7 < 128; ++var7)
        {
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(var8, var9, var10) && this.field_150522_a.canBlockStay(par1World, var8, var9, var10))
            {
                par1World.setBlock(var8, var9, var10, this.field_150522_a, this.tallGrassMetadata, 2);
            }
        }

        return true;
    }
}
