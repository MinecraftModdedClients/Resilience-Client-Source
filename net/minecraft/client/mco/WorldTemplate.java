package net.minecraft.client.mco;

import com.google.gson.JsonObject;
import net.minecraft.util.ValueObject;

public class WorldTemplate extends ValueObject
{
    public String field_148787_a;
    public String field_148785_b;
    public String field_148786_c;
    public String field_148784_d;
    private static final String __OBFID = "CL_00001174";

    public static WorldTemplate func_148783_a(JsonObject p_148783_0_)
    {
        WorldTemplate var1 = new WorldTemplate();

        try
        {
            var1.field_148787_a = p_148783_0_.get("id").getAsString();
            var1.field_148785_b = p_148783_0_.get("name").getAsString();
            var1.field_148786_c = p_148783_0_.get("version").getAsString();
            var1.field_148784_d = p_148783_0_.get("author").getAsString();
        }
        catch (IllegalArgumentException var3)
        {
            ;
        }

        return var1;
    }
}
