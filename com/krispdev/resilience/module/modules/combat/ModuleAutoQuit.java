package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventHealthUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class ModuleAutoQuit extends DefaultModule{
	
	public static NumberValue quitHealth = new NumberValue(6, 1, 20, "AutoQuit Health", true);
	
	public ModuleAutoQuit(){
		super("AutoQuit", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically quits the game when your health gets low");
	}

	@Override
	public void onHealthUpdate(EventHealthUpdate event){
		if(event.getHealth() <= quitHealth.getValue()){
			invoker.sendChatMessage("\247bHello");
			this.setEnabled(false);
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
