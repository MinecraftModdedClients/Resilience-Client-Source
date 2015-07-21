package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S29PacketSoundEffect;

public class CommandPlaySound extends CommandBase
{
    private static final String __OBFID = "CL_00000774";

    public String getCommandName()
    {
        return "playsound";
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
        return "commands.playsound.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 2)
        {
            throw new WrongUsageException(this.getCommandUsage(par1ICommandSender), new Object[0]);
        }
        else
        {
            byte var3 = 0;
            int var36 = var3 + 1;
            String var4 = par2ArrayOfStr[var3];
            EntityPlayerMP var5 = getPlayer(par1ICommandSender, par2ArrayOfStr[var36++]);
            double var6 = (double)var5.getPlayerCoordinates().posX;
            double var8 = (double)var5.getPlayerCoordinates().posY;
            double var10 = (double)var5.getPlayerCoordinates().posZ;
            double var12 = 1.0D;
            double var14 = 1.0D;
            double var16 = 0.0D;

            if (par2ArrayOfStr.length > var36)
            {
                var6 = func_110666_a(par1ICommandSender, var6, par2ArrayOfStr[var36++]);
            }

            if (par2ArrayOfStr.length > var36)
            {
                var8 = func_110665_a(par1ICommandSender, var8, par2ArrayOfStr[var36++], 0, 0);
            }

            if (par2ArrayOfStr.length > var36)
            {
                var10 = func_110666_a(par1ICommandSender, var10, par2ArrayOfStr[var36++]);
            }

            if (par2ArrayOfStr.length > var36)
            {
                var12 = parseDoubleBounded(par1ICommandSender, par2ArrayOfStr[var36++], 0.0D, 3.4028234663852886E38D);
            }

            if (par2ArrayOfStr.length > var36)
            {
                var14 = parseDoubleBounded(par1ICommandSender, par2ArrayOfStr[var36++], 0.0D, 2.0D);
            }

            if (par2ArrayOfStr.length > var36)
            {
                var16 = parseDoubleBounded(par1ICommandSender, par2ArrayOfStr[var36++], 0.0D, 1.0D);
            }

            double var18 = var12 > 1.0D ? var12 * 16.0D : 16.0D;
            double var20 = var5.getDistance(var6, var8, var10);

            if (var20 > var18)
            {
                if (var16 <= 0.0D)
                {
                    throw new CommandException("commands.playsound.playerTooFar", new Object[] {var5.getCommandSenderName()});
                }

                double var22 = var6 - var5.posX;
                double var24 = var8 - var5.posY;
                double var26 = var10 - var5.posZ;
                double var28 = Math.sqrt(var22 * var22 + var24 * var24 + var26 * var26);
                double var30 = var5.posX;
                double var32 = var5.posY;
                double var34 = var5.posZ;

                if (var28 > 0.0D)
                {
                    var30 += var22 / var28 * 2.0D;
                    var32 += var24 / var28 * 2.0D;
                    var34 += var26 / var28 * 2.0D;
                }

                var5.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var4, var30, var32, var34, (float)var16, (float)var14));
            }
            else
            {
                var5.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var4, var6, var8, var10, (float)var12, (float)var14));
            }

            notifyAdmins(par1ICommandSender, "commands.playsound.success", new Object[] {var4, var5.getCommandSenderName()});
        }
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 1;
    }
}
