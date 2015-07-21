package com.krispdev.resilience.online.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.FileUtils;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class GuiRegister extends GuiScreen{
	
	private boolean firstTime;
	private GuiScreen parent;
	private GuiTextField emailBox;
	private GuiTextField passwordBox;
	private boolean goToParent;
	
	private String state = "";
	
	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	
	public GuiRegister(GuiScreen parent, boolean firstTime, boolean goToParent){
		this.parent = parent;
		this.firstTime = firstTime;
		this.goToParent = goToParent;
	}
	
	public void drawScreen(int i, int j, float f){
		drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 0xff101010);
		Resilience.getInstance().getLargeItalicFont().drawCenteredString("Welcome to \247bResilience Online!", Resilience.getInstance().getInvoker().getWidth()/2, 4, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247bResilience Online \247chas experienced a database wipe. Your old accounts will not work.", invoker.getWidth()/2, 28, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247bResilience Online \247cis going to cost at least $2500! Donate to keep this free!", invoker.getWidth()/2, 28+12, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("\247bResilience Online \247fallows you to organize a team and play with your friends like never before.", invoker.getWidth()/2, 28+12*2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("To use these features you must register. \247bUse a unique password, not your Minecraft password!", invoker.getWidth()/2, 28+12*3, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString(state, invoker.getWidth()/2, 28+12*4, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Email:", Resilience.getInstance().getInvoker().getWidth()/2-100, 81, 0xa0a0a0);
		Resilience.getInstance().getStandardFont().drawString("Password:", Resilience.getInstance().getInvoker().getWidth()/2-100, 122, 0xa0a0a0);
		emailBox.drawTextBox();
		passwordBox.drawTextBox();
		super.drawScreen(i, j ,f);
	}
	
	public void onGuiClosed(){
		Keyboard.enableRepeatEvents(false);
	}
	
	protected void actionPerformed(GuiButton btn)
	{
		if(invoker.getId(btn) == 1){
			invoker.displayScreen(parent);
		}
		if(invoker.getId(btn) == 0){
			if(emailBox.getText().equals("") || !emailBox.getText().contains("@") || !emailBox.getText().contains(".")){
				state = "\247cError! Invalid email. Don't worry, we will never disclose your email to another company!";
				return;
			}else if(passwordBox.getText().equals("")){
				state = "\247cError! For security reasons, blank passwords are not accepted.";
				return;
			}else if(passwordBox.getText().length() < 8){
				state = "\247cError! For security reasons, passwords less than 8 characters long are not accepted";
				return;
			}else if(passwordBox.getText().equals("12345678")){
				state = "\247cThat password would be cracked instantly...";
				return;
			}
			
			String url = "http://resilience.krispdev.com/registerUser.php?ign="+invoker.getSessionUsername()+"&password="+passwordBox.getText()+"&email="+emailBox.getText();
			String result = Utils.sendGetRequest(url);
			
			if(result.equals("err")){
				state = "\247cError registering! Please try again later.";
			}else{
				Resilience.getInstance().getValues().onlinePassword = passwordBox.getText();
			}
			
			invoker.displayScreen(new GuiLogin(parent, goToParent));
		}
	}
	
	@Override
	public void updateScreen(){
		emailBox.updateCursorCounter();
		passwordBox.updateCursorCounter();
	}
	 
	@Override
	protected void keyTyped(char c, int i){
		emailBox.textboxKeyTyped(c, i);
		passwordBox.textboxKeyTyped(c, i);
		
		if(c == '\t'){
			if(emailBox.isFocused()){
				emailBox.setFocused(false);
				passwordBox.setFocused(true);
			}else{
				emailBox.setFocused(true);
				passwordBox.setFocused(false);
			}
			
		}
		
		if(c == '\r'){
			actionPerformed((GuiButton)this.buttonList.get(0));
		}
	}
	
	protected void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		emailBox.mouseClicked(i, j, k);
		passwordBox.mouseClicked(i, j, k);
	}
	
	public void initGui(){
		Keyboard.enableRepeatEvents(true);
		this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth()/2 - 102, 180, 204, 20, "Register"));
		this.buttonList.add(new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() /2 - 102, 204, 204, 20, "Register Next Time"));
		emailBox = new GuiTextField(mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth()/2 - 100, 96, 200, 20);
		passwordBox = new GuiTextField(mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth()/2 - 100, 136, 200, 20);
	}
	
}
