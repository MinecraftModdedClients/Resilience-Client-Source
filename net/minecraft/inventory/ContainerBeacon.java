package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBeacon;

public class ContainerBeacon extends Container
{
    private TileEntityBeacon theBeacon;

    /**
     * This beacon's slot where you put in Emerald, Diamond, Gold or Iron Ingot.
     */
    private final ContainerBeacon.BeaconSlot beaconSlot;
    private int field_82865_g;
    private int field_82867_h;
    private int field_82868_i;
    private static final String __OBFID = "CL_00001735";

    public ContainerBeacon(InventoryPlayer par1InventoryPlayer, TileEntityBeacon par2TileEntityBeacon)
    {
        this.theBeacon = par2TileEntityBeacon;
        this.addSlotToContainer(this.beaconSlot = new ContainerBeacon.BeaconSlot(par2TileEntityBeacon, 0, 136, 110));
        byte var3 = 36;
        short var4 = 137;
        int var5;

        for (var5 = 0; var5 < 3; ++var5)
        {
            for (int var6 = 0; var6 < 9; ++var6)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var6 + var5 * 9 + 9, var3 + var6 * 18, var4 + var5 * 18));
            }
        }

        for (var5 = 0; var5 < 9; ++var5)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var5, var3 + var5 * 18, 58 + var4));
        }

        this.field_82865_g = par2TileEntityBeacon.func_145998_l();
        this.field_82867_h = par2TileEntityBeacon.func_146007_j();
        this.field_82868_i = par2TileEntityBeacon.func_146006_k();
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.field_82865_g);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.field_82867_h);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.field_82868_i);
    }

    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.theBeacon.func_146005_c(par2);
        }

        if (par1 == 1)
        {
            this.theBeacon.func_146001_d(par2);
        }

        if (par1 == 2)
        {
            this.theBeacon.func_146004_e(par2);
        }
    }

    public TileEntityBeacon func_148327_e()
    {
        return this.theBeacon;
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.theBeacon.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 == 0)
            {
                if (!this.mergeItemStack(var5, 1, 37, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            else if (!this.beaconSlot.getHasStack() && this.beaconSlot.isItemValid(var5) && var5.stackSize == 1)
            {
                if (!this.mergeItemStack(var5, 0, 1, false))
                {
                    return null;
                }
            }
            else if (par2 >= 1 && par2 < 28)
            {
                if (!this.mergeItemStack(var5, 28, 37, false))
                {
                    return null;
                }
            }
            else if (par2 >= 28 && par2 < 37)
            {
                if (!this.mergeItemStack(var5, 1, 28, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 1, 37, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }

    class BeaconSlot extends Slot
    {
        private static final String __OBFID = "CL_00001736";

        public BeaconSlot(IInventory par2IInventory, int par3, int par4, int par5)
        {
            super(par2IInventory, par3, par4, par5);
        }

        public boolean isItemValid(ItemStack par1ItemStack)
        {
            return par1ItemStack == null ? false : par1ItemStack.getItem() == Items.emerald || par1ItemStack.getItem() == Items.diamond || par1ItemStack.getItem() == Items.gold_ingot || par1ItemStack.getItem() == Items.iron_ingot;
        }

        public int getSlotStackLimit()
        {
            return 1;
        }
    }
}
