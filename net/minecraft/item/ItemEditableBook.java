package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class ItemEditableBook extends Item
{
    private static final String __OBFID = "CL_00000077";

    public ItemEditableBook()
    {
        this.setMaxStackSize(1);
    }

    public static boolean validBookTagContents(NBTTagCompound par0NBTTagCompound)
    {
        if (!ItemWritableBook.func_150930_a(par0NBTTagCompound))
        {
            return false;
        }
        else if (!par0NBTTagCompound.func_150297_b("title", 8))
        {
            return false;
        }
        else
        {
            String var1 = par0NBTTagCompound.getString("title");
            return var1 != null && var1.length() <= 16 ? par0NBTTagCompound.func_150297_b("author", 8) : false;
        }
    }

    public String getItemStackDisplayName(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound())
        {
            NBTTagCompound var2 = par1ItemStack.getTagCompound();
            String var3 = var2.getString("title");

            if (!StringUtils.isNullOrEmpty(var3))
            {
                return var3;
            }
        }

        return super.getItemStackDisplayName(par1ItemStack);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (par1ItemStack.hasTagCompound())
        {
            NBTTagCompound var5 = par1ItemStack.getTagCompound();
            String var6 = var5.getString("author");

            if (!StringUtils.isNullOrEmpty(var6))
            {
                par3List.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("book.byAuthor", new Object[] {var6}));
            }
        }
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

    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
}
