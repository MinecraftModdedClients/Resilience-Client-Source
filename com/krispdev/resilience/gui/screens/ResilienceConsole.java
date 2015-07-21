package com.krispdev.resilience.gui.screens;

import java.net.URI;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.gui.objects.other.GuiCustomFontField;
import com.krispdev.resilience.utilities.Utils;

public class ResilienceConsole extends GuiScreen{
	
    private boolean field_73897_d;
    private boolean field_73905_m;
    private int field_73903_n;

    /** used to pass around the URI to various dialogues and to the host os */
    private URI clickedURI;

    /** Chat entry field */
    protected GuiCustomFontField inputField;

    /**
     * is the text that appears when you press the chat key and the input box appears pre-filled
     */
    private String defaultInputFieldText = "";

    public ResilienceConsole() {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.inputField = new GuiCustomFontField(Resilience.getInstance().getStandardFont(), 79, 18, width - 75, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.inputField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.field_73905_m = false;

        if (par2 == 15)
        {
            //this.completePlayerName();
        }
        else
        {
            this.field_73897_d = false;
        }

        if (par2 == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (par2 != 28 && par2 != 156)
        {
        	this.inputField.textboxKeyTyped(par1, par2);
        }
        else
        {
            String var3 = this.inputField.getText().trim();

            if (var3.length() > 0)
            {   
                for(Command cmd : Command.cmdList){
					try {
						if(!var3.startsWith(cmd.getWords())) continue;
						boolean result = cmd.recieveCommand(var3);
	                	if(result){
	        	            this.mc.displayGuiScreen((GuiScreen)null);
	                		return;
	                	}
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
                
                Resilience.getInstance().getLogger().warningChat("\247fUnkown command \"\247b".concat(var3).concat("\247f\". Type \"\247bhelp\247f\" for help"));
            }

        this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    /**
     * Handles mouse input.
     */
    public void func_146274_d()
    {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();

        if (var1 != 0)
        {
            if (var1 > 1)
            {
                var1 = 1;
            }

            if (var1 < -1)
            {
                var1 = -1;
            }

            if (!isShiftKeyDown())
            {
                var1 *= 7;
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        this.inputField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par2 == 0)
        {
            if (par1)
            {
                this.func_73896_a(this.clickedURI);
            }

            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    private void func_73896_a(URI par1URI)
    {
        try
        {
            Class var2 = Class.forName("java.awt.Desktop");
            Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            var2.getMethod("browse", new Class[] {URI.class}).invoke(var3, new Object[] {par1URI});
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
        }
    }

    /**
     * Autocompletes player name
     */
    public void completePlayerName()
    {
    }

    private void func_73893_a(String par1Str, String par2Str)
    {
        if (par1Str.length() >= 1)
        {
            this.field_73905_m = true;
        }
    }

    /**
     * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
     * message from the current cursor position
     */
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
    	GL11.glPushMatrix();
    	GL11.glDisable(GL11.GL_LIGHTING);
        Utils.drawBetterRect(75, 14, width - 75, 30, 0xaa000000, 0xaa4e4e4e);
        this.inputField.drawTextBox();
        String msg = inputField.getText();
        int theHeight = 35;

        for(Command cmd : Command.cmdList){
        	if((cmd.getWords().startsWith(msg)) && msg.length() > 0){
        		String toDisplay = cmd.getWords() + cmd.getExtras() + " | " + cmd.getDesc();
                Utils.drawBetterRect(75, theHeight - 3, width - 75, theHeight + 12, 0xeeffffff, 0xee262626);
        		Resilience.getInstance().getStandardFont().drawString("\247f"+toDisplay, 79, theHeight-2, 0xff153F15);
                Resilience.getInstance().getStandardFont().drawString("\247b"+msg, 79, theHeight-2, 0xff123456);
	        	theHeight += 16;
        	}else if(msg.startsWith(cmd.getWords())){
        		String toDisplay = "\247b" + cmd.getWords() + cmd.getExtras() + " \247f| " + cmd.getDesc();
                Utils.drawBetterRect(75, theHeight - 3, width - 75, theHeight + 12, 0xeeffffff, 0xee262626);
                
        		Resilience.getInstance().getStandardFont().drawString(toDisplay, 79, theHeight-2, 0xff123f15);
        		
	        	theHeight += 16;
        	}
        }
        
        if(msg.length() == 0){
        	Resilience.getInstance().getStandardFont().drawString("\247fType \"\247bhelp\247f\" to get help.", 79, 35, 0xff123456);
        }
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return true;
    }
}
