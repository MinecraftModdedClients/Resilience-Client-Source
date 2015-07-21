package com.krispdev.resilience.gui.objects.buttons;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class ResilienceButton extends GuiButton{
	
	private float x, y, x1, y1;
	
	public ResilienceButton(int par1, float par2, float par3, float par4, float par5, String par6Str) {
		super(par1, (int)par2, (int)par3, (int)par4, (int)par5, par6Str);
		this.x = par2;
		this.y = par3;
		this.x1 = par4;
		this.y1 = par5;
		this.displayString = par6Str;
	}

	public ResilienceButton(int i, int j, int k, String stringParams) {
		this(i, j, k, 200, 20, stringParams);
	}
	
	
	@Override
    public void drawButton(Minecraft p_146112_1_, int mX, int mY){
    	boolean overButton = mX >= x && mX <= x+x1 && mY >= y && mY <= y+y1; 
    	Utils.drawRect(x, y, x+x1, y+y1, overButton ? 0x553333ff :  0x550000e5);
    	if(y1 == 15){
    		Resilience.getInstance().getStandardFont().drawCenteredString(displayString, (int)(x+x1/2), (y+1.5F), 0xffffffff);
    	}else if(y1<30){
    		Resilience.getInstance().getPanelTitleFont().drawCenteredString(displayString, (int)(x+x1/2), (y+2), 0xffffffff);
    	}else{
    		Resilience.getInstance().getPanelTitleFont().drawCenteredString(displayString, (int)(x+x1/2), (int)(y+11.5F), 0xffffffff);
    	}
    }
	
	public void setY(int y){
		this.field_146129_i = y;
		this.y = y;
	}
	
}
