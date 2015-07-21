package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemArmor extends Item
{
    /** Holds the 'base' maxDamage that each armorType have. */
    private static final int[] maxDamageArray = new int[] {11, 16, 15, 13};
    private static final String[] CLOTH_OVERLAY_NAMES = new String[] {"leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay"};
    public static final String[] EMPTY_SLOT_NAMES = new String[] {"empty_armor_slot_helmet", "empty_armor_slot_chestplate", "empty_armor_slot_leggings", "empty_armor_slot_boots"};
    private static final IBehaviorDispenseItem dispenserBehavior = new BehaviorDefaultDispenseItem()
    {
        private static final String __OBFID = "CL_00001767";
        protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
        {
            EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
            int var4 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
            int var5 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
            int var6 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
            AxisAlignedBB var7 = AxisAlignedBB.getAABBPool().getAABB((double)var4, (double)var5, (double)var6, (double)(var4 + 1), (double)(var5 + 1), (double)(var6 + 1));
            List var8 = par1IBlockSource.getWorld().selectEntitiesWithinAABB(EntityLivingBase.class, var7, new IEntitySelector.ArmoredMob(par2ItemStack));

            if (var8.size() > 0)
            {
                EntityLivingBase var9 = (EntityLivingBase)var8.get(0);
                int var10 = var9 instanceof EntityPlayer ? 1 : 0;
                int var11 = EntityLiving.getArmorPosition(par2ItemStack);
                ItemStack var12 = par2ItemStack.copy();
                var12.stackSize = 1;
                var9.setCurrentItemOrArmor(var11 - var10, var12);

                if (var9 instanceof EntityLiving)
                {
                    ((EntityLiving)var9).setEquipmentDropChance(var11, 2.0F);
                }

                --par2ItemStack.stackSize;
                return par2ItemStack;
            }
            else
            {
                return super.dispenseStack(par1IBlockSource, par2ItemStack);
            }
        }
    };

    /**
     * Stores the armor type: 0 is helmet, 1 is plate, 2 is legs and 3 is boots
     */
    public final int armorType;

    /** Holds the amount of damage that the armor reduces at full durability. */
    public final int damageReduceAmount;

    /**
     * Used on RenderPlayer to select the correspondent armor to be rendered on the player: 0 is cloth, 1 is chain, 2 is
     * iron, 3 is diamond and 4 is gold.
     */
    public final int renderIndex;

    /** The EnumArmorMaterial used for this ItemArmor */
    private final ItemArmor.ArmorMaterial material;
    private IIcon overlayIcon;
    private IIcon emptySlotIcon;
    private static final String __OBFID = "CL_00001766";

    public ItemArmor(ItemArmor.ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_)
    {
        this.material = p_i45325_1_;
        this.armorType = p_i45325_3_;
        this.renderIndex = p_i45325_2_;
        this.damageReduceAmount = p_i45325_1_.getDamageReductionAmount(p_i45325_3_);
        this.setMaxDamage(p_i45325_1_.getDurability(p_i45325_3_));
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
    }

    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        if (par2 > 0)
        {
            return 16777215;
        }
        else
        {
            int var3 = this.getColor(par1ItemStack);

            if (var3 < 0)
            {
                var3 = 16777215;
            }

            return var3;
        }
    }

    public boolean requiresMultipleRenderPasses()
    {
        return this.material == ItemArmor.ArmorMaterial.CLOTH;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    /**
     * Return the armor material for this armor item.
     */
    public ItemArmor.ArmorMaterial getArmorMaterial()
    {
        return this.material;
    }

    /**
     * Return whether the specified armor ItemStack has a color.
     */
    public boolean hasColor(ItemStack par1ItemStack)
    {
        return this.material != ItemArmor.ArmorMaterial.CLOTH ? false : (!par1ItemStack.hasTagCompound() ? false : (!par1ItemStack.getTagCompound().func_150297_b("display", 10) ? false : par1ItemStack.getTagCompound().getCompoundTag("display").func_150297_b("color", 3)));
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
    public int getColor(ItemStack par1ItemStack)
    {
        if (this.material != ItemArmor.ArmorMaterial.CLOTH)
        {
            return -1;
        }
        else
        {
            NBTTagCompound var2 = par1ItemStack.getTagCompound();

            if (var2 == null)
            {
                return 10511680;
            }
            else
            {
                NBTTagCompound var3 = var2.getCompoundTag("display");
                return var3 == null ? 10511680 : (var3.func_150297_b("color", 3) ? var3.getInteger("color") : 10511680);
            }
        }
    }

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(par1, par2);
    }

    /**
     * Remove the color from the specified armor ItemStack.
     */
    public void removeColor(ItemStack par1ItemStack)
    {
        if (this.material == ItemArmor.ArmorMaterial.CLOTH)
        {
            NBTTagCompound var2 = par1ItemStack.getTagCompound();

            if (var2 != null)
            {
                NBTTagCompound var3 = var2.getCompoundTag("display");

                if (var3.hasKey("color"))
                {
                    var3.removeTag("color");
                }
            }
        }
    }

    public void func_82813_b(ItemStack par1ItemStack, int par2)
    {
        if (this.material != ItemArmor.ArmorMaterial.CLOTH)
        {
            throw new UnsupportedOperationException("Can\'t dye non-leather!");
        }
        else
        {
            NBTTagCompound var3 = par1ItemStack.getTagCompound();

            if (var3 == null)
            {
                var3 = new NBTTagCompound();
                par1ItemStack.setTagCompound(var3);
            }

            NBTTagCompound var4 = var3.getCompoundTag("display");

            if (!var3.func_150297_b("display", 10))
            {
                var3.setTag("display", var4);
            }

            var4.setInteger("color", par2);
        }
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return this.material.func_151685_b() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        if (this.material == ItemArmor.ArmorMaterial.CLOTH)
        {
            this.overlayIcon = par1IconRegister.registerIcon(CLOTH_OVERLAY_NAMES[this.armorType]);
        }

        this.emptySlotIcon = par1IconRegister.registerIcon(EMPTY_SLOT_NAMES[this.armorType]);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        int var4 = EntityLiving.getArmorPosition(par1ItemStack) - 1;
        ItemStack var5 = par3EntityPlayer.getCurrentArmor(var4);

        if (var5 == null)
        {
            par3EntityPlayer.setCurrentItemOrArmor(var4, par1ItemStack.copy());
            par1ItemStack.stackSize = 0;
        }

        return par1ItemStack;
    }

    public static IIcon func_94602_b(int par0)
    {
        switch (par0)
        {
            case 0:
                return Items.diamond_helmet.emptySlotIcon;

            case 1:
                return Items.diamond_chestplate.emptySlotIcon;

            case 2:
                return Items.diamond_leggings.emptySlotIcon;

            case 3:
                return Items.diamond_boots.emptySlotIcon;

            default:
                return null;
        }
    }

    public static enum ArmorMaterial
    {
        CLOTH("CLOTH", 0, 5, new int[]{1, 3, 2, 1}, 15),
        CHAIN("CHAIN", 1, 15, new int[]{2, 5, 4, 1}, 12),
        IRON("IRON", 2, 15, new int[]{2, 6, 5, 2}, 9),
        GOLD("GOLD", 3, 7, new int[]{2, 5, 3, 1}, 25),
        DIAMOND("DIAMOND", 4, 33, new int[]{3, 8, 6, 3}, 10);
        private int maxDamageFactor;
        private int[] damageReductionAmountArray;
        private int enchantability;

        private static final ItemArmor.ArmorMaterial[] $VALUES = new ItemArmor.ArmorMaterial[]{CLOTH, CHAIN, IRON, GOLD, DIAMOND};
        private static final String __OBFID = "CL_00001768";

        private ArmorMaterial(String par1Str, int par2, int par3, int[] par4ArrayOfInteger, int par5)
        {
            this.maxDamageFactor = par3;
            this.damageReductionAmountArray = par4ArrayOfInteger;
            this.enchantability = par5;
        }

        public int getDurability(int par1)
        {
            return ItemArmor.maxDamageArray[par1] * this.maxDamageFactor;
        }

        public int getDamageReductionAmount(int par1)
        {
            return this.damageReductionAmountArray[par1];
        }

        public int getEnchantability()
        {
            return this.enchantability;
        }

        public Item func_151685_b()
        {
            return this == CLOTH ? Items.leather : (this == CHAIN ? Items.iron_ingot : (this == GOLD ? Items.gold_ingot : (this == IRON ? Items.iron_ingot : (this == DIAMOND ? Items.diamond : null))));
        }
    }
}
