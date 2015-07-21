package net.minecraft.scoreboard;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ScorePlayerTeam extends Team
{
    private final Scoreboard theScoreboard;
    private final String field_96675_b;

    /** A set of all team member usernames. */
    private final Set membershipSet = new HashSet();
    private String teamNameSPT;
    private String namePrefixSPT = "";
    private String colorSuffix = "";
    private boolean allowFriendlyFire = true;
    private boolean canSeeFriendlyInvisibles = true;
    private static final String __OBFID = "CL_00000616";

    public ScorePlayerTeam(Scoreboard par1Scoreboard, String par2Str)
    {
        this.theScoreboard = par1Scoreboard;
        this.field_96675_b = par2Str;
        this.teamNameSPT = par2Str;
    }

    /**
     * Retrieve the name by which this team is registered in the scoreboard
     */
    public String getRegisteredName()
    {
        return this.field_96675_b;
    }

    public String func_96669_c()
    {
        return this.teamNameSPT;
    }

    public void setTeamName(String par1Str)
    {
        if (par1Str == null)
        {
            throw new IllegalArgumentException("Name cannot be null");
        }
        else
        {
            this.teamNameSPT = par1Str;
            this.theScoreboard.func_96538_b(this);
        }
    }

    public Collection getMembershipCollection()
    {
        return this.membershipSet;
    }

    /**
     * Returns the color prefix for the player's team name
     */
    public String getColorPrefix()
    {
        return this.namePrefixSPT;
    }

    public void setNamePrefix(String par1Str)
    {
        if (par1Str == null)
        {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        else
        {
            this.namePrefixSPT = par1Str;
            this.theScoreboard.func_96538_b(this);
        }
    }

    /**
     * Returns the color suffix for the player's team name
     */
    public String getColorSuffix()
    {
        return this.colorSuffix;
    }

    public void setNameSuffix(String par1Str)
    {
        if (par1Str == null)
        {
            throw new IllegalArgumentException("Suffix cannot be null");
        }
        else
        {
            this.colorSuffix = par1Str;
            this.theScoreboard.func_96538_b(this);
        }
    }

    public String func_142053_d(String par1Str)
    {
        return this.getColorPrefix() + par1Str + this.getColorSuffix();
    }

    /**
     * Returns the player name including the color prefixes and suffixes
     */
    public static String formatPlayerName(Team par0Team, String par1Str)
    {
        return par0Team == null ? par1Str : par0Team.func_142053_d(par1Str);
    }

    public boolean getAllowFriendlyFire()
    {
        return this.allowFriendlyFire;
    }

    public void setAllowFriendlyFire(boolean par1)
    {
        this.allowFriendlyFire = par1;
        this.theScoreboard.func_96538_b(this);
    }

    public boolean func_98297_h()
    {
        return this.canSeeFriendlyInvisibles;
    }

    public void setSeeFriendlyInvisiblesEnabled(boolean par1)
    {
        this.canSeeFriendlyInvisibles = par1;
        this.theScoreboard.func_96538_b(this);
    }

    public int func_98299_i()
    {
        int var1 = 0;

        if (this.getAllowFriendlyFire())
        {
            var1 |= 1;
        }

        if (this.func_98297_h())
        {
            var1 |= 2;
        }

        return var1;
    }

    public void func_98298_a(int par1)
    {
        this.setAllowFriendlyFire((par1 & 1) > 0);
        this.setSeeFriendlyInvisiblesEnabled((par1 & 2) > 0);
    }
}
