package net.minecraft.client.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.ValueObjectSubscription;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenSubscription extends GuiScreen
{
    private static final Logger logger = LogManager.getLogger();
    private final GuiScreen field_146780_f;
    private final McoServer field_146781_g;
    private final int field_146787_h = 0;
    private final int field_146788_i = 1;
    private int field_146785_r;
    private String field_146784_s;
    private final String field_146783_t = "https://account.mojang.com";
    private final String field_146782_u = "/buy/realms";
    private static final String __OBFID = "CL_00000813";

    public GuiScreenSubscription(GuiScreen par1GuiScreen, McoServer par2McoServer)
    {
        this.field_146780_f = par1GuiScreen;
        this.field_146781_g = par2McoServer;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.func_146778_a(this.field_146781_g.field_148812_a);
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("mco.configure.world.subscription.extend", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
    }

    private void func_146778_a(long p_146778_1_)
    {
        Session var3 = this.mc.getSession();
        McoClient var4 = new McoClient(var3.getSessionID(), var3.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());

        try
        {
            ValueObjectSubscription var5 = var4.func_148705_g(p_146778_1_);
            this.field_146785_r = var5.field_148789_b;
            this.field_146784_s = this.func_146776_b(var5.field_148790_a);
        }
        catch (ExceptionMcoService var6)
        {
            logger.error("Couldn\'t get subscription");
        }
        catch (IOException var7)
        {
            logger.error("Couldn\'t parse response subscribing");
        }
    }

    private String func_146776_b(long p_146776_1_)
    {
        GregorianCalendar var3 = new GregorianCalendar(TimeZone.getDefault());
        var3.setTimeInMillis(p_146776_1_);
        return SimpleDateFormat.getDateTimeInstance().format(var3.getTime());
    }

    /**
     * "Called when the screen is unloaded. Used to disable keyboard repeat events."
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.enabled)
        {
            if (p_146284_1_.id == 0)
            {
                this.mc.displayGuiScreen(this.field_146780_f);
            }
            else if (p_146284_1_.id == 1)
            {
                String var2 = "https://account.mojang.com/buy/realms?wid=" + this.field_146781_g.field_148812_a + "?pid=" + this.func_146777_g();
                Clipboard var3 = Toolkit.getDefaultToolkit().getSystemClipboard();
                var3.setContents(new StringSelection(var2), (ClipboardOwner)null);
                this.func_146779_a(var2);
            }
        }
    }

    private String func_146777_g()
    {
        String var1 = this.mc.getSession().getSessionID();
        String[] var2 = var1.split(":");
        return var2.length == 3 ? var2[2] : "";
    }

    private void func_146779_a(String p_146779_1_)
    {
        try
        {
            URI var2 = new URI(p_146779_1_);
            Class var3 = Class.forName("java.awt.Desktop");
            Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            var3.getMethod("browse", new Class[] {URI.class}).invoke(var4, new Object[] {var2});
        }
        catch (Throwable var5)
        {
            logger.error("Couldn\'t open link");
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.configure.world.subscription.title", new Object[0]), this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.subscription.start", new Object[0]), this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRendererObj, this.field_146784_s, this.width / 2 - 100, 66, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.subscription.daysleft", new Object[0]), this.width / 2 - 100, 85, 10526880);
        this.drawString(this.fontRendererObj, String.valueOf(this.field_146785_r), this.width / 2 - 100, 98, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
