package com.krispdev.resilience.account;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.GuiResilienceMain;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.utilities.Utils;

public class GuiAccountScreen extends GuiScreen{
	
	public static final GuiAccountScreen guiScreen = new GuiAccountScreen(new GuiResilienceMain(new HookGuiMainMenu()));
	private GuiAccountSlot accountSlot;
	private GuiScreen parentScreen;
	public static String status = "Idle";
	private boolean firstTime = true;
	
	public GuiAccountScreen(GuiScreen screen){
		this.parentScreen = screen;
		Resilience.getInstance().getFileManager().loadAccounts();
	}
	
	public void initGui(){
		firstTime = true;
		accountSlot = new GuiAccountSlot(Minecraft.getMinecraft(), this);
		accountSlot.registerScrollButtons(7, 8);
		this.buttonList.clear();
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, width/2-100, height-30+1+2, 200, 20, "Login"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, width/2-204, height-30+1+2, 100, 20, "Add"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(2, width/2+104, height-30+1+2, 100, 20, "Delete"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(4, width/2-204, height-53+2, 100, 20, "Edit"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(5, width/2+104, height-53+2, 100, 20, "Direct"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(3, 4, 4, 50, 20, "Back"));
	}
	
	public void drawScreen(int i, int j, float f){
		Utils.drawRect(0, 0, width, height, 0xff101010);
		accountSlot.drawScreen(i, j, f);
		Resilience.getInstance().getStandardFont().drawString(firstTime ? "\247fStatus: \2478Idle" : "\247fStatus: "+status, 62, 9, 0x6655FF55);
		Resilience.getInstance().getStandardFont().drawCenteredString("Alt Count: \247f"+Account.accountList.size(), this.width/2, 4, 0xffe4e4e4);
		Resilience.getInstance().getStandardFont().drawString("\247fUsername: \247b"+Resilience.getInstance().getInvoker().getSessionUsername(), Resilience.getInstance().getInvoker().getWidth() - Resilience.getInstance().getStandardFont().getWidth("\247fUsername: \247b"+Resilience.getInstance().getInvoker().getSessionUsername()) - 11, 9, 0xffffffff);
		super.drawScreen(i, j, f);
	}
	
	public void actionPerformed(GuiButton btn){
		Utils.setOnline(false);
		if(btn.id == 0){
			firstTime = false;
			if(Account.accountList.size() <= 0) return;
			Account acc = Account.accountList.get(accountSlot.getSelectedSlot());
			if(acc == null) return;
			if(acc.isPremium()){
				final String username = acc.getUsername();
				final String password = acc.getPassword();
				
				status = "\247fLoggin in...";
				new Thread(){
					public void run(){
						try{
							status = Utils.setSessionData(username, password);
							Resilience.getInstance().getFileManager().saveAccounts();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}.start();
			}else{
				mc.session = new Session(acc.getUsername(), "", "");
				status = "\247bSuccess!";
			}
		}else if(btn.id == 1){
			mc.displayGuiScreen(new GuiAdd(guiScreen));
		}else if(btn.id == 4){
			if(Account.accountList.size() <= 0) return;
			Account acc = Account.accountList.get(accountSlot.getSelectedSlot());
			if(acc == null) return;
			Resilience.getInstance().getInvoker().displayScreen(new GuiEdit(acc, this));
		}else if(btn.id == 2){
			try{
				if(Account.accountList.size() <= 0) return;
				Account.accountList.remove(Account.accountList.indexOf(Account.accountList.get(accountSlot.getSelectedSlot())));
				Resilience.getInstance().getFileManager().saveAccounts();
			}catch(Exception e){}
		}else if(btn.id == 5){
			Resilience.getInstance().getInvoker().displayScreen(new GuiDirect(this));
		}else{
			mc.displayGuiScreen(parentScreen);
		}
	}
}
