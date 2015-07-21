package net.minecraft.command.server;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CommandSetBlock extends CommandBase
{
    private static final String __OBFID = "CL_00000949";

    public String getCommandName()
    {
        return "setblock";
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
        return "commands.setblock.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length >= 4)
        {
            int var3 = par1ICommandSender.getPlayerCoordinates().posX;
            int var4 = par1ICommandSender.getPlayerCoordinates().posY;
            int var5 = par1ICommandSender.getPlayerCoordinates().posZ;
            var3 = MathHelper.floor_double(func_110666_a(par1ICommandSender, (double)var3, par2ArrayOfStr[0]));
            var4 = MathHelper.floor_double(func_110666_a(par1ICommandSender, (double)var4, par2ArrayOfStr[1]));
            var5 = MathHelper.floor_double(func_110666_a(par1ICommandSender, (double)var5, par2ArrayOfStr[2]));
            Block var6 = CommandBase.getBlockByText(par1ICommandSender, par2ArrayOfStr[3]);
            int var7 = 0;

            if (par2ArrayOfStr.length >= 5)
            {
                var7 = parseIntBounded(par1ICommandSender, par2ArrayOfStr[4], 0, 15);
            }

            World var8 = par1ICommandSender.getEntityWorld();

            if (!var8.blockExists(var3, var4, var5))
            {
                throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
            }
            else
            {
                NBTTagCompound var9 = new NBTTagCompound();
                boolean var10 = false;

                if (par2ArrayOfStr.length >= 7 && var6.hasTileEntity())
                {
                    String var11 = func_147178_a(par1ICommandSender, par2ArrayOfStr, 6).getUnformattedText();

                    try
                    {
                        NBTBase var12 = JsonToNBT.func_150315_a(var11);

                        if (!(var12 instanceof NBTTagCompound))
                        {
                            throw new CommandException("commands.setblock.tagError", new Object[] {"Not a valid tag"});
                        }

                        var9 = (NBTTagCompound)var12;
                        var10 = true;
                    }
                    catch (NBTException var13)
                    {
                        throw new CommandException("commands.setblock.tagError", new Object[] {var13.getMessage()});
                    }
                }

                if (par2ArrayOfStr.length >= 6)
                {
                    if (par2ArrayOfStr[5].equals("destroy"))
                    {
                        var8.func_147480_a(var3, var4, var5, true);
                    }
                    else if (par2ArrayOfStr[5].equals("keep") && !var8.isAirBlock(var3, var4, var5))
                    {
                        throw new CommandException("commands.setblock.noChange", new Object[0]);
                    }
                }

                if (!var8.setBlock(var3, var4, var5, var6, var7, 3))
                {
                    throw new CommandException("commands.setblock.noChange", new Object[0]);
                }
                else
                {
                    if (var10)
                    {
                        TileEntity var14 = var8.getTileEntity(var3, var4, var5);

                        if (var14 != null)
                        {
                            var9.setInteger("x", var3);
                            var9.setInteger("y", var4);
                            var9.setInteger("z", var5);
                            var14.readFromNBT(var9);
                        }
                    }

                    notifyAdmins(par1ICommandSender, "commands.setblock.success", new Object[0]);
                }
            }
        }
        else
        {
            throw new WrongUsageException("commands.setblock.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 4 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, Block.blockRegistry.getKeys()) : (par2ArrayOfStr.length == 6 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"replace", "destroy", "keep"}): null);
    }
}
