package net.minecraft.client.mco;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;
import net.minecraft.util.ValueObject;

public class Backup extends ValueObject
{
    public String field_148780_a;
    public Date field_148778_b;
    public long field_148779_c;
    private static final String __OBFID = "CL_00001164";

    public static Backup func_148777_a(JsonElement p_148777_0_)
    {
        JsonObject var1 = p_148777_0_.getAsJsonObject();
        Backup var2 = new Backup();

        try
        {
            var2.field_148780_a = var1.get("backupId").getAsString();
            var2.field_148778_b = new Date(Long.parseLong(var1.get("lastModifiedDate").getAsString()));
            var2.field_148779_c = Long.parseLong(var1.get("size").getAsString());
        }
        catch (IllegalArgumentException var4)
        {
            ;
        }

        return var2;
    }
}
