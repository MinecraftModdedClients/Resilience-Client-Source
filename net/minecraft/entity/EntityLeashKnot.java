package net.minecraft.entity;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging
{
    private static final String __OBFID = "CL_00001548";

    public EntityLeashKnot(World par1World)
    {
        super(par1World);
    }

    public EntityLeashKnot(World par1World, int par2, int par3, int par4)
    {
        super(par1World, par2, par3, par4, 0);
        this.setPosition((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D);
    }

    protected void entityInit()
    {
        super.entityInit();
    }

    public void setDirection(int par1) {}

    public int getWidthPixels()
    {
        return 9;
    }

    public int getHeightPixels()
    {
        return 9;
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double par1)
    {
        return par1 < 1024.0D;
    }

    /**
     * Called when this entity is broken. Entity parameter may be null.
     */
    public void onBroken(Entity par1Entity) {}

    /**
     * Either write this entity to the NBT tag given and return true, or return false without doing anything. If this
     * returns false the entity is not saved on disk. Ridden entities return false here as they are saved with their
     * rider.
     */
    public boolean writeToNBTOptional(NBTTagCompound par1NBTTagCompound)
    {
        return false;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

    /**
     * First layer of player interaction
     */
    public boolean interactFirst(EntityPlayer par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.getHeldItem();
        boolean var3 = false;
        double var4;
        List var6;
        Iterator var7;
        EntityLiving var8;

        if (var2 != null && var2.getItem() == Items.lead && !this.worldObj.isClient)
        {
            var4 = 7.0D;
            var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));

            if (var6 != null)
            {
                var7 = var6.iterator();

                while (var7.hasNext())
                {
                    var8 = (EntityLiving)var7.next();

                    if (var8.getLeashed() && var8.getLeashedToEntity() == par1EntityPlayer)
                    {
                        var8.setLeashedToEntity(this, true);
                        var3 = true;
                    }
                }
            }
        }

        if (!this.worldObj.isClient && !var3)
        {
            this.setDead();

            if (par1EntityPlayer.capabilities.isCreativeMode)
            {
                var4 = 7.0D;
                var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));

                if (var6 != null)
                {
                    var7 = var6.iterator();

                    while (var7.hasNext())
                    {
                        var8 = (EntityLiving)var7.next();

                        if (var8.getLeashed() && var8.getLeashedToEntity() == this)
                        {
                            var8.clearLeashed(true, false);
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * checks to make sure painting can be placed there
     */
    public boolean onValidSurface()
    {
        return this.worldObj.getBlock(this.field_146063_b, this.field_146064_c, this.field_146062_d).getRenderType() == 11;
    }

    public static EntityLeashKnot func_110129_a(World par0World, int par1, int par2, int par3)
    {
        EntityLeashKnot var4 = new EntityLeashKnot(par0World, par1, par2, par3);
        var4.forceSpawn = true;
        par0World.spawnEntityInWorld(var4);
        return var4;
    }

    public static EntityLeashKnot getKnotForBlock(World par0World, int par1, int par2, int par3)
    {
        List var4 = par0World.getEntitiesWithinAABB(EntityLeashKnot.class, AxisAlignedBB.getAABBPool().getAABB((double)par1 - 1.0D, (double)par2 - 1.0D, (double)par3 - 1.0D, (double)par1 + 1.0D, (double)par2 + 1.0D, (double)par3 + 1.0D));

        if (var4 != null)
        {
            Iterator var5 = var4.iterator();

            while (var5.hasNext())
            {
                EntityLeashKnot var6 = (EntityLeashKnot)var5.next();

                if (var6.field_146063_b == par1 && var6.field_146064_c == par2 && var6.field_146062_d == par3)
                {
                    return var6;
                }
            }
        }

        return null;
    }
}
