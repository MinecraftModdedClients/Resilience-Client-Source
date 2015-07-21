package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

public class GuiScreenConfirmation extends GuiScreen
{
    private final GuiScreenConfirmation.ConfirmationType field_146937_i;
    private final String field_146934_r;
    private final String field_146933_s;
    protected final GuiScreen field_146935_a;
    protected final String field_146931_f;
    protected final String field_146932_g;
    protected final int field_146936_h;
    private static final String __OBFID = "CL_00000781";

    public GuiScreenConfirmation(GuiScreen par1GuiScreen, GuiScreenConfirmation.ConfirmationType par2GuiScreenConfirmationType, String par3Str, String par4Str, int par5)
    {
        this.field_146935_a = par1GuiScreen;
        this.field_146936_h = par5;
        this.field_146937_i = par2GuiScreenConfirmationType;
        this.field_146934_r = par3Str;
        this.field_146933_s = par4Str;
        this.field_146931_f = I18n.format("gui.yes", new Object[0]);
        this.field_146932_g = I18n.format("gui.no", new Object[0]);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 155, this.height / 6 + 112, this.field_146931_f));
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 - 155 + 160, this.height / 6 + 112, this.field_146932_g));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        this.field_146935_a.confirmClicked(p_146284_1_.id == 0, this.field_146936_h);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146937_i.field_148515_d, this.width / 2, 70, this.field_146937_i.field_148518_c);
        this.drawCenteredString(this.fontRendererObj, this.field_146934_r, this.width / 2, 90, 16777215);
        this.drawCenteredString(this.fontRendererObj, this.field_146933_s, this.width / 2, 110, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    public static enum ConfirmationType
    {
        Warning("Warning", 0, "Warning!", 16711680),
        Info("Info", 1, "Info!", 8226750);
        public final int field_148518_c;
        public final String field_148515_d;

        private static final GuiScreenConfirmation.ConfirmationType[] $VALUES = new GuiScreenConfirmation.ConfirmationType[]{Warning, Info};
        private static final String __OBFID = "CL_00000782";

        private ConfirmationType(String par1Str, int par2, String par3Str, int par4)
        {
            this.field_148515_d = par3Str;
            this.field_148518_c = par4;
        }
    }
}
