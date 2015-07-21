package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemDoor extends Item
{
    private Material doorMaterial;
    private static final String __OBFID = "CL_00000020";

    public ItemDoor(Material p_i45334_1_)
    {
        this.doorMaterial = p_i45334_1_;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else
        {
            ++par5;
            Block var11;

            if (this.doorMaterial == Material.wood)
            {
                var11 = Blocks.wooden_door;
            }
            else
            {
                var11 = Blocks.iron_door;
            }

            if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
            {
                if (!var11.canPlaceBlockAt(par3World, par4, par5, par6))
                {
                    return false;
                }
                else
                {
                    int var12 = MathHelper.floor_double((double)((par2EntityPlayer.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    func_150924_a(par3World, par4, par5, par6, var12, var11);
                    --par1ItemStack.stackSize;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    public static void func_150924_a(World p_150924_0_, int p_150924_1_, int p_150924_2_, int p_150924_3_, int p_150924_4_, Block p_150924_5_)
    {
        byte var6 = 0;
        byte var7 = 0;

        if (p_150924_4_ == 0)
        {
            var7 = 1;
        }

        if (p_150924_4_ == 1)
        {
            var6 = -1;
        }

        if (p_150924_4_ == 2)
        {
            var7 = -1;
        }

        if (p_150924_4_ == 3)
        {
            var6 = 1;
        }

        int var8 = (p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_, p_150924_3_ - var7).isNormalCube() ? 1 : 0) + (p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_ + 1, p_150924_3_ - var7).isNormalCube() ? 1 : 0);
        int var9 = (p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_, p_150924_3_ + var7).isNormalCube() ? 1 : 0) + (p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_ + 1, p_150924_3_ + var7).isNormalCube() ? 1 : 0);
        boolean var10 = p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_, p_150924_3_ - var7) == p_150924_5_ || p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_ + 1, p_150924_3_ - var7) == p_150924_5_;
        boolean var11 = p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_, p_150924_3_ + var7) == p_150924_5_ || p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_ + 1, p_150924_3_ + var7) == p_150924_5_;
        boolean var12 = false;

        if (var10 && !var11)
        {
            var12 = true;
        }
        else if (var9 > var8)
        {
            var12 = true;
        }

        p_150924_0_.setBlock(p_150924_1_, p_150924_2_, p_150924_3_, p_150924_5_, p_150924_4_, 2);
        p_150924_0_.setBlock(p_150924_1_, p_150924_2_ + 1, p_150924_3_, p_150924_5_, 8 | (var12 ? 1 : 0), 2);
        p_150924_0_.notifyBlocksOfNeighborChange(p_150924_1_, p_150924_2_, p_150924_3_, p_150924_5_);
        p_150924_0_.notifyBlocksOfNeighborChange(p_150924_1_, p_150924_2_ + 1, p_150924_3_, p_150924_5_);
    }
}
