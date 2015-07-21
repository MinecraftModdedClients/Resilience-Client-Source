package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ItemSnow extends ItemBlockWithMetadata
{
    private static final String __OBFID = "CL_00000068";

    public ItemSnow(Block p_i45354_1_, Block p_i45354_2_)
    {
        super(p_i45354_1_, p_i45354_2_);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par1ItemStack.stackSize == 0)
        {
            return false;
        }
        else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            Block var11 = par3World.getBlock(par4, par5, par6);

            if (var11 == Blocks.snow_layer)
            {
                int var12 = par3World.getBlockMetadata(par4, par5, par6);
                int var13 = var12 & 7;

                if (var13 <= 6 && par3World.checkNoEntityCollision(this.field_150939_a.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlockMetadataWithNotify(par4, par5, par6, var13 + 1 | var12 & -8, 2))
                {
                    par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.func_150497_c() + 1.0F) / 2.0F, this.field_150939_a.stepSound.func_150494_d() * 0.8F);
                    --par1ItemStack.stackSize;
                    return true;
                }
            }

            return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }
    }
}
