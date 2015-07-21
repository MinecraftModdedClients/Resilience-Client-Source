package com.krispdev.resilience.gui.screens.managers.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ResilienceSlot;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Utils;

public class GuiModuleSlot extends ResilienceSlot{
	
	private int selectedSlot = 0;
	private Minecraft mc;
	private GuiScreen screen;
	
	public GuiModuleSlot(Minecraft mc, GuiScreen screen){
		super(mc, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 32, Resilience.getInstance().getInvoker().getHeight() - 59, 50);
		this.screen = screen;
	}
	
	@Override
	protected int func_148138_e()
	{
		return this.getSize() * 50;
	}
	
	@Override
	protected int getSize()
	{
		return GuiModuleManager.visibleModules.size();
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
			DefaultModule module = GuiModuleManager.visibleModules.get(selected);
			Resilience.getInstance().getModListFont().drawString("\247b"+module.getName(), x, y, 0xFFFFFF);
			Resilience.getInstance().getStandardFont().drawString("Keybind: \247f"+Keyboard.getKeyName(module.getKeyCode()), x, y+12, 0xffffffff);
			Resilience.getInstance().getStandardFont().drawString("State: "+(module.isEnabled() ? "\2473Enabled" : "\2478Disabled"), x, y+23, 0xffffffff);
			Resilience.getInstance().getButtonExtraFont().drawString("\2473"+module.getDescription(), x, y + 34, 0x66ffffff);
		}catch(Exception e){}
	}
	
}
