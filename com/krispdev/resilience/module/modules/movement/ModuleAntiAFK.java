package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Timer;

public class ModuleAntiAFK extends DefaultModule{
	
	private Timer timer = new Timer();
	
	public ModuleAntiAFK(){
		super("AntiAFK", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Jumps every X seconds to prevent AFK");
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		if(timer.hasTimePassed((long)Resilience.getInstance().getValues().antiAFKSeconds.getValue()*1000) && Resilience.getInstance().getWrapper().getPlayer() != null && invoker.isOnGround()){
			timer.reset();
			invoker.jump();
		}
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
