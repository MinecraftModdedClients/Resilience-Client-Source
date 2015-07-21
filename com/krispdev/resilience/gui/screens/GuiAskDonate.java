package com.krispdev.resilience.gui.screens;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.Sys;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class GuiAskDonate extends GuiScreen{

	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	private GuiScreen parent;
	
	public GuiAskDonate(GuiScreen parent){
		this.parent = parent;
	}
	
	@Override
	public void initGui(){
		invoker.clearButtons(this);
		
		invoker.addButton(this, new ResilienceButton(0,8,invoker.getHeight()-28,100,20,"Remind Me Later"));
		invoker.addButton(this, new ResilienceButton(1,invoker.getWidth()/2-75,invoker.getHeight()-28,150,20,"Donate!"));
		invoker.addButton(this, new ResilienceButton(2,invoker.getWidth()-108,invoker.getHeight()-28,100,20,"Never >:("));
	}
	
	@Override
	public void drawScreen(int i, int j, float f){
		drawRect(0, 0, invoker.getWidth(), invoker.getHeight(), 0xff101010);
		Resilience.getInstance().getPanelTitleFont().drawCenteredString("Donate to "+Resilience.getInstance().getName()+"!", invoker.getWidth()/2, 8, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("Donators who paid $5 or more to " +Resilience.getInstance().getName()+" receive: A cape in-game (seen by everybody using the client),", invoker.getWidth()/2, 24, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("A spot on the donation credits (In the" + Resilience.getInstance().getName() + " menu under \"Donate\"),", invoker.getWidth()/2, 24+18, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("Eternal happiness!", invoker.getWidth()/2, 24+18*4, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("More features in the future (such as VIP mods),", invoker.getWidth()/2, 24+18*2, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("The removal of this screen,", invoker.getWidth()/2, 24+18*3, 0xffFF55FF);
		super.drawScreen(i, j, f);
	}
	
	@Override
	public void actionPerformed(GuiButton btn){
		if(invoker.getId(btn) == 0){
			invoker.displayScreen(parent);
		}else if(invoker.getId(btn) == 1){
			Sys.openURL("http://resilience.krispdev.com/donate");
			invoker.displayScreen(parent);
			Resilience.getInstance().getFileManager().saveOptions("0");
		}else{
			Resilience.getInstance().getFileManager().saveOptions("-1");
			invoker.displayScreen(parent);
		}
	}
}
