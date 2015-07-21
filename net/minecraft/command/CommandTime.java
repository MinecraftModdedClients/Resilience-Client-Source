package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandTime extends CommandBase
{
    private static final String __OBFID = "CL_00001183";

    public String getCommandName()
    {
        return "time";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.time.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 1)
        {
            int var3;

            if (par2ArrayOfStr[0].equals("set"))
            {
                if (par2ArrayOfStr[1].equals("day"))
                {
                    var3 = 1000;
                }
                else if (par2ArrayOfStr[1].equals("night"))
                {
                    var3 = 13000;
                }
                else
                {
                    var3 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
                }

                this.setTime(par1ICommandSender, var3);
                notifyAdmins(par1ICommandSender, "commands.time.set", new Object[] {Integer.valueOf(var3)});
                return;
            }

            if (par2ArrayOfStr[0].equals("add"))
            {
                var3 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
                this.addTime(par1ICommandSender, var3);
                notifyAdmins(par1ICommandSender, "commands.time.added", new Object[] {Integer.valueOf(var3)});
                return;
            }
        }

        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"set", "add"}): (par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set") ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"day", "night"}): null);
    }

    /**
     * Set the time in the server object.
     */
    protected void setTime(ICommandSender par1ICommandSender, int par2)
    {
        for (int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; ++var3)
        {
            MinecraftServer.getServer().worldServers[var3].setWorldTime((long)par2);
        }
    }

    /**
     * Adds (or removes) time in the server object.
     */
    protected void addTime(ICommandSender par1ICommandSender, int par2)
    {
        for (int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; ++var3)
        {
            WorldServer var4 = MinecraftServer.getServer().worldServers[var3];
            var4.setWorldTime(var4.getWorldTime() + (long)par2);
        }
    }
}
