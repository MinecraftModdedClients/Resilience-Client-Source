package net.minecraft.dispenser;

import net.minecraft.item.ItemStack;

public interface IBehaviorDispenseItem
{
    IBehaviorDispenseItem itemDispenseBehaviorProvider = new IBehaviorDispenseItem()
    {
        private static final String __OBFID = "CL_00001200";
        public ItemStack dispense(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
        {
            return par2ItemStack;
        }
    };

    /**
     * Dispenses the specified ItemStack from a dispenser.
     */
    ItemStack dispense(IBlockSource var1, ItemStack var2);
}
