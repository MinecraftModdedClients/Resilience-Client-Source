package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;

public class CommandClearInventory extends CommandBase
{
    private static final String __OBFID = "CL_00000218";

    public String getCommandName()
    {
        return "clear";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.clear.usage";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        EntityPlayerMP var3 = par2ArrayOfStr.length == 0 ? getCommandSenderAsPlayer(par1ICommandSender) : getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        Item var4 = par2ArrayOfStr.length >= 2 ? getItemByText(par1ICommandSender, par2ArrayOfStr[1]) : null;
        int var5 = par2ArrayOfStr.length >= 3 ? parseIntWithMin(par1ICommandSender, par2ArrayOfStr[2], 0) : -1;

        if (par2ArrayOfStr.length >= 2 && var4 == null)
        {
            throw new CommandException("commands.clear.failure", new Object[] {var3.getCommandSenderName()});
        }
        else
        {
            int var6 = var3.inventory.clearInventory(var4, var5);
            var3.inventoryContainer.detectAndSendChanges();

            if (!var3.capabilities.isCreativeMode)
            {
                var3.updateHeldItem();
            }

            if (var6 == 0)
            {
                throw new CommandException("commands.clear.failure", new Object[] {var3.getCommandSenderName()});
            }
            else
            {
                notifyAdmins(par1ICommandSender, "commands.clear.success", new Object[] {var3.getCommandSenderName(), Integer.valueOf(var6)});
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.func_147209_d()) : (par2ArrayOfStr.length == 2 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Item.itemRegistry.getKeys()) : null);
    }

    protected String[] func_147209_d()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}
