package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public final class ItemStack
{
    public static final DecimalFormat field_111284_a = new DecimalFormat("#.###");

    /** Size of the stack. */
    public int stackSize;

    /**
     * Number of animation frames to go when receiving an item (by walking into it, for example).
     */
    public int animationsToGo;
    private Item field_151002_e;

    /**
     * A NBTTagMap containing data about an ItemStack. Can only be used for non stackable items
     */
    public NBTTagCompound stackTagCompound;

    /** Damage dealt to the item or number of use. Raise when using items. */
    private int itemDamage;

    /** Item frame this stack is on, or null if not on an item frame. */
    private EntityItemFrame itemFrame;
    private static final String __OBFID = "CL_00000043";

    public ItemStack(Block par1Block)
    {
        this(par1Block, 1);
    }

    public ItemStack(Block par1Block, int par2)
    {
        this(par1Block, par2, 0);
    }

    public ItemStack(Block par1Block, int par2, int par3)
    {
        this(Item.getItemFromBlock(par1Block), par2, par3);
    }

    public ItemStack(Item par1Item)
    {
        this(par1Item, 1);
    }

    public ItemStack(Item par1Item, int par2)
    {
        this(par1Item, par2, 0);
    }

    public ItemStack(Item par1Item, int par2, int par3)
    {
        this.field_151002_e = par1Item;
        this.stackSize = par2;
        this.itemDamage = par3;

        if (this.itemDamage < 0)
        {
            this.itemDamage = 0;
        }
    }

    public static ItemStack loadItemStackFromNBT(NBTTagCompound par0NBTTagCompound)
    {
        ItemStack var1 = new ItemStack();
        var1.readFromNBT(par0NBTTagCompound);
        return var1.getItem() != null ? var1 : null;
    }

    private ItemStack() {}

    /**
     * Remove the argument from the stack size. Return a new stack object with argument size.
     */
    public ItemStack splitStack(int par1)
    {
        ItemStack var2 = new ItemStack(this.field_151002_e, par1, this.itemDamage);

        if (this.stackTagCompound != null)
        {
            var2.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }

        this.stackSize -= par1;
        return var2;
    }

    /**
     * Returns the object corresponding to the stack.
     */
    public Item getItem()
    {
        return this.field_151002_e;
    }

    /**
     * Returns the icon index of the current stack.
     */
    public IIcon getIconIndex()
    {
        return this.getItem().getIconIndex(this);
    }

    public int getItemSpriteNumber()
    {
        return this.getItem().getSpriteNumber();
    }

    public boolean tryPlaceItemIntoWorld(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5, int par6, float par7, float par8, float par9)
    {
        boolean var10 = this.getItem().onItemUse(this, par1EntityPlayer, par2World, par3, par4, par5, par6, par7, par8, par9);

        if (var10)
        {
            par1EntityPlayer.addStat(StatList.objectUseStats[Item.getIdFromItem(this.field_151002_e)], 1);
        }

        return var10;
    }

    public float func_150997_a(Block p_150997_1_)
    {
        return this.getItem().func_150893_a(this, p_150997_1_);
    }

    /**
     * Called whenever this item stack is equipped and right clicked. Returns the new item stack to put in the position
     * where this item is. Args: world, player
     */
    public ItemStack useItemRightClick(World par1World, EntityPlayer par2EntityPlayer)
    {
        return this.getItem().onItemRightClick(this, par1World, par2EntityPlayer);
    }

    public ItemStack onFoodEaten(World par1World, EntityPlayer par2EntityPlayer)
    {
        return this.getItem().onEaten(this, par1World, par2EntityPlayer);
    }

    /**
     * Write the stack fields to a NBT object. Return the new NBT object.
     */
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("id", (short)Item.getIdFromItem(this.field_151002_e));
        par1NBTTagCompound.setByte("Count", (byte)this.stackSize);
        par1NBTTagCompound.setShort("Damage", (short)this.itemDamage);

        if (this.stackTagCompound != null)
        {
            par1NBTTagCompound.setTag("tag", this.stackTagCompound);
        }

        return par1NBTTagCompound;
    }

    /**
     * Read the stack fields from a NBT object.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.field_151002_e = Item.getItemById(par1NBTTagCompound.getShort("id"));
        this.stackSize = par1NBTTagCompound.getByte("Count");
        this.itemDamage = par1NBTTagCompound.getShort("Damage");

        if (this.itemDamage < 0)
        {
            this.itemDamage = 0;
        }

        if (par1NBTTagCompound.func_150297_b("tag", 10))
        {
            this.stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
        }
    }

    /**
     * Returns maximum size of the stack.
     */
    public int getMaxStackSize()
    {
        return this.getItem().getItemStackLimit();
    }

    /**
     * Returns true if the ItemStack can hold 2 or more units of the item.
     */
    public boolean isStackable()
    {
        return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
    }

    /**
     * true if this itemStack is damageable
     */
    public boolean isItemStackDamageable()
    {
        return this.field_151002_e.getMaxDamage() <= 0 ? false : !this.hasTagCompound() || !this.getTagCompound().getBoolean("Unbreakable");
    }

    public boolean getHasSubtypes()
    {
        return this.field_151002_e.getHasSubtypes();
    }

    /**
     * returns true when a damageable item is damaged
     */
    public boolean isItemDamaged()
    {
        return this.isItemStackDamageable() && this.itemDamage > 0;
    }

    /**
     * gets the damage of an itemstack, for displaying purposes
     */
    public int getItemDamageForDisplay()
    {
        return this.itemDamage;
    }

    /**
     * gets the damage of an itemstack
     */
    public int getItemDamage()
    {
        return this.itemDamage;
    }

    /**
     * Sets the item damage of the ItemStack.
     */
    public void setItemDamage(int par1)
    {
        this.itemDamage = par1;

        if (this.itemDamage < 0)
        {
            this.itemDamage = 0;
        }
    }

    /**
     * Returns the max damage an item in the stack can take.
     */
    public int getMaxDamage()
    {
        return this.field_151002_e.getMaxDamage();
    }

    /**
     * Attempts to damage the ItemStack with par1 amount of damage, If the ItemStack has the Unbreaking enchantment
     * there is a chance for each point of damage to be negated. Returns true if it takes more damage than
     * getMaxDamage(). Returns false otherwise or if the ItemStack can't be damaged or if all points of damage are
     * negated.
     */
    public boolean attemptDamageItem(int par1, Random par2Random)
    {
        if (!this.isItemStackDamageable())
        {
            return false;
        }
        else
        {
            if (par1 > 0)
            {
                int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
                int var4 = 0;

                for (int var5 = 0; var3 > 0 && var5 < par1; ++var5)
                {
                    if (EnchantmentDurability.negateDamage(this, var3, par2Random))
                    {
                        ++var4;
                    }
                }

                par1 -= var4;

                if (par1 <= 0)
                {
                    return false;
                }
            }

            this.itemDamage += par1;
            return this.itemDamage > this.getMaxDamage();
        }
    }

    /**
     * Damages the item in the ItemStack
     */
    public void damageItem(int par1, EntityLivingBase par2EntityLivingBase)
    {
        if (!(par2EntityLivingBase instanceof EntityPlayer) || !((EntityPlayer)par2EntityLivingBase).capabilities.isCreativeMode)
        {
            if (this.isItemStackDamageable())
            {
                if (this.attemptDamageItem(par1, par2EntityLivingBase.getRNG()))
                {
                    par2EntityLivingBase.renderBrokenItemStack(this);
                    --this.stackSize;

                    if (par2EntityLivingBase instanceof EntityPlayer)
                    {
                        EntityPlayer var3 = (EntityPlayer)par2EntityLivingBase;
                        var3.addStat(StatList.objectBreakStats[Item.getIdFromItem(this.field_151002_e)], 1);

                        if (this.stackSize == 0 && this.getItem() instanceof ItemBow)
                        {
                            var3.destroyCurrentEquippedItem();
                        }
                    }

                    if (this.stackSize < 0)
                    {
                        this.stackSize = 0;
                    }

                    this.itemDamage = 0;
                }
            }
        }
    }

    /**
     * Calls the corresponding fct in di
     */
    public void hitEntity(EntityLivingBase par1EntityLivingBase, EntityPlayer par2EntityPlayer)
    {
        boolean var3 = this.field_151002_e.hitEntity(this, par1EntityLivingBase, par2EntityPlayer);

        if (var3)
        {
            par2EntityPlayer.addStat(StatList.objectUseStats[Item.getIdFromItem(this.field_151002_e)], 1);
        }
    }

    public void func_150999_a(World p_150999_1_, Block p_150999_2_, int p_150999_3_, int p_150999_4_, int p_150999_5_, EntityPlayer p_150999_6_)
    {
        boolean var7 = this.field_151002_e.onBlockDestroyed(this, p_150999_1_, p_150999_2_, p_150999_3_, p_150999_4_, p_150999_5_, p_150999_6_);

        if (var7)
        {
            p_150999_6_.addStat(StatList.objectUseStats[Item.getIdFromItem(this.field_151002_e)], 1);
        }
    }

    public boolean func_150998_b(Block p_150998_1_)
    {
        return this.field_151002_e.func_150897_b(p_150998_1_);
    }

    public boolean interactWithEntity(EntityPlayer par1EntityPlayer, EntityLivingBase par2EntityLivingBase)
    {
        return this.field_151002_e.itemInteractionForEntity(this, par1EntityPlayer, par2EntityLivingBase);
    }

    /**
     * Returns a new stack with the same properties.
     */
    public ItemStack copy()
    {
        ItemStack var1 = new ItemStack(this.field_151002_e, this.stackSize, this.itemDamage);

        if (this.stackTagCompound != null)
        {
            var1.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }

        return var1;
    }

    public static boolean areItemStackTagsEqual(ItemStack par0ItemStack, ItemStack par1ItemStack)
    {
        return par0ItemStack == null && par1ItemStack == null ? true : (par0ItemStack != null && par1ItemStack != null ? (par0ItemStack.stackTagCompound == null && par1ItemStack.stackTagCompound != null ? false : par0ItemStack.stackTagCompound == null || par0ItemStack.stackTagCompound.equals(par1ItemStack.stackTagCompound)) : false);
    }

    /**
     * compares ItemStack argument1 with ItemStack argument2; returns true if both ItemStacks are equal
     */
    public static boolean areItemStacksEqual(ItemStack par0ItemStack, ItemStack par1ItemStack)
    {
        return par0ItemStack == null && par1ItemStack == null ? true : (par0ItemStack != null && par1ItemStack != null ? par0ItemStack.isItemStackEqual(par1ItemStack) : false);
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if both ItemStacks are equal
     */
    private boolean isItemStackEqual(ItemStack par1ItemStack)
    {
        return this.stackSize != par1ItemStack.stackSize ? false : (this.field_151002_e != par1ItemStack.field_151002_e ? false : (this.itemDamage != par1ItemStack.itemDamage ? false : (this.stackTagCompound == null && par1ItemStack.stackTagCompound != null ? false : this.stackTagCompound == null || this.stackTagCompound.equals(par1ItemStack.stackTagCompound))));
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if the Items contained in both ItemStacks are
     * equal
     */
    public boolean isItemEqual(ItemStack par1ItemStack)
    {
        return this.field_151002_e == par1ItemStack.field_151002_e && this.itemDamage == par1ItemStack.itemDamage;
    }

    public String getUnlocalizedName()
    {
        return this.field_151002_e.getUnlocalizedName(this);
    }

    /**
     * Creates a copy of a ItemStack, a null parameters will return a null.
     */
    public static ItemStack copyItemStack(ItemStack par0ItemStack)
    {
        return par0ItemStack == null ? null : par0ItemStack.copy();
    }

    public String toString()
    {
        return this.stackSize + "x" + this.field_151002_e.getUnlocalizedName() + "@" + this.itemDamage;
    }

    /**
     * Called each tick as long the ItemStack in on player inventory. Used to progress the pickup animation and update
     * maps.
     */
    public void updateAnimation(World par1World, Entity par2Entity, int par3, boolean par4)
    {
        if (this.animationsToGo > 0)
        {
            --this.animationsToGo;
        }

        this.field_151002_e.onUpdate(this, par1World, par2Entity, par3, par4);
    }

    public void onCrafting(World par1World, EntityPlayer par2EntityPlayer, int par3)
    {
        par2EntityPlayer.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.field_151002_e)], par3);
        this.field_151002_e.onCreated(this, par1World, par2EntityPlayer);
    }

    public int getMaxItemUseDuration()
    {
        return this.getItem().getMaxItemUseDuration(this);
    }

    public EnumAction getItemUseAction()
    {
        return this.getItem().getItemUseAction(this);
    }

    /**
     * Called when the player releases the use item button. Args: world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(World par1World, EntityPlayer par2EntityPlayer, int par3)
    {
        this.getItem().onPlayerStoppedUsing(this, par1World, par2EntityPlayer, par3);
    }

    /**
     * Returns true if the ItemStack has an NBTTagCompound. Currently used to store enchantments.
     */
    public boolean hasTagCompound()
    {
        return this.stackTagCompound != null;
    }

    /**
     * Returns the NBTTagCompound of the ItemStack.
     */
    public NBTTagCompound getTagCompound()
    {
        return this.stackTagCompound;
    }

    public NBTTagList getEnchantmentTagList()
    {
        return this.stackTagCompound == null ? null : this.stackTagCompound.getTagList("ench", 10);
    }

    /**
     * Assigns a NBTTagCompound to the ItemStack, minecraft validates that only non-stackable items can have it.
     */
    public void setTagCompound(NBTTagCompound par1NBTTagCompound)
    {
        this.stackTagCompound = par1NBTTagCompound;
    }

    /**
     * returns the display name of the itemstack
     */
    public String getDisplayName()
    {
        String var1 = this.getItem().getItemStackDisplayName(this);

        if (this.stackTagCompound != null && this.stackTagCompound.func_150297_b("display", 10))
        {
            NBTTagCompound var2 = this.stackTagCompound.getCompoundTag("display");

            if (var2.func_150297_b("Name", 8))
            {
                var1 = var2.getString("Name");
            }
        }

        return var1;
    }

    public ItemStack setStackDisplayName(String p_151001_1_)
    {
        if (this.stackTagCompound == null)
        {
            this.stackTagCompound = new NBTTagCompound();
        }

        if (!this.stackTagCompound.func_150297_b("display", 10))
        {
            this.stackTagCompound.setTag("display", new NBTTagCompound());
        }

        this.stackTagCompound.getCompoundTag("display").setString("Name", p_151001_1_);
        return this;
    }

    public void func_135074_t()
    {
        if (this.stackTagCompound != null)
        {
            if (this.stackTagCompound.func_150297_b("display", 10))
            {
                NBTTagCompound var1 = this.stackTagCompound.getCompoundTag("display");
                var1.removeTag("Name");

                if (var1.hasNoTags())
                {
                    this.stackTagCompound.removeTag("display");

                    if (this.stackTagCompound.hasNoTags())
                    {
                        this.setTagCompound((NBTTagCompound)null);
                    }
                }
            }
        }
    }

    /**
     * Returns true if the itemstack has a display name
     */
    public boolean hasDisplayName()
    {
        return this.stackTagCompound == null ? false : (!this.stackTagCompound.func_150297_b("display", 10) ? false : this.stackTagCompound.getCompoundTag("display").func_150297_b("Name", 8));
    }

    /**
     * Return a list of strings containing information about the item
     */
    public List getTooltip(EntityPlayer par1EntityPlayer, boolean par2)
    {
        ArrayList var3 = new ArrayList();
        String var4 = this.getDisplayName();

        if (this.hasDisplayName())
        {
            var4 = EnumChatFormatting.ITALIC + var4 + EnumChatFormatting.RESET;
        }

        int var6;

        if (par2)
        {
            String var5 = "";

            if (var4.length() > 0)
            {
                var4 = var4 + " (";
                var5 = ")";
            }

            var6 = Item.getIdFromItem(this.field_151002_e);

            if (this.getHasSubtypes())
            {
                var4 = var4 + String.format("#%04d/%d%s", new Object[] {Integer.valueOf(var6), Integer.valueOf(this.itemDamage), var5});
            }
            else
            {
                var4 = var4 + String.format("#%04d%s", new Object[] {Integer.valueOf(var6), var5});
            }
        }
        else if (!this.hasDisplayName() && this.field_151002_e == Items.filled_map)
        {
            var4 = var4 + " #" + this.itemDamage;
        }

        var3.add(var4);
        this.field_151002_e.addInformation(this, par1EntityPlayer, var3, par2);

        if (this.hasTagCompound())
        {
            NBTTagList var14 = this.getEnchantmentTagList();

            if (var14 != null)
            {
                for (var6 = 0; var6 < var14.tagCount(); ++var6)
                {
                    short var7 = var14.getCompoundTagAt(var6).getShort("id");
                    short var8 = var14.getCompoundTagAt(var6).getShort("lvl");

                    if (Enchantment.enchantmentsList[var7] != null)
                    {
                        var3.add(Enchantment.enchantmentsList[var7].getTranslatedName(var8));
                    }
                }
            }

            if (this.stackTagCompound.func_150297_b("display", 10))
            {
                NBTTagCompound var17 = this.stackTagCompound.getCompoundTag("display");

                if (var17.func_150297_b("color", 3))
                {
                    if (par2)
                    {
                        var3.add("Color: #" + Integer.toHexString(var17.getInteger("color")).toUpperCase());
                    }
                    else
                    {
                        var3.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
                    }
                }

                if (var17.func_150299_b("Lore") == 9)
                {
                    NBTTagList var15 = var17.getTagList("Lore", 8);

                    if (var15.tagCount() > 0)
                    {
                        for (int var19 = 0; var19 < var15.tagCount(); ++var19)
                        {
                            var3.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + var15.getStringTagAt(var19));
                        }
                    }
                }
            }
        }

        Multimap var13 = this.getAttributeModifiers();

        if (!var13.isEmpty())
        {
            var3.add("");
            Iterator var16 = var13.entries().iterator();

            while (var16.hasNext())
            {
                Entry var18 = (Entry)var16.next();
                AttributeModifier var20 = (AttributeModifier)var18.getValue();
                double var9 = var20.getAmount();
                double var11;

                if (var20.getOperation() != 1 && var20.getOperation() != 2)
                {
                    var11 = var20.getAmount();
                }
                else
                {
                    var11 = var20.getAmount() * 100.0D;
                }

                if (var9 > 0.0D)
                {
                    var3.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + var20.getOperation(), new Object[] {field_111284_a.format(var11), StatCollector.translateToLocal("attribute.name." + (String)var18.getKey())}));
                }
                else if (var9 < 0.0D)
                {
                    var11 *= -1.0D;
                    var3.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + var20.getOperation(), new Object[] {field_111284_a.format(var11), StatCollector.translateToLocal("attribute.name." + (String)var18.getKey())}));
                }
            }
        }

        if (this.hasTagCompound() && this.getTagCompound().getBoolean("Unbreakable"))
        {
            var3.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
        }

        if (par2 && this.isItemDamaged())
        {
            var3.add("Durability: " + (this.getMaxDamage() - this.getItemDamageForDisplay()) + " / " + this.getMaxDamage());
        }

        return var3;
    }

    public boolean hasEffect()
    {
        return this.getItem().hasEffect(this);
    }

    public EnumRarity getRarity()
    {
        return this.getItem().getRarity(this);
    }

    /**
     * True if it is a tool and has no enchantments to begin with
     */
    public boolean isItemEnchantable()
    {
        return !this.getItem().isItemTool(this) ? false : !this.isItemEnchanted();
    }

    /**
     * Adds an enchantment with a desired level on the ItemStack.
     */
    public void addEnchantment(Enchantment par1Enchantment, int par2)
    {
        if (this.stackTagCompound == null)
        {
            this.setTagCompound(new NBTTagCompound());
        }

        if (!this.stackTagCompound.func_150297_b("ench", 9))
        {
            this.stackTagCompound.setTag("ench", new NBTTagList());
        }

        NBTTagList var3 = this.stackTagCompound.getTagList("ench", 10);
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setShort("id", (short)par1Enchantment.effectId);
        var4.setShort("lvl", (short)((byte)par2));
        var3.appendTag(var4);
    }

    /**
     * True if the item has enchantment data
     */
    public boolean isItemEnchanted()
    {
        return this.stackTagCompound != null && this.stackTagCompound.func_150297_b("ench", 9);
    }

    public void setTagInfo(String par1Str, NBTBase par2NBTBase)
    {
        if (this.stackTagCompound == null)
        {
            this.setTagCompound(new NBTTagCompound());
        }

        this.stackTagCompound.setTag(par1Str, par2NBTBase);
    }

    public boolean canEditBlocks()
    {
        return this.getItem().canItemEditBlocks();
    }

    /**
     * Return whether this stack is on an item frame.
     */
    public boolean isOnItemFrame()
    {
        return this.itemFrame != null;
    }

    /**
     * Set the item frame this stack is on.
     */
    public void setItemFrame(EntityItemFrame par1EntityItemFrame)
    {
        this.itemFrame = par1EntityItemFrame;
    }

    /**
     * Return the item frame this stack is on. Returns null if not on an item frame.
     */
    public EntityItemFrame getItemFrame()
    {
        return this.itemFrame;
    }

    /**
     * Get this stack's repair cost, or 0 if no repair cost is defined.
     */
    public int getRepairCost()
    {
        return this.hasTagCompound() && this.stackTagCompound.func_150297_b("RepairCost", 3) ? this.stackTagCompound.getInteger("RepairCost") : 0;
    }

    /**
     * Set this stack's repair cost.
     */
    public void setRepairCost(int par1)
    {
        if (!this.hasTagCompound())
        {
            this.stackTagCompound = new NBTTagCompound();
        }

        this.stackTagCompound.setInteger("RepairCost", par1);
    }

    /**
     * Gets the attribute modifiers for this ItemStack.\nWill check for an NBT tag list containing modifiers for the
     * stack.
     */
    public Multimap getAttributeModifiers()
    {
        Object var1;

        if (this.hasTagCompound() && this.stackTagCompound.func_150297_b("AttributeModifiers", 9))
        {
            var1 = HashMultimap.create();
            NBTTagList var2 = this.stackTagCompound.getTagList("AttributeModifiers", 10);

            for (int var3 = 0; var3 < var2.tagCount(); ++var3)
            {
                NBTTagCompound var4 = var2.getCompoundTagAt(var3);
                AttributeModifier var5 = SharedMonsterAttributes.readAttributeModifierFromNBT(var4);

                if (var5.getID().getLeastSignificantBits() != 0L && var5.getID().getMostSignificantBits() != 0L)
                {
                    ((Multimap)var1).put(var4.getString("AttributeName"), var5);
                }
            }
        }
        else
        {
            var1 = this.getItem().getItemAttributeModifiers();
        }

        return (Multimap)var1;
    }

    public void func_150996_a(Item p_150996_1_)
    {
        this.field_151002_e = p_150996_1_;
    }

    public IChatComponent func_151000_E()
    {
        IChatComponent var1 = (new ChatComponentText("[")).appendText(this.getDisplayName()).appendText("]");

        if (this.field_151002_e != null)
        {
            NBTTagCompound var2 = new NBTTagCompound();
            this.writeToNBT(var2);
            var1.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(var2.toString())));
            var1.getChatStyle().setColor(this.getRarity().rarityColor);
        }

        return var1;
    }
}
