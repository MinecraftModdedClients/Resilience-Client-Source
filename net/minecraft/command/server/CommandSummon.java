package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class CommandSummon extends CommandBase
{
    private static final String __OBFID = "CL_00001158";

    public String getCommandName()
    {
        return "summon";
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
        return "commands.summon.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length >= 1)
        {
            String var3 = par2ArrayOfStr[0];
            double var4 = (double)par1ICommandSender.getPlayerCoordinates().posX + 0.5D;
            double var6 = (double)par1ICommandSender.getPlayerCoordinates().posY;
            double var8 = (double)par1ICommandSender.getPlayerCoordinates().posZ + 0.5D;

            if (par2ArrayOfStr.length >= 4)
            {
                var4 = func_110666_a(par1ICommandSender, var4, par2ArrayOfStr[1]);
                var6 = func_110666_a(par1ICommandSender, var6, par2ArrayOfStr[2]);
                var8 = func_110666_a(par1ICommandSender, var8, par2ArrayOfStr[3]);
            }

            World var10 = par1ICommandSender.getEntityWorld();

            if (!var10.blockExists((int)var4, (int)var6, (int)var8))
            {
                notifyAdmins(par1ICommandSender, "commands.summon.outOfWorld", new Object[0]);
            }
            else
            {
                NBTTagCompound var11 = new NBTTagCompound();
                boolean var12 = false;

                if (par2ArrayOfStr.length >= 5)
                {
                    IChatComponent var13 = func_147178_a(par1ICommandSender, par2ArrayOfStr, 4);

                    try
                    {
                        NBTBase var14 = JsonToNBT.func_150315_a(var13.getUnformattedText());

                        if (!(var14 instanceof NBTTagCompound))
                        {
                            notifyAdmins(par1ICommandSender, "commands.summon.tagError", new Object[] {"Not a valid tag"});
                            return;
                        }

                        var11 = (NBTTagCompound)var14;
                        var12 = true;
                    }
                    catch (NBTException var17)
                    {
                        notifyAdmins(par1ICommandSender, "commands.summon.tagError", new Object[] {var17.getMessage()});
                        return;
                    }
                }

                var11.setString("id", var3);
                Entity var18 = EntityList.createEntityFromNBT(var11, var10);

                if (var18 != null)
                {
                    var18.setLocationAndAngles(var4, var6, var8, var18.rotationYaw, var18.rotationPitch);

                    if (!var12 && var18 instanceof EntityLiving)
                    {
                        ((EntityLiving)var18).onSpawnWithEgg((IEntityLivingData)null);
                    }

                    var10.spawnEntityInWorld(var18);
                    Entity var19 = var18;

                    for (NBTTagCompound var15 = var11; var15.func_150297_b("Riding", 10); var15 = var15.getCompoundTag("Riding"))
                    {
                        Entity var16 = EntityList.createEntityFromNBT(var15.getCompoundTag("Riding"), var10);

                        if (var16 != null)
                        {
                            var16.setLocationAndAngles(var4, var6, var8, var16.rotationYaw, var16.rotationPitch);
                            var10.spawnEntityInWorld(var16);
                            var19.mountEntity(var16);
                        }

                        var19 = var16;
                    }

                    notifyAdmins(par1ICommandSender, "commands.summon.success", new Object[0]);
                }
                else
                {
                    notifyAdmins(par1ICommandSender, "commands.summon.failed", new Object[0]);
                }
            }
        }
        else
        {
            throw new WrongUsageException("commands.summon.usage", new Object[0]);
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.func_147182_d()) : null;
    }

    protected String[] func_147182_d()
    {
        return (String[])EntityList.func_151515_b().toArray(new String[0]);
    }
}
