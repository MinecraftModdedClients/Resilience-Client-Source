package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

public class ItemColored extends ItemBlock
{
    private final Block field_150944_b;
    private String[] field_150945_c;
    private static final String __OBFID = "CL_00000003";

    public ItemColored(Block p_i45332_1_, boolean p_i45332_2_)
    {
        super(p_i45332_1_);
        this.field_150944_b = p_i45332_1_;

        if (p_i45332_2_)
        {
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }
    }

    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return this.field_150944_b.getRenderColor(par1ItemStack.getItemDamage());
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int par1)
    {
        return this.field_150944_b.getIcon(0, par1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    public ItemColored func_150943_a(String[] p_150943_1_)
    {
        this.field_150945_c = p_150943_1_;
        return this;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        if (this.field_150945_c == null)
        {
            return super.getUnlocalizedName(par1ItemStack);
        }
        else
        {
            int var2 = par1ItemStack.getItemDamage();
            return var2 >= 0 && var2 < this.field_150945_c.length ? super.getUnlocalizedName(par1ItemStack) + "." + this.field_150945_c[var2] : super.getUnlocalizedName(par1ItemStack);
        }
    }
}
