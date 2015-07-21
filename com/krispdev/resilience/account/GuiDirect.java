package com.krispdev.resilience.account;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.utilities.Utils;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;

public class GuiDirect extends GuiScreen{
	

	public GuiDirect(GuiScreen parent){
		this.parentScreen = parent;
	}
	
	private GuiScreen parentScreen;
	private GuiTextField usernameBox;
	private GuiPasswordBox passwordBox;

	public void updateScreen()
	{
		usernameBox.updateCursorCounter();
		passwordBox.updateCursorCounter();
	}
	
	protected void keyTyped(char c, int i)
	{
		usernameBox.textboxKeyTyped(c, i);
		passwordBox.textboxKeyTyped(c, i);
		
		if(c == '\t')
		{
			if(usernameBox.isFocused())
			{
				usernameBox.setFocused(false);
				passwordBox.setFocused(true);
			}else
			{
				usernameBox.setFocused(true);
				passwordBox.setFocused(false);
			}
		}
		
		if(c == '\r')
		{
			actionPerformed((GuiButton)this.buttonList.get(0));
		}
	}
	
	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		usernameBox.mouseClicked(i, j, k);
		passwordBox.mouseClicked(i, j, k);
	}
	
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth()/2 - 102, Resilience.getInstance().getInvoker().getHeight() /2 +25, 204, 20, "Login"));
		this.buttonList.add(new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() /2 - 102, Resilience.getInstance().getInvoker().getHeight() /2 + 49, 204, 20, "Back"));
		usernameBox = new GuiTextField(mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth()/2 - 100, 76, 200, 20);
		passwordBox = new GuiPasswordBox(mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth()/2 - 100, 116, 200, 20);
	}
	
	public void drawScreen(int i, int j, float f)
	{
		drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 0xff101010);
		Resilience.getInstance().getStandardFont().drawString("Username", Resilience.getInstance().getInvoker().getWidth()/2-100, 63, 0xa0a0a0);
		Resilience.getInstance().getStandardFont().drawString("Password", Resilience.getInstance().getInvoker().getWidth()/2-100, 104, 0xa0a0a0);
		usernameBox.drawTextBox();
		passwordBox.drawTextBox();
		super.drawScreen(i, j ,f);
	}
	
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	protected void actionPerformed(GuiButton par1)
	{
		if(par1.id == 1)
		{
			mc.displayGuiScreen(GuiAccountScreen.guiScreen);
		}
		if(par1.id == 0)
		{
			Account acc = new Account(usernameBox.getText(), passwordBox.getText());
			if(acc != null && acc.isPremium()){
				String username = acc.getUsername();
				String password = acc.getPassword();
				try
				{
					GuiAccountScreen.status = Utils.setSessionData(username, password);
					Resilience.getInstance().getFileManager().saveAccounts();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				mc.displayGuiScreen(parentScreen);
			}else{
				mc.session = new Session(acc.getUsername(), "", "");
				GuiAccountScreen.status = "\247bSuccess!";
				mc.displayGuiScreen(parentScreen);
			}
		}
	}
	
	
}
