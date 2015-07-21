package com.krispdev.resilience.gui.screens;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.utilities.Utils;

public class UpdateGamePrompt extends GuiScreen{
	
	private GuiScreen parentScreen;
	
	public UpdateGamePrompt(GuiScreen screen){
		this.parentScreen = screen;
	}
	
	@Override
	public void initGui(){
		this.buttonList.clear();
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, 8, Resilience.getInstance().getInvoker().getHeight()-28, 108, 20, "Update Now"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth()-108, Resilience.getInstance().getInvoker().getHeight()-28, 100, 20, "Remind Me Later"));
	}
	
	public void drawScreen(int i, int j, float f){
		Utils.drawRect(0,0,Resilience.getInstance().getInvoker().getWidth(),Resilience.getInstance().getInvoker().getHeight(),0xff202020);
		Resilience.getInstance().getPanelTitleFont().drawCenteredString("An update has been found for ".concat(Resilience.getInstance().getName()), Resilience.getInstance().getInvoker().getWidth()/2, 8, 0xffffffff);
		super.drawScreen(i, j, f);
	}
	
	@Override
	public void actionPerformed(GuiButton btn){
		if(Resilience.getInstance().getInvoker().getId(btn) == 0){
			mc.displayGuiScreen(new GuiUpdating());
		}else{
			mc.displayGuiScreen(new HookGuiMainMenu());
		}
	}
}
