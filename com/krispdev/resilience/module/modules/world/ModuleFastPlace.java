package com.krispdev.resilience.module.modules.world;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleFastPlace extends DefaultModule{
	
	public ModuleFastPlace(){
		super("FastPlace", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.WORLD);
		this.setDescription("Allows you to place blocks very fast");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		invoker.setRightDelayTimer(0);
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
		invoker.setRightDelayTimer(4);
	}
	
}
