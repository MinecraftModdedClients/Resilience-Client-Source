package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModulePhaze extends DefaultModule{
	
	
	public ModulePhaze(){
		super("Phaze", 0, NoCheatMode.SEMICOMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Allows you to glitch through doors, etc.");
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		invoker.setNoClip(true);
		invoker.setOnGround(true);
	}
	
	public void onDisable(){
		Resilience.getInstance().getEventManager().unregister(this);
		if(Resilience.getInstance().getWrapper().getPlayer() == null) return;
		invoker.setNoClip(false);
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}
}
