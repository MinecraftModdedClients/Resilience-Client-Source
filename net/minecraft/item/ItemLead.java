package net.minecraft.item;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemLead extends Item
{
    private static final String __OBFID = "CL_00000045";

    public ItemLead()
    {
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        Block var11 = par3World.getBlock(par4, par5, par6);

        if (var11.getRenderType() == 11)
        {
            if (par3World.isClient)
            {
                return true;
            }
            else
            {
                func_150909_a(par2EntityPlayer, par3World, par4, par5, par6);
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public static boolean func_150909_a(EntityPlayer p_150909_0_, World p_150909_1_, int p_150909_2_, int p_150909_3_, int p_150909_4_)
    {
        EntityLeashKnot var5 = EntityLeashKnot.getKnotForBlock(p_150909_1_, p_150909_2_, p_150909_3_, p_150909_4_);
        boolean var6 = false;
        double var7 = 7.0D;
        List var9 = p_150909_1_.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB((double)p_150909_2_ - var7, (double)p_150909_3_ - var7, (double)p_150909_4_ - var7, (double)p_150909_2_ + var7, (double)p_150909_3_ + var7, (double)p_150909_4_ + var7));

        if (var9 != null)
        {
            Iterator var10 = var9.iterator();

            while (var10.hasNext())
            {
                EntityLiving var11 = (EntityLiving)var10.next();

                if (var11.getLeashed() && var11.getLeashedToEntity() == p_150909_0_)
                {
                    if (var5 == null)
                    {
                        var5 = EntityLeashKnot.func_110129_a(p_150909_1_, p_150909_2_, p_150909_3_, p_150909_4_);
                    }

                    var11.setLeashedToEntity(var5, true);
                    var6 = true;
                }
            }
        }

        return var6;
    }
}
