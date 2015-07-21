package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBlaze extends ModelBase
{
    /** The sticks that fly around the Blaze. */
    private ModelRenderer[] blazeSticks = new ModelRenderer[12];
    private ModelRenderer blazeHead;
    private static final String __OBFID = "CL_00000831";

    public ModelBlaze()
    {
        for (int var1 = 0; var1 < this.blazeSticks.length; ++var1)
        {
            this.blazeSticks[var1] = new ModelRenderer(this, 0, 16);
            this.blazeSticks[var1].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
        }

        this.blazeHead = new ModelRenderer(this, 0, 0);
        this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
    }

    public int func_78104_a()
    {
        return 8;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.blazeHead.render(par7);

        for (int var8 = 0; var8 < this.blazeSticks.length; ++var8)
        {
            this.blazeSticks[var8].render(par7);
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        float var8 = par3 * (float)Math.PI * -0.1F;
        int var9;

        for (var9 = 0; var9 < 4; ++var9)
        {
            this.blazeSticks[var9].rotationPointY = -2.0F + MathHelper.cos(((float)(var9 * 2) + par3) * 0.25F);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 9.0F;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 9.0F;
            ++var8;
        }

        var8 = ((float)Math.PI / 4F) + par3 * (float)Math.PI * 0.03F;

        for (var9 = 4; var9 < 8; ++var9)
        {
            this.blazeSticks[var9].rotationPointY = 2.0F + MathHelper.cos(((float)(var9 * 2) + par3) * 0.25F);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 7.0F;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 7.0F;
            ++var8;
        }

        var8 = 0.47123894F + par3 * (float)Math.PI * -0.05F;

        for (var9 = 8; var9 < 12; ++var9)
        {
            this.blazeSticks[var9].rotationPointY = 11.0F + MathHelper.cos(((float)var9 * 1.5F + par3) * 0.5F);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 5.0F;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 5.0F;
            ++var8;
        }

        this.blazeHead.rotateAngleY = par4 / (180F / (float)Math.PI);
        this.blazeHead.rotateAngleX = par5 / (180F / (float)Math.PI);
    }
}
