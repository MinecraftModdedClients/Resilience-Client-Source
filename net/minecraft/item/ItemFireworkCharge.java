package net.minecraft.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemFireworkCharge extends Item
{
    private IIcon field_150904_a;
    private static final String __OBFID = "CL_00000030";

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 > 0 ? this.field_150904_a : super.getIconFromDamageForRenderPass(par1, par2);
    }

    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        if (par2 != 1)
        {
            return super.getColorFromItemStack(par1ItemStack, par2);
        }
        else
        {
            NBTBase var3 = func_150903_a(par1ItemStack, "Colors");

            if (var3 != null && var3 instanceof NBTTagIntArray)
            {
                NBTTagIntArray var4 = (NBTTagIntArray)var3;
                int[] var5 = var4.func_150302_c();

                if (var5.length == 1)
                {
                    return var5[0];
                }
                else
                {
                    int var6 = 0;
                    int var7 = 0;
                    int var8 = 0;
                    int[] var9 = var5;
                    int var10 = var5.length;

                    for (int var11 = 0; var11 < var10; ++var11)
                    {
                        int var12 = var9[var11];
                        var6 += (var12 & 16711680) >> 16;
                        var7 += (var12 & 65280) >> 8;
                        var8 += (var12 & 255) >> 0;
                    }

                    var6 /= var5.length;
                    var7 /= var5.length;
                    var8 /= var5.length;
                    return var6 << 16 | var7 << 8 | var8;
                }
            }
            else
            {
                return 9079434;
            }
        }
    }

    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    public static NBTBase func_150903_a(ItemStack p_150903_0_, String p_150903_1_)
    {
        if (p_150903_0_.hasTagCompound())
        {
            NBTTagCompound var2 = p_150903_0_.getTagCompound().getCompoundTag("Explosion");

            if (var2 != null)
            {
                return var2.getTag(p_150903_1_);
            }
        }

        return null;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (par1ItemStack.hasTagCompound())
        {
            NBTTagCompound var5 = par1ItemStack.getTagCompound().getCompoundTag("Explosion");

            if (var5 != null)
            {
                func_150902_a(var5, par3List);
            }
        }
    }

    public static void func_150902_a(NBTTagCompound p_150902_0_, List p_150902_1_)
    {
        byte var2 = p_150902_0_.getByte("Type");

        if (var2 >= 0 && var2 <= 4)
        {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type." + var2).trim());
        }
        else
        {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
        }

        int[] var3 = p_150902_0_.getIntArray("Colors");
        int var8;
        int var9;

        if (var3.length > 0)
        {
            boolean var4 = true;
            String var5 = "";
            int[] var6 = var3;
            int var7 = var3.length;

            for (var8 = 0; var8 < var7; ++var8)
            {
                var9 = var6[var8];

                if (!var4)
                {
                    var5 = var5 + ", ";
                }

                var4 = false;
                boolean var10 = false;

                for (int var11 = 0; var11 < 16; ++var11)
                {
                    if (var9 == ItemDye.field_150922_c[var11])
                    {
                        var10 = true;
                        var5 = var5 + StatCollector.translateToLocal("item.fireworksCharge." + ItemDye.field_150923_a[var11]);
                        break;
                    }
                }

                if (!var10)
                {
                    var5 = var5 + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
            }

            p_150902_1_.add(var5);
        }

        int[] var13 = p_150902_0_.getIntArray("FadeColors");
        boolean var16;

        if (var13.length > 0)
        {
            var16 = true;
            String var14 = StatCollector.translateToLocal("item.fireworksCharge.fadeTo") + " ";
            int[] var15 = var13;
            var8 = var13.length;

            for (var9 = 0; var9 < var8; ++var9)
            {
                int var18 = var15[var9];

                if (!var16)
                {
                    var14 = var14 + ", ";
                }

                var16 = false;
                boolean var19 = false;

                for (int var12 = 0; var12 < 16; ++var12)
                {
                    if (var18 == ItemDye.field_150922_c[var12])
                    {
                        var19 = true;
                        var14 = var14 + StatCollector.translateToLocal("item.fireworksCharge." + ItemDye.field_150923_a[var12]);
                        break;
                    }
                }

                if (!var19)
                {
                    var14 = var14 + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
            }

            p_150902_1_.add(var14);
        }

        var16 = p_150902_0_.getBoolean("Trail");

        if (var16)
        {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
        }

        boolean var17 = p_150902_0_.getBoolean("Flicker");

        if (var17)
        {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
        }
    }

    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.field_150904_a = par1IconRegister.registerIcon(this.getIconString() + "_overlay");
    }
}
