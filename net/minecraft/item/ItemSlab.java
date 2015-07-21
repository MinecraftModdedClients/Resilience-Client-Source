package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSlab extends ItemBlock
{
    private final boolean field_150948_b;
    private final BlockSlab field_150949_c;
    private final BlockSlab field_150947_d;
    private static final String __OBFID = "CL_00000071";

    public ItemSlab(Block p_i45355_1_, BlockSlab p_i45355_2_, BlockSlab p_i45355_3_, boolean p_i45355_4_)
    {
        super(p_i45355_1_);
        this.field_150949_c = p_i45355_2_;
        this.field_150947_d = p_i45355_3_;
        this.field_150948_b = p_i45355_4_;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int par1)
    {
        return Block.getBlockFromItem(this).getIcon(2, par1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return this.field_150949_c.func_150002_b(par1ItemStack.getItemDamage());
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (this.field_150948_b)
        {
            return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }
        else if (par1ItemStack.stackSize == 0)
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
            int var12 = par3World.getBlockMetadata(par4, par5, par6);
            int var13 = var12 & 7;
            boolean var14 = (var12 & 8) != 0;

            if ((par7 == 1 && !var14 || par7 == 0 && var14) && var11 == this.field_150949_c && var13 == par1ItemStack.getItemDamage())
            {
                if (par3World.checkNoEntityCollision(this.field_150947_d.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlock(par4, par5, par6, this.field_150947_d, var13, 3))
                {
                    par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), this.field_150947_d.stepSound.func_150496_b(), (this.field_150947_d.stepSound.func_150497_c() + 1.0F) / 2.0F, this.field_150947_d.stepSound.func_150494_d() * 0.8F);
                    --par1ItemStack.stackSize;
                }

                return true;
            }
            else
            {
                return this.func_150946_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7) ? true : super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
            }
        }
    }

    public boolean func_150936_a(World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, EntityPlayer p_150936_6_, ItemStack p_150936_7_)
    {
        int var8 = p_150936_2_;
        int var9 = p_150936_3_;
        int var10 = p_150936_4_;
        Block var11 = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
        int var12 = p_150936_1_.getBlockMetadata(p_150936_2_, p_150936_3_, p_150936_4_);
        int var13 = var12 & 7;
        boolean var14 = (var12 & 8) != 0;

        if ((p_150936_5_ == 1 && !var14 || p_150936_5_ == 0 && var14) && var11 == this.field_150949_c && var13 == p_150936_7_.getItemDamage())
        {
            return true;
        }
        else
        {
            if (p_150936_5_ == 0)
            {
                --p_150936_3_;
            }

            if (p_150936_5_ == 1)
            {
                ++p_150936_3_;
            }

            if (p_150936_5_ == 2)
            {
                --p_150936_4_;
            }

            if (p_150936_5_ == 3)
            {
                ++p_150936_4_;
            }

            if (p_150936_5_ == 4)
            {
                --p_150936_2_;
            }

            if (p_150936_5_ == 5)
            {
                ++p_150936_2_;
            }

            Block var15 = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
            int var16 = p_150936_1_.getBlockMetadata(p_150936_2_, p_150936_3_, p_150936_4_);
            var13 = var16 & 7;
            return var15 == this.field_150949_c && var13 == p_150936_7_.getItemDamage() ? true : super.func_150936_a(p_150936_1_, var8, var9, var10, p_150936_5_, p_150936_6_, p_150936_7_);
        }
    }

    private boolean func_150946_a(ItemStack p_150946_1_, EntityPlayer p_150946_2_, World p_150946_3_, int p_150946_4_, int p_150946_5_, int p_150946_6_, int p_150946_7_)
    {
        if (p_150946_7_ == 0)
        {
            --p_150946_5_;
        }

        if (p_150946_7_ == 1)
        {
            ++p_150946_5_;
        }

        if (p_150946_7_ == 2)
        {
            --p_150946_6_;
        }

        if (p_150946_7_ == 3)
        {
            ++p_150946_6_;
        }

        if (p_150946_7_ == 4)
        {
            --p_150946_4_;
        }

        if (p_150946_7_ == 5)
        {
            ++p_150946_4_;
        }

        Block var8 = p_150946_3_.getBlock(p_150946_4_, p_150946_5_, p_150946_6_);
        int var9 = p_150946_3_.getBlockMetadata(p_150946_4_, p_150946_5_, p_150946_6_);
        int var10 = var9 & 7;

        if (var8 == this.field_150949_c && var10 == p_150946_1_.getItemDamage())
        {
            if (p_150946_3_.checkNoEntityCollision(this.field_150947_d.getCollisionBoundingBoxFromPool(p_150946_3_, p_150946_4_, p_150946_5_, p_150946_6_)) && p_150946_3_.setBlock(p_150946_4_, p_150946_5_, p_150946_6_, this.field_150947_d, var10, 3))
            {
                p_150946_3_.playSoundEffect((double)((float)p_150946_4_ + 0.5F), (double)((float)p_150946_5_ + 0.5F), (double)((float)p_150946_6_ + 0.5F), this.field_150947_d.stepSound.func_150496_b(), (this.field_150947_d.stepSound.func_150497_c() + 1.0F) / 2.0F, this.field_150947_d.stepSound.func_150494_d() * 0.8F);
                --p_150946_1_.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
