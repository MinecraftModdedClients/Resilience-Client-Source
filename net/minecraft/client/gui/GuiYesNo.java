package net.minecraft.client.gui;

import java.util.Iterator;
import net.minecraft.client.resources.I18n;

public class GuiYesNo extends GuiScreen
{
    protected GuiScreen field_146355_a;
    protected String field_146351_f;
    private String field_146354_r;
    protected String field_146352_g;
    protected String field_146356_h;
    protected int field_146357_i;
    private int field_146353_s;
    private static final String __OBFID = "CL_00000684";

    public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, int par4)
    {
        this.field_146355_a = par1GuiScreen;
        this.field_146351_f = par2Str;
        this.field_146354_r = par3Str;
        this.field_146357_i = par4;
        this.field_146352_g = I18n.format("gui.yes", new Object[0]);
        this.field_146356_h = I18n.format("gui.no", new Object[0]);
    }

    public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, String par4Str, String par5Str, int par6)
    {
        this.field_146355_a = par1GuiScreen;
        this.field_146351_f = par2Str;
        this.field_146354_r = par3Str;
        this.field_146352_g = par4Str;
        this.field_146356_h = par5Str;
        this.field_146357_i = par6;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 155, this.height / 6 + 96, this.field_146352_g));
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 - 155 + 160, this.height / 6 + 96, this.field_146356_h));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        this.field_146355_a.confirmClicked(p_146284_1_.id == 0, this.field_146357_i);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146351_f, this.width / 2, 70, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.field_146354_r, this.width / 2, 90, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    public void func_146350_a(int p_146350_1_)
    {
        this.field_146353_s = p_146350_1_;
        GuiButton var3;

        for (Iterator var2 = this.buttonList.iterator(); var2.hasNext(); var3.enabled = false)
        {
            var3 = (GuiButton)var2.next();
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        GuiButton var2;

        if (--this.field_146353_s == 0)
        {
            for (Iterator var1 = this.buttonList.iterator(); var1.hasNext(); var2.enabled = true)
            {
                var2 = (GuiButton)var1.next();
            }
        }
    }
}
