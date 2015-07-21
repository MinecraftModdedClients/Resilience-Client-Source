package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings;

public class CommandPublishLocalServer extends CommandBase
{
    private static final String __OBFID = "CL_00000799";

    public String getCommandName()
    {
        return "publish";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.publish.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        String var3 = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, false);

        if (var3 != null)
        {
            notifyAdmins(par1ICommandSender, "commands.publish.started", new Object[] {var3});
        }
        else
        {
            notifyAdmins(par1ICommandSender, "commands.publish.failed", new Object[0]);
        }
    }
}
