package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

public class GuiConfirmOpenLink extends GuiYesNo
{
    private String field_146363_r;
    private String field_146362_s;
    private String field_146361_t;
    private boolean field_146360_u = true;
    private static final String __OBFID = "CL_00000683";

    public GuiConfirmOpenLink(GuiScreen par1GuiScreen, String par2Str, int par3, boolean par4)
    {
        super(par1GuiScreen, I18n.format(par4 ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]), par2Str, par3);
        this.field_146352_g = I18n.format(par4 ? "chat.link.open" : "gui.yes", new Object[0]);
        this.field_146356_h = I18n.format(par4 ? "gui.cancel" : "gui.no", new Object[0]);
        this.field_146362_s = I18n.format("chat.copy", new Object[0]);
        this.field_146363_r = I18n.format("chat.link.warning", new Object[0]);
        this.field_146361_t = par2Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.add(new GuiButton(0, this.width / 3 - 83 + 0, this.height / 6 + 96, 100, 20, this.field_146352_g));
        this.buttonList.add(new GuiButton(2, this.width / 3 - 83 + 105, this.height / 6 + 96, 100, 20, this.field_146362_s));
        this.buttonList.add(new GuiButton(1, this.width / 3 - 83 + 210, this.height / 6 + 96, 100, 20, this.field_146356_h));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.id == 2)
        {
            this.func_146359_e();
        }

        this.field_146355_a.confirmClicked(p_146284_1_.id == 0, this.field_146357_i);
    }

    public void func_146359_e()
    {
        setClipboardString(this.field_146361_t);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);

        if (this.field_146360_u)
        {
            this.drawCenteredString(this.fontRendererObj, this.field_146363_r, this.width / 2, 110, 16764108);
        }
    }

    public void func_146358_g()
    {
        this.field_146360_u = false;
    }
}
