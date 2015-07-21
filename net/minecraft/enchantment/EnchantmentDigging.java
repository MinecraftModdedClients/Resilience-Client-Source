package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class EnchantmentDigging extends Enchantment
{
    private static final String __OBFID = "CL_00000104";

    protected EnchantmentDigging(int par1, int par2)
    {
        super(par1, par2, EnumEnchantmentType.digger);
        this.setName("digging");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return 1 + 10 * (par1 - 1);
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return super.getMinEnchantability(par1) + 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 5;
    }

    public boolean canApply(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem() == Items.shears ? true : super.canApply(par1ItemStack);
    }
}
