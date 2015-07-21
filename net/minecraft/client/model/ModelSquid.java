package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSquid extends ModelBase
{
    /** The squid's body */
    ModelRenderer squidBody;

    /** The squid's tentacles */
    ModelRenderer[] squidTentacles = new ModelRenderer[8];
    private static final String __OBFID = "CL_00000861";

    public ModelSquid()
    {
        byte var1 = -16;
        this.squidBody = new ModelRenderer(this, 0, 0);
        this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
        this.squidBody.rotationPointY += (float)(24 + var1);

        for (int var2 = 0; var2 < this.squidTentacles.length; ++var2)
        {
            this.squidTentacles[var2] = new ModelRenderer(this, 48, 0);
            double var3 = (double)var2 * Math.PI * 2.0D / (double)this.squidTentacles.length;
            float var5 = (float)Math.cos(var3) * 5.0F;
            float var6 = (float)Math.sin(var3) * 5.0F;
            this.squidTentacles[var2].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
            this.squidTentacles[var2].rotationPointX = var5;
            this.squidTentacles[var2].rotationPointZ = var6;
            this.squidTentacles[var2].rotationPointY = (float)(31 + var1);
            var3 = (double)var2 * Math.PI * -2.0D / (double)this.squidTentacles.length + (Math.PI / 2D);
            this.squidTentacles[var2].rotateAngleY = (float)var3;
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        ModelRenderer[] var8 = this.squidTentacles;
        int var9 = var8.length;

        for (int var10 = 0; var10 < var9; ++var10)
        {
            ModelRenderer var11 = var8[var10];
            var11.rotateAngleX = par3;
        }
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.squidBody.render(par7);

        for (int var8 = 0; var8 < this.squidTentacles.length; ++var8)
        {
            this.squidTentacles[var8].render(par7);
        }
    }
}
