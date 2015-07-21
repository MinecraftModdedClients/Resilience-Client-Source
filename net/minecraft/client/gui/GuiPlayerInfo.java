package net.minecraft.client.gui;

public class GuiPlayerInfo
{
    /** The string value of the object */
    public final String name;

    /** Player name in lowercase. */
    private final String nameinLowerCase;

    /** Player response time to server in milliseconds */
    public int responseTime;
    private static final String __OBFID = "CL_00000888";

    public GuiPlayerInfo(String par1Str)
    {
        this.name = par1Str;
        this.nameinLowerCase = par1Str.toLowerCase();
    }
}
