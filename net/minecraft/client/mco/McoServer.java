package net.minecraft.client.mco;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class McoServer
{
    public long field_148812_a;
    public String field_148810_b;
    public String field_148811_c;
    public String field_148808_d;
    public String field_148809_e;
    public List field_148806_f;
    public String field_148807_g;
    public boolean field_148819_h;
    public int field_148820_i;
    public int field_148817_j;
    public int field_148818_k;
    public int field_148815_l;
    public String field_148816_m = "";
    public String field_148813_n = "\u00a770";
    public boolean field_148814_o = false;
    private static final String __OBFID = "CL_00001166";

    public String func_148800_a()
    {
        return this.field_148811_c;
    }

    public String func_148801_b()
    {
        return this.field_148810_b;
    }

    public void func_148803_a(String p_148803_1_)
    {
        this.field_148810_b = p_148803_1_;
    }

    public void func_148804_b(String p_148804_1_)
    {
        this.field_148811_c = p_148804_1_;
    }

    public void func_148799_a(McoServer p_148799_1_)
    {
        this.field_148816_m = p_148799_1_.field_148816_m;
        this.field_148815_l = p_148799_1_.field_148815_l;
    }

    public static McoServer func_148802_a(JsonObject p_148802_0_)
    {
        McoServer var1 = new McoServer();

        try
        {
            var1.field_148812_a = !p_148802_0_.get("id").isJsonNull() ? p_148802_0_.get("id").getAsLong() : -1L;
            var1.field_148810_b = !p_148802_0_.get("name").isJsonNull() ? p_148802_0_.get("name").getAsString() : null;
            var1.field_148811_c = !p_148802_0_.get("motd").isJsonNull() ? p_148802_0_.get("motd").getAsString() : null;
            var1.field_148808_d = !p_148802_0_.get("state").isJsonNull() ? p_148802_0_.get("state").getAsString() : McoServer.State.CLOSED.name();
            var1.field_148809_e = !p_148802_0_.get("owner").isJsonNull() ? p_148802_0_.get("owner").getAsString() : null;

            if (p_148802_0_.get("invited").isJsonArray())
            {
                var1.field_148806_f = func_148798_a(p_148802_0_.get("invited").getAsJsonArray());
            }
            else
            {
                var1.field_148806_f = new ArrayList();
            }

            var1.field_148818_k = !p_148802_0_.get("daysLeft").isJsonNull() ? p_148802_0_.get("daysLeft").getAsInt() : 0;
            var1.field_148807_g = !p_148802_0_.get("ip").isJsonNull() ? p_148802_0_.get("ip").getAsString() : null;
            var1.field_148819_h = !p_148802_0_.get("expired").isJsonNull() && p_148802_0_.get("expired").getAsBoolean();
            var1.field_148820_i = !p_148802_0_.get("difficulty").isJsonNull() ? p_148802_0_.get("difficulty").getAsInt() : 0;
            var1.field_148817_j = !p_148802_0_.get("gameMode").isJsonNull() ? p_148802_0_.get("gameMode").getAsInt() : 0;
        }
        catch (IllegalArgumentException var3)
        {
            ;
        }

        return var1;
    }

    private static List func_148798_a(JsonArray p_148798_0_)
    {
        ArrayList var1 = new ArrayList();
        Iterator var2 = p_148798_0_.iterator();

        while (var2.hasNext())
        {
            var1.add(((JsonElement)var2.next()).getAsString());
        }

        return var1;
    }

    public static McoServer func_148805_c(String p_148805_0_)
    {
        McoServer var1 = new McoServer();

        try
        {
            JsonParser var2 = new JsonParser();
            JsonObject var3 = var2.parse(p_148805_0_).getAsJsonObject();
            var1 = func_148802_a(var3);
        }
        catch (JsonIOException var4)
        {
            ;
        }
        catch (JsonSyntaxException var5)
        {
            ;
        }

        return var1;
    }

    public int hashCode()
    {
        return (new HashCodeBuilder(17, 37)).append(this.field_148812_a).append(this.field_148810_b).append(this.field_148811_c).append(this.field_148808_d).append(this.field_148809_e).append(this.field_148819_h).toHashCode();
    }

    public boolean equals(Object par1Obj)
    {
        if (par1Obj == null)
        {
            return false;
        }
        else if (par1Obj == this)
        {
            return true;
        }
        else if (par1Obj.getClass() != this.getClass())
        {
            return false;
        }
        else
        {
            McoServer var2 = (McoServer)par1Obj;
            return (new EqualsBuilder()).append(this.field_148812_a, var2.field_148812_a).append(this.field_148810_b, var2.field_148810_b).append(this.field_148811_c, var2.field_148811_c).append(this.field_148808_d, var2.field_148808_d).append(this.field_148809_e, var2.field_148809_e).append(this.field_148819_h, var2.field_148819_h).isEquals();
        }
    }

    public static enum State
    {
        CLOSED("CLOSED", 0),
        OPEN("OPEN", 1),
        ADMIN_LOCK("ADMIN_LOCK", 2);

        private static final McoServer.State[] $VALUES = new McoServer.State[]{CLOSED, OPEN, ADMIN_LOCK};
        private static final String __OBFID = "CL_00001167";

        private State(String p_i45485_1_, int p_i45485_2_) {}
    }
}
