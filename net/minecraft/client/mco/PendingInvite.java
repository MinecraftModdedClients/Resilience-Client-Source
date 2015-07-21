package net.minecraft.client.mco;

import com.google.gson.JsonObject;
import net.minecraft.util.ValueObject;

public class PendingInvite extends ValueObject
{
    public String field_148776_a;
    public String field_148774_b;
    public String field_148775_c;
    private static final String __OBFID = "CL_00001170";

    public static PendingInvite func_148773_a(JsonObject p_148773_0_)
    {
        PendingInvite var1 = new PendingInvite();

        try
        {
            var1.field_148776_a = p_148773_0_.get("invitationId").getAsString();
            var1.field_148774_b = p_148773_0_.get("worldName").getAsString();
            var1.field_148775_c = p_148773_0_.get("worldOwnerName").getAsString();
        }
        catch (Exception var3)
        {
            ;
        }

        return var1;
    }
}
