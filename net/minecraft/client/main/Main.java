package net.minecraft.client.main;

import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Main
{
    private static final String __OBFID = "CL_00001461";

    public static void main(String[] par0ArrayOfStr)
    {
        System.setProperty("java.net.preferIPv4Stack", "true");
        OptionParser var1 = new OptionParser();
        var1.allowsUnrecognizedOptions();
        var1.accepts("demo");
        var1.accepts("fullscreen");
        ArgumentAcceptingOptionSpec var2 = var1.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec var3 = var1.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(25565), new Integer[0]);
        ArgumentAcceptingOptionSpec var4 = var1.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), new File[0]);
        ArgumentAcceptingOptionSpec var5 = var1.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec var6 = var1.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec var7 = var1.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec var8 = var1.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        ArgumentAcceptingOptionSpec var9 = var1.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec var10 = var1.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec var11 = var1.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L, new String[0]);
        ArgumentAcceptingOptionSpec var12 = var1.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec var13 = var1.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec var14 = var1.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec var15 = var1.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(854), new Integer[0]);
        ArgumentAcceptingOptionSpec var16 = var1.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(480), new Integer[0]);
        NonOptionArgumentSpec var17 = var1.nonOptions();
        OptionSet var18 = var1.parse(par0ArrayOfStr);
        List var19 = var18.valuesOf(var17);
        String var20 = (String)var18.valueOf(var7);
        Proxy var21 = Proxy.NO_PROXY;

        if (var20 != null)
        {
            try
            {
                var21 = new Proxy(Type.SOCKS, new InetSocketAddress(var20, ((Integer)var18.valueOf(var8)).intValue()));
            }
            catch (Exception var36)
            {
                ;
            }
        }

        final String var22 = (String)var18.valueOf(var9);
        final String var23 = (String)var18.valueOf(var10);

        if (!var21.equals(Proxy.NO_PROXY) && func_110121_a(var22) && func_110121_a(var23))
        {
            Authenticator.setDefault(new Authenticator()
            {
                private static final String __OBFID = "CL_00000828";
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(var22, var23.toCharArray());
                }
            });
        }

        int var24 = ((Integer)var18.valueOf(var15)).intValue();
        int var25 = ((Integer)var18.valueOf(var16)).intValue();
        boolean var26 = var18.has("fullscreen");
        boolean var27 = var18.has("demo");
        String var28 = (String)var18.valueOf(var14);
        File var29 = (File)var18.valueOf(var4);
        File var30 = var18.has(var5) ? (File)var18.valueOf(var5) : new File(var29, "assets/");
        File var31 = var18.has(var6) ? (File)var18.valueOf(var6) : new File(var29, "resourcepacks/");
        String var32 = var18.has(var12) ? (String)var12.value(var18) : (String)var11.value(var18);
        Session var33 = new Session((String)var11.value(var18), var32, (String)var13.value(var18));
        Minecraft var34 = new Minecraft(var33, var24, var25, var26, var27, var29, var30, var31, var21, var28);
        String var35 = (String)var18.valueOf(var2);

        if (var35 != null)
        {
            var34.setServer(var35, ((Integer)var18.valueOf(var3)).intValue());
        }

        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread")
        {
            private static final String __OBFID = "CL_00000829";
            public void run()
            {
                Minecraft.stopIntegratedServer();
            }
        });

        if (!var19.isEmpty())
        {
            System.out.println("Completely ignored arguments: " + var19);
        }

        Thread.currentThread().setName("Client thread");
        var34.run();
    }

    private static boolean func_110121_a(String par0Str)
    {
        return par0Str != null && !par0Str.isEmpty();
    }
}
