package com.krispdev.resilience.gui.screens.managers.modules;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class GuiModuleManager extends GuiScreen{

	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	private GuiScreen parent;
	public static ArrayList<DefaultModule> visibleModules = new ArrayList<DefaultModule>();
	private GuiModuleSlot moduleSlot;
	private String state = "Waiting for command...";
	private boolean bind;
	
	public GuiModuleManager(GuiScreen parent){
		visibleModules.clear();
		this.parent = parent;
		for(DefaultModule module : Resilience.getInstance().getModuleManager().moduleList){
			visibleModules.add(module);
		}
	}
	
	@Override
	public void initGui(){
		moduleSlot = new GuiModuleSlot(invoker.getWrapper().getMinecraft(), this);
		moduleSlot.registerScrollButtons(7, 8);
		invoker.addButton(this, new ResilienceButton(1, width/2-100, height-54+1+2, 200, 20, "Toggle"));
		invoker.addButton(this, new ResilienceButton(3, width/2-100, height-30+1+2, 200, 20, "Reload"));
		invoker.addButton(this, new ResilienceButton(5, width/2+104, height-53+2, 100, 20, "Reset"));
		invoker.addButton(this, new ResilienceButton(6, width/2-204, height-53+2, 100, 20, "Remove"));
		invoker.addButton(this, new ResilienceButton(2, width/2-204, height-30+1+2, 100, 20, "Change Bind"));
		invoker.addButton(this, new ResilienceButton(4, width/2+104, height-30+1+2, 100, 20, "Clear Bind"));
		invoker.addButton(this, new ResilienceButton(0, 4, 4, 50, 20, "Back"));
	}
	
	@Override
	public void drawScreen(int i, int j, float f){
		moduleSlot.drawScreen(i, j, f);
		Resilience.getInstance().getStandardFont().drawCenteredString(state, invoker.getWidth()/2, 4, 0xffffffff);
		super.drawScreen(i, j, f);
	}
	
	@Override
	public void actionPerformed(GuiButton btn){
		DefaultModule module = null; 
		try{
			module = visibleModules.get(moduleSlot.getSelectedSlot());
		}catch(Exception e){}
		if(invoker.getId(btn) == 1 && module != null){
			module.toggle();
			state = "\247bToggled "+module.getName();
			Resilience.getInstance().getFileManager().saveEnabledMods();
		}else if(invoker.getId(btn) == 2 && module != null){
			state = "\247bPress any key now...";
			bind = true;
		}else if(invoker.getId(btn) == 5 && module != null){
			try{
				int index = Resilience.getInstance().getModuleManager().moduleList.indexOf(module);
				Class clazz = module.getClass();
				String pack = clazz.getPackage().getName();
				String name = clazz.getSimpleName();
				Class newClass = Class.forName(pack+"."+name);
				Object obj = newClass.newInstance();
				if(obj instanceof DefaultModule){
					DefaultModule mod = (DefaultModule) obj;
					Resilience.getInstance().getModuleManager().moduleList.remove(index);
					Resilience.getInstance().getModuleManager().moduleList.add(index, mod);
					visibleModules.remove(index);
					visibleModules.add(index, mod);
					Resilience.getInstance().getFileManager().saveBinds();
				}else{
					state = "\247cA strange error occured! Object is not a module???";
				}
				state = "\247bReset "+module.getName()+" to default settings";
			}catch(Exception e){
				e.printStackTrace();
				state = "\247cError reseting "+module.getName();
			}
		}else if(invoker.getId(btn) == 4 && module != null){
			module.setKeyBind(0);
			Resilience.getInstance().getFileManager().saveBinds();
		}else if(invoker.getId(btn) == 3){
			Resilience.getInstance().getModuleManager().moduleList.clear();
			Resilience.getInstance().getModuleManager().instantiateModules();
			
			visibleModules.clear();
			for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
				visibleModules.add(mod);
			}
			state = "\247bReloaded all modules";
		}else if(invoker.getId(btn) == 6 && module != null){
			state = "\247bRemoved "+module.getName();
			module.setEnabled(false);
			Resilience.getInstance().getEventManager().unregisterGameListener(module);
			int index = Resilience.getInstance().getModuleManager().moduleList.indexOf(module);
			Resilience.getInstance().getModuleManager().moduleList.remove(index);
			visibleModules.remove(index);
		}else{
			invoker.displayScreen(parent);
		}
	}

	@Override
	public void keyTyped(char c, int i){
		if(bind){
			bind = false;
			DefaultModule module = visibleModules.get(moduleSlot.getSelectedSlot());
			if(i == 1){
				state = "\247bCancelled keybind";
				return;
			}
			module.setKeyBind(i);
			Resilience.getInstance().getFileManager().saveBinds();
			state = "\247bSet the keybind of "+module.getName()+" to "+Keyboard.getKeyName(i);
		}
	}
	
}
