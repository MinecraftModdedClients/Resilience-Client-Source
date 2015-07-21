package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleTwerk extends DefaultModule{
	
	private boolean skipTick;
	
	public ModuleTwerk(){
		super("Twerk", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Sneaks and unsneaks. AKA twerks.");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		if(!skipTick){
			skipTick = true;
			invoker.setSneakKeyPressed(true);
		}else{
			skipTick = false;
			invoker.setSneakKeyPressed(false);
		}
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
		invoker.setSneakKeyPressed(false);
	}
	
}
