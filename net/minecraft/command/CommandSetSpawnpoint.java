package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;

public class CommandSetSpawnpoint extends CommandBase
{
    private static final String __OBFID = "CL_00001026";

    public String getCommandName()
    {
        return "spawnpoint";
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
        return "commands.spawnpoint.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        EntityPlayerMP var3 = par2ArrayOfStr.length == 0 ? getCommandSenderAsPlayer(par1ICommandSender) : getPlayer(par1ICommandSender, par2ArrayOfStr[0]);

        if (par2ArrayOfStr.length == 4)
        {
            if (var3.worldObj != null)
            {
                byte var4 = 1;
                int var5 = 30000000;
                int var10 = var4 + 1;
                int var6 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[var4], -var5, var5);
                int var7 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[var10++], 0, 256);
                int var8 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[var10++], -var5, var5);
                var3.setSpawnChunk(new ChunkCoordinates(var6, var7, var8), true);
                notifyAdmins(par1ICommandSender, "commands.spawnpoint.success", new Object[] {var3.getCommandSenderName(), Integer.valueOf(var6), Integer.valueOf(var7), Integer.valueOf(var8)});
            }
        }
        else
        {
            if (par2ArrayOfStr.length > 1)
            {
                throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
            }

            ChunkCoordinates var11 = var3.getPlayerCoordinates();
            var3.setSpawnChunk(var11, true);
            notifyAdmins(par1ICommandSender, "commands.spawnpoint.success", new Object[] {var3.getCommandSenderName(), Integer.valueOf(var11.posX), Integer.valueOf(var11.posY), Integer.valueOf(var11.posZ)});
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length != 1 && par2ArrayOfStr.length != 2 ? null : getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}
