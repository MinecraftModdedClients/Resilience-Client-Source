package net.minecraft.client.gui;

import java.io.UnsupportedEncodingException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.mco.GuiScreenResetWorld;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenEditOnlineWorld extends GuiScreen
{
    private static final Logger logger = LogManager.getLogger();
    private GuiScreen field_146855_f;
    private GuiScreen field_146857_g;
    private GuiTextField field_146863_h;
    private GuiTextField field_146864_i;
    private McoServer field_146861_r;
    private GuiButton field_146860_s;
    private int field_146859_t;
    private int field_146858_u;
    private int field_146856_v;
    private GuiScreenOnlineServersSubscreen field_146854_w;
    private static final String __OBFID = "CL_00000779";

    public GuiScreenEditOnlineWorld(GuiScreen par1GuiScreen, GuiScreen par2GuiScreen, McoServer par3McoServer)
    {
        this.field_146855_f = par1GuiScreen;
        this.field_146857_g = par2GuiScreen;
        this.field_146861_r = par3McoServer;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_146864_i.updateCursorCounter();
        this.field_146863_h.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146859_t = this.width / 4;
        this.field_146858_u = this.width / 4 - 2;
        this.field_146856_v = this.width / 2 + 4;
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.field_146860_s = new GuiButton(0, this.field_146859_t, this.height / 4 + 120 + 22, this.field_146858_u, 20, I18n.format("mco.configure.world.buttons.done", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.field_146856_v, this.height / 4 + 120 + 22, this.field_146858_u, 20, I18n.format("gui.cancel", new Object[0])));
        this.field_146864_i = new GuiTextField(this.fontRendererObj, this.field_146859_t, 56, 212, 20);
        this.field_146864_i.setFocused(true);
        this.field_146864_i.func_146203_f(32);
        this.field_146864_i.setText(this.field_146861_r.func_148801_b());
        this.field_146863_h = new GuiTextField(this.fontRendererObj, this.field_146859_t, 96, 212, 20);
        this.field_146863_h.func_146203_f(32);
        this.field_146863_h.setText(this.field_146861_r.func_148800_a());
        this.field_146854_w = new GuiScreenOnlineServersSubscreen(this.width, this.height, this.field_146859_t, 122, this.field_146861_r.field_148820_i, this.field_146861_r.field_148817_j);
        this.buttonList.addAll(this.field_146854_w.field_148405_a);
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
                this.mc.displayGuiScreen(this.field_146855_f);
            }
            else if (p_146284_1_.id == 0)
            {
                this.func_146853_g();
            }
            else if (p_146284_1_.id == 2)
            {
                this.mc.displayGuiScreen(new GuiScreenResetWorld(this, this.field_146861_r));
            }
            else
            {
                this.field_146854_w.func_148397_a(p_146284_1_);
            }
        }
    }

    private void func_146853_g()
    {
        Session var1 = this.mc.getSession();
        McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());

        try
        {
            String var3 = this.field_146863_h.getText() != null && !this.field_146863_h.getText().trim().equals("") ? this.field_146863_h.getText() : null;
            var2.func_148689_a(this.field_146861_r.field_148812_a, this.field_146864_i.getText(), var3, this.field_146854_w.field_148402_e, this.field_146854_w.field_148399_f);
            this.field_146861_r.func_148803_a(this.field_146864_i.getText());
            this.field_146861_r.func_148804_b(this.field_146863_h.getText());
            this.field_146861_r.field_148820_i = this.field_146854_w.field_148402_e;
            this.field_146861_r.field_148817_j = this.field_146854_w.field_148399_f;
            this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this.field_146857_g, this.field_146861_r));
        }
        catch (ExceptionMcoService var4)
        {
            logger.error("Couldn\'t edit world");
        }
        catch (UnsupportedEncodingException var5)
        {
            logger.error("Couldn\'t edit world");
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.field_146864_i.textboxKeyTyped(par1, par2);
        this.field_146863_h.textboxKeyTyped(par1, par2);

        if (par2 == 15)
        {
            this.field_146864_i.setFocused(!this.field_146864_i.isFocused());
            this.field_146863_h.setFocused(!this.field_146863_h.isFocused());
        }

        if (par2 == 28 || par2 == 156)
        {
            this.func_146853_g();
        }

        this.field_146860_s.enabled = this.field_146864_i.getText() != null && !this.field_146864_i.getText().trim().equals("");
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.field_146863_h.mouseClicked(par1, par2, par3);
        this.field_146864_i.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.configure.world.edit.title", new Object[0]), this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.name", new Object[0]), this.field_146859_t, 43, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.description", new Object[0]), this.field_146859_t, 84, 10526880);
        this.field_146864_i.drawTextBox();
        this.field_146863_h.drawTextBox();
        this.field_146854_w.func_148394_a(this, this.fontRendererObj);
        super.drawScreen(par1, par2, par3);
    }
}
