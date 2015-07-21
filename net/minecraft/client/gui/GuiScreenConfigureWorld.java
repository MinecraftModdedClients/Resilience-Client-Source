package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.mco.GuiScreenBackup;
import net.minecraft.client.gui.mco.GuiScreenResetWorld;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenConfigureWorld extends GuiScreen
{
    private static final Logger logger = LogManager.getLogger();
    private final GuiScreen field_146884_f;
    private McoServer field_146885_g;
    private GuiScreenConfigureWorld.SelectionListInvited field_146890_h;
    private int field_146891_i;
    private int field_146897_r;
    private int field_146896_s;
    private int field_146895_t = -1;
    private String field_146894_u;
    private GuiButton field_146893_v;
    private GuiButton field_146892_w;
    private GuiButton field_146900_x;
    private GuiButton field_146899_y;
    private GuiButton field_146898_z;
    private GuiButton field_146886_A;
    private GuiButton field_146887_B;
    private GuiButton field_146888_C;
    private boolean field_146883_D;
    private static final String __OBFID = "CL_00000773";

    public GuiScreenConfigureWorld(GuiScreen par1GuiScreen, McoServer par2McoServer)
    {
        this.field_146884_f = par1GuiScreen;
        this.field_146885_g = par2McoServer;
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
        this.field_146891_i = this.width / 2 - 200;
        this.field_146897_r = 180;
        this.field_146896_s = this.width / 2;
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();

        if (this.field_146885_g.field_148808_d.equals("CLOSED"))
        {
            this.buttonList.add(this.field_146893_v = new GuiButton(0, this.field_146891_i, this.func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.open", new Object[0])));
            this.field_146893_v.enabled = !this.field_146885_g.field_148819_h;
        }
        else
        {
            this.buttonList.add(this.field_146892_w = new GuiButton(1, this.field_146891_i, this.func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.close", new Object[0])));
            this.field_146892_w.enabled = !this.field_146885_g.field_148819_h;
        }

        this.buttonList.add(this.field_146887_B = new GuiButton(7, this.field_146891_i + this.field_146897_r / 2 + 2, this.func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.subscription", new Object[0])));
        this.buttonList.add(this.field_146900_x = new GuiButton(5, this.field_146891_i, this.func_146873_a(10), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.edit", new Object[0])));
        this.buttonList.add(this.field_146899_y = new GuiButton(6, this.field_146891_i + this.field_146897_r / 2 + 2, this.func_146873_a(10), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.reset", new Object[0])));
        this.buttonList.add(this.field_146898_z = new GuiButton(4, this.field_146896_s, this.func_146873_a(10), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.invite", new Object[0])));
        this.buttonList.add(this.field_146886_A = new GuiButton(3, this.field_146896_s + this.field_146897_r / 2 + 2, this.func_146873_a(10), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.uninvite", new Object[0])));
        this.buttonList.add(this.field_146888_C = new GuiButton(8, this.field_146896_s, this.func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("mco.configure.world.buttons.backup", new Object[0])));
        this.buttonList.add(new GuiButton(10, this.field_146896_s + this.field_146897_r / 2 + 2, this.func_146873_a(12), this.field_146897_r / 2 - 2, 20, I18n.format("gui.back", new Object[0])));
        this.field_146890_h = new GuiScreenConfigureWorld.SelectionListInvited();
        this.field_146900_x.enabled = !this.field_146885_g.field_148819_h;
        this.field_146899_y.enabled = !this.field_146885_g.field_148819_h;
        this.field_146898_z.enabled = !this.field_146885_g.field_148819_h;
        this.field_146886_A.enabled = !this.field_146885_g.field_148819_h;
        this.field_146888_C.enabled = !this.field_146885_g.field_148819_h;
    }

    private int func_146873_a(int p_146873_1_)
    {
        return 40 + p_146873_1_ * 13;
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
            if (p_146284_1_.id == 10)
            {
                if (this.field_146883_D)
                {
                    ((GuiScreenOnlineServers)this.field_146884_f).func_146670_h();
                }

                this.mc.displayGuiScreen(this.field_146884_f);
            }
            else if (p_146284_1_.id == 5)
            {
                this.mc.displayGuiScreen(new GuiScreenEditOnlineWorld(this, this.field_146884_f, this.field_146885_g));
            }
            else if (p_146284_1_.id == 1)
            {
                String var2 = I18n.format("mco.configure.world.close.question.line1", new Object[0]);
                String var3 = I18n.format("mco.configure.world.close.question.line2", new Object[0]);
                this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var2, var3, 1));
            }
            else if (p_146284_1_.id == 0)
            {
                this.func_146876_g();
            }
            else if (p_146284_1_.id == 4)
            {
                this.mc.displayGuiScreen(new GuiScreenInvite(this.field_146884_f, this, this.field_146885_g));
            }
            else if (p_146284_1_.id == 3)
            {
                this.func_146877_i();
            }
            else if (p_146284_1_.id == 6)
            {
                this.mc.displayGuiScreen(new GuiScreenResetWorld(this, this.field_146885_g));
            }
            else if (p_146284_1_.id == 7)
            {
                this.mc.displayGuiScreen(new GuiScreenSubscription(this, this.field_146885_g));
            }
            else if (p_146284_1_.id == 8)
            {
                this.mc.displayGuiScreen(new GuiScreenBackup(this, this.field_146885_g.field_148812_a));
            }
        }
    }

    private void func_146876_g()
    {
        Session var1 = this.mc.getSession();
        McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());

        try
        {
            Boolean var3 = var2.func_148692_e(this.field_146885_g.field_148812_a);

            if (var3.booleanValue())
            {
                this.field_146883_D = true;
                this.field_146885_g.field_148808_d = "OPEN";
                this.initGui();
            }
        }
        catch (ExceptionMcoService var4)
        {
            logger.error("Couldn\'t open world");
        }
        catch (IOException var5)
        {
            logger.error("Could not parse response opening world");
        }
    }

    private void func_146882_h()
    {
        Session var1 = this.mc.getSession();
        McoClient var2 = new McoClient(var1.getSessionID(), var1.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());

        try
        {
            boolean var3 = var2.func_148700_f(this.field_146885_g.field_148812_a).booleanValue();

            if (var3)
            {
                this.field_146883_D = true;
                this.field_146885_g.field_148808_d = "CLOSED";
                this.initGui();
            }
        }
        catch (ExceptionMcoService var4)
        {
            logger.error("Couldn\'t close world");
        }
        catch (IOException var5)
        {
            logger.error("Could not parse response closing world");
        }
    }

    private void func_146877_i()
    {
        if (this.field_146895_t >= 0 && this.field_146895_t < this.field_146885_g.field_148806_f.size())
        {
            this.field_146894_u = (String)this.field_146885_g.field_148806_f.get(this.field_146895_t);
            GuiYesNo var1 = new GuiYesNo(this, "Question", I18n.format("mco.configure.world.uninvite.question", new Object[0]) + " \'" + this.field_146894_u + "\'", 3);
            this.mc.displayGuiScreen(var1);
        }
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par2 == 3)
        {
            if (par1)
            {
                Session var3 = this.mc.getSession();
                McoClient var4 = new McoClient(var3.getSessionID(), var3.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());

                try
                {
                    var4.func_148694_a(this.field_146885_g.field_148812_a, this.field_146894_u);
                }
                catch (ExceptionMcoService var6)
                {
                    logger.error("Couldn\'t uninvite user");
                }

                this.func_146875_d(this.field_146895_t);
            }

            this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this.field_146884_f, this.field_146885_g));
        }

        if (par2 == 1)
        {
            if (par1)
            {
                this.func_146882_h();
            }

            this.mc.displayGuiScreen(this);
        }
    }

    private void func_146875_d(int p_146875_1_)
    {
        this.field_146885_g.field_148806_f.remove(p_146875_1_);
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
        this.field_146890_h.func_148446_a(par1, par2, par3);
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.configure.world.title", new Object[0]), this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.name", new Object[0]), this.field_146891_i, this.func_146873_a(1), 10526880);
        this.drawString(this.fontRendererObj, this.field_146885_g.func_148801_b(), this.field_146891_i, this.func_146873_a(2), 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.description", new Object[0]), this.field_146891_i, this.func_146873_a(4), 10526880);
        this.drawString(this.fontRendererObj, this.field_146885_g.func_148800_a(), this.field_146891_i, this.func_146873_a(5), 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.status", new Object[0]), this.field_146891_i, this.func_146873_a(7), 10526880);
        this.drawString(this.fontRendererObj, this.func_146870_p(), this.field_146891_i, this.func_146873_a(8), 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.invited", new Object[0]), this.field_146896_s, this.func_146873_a(1), 10526880);
        super.drawScreen(par1, par2, par3);
    }

    private String func_146870_p()
    {
        if (this.field_146885_g.field_148819_h)
        {
            return "Expired";
        }
        else
        {
            String var1 = this.field_146885_g.field_148808_d.toLowerCase();
            return Character.toUpperCase(var1.charAt(0)) + var1.substring(1);
        }
    }

    class SelectionListInvited extends SelectionListBase
    {
        private static final String __OBFID = "CL_00000775";

        public SelectionListInvited()
        {
            super(GuiScreenConfigureWorld.this.mc, GuiScreenConfigureWorld.this.field_146896_s, GuiScreenConfigureWorld.this.func_146873_a(2), GuiScreenConfigureWorld.this.field_146897_r, GuiScreenConfigureWorld.this.func_146873_a(9) - GuiScreenConfigureWorld.this.func_146873_a(2), 12);
        }

        protected int func_148443_a()
        {
            return GuiScreenConfigureWorld.this.field_146885_g.field_148806_f.size() + 1;
        }

        protected void func_148449_a(int p_148449_1_, boolean p_148449_2_)
        {
            if (p_148449_1_ < GuiScreenConfigureWorld.this.field_146885_g.field_148806_f.size())
            {
                GuiScreenConfigureWorld.this.field_146895_t = p_148449_1_;
            }
        }

        protected boolean func_148444_a(int p_148444_1_)
        {
            return p_148444_1_ == GuiScreenConfigureWorld.this.field_146895_t;
        }

        protected int func_148447_b()
        {
            return this.func_148443_a() * 12;
        }

        protected void func_148445_c() {}

        protected void func_148442_a(int p_148442_1_, int p_148442_2_, int p_148442_3_, int p_148442_4_, Tessellator p_148442_5_)
        {
            if (p_148442_1_ < GuiScreenConfigureWorld.this.field_146885_g.field_148806_f.size())
            {
                this.func_148463_b(p_148442_1_, p_148442_2_, p_148442_3_, p_148442_4_, p_148442_5_);
            }
        }

        private void func_148463_b(int p_148463_1_, int p_148463_2_, int p_148463_3_, int p_148463_4_, Tessellator p_148463_5_)
        {
            String var6 = (String)GuiScreenConfigureWorld.this.field_146885_g.field_148806_f.get(p_148463_1_);
            GuiScreenConfigureWorld.this.drawString(GuiScreenConfigureWorld.this.fontRendererObj, var6, p_148463_2_ + 2, p_148463_3_ + 1, 16777215);
        }
    }
}
