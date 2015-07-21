package net.minecraft.util;

public class MouseFilter
{
    private float field_76336_a;
    private float field_76334_b;
    private float field_76335_c;
    private static final String __OBFID = "CL_00001500";

    /**
     * Smooths mouse input
     */
    public float smooth(float par1, float par2)
    {
        this.field_76336_a += par1;
        par1 = (this.field_76336_a - this.field_76334_b) * par2;
        this.field_76335_c += (par1 - this.field_76335_c) * 0.5F;

        if (par1 > 0.0F && par1 > this.field_76335_c || par1 < 0.0F && par1 < this.field_76335_c)
        {
            par1 = this.field_76335_c;
        }

        this.field_76334_b += par1;
        return par1;
    }
}
