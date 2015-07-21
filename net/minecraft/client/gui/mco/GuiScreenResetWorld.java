package net.minecraft.client.gui.mco;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenConfirmation;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenResetWorld extends ScreenWithCallback
{
    private static final Logger logger = LogManager.getLogger();
    private GuiScreen field_146742_f;
    private McoServer field_146743_g;
    private GuiTextField field_146749_h;
    private final int field_146750_i = 1;
    private final int field_146747_r = 2;
    private static int field_146746_s = 3;
    private WorldTemplate field_146745_t;
    private GuiButton field_146744_u;
    private static final String __OBFID = "CL_00000810";

    public GuiScreenResetWorld(GuiScreen par1GuiScreen, McoServer par2McoServer)
    {
        this.field_146742_f = par1GuiScreen;
        this.field_146743_g = par2McoServer;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_146749_h.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.field_146744_u = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, 97, 20, I18n.format("mco.configure.world.buttons.reset", new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 5, this.height / 4 + 120 + 12, 97, 20, I18n.format("gui.cancel", new Object[0])));
        this.field_146749_h = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 99, 200, 20);
        this.field_146749_h.setFocused(true);
        this.field_146749_h.func_146203_f(32);
        this.field_146749_h.setText("");

        if (this.field_146745_t == null)
        {
            this.buttonList.add(new GuiButton(field_146746_s, this.width / 2 - 100, 125, 200, 20, I18n.format("mco.template.default.name", new Object[0])));
        }
        else
        {
            this.field_146749_h.setText("");
            this.field_146749_h.func_146184_c(false);
            this.field_146749_h.setFocused(false);
            this.buttonList.add(new GuiButton(field_146746_s, this.width / 2 - 100, 125, 200, 20, I18n.format("mco.template.name", new Object[0]) + ": " + this.field_146745_t.field_148785_b));
        }
    }

    /**
     * "Called when the screen is unloaded. Used to disable keyboard repeat events."
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.field_146749_h.textboxKeyTyped(par1, par2);

        if (par2 == 28 || par2 == 156)
        {
            this.actionPerformed(this.field_146744_u);
        }
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.enabled)
        {
            if (p_146284_1_.id == 2)
            {
                this.mc.displayGuiScreen(this.field_146742_f);
            }
            else if (p_146284_1_.id == 1)
            {
                String var2 = I18n.format("mco.configure.world.reset.question.line1", new Object[0]);
                String var3 = I18n.format("mco.configure.world.reset.question.line2", new Object[0]);
                this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Warning, var2, var3, 1));
            }
            else if (p_146284_1_.id == field_146746_s)
            {
                this.mc.displayGuiScreen(new GuiScreenMcoWorldTemplate(this, this.field_146745_t));
            }
        }
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par1 && par2 == 1)
        {
            this.func_146741_h();
        }
        else
        {
            this.mc.displayGuiScreen(this);
        }
    }

    private void func_146741_h()
    {
        GuiScreenResetWorld.ResetWorldTask var1 = new GuiScreenResetWorld.ResetWorldTask(this.field_146743_g.field_148812_a, this.field_146749_h.getText(), this.field_146745_t);
        GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(this.mc, this.field_146742_f, var1);
        var2.func_146902_g();
        this.mc.displayGuiScreen(var2);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.field_146749_h.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.reset.world.title", new Object[0]), this.width / 2, 17, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.reset.world.warning", new Object[0]), this.width / 2, 56, 16711680);
        this.drawString(this.fontRendererObj, I18n.format("mco.reset.world.seed", new Object[0]), this.width / 2 - 100, 86, 10526880);
        this.field_146749_h.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }

    void func_146740_a(WorldTemplate p_146740_1_)
    {
        this.field_146745_t = p_146740_1_;
    }

    void func_146735_a(Object p_146735_1_)
    {
        this.func_146740_a((WorldTemplate)p_146735_1_);
    }

    class ResetWorldTask extends TaskLongRunning
    {
        private final long field_148422_c;
        private final String field_148420_d;
        private final WorldTemplate field_148421_e;
        private static final String __OBFID = "CL_00000811";

        public ResetWorldTask(long par2, String par4Str, WorldTemplate par5WorldTemplate)
        {
            this.field_148422_c = par2;
            this.field_148420_d = par4Str;
            this.field_148421_e = par5WorldTemplate;
        }

        public void run()
        {
            Session var1 = GuiScreenResetWorld.this.mc.getSession();
            McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
            String var3 = I18n.format("mco.reset.world.resetting.screen.title", new Object[0]);
            this.func_148417_b(var3);

            try
            {
                if (this.func_148418_c())
                {
                    return;
                }

                if (this.field_148421_e != null)
                {
                    var2.func_148696_e(this.field_148422_c, this.field_148421_e.field_148787_a);
                }
                else
                {
                    var2.func_148699_d(this.field_148422_c, this.field_148420_d);
                }

                if (this.func_148418_c())
                {
                    return;
                }

                GuiScreenResetWorld.this.mc.displayGuiScreen(GuiScreenResetWorld.this.field_146742_f);
            }
            catch (ExceptionMcoService var5)
            {
                if (this.func_148418_c())
                {
                    return;
                }

                GuiScreenResetWorld.logger.error("Couldn\'t reset world");
                this.func_148416_a(var5.toString());
            }
            catch (Exception var6)
            {
                if (this.func_148418_c())
                {
                    return;
                }

                GuiScreenResetWorld.logger.error("Couldn\'t reset world");
                this.func_148416_a(var6.toString());
            }
        }
    }
}
