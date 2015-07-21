package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class EnchantmentProtection extends Enchantment
{
    /** Holds the name to be translated of each protection type. */
    private static final String[] protectionName = new String[] {"all", "fire", "fall", "explosion", "projectile"};

    /**
     * Holds the base factor of enchantability needed to be able to use the enchant.
     */
    private static final int[] baseEnchantability = new int[] {1, 10, 5, 5, 3};

    /**
     * Holds how much each level increased the enchantability factor to be able to use this enchant.
     */
    private static final int[] levelEnchantability = new int[] {11, 8, 6, 8, 6};

    /**
     * Used on the formula of base enchantability, this is the 'window' factor of values to be able to use thing
     * enchant.
     */
    private static final int[] thresholdEnchantability = new int[] {20, 12, 10, 12, 15};

    /**
     * Defines the type of protection of the enchantment, 0 = all, 1 = fire, 2 = fall (feather fall), 3 = explosion and
     * 4 = projectile.
     */
    public final int protectionType;
    private static final String __OBFID = "CL_00000121";

    public EnchantmentProtection(int par1, int par2, int par3)
    {
        super(par1, par2, EnumEnchantmentType.armor);
        this.protectionType = par3;

        if (par3 == 2)
        {
            this.type = EnumEnchantmentType.armor_feet;
        }
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return baseEnchantability[this.protectionType] + (par1 - 1) * levelEnchantability[this.protectionType];
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return this.getMinEnchantability(par1) + thresholdEnchantability[this.protectionType];
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 4;
    }

    /**
     * Calculates de damage protection of the enchantment based on level and damage source passed.
     */
    public int calcModifierDamage(int par1, DamageSource par2DamageSource)
    {
        if (par2DamageSource.canHarmInCreative())
        {
            return 0;
        }
        else
        {
            float var3 = (float)(6 + par1 * par1) / 3.0F;
            return this.protectionType == 0 ? MathHelper.floor_float(var3 * 0.75F) : (this.protectionType == 1 && par2DamageSource.isFireDamage() ? MathHelper.floor_float(var3 * 1.25F) : (this.protectionType == 2 && par2DamageSource == DamageSource.fall ? MathHelper.floor_float(var3 * 2.5F) : (this.protectionType == 3 && par2DamageSource.isExplosion() ? MathHelper.floor_float(var3 * 1.5F) : (this.protectionType == 4 && par2DamageSource.isProjectile() ? MathHelper.floor_float(var3 * 1.5F) : 0))));
        }
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    public String getName()
    {
        return "enchantment.protect." + protectionName[this.protectionType];
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment par1Enchantment)
    {
        if (par1Enchantment instanceof EnchantmentProtection)
        {
            EnchantmentProtection var2 = (EnchantmentProtection)par1Enchantment;
            return var2.protectionType == this.protectionType ? false : this.protectionType == 2 || var2.protectionType == 2;
        }
        else
        {
            return super.canApplyTogether(par1Enchantment);
        }
    }

    /**
     * Gets the amount of ticks an entity should be set fire, adjusted for fire protection.
     */
    public static int getFireTimeForEntity(Entity par0Entity, int par1)
    {
        int var2 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, par0Entity.getLastActiveItems());

        if (var2 > 0)
        {
            par1 -= MathHelper.floor_float((float)par1 * (float)var2 * 0.15F);
        }

        return par1;
    }

    public static double func_92092_a(Entity par0Entity, double par1)
    {
        int var3 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, par0Entity.getLastActiveItems());

        if (var3 > 0)
        {
            par1 -= (double)MathHelper.floor_double(par1 * (double)((float)var3 * 0.15F));
        }

        return par1;
    }
}
