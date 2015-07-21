package com.krispdev.resilience.module.modules.player;

import java.util.Random;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleRetard extends DefaultModule{
	
	private Random rand = new Random();
	private int ticks = 0;
	
	public ModuleRetard(){
		super("Retard", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.PLAYER);
		this.setDescription("Spinns ur hed arund n mackes u luk funy");
	}

	@Override
	public void onPreMotion(EventPreMotion event){
		ticks++;
		
		if(ticks > rand.nextInt(50)){
			ticks = 0;
			invoker.swingItem();
		}
		
		if(invoker.moveForward() < 0.1){
			float yaw = rand.nextInt(360)-180;
			float pitch = rand.nextInt(360)-180;

			invoker.setRotationPitch(pitch);
			invoker.setRotationYaw(yaw);
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
