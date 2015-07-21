package net.minecraft.client.gui.inventory;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

public class CreativeCrafting implements ICrafting
{
    private final Minecraft field_146109_a;
    private static final String __OBFID = "CL_00000751";

    public CreativeCrafting(Minecraft par1Minecraft)
    {
        this.field_146109_a = par1Minecraft;
    }

    public void sendContainerAndContentsToPlayer(Container par1Container, List par2List) {}

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot. Args: Container, slot number, slot contents
     */
    public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack)
    {
        this.field_146109_a.playerController.sendSlotPacket(par3ItemStack, par2);
    }

    /**
     * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing progress,
     * and enchanting level. Normally the first int identifies which variable to update, and the second contains the new
     * value. Both are truncated to shorts in non-local SMP.
     */
    public void sendProgressBarUpdate(Container par1Container, int par2, int par3) {}
}
