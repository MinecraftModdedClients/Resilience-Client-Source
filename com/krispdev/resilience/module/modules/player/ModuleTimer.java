package com.krispdev.resilience.module.modules.player;

import java.text.DecimalFormat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventValueChange;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleTimer extends DefaultModule{
	
	public ModuleTimer(){
		super("Speed", 0, NoCheatMode.INCOMPATIBLE);
		this.setCategory(ModuleCategory.PLAYER);
		this.setDescription("Slows down/speeds up time");
		this.setSave(false);
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
		this.setDisplayName("Timer ("+new DecimalFormat("#.#").format(Resilience.getInstance().getValues().timerSpeed.getValue())+")");
		invoker.setTimerSpeed(Resilience.getInstance().getValues().timerSpeed.getValue());
	}
	
	@Override
	public void onValueChange(EventValueChange event){
		if(event.getValue() == Resilience.getInstance().getValues().timerSpeed){
			invoker.setTimerSpeed(Resilience.getInstance().getValues().timerSpeed.getValue());
			this.setDisplayName("Timer ("+new DecimalFormat("#.#").format(Resilience.getInstance().getValues().timerSpeed.getValue())+")");
		}
	}
	
	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
		invoker.setTimerSpeed(1);
	}
	
}
