package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class GuiScreenAddServer extends GuiScreen
{
    private GuiScreen field_146310_a;
    private GuiTextField field_146308_f;
    private GuiTextField field_146309_g;
    private ServerData field_146311_h;
    private static final String __OBFID = "CL_00000695";

    public GuiScreenAddServer(GuiScreen par1GuiScreen, ServerData par2ServerData)
    {
        this.field_146310_a = par1GuiScreen;
        this.field_146311_h = par2ServerData;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_146309_g.updateCursorCounter();
        this.field_146308_f.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("addServer.add", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        this.field_146309_g = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
        this.field_146309_g.setFocused(true);
        this.field_146309_g.setText(this.field_146311_h.serverName);
        this.field_146308_f = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 106, 200, 20);
        this.field_146308_f.func_146203_f(128);
        this.field_146308_f.setText(this.field_146311_h.serverIP);
        ((GuiButton)this.buttonList.get(0)).enabled = this.field_146308_f.getText().length() > 0 && this.field_146308_f.getText().split(":").length > 0 && this.field_146309_g.getText().length() > 0;
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
                this.field_146310_a.confirmClicked(false, 0);
            }
            else if (p_146284_1_.id == 0)
            {
                this.field_146311_h.serverName = this.field_146309_g.getText();
                this.field_146311_h.serverIP = this.field_146308_f.getText();
                this.field_146310_a.confirmClicked(true, 0);
            }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.field_146309_g.textboxKeyTyped(par1, par2);
        this.field_146308_f.textboxKeyTyped(par1, par2);

        if (par2 == 15)
        {
            this.field_146309_g.setFocused(!this.field_146309_g.isFocused());
            this.field_146308_f.setFocused(!this.field_146308_f.isFocused());
        }

        if (par2 == 28 || par2 == 156)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

        ((GuiButton)this.buttonList.get(0)).enabled = this.field_146308_f.getText().length() > 0 && this.field_146308_f.getText().split(":").length > 0 && this.field_146309_g.getText().length() > 0;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.field_146308_f.mouseClicked(par1, par2, par3);
        this.field_146309_g.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("addServer.title", new Object[0]), this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("addServer.enterName", new Object[0]), this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 94, 10526880);
        this.field_146309_g.drawTextBox();
        this.field_146308_f.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
