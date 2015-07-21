package net.minecraft.command.server;

import java.util.List;
import java.util.regex.Matcher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandPardonIp extends CommandBase
{
    private static final String __OBFID = "CL_00000720";

    public String getCommandName()
    {
        return "pardon-ip";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 3;
    }

    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive() && super.canCommandSenderUseCommand(par1ICommandSender);
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.unbanip.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 1)
        {
            Matcher var3 = CommandBanIp.field_147211_a.matcher(par2ArrayOfStr[0]);

            if (var3.matches())
            {
                MinecraftServer.getServer().getConfigurationManager().getBannedIPs().remove(par2ArrayOfStr[0]);
                notifyAdmins(par1ICommandSender, "commands.unbanip.success", new Object[] {par2ArrayOfStr[0]});
            }
            else
            {
                throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
            }
        }
        else
        {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().keySet()) : null;
    }
}
