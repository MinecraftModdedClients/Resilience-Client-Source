package net.minecraft.client.gui;

import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiButtonLink extends GuiButton
{
    private static final Logger logger = LogManager.getLogger();
    private static final String __OBFID = "CL_00000673";

    public GuiButtonLink(int par1, int par2, int par3, int par4, int par5, String par6Str)
    {
        super(par1, par2, par3, par4, par5, par6Str);
    }

    public void func_146138_a(String p_146138_1_)
    {
        try
        {
            URI var2 = new URI(p_146138_1_);
            Class var3 = Class.forName("java.awt.Desktop");
            Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            var3.getMethod("browse", new Class[] {URI.class}).invoke(var4, new Object[] {var2});
        }
        catch (Throwable var5)
        {
            logger.error("Couldn\'t open link", var5);
        }
    }
}
