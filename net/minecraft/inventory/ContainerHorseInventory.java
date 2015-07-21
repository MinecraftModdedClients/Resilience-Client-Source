package net.minecraft.inventory;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventory extends Container
{
    private IInventory field_111243_a;
    private EntityHorse theHorse;
    private static final String __OBFID = "CL_00001751";

    public ContainerHorseInventory(IInventory par1IInventory, final IInventory par2IInventory, final EntityHorse par3EntityHorse)
    {
        this.field_111243_a = par2IInventory;
        this.theHorse = par3EntityHorse;
        byte var4 = 3;
        par2IInventory.openInventory();
        int var5 = (var4 - 4) * 18;
        this.addSlotToContainer(new Slot(par2IInventory, 0, 8, 18)
        {
            private static final String __OBFID = "CL_00001752";
            public boolean isItemValid(ItemStack par1ItemStack)
            {
                return super.isItemValid(par1ItemStack) && par1ItemStack.getItem() == Items.saddle && !this.getHasStack();
            }
        });
        this.addSlotToContainer(new Slot(par2IInventory, 1, 8, 36)
        {
            private static final String __OBFID = "CL_00001753";
            public boolean isItemValid(ItemStack par1ItemStack)
            {
                return super.isItemValid(par1ItemStack) && par3EntityHorse.func_110259_cr() && EntityHorse.func_146085_a(par1ItemStack.getItem());
            }
            public boolean func_111238_b()
            {
                return par3EntityHorse.func_110259_cr();
            }
        });
        int var6;
        int var7;

        if (par3EntityHorse.isChested())
        {
            for (var6 = 0; var6 < var4; ++var6)
            {
                for (var7 = 0; var7 < 5; ++var7)
                {
                    this.addSlotToContainer(new Slot(par2IInventory, 2 + var7 + var6 * 5, 80 + var7 * 18, 18 + var6 * 18));
                }
            }
        }

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (var7 = 0; var7 < 9; ++var7)
            {
                this.addSlotToContainer(new Slot(par1IInventory, var7 + var6 * 9 + 9, 8 + var7 * 18, 102 + var6 * 18 + var5));
            }
        }

        for (var6 = 0; var6 < 9; ++var6)
        {
            this.addSlotToContainer(new Slot(par1IInventory, var6, 8 + var6 * 18, 160 + var5));
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.field_111243_a.isUseableByPlayer(par1EntityPlayer) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity(par1EntityPlayer) < 8.0F;
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

            if (par2 < this.field_111243_a.getSizeInventory())
            {
                if (!this.mergeItemStack(var5, this.field_111243_a.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (this.getSlot(1).isItemValid(var5) && !this.getSlot(1).getHasStack())
            {
                if (!this.mergeItemStack(var5, 1, 2, false))
                {
                    return null;
                }
            }
            else if (this.getSlot(0).isItemValid(var5))
            {
                if (!this.mergeItemStack(var5, 0, 1, false))
                {
                    return null;
                }
            }
            else if (this.field_111243_a.getSizeInventory() <= 2 || !this.mergeItemStack(var5, 2, this.field_111243_a.getSizeInventory(), false))
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
        }

        return var3;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        this.field_111243_a.closeInventory();
    }
}
