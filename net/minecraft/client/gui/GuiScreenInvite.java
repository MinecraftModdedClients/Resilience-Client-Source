package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenInvite extends GuiScreen
{
    private static final Logger logger = LogManager.getLogger();
    private GuiTextField field_146921_f;
    private McoServer field_146923_g;
    private final GuiScreen field_146929_h;
    private final GuiScreenConfigureWorld field_146930_i;
    private final int field_146927_r = 0;
    private final int field_146926_s = 1;
    private String field_146925_t = "Could not invite the provided name";
    private String field_146924_u;
    private boolean field_146922_v;
    private static final String __OBFID = "CL_00000780";

    public GuiScreenInvite(GuiScreen par1GuiScreen, GuiScreenConfigureWorld par2GuiScreenConfigureWorld, McoServer par3McoServer)
    {
        this.field_146929_h = par1GuiScreen;
        this.field_146930_i = par2GuiScreenConfigureWorld;
        this.field_146923_g = par3McoServer;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_146921_f.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("mco.configure.world.buttons.invite", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        this.field_146921_f = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
        this.field_146921_f.setFocused(true);
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
            if (p_146284_1_.id == 1)
            {
                this.mc.displayGuiScreen(this.field_146930_i);
            }
            else if (p_146284_1_.id == 0)
            {
                Session var2 = this.mc.getSession();
                McoClient var3 = new McoClient(var2.getSessionID(), var2.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());

                if (this.field_146921_f.getText() == null || this.field_146921_f.getText().isEmpty())
                {
                    return;
                }

                try
                {
                    McoServer var4 = var3.func_148697_b(this.field_146923_g.field_148812_a, this.field_146921_f.getText());

                    if (var4 != null)
                    {
                        this.field_146923_g.field_148806_f = var4.field_148806_f;
                        this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this.field_146929_h, this.field_146923_g));
                    }
                    else
                    {
                        this.func_146920_a(this.field_146925_t);
                    }
                }
                catch (ExceptionMcoService var5)
                {
                    logger.error("Couldn\'t invite user");
                    this.func_146920_a(var5.field_148829_b);
                }
                catch (IOException var6)
                {
                    logger.error("Couldn\'t parse response inviting user", var6);
                    this.func_146920_a(this.field_146925_t);
                }
            }
        }
    }

    private void func_146920_a(String p_146920_1_)
    {
        this.field_146922_v = true;
        this.field_146924_u = p_146920_1_;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.field_146921_f.textboxKeyTyped(par1, par2);

        if (par2 == 15)
        {
            if (this.field_146921_f.isFocused())
            {
                this.field_146921_f.setFocused(false);
            }
            else
            {
                this.field_146921_f.setFocused(true);
            }
        }

        if (par2 == 28 || par2 == 156)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.field_146921_f.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.invite.profile.name", new Object[0]), this.width / 2 - 100, 53, 10526880);

        if (this.field_146922_v)
        {
            this.drawCenteredString(this.fontRendererObj, this.field_146924_u, this.width / 2, 100, 16711680);
        }

        this.field_146921_f.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
