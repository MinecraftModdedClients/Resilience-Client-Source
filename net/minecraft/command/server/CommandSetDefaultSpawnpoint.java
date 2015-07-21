package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChunkCoordinates;

public class CommandSetDefaultSpawnpoint extends CommandBase
{
    private static final String __OBFID = "CL_00000973";

    public String getCommandName()
    {
        return "setworldspawn";
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
        return "commands.setworldspawn.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 3)
        {
            if (par1ICommandSender.getEntityWorld() == null)
            {
                throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
            }

            byte var3 = 0;
            int var8 = var3 + 1;
            int var4 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[var3], -30000000, 30000000);
            int var5 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[var8++], 0, 256);
            int var6 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[var8++], -30000000, 30000000);
            par1ICommandSender.getEntityWorld().setSpawnLocation(var4, var5, var6);
            notifyAdmins(par1ICommandSender, "commands.setworldspawn.success", new Object[] {Integer.valueOf(var4), Integer.valueOf(var5), Integer.valueOf(var6)});
        }
        else
        {
            if (par2ArrayOfStr.length != 0)
            {
                throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
            }

            ChunkCoordinates var9 = getCommandSenderAsPlayer(par1ICommandSender).getPlayerCoordinates();
            par1ICommandSender.getEntityWorld().setSpawnLocation(var9.posX, var9.posY, var9.posZ);
            notifyAdmins(par1ICommandSender, "commands.setworldspawn.success", new Object[] {Integer.valueOf(var9.posX), Integer.valueOf(var9.posY), Integer.valueOf(var9.posZ)});
        }
    }
}
