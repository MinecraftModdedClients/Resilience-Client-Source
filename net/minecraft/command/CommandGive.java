package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class CommandGive extends CommandBase
{
    private static final String __OBFID = "CL_00000502";

    public String getCommandName()
    {
        return "give";
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
        return "commands.give.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 2)
        {
            throw new WrongUsageException("commands.give.usage", new Object[0]);
        }
        else
        {
            EntityPlayerMP var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
            Item var4 = getItemByText(par1ICommandSender, par2ArrayOfStr[1]);
            int var5 = 1;
            int var6 = 0;

            if (par2ArrayOfStr.length >= 3)
            {
                var5 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], 1, 64);
            }

            if (par2ArrayOfStr.length >= 4)
            {
                var6 = parseInt(par1ICommandSender, par2ArrayOfStr[3]);
            }

            ItemStack var7 = new ItemStack(var4, var5, var6);

            if (par2ArrayOfStr.length >= 5)
            {
                String var8 = func_147178_a(par1ICommandSender, par2ArrayOfStr, 4).getUnformattedText();

                try
                {
                    NBTBase var9 = JsonToNBT.func_150315_a(var8);

                    if (!(var9 instanceof NBTTagCompound))
                    {
                        notifyAdmins(par1ICommandSender, "commands.give.tagError", new Object[] {"Not a valid tag"});
                        return;
                    }

                    var7.setTagCompound((NBTTagCompound)var9);
                }
                catch (NBTException var10)
                {
                    notifyAdmins(par1ICommandSender, "commands.give.tagError", new Object[] {var10.getMessage()});
                    return;
                }
            }

            EntityItem var11 = var3.dropPlayerItemWithRandomChoice(var7, false);
            var11.delayBeforeCanPickup = 0;
            var11.func_145797_a(var3.getCommandSenderName());
            notifyAdmins(par1ICommandSender, "commands.give.success", new Object[] {var7.func_151000_E(), Integer.valueOf(var5), var3.getCommandSenderName()});
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getPlayers()) : (par2ArrayOfStr.length == 2 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Item.itemRegistry.getKeys()) : null);
    }

    protected String[] getPlayers()
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
