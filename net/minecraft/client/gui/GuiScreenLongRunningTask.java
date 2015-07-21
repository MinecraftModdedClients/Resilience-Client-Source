package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class GuiScreenLongRunningTask extends GuiScreen
{
    private static final AtomicInteger field_146908_f = new AtomicInteger(0);
    private final int field_146910_g = 666;
    private final int field_146917_h = 667;
    private final GuiScreen field_146919_i;
    private final Thread field_146914_r;
    private volatile String field_146913_s = "";
    private volatile boolean field_146912_t;
    private volatile String field_146911_u;
    private volatile boolean field_146909_v;
    private int field_146907_w;
    private TaskLongRunning field_146918_x;
    private int field_146916_y = 212;
    public static final String[] field_146915_a = new String[] {"\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _"};
    private static final String __OBFID = "CL_00000783";

    public GuiScreenLongRunningTask(Minecraft par1Minecraft, GuiScreen par2GuiScreen, TaskLongRunning par3TaskLongRunning)
    {
        super.buttonList = Collections.synchronizedList(new ArrayList());
        this.mc = par1Minecraft;
        this.field_146919_i = par2GuiScreen;
        this.field_146918_x = par3TaskLongRunning;
        par3TaskLongRunning.func_148412_a(this);
        this.field_146914_r = new Thread(par3TaskLongRunning, "MCO Task #" + field_146908_f.incrementAndGet());
    }

    public void func_146902_g()
    {
        this.field_146914_r.start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146907_w;
        this.field_146918_x.func_148414_a();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.field_146918_x.func_148411_d();
        this.buttonList.add(new GuiButton(666, this.width / 2 - this.field_146916_y / 2, 170, this.field_146916_y, 20, I18n.format("gui.cancel", new Object[0])));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.id == 666 || p_146284_1_.id == 667)
        {
            this.field_146909_v = true;
            this.mc.displayGuiScreen(this.field_146919_i);
        }

        this.field_146918_x.func_148415_a(p_146284_1_);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146913_s, this.width / 2, this.height / 2 - 50, 16777215);
        this.drawCenteredString(this.fontRendererObj, "", this.width / 2, this.height / 2 - 10, 16777215);

        if (!this.field_146912_t)
        {
            this.drawCenteredString(this.fontRendererObj, field_146915_a[this.field_146907_w % field_146915_a.length], this.width / 2, this.height / 2 + 15, 8421504);
        }

        if (this.field_146912_t)
        {
            this.drawCenteredString(this.fontRendererObj, this.field_146911_u, this.width / 2, this.height / 2 + 15, 16711680);
        }

        super.drawScreen(par1, par2, par3);
    }

    public void func_146905_a(String p_146905_1_)
    {
        this.field_146912_t = true;
        this.field_146911_u = p_146905_1_;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(667, this.width / 2 - this.field_146916_y / 2, this.height / 4 + 120 + 12, I18n.format("gui.back", new Object[0])));
    }

    public Minecraft func_146903_h()
    {
        return this.mc;
    }

    public void func_146906_b(String p_146906_1_)
    {
        this.field_146913_s = p_146906_1_;
    }

    public boolean func_146904_i()
    {
        return this.field_146909_v;
    }
}
