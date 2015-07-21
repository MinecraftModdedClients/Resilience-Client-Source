package net.minecraft.world.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFormatOld implements ISaveFormat
{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Reference to the File object representing the directory for the world saves
     */
    protected final File savesDirectory;
    private static final String __OBFID = "CL_00000586";

    public SaveFormatOld(File par1File)
    {
        if (!par1File.exists())
        {
            par1File.mkdirs();
        }

        this.savesDirectory = par1File;
    }

    public List getSaveList() throws AnvilConverterException
    {
        ArrayList var1 = new ArrayList();

        for (int var2 = 0; var2 < 5; ++var2)
        {
            String var3 = "World" + (var2 + 1);
            WorldInfo var4 = this.getWorldInfo(var3);

            if (var4 != null)
            {
                var1.add(new SaveFormatComparator(var3, "", var4.getLastTimePlayed(), var4.getSizeOnDisk(), var4.getGameType(), false, var4.isHardcoreModeEnabled(), var4.areCommandsAllowed()));
            }
        }

        return var1;
    }

    public void flushCache() {}

    /**
     * gets the world info
     */
    public WorldInfo getWorldInfo(String par1Str)
    {
        File var2 = new File(this.savesDirectory, par1Str);

        if (!var2.exists())
        {
            return null;
        }
        else
        {
            File var3 = new File(var2, "level.dat");
            NBTTagCompound var4;
            NBTTagCompound var5;

            if (var3.exists())
            {
                try
                {
                    var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
                    var5 = var4.getCompoundTag("Data");
                    return new WorldInfo(var5);
                }
                catch (Exception var7)
                {
                    logger.error("Exception reading " + var3, var7);
                }
            }

            var3 = new File(var2, "level.dat_old");

            if (var3.exists())
            {
                try
                {
                    var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
                    var5 = var4.getCompoundTag("Data");
                    return new WorldInfo(var5);
                }
                catch (Exception var6)
                {
                    logger.error("Exception reading " + var3, var6);
                }
            }

            return null;
        }
    }

    /**
     * @args: Takes two arguments - first the name of the directory containing the world and second the new name for
     * that world. @desc: Renames the world by storing the new name in level.dat. It does *not* rename the directory
     * containing the world data.
     */
    public void renameWorld(String par1Str, String par2Str)
    {
        File var3 = new File(this.savesDirectory, par1Str);

        if (var3.exists())
        {
            File var4 = new File(var3, "level.dat");

            if (var4.exists())
            {
                try
                {
                    NBTTagCompound var5 = CompressedStreamTools.readCompressed(new FileInputStream(var4));
                    NBTTagCompound var6 = var5.getCompoundTag("Data");
                    var6.setString("LevelName", par2Str);
                    CompressedStreamTools.writeCompressed(var5, new FileOutputStream(var4));
                }
                catch (Exception var7)
                {
                    var7.printStackTrace();
                }
            }
        }
    }

    /**
     * @args: Takes one argument - the name of the directory of the world to delete. @desc: Delete the world by deleting
     * the associated directory recursively.
     */
    public boolean deleteWorldDirectory(String par1Str)
    {
        File var2 = new File(this.savesDirectory, par1Str);

        if (!var2.exists())
        {
            return true;
        }
        else
        {
            logger.info("Deleting level " + par1Str);

            for (int var3 = 1; var3 <= 5; ++var3)
            {
                logger.info("Attempt " + var3 + "...");

                if (deleteFiles(var2.listFiles()))
                {
                    break;
                }

                logger.warn("Unsuccessful in deleting contents.");

                if (var3 < 5)
                {
                    try
                    {
                        Thread.sleep(500L);
                    }
                    catch (InterruptedException var5)
                    {
                        ;
                    }
                }
            }

            return var2.delete();
        }
    }

    /**
     * @args: Takes one argument - the list of files and directories to delete. @desc: Deletes the files and directory
     * listed in the list recursively.
     */
    protected static boolean deleteFiles(File[] par0ArrayOfFile)
    {
        for (int var1 = 0; var1 < par0ArrayOfFile.length; ++var1)
        {
            File var2 = par0ArrayOfFile[var1];
            logger.debug("Deleting " + var2);

            if (var2.isDirectory() && !deleteFiles(var2.listFiles()))
            {
                logger.warn("Couldn\'t delete directory " + var2);
                return false;
            }

            if (!var2.delete())
            {
                logger.warn("Couldn\'t delete file " + var2);
                return false;
            }
        }

        return true;
    }

    /**
     * Returns back a loader for the specified save directory
     */
    public ISaveHandler getSaveLoader(String par1Str, boolean par2)
    {
        return new SaveHandler(this.savesDirectory, par1Str, par2);
    }

    /**
     * Checks if the save directory uses the old map format
     */
    public boolean isOldMapFormat(String par1Str)
    {
        return false;
    }

    /**
     * Converts the specified map to the new map format. Args: worldName, loadingScreen
     */
    public boolean convertMapFormat(String par1Str, IProgressUpdate par2IProgressUpdate)
    {
        return false;
    }

    /**
     * Return whether the given world can be loaded.
     */
    public boolean canLoadWorld(String par1Str)
    {
        File var2 = new File(this.savesDirectory, par1Str);
        return var2.isDirectory();
    }
}
