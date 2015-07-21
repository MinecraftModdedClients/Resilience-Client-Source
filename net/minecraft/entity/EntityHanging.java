package net.minecraft.entity;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityHanging extends Entity
{
    private int tickCounter1;
    public int hangingDirection;
    public int field_146063_b;
    public int field_146064_c;
    public int field_146062_d;
    private static final String __OBFID = "CL_00001546";

    public EntityHanging(World par1World)
    {
        super(par1World);
        this.yOffset = 0.0F;
        this.setSize(0.5F, 0.5F);
    }

    public EntityHanging(World par1World, int par2, int par3, int par4, int par5)
    {
        this(par1World);
        this.field_146063_b = par2;
        this.field_146064_c = par3;
        this.field_146062_d = par4;
    }

    protected void entityInit() {}

    public void setDirection(int par1)
    {
        this.hangingDirection = par1;
        this.prevRotationYaw = this.rotationYaw = (float)(par1 * 90);
        float var2 = (float)this.getWidthPixels();
        float var3 = (float)this.getHeightPixels();
        float var4 = (float)this.getWidthPixels();

        if (par1 != 2 && par1 != 0)
        {
            var2 = 0.5F;
        }
        else
        {
            var4 = 0.5F;
            this.rotationYaw = this.prevRotationYaw = (float)(Direction.rotateOpposite[par1] * 90);
        }

        var2 /= 32.0F;
        var3 /= 32.0F;
        var4 /= 32.0F;
        float var5 = (float)this.field_146063_b + 0.5F;
        float var6 = (float)this.field_146064_c + 0.5F;
        float var7 = (float)this.field_146062_d + 0.5F;
        float var8 = 0.5625F;

        if (par1 == 2)
        {
            var7 -= var8;
        }

        if (par1 == 1)
        {
            var5 -= var8;
        }

        if (par1 == 0)
        {
            var7 += var8;
        }

        if (par1 == 3)
        {
            var5 += var8;
        }

        if (par1 == 2)
        {
            var5 -= this.func_70517_b(this.getWidthPixels());
        }

        if (par1 == 1)
        {
            var7 += this.func_70517_b(this.getWidthPixels());
        }

        if (par1 == 0)
        {
            var5 += this.func_70517_b(this.getWidthPixels());
        }

        if (par1 == 3)
        {
            var7 -= this.func_70517_b(this.getWidthPixels());
        }

        var6 += this.func_70517_b(this.getHeightPixels());
        this.setPosition((double)var5, (double)var6, (double)var7);
        float var9 = -0.03125F;
        this.boundingBox.setBounds((double)(var5 - var2 - var9), (double)(var6 - var3 - var9), (double)(var7 - var4 - var9), (double)(var5 + var2 + var9), (double)(var6 + var3 + var9), (double)(var7 + var4 + var9));
    }

    private float func_70517_b(int par1)
    {
        return par1 == 32 ? 0.5F : (par1 == 64 ? 0.5F : 0.0F);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.tickCounter1++ == 100 && !this.worldObj.isClient)
        {
            this.tickCounter1 = 0;

            if (!this.isDead && !this.onValidSurface())
            {
                this.setDead();
                this.onBroken((Entity)null);
            }
        }
    }

    /**
     * checks to make sure painting can be placed there
     */
    public boolean onValidSurface()
    {
        if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty())
        {
            return false;
        }
        else
        {
            int var1 = Math.max(1, this.getWidthPixels() / 16);
            int var2 = Math.max(1, this.getHeightPixels() / 16);
            int var3 = this.field_146063_b;
            int var4 = this.field_146064_c;
            int var5 = this.field_146062_d;

            if (this.hangingDirection == 2)
            {
                var3 = MathHelper.floor_double(this.posX - (double)((float)this.getWidthPixels() / 32.0F));
            }

            if (this.hangingDirection == 1)
            {
                var5 = MathHelper.floor_double(this.posZ - (double)((float)this.getWidthPixels() / 32.0F));
            }

            if (this.hangingDirection == 0)
            {
                var3 = MathHelper.floor_double(this.posX - (double)((float)this.getWidthPixels() / 32.0F));
            }

            if (this.hangingDirection == 3)
            {
                var5 = MathHelper.floor_double(this.posZ - (double)((float)this.getWidthPixels() / 32.0F));
            }

            var4 = MathHelper.floor_double(this.posY - (double)((float)this.getHeightPixels() / 32.0F));

            for (int var6 = 0; var6 < var1; ++var6)
            {
                for (int var7 = 0; var7 < var2; ++var7)
                {
                    Material var8;

                    if (this.hangingDirection != 2 && this.hangingDirection != 0)
                    {
                        var8 = this.worldObj.getBlock(this.field_146063_b, var4 + var7, var5 + var6).getMaterial();
                    }
                    else
                    {
                        var8 = this.worldObj.getBlock(var3 + var6, var4 + var7, this.field_146062_d).getMaterial();
                    }

                    if (!var8.isSolid())
                    {
                        return false;
                    }
                }
            }

            List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
            Iterator var10 = var9.iterator();
            Entity var11;

            do
            {
                if (!var10.hasNext())
                {
                    return true;
                }

                var11 = (Entity)var10.next();
            }
            while (!(var11 instanceof EntityHanging));

            return false;
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    /**
     * Called when a player attacks an entity. If this returns true the attack will not happen.
     */
    public boolean hitByEntity(Entity par1Entity)
    {
        return par1Entity instanceof EntityPlayer ? this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)par1Entity), 0.0F) : false;
    }

    public void func_145781_i(int p_145781_1_)
    {
        this.worldObj.func_147450_X();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            if (!this.isDead && !this.worldObj.isClient)
            {
                this.setDead();
                this.setBeenAttacked();
                this.onBroken(par1DamageSource.getEntity());
            }

            return true;
        }
    }

    /**
     * Tries to moves the entity by the passed in displacement. Args: x, y, z
     */
    public void moveEntity(double par1, double par3, double par5)
    {
        if (!this.worldObj.isClient && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.setDead();
            this.onBroken((Entity)null);
        }
    }

    /**
     * Adds to the current velocity of the entity. Args: x, y, z
     */
    public void addVelocity(double par1, double par3, double par5)
    {
        if (!this.worldObj.isClient && !this.isDead && par1 * par1 + par3 * par3 + par5 * par5 > 0.0D)
        {
            this.setDead();
            this.onBroken((Entity)null);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Direction", (byte)this.hangingDirection);
        par1NBTTagCompound.setInteger("TileX", this.field_146063_b);
        par1NBTTagCompound.setInteger("TileY", this.field_146064_c);
        par1NBTTagCompound.setInteger("TileZ", this.field_146062_d);

        switch (this.hangingDirection)
        {
            case 0:
                par1NBTTagCompound.setByte("Dir", (byte)2);
                break;

            case 1:
                par1NBTTagCompound.setByte("Dir", (byte)1);
                break;

            case 2:
                par1NBTTagCompound.setByte("Dir", (byte)0);
                break;

            case 3:
                par1NBTTagCompound.setByte("Dir", (byte)3);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.func_150297_b("Direction", 99))
        {
            this.hangingDirection = par1NBTTagCompound.getByte("Direction");
        }
        else
        {
            switch (par1NBTTagCompound.getByte("Dir"))
            {
                case 0:
                    this.hangingDirection = 2;
                    break;

                case 1:
                    this.hangingDirection = 1;
                    break;

                case 2:
                    this.hangingDirection = 0;
                    break;

                case 3:
                    this.hangingDirection = 3;
            }
        }

        this.field_146063_b = par1NBTTagCompound.getInteger("TileX");
        this.field_146064_c = par1NBTTagCompound.getInteger("TileY");
        this.field_146062_d = par1NBTTagCompound.getInteger("TileZ");
        this.setDirection(this.hangingDirection);
    }

    public abstract int getWidthPixels();

    public abstract int getHeightPixels();

    /**
     * Called when this entity is broken. Entity parameter may be null.
     */
    public abstract void onBroken(Entity var1);

    protected boolean shouldSetPosAfterLoading()
    {
        return false;
    }
}
