package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemExpBottle extends Item
{
    private static final String __OBFID = "CL_00000028";

    public ItemExpBottle()
    {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isClient)
        {
            par2World.spawnEntityInWorld(new EntityExpBottle(par2World, par3EntityPlayer));
        }

        return par1ItemStack;
    }
}
