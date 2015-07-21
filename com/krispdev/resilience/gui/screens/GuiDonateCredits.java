package com.krispdev.resilience.gui.screens;

import java.util.Collections;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.Sys;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.donate.DonatorSlot;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.hooks.HookGuiMainMenu;

public class GuiDonateCredits extends GuiScreen{
	
    public static GuiDonateCredits guiDonate = new GuiDonateCredits(new GuiResilienceMain(new HookGuiMainMenu()));
	private DonatorSlot slot;
	
	private GuiScreen parent;
	
	public GuiDonateCredits(GuiScreen parent){
		this.parent = parent;
	}

    public void initGui(){
    	 this.buttonList.clear();
    	 Collections.sort(Donator.donatorList);
    	 slot = new DonatorSlot(mc, this);
    	 slot.registerScrollButtons(7, 8);
    	 Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, 4, 4, 70, 20, "Back"));
    	 Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(69, Resilience.getInstance().getInvoker().getWidth()/2 - 76, Resilience.getInstance().getInvoker().getHeight() - 51, 160, 20, "Donate"));
    }
	
    @Override
    public void actionPerformed(GuiButton btn){
    	if(btn.id == 0){
    		mc.displayGuiScreen(parent);
    	}else{
    		Sys.openURL("http://resilience.krispdev.com/donate");
    	}
    }
    
    public void drawScreen(int i, int j, float f){
		this.drawDefaultBackground();
		slot.drawScreen(i, j, f);
		Resilience.getInstance().getPanelTitleFont().drawCenteredString("Huge thanks to all these wonderful people!", Resilience.getInstance().getInvoker().getWidth()/2, 8, 0xffFFAA00);
		super.drawScreen(i, j, f);
    }
}
