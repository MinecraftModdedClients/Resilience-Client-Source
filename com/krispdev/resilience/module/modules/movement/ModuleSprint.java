package com.krispdev.resilience.module.modules.movement;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleSprint extends DefaultModule{

	public ModuleSprint() {
		super("Sprint", Keyboard.KEY_C, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Forces sprint");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		if(!invoker.isSneaking() && !invoker.isCollidedHorizontally() && !invoker.isOnLadder() && invoker.moveForward() > 0){
			invoker.setSprinting(true);
		}
	}
	
	@Override
	public void onDisable() {
		if(invoker.getWrapper().getPlayer() != null){
			invoker.setSprinting(false);
		}
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
