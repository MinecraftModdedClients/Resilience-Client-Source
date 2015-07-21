package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleGod extends DefaultModule{
	
	private int ticks = 0;
	
	public ModuleGod(){
		super("God", 0, NoCheatMode.VANILLAONLY);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Makes you invinsible! For vanilla servers ONLY.");
	}

	@Override
	public void onPreMotion(EventPreMotion event){
		ticks++;
		if(ticks > -1){
			ticks = 0;
			event.setCancelled(true);
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
