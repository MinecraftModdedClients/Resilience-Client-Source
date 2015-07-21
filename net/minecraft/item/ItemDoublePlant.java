package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerGrass;

public class ItemDoublePlant extends ItemMultiTexture
{
    private static final String __OBFID = "CL_00000021";

    public ItemDoublePlant(Block p_i45335_1_, BlockDoublePlant p_i45335_2_, String[] p_i45335_3_)
    {
        super(p_i45335_1_, p_i45335_2_, p_i45335_3_);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int par1)
    {
        return BlockDoublePlant.func_149890_d(par1) == 0 ? ((BlockDoublePlant)this.field_150941_b).field_149891_b[0] : ((BlockDoublePlant)this.field_150941_b).func_149888_a(true, par1);
    }

    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        int var3 = BlockDoublePlant.func_149890_d(par1ItemStack.getItemDamage());
        return var3 != 2 && var3 != 3 ? super.getColorFromItemStack(par1ItemStack, par2) : ColorizerGrass.getGrassColor(0.5D, 1.0D);
    }
}
