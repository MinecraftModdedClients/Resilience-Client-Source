package com.krispdev.resilience.online.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ResilienceSlot;
import com.krispdev.resilience.gui.screens.managers.modules.GuiModuleManager;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.online.friend.OnlineFriend;
import com.krispdev.resilience.utilities.Utils;

public class GuiFriendSlot extends ResilienceSlot{
	
	private int selectedSlot = 0;
	private Minecraft mc;
	private GuiScreen screen;
	
	public GuiFriendSlot(Minecraft mc, GuiScreen screen){
		super(mc, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 32, Resilience.getInstance().getInvoker().getHeight() - 59, 38);
		this.screen = screen;
	}
	
	@Override
	protected int func_148138_e()
	{
		return this.getSize() * 38;
	}
	
	@Override
	protected int getSize()
	{
		return GuiFriendManager.friends.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2, int var3, int var4)
	{
		selectedSlot = var1;
	}

	@Override
	protected boolean isSelected(int var1)
	{
		return selectedSlot == var1;
	}
	
	protected int getSelectedSlot()
	{
		return selectedSlot;
	}

	@Override
	protected void drawBackground()
	{
		Utils.drawRect(0, 0, screen.width, screen.height, 0xff101010);
	}
	
	@Override
	protected void drawSlot(int selected, int x, int y, int var4, Tessellator var5, int var6, int var7)
	{
		try
		{
			OnlineFriend friend = GuiFriendManager.friends.get(selected);
			Resilience.getInstance().getModListFont().drawString("\247f"+friend.getUsername(), x, y, 0xFFFFFF);
			Resilience.getInstance().getStandardFont().drawString(friend.isOnline() ? "\247bOnline" : "\247cOffline", x, y+12, 0xffffffff);
			if(friend.isOnline()){
				Resilience.getInstance().getStandardFont().drawString(friend.getStatus().replace(",port,", ":"), x, y+23, 0xff787878);
			}else{
				Resilience.getInstance().getStandardFont().drawString("User is currently offline", x, y+23, 0xff787878);
			}
				//Resilience.getInstance().getButtonExtraFont().drawString("\2473"+module.getDescription(), x, y + 34, 0x66ffffff);
		}catch(Exception e){}
	}
	

}
