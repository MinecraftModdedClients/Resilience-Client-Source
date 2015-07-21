package net.minecraft.world.storage;

import net.minecraft.world.WorldSettings;

public class SaveFormatComparator implements Comparable
{
    /** the file name of this save */
    private final String fileName;

    /** the displayed name of this save file */
    private final String displayName;
    private final long lastTimePlayed;
    private final long sizeOnDisk;
    private final boolean requiresConversion;

    /** Instance of EnumGameType. */
    private final WorldSettings.GameType theEnumGameType;
    private final boolean hardcore;
    private final boolean cheatsEnabled;
    private static final String __OBFID = "CL_00000601";

    public SaveFormatComparator(String par1Str, String par2Str, long par3, long par5, WorldSettings.GameType par7EnumGameType, boolean par8, boolean par9, boolean par10)
    {
        this.fileName = par1Str;
        this.displayName = par2Str;
        this.lastTimePlayed = par3;
        this.sizeOnDisk = par5;
        this.theEnumGameType = par7EnumGameType;
        this.requiresConversion = par8;
        this.hardcore = par9;
        this.cheatsEnabled = par10;
    }

    /**
     * return the file name
     */
    public String getFileName()
    {
        return this.fileName;
    }

    /**
     * return the display name of the save
     */
    public String getDisplayName()
    {
        return this.displayName;
    }

    public boolean requiresConversion()
    {
        return this.requiresConversion;
    }

    public long getLastTimePlayed()
    {
        return this.lastTimePlayed;
    }

    public int compareTo(SaveFormatComparator par1SaveFormatComparator)
    {
        return this.lastTimePlayed < par1SaveFormatComparator.lastTimePlayed ? 1 : (this.lastTimePlayed > par1SaveFormatComparator.lastTimePlayed ? -1 : this.fileName.compareTo(par1SaveFormatComparator.fileName));
    }

    /**
     * Gets the EnumGameType.
     */
    public WorldSettings.GameType getEnumGameType()
    {
        return this.theEnumGameType;
    }

    public boolean isHardcoreModeEnabled()
    {
        return this.hardcore;
    }

    /**
     * @return {@code true} if cheats are enabled for this world
     */
    public boolean getCheatsEnabled()
    {
        return this.cheatsEnabled;
    }

    public int compareTo(Object par1Obj)
    {
        return this.compareTo((SaveFormatComparator)par1Obj);
    }
}
