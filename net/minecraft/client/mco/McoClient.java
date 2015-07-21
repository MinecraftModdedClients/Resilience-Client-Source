package net.minecraft.client.mco;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;

public class McoClient
{
    private final String field_148719_a;
    private final String field_148717_b;
    private final String field_148718_c;
    private final Proxy field_148715_d;
    private static String field_148716_e = "https://mcoapi.minecraft.net/";
    private static final String __OBFID = "CL_00001156";

    public McoClient(String p_i45484_1_, String p_i45484_2_, String p_i45484_3_, Proxy p_i45484_4_)
    {
        this.field_148719_a = p_i45484_1_;
        this.field_148717_b = p_i45484_2_;
        this.field_148718_c = p_i45484_3_;
        this.field_148715_d = p_i45484_4_;
    }

    public ValueObjectList func_148703_a() throws ExceptionMcoService, IOException
    {
        String var1 = this.func_148713_a(Request.func_148666_a(field_148716_e + "worlds"));
        return ValueObjectList.func_148771_a(var1);
    }

    public McoServer func_148709_a(long p_148709_1_) throws ExceptionMcoService, IOException
    {
        String var3 = this.func_148713_a(Request.func_148666_a(field_148716_e + "worlds" + "/$ID".replace("$ID", String.valueOf(p_148709_1_))));
        return McoServer.func_148805_c(var3);
    }

    public McoServerAddress func_148688_b(long p_148688_1_) throws Exception
    {
        String var3 = field_148716_e + "worlds" + "/$ID/join".replace("$ID", "" + p_148688_1_);
        String var4 = this.func_148713_a(Request.func_148670_a(var3, 5000, 30000));
        return McoServerAddress.func_148769_a(var4);
    }

    public void func_148707_a(String p_148707_1_, String p_148707_2_) throws ExceptionMcoService, UnsupportedEncodingException
    {
        StringBuilder var3 = new StringBuilder();
        var3.append(field_148716_e).append("worlds");
        var3.append("?name=").append(this.func_148711_c(p_148707_1_));
        var3.append("&template=").append(p_148707_2_);
        this.func_148713_a(Request.func_148661_a(var3.toString(), "", 5000, 30000));
    }

    public Boolean func_148687_b() throws ExceptionMcoService, IOException
    {
        String var1 = field_148716_e + "mco" + "/available";
        String var2 = this.func_148713_a(Request.func_148666_a(var1));
        return Boolean.valueOf(var2);
    }

    public Boolean func_148695_c() throws ExceptionMcoService, IOException
    {
        String var1 = field_148716_e + "mco" + "/client/outdated";
        String var2 = this.func_148713_a(Request.func_148666_a(var1));
        return Boolean.valueOf(var2);
    }

    public int func_148702_d() throws ExceptionMcoService
    {
        String var1 = field_148716_e + "payments" + "/unused";
        String var2 = this.func_148713_a(Request.func_148666_a(var1));
        return Integer.valueOf(var2).intValue();
    }

    public void func_148694_a(long p_148694_1_, String p_148694_3_) throws ExceptionMcoService
    {
        String var4 = field_148716_e + "invites" + "/$WORLD_ID/invite/$USER_NAME".replace("$WORLD_ID", String.valueOf(p_148694_1_)).replace("$USER_NAME", p_148694_3_);
        this.func_148713_a(Request.func_148663_b(var4));
    }

    public void func_148698_c(long p_148698_1_) throws ExceptionMcoService
    {
        String var3 = field_148716_e + "invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_148698_1_));
        this.func_148713_a(Request.func_148663_b(var3));
    }

    public McoServer func_148697_b(long p_148697_1_, String p_148697_3_) throws ExceptionMcoService, IOException
    {
        String var4 = field_148716_e + "invites" + "/$WORLD_ID/invite/$USER_NAME".replace("$WORLD_ID", String.valueOf(p_148697_1_)).replace("$USER_NAME", p_148697_3_);
        String var5 = this.func_148713_a(Request.func_148672_c(var4, ""));
        return McoServer.func_148805_c(var5);
    }

    public BackupList func_148704_d(long p_148704_1_) throws ExceptionMcoService
    {
        String var3 = field_148716_e + "worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(p_148704_1_));
        String var4 = this.func_148713_a(Request.func_148666_a(var3));
        return BackupList.func_148796_a(var4);
    }

    public void func_148689_a(long p_148689_1_, String p_148689_3_, String p_148689_4_, int p_148689_5_, int p_148689_6_) throws ExceptionMcoService, UnsupportedEncodingException
    {
        StringBuilder var7 = new StringBuilder();
        var7.append(field_148716_e).append("worlds").append("/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_148689_1_)));
        var7.append("?name=").append(this.func_148711_c(p_148689_3_));

        if (p_148689_4_ != null && !p_148689_4_.trim().equals(""))
        {
            var7.append("&motd=").append(this.func_148711_c(p_148689_4_));
        }
        else
        {
            var7.append("&motd=");
        }

        var7.append("&difficulty=").append(p_148689_5_).append("&gameMode=").append(p_148689_6_);
        this.func_148713_a(Request.func_148668_d(var7.toString(), ""));
    }

    public void func_148712_c(long p_148712_1_, String p_148712_3_) throws ExceptionMcoService
    {
        String var4 = field_148716_e + "worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(p_148712_1_)) + "?backupId=" + p_148712_3_;
        this.func_148713_a(Request.func_148669_b(var4, "", 40000, 40000));
    }

    public WorldTemplateList func_148693_e() throws ExceptionMcoService
    {
        String var1 = field_148716_e + "worlds" + "/templates";
        String var2 = this.func_148713_a(Request.func_148666_a(var1));
        return WorldTemplateList.func_148781_a(var2);
    }

    public Boolean func_148692_e(long p_148692_1_) throws ExceptionMcoService, IOException
    {
        String var3 = field_148716_e + "worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(p_148692_1_));
        String var4 = this.func_148713_a(Request.func_148668_d(var3, ""));
        return Boolean.valueOf(var4);
    }

    public Boolean func_148700_f(long p_148700_1_) throws ExceptionMcoService, IOException
    {
        String var3 = field_148716_e + "worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(p_148700_1_));
        String var4 = this.func_148713_a(Request.func_148668_d(var3, ""));
        return Boolean.valueOf(var4);
    }

    public Boolean func_148699_d(long p_148699_1_, String p_148699_3_) throws ExceptionMcoService, UnsupportedEncodingException
    {
        StringBuilder var4 = new StringBuilder();
        var4.append(field_148716_e).append("worlds").append("/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(p_148699_1_)));

        if (p_148699_3_ != null && p_148699_3_.length() > 0)
        {
            var4.append("?seed=").append(this.func_148711_c(p_148699_3_));
        }

        String var5 = this.func_148713_a(Request.func_148669_b(var4.toString(), "", 30000, 80000));
        return Boolean.valueOf(var5);
    }

    public Boolean func_148696_e(long p_148696_1_, String p_148696_3_) throws ExceptionMcoService
    {
        StringBuilder var4 = new StringBuilder();
        var4.append(field_148716_e).append("worlds").append("/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(p_148696_1_)));

        if (p_148696_3_ != null)
        {
            var4.append("?template=").append(p_148696_3_);
        }

        String var5 = this.func_148713_a(Request.func_148669_b(var4.toString(), "", 30000, 80000));
        return Boolean.valueOf(var5);
    }

    public ValueObjectSubscription func_148705_g(long p_148705_1_) throws ExceptionMcoService, IOException
    {
        String var3 = this.func_148713_a(Request.func_148666_a(field_148716_e + "subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_148705_1_))));
        return ValueObjectSubscription.func_148788_a(var3);
    }

    public int func_148701_f() throws ExceptionMcoService
    {
        String var1 = this.func_148713_a(Request.func_148666_a(field_148716_e + "invites" + "/count/pending"));
        return Integer.parseInt(var1);
    }

    public PendingInvitesList func_148710_g() throws ExceptionMcoService
    {
        String var1 = this.func_148713_a(Request.func_148666_a(field_148716_e + "invites" + "/pending"));
        return PendingInvitesList.func_148767_a(var1);
    }

    public void func_148691_a(String p_148691_1_) throws ExceptionMcoService
    {
        this.func_148713_a(Request.func_148668_d(field_148716_e + "invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", p_148691_1_), ""));
    }

    public String func_148708_h(long p_148708_1_) throws ExceptionMcoService
    {
        return this.func_148713_a(Request.func_148666_a(field_148716_e + "worlds" + "/$WORLD_ID/backups/download".replace("$WORLD_ID", String.valueOf(p_148708_1_))));
    }

    public void func_148706_b(String p_148706_1_) throws ExceptionMcoService
    {
        this.func_148713_a(Request.func_148668_d(field_148716_e + "invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", p_148706_1_), ""));
    }

    public void func_148714_h() throws ExceptionMcoService
    {
        this.func_148713_a(Request.func_148672_c(field_148716_e + "mco" + "/tos/agreed", ""));
    }

    public String func_148690_i() throws ExceptionMcoService
    {
        return this.func_148713_a(Request.func_148666_a(field_148716_e + "mco" + "/stat"));
    }

    private String func_148711_c(String p_148711_1_) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(p_148711_1_, "UTF-8");
    }

    private String func_148713_a(Request p_148713_1_) throws ExceptionMcoService
    {
        McoClientConfig.func_148684_a(this.field_148715_d);
        p_148713_1_.func_148665_a("sid", this.field_148719_a);
        p_148713_1_.func_148665_a("user", this.field_148717_b);
        p_148713_1_.func_148665_a("version", this.field_148718_c);

        try
        {
            int var2 = p_148713_1_.func_148671_a();

            if (var2 == 503)
            {
                int var3 = p_148713_1_.func_148664_b();
                throw new ExceptionRetryCall(var3);
            }
            else if (var2 >= 200 && var2 < 300)
            {
                return p_148713_1_.func_148659_d();
            }
            else
            {
                throw new ExceptionMcoService(p_148713_1_.func_148671_a(), p_148713_1_.func_148659_d(), p_148713_1_.func_148673_g());
            }
        }
        catch (ExceptionMcoHttp var4)
        {
            throw new ExceptionMcoService(500, "Server not available!", -1);
        }
    }
}
