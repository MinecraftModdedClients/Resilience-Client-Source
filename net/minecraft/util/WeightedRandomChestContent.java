package net.minecraft.util;

import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;

public class WeightedRandomChestContent extends WeightedRandom.Item
{
    /** The Item/Block ID to generate in the Chest. */
    private ItemStack theItemId;

    /** The minimum chance of item generating. */
    private int theMinimumChanceToGenerateItem;

    /** The maximum chance of item generating. */
    private int theMaximumChanceToGenerateItem;
    private static final String __OBFID = "CL_00001505";

    public WeightedRandomChestContent(Item p_i45311_1_, int p_i45311_2_, int p_i45311_3_, int p_i45311_4_, int p_i45311_5_)
    {
        super(p_i45311_5_);
        this.theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
        this.theMinimumChanceToGenerateItem = p_i45311_3_;
        this.theMaximumChanceToGenerateItem = p_i45311_4_;
    }

    public WeightedRandomChestContent(ItemStack par1ItemStack, int par2, int par3, int par4)
    {
        super(par4);
        this.theItemId = par1ItemStack;
        this.theMinimumChanceToGenerateItem = par2;
        this.theMaximumChanceToGenerateItem = par3;
    }

    /**
     * Generates the Chest contents.
     */
    public static void generateChestContents(Random par0Random, WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, IInventory par2IInventory, int par3)
    {
        for (int var4 = 0; var4 < par3; ++var4)
        {
            WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(par0Random, par1ArrayOfWeightedRandomChestContent);
            int var6 = var5.theMinimumChanceToGenerateItem + par0Random.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);

            if (var5.theItemId.getMaxStackSize() >= var6)
            {
                ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                par2IInventory.setInventorySlotContents(par0Random.nextInt(par2IInventory.getSizeInventory()), var7);
            }
            else
            {
                for (int var9 = 0; var9 < var6; ++var9)
                {
                    ItemStack var8 = var5.theItemId.copy();
                    var8.stackSize = 1;
                    par2IInventory.setInventorySlotContents(par0Random.nextInt(par2IInventory.getSizeInventory()), var8);
                }
            }
        }
    }

    public static void func_150706_a(Random p_150706_0_, WeightedRandomChestContent[] p_150706_1_, TileEntityDispenser p_150706_2_, int p_150706_3_)
    {
        for (int var4 = 0; var4 < p_150706_3_; ++var4)
        {
            WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(p_150706_0_, p_150706_1_);
            int var6 = var5.theMinimumChanceToGenerateItem + p_150706_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);

            if (var5.theItemId.getMaxStackSize() >= var6)
            {
                ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                p_150706_2_.setInventorySlotContents(p_150706_0_.nextInt(p_150706_2_.getSizeInventory()), var7);
            }
            else
            {
                for (int var9 = 0; var9 < var6; ++var9)
                {
                    ItemStack var8 = var5.theItemId.copy();
                    var8.stackSize = 1;
                    p_150706_2_.setInventorySlotContents(p_150706_0_.nextInt(p_150706_2_.getSizeInventory()), var8);
                }
            }
        }
    }

    public static WeightedRandomChestContent[] func_92080_a(WeightedRandomChestContent[] par0ArrayOfWeightedRandomChestContent, WeightedRandomChestContent ... par1ArrayOfWeightedRandomChestContent)
    {
        WeightedRandomChestContent[] var2 = new WeightedRandomChestContent[par0ArrayOfWeightedRandomChestContent.length + par1ArrayOfWeightedRandomChestContent.length];
        int var3 = 0;

        for (int var4 = 0; var4 < par0ArrayOfWeightedRandomChestContent.length; ++var4)
        {
            var2[var3++] = par0ArrayOfWeightedRandomChestContent[var4];
        }

        WeightedRandomChestContent[] var8 = par1ArrayOfWeightedRandomChestContent;
        int var5 = par1ArrayOfWeightedRandomChestContent.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            WeightedRandomChestContent var7 = var8[var6];
            var2[var3++] = var7;
        }

        return var2;
    }
}
