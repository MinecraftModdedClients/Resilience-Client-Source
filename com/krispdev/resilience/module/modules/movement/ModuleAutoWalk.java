package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAutoWalk extends DefaultModule{
	
	public ModuleAutoWalk(){
		super("AutoWalk", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Automatically walks");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		invoker.setForwardKeyPressed(true);
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		invoker.setForwardKeyPressed(false);
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
