package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleStep extends DefaultModule{

	public ModuleStep(){
		super("Step", 0, NoCheatMode.INCOMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Automatically steps up blocks like stairs");
	}
	
	public void onUpdate(EventOnUpdate event){
		invoker.setStepHeight(Resilience.getInstance().getValues().stepHeight.getValue());
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		if(invoker.getWrapper().getPlayer() != null){
			invoker.setStepHeight(0.5F);
		}
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
