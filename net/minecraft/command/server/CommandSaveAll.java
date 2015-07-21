package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase
{
    private static final String __OBFID = "CL_00000826";

    public String getCommandName()
    {
        return "save-all";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.save.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        MinecraftServer var3 = MinecraftServer.getServer();
        par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.save.start", new Object[0]));

        if (var3.getConfigurationManager() != null)
        {
            var3.getConfigurationManager().saveAllPlayerData();
        }

        try
        {
            int var4;
            WorldServer var5;
            boolean var6;

            for (var4 = 0; var4 < var3.worldServers.length; ++var4)
            {
                if (var3.worldServers[var4] != null)
                {
                    var5 = var3.worldServers[var4];
                    var6 = var5.levelSaving;
                    var5.levelSaving = false;
                    var5.saveAllChunks(true, (IProgressUpdate)null);
                    var5.levelSaving = var6;
                }
            }

            if (par2ArrayOfStr.length > 0 && "flush".equals(par2ArrayOfStr[0]))
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));

                for (var4 = 0; var4 < var3.worldServers.length; ++var4)
                {
                    if (var3.worldServers[var4] != null)
                    {
                        var5 = var3.worldServers[var4];
                        var6 = var5.levelSaving;
                        var5.levelSaving = false;
                        var5.saveChunkData();
                        var5.levelSaving = var6;
                    }
                }

                par1ICommandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (MinecraftException var7)
        {
            notifyAdmins(par1ICommandSender, "commands.save.failed", new Object[] {var7.getMessage()});
            return;
        }

        notifyAdmins(par1ICommandSender, "commands.save.success", new Object[0]);
    }
}
