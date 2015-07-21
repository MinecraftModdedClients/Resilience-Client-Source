package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.mco.GuiScreenCreateOnlineWorld;
import net.minecraft.client.gui.mco.GuiScreenPendingInvitation;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.McoServerList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiScreenOnlineServers extends GuiScreen
{
    private static final AtomicInteger field_146701_a = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation field_146697_g = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation field_146702_h = new ResourceLocation("textures/gui/title/minecraft.png");
    private static McoServerList field_146703_i = new McoServerList();
    private static GuiScreenRealmsPinger field_146709_r = new GuiScreenRealmsPinger();
    private static final ThreadPoolExecutor field_146708_s = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
    private GuiScreen field_146707_t;
    private GuiScreenOnlineServers.OnlineServerList field_146706_u;
    private long field_146705_v = -1L;
    private GuiButton field_146704_w;
    private GuiButton field_146712_x;
    private GuiButtonLink field_146711_y;
    private GuiButton field_146710_z;
    private String field_146698_A;
    private boolean field_146699_B;
    private List field_146700_C = Lists.newArrayList();
    private volatile int field_146694_D = 0;
    private int field_146696_E;
    private static final String __OBFID = "CL_00000792";

    public GuiScreenOnlineServers(GuiScreen par1GuiScreen)
    {
        this.field_146707_t = par1GuiScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        field_146703_i.func_148475_a(this.mc.getSession());

        if (!this.field_146699_B)
        {
            this.field_146699_B = true;
            this.field_146706_u = new GuiScreenOnlineServers.OnlineServerList();
        }
        else
        {
            this.field_146706_u.func_148346_a(this.width, this.height, 32, this.height - 64);
        }

        this.func_146688_g();
    }

    public void func_146688_g()
    {
        this.buttonList.add(this.field_146710_z = new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("mco.selectServer.play", new Object[0])));
        String var1 = this.field_146694_D > 0 ? I18n.format("mco.selectServer.create", new Object[0]) : I18n.format("mco.selectServer.buy", new Object[0]);
        this.buttonList.add(this.field_146712_x = new GuiButton(2, this.width / 2 - 48, this.height - 52, 100, 20, var1));
        this.buttonList.add(this.field_146704_w = new GuiButton(3, this.width / 2 + 58, this.height - 52, 100, 20, I18n.format("mco.selectServer.configure", new Object[0])));
        this.buttonList.add(this.field_146711_y = new GuiButtonLink(4, this.width / 2 - 154, this.height - 28, 154, 20, I18n.format("mco.selectServer.moreinfo", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 6, this.height - 28, 153, 20, I18n.format("gui.cancel", new Object[0])));
        McoServer var2 = this.func_146691_a(this.field_146705_v);
        this.field_146710_z.enabled = var2 != null && var2.field_148808_d.equals(McoServer.State.OPEN.name()) && !var2.field_148819_h;
        this.field_146704_w.enabled = var2 != null && !var2.field_148808_d.equals(McoServer.State.ADMIN_LOCK.name());

        if (var2 != null && !var2.field_148809_e.equals(this.mc.getSession().getUsername()))
        {
            this.field_146704_w.displayString = I18n.format("mco.selectServer.leave", new Object[0]);
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146696_E;

        if (field_146703_i.func_148472_a())
        {
            List var1 = field_146703_i.func_148473_c();
            Iterator var2 = var1.iterator();

            while (var2.hasNext())
            {
                McoServer var3 = (McoServer)var2.next();
                Iterator var4 = this.field_146700_C.iterator();

                while (var4.hasNext())
                {
                    McoServer var5 = (McoServer)var4.next();

                    if (var3.field_148812_a == var5.field_148812_a)
                    {
                        var3.func_148799_a(var5);
                        break;
                    }
                }
            }

            this.field_146694_D = field_146703_i.func_148469_e();
            this.field_146700_C = var1;
            field_146703_i.func_148479_b();
        }

        this.field_146712_x.displayString = this.field_146694_D > 0 ? I18n.format("mco.selectServer.create", new Object[0]) : I18n.format("mco.selectServer.buy", new Object[0]);
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
                this.func_146656_d(this.field_146705_v);
            }
            else if (p_146284_1_.id == 3)
            {
                this.func_146667_u();
            }
            else if (p_146284_1_.id == 0)
            {
                this.func_146669_s();
                this.mc.displayGuiScreen(this.field_146707_t);
            }
            else if (p_146284_1_.id == 2)
            {
                this.func_146669_s();
                this.mc.displayGuiScreen((GuiScreen)(this.field_146694_D > 0 ? new GuiScreenCreateOnlineWorld(this) : new GuiScreenBuyRealms(this)));
            }
            else if (p_146284_1_.id == 4)
            {
                this.func_146660_t();
            }
            else
            {
                this.field_146706_u.func_148357_a(p_146284_1_);
            }
        }
    }

    private void func_146669_s()
    {
        field_146703_i.func_148476_f();
        field_146709_r.func_148507_b();
    }

    private void func_146660_t()
    {
        String var1 = I18n.format("mco.more.info.question.line1", new Object[0]);
        String var2 = I18n.format("mco.more.info.question.line2", new Object[0]);
        this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var1, var2, 4));
    }

    private void func_146667_u()
    {
        McoServer var1 = this.func_146691_a(this.field_146705_v);

        if (var1 != null)
        {
            if (this.mc.getSession().getUsername().equals(var1.field_148809_e))
            {
                McoServer var2 = this.func_146677_c(var1.field_148812_a);

                if (var2 != null)
                {
                    this.func_146669_s();
                    this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this, var2));
                }
            }
            else
            {
                String var4 = I18n.format("mco.configure.world.leave.question.line1", new Object[0]);
                String var3 = I18n.format("mco.configure.world.leave.question.line2", new Object[0]);
                this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var4, var3, 3));
            }
        }
    }

    private McoServer func_146691_a(long p_146691_1_)
    {
        Iterator var3 = this.field_146700_C.iterator();
        McoServer var4;

        do
        {
            if (!var3.hasNext())
            {
                return null;
            }

            var4 = (McoServer)var3.next();
        }
        while (var4.field_148812_a != p_146691_1_);

        return var4;
    }

    private int func_146672_b(long p_146672_1_)
    {
        for (int var3 = 0; var3 < this.field_146700_C.size(); ++var3)
        {
            if (((McoServer)this.field_146700_C.get(var3)).field_148812_a == p_146672_1_)
            {
                return var3;
            }
        }

        return -1;
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par2 == 3 && par1)
        {
            (new Thread("MCO Configure Requester #" + field_146701_a.incrementAndGet())
            {
                private static final String __OBFID = "CL_00000793";
                public void run()
                {
                    try
                    {
                        McoServer var1 = GuiScreenOnlineServers.this.func_146691_a(GuiScreenOnlineServers.this.field_146705_v);

                        if (var1 != null)
                        {
                            Session var2 = GuiScreenOnlineServers.this.mc.getSession();
                            McoClient var3 = new McoClient(var2.getSessionID(), var2.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
                            GuiScreenOnlineServers.field_146703_i.func_148470_a(var1);
                            GuiScreenOnlineServers.this.field_146700_C.remove(var1);
                            var3.func_148698_c(var1.field_148812_a);
                            GuiScreenOnlineServers.field_146703_i.func_148470_a(var1);
                            GuiScreenOnlineServers.this.field_146700_C.remove(var1);
                            GuiScreenOnlineServers.this.func_146685_v();
                        }
                    }
                    catch (ExceptionMcoService var4)
                    {
                        GuiScreenOnlineServers.logger.error("Couldn\'t configure world");
                    }
                }
            }).start();
        }
        else if (par2 == 4 && par1)
        {
            this.field_146711_y.func_146138_a("http://realms.minecraft.net/");
        }

        this.mc.displayGuiScreen(this);
    }

    private void func_146685_v()
    {
        int var1 = this.func_146672_b(this.field_146705_v);

        if (this.field_146700_C.size() - 1 == var1)
        {
            --var1;
        }

        if (this.field_146700_C.size() == 0)
        {
            var1 = -1;
        }

        if (var1 >= 0 && var1 < this.field_146700_C.size())
        {
            this.field_146705_v = ((McoServer)this.field_146700_C.get(var1)).field_148812_a;
        }
    }

    public void func_146670_h()
    {
        this.field_146705_v = -1L;
    }

    private McoServer func_146677_c(long p_146677_1_)
    {
        Session var3 = this.mc.getSession();
        McoClient var4 = new McoClient(var3.getSessionID(), var3.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());

        try
        {
            return var4.func_148709_a(p_146677_1_);
        }
        catch (ExceptionMcoService var6)
        {
            logger.error("Couldn\'t get own world");
        }
        catch (IOException var7)
        {
            logger.error("Couldn\'t parse response getting own world");
        }

        return null;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 59)
        {
            this.mc.gameSettings.hideServerAddress = !this.mc.gameSettings.hideServerAddress;
            this.mc.gameSettings.saveOptions();
        }
        else
        {
            if (par2 != 28 && par2 != 156)
            {
                super.keyTyped(par1, par2);
            }
            else
            {
                this.actionPerformed((GuiButton)this.buttonList.get(0));
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.field_146698_A = null;
        this.drawDefaultBackground();
        this.field_146706_u.func_148350_a(par1, par2, par3);
        this.func_146665_b(this.width / 2 - 50, 7);
        super.drawScreen(par1, par2, par3);

        if (this.field_146698_A != null)
        {
            this.func_146658_b(this.field_146698_A, par1, par2);
        }

        this.func_146659_c(par1, par2);
    }

    private void func_146665_b(int p_146665_1_, int p_146665_2_)
    {
        this.mc.getTextureManager().bindTexture(field_146702_h);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.drawTexturedModalRect(p_146665_1_ * 2, p_146665_2_ * 2, 0, 97, 200, 50);
        GL11.glPopMatrix();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);

        if (this.func_146662_d(par1, par2) && field_146703_i.func_148468_d() != 0)
        {
            this.func_146669_s();
            GuiScreenPendingInvitation var4 = new GuiScreenPendingInvitation(this.field_146707_t);
            this.mc.displayGuiScreen(var4);
        }
    }

    private void func_146659_c(int p_146659_1_, int p_146659_2_)
    {
        int var3 = field_146703_i.func_148468_d();
        boolean var4 = this.func_146662_d(p_146659_1_, p_146659_2_);
        this.mc.getTextureManager().bindTexture(field_146697_g);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPushMatrix();
        this.drawTexturedModalRect(this.width / 2 + 58, 12, var4 ? 166 : 182, 22, 16, 16);
        GL11.glPopMatrix();
        int var5;
        int var6;

        if (var3 != 0)
        {
            var5 = 198 + (Math.min(var3, 6) - 1) * 8;
            var6 = (int)(Math.max(0.0F, Math.max(MathHelper.sin((float)(10 + this.field_146696_E) * 0.57F), MathHelper.cos((float)this.field_146696_E * 0.35F))) * -6.0F);
            this.mc.getTextureManager().bindTexture(field_146697_g);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();
            this.drawTexturedModalRect(this.width / 2 + 58 + 4, 16 + var6, var5, 22, 8, 8);
            GL11.glPopMatrix();
        }

        if (var4)
        {
            var5 = p_146659_1_ + 12;
            var6 = p_146659_2_ - 12;
            String var7 = "";

            if (var3 != 0)
            {
                var7 = I18n.format("mco.invites.pending", new Object[0]);
            }
            else
            {
                var7 = I18n.format("mco.invites.nopending", new Object[0]);
            }

            int var8 = this.fontRendererObj.getStringWidth(var7);
            this.drawGradientRect(var5 - 3, var6 - 3, var5 + var8 + 3, var6 + 8 + 3, -1073741824, -1073741824);
            this.fontRendererObj.drawStringWithShadow(var7, var5, var6, -1);
        }
    }

    private boolean func_146662_d(int p_146662_1_, int p_146662_2_)
    {
        int var3 = this.width / 2 + 56;
        int var4 = this.width / 2 + 78;
        byte var5 = 13;
        byte var6 = 27;
        return var3 <= p_146662_1_ && p_146662_1_ <= var4 && var5 <= p_146662_2_ && p_146662_2_ <= var6;
    }

    private void func_146656_d(long p_146656_1_)
    {
        McoServer var3 = this.func_146691_a(p_146656_1_);

        if (var3 != null)
        {
            this.func_146669_s();
            GuiScreenLongRunningTask var4 = new GuiScreenLongRunningTask(this.mc, this, new TaskOnlineConnect(this, var3));
            var4.func_146902_g();
            this.mc.displayGuiScreen(var4);
        }
    }

    private void func_146661_c(int p_146661_1_, int p_146661_2_, int p_146661_3_, int p_146661_4_)
    {
        this.mc.getTextureManager().bindTexture(field_146697_g);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.drawTexturedModalRect(p_146661_1_ * 2, p_146661_2_ * 2, 191, 0, 16, 15);
        GL11.glPopMatrix();

        if (p_146661_3_ >= p_146661_1_ && p_146661_3_ <= p_146661_1_ + 9 && p_146661_4_ >= p_146661_2_ && p_146661_4_ <= p_146661_2_ + 9)
        {
            this.field_146698_A = I18n.format("mco.selectServer.expired", new Object[0]);
        }
    }

    private void func_146687_b(int p_146687_1_, int p_146687_2_, int p_146687_3_, int p_146687_4_, int p_146687_5_)
    {
        if (this.field_146696_E % 20 < 10)
        {
            this.mc.getTextureManager().bindTexture(field_146697_g);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            this.drawTexturedModalRect(p_146687_1_ * 2, p_146687_2_ * 2, 207, 0, 16, 15);
            GL11.glPopMatrix();
        }

        if (p_146687_3_ >= p_146687_1_ && p_146687_3_ <= p_146687_1_ + 9 && p_146687_4_ >= p_146687_2_ && p_146687_4_ <= p_146687_2_ + 9)
        {
            if (p_146687_5_ == 0)
            {
                this.field_146698_A = I18n.format("mco.selectServer.expires.soon", new Object[0]);
            }
            else if (p_146687_5_ == 1)
            {
                this.field_146698_A = I18n.format("mco.selectServer.expires.day", new Object[0]);
            }
            else
            {
                this.field_146698_A = I18n.format("mco.selectServer.expires.days", new Object[] {Integer.valueOf(p_146687_5_)});
            }
        }
    }

    private void func_146683_d(int p_146683_1_, int p_146683_2_, int p_146683_3_, int p_146683_4_)
    {
        this.mc.getTextureManager().bindTexture(field_146697_g);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.drawTexturedModalRect(p_146683_1_ * 2, p_146683_2_ * 2, 207, 0, 16, 15);
        GL11.glPopMatrix();

        if (p_146683_3_ >= p_146683_1_ && p_146683_3_ <= p_146683_1_ + 9 && p_146683_4_ >= p_146683_2_ && p_146683_4_ <= p_146683_2_ + 9)
        {
            this.field_146698_A = I18n.format("mco.selectServer.open", new Object[0]);
        }
    }

    private void func_146671_e(int p_146671_1_, int p_146671_2_, int p_146671_3_, int p_146671_4_)
    {
        this.mc.getTextureManager().bindTexture(field_146697_g);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.drawTexturedModalRect(p_146671_1_ * 2, p_146671_2_ * 2, 223, 0, 16, 15);
        GL11.glPopMatrix();

        if (p_146671_3_ >= p_146671_1_ && p_146671_3_ <= p_146671_1_ + 9 && p_146671_4_ >= p_146671_2_ && p_146671_4_ <= p_146671_2_ + 9)
        {
            this.field_146698_A = I18n.format("mco.selectServer.closed", new Object[0]);
        }
    }

    private void func_146666_f(int p_146666_1_, int p_146666_2_, int p_146666_3_, int p_146666_4_)
    {
        this.mc.getTextureManager().bindTexture(field_146697_g);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.drawTexturedModalRect(p_146666_1_ * 2, p_146666_2_ * 2, 223, 0, 16, 15);
        GL11.glPopMatrix();

        if (p_146666_3_ >= p_146666_1_ && p_146666_3_ <= p_146666_1_ + 9 && p_146666_4_ >= p_146666_2_ && p_146666_4_ <= p_146666_2_ + 9)
        {
            this.field_146698_A = I18n.format("mco.selectServer.locked", new Object[0]);
        }
    }

    protected void func_146658_b(String p_146658_1_, int p_146658_2_, int p_146658_3_)
    {
        if (p_146658_1_ != null)
        {
            int var4 = p_146658_2_ + 12;
            int var5 = p_146658_3_ - 12;
            int var6 = this.fontRendererObj.getStringWidth(p_146658_1_);
            this.drawGradientRect(var4 - 3, var5 - 3, var4 + var6 + 3, var5 + 8 + 3, -1073741824, -1073741824);
            this.fontRendererObj.drawStringWithShadow(p_146658_1_, var4, var5, -1);
        }
    }

    class OnlineServerList extends GuiScreenSelectLocation
    {
        private static final String __OBFID = "CL_00000794";

        public OnlineServerList()
        {
            super(GuiScreenOnlineServers.this.mc, GuiScreenOnlineServers.this.width, GuiScreenOnlineServers.this.height, 32, GuiScreenOnlineServers.this.height - 64, 36);
        }

        protected int func_148355_a()
        {
            return GuiScreenOnlineServers.this.field_146700_C.size() + 1;
        }

        protected void func_148352_a(int p_148352_1_, boolean p_148352_2_)
        {
            if (p_148352_1_ < GuiScreenOnlineServers.this.field_146700_C.size())
            {
                McoServer var3 = (McoServer)GuiScreenOnlineServers.this.field_146700_C.get(p_148352_1_);
                GuiScreenOnlineServers.this.field_146705_v = var3.field_148812_a;

                if (!GuiScreenOnlineServers.this.mc.getSession().getUsername().equals(var3.field_148809_e))
                {
                    GuiScreenOnlineServers.this.field_146704_w.displayString = I18n.format("mco.selectServer.leave", new Object[0]);
                }
                else
                {
                    GuiScreenOnlineServers.this.field_146704_w.displayString = I18n.format("mco.selectServer.configure", new Object[0]);
                }

                GuiScreenOnlineServers.this.field_146704_w.enabled = !var3.field_148808_d.equals(McoServer.State.ADMIN_LOCK.name());
                GuiScreenOnlineServers.this.field_146710_z.enabled = var3.field_148808_d.equals(McoServer.State.OPEN.name()) && !var3.field_148819_h;

                if (p_148352_2_ && GuiScreenOnlineServers.this.field_146710_z.enabled)
                {
                    GuiScreenOnlineServers.this.func_146656_d(GuiScreenOnlineServers.this.field_146705_v);
                }
            }
        }

        protected boolean func_148356_a(int p_148356_1_)
        {
            return p_148356_1_ == GuiScreenOnlineServers.this.func_146672_b(GuiScreenOnlineServers.this.field_146705_v);
        }

        protected boolean func_148349_b(int p_148349_1_)
        {
            try
            {
                return p_148349_1_ >= 0 && p_148349_1_ < GuiScreenOnlineServers.this.field_146700_C.size() && ((McoServer)GuiScreenOnlineServers.this.field_146700_C.get(p_148349_1_)).field_148809_e.toLowerCase().equals(GuiScreenOnlineServers.this.mc.getSession().getUsername());
            }
            catch (Exception var3)
            {
                return false;
            }
        }

        protected int func_148351_b()
        {
            return this.func_148355_a() * 36;
        }

        protected void func_148358_c()
        {
            GuiScreenOnlineServers.this.drawDefaultBackground();
        }

        protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_)
        {
            if (p_148348_1_ < GuiScreenOnlineServers.this.field_146700_C.size())
            {
                this.func_148390_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
            }
        }

        private void func_148390_b(int p_148390_1_, int p_148390_2_, int p_148390_3_, int p_148390_4_, Tessellator p_148390_5_)
        {
            final McoServer var6 = (McoServer)GuiScreenOnlineServers.this.field_146700_C.get(p_148390_1_);

            if (!var6.field_148814_o)
            {
                var6.field_148814_o = true;
                GuiScreenOnlineServers.field_146708_s.submit(new Runnable()
                {
                    private static final String __OBFID = "CL_00000795";
                    public void run()
                    {
                        try
                        {
                            GuiScreenOnlineServers.field_146709_r.func_148506_a(var6);
                        }
                        catch (UnknownHostException var2)
                        {
                            GuiScreenOnlineServers.logger.error("Pinger: Could not resolve host");
                        }
                    }
                });
            }

            GuiScreenOnlineServers.this.drawString(GuiScreenOnlineServers.this.fontRendererObj, var6.func_148801_b(), p_148390_2_ + 2, p_148390_3_ + 1, 16777215);
            short var7 = 207;
            byte var8 = 1;

            if (var6.field_148819_h)
            {
                GuiScreenOnlineServers.this.func_146661_c(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
            }
            else if (var6.field_148808_d.equals(McoServer.State.CLOSED.name()))
            {
                GuiScreenOnlineServers.this.func_146671_e(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
            }
            else if (var6.field_148809_e.equals(GuiScreenOnlineServers.this.mc.getSession().getUsername()) && var6.field_148818_k < 7)
            {
                this.func_148389_a(p_148390_1_, p_148390_2_ - 14, p_148390_3_, var6);
                GuiScreenOnlineServers.this.func_146687_b(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f, var6.field_148818_k);
            }
            else if (var6.field_148808_d.equals(McoServer.State.OPEN.name()))
            {
                GuiScreenOnlineServers.this.func_146683_d(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
                this.func_148389_a(p_148390_1_, p_148390_2_ - 14, p_148390_3_, var6);
            }
            else if (var6.field_148808_d.equals(McoServer.State.ADMIN_LOCK.name()))
            {
                GuiScreenOnlineServers.this.func_146666_f(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
            }

            GuiScreenOnlineServers.this.drawString(GuiScreenOnlineServers.this.fontRendererObj, var6.field_148813_n, p_148390_2_ + 200 - GuiScreenOnlineServers.this.fontRendererObj.getStringWidth(var6.field_148813_n), p_148390_3_ + 1, 8421504);
            GuiScreenOnlineServers.this.drawString(GuiScreenOnlineServers.this.fontRendererObj, var6.func_148800_a(), p_148390_2_ + 2, p_148390_3_ + 12, 7105644);
            GuiScreenOnlineServers.this.drawString(GuiScreenOnlineServers.this.fontRendererObj, var6.field_148809_e, p_148390_2_ + 2, p_148390_3_ + 12 + 11, 5000268);
        }

        private void func_148389_a(int p_148389_1_, int p_148389_2_, int p_148389_3_, McoServer p_148389_4_)
        {
            if (p_148389_4_.field_148807_g != null)
            {
                if (p_148389_4_.field_148816_m != null)
                {
                    GuiScreenOnlineServers.this.drawString(GuiScreenOnlineServers.this.fontRendererObj, p_148389_4_.field_148816_m, p_148389_2_ + 215 - GuiScreenOnlineServers.this.fontRendererObj.getStringWidth(p_148389_4_.field_148816_m), p_148389_3_ + 1, 8421504);
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GuiScreenOnlineServers.this.mc.getTextureManager().bindTexture(Gui.icons);
            }
        }
    }
}
