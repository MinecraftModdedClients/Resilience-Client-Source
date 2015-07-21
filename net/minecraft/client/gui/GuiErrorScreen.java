package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

public class GuiErrorScreen extends GuiScreen
{
    private String field_146313_a;
    private String field_146312_f;
    private static final String __OBFID = "CL_00000696";

    public GuiErrorScreen(String par1Str, String par2Str)
    {
        this.field_146313_a = par1Str;
        this.field_146312_f = par2Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 140, I18n.format("gui.cancel", new Object[0])));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawGradientRect(0, 0, this.width, this.height, -12574688, -11530224);
        this.drawCenteredString(this.fontRendererObj, this.field_146313_a, this.width / 2, 90, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.field_146312_f, this.width / 2, 110, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        this.mc.displayGuiScreen((GuiScreen)null);
    }
}
