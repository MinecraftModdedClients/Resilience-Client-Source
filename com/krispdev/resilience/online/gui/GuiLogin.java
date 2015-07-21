package com.krispdev.resilience.online.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.account.GuiPasswordBox;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.file.FileUtils;
import com.krispdev.resilience.gui.objects.buttons.CheckBox;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.GuiAskDonate;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class GuiLogin extends GuiScreen{

	private GuiScreen parent;
	private GuiPasswordBox passwordBox;
	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	private CheckBox hidePass;
	private String state = "";
	private boolean goToParent;
	
	public GuiLogin(GuiScreen parent, boolean goToParent){
		this.parent = parent;
		this.goToParent = goToParent;
	}
	
	public void drawScreen(int i, int j, float f){
		drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 0xff101010);
		Resilience.getInstance().getLargeItalicFont().drawCenteredString("Welcome back to \247bResilience Online!", Resilience.getInstance().getInvoker().getWidth()/2, 4, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("Your current account is \247b"+invoker.getSessionUsername(), invoker.getWidth()/2, 28, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("Please login to \247bResilience Online \247fwith the password you registered with!", invoker.getWidth()/2, 28+12, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString(state, invoker.getWidth()/2, 28+12*2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("Hide Password", invoker.getWidth()/2-60, 127.5F, 0xffffffff);
		if(hidePass.isChecked()){
			passwordBox.show = false;
		}else{
			passwordBox.show = true;
		}
		Resilience.getInstance().getStandardFont().drawString("Password:", Resilience.getInstance().getInvoker().getWidth()/2-100, 81, 0xa0a0a0);
		passwordBox.drawTextBox();
		hidePass.draw(i, j);
		super.drawScreen(i, j ,f);
	}
	
	public void onGuiClosed(){
		Keyboard.enableRepeatEvents(false);
	}
	
	protected void actionPerformed(GuiButton btn)
	{
		if(invoker.getId(btn) == 1){
			if(Resilience.getInstance().getFileManager().shouldAsk && !Donator.isDonator(invoker.getSessionUsername(), 0.01F)){
				invoker.displayScreen(new GuiAskDonate(new HookGuiMainMenu()));
			}else{
				invoker.displayScreen(parent);
			}
		}
		if(invoker.getId(btn) == 0){
			String url = "http://resilience.krispdev.com/updateOnline.php?ign="+invoker.getSessionUsername()+"&password="+passwordBox.getText()+"&online=true&channel="+Resilience.getInstance().getValues().userChannel;
			String result = Utils.sendGetRequest(url);
			
			if(result.equals("err")){
				state = "\247cError connecting, please try again later.";
			}else if(result.equals("Incorrect password!")){
				state = "\247cIncorrect password! If you've forgotten your password, please email krisphf@gmail.com";
			}else{
	        	Resilience.getInstance().setNewAccount(false);
				Resilience.getInstance().getValues().onlinePassword = passwordBox.getText();
				if(Resilience.getInstance().getFileManager().shouldAsk && !Donator.isDonator(invoker.getSessionUsername(), 0.01F) /*&& !hasAsked*/){
					invoker.displayScreen(new GuiAskDonate(new HookGuiMainMenu()));
				}else{
					if(goToParent){
						invoker.displayScreen(parent);
					}else{
						invoker.displayScreen(new GuiFriendManager(parent));
					}
				}
			}
		}
	}
	
	@Override
	public void updateScreen(){
		if(passwordBox != null){
			passwordBox.updateCursorCounter();
		}
	}
	 
	@Override
	protected void keyTyped(char c, int i){
		passwordBox.textboxKeyTyped(c, i);
		
		if(c == '\r'){
			actionPerformed((GuiButton)this.buttonList.get(0));
		}
	}
	
	protected void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		passwordBox.mouseClicked(i, j, k);
		hidePass.clicked(i, j);
	}
	
	public void initGui(){
		Keyboard.enableRepeatEvents(true);
		passwordBox = new GuiPasswordBox(mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth()/2 - 100, 96, 200, 20);
		this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth()/2 - 102, 150, 204, 20, "Login"));
		this.buttonList.add(new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() /2 - 102, 174, 204, 20, "Go Offline"));
		hidePass = new CheckBox(invoker.getWidth()/2-101, 129, true);
	}
	
}
