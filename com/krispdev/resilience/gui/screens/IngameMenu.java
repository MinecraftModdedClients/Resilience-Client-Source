package com.krispdev.resilience.gui.screens;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.file.FileUtils;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.managers.modules.GuiModuleManager;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.online.gui.GuiFriendManager;
import com.krispdev.resilience.online.gui.GuiLogin;
import com.krispdev.resilience.online.gui.GuiOnlineDonate;
import com.krispdev.resilience.online.gui.GuiRegister;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class IngameMenu extends GuiScreen{
	
	private GuiScreen parent;
	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	private ResilienceButton enableButton;
	private IngameMenu instance;
	
	public IngameMenu(GuiScreen parent){
		this.parent = parent;
		instance = this;
	}
	
	@Override
	public void drawScreen(int x, int y, float f){
		this.drawDefaultBackground();
		super.drawScreen(x, y, f);
	}
	
	@Override
	public void initGui(){
		invoker.addButton(this, new ResilienceButton(0, invoker.getWidth()/2-100, invoker.getHeight()/4-10, 200, 20, "Module Manager"));
		invoker.addButton(this, enableButton = new ResilienceButton(1, invoker.getWidth()/2-100, Resilience.getInstance().getInvoker().getHeight()/4-34, 200, 20, (Resilience.getInstance().isEnabled() ? "Disable" : "Enable") + " ".concat(Resilience.getInstance().getName())));
		invoker.addButton(this, new ResilienceButton(3, invoker.getWidth()/2-100, invoker.getHeight()/4-10+24, 200, 20, "Resilience Online"));
		invoker.addButton(this, new ResilienceButton(2, 4, 4, 50, 20, "Back"));
	}
	
	@Override
	public void actionPerformed(GuiButton btn){
		if(invoker.getId(btn) == 0){
			Resilience.getInstance().getInvoker().displayScreen(new GuiModuleManager(this));
		}
		if(Resilience.getInstance().getInvoker().getId(btn) == 1){
			Resilience.getInstance().setEnabled(!Resilience.getInstance().isEnabled());
			
			if(Resilience.getInstance().isEnabled()){
				enableButton.displayString = "Disable ".concat(Resilience.getInstance().getName());
				Resilience.getInstance().getFileManager().loadEnabledMods();
			}else{
				enableButton.displayString = "Enable ".concat(Resilience.getInstance().getName());
				for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
					if(mod.getCategory() != ModuleCategory.COMBAT_EXTENSION){
						mod.setEnabled(false);
					}
				}
				Resilience.getInstance().getInvoker().setGammaSetting(1);
				for(int i=0; i<150; i++){
					Resilience.getInstance().getInvoker().addChatMessage("");
				}
				Resilience.getInstance().getInvoker().displayScreen(null);
			}
		}
		if(invoker.getId(btn) == 2){
			invoker.displayScreen(parent);
		}else if(invoker.getId(btn) == 3){
			new Thread(){
				public void run(){
					if(!Resilience.getInstance().isNewAccount()){
						String result = Utils.sendGetRequest("http://resilience.krispdev.com/updateOnline.php?ign="+Resilience.getInstance().getInvoker().getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&online=true&channel="+Resilience.getInstance().getValues().userChannel);
						
						if(result.equals("")){
							Resilience.getInstance().getInvoker().displayScreen(new GuiFriendManager(instance));
						}else{
							Resilience.getInstance().getInvoker().displayScreen(new GuiLogin(instance, false));
						}
					}else{
						Resilience.getInstance().getInvoker().displayScreen(new GuiRegister(new GuiFriendManager(instance), true, false));
					}
				}
			}.start();
		}
	}
	
}
