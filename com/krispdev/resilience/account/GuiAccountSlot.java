package com.krispdev.resilience.account;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ResilienceSlot;
import com.krispdev.resilience.utilities.Utils;

public class GuiAccountSlot extends ResilienceSlot{
	
	private int selectedSlot = 0;
	private Minecraft mc;
	private GuiScreen screen;
	
	public GuiAccountSlot(Minecraft mc, GuiScreen screen){
		super(mc, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 32, Resilience.getInstance().getInvoker().getHeight() - 59, 30);
		this.screen = screen;
	}
	
	@Override
	protected int func_148138_e()
	{
		return this.getSize() * 30;
	}
	
	@Override
	protected int getSize()
	{
		return Account.accountList.size();
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
			Account account = Account.accountList.get(selected);
			Resilience.getInstance().getPanelTitleFont().drawString(account.getUsername(), x, y + 1, 0xFFFFFF);
			if(account.isPremium())
			{
				Resilience.getInstance().getStandardFont().drawString("\247bPremium", x, y + 14, 0x66ffffff);
			}else
			{
				Resilience.getInstance().getStandardFont().drawString("\247cNon-Premium (No Password)", x, y + 14, 0x66ffffff);
			}
		}catch(Exception e){}
	}
}
