package com.krispdev.resilience.gui.screens;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.managers.modules.GuiModuleManager;

public class GuiManagerSelect extends GuiScreen{
	
	private GuiScreen parent;
	
	public GuiManagerSelect(GuiScreen parent){
		this.parent = parent;
	}
	
	public void initGui(){
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, 4, 4, 50, 20, "Back"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth()/2-100, Resilience.getInstance().getInvoker().getHeight()/2-10, 200, 20, "Module Manager"));
	}
	
	@Override
	public void drawScreen(int x, int y, float f){
		if(Resilience.getInstance().getWrapper().getPlayer() != null){
			this.drawDefaultBackground();
		}else{
			drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 0xff101010);
		}
		
		super.drawScreen(x, y, f);
	}
	
	@Override
	public void actionPerformed(GuiButton btn){
		if(Resilience.getInstance().getInvoker().getId(btn) == 0){
			Resilience.getInstance().getInvoker().displayScreen(parent);
		}else if(Resilience.getInstance().getInvoker().getId(btn) == 1){
			Resilience.getInstance().getInvoker().displayScreen(new GuiModuleManager(this));
		}
	}
	
}
