package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemWritableBook extends Item
{
    private static final String __OBFID = "CL_00000076";

    public ItemWritableBook()
    {
        this.setMaxStackSize(1);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.displayGUIBook(par1ItemStack);
        return par1ItemStack;
    }

    /**
     * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
     */
    public boolean getShareTag()
    {
        return true;
    }

    public static boolean func_150930_a(NBTTagCompound p_150930_0_)
    {
        if (p_150930_0_ == null)
        {
            return false;
        }
        else if (!p_150930_0_.func_150297_b("pages", 9))
        {
            return false;
        }
        else
        {
            NBTTagList var1 = p_150930_0_.getTagList("pages", 8);

            for (int var2 = 0; var2 < var1.tagCount(); ++var2)
            {
                String var3 = var1.getStringTagAt(var2);

                if (var3 == null)
                {
                    return false;
                }

                if (var3.length() > 256)
                {
                    return false;
                }
            }

            return true;
        }
    }
}
