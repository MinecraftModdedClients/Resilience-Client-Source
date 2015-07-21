package net.minecraft.client.gui;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiOptions extends GuiScreen
{
    private static final GameSettings.Options[] field_146440_f = new GameSettings.Options[] {GameSettings.Options.FOV, GameSettings.Options.DIFFICULTY};
    private final GuiScreen field_146441_g;
    private final GameSettings field_146443_h;
    protected String field_146442_a = "Options";
    private static final String __OBFID = "CL_00000700";

    public GuiOptions(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        this.field_146441_g = par1GuiScreen;
        this.field_146443_h = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int var1 = 0;
        this.field_146442_a = I18n.format("options.title", new Object[0]);
        GameSettings.Options[] var2 = field_146440_f;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            GameSettings.Options var5 = var2[var4];

            if (var5.getEnumFloat())
            {
                this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), var5));
            }
            else
            {
                GuiOptionButton var6 = new GuiOptionButton(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), var5, this.field_146443_h.getKeyBinding(var5));

                if (var5 == GameSettings.Options.DIFFICULTY && this.mc.theWorld != null && this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
                {
                    var6.enabled = false;
                    var6.displayString = I18n.format("options.difficulty", new Object[0]) + ": " + I18n.format("options.difficulty.hardcore", new Object[0]);
                }

                this.buttonList.add(var6);
            }

            ++var1;
        }

        this.buttonList.add(new GuiButton(106, this.width / 2 - 152, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
        this.buttonList.add(new GuiButton(8675309, this.width / 2 + 2, this.height / 6 + 72 - 6, 150, 20, "Super Secret Settings...")
        {
            private static final String __OBFID = "CL_00000701";
            public void func_146113_a(SoundHandler p_146113_1_)
            {
                SoundEventAccessorComposite var2 = p_146113_1_.func_147686_a(new SoundCategory[] {SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER});

                if (var2 != null)
                {
                    p_146113_1_.playSound(PositionedSoundRecord.func_147674_a(var2.func_148729_c(), 0.5F));
                }
            }
        });
        this.buttonList.add(new GuiButton(101, this.width / 2 - 152, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
        this.buttonList.add(new GuiButton(100, this.width / 2 + 2, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
        this.buttonList.add(new GuiButton(102, this.width / 2 - 152, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
        this.buttonList.add(new GuiButton(103, this.width / 2 + 2, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.multiplayer.title", new Object[0])));
        this.buttonList.add(new GuiButton(105, this.width / 2 - 152, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
        this.buttonList.add(new GuiButton(104, this.width / 2 + 2, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.enabled)
        {
            if (p_146284_1_.id < 100 && p_146284_1_ instanceof GuiOptionButton)
            {
                this.field_146443_h.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
                p_146284_1_.displayString = this.field_146443_h.getKeyBinding(GameSettings.Options.getEnumOptions(p_146284_1_.id));
            }

            if (p_146284_1_.id == 8675309)
            {
                this.mc.entityRenderer.activateNextShader();
            }

            if (p_146284_1_.id == 101)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiVideoSettings(this, this.field_146443_h));
            }

            if (p_146284_1_.id == 100)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiControls(this, this.field_146443_h));
            }

            if (p_146284_1_.id == 102)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiLanguage(this, this.field_146443_h, this.mc.getLanguageManager()));
            }

            if (p_146284_1_.id == 103)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new ScreenChatOptions(this, this.field_146443_h));
            }

            if (p_146284_1_.id == 104)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiSnooper(this, this.field_146443_h));
            }

            if (p_146284_1_.id == 200)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.field_146441_g);
            }

            if (p_146284_1_.id == 105)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
            }

            if (p_146284_1_.id == 106)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.field_146443_h));
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146442_a, this.width / 2, 15, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
