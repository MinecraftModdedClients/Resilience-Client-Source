package com.krispdev.resilience.gui.screens;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.utilities.Utils;

public class GuiBanned extends GuiScreen{
	
	public void initGui(){
		Resilience.getInstance().getInvoker().clearButtons(this);
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0,this.width/2-50,this.height-30,100,20,"Close Minecraft"));
	}
		
	public void drawScreen(int i, int j, float f)
	{
		Utils.drawRect(0, 0, width, height, 0xff262626);
		Resilience.getInstance().getLargeFont().drawCenteredString("Your account has been banned",this.width/2,10,0xffff0000);
		Resilience.getInstance().getPanelTitleFont().drawCenteredString("Ban Reason: "+Resilience.getInstance().getValues().banReason, this.width/2, 34, 0xffffff00);
		Resilience.getInstance().getStandardFont().drawCenteredString("Ban Lasts for "+Resilience.getInstance().getValues().banTime+" days.", this.width/2, 50, 0xffffff00);
		super.drawScreen(i, j, f);
	}
	
	public void actionPerformed(GuiButton btn){
		System.exit(0);
	}
	
}
