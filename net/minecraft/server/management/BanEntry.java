package net.minecraft.server.management;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BanEntry
{
    private static final Logger logger = LogManager.getLogger();
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    private final String username;
    private Date banStartDate = new Date();
    private String bannedBy = "(Unknown)";
    private Date banEndDate;
    private String reason = "Banned by an operator.";
    private static final String __OBFID = "CL_00001395";

    public BanEntry(String par1Str)
    {
        this.username = par1Str;
    }

    public String getBannedUsername()
    {
        return this.username;
    }

    public Date getBanStartDate()
    {
        return this.banStartDate;
    }

    public String getBannedBy()
    {
        return this.bannedBy;
    }

    public void setBannedBy(String par1Str)
    {
        this.bannedBy = par1Str;
    }

    public Date getBanEndDate()
    {
        return this.banEndDate;
    }

    public boolean hasBanExpired()
    {
        return this.banEndDate == null ? false : this.banEndDate.before(new Date());
    }

    public String getBanReason()
    {
        return this.reason;
    }

    public void setBanReason(String par1Str)
    {
        this.reason = par1Str;
    }

    public String buildBanString()
    {
        StringBuilder var1 = new StringBuilder();
        var1.append(this.getBannedUsername());
        var1.append("|");
        var1.append(dateFormat.format(this.getBanStartDate()));
        var1.append("|");
        var1.append(this.getBannedBy());
        var1.append("|");
        var1.append(this.getBanEndDate() == null ? "Forever" : dateFormat.format(this.getBanEndDate()));
        var1.append("|");
        var1.append(this.getBanReason());
        return var1.toString();
    }
}
