package com.krispdev.resilience.module.modules.combat;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleCriticals extends DefaultModule{
	
	public ModuleCriticals(){
		super("Criticals", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically lands criticals when you click");
	}
	
	@Override
	public void onClick(EventOnClick event){
		if(event.getButton() == 0 && invoker.isOnGround() && !invoker.isInWater() && ((invoker.getObjectMouseOver() != null && invoker.getObjectMouseOver().typeOfHit == MovingObjectType.ENTITY) || (event.isAutoClick() && Resilience.getInstance().getValues().killAuraEnabled))){
			invoker.setMotionY(0.290000000);
			invoker.setFallDistance(0.289F);
			invoker.setOnGround(false);
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
