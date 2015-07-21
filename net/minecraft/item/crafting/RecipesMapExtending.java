package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class RecipesMapExtending extends ShapedRecipes
{
    private static final String __OBFID = "CL_00000088";

    public RecipesMapExtending()
    {
        super(3, 3, new ItemStack[] {new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.filled_map, 0, 32767), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper)}, new ItemStack(Items.map, 0, 0));
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        if (!super.matches(par1InventoryCrafting, par2World))
        {
            return false;
        }
        else
        {
            ItemStack var3 = null;

            for (int var4 = 0; var4 < par1InventoryCrafting.getSizeInventory() && var3 == null; ++var4)
            {
                ItemStack var5 = par1InventoryCrafting.getStackInSlot(var4);

                if (var5 != null && var5.getItem() == Items.filled_map)
                {
                    var3 = var5;
                }
            }

            if (var3 == null)
            {
                return false;
            }
            else
            {
                MapData var6 = Items.filled_map.getMapData(var3, par2World);
                return var6 == null ? false : var6.scale < 4;
            }
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
        ItemStack var2 = null;

        for (int var3 = 0; var3 < par1InventoryCrafting.getSizeInventory() && var2 == null; ++var3)
        {
            ItemStack var4 = par1InventoryCrafting.getStackInSlot(var3);

            if (var4 != null && var4.getItem() == Items.filled_map)
            {
                var2 = var4;
            }
        }

        var2 = var2.copy();
        var2.stackSize = 1;

        if (var2.getTagCompound() == null)
        {
            var2.setTagCompound(new NBTTagCompound());
        }

        var2.getTagCompound().setBoolean("map_is_scaling", true);
        return var2;
    }
}
