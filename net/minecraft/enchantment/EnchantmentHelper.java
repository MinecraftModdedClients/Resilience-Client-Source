package net.minecraft.enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandom;

public class EnchantmentHelper
{
    /** Is the random seed of enchantment effects. */
    private static final Random enchantmentRand = new Random();

    /**
     * Used to calculate the extra armor of enchantments on armors equipped on player.
     */
    private static final EnchantmentHelper.ModifierDamage enchantmentModifierDamage = new EnchantmentHelper.ModifierDamage(null);

    /**
     * Used to calculate the (magic) extra damage done by enchantments on current equipped item of player.
     */
    private static final EnchantmentHelper.ModifierLiving enchantmentModifierLiving = new EnchantmentHelper.ModifierLiving(null);
    private static final EnchantmentHelper.HurtIterator field_151388_d = new EnchantmentHelper.HurtIterator(null);
    private static final EnchantmentHelper.DamageIterator field_151389_e = new EnchantmentHelper.DamageIterator(null);
    private static final String __OBFID = "CL_00000107";

    /**
     * Returns the level of enchantment on the ItemStack passed.
     */
    public static int getEnchantmentLevel(int par0, ItemStack par1ItemStack)
    {
        if (par1ItemStack == null)
        {
            return 0;
        }
        else
        {
            NBTTagList var2 = par1ItemStack.getEnchantmentTagList();

            if (var2 == null)
            {
                return 0;
            }
            else
            {
                for (int var3 = 0; var3 < var2.tagCount(); ++var3)
                {
                    short var4 = var2.getCompoundTagAt(var3).getShort("id");
                    short var5 = var2.getCompoundTagAt(var3).getShort("lvl");

                    if (var4 == par0)
                    {
                        return var5;
                    }
                }

                return 0;
            }
        }
    }

    /**
     * Return the enchantments for the specified stack.
     */
    public static Map getEnchantments(ItemStack par0ItemStack)
    {
        LinkedHashMap var1 = new LinkedHashMap();
        NBTTagList var2 = par0ItemStack.getItem() == Items.enchanted_book ? Items.enchanted_book.func_92110_g(par0ItemStack) : par0ItemStack.getEnchantmentTagList();

        if (var2 != null)
        {
            for (int var3 = 0; var3 < var2.tagCount(); ++var3)
            {
                short var4 = var2.getCompoundTagAt(var3).getShort("id");
                short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
                var1.put(Integer.valueOf(var4), Integer.valueOf(var5));
            }
        }

        return var1;
    }

    /**
     * Set the enchantments for the specified stack.
     */
    public static void setEnchantments(Map par0Map, ItemStack par1ItemStack)
    {
        NBTTagList var2 = new NBTTagList();
        Iterator var3 = par0Map.keySet().iterator();

        while (var3.hasNext())
        {
            int var4 = ((Integer)var3.next()).intValue();
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setShort("id", (short)var4);
            var5.setShort("lvl", (short)((Integer)par0Map.get(Integer.valueOf(var4))).intValue());
            var2.appendTag(var5);

            if (par1ItemStack.getItem() == Items.enchanted_book)
            {
                Items.enchanted_book.addEnchantment(par1ItemStack, new EnchantmentData(var4, ((Integer)par0Map.get(Integer.valueOf(var4))).intValue()));
            }
        }

        if (var2.tagCount() > 0)
        {
            if (par1ItemStack.getItem() != Items.enchanted_book)
            {
                par1ItemStack.setTagInfo("ench", var2);
            }
        }
        else if (par1ItemStack.hasTagCompound())
        {
            par1ItemStack.getTagCompound().removeTag("ench");
        }
    }

    /**
     * Returns the biggest level of the enchantment on the array of ItemStack passed.
     */
    public static int getMaxEnchantmentLevel(int par0, ItemStack[] par1ArrayOfItemStack)
    {
        if (par1ArrayOfItemStack == null)
        {
            return 0;
        }
        else
        {
            int var2 = 0;
            ItemStack[] var3 = par1ArrayOfItemStack;
            int var4 = par1ArrayOfItemStack.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                ItemStack var6 = var3[var5];
                int var7 = getEnchantmentLevel(par0, var6);

                if (var7 > var2)
                {
                    var2 = var7;
                }
            }

            return var2;
        }
    }

    /**
     * Executes the enchantment modifier on the ItemStack passed.
     */
    private static void applyEnchantmentModifier(EnchantmentHelper.IModifier par0IEnchantmentModifier, ItemStack par1ItemStack)
    {
        if (par1ItemStack != null)
        {
            NBTTagList var2 = par1ItemStack.getEnchantmentTagList();

            if (var2 != null)
            {
                for (int var3 = 0; var3 < var2.tagCount(); ++var3)
                {
                    short var4 = var2.getCompoundTagAt(var3).getShort("id");
                    short var5 = var2.getCompoundTagAt(var3).getShort("lvl");

                    if (Enchantment.enchantmentsList[var4] != null)
                    {
                        par0IEnchantmentModifier.calculateModifier(Enchantment.enchantmentsList[var4], var5);
                    }
                }
            }
        }
    }

    /**
     * Executes the enchantment modifier on the array of ItemStack passed.
     */
    private static void applyEnchantmentModifierArray(EnchantmentHelper.IModifier par0IEnchantmentModifier, ItemStack[] par1ArrayOfItemStack)
    {
        ItemStack[] var2 = par1ArrayOfItemStack;
        int var3 = par1ArrayOfItemStack.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            ItemStack var5 = var2[var4];
            applyEnchantmentModifier(par0IEnchantmentModifier, var5);
        }
    }

    /**
     * Returns the modifier of protection enchantments on armors equipped on player.
     */
    public static int getEnchantmentModifierDamage(ItemStack[] par0ArrayOfItemStack, DamageSource par1DamageSource)
    {
        enchantmentModifierDamage.damageModifier = 0;
        enchantmentModifierDamage.source = par1DamageSource;
        applyEnchantmentModifierArray(enchantmentModifierDamage, par0ArrayOfItemStack);

        if (enchantmentModifierDamage.damageModifier > 25)
        {
            enchantmentModifierDamage.damageModifier = 25;
        }

        return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
    }

    /**
     * Return the (magic) extra damage of the enchantments on player equipped item.
     */
    public static float getEnchantmentModifierLiving(EntityLivingBase par0EntityLivingBase, EntityLivingBase par1EntityLivingBase)
    {
        enchantmentModifierLiving.livingModifier = 0.0F;
        enchantmentModifierLiving.entityLiving = par1EntityLivingBase;
        applyEnchantmentModifier(enchantmentModifierLiving, par0EntityLivingBase.getHeldItem());
        return enchantmentModifierLiving.livingModifier;
    }

    public static void func_151384_a(EntityLivingBase p_151384_0_, Entity p_151384_1_)
    {
        field_151388_d.field_151363_b = p_151384_1_;
        field_151388_d.field_151364_a = p_151384_0_;
        applyEnchantmentModifierArray(field_151388_d, p_151384_0_.getLastActiveItems());

        if (p_151384_1_ instanceof EntityPlayer)
        {
            applyEnchantmentModifier(field_151388_d, p_151384_0_.getHeldItem());
        }
    }

    public static void func_151385_b(EntityLivingBase p_151385_0_, Entity p_151385_1_)
    {
        field_151389_e.field_151366_a = p_151385_0_;
        field_151389_e.field_151365_b = p_151385_1_;
        applyEnchantmentModifierArray(field_151389_e, p_151385_0_.getLastActiveItems());

        if (p_151385_0_ instanceof EntityPlayer)
        {
            applyEnchantmentModifier(field_151389_e, p_151385_0_.getHeldItem());
        }
    }

    /**
     * Returns the knockback value of enchantments on equipped player item.
     */
    public static int getKnockbackModifier(EntityLivingBase par0EntityLivingBase, EntityLivingBase par1EntityLivingBase)
    {
        return getEnchantmentLevel(Enchantment.knockback.effectId, par0EntityLivingBase.getHeldItem());
    }

    public static int getFireAspectModifier(EntityLivingBase par0EntityLivingBase)
    {
        return getEnchantmentLevel(Enchantment.fireAspect.effectId, par0EntityLivingBase.getHeldItem());
    }

    /**
     * Returns the 'Water Breathing' modifier of enchantments on player equipped armors.
     */
    public static int getRespiration(EntityLivingBase par0EntityLivingBase)
    {
        return getMaxEnchantmentLevel(Enchantment.respiration.effectId, par0EntityLivingBase.getLastActiveItems());
    }

    /**
     * Return the extra efficiency of tools based on enchantments on equipped player item.
     */
    public static int getEfficiencyModifier(EntityLivingBase par0EntityLivingBase)
    {
        return getEnchantmentLevel(Enchantment.efficiency.effectId, par0EntityLivingBase.getHeldItem());
    }

    /**
     * Returns the silk touch status of enchantments on current equipped item of player.
     */
    public static boolean getSilkTouchModifier(EntityLivingBase par0EntityLivingBase)
    {
        return getEnchantmentLevel(Enchantment.silkTouch.effectId, par0EntityLivingBase.getHeldItem()) > 0;
    }

    /**
     * Returns the fortune enchantment modifier of the current equipped item of player.
     */
    public static int getFortuneModifier(EntityLivingBase par0EntityLivingBase)
    {
        return getEnchantmentLevel(Enchantment.fortune.effectId, par0EntityLivingBase.getHeldItem());
    }

    public static int func_151386_g(EntityLivingBase p_151386_0_)
    {
        return getEnchantmentLevel(Enchantment.field_151370_z.effectId, p_151386_0_.getHeldItem());
    }

    public static int func_151387_h(EntityLivingBase p_151387_0_)
    {
        return getEnchantmentLevel(Enchantment.field_151369_A.effectId, p_151387_0_.getHeldItem());
    }

    /**
     * Returns the looting enchantment modifier of the current equipped item of player.
     */
    public static int getLootingModifier(EntityLivingBase par0EntityLivingBase)
    {
        return getEnchantmentLevel(Enchantment.looting.effectId, par0EntityLivingBase.getHeldItem());
    }

    /**
     * Returns the aqua affinity status of enchantments on current equipped item of player.
     */
    public static boolean getAquaAffinityModifier(EntityLivingBase par0EntityLivingBase)
    {
        return getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, par0EntityLivingBase.getLastActiveItems()) > 0;
    }

    public static ItemStack func_92099_a(Enchantment par0Enchantment, EntityLivingBase par1EntityLivingBase)
    {
        ItemStack[] var2 = par1EntityLivingBase.getLastActiveItems();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            ItemStack var5 = var2[var4];

            if (var5 != null && getEnchantmentLevel(par0Enchantment.effectId, var5) > 0)
            {
                return var5;
            }
        }

        return null;
    }

    /**
     * Returns the enchantability of itemstack, it's uses a singular formula for each index (2nd parameter: 0, 1 and 2),
     * cutting to the max enchantability power of the table (3rd parameter)
     */
    public static int calcItemStackEnchantability(Random par0Random, int par1, int par2, ItemStack par3ItemStack)
    {
        Item var4 = par3ItemStack.getItem();
        int var5 = var4.getItemEnchantability();

        if (var5 <= 0)
        {
            return 0;
        }
        else
        {
            if (par2 > 15)
            {
                par2 = 15;
            }

            int var6 = par0Random.nextInt(8) + 1 + (par2 >> 1) + par0Random.nextInt(par2 + 1);
            return par1 == 0 ? Math.max(var6 / 3, 1) : (par1 == 1 ? var6 * 2 / 3 + 1 : Math.max(var6, par2 * 2));
        }
    }

    /**
     * Adds a random enchantment to the specified item. Args: random, itemStack, enchantabilityLevel
     */
    public static ItemStack addRandomEnchantment(Random par0Random, ItemStack par1ItemStack, int par2)
    {
        List var3 = buildEnchantmentList(par0Random, par1ItemStack, par2);
        boolean var4 = par1ItemStack.getItem() == Items.book;

        if (var4)
        {
            par1ItemStack.func_150996_a(Items.enchanted_book);
        }

        if (var3 != null)
        {
            Iterator var5 = var3.iterator();

            while (var5.hasNext())
            {
                EnchantmentData var6 = (EnchantmentData)var5.next();

                if (var4)
                {
                    Items.enchanted_book.addEnchantment(par1ItemStack, var6);
                }
                else
                {
                    par1ItemStack.addEnchantment(var6.enchantmentobj, var6.enchantmentLevel);
                }
            }
        }

        return par1ItemStack;
    }

    /**
     * Create a list of random EnchantmentData (enchantments) that can be added together to the ItemStack, the 3rd
     * parameter is the total enchantability level.
     */
    public static List buildEnchantmentList(Random par0Random, ItemStack par1ItemStack, int par2)
    {
        Item var3 = par1ItemStack.getItem();
        int var4 = var3.getItemEnchantability();

        if (var4 <= 0)
        {
            return null;
        }
        else
        {
            var4 /= 2;
            var4 = 1 + par0Random.nextInt((var4 >> 1) + 1) + par0Random.nextInt((var4 >> 1) + 1);
            int var5 = var4 + par2;
            float var6 = (par0Random.nextFloat() + par0Random.nextFloat() - 1.0F) * 0.15F;
            int var7 = (int)((float)var5 * (1.0F + var6) + 0.5F);

            if (var7 < 1)
            {
                var7 = 1;
            }

            ArrayList var8 = null;
            Map var9 = mapEnchantmentData(var7, par1ItemStack);

            if (var9 != null && !var9.isEmpty())
            {
                EnchantmentData var10 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, var9.values());

                if (var10 != null)
                {
                    var8 = new ArrayList();
                    var8.add(var10);

                    for (int var11 = var7; par0Random.nextInt(50) <= var11; var11 >>= 1)
                    {
                        Iterator var12 = var9.keySet().iterator();

                        while (var12.hasNext())
                        {
                            Integer var13 = (Integer)var12.next();
                            boolean var14 = true;
                            Iterator var15 = var8.iterator();

                            while (true)
                            {
                                if (var15.hasNext())
                                {
                                    EnchantmentData var16 = (EnchantmentData)var15.next();

                                    if (var16.enchantmentobj.canApplyTogether(Enchantment.enchantmentsList[var13.intValue()]))
                                    {
                                        continue;
                                    }

                                    var14 = false;
                                }

                                if (!var14)
                                {
                                    var12.remove();
                                }

                                break;
                            }
                        }

                        if (!var9.isEmpty())
                        {
                            EnchantmentData var17 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, var9.values());
                            var8.add(var17);
                        }
                    }
                }
            }

            return var8;
        }
    }

    /**
     * Creates a 'Map' of EnchantmentData (enchantments) possible to add on the ItemStack and the enchantability level
     * passed.
     */
    public static Map mapEnchantmentData(int par0, ItemStack par1ItemStack)
    {
        Item var2 = par1ItemStack.getItem();
        HashMap var3 = null;
        boolean var4 = par1ItemStack.getItem() == Items.book;
        Enchantment[] var5 = Enchantment.enchantmentsList;
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            Enchantment var8 = var5[var7];

            if (var8 != null && (var8.type.canEnchantItem(var2) || var4))
            {
                for (int var9 = var8.getMinLevel(); var9 <= var8.getMaxLevel(); ++var9)
                {
                    if (par0 >= var8.getMinEnchantability(var9) && par0 <= var8.getMaxEnchantability(var9))
                    {
                        if (var3 == null)
                        {
                            var3 = new HashMap();
                        }

                        var3.put(Integer.valueOf(var8.effectId), new EnchantmentData(var8, var9));
                    }
                }
            }
        }

        return var3;
    }

    static final class ModifierDamage implements EnchantmentHelper.IModifier
    {
        public int damageModifier;
        public DamageSource source;
        private static final String __OBFID = "CL_00000114";

        private ModifierDamage() {}

        public void calculateModifier(Enchantment par1Enchantment, int par2)
        {
            this.damageModifier += par1Enchantment.calcModifierDamage(par2, this.source);
        }

        ModifierDamage(Object par1Empty3)
        {
            this();
        }
    }

    interface IModifier
    {
        void calculateModifier(Enchantment var1, int var2);
    }

    static final class ModifierLiving implements EnchantmentHelper.IModifier
    {
        public float livingModifier;
        public EntityLivingBase entityLiving;
        private static final String __OBFID = "CL_00000112";

        private ModifierLiving() {}

        public void calculateModifier(Enchantment par1Enchantment, int par2)
        {
            this.livingModifier += par1Enchantment.calcModifierLiving(par2, this.entityLiving);
        }

        ModifierLiving(Object par1Empty3)
        {
            this();
        }
    }

    static final class HurtIterator implements EnchantmentHelper.IModifier
    {
        public EntityLivingBase field_151364_a;
        public Entity field_151363_b;
        private static final String __OBFID = "CL_00000110";

        private HurtIterator() {}

        public void calculateModifier(Enchantment par1Enchantment, int par2)
        {
            par1Enchantment.func_151367_b(this.field_151364_a, this.field_151363_b, par2);
        }

        HurtIterator(Object p_i45360_1_)
        {
            this();
        }
    }

    static final class DamageIterator implements EnchantmentHelper.IModifier
    {
        public EntityLivingBase field_151366_a;
        public Entity field_151365_b;
        private static final String __OBFID = "CL_00000109";

        private DamageIterator() {}

        public void calculateModifier(Enchantment par1Enchantment, int par2)
        {
            par1Enchantment.func_151368_a(this.field_151366_a, this.field_151365_b, par2);
        }

        DamageIterator(Object p_i45359_1_)
        {
            this();
        }
    }
}
