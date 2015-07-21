package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemPotion extends Item
{
    /**
     * Contains a map from integers to the list of potion effects that potions with that damage value confer (to prevent
     * recalculating it).
     */
    private HashMap effectCache = new HashMap();
    private static final Map field_77835_b = new LinkedHashMap();
    private IIcon field_94591_c;
    private IIcon field_94590_d;
    private IIcon field_94592_ct;
    private static final String __OBFID = "CL_00000055";

    public ItemPotion()
    {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }

    /**
     * Returns a list of potion effects for the specified itemstack.
     */
    public List getEffects(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().func_150297_b("CustomPotionEffects", 9))
        {
            ArrayList var7 = new ArrayList();
            NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("CustomPotionEffects", 10);

            for (int var4 = 0; var4 < var3.tagCount(); ++var4)
            {
                NBTTagCompound var5 = var3.getCompoundTagAt(var4);
                PotionEffect var6 = PotionEffect.readCustomPotionEffectFromNBT(var5);

                if (var6 != null)
                {
                    var7.add(var6);
                }
            }

            return var7;
        }
        else
        {
            List var2 = (List)this.effectCache.get(Integer.valueOf(par1ItemStack.getItemDamage()));

            if (var2 == null)
            {
                var2 = PotionHelper.getPotionEffects(par1ItemStack.getItemDamage(), false);
                this.effectCache.put(Integer.valueOf(par1ItemStack.getItemDamage()), var2);
            }

            return var2;
        }
    }

    /**
     * Returns a list of effects for the specified potion damage value.
     */
    public List getEffects(int par1)
    {
        List var2 = (List)this.effectCache.get(Integer.valueOf(par1));

        if (var2 == null)
        {
            var2 = PotionHelper.getPotionEffects(par1, false);
            this.effectCache.put(Integer.valueOf(par1), var2);
        }

        return var2;
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        if (!par2World.isClient)
        {
            List var4 = this.getEffects(par1ItemStack);

            if (var4 != null)
            {
                Iterator var5 = var4.iterator();

                while (var5.hasNext())
                {
                    PotionEffect var6 = (PotionEffect)var5.next();
                    par3EntityPlayer.addPotionEffect(new PotionEffect(var6));
                }
            }
        }

        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return new ItemStack(Items.glass_bottle);
            }

            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }

        return par1ItemStack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (isSplash(par1ItemStack.getItemDamage()))
        {
            if (!par3EntityPlayer.capabilities.isCreativeMode)
            {
                --par1ItemStack.stackSize;
            }

            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!par2World.isClient)
            {
                par2World.spawnEntityInWorld(new EntityPotion(par2World, par3EntityPlayer, par1ItemStack));
            }

            return par1ItemStack;
        }
        else
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
            return par1ItemStack;
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return false;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int par1)
    {
        return isSplash(par1) ? this.field_94591_c : this.field_94590_d;
    }

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 == 0 ? this.field_94592_ct : super.getIconFromDamageForRenderPass(par1, par2);
    }

    /**
     * returns wether or not a potion is a throwable splash potion based on damage value
     */
    public static boolean isSplash(int par0)
    {
        return (par0 & 16384) != 0;
    }

    public int getColorFromDamage(int par1)
    {
        return PotionHelper.func_77915_a(par1, false);
    }

    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return par2 > 0 ? 16777215 : this.getColorFromDamage(par1ItemStack.getItemDamage());
    }

    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    public boolean isEffectInstant(int par1)
    {
        List var2 = this.getEffects(par1);

        if (var2 != null && !var2.isEmpty())
        {
            Iterator var3 = var2.iterator();
            PotionEffect var4;

            do
            {
                if (!var3.hasNext())
                {
                    return false;
                }

                var4 = (PotionEffect)var3.next();
            }
            while (!Potion.potionTypes[var4.getPotionID()].isInstant());

            return true;
        }
        else
        {
            return false;
        }
    }

    public String getItemStackDisplayName(ItemStack par1ItemStack)
    {
        if (par1ItemStack.getItemDamage() == 0)
        {
            return StatCollector.translateToLocal("item.emptyPotion.name").trim();
        }
        else
        {
            String var2 = "";

            if (isSplash(par1ItemStack.getItemDamage()))
            {
                var2 = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
            }

            List var3 = Items.potionitem.getEffects(par1ItemStack);
            String var4;

            if (var3 != null && !var3.isEmpty())
            {
                var4 = ((PotionEffect)var3.get(0)).getEffectName();
                var4 = var4 + ".postfix";
                return var2 + StatCollector.translateToLocal(var4).trim();
            }
            else
            {
                var4 = PotionHelper.func_77905_c(par1ItemStack.getItemDamage());
                return StatCollector.translateToLocal(var4).trim() + " " + super.getItemStackDisplayName(par1ItemStack);
            }
        }
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (par1ItemStack.getItemDamage() != 0)
        {
            List var5 = Items.potionitem.getEffects(par1ItemStack);
            HashMultimap var6 = HashMultimap.create();
            Iterator var16;

            if (var5 != null && !var5.isEmpty())
            {
                var16 = var5.iterator();

                while (var16.hasNext())
                {
                    PotionEffect var8 = (PotionEffect)var16.next();
                    String var9 = StatCollector.translateToLocal(var8.getEffectName()).trim();
                    Potion var10 = Potion.potionTypes[var8.getPotionID()];
                    Map var11 = var10.func_111186_k();

                    if (var11 != null && var11.size() > 0)
                    {
                        Iterator var12 = var11.entrySet().iterator();

                        while (var12.hasNext())
                        {
                            Entry var13 = (Entry)var12.next();
                            AttributeModifier var14 = (AttributeModifier)var13.getValue();
                            AttributeModifier var15 = new AttributeModifier(var14.getName(), var10.func_111183_a(var8.getAmplifier(), var14), var14.getOperation());
                            var6.put(((IAttribute)var13.getKey()).getAttributeUnlocalizedName(), var15);
                        }
                    }

                    if (var8.getAmplifier() > 0)
                    {
                        var9 = var9 + " " + StatCollector.translateToLocal("potion.potency." + var8.getAmplifier()).trim();
                    }

                    if (var8.getDuration() > 20)
                    {
                        var9 = var9 + " (" + Potion.getDurationString(var8) + ")";
                    }

                    if (var10.isBadEffect())
                    {
                        par3List.add(EnumChatFormatting.RED + var9);
                    }
                    else
                    {
                        par3List.add(EnumChatFormatting.GRAY + var9);
                    }
                }
            }
            else
            {
                String var7 = StatCollector.translateToLocal("potion.empty").trim();
                par3List.add(EnumChatFormatting.GRAY + var7);
            }

            if (!var6.isEmpty())
            {
                par3List.add("");
                par3List.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
                var16 = var6.entries().iterator();

                while (var16.hasNext())
                {
                    Entry var17 = (Entry)var16.next();
                    AttributeModifier var18 = (AttributeModifier)var17.getValue();
                    double var19 = var18.getAmount();
                    double var20;

                    if (var18.getOperation() != 1 && var18.getOperation() != 2)
                    {
                        var20 = var18.getAmount();
                    }
                    else
                    {
                        var20 = var18.getAmount() * 100.0D;
                    }

                    if (var19 > 0.0D)
                    {
                        par3List.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + var18.getOperation(), new Object[] {ItemStack.field_111284_a.format(var20), StatCollector.translateToLocal("attribute.name." + (String)var17.getKey())}));
                    }
                    else if (var19 < 0.0D)
                    {
                        var20 *= -1.0D;
                        par3List.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + var18.getOperation(), new Object[] {ItemStack.field_111284_a.format(var20), StatCollector.translateToLocal("attribute.name." + (String)var17.getKey())}));
                    }
                }
            }
        }
    }

    public boolean hasEffect(ItemStack par1ItemStack)
    {
        List var2 = this.getEffects(par1ItemStack);
        return var2 != null && !var2.isEmpty();
    }

    /**
     * This returns the sub items
     */
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        super.getSubItems(p_150895_1_, p_150895_2_, p_150895_3_);
        int var5;

        if (field_77835_b.isEmpty())
        {
            for (int var4 = 0; var4 <= 15; ++var4)
            {
                for (var5 = 0; var5 <= 1; ++var5)
                {
                    int var6;

                    if (var5 == 0)
                    {
                        var6 = var4 | 8192;
                    }
                    else
                    {
                        var6 = var4 | 16384;
                    }

                    for (int var7 = 0; var7 <= 2; ++var7)
                    {
                        int var8 = var6;

                        if (var7 != 0)
                        {
                            if (var7 == 1)
                            {
                                var8 = var6 | 32;
                            }
                            else if (var7 == 2)
                            {
                                var8 = var6 | 64;
                            }
                        }

                        List var9 = PotionHelper.getPotionEffects(var8, false);

                        if (var9 != null && !var9.isEmpty())
                        {
                            field_77835_b.put(var9, Integer.valueOf(var8));
                        }
                    }
                }
            }
        }

        Iterator var10 = field_77835_b.values().iterator();

        while (var10.hasNext())
        {
            var5 = ((Integer)var10.next()).intValue();
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, var5));
        }
    }

    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.field_94590_d = par1IconRegister.registerIcon(this.getIconString() + "_" + "bottle_drinkable");
        this.field_94591_c = par1IconRegister.registerIcon(this.getIconString() + "_" + "bottle_splash");
        this.field_94592_ct = par1IconRegister.registerIcon(this.getIconString() + "_" + "overlay");
    }

    public static IIcon func_94589_d(String par0Str)
    {
        return par0Str.equals("bottle_drinkable") ? Items.potionitem.field_94590_d : (par0Str.equals("bottle_splash") ? Items.potionitem.field_94591_c : (par0Str.equals("overlay") ? Items.potionitem.field_94592_ct : null));
    }
}
