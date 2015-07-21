package net.minecraft.client.mco;

import java.net.Proxy;

public class McoClientConfig
{
    private static Proxy field_148686_a;
    private static final String __OBFID = "CL_00001157";

    public static Proxy func_148685_a()
    {
        return field_148686_a;
    }

    public static void func_148684_a(Proxy p_148684_0_)
    {
        if (field_148686_a == null)
        {
            field_148686_a = p_148684_0_;
        }
    }
}
