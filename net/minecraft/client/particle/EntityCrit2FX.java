package net.minecraft.client.particle;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityCrit2FX extends EntityFX
{
    /** Entity that had been hit and done the Critical hit on. */
    private Entity theEntity;
    private int currentLife;
    private int maximumLife;
    private String particleName;
    private static final String __OBFID = "CL_00000899";

    public EntityCrit2FX(World par1World, Entity par2Entity)
    {
        this(par1World, par2Entity, "crit");
    }

    public EntityCrit2FX(World par1World, Entity par2Entity, String par3Str)
    {
        super(par1World, par2Entity.posX, par2Entity.boundingBox.minY + (double)(par2Entity.height / 2.0F), par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
        this.theEntity = par2Entity;
        this.maximumLife = 3;
        this.particleName = par3Str;
        this.onUpdate();
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {}

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        for (int var1 = 0; var1 < 16; ++var1)
        {
            double var2 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
            double var4 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
            double var6 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);

            if (var2 * var2 + var4 * var4 + var6 * var6 <= 1.0D)
            {
                double var8 = this.theEntity.posX + var2 * (double)this.theEntity.width / 4.0D;
                double var10 = this.theEntity.boundingBox.minY + (double)(this.theEntity.height / 2.0F) + var4 * (double)this.theEntity.height / 4.0D;
                double var12 = this.theEntity.posZ + var6 * (double)this.theEntity.width / 4.0D;
                this.worldObj.spawnParticle(this.particleName, var8, var10, var12, var2, var4 + 0.2D, var6);
            }
        }

        ++this.currentLife;

        if (this.currentLife >= this.maximumLife)
        {
            this.setDead();
        }
    }

    public int getFXLayer()
    {
        return 3;
    }
}
