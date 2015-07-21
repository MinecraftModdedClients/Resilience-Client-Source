package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAutoJump extends DefaultModule{

	public ModuleAutoJump(){
		super("AutoJump", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Automatically jumps. Also known as bunnyhop");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		invoker.setJumpKeyPressed(true);
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		invoker.setJumpKeyPressed(false);
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
