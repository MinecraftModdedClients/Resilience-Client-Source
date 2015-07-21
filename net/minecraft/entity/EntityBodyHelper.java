package net.minecraft.entity;

import net.minecraft.util.MathHelper;

public class EntityBodyHelper
{
    /** Instance of EntityLiving. */
    private EntityLivingBase theLiving;
    private int field_75666_b;
    private float field_75667_c;
    private static final String __OBFID = "CL_00001570";

    public EntityBodyHelper(EntityLivingBase par1EntityLivingBase)
    {
        this.theLiving = par1EntityLivingBase;
    }

    public void func_75664_a()
    {
        double var1 = this.theLiving.posX - this.theLiving.prevPosX;
        double var3 = this.theLiving.posZ - this.theLiving.prevPosZ;

        if (var1 * var1 + var3 * var3 > 2.500000277905201E-7D)
        {
            this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
            this.theLiving.rotationYawHead = this.func_75665_a(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0F);
            this.field_75667_c = this.theLiving.rotationYawHead;
            this.field_75666_b = 0;
        }
        else
        {
            float var5 = 75.0F;

            if (Math.abs(this.theLiving.rotationYawHead - this.field_75667_c) > 15.0F)
            {
                this.field_75666_b = 0;
                this.field_75667_c = this.theLiving.rotationYawHead;
            }
            else
            {
                ++this.field_75666_b;
                boolean var6 = true;

                if (this.field_75666_b > 10)
                {
                    var5 = Math.max(1.0F - (float)(this.field_75666_b - 10) / 10.0F, 0.0F) * 75.0F;
                }
            }

            this.theLiving.renderYawOffset = this.func_75665_a(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, var5);
        }
    }

    private float func_75665_a(float par1, float par2, float par3)
    {
        float var4 = MathHelper.wrapAngleTo180_float(par1 - par2);

        if (var4 < -par3)
        {
            var4 = -par3;
        }

        if (var4 >= par3)
        {
            var4 = par3;
        }

        return par1 - var4;
    }
}
