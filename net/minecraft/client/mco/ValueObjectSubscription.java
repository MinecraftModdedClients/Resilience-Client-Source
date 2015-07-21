package net.minecraft.client.mco;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.ValueObject;

public class ValueObjectSubscription extends ValueObject
{
    public long field_148790_a;
    public int field_148789_b;
    private static final String __OBFID = "CL_00001172";

    public static ValueObjectSubscription func_148788_a(String p_148788_0_)
    {
        ValueObjectSubscription var1 = new ValueObjectSubscription();

        try
        {
            JsonParser var2 = new JsonParser();
            JsonObject var3 = var2.parse(p_148788_0_).getAsJsonObject();
            var1.field_148790_a = var3.get("startDate").getAsLong();
            var1.field_148789_b = var3.get("daysLeft").getAsInt();
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
}
