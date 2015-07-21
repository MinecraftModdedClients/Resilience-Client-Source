package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelIronGolem extends ModelBase
{
    /** The head model for the iron golem. */
    public ModelRenderer ironGolemHead;

    /** The body model for the iron golem. */
    public ModelRenderer ironGolemBody;

    /** The right arm model for the iron golem. */
    public ModelRenderer ironGolemRightArm;

    /** The left arm model for the iron golem. */
    public ModelRenderer ironGolemLeftArm;

    /** The left leg model for the Iron Golem. */
    public ModelRenderer ironGolemLeftLeg;

    /** The right leg model for the Iron Golem. */
    public ModelRenderer ironGolemRightLeg;
    private static final String __OBFID = "CL_00000863";

    public ModelIronGolem()
    {
        this(0.0F);
    }

    public ModelIronGolem(float par1)
    {
        this(par1, -7.0F);
    }

    public ModelIronGolem(float par1, float par2)
    {
        short var3 = 128;
        short var4 = 128;
        this.ironGolemHead = (new ModelRenderer(this)).setTextureSize(var3, var4);
        this.ironGolemHead.setRotationPoint(0.0F, 0.0F + par2, -2.0F);
        this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, par1);
        this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, par1);
        this.ironGolemBody = (new ModelRenderer(this)).setTextureSize(var3, var4);
        this.ironGolemBody.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
        this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, par1);
        this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, par1 + 0.5F);
        this.ironGolemRightArm = (new ModelRenderer(this)).setTextureSize(var3, var4);
        this.ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, par1);
        this.ironGolemLeftArm = (new ModelRenderer(this)).setTextureSize(var3, var4);
        this.ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, par1);
        this.ironGolemLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(var3, var4);
        this.ironGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + par2, 0.0F);
        this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, par1);
        this.ironGolemRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(var3, var4);
        this.ironGolemRightLeg.mirror = true;
        this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + par2, 0.0F);
        this.ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, par1);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.ironGolemHead.render(par7);
        this.ironGolemBody.render(par7);
        this.ironGolemLeftLeg.render(par7);
        this.ironGolemRightLeg.render(par7);
        this.ironGolemRightArm.render(par7);
        this.ironGolemLeftArm.render(par7);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        this.ironGolemHead.rotateAngleY = par4 / (180F / (float)Math.PI);
        this.ironGolemHead.rotateAngleX = par5 / (180F / (float)Math.PI);
        this.ironGolemLeftLeg.rotateAngleX = -1.5F * this.func_78172_a(par1, 13.0F) * par2;
        this.ironGolemRightLeg.rotateAngleX = 1.5F * this.func_78172_a(par1, 13.0F) * par2;
        this.ironGolemLeftLeg.rotateAngleY = 0.0F;
        this.ironGolemRightLeg.rotateAngleY = 0.0F;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
        EntityIronGolem var5 = (EntityIronGolem)par1EntityLivingBase;
        int var6 = var5.getAttackTimer();

        if (var6 > 0)
        {
            this.ironGolemRightArm.rotateAngleX = -2.0F + 1.5F * this.func_78172_a((float)var6 - par4, 10.0F);
            this.ironGolemLeftArm.rotateAngleX = -2.0F + 1.5F * this.func_78172_a((float)var6 - par4, 10.0F);
        }
        else
        {
            int var7 = var5.getHoldRoseTick();

            if (var7 > 0)
            {
                this.ironGolemRightArm.rotateAngleX = -0.8F + 0.025F * this.func_78172_a((float)var7, 70.0F);
                this.ironGolemLeftArm.rotateAngleX = 0.0F;
            }
            else
            {
                this.ironGolemRightArm.rotateAngleX = (-0.2F + 1.5F * this.func_78172_a(par2, 13.0F)) * par3;
                this.ironGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * this.func_78172_a(par2, 13.0F)) * par3;
            }
        }
    }

    private float func_78172_a(float par1, float par2)
    {
        return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F) / (par2 * 0.25F);
    }
}
