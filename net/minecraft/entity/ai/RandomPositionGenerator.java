package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RandomPositionGenerator
{
    /**
     * used to store a driection when the user passes a point to move towards or away from. WARNING: NEVER THREAD SAFE.
     * MULTIPLE findTowards and findAway calls, will share this var
     */
    private static Vec3 staticVector = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
    private static final String __OBFID = "CL_00001629";

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks
     */
    public static Vec3 findRandomTarget(EntityCreature par0EntityCreature, int par1, int par2)
    {
        return findRandomTargetBlock(par0EntityCreature, par1, par2, (Vec3)null);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the direction of the point par3
     */
    public static Vec3 findRandomTargetBlockTowards(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3)
    {
        staticVector.xCoord = par3Vec3.xCoord - par0EntityCreature.posX;
        staticVector.yCoord = par3Vec3.yCoord - par0EntityCreature.posY;
        staticVector.zCoord = par3Vec3.zCoord - par0EntityCreature.posZ;
        return findRandomTargetBlock(par0EntityCreature, par1, par2, staticVector);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the reverse direction of the point par3
     */
    public static Vec3 findRandomTargetBlockAwayFrom(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3)
    {
        staticVector.xCoord = par0EntityCreature.posX - par3Vec3.xCoord;
        staticVector.yCoord = par0EntityCreature.posY - par3Vec3.yCoord;
        staticVector.zCoord = par0EntityCreature.posZ - par3Vec3.zCoord;
        return findRandomTargetBlock(par0EntityCreature, par1, par2, staticVector);
    }

    /**
     * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction of
     * par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
     */
    private static Vec3 findRandomTargetBlock(EntityCreature par0EntityCreature, int par1, int par2, Vec3 par3Vec3)
    {
        Random var4 = par0EntityCreature.getRNG();
        boolean var5 = false;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        float var9 = -99999.0F;
        boolean var10;

        if (par0EntityCreature.hasHome())
        {
            double var11 = (double)(par0EntityCreature.getHomePosition().getDistanceSquared(MathHelper.floor_double(par0EntityCreature.posX), MathHelper.floor_double(par0EntityCreature.posY), MathHelper.floor_double(par0EntityCreature.posZ)) + 4.0F);
            double var13 = (double)(par0EntityCreature.func_110174_bM() + (float)par1);
            var10 = var11 < var13 * var13;
        }
        else
        {
            var10 = false;
        }

        for (int var16 = 0; var16 < 10; ++var16)
        {
            int var12 = var4.nextInt(2 * par1) - par1;
            int var17 = var4.nextInt(2 * par2) - par2;
            int var14 = var4.nextInt(2 * par1) - par1;

            if (par3Vec3 == null || (double)var12 * par3Vec3.xCoord + (double)var14 * par3Vec3.zCoord >= 0.0D)
            {
                var12 += MathHelper.floor_double(par0EntityCreature.posX);
                var17 += MathHelper.floor_double(par0EntityCreature.posY);
                var14 += MathHelper.floor_double(par0EntityCreature.posZ);

                if (!var10 || par0EntityCreature.isWithinHomeDistance(var12, var17, var14))
                {
                    float var15 = par0EntityCreature.getBlockPathWeight(var12, var17, var14);

                    if (var15 > var9)
                    {
                        var9 = var15;
                        var6 = var12;
                        var7 = var17;
                        var8 = var14;
                        var5 = true;
                    }
                }
            }
        }

        if (var5)
        {
            return par0EntityCreature.worldObj.getWorldVec3Pool().getVecFromPool((double)var6, (double)var7, (double)var8);
        }
        else
        {
            return null;
        }
    }
}
