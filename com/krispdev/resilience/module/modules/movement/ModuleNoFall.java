package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleNoFall extends DefaultModule{
	
	private boolean wasOnGround = false;
	
	public ModuleNoFall(){
		super("NoFall", 0, NoCheatMode.INCOMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Avoids fall damage");
	}

	@Override
	public void onPreMotion(EventPreMotion event){
		wasOnGround = invoker.isOnGround();
		
		invoker.setOnGround(true);
	}
	
	@Override
	public void onPostMotion(EventPostMotion event){
		invoker.setOnGround(wasOnGround);
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
