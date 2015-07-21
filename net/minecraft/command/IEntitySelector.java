package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IEntitySelector
{
    IEntitySelector selectAnything = new IEntitySelector()
    {
        private static final String __OBFID = "CL_00001541";
        public boolean isEntityApplicable(Entity par1Entity)
        {
            return par1Entity.isEntityAlive();
        }
    };
    IEntitySelector selectInventories = new IEntitySelector()
    {
        private static final String __OBFID = "CL_00001542";
        public boolean isEntityApplicable(Entity par1Entity)
        {
            return par1Entity instanceof IInventory && par1Entity.isEntityAlive();
        }
    };

    /**
     * Return whether the specified entity is applicable to this filter.
     */
    boolean isEntityApplicable(Entity var1);

    public static class ArmoredMob implements IEntitySelector
    {
        private final ItemStack field_96567_c;
        private static final String __OBFID = "CL_00001543";

        public ArmoredMob(ItemStack par1ItemStack)
        {
            this.field_96567_c = par1ItemStack;
        }

        public boolean isEntityApplicable(Entity par1Entity)
        {
            if (!par1Entity.isEntityAlive())
            {
                return false;
            }
            else if (!(par1Entity instanceof EntityLivingBase))
            {
                return false;
            }
            else
            {
                EntityLivingBase var2 = (EntityLivingBase)par1Entity;
                return var2.getEquipmentInSlot(EntityLiving.getArmorPosition(this.field_96567_c)) != null ? false : (var2 instanceof EntityLiving ? ((EntityLiving)var2).canPickUpLoot() : var2 instanceof EntityPlayer);
            }
        }
    }
}
