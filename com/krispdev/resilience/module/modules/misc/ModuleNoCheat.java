package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnModuleToggle;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleNoCheat extends DefaultModule{
	
	public ModuleNoCheat(){
		super("NoCheat", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Turns off and warns mods that may be incompatible with NoCheatPlus");
	}

	@Override
	public void onModuleToggle(EventOnModuleToggle event){
		DefaultModule module = event.getModule();
		
		if(module.isEnabled()){
			return;
		}
		
		if(module.getNoCheatMode() == NoCheatMode.INCOMPATIBLE || module.getNoCheatMode() == NoCheatMode.VANILLAONLY){
			event.setCancelled(true);
			Resilience.getInstance().getLogger().warningChat("\247cThe mod "+module.getName()+" does not bypass NoCheat and has been turned off. Turn off \"NoCheat\" to use this mod.");
			return;
		}
		
		if(module.getNoCheatMode() == NoCheatMode.SEMICOMPATIBLE){
			Resilience.getInstance().getLogger().warningChat("\247cThe mod "+module.getName()+" only bypasses NoCheat on SOME servers! Use with caution!");
		}
	}
	
	@Override
	public void onEnable() {
		try{
			for(DefaultModule module : Resilience.getInstance().getModuleManager().moduleList){
				if(!module.isEnabled()) continue;
				
				if(module.getNoCheatMode() == NoCheatMode.INCOMPATIBLE || module.getNoCheatMode() == NoCheatMode.VANILLAONLY){
					module.setEnabled(false);
					Resilience.getInstance().getLogger().warningChat("\247cThe mod "+module.getName()+" does not bypass NoCheat and has been turned off. Turn off \"NoCheat\" to use this mod.");
				}
				
				if(module.getNoCheatMode() == NoCheatMode.SEMICOMPATIBLE){
					Resilience.getInstance().getLogger().warningChat("\247cThe mod "+module.getName()+" only bypasses NoCheat on SOME servers! Use with caution!");
				}
			}
			Resilience.getInstance().getEventManager().register(this);
		}catch(Exception e){}
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
	}

}
