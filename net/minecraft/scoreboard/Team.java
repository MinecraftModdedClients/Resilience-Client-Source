package net.minecraft.scoreboard;

public abstract class Team
{
    private static final String __OBFID = "CL_00000621";

    /**
     * Same as ==
     */
    public boolean isSameTeam(Team par1Team)
    {
        return par1Team == null ? false : this == par1Team;
    }

    /**
     * Retrieve the name by which this team is registered in the scoreboard
     */
    public abstract String getRegisteredName();

    public abstract String func_142053_d(String var1);

    public abstract boolean func_98297_h();

    public abstract boolean getAllowFriendlyFire();
}
