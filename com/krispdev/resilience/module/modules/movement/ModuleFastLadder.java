package com.krispdev.resilience.module.modules.movement;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleFastLadder extends DefaultModule{
	
	private int ticks = 0;
	
	public ModuleFastLadder(){
		super("FastLadder", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Climbs up ladders faster than usual");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		ticks++;
		if(invoker.isOnLadder() && Keyboard.isKeyDown(invoker.getForwardCode())){
			invoker.setMotionY(0.25);
		}else if(invoker.isOnLadder() && !Keyboard.isKeyDown(invoker.getForwardCode())){
			invoker.setMotionY(-0.25);
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
