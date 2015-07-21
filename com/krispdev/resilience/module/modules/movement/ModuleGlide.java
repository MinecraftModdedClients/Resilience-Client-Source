package com.krispdev.resilience.module.modules.movement;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleGlide extends DefaultModule{

	public ModuleGlide(){
		super("Glide", Keyboard.KEY_G, NoCheatMode.SEMICOMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Gives you the ability to glide");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		if(invoker.getMotionY() <= -0.15 && !invoker.isInWater() && !invoker.isOnGround() && !invoker.isOnLadder() && !Resilience.getInstance().getValues().flightEnabled){
			invoker.setMotionY(-0.15);
			invoker.setOnGround(true);
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
