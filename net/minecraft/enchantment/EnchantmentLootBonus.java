package net.minecraft.enchantment;

public class EnchantmentLootBonus extends Enchantment
{
    private static final String __OBFID = "CL_00000119";

    protected EnchantmentLootBonus(int par1, int par2, EnumEnchantmentType par3EnumEnchantmentType)
    {
        super(par1, par2, par3EnumEnchantmentType);

        if (par3EnumEnchantmentType == EnumEnchantmentType.digger)
        {
            this.setName("lootBonusDigger");
        }
        else if (par3EnumEnchantmentType == EnumEnchantmentType.fishing_rod)
        {
            this.setName("lootBonusFishing");
        }
        else
        {
            this.setName("lootBonus");
        }
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return 15 + (par1 - 1) * 9;
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

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment par1Enchantment)
    {
        return super.canApplyTogether(par1Enchantment) && par1Enchantment.effectId != silkTouch.effectId;
    }
}
