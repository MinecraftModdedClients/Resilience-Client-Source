package net.minecraft.client.mco;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiScreenClientOutdated extends GuiScreen
{
    private final GuiScreen field_146901_a;
    private static final String __OBFID = "CL_00000772";

    public GuiScreenClientOutdated(GuiScreen par1GuiScreen)
    {
        this.field_146901_a = par1GuiScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Back"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        String var4 = I18n.format("mco.client.outdated.title", new Object[0]);
        String var5 = I18n.format("mco.client.outdated.msg", new Object[0]);
        this.drawCenteredString(this.fontRendererObj, var4, this.width / 2, this.height / 2 - 50, 16711680);
        this.drawCenteredString(this.fontRendererObj, var5, this.width / 2, this.height / 2 - 30, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.id == 0)
        {
            this.mc.displayGuiScreen(this.field_146901_a);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 28 || par2 == 156)
        {
            this.mc.displayGuiScreen(this.field_146901_a);
        }
    }
}
