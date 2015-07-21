package net.minecraft.command;

import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;

public class CommandEnchant extends CommandBase
{
    private static final String __OBFID = "CL_00000377";

    public String getCommandName()
    {
        return "enchant";
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
        return "commands.enchant.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 2)
        {
            throw new WrongUsageException("commands.enchant.usage", new Object[0]);
        }
        else
        {
            EntityPlayerMP var3 = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
            int var4 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 0, Enchantment.enchantmentsList.length - 1);
            int var5 = 1;
            ItemStack var6 = var3.getCurrentEquippedItem();

            if (var6 == null)
            {
                throw new CommandException("commands.enchant.noItem", new Object[0]);
            }
            else
            {
                Enchantment var7 = Enchantment.enchantmentsList[var4];

                if (var7 == null)
                {
                    throw new NumberInvalidException("commands.enchant.notFound", new Object[] {Integer.valueOf(var4)});
                }
                else if (!var7.canApply(var6))
                {
                    throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
                }
                else
                {
                    if (par2ArrayOfStr.length >= 3)
                    {
                        var5 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], var7.getMinLevel(), var7.getMaxLevel());
                    }

                    if (var6.hasTagCompound())
                    {
                        NBTTagList var8 = var6.getEnchantmentTagList();

                        if (var8 != null)
                        {
                            for (int var9 = 0; var9 < var8.tagCount(); ++var9)
                            {
                                short var10 = var8.getCompoundTagAt(var9).getShort("id");

                                if (Enchantment.enchantmentsList[var10] != null)
                                {
                                    Enchantment var11 = Enchantment.enchantmentsList[var10];

                                    if (!var11.canApplyTogether(var7))
                                    {
                                        throw new CommandException("commands.enchant.cantCombine", new Object[] {var7.getTranslatedName(var5), var11.getTranslatedName(var8.getCompoundTagAt(var9).getShort("lvl"))});
                                    }
                                }
                            }
                        }
                    }

                    var6.addEnchantment(var7, var5);
                    notifyAdmins(par1ICommandSender, "commands.enchant.success", new Object[0]);
                }
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getListOfPlayers()) : null;
    }

    protected String[] getListOfPlayers()
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
