package com.krispdev.resilience.online.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class GuiAddFriend extends GuiScreen{

	private GuiScreen parent;
	private GuiTextField nameBox;
	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	private String state = "";
	
	public GuiAddFriend(GuiScreen parent){
		this.parent = parent;
	}
	
	public void drawScreen(int i, int j, float f){
		drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 0xff101010);
		Resilience.getInstance().getLargeItalicFont().drawCenteredString("\247bAdd a Friend!", Resilience.getInstance().getInvoker().getWidth()/2, 4, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("You can send your friends friend requests to view where they're playing and if they're online!", invoker.getWidth()/2, 28, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("To do so, simply fill in their username and hit \"Send Request\"", invoker.getWidth()/2, 28+12, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString(state, invoker.getWidth()/2, 28+12*3, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Friend's Username (Case Sensitive!):", Resilience.getInstance().getInvoker().getWidth()/2-100, 81, 0xffddddff);
		nameBox.drawTextBox();
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
			if(nameBox.getText().equalsIgnoreCase(invoker.getSessionUsername())){
				if(parent instanceof GuiFriendManager){
					GuiFriendManager fm = (GuiFriendManager) parent;
					fm.state = "\247cYou can't add yourself!";
				}
			}
			String url = "http://resilience.krispdev.com/requestFriend.php?ign="+invoker.getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&target="+nameBox.getText();
			String result = Utils.sendGetRequest(url);
			
			if(result.equals("err")){
				state = "\247cError connecting, please try again later.";
			}else if(result.equals("Incorrect password!")){
				state = "\247cIncorrect password! If you've forgotten your password, please email krisphf@gmail.com";
			}else{
				if(parent instanceof GuiFriendManager){
					GuiFriendManager fm = (GuiFriendManager) parent;
					fm.state = "\247bSent a friend request to "+nameBox.getText();
				}
				invoker.displayScreen(parent);
				
			}
		}
	}
	
	@Override
	public void updateScreen(){
		nameBox.updateCursorCounter();
	}
	 
	@Override
	protected void keyTyped(char c, int i){
		nameBox.textboxKeyTyped(c, i);
		
		if(c == '\r'){
			actionPerformed((GuiButton)this.buttonList.get(0));
		}
	}
	
	protected void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		nameBox.mouseClicked(i, j, k);
	}
	
	public void initGui(){
		Keyboard.enableRepeatEvents(true);
		this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth()/2 - 102, 150, 204, 20, "Send Request"));
		this.buttonList.add(new ResilienceButton(1, Resilience.getInstance().getInvoker().getWidth() /2 - 102, 174, 204, 20, "Cancel"));
		nameBox = new GuiTextField(mc.fontRenderer, Resilience.getInstance().getInvoker().getWidth()/2 - 100, 96, 200, 20);
	}
	
	
}
