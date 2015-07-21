package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldGenFlowers extends WorldGenerator
{
    private Block field_150552_a;
    private int field_150551_b;
    private static final String __OBFID = "CL_00000410";

    public WorldGenFlowers(Block p_i45452_1_)
    {
        this.field_150552_a = p_i45452_1_;
    }

    public void func_150550_a(Block p_150550_1_, int p_150550_2_)
    {
        this.field_150552_a = p_150550_1_;
        this.field_150551_b = p_150550_2_;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 64; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(var7, var8, var9) && (!par1World.provider.hasNoSky || var8 < 255) && this.field_150552_a.canBlockStay(par1World, var7, var8, var9))
            {
                par1World.setBlock(var7, var8, var9, this.field_150552_a, this.field_150551_b, 2);
            }
        }

        return true;
    }
}
