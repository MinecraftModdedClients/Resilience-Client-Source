package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBuyRealms extends GuiScreen
{
    private static final Logger logger = LogManager.getLogger();
    private GuiScreen field_146817_f;
    private static int field_146818_g = 111;
    private volatile String field_146820_h = "";
    private static final String __OBFID = "CL_00000770";

    public GuiScreenBuyRealms(GuiScreen p_i45035_1_)
    {
        this.field_146817_f = p_i45035_1_;
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
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        short var1 = 212;
        this.buttonList.add(new GuiButton(field_146818_g, this.width / 2 - var1 / 2, 180, var1, 20, I18n.format("gui.back", new Object[0])));
        this.func_146816_h();
    }

    private void func_146816_h()
    {
        Session var1 = this.mc.getSession();
        final McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
        (new Thread()
        {
            private static final String __OBFID = "CL_00000771";
            public void run()
            {
                try
                {
                    GuiScreenBuyRealms.this.field_146820_h = var2.func_148690_i();
                }
                catch (ExceptionMcoService var2x)
                {
                    GuiScreenBuyRealms.logger.error("Could not get stat");
                }
            }
        }).start();
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
            if (p_146284_1_.id == field_146818_g)
            {
                this.mc.displayGuiScreen(this.field_146817_f);
            }
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
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.buy.realms.title", new Object[0]), this.width / 2, 11, 16777215);
        String[] var4 = this.field_146820_h.split("\n");
        int var5 = 52;
        String[] var6 = var4;
        int var7 = var4.length;

        for (int var8 = 0; var8 < var7; ++var8)
        {
            String var9 = var6[var8];
            this.drawCenteredString(this.fontRendererObj, var9, this.width / 2, var5, 10526880);
            var5 += 18;
        }

        super.drawScreen(par1, par2, par3);
    }
}
