package com.krispdev.resilience.module.modules.movement;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class ModuleFlight extends DefaultModule{
	
	public ModuleFlight() {
		super("Flight", Keyboard.KEY_F, NoCheatMode.INCOMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Allows you to fly");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().flightEnabled = true;
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		this.setDisplayName("Flight ("+Math.round(Resilience.getInstance().getValues().flySpeed.getValue())+")");
		
		invoker.setMotionX(0);
		invoker.setMotionY(0);
		invoker.setMotionZ(0);
		
		invoker.setLandMovementFactor(Resilience.getInstance().getValues().flySpeed.getValue()/3);
		invoker.setJumpMovementFactor(Resilience.getInstance().getValues().flySpeed.getValue()/3);
		
		if(Keyboard.isKeyDown(invoker.getJumpCode())){
			invoker.setMotionY(invoker.getMotionY()+Resilience.getInstance().getValues().flySpeed.getValue()/4);
		}
		
		if(Keyboard.isKeyDown(invoker.getSneakCode())){
			invoker.setMotionY(invoker.getMotionY()-Resilience.getInstance().getValues().flySpeed.getValue()/4);
		}
	}
	
	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().flightEnabled = false;
		Resilience.getInstance().getEventManager().unregister(this);
	}

}
