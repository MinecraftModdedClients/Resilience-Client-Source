package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class NpcMerchant implements IMerchant
{
    /** Instance of Merchants Inventory. */
    private InventoryMerchant theMerchantInventory;

    /** This merchant's current player customer. */
    private EntityPlayer customer;

    /** The MerchantRecipeList instance. */
    private MerchantRecipeList recipeList;
    private static final String __OBFID = "CL_00001705";

    public NpcMerchant(EntityPlayer par1EntityPlayer)
    {
        this.customer = par1EntityPlayer;
        this.theMerchantInventory = new InventoryMerchant(par1EntityPlayer, this);
    }

    public EntityPlayer getCustomer()
    {
        return this.customer;
    }

    public void setCustomer(EntityPlayer par1EntityPlayer) {}

    public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer)
    {
        return this.recipeList;
    }

    public void setRecipes(MerchantRecipeList par1MerchantRecipeList)
    {
        this.recipeList = par1MerchantRecipeList;
    }

    public void useRecipe(MerchantRecipe par1MerchantRecipe) {}

    public void func_110297_a_(ItemStack par1ItemStack) {}
}
