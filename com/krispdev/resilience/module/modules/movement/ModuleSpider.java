package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleSpider extends DefaultModule{
	
	public ModuleSpider(){
		super("Spider", 0, NoCheatMode.INCOMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Climbs up walls");
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		if(invoker.isCollidedHorizontally()){
			invoker.setMotionY(0.2);
			
			float var6 = 0.15F;

	        if (invoker.getMotionX() < (double)(-var6))
	        {
	            invoker.setMotionX((double)(-var6));
	        }

	        if (invoker.getMotionX() > (double)var6)
	        {
	            invoker.setMotionX((double)var6);
	        }

	        if (invoker.getMotionZ() < (double)(-var6))
	        {
	            invoker.setMotionZ((double)(-var6));
	        }

	        if (invoker.getMotionZ() > (double)var6)
	        {
	            invoker.setMotionZ((double)var6);
	        }

	        invoker.setFallDistance(0);
	        
	        if (invoker.getMotionY() < -0.15D)
	        {
	            invoker.setMotionY(-0.15D);
	        }
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
