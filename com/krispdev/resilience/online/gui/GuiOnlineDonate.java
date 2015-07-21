package com.krispdev.resilience.online.gui;

import org.lwjgl.Sys;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.wrappers.MethodInvoker;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiOnlineDonate extends GuiScreen{
	
	private GuiScreen parent;
	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	
	public GuiOnlineDonate(GuiScreen parent){
		this.parent = parent;
	}
	
	public void initGui(){
		invoker.clearButtons(this);
		
		invoker.addButton(this, new ResilienceButton(0,8,invoker.getHeight()-28,100,20,"Donate!"));
		invoker.addButton(this, new ResilienceButton(1,invoker.getWidth()-108,invoker.getHeight()-28,100,20,"Not Now"));
	}
	
	@Override
	public void drawScreen(int i, int j, float f){
		drawRect(0, 0, invoker.getWidth(), invoker.getHeight(), 0xff101010);
		Resilience.getInstance().getPanelTitleFont().drawCenteredString("\247bHello, "+invoker.getSessionUsername()+"!", invoker.getWidth()/2, 8, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247bResilience Online\247f is a feature that allows users using the client to communicate and play", invoker.getWidth()/2, 24, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247fin a revolutionary new way. The cost of hosting these servers, however, is immense (at least $2500 a year).", invoker.getWidth()/2, 24+12, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247fIn order to keep \247bResilience Online\247f a free feature, I need your help. A small $5 donation can go", invoker.getWidth()/2, 24+12*2, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247fa long way to helping support the servers, and even upgrade them for a better experience. Not only do you", invoker.getWidth()/2, 24+12*3, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247fget \247bResilience Online\247f if I can no longer support a free version, you also get a Resilience cape,", invoker.getWidth()/2, 24+12*4, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247fa spot on the donation credits with a message of your choice, and a gold [VIP] prefix in the IRC.", invoker.getWidth()/2, 24+12*5, 0xffFF55FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247fPlease help support the project with your donation!", invoker.getWidth()/2, 24+12*6, 0xffFF55FF);
		super.drawScreen(i, j, f);
	}
	
	@Override
	public void actionPerformed(GuiButton btn){
		if(invoker.getId(btn) == 0){
			Sys.openURL("http://resilience.krispdev.com/donate");
			invoker.displayScreen(parent);
		}else if(invoker.getId(btn) == 1){
			invoker.displayScreen(parent);
		}
	}
	
}
