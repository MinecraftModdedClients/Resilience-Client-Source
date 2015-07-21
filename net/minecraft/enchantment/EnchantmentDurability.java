package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class EnchantmentDurability extends Enchantment
{
    private static final String __OBFID = "CL_00000103";

    protected EnchantmentDurability(int par1, int par2)
    {
        super(par1, par2, EnumEnchantmentType.breakable);
        this.setName("durability");
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return 5 + (par1 - 1) * 8;
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
        return 3;
    }

    public boolean canApply(ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemStackDamageable() ? true : super.canApply(par1ItemStack);
    }

    /**
     * Used by ItemStack.attemptDamageItem. Randomly determines if a point of damage should be negated using the
     * enchantment level (par1). If the ItemStack is Armor then there is a flat 60% chance for damage to be negated no
     * matter the enchantment level, otherwise there is a 1-(par/1) chance for damage to be negated.
     */
    public static boolean negateDamage(ItemStack par0ItemStack, int par1, Random par2Random)
    {
        return par0ItemStack.getItem() instanceof ItemArmor && par2Random.nextFloat() < 0.6F ? false : par2Random.nextInt(par1 + 1) > 0;
    }
}
