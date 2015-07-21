package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandTeleport extends CommandBase
{
    private static final String __OBFID = "CL_00001180";

    public String getCommandName()
    {
        return "tp";
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
        return "commands.tp.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 1)
        {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
        else
        {
            EntityPlayerMP var3;

            if (par2ArrayOfStr.length != 2 && par2ArrayOfStr.length != 4)
            {
                var3 = getCommandSenderAsPlayer(par1ICommandSender);
            }
            else
            {
                var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);

                if (var3 == null)
                {
                    throw new PlayerNotFoundException();
                }
            }

            if (par2ArrayOfStr.length != 3 && par2ArrayOfStr.length != 4)
            {
                if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2)
                {
                    EntityPlayerMP var11 = getPlayer(par1ICommandSender, par2ArrayOfStr[par2ArrayOfStr.length - 1]);

                    if (var11 == null)
                    {
                        throw new PlayerNotFoundException();
                    }

                    if (var11.worldObj != var3.worldObj)
                    {
                        notifyAdmins(par1ICommandSender, "commands.tp.notSameDimension", new Object[0]);
                        return;
                    }

                    var3.mountEntity((Entity)null);
                    var3.playerNetServerHandler.setPlayerLocation(var11.posX, var11.posY, var11.posZ, var11.rotationYaw, var11.rotationPitch);
                    notifyAdmins(par1ICommandSender, "commands.tp.success", new Object[] {var3.getCommandSenderName(), var11.getCommandSenderName()});
                }
            }
            else if (var3.worldObj != null)
            {
                int var4 = par2ArrayOfStr.length - 3;
                double var5 = func_110666_a(par1ICommandSender, var3.posX, par2ArrayOfStr[var4++]);
                double var7 = func_110665_a(par1ICommandSender, var3.posY, par2ArrayOfStr[var4++], 0, 0);
                double var9 = func_110666_a(par1ICommandSender, var3.posZ, par2ArrayOfStr[var4++]);
                var3.mountEntity((Entity)null);
                var3.setPositionAndUpdate(var5, var7, var9);
                notifyAdmins(par1ICommandSender, "commands.tp.success.coordinates", new Object[] {var3.getCommandSenderName(), Double.valueOf(var5), Double.valueOf(var7), Double.valueOf(var9)});
            }
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
