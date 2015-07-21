package com.krispdev.resilience.gui.screens;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.utilities.Utils;

public class GuiInfo extends GuiScreen{
	
	private GuiScreen parentScreen;
	
	public GuiInfo(GuiScreen screen){
		this.parentScreen = screen;
	}
	
	public void initGui(){
		Resilience.getInstance().getInvoker().clearButtons(this);
		this.buttonList.add(new ResilienceButton(0, 8, 8, 50, 20, "Back"));
	}
	
	public void drawScreen(int i, int j, float f){
		Utils.drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 0xff202020);
		Resilience.getInstance().getStandardFont().drawCenteredString("This client was coded by Krisp.", Resilience.getInstance().getInvoker().getWidth()/2, 18, 0xffe4e4e4);
		Resilience.getInstance().getStandardFont().drawCenteredString("Optifine is currently included in this update", Resilience.getInstance().getInvoker().getWidth()/2, 30, 0xffe4e4e4);
		Resilience.getInstance().getStandardFont().drawCenteredString("If you have any questions, comments, or suggestions feel free to contact me:", Resilience.getInstance().getInvoker().getWidth()/2, 42, 0xffe4e4e4);
		Resilience.getInstance().getStandardFont().drawCenteredString("krisphf@gmail.com", Resilience.getInstance().getInvoker().getWidth()/2, 54, 0xff0055ff);
		Resilience.getInstance().getStandardFont().drawCenteredString("Credits: Aarow - Bow aimbot, Ownage - Font Renderer, N3xuz_DK - FastBow/Eat exploit, Halalaboos - Projectiles", Resilience.getInstance().getInvoker().getWidth()/2, 70, 0xffe4e4e4);
		Resilience.getInstance().getStandardFont().drawCenteredString("Special Credits: Bluscream - IRC Management. Huge thanks.", Resilience.getInstance().getInvoker().getWidth()/2, 88, 0xffe4e4e4);
		super.drawScreen(i, j, f);
	}
	
	public void actionPerformed(GuiButton btn){
		if(Resilience.getInstance().getInvoker().getId(btn) == 0){
			Resilience.getInstance().getInvoker().displayScreen(parentScreen);
		}
	}
}
