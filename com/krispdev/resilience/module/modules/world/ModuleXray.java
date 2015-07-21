package com.krispdev.resilience.module.modules.world;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventGameShutdown;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleXray extends DefaultModule{

	private int prevSmoothLighting = 2;
	
	public ModuleXray(){
		super("Xray", Keyboard.KEY_X, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.WORLD);
		this.setDescription("Allows you to see through blocks to find ores");
		this.setSave(false);
	}

	@Override
	public void onEnable(){
		prevSmoothLighting = invoker.getSmoothLighting();
		invoker.setSmoothLighting(2);
		Resilience.getInstance().getXrayUtils().xrayEnabled = true;
	}

	@Override
	public void onToggle(){
		Resilience.getInstance().getInvoker().loadRenderers();
	}
	
	@Override
	public void onDisable() {
		invoker.setSmoothLighting(prevSmoothLighting);
		Resilience.getInstance().getXrayUtils().xrayEnabled = false;
	}
	
	@Override
	public void onGameShutdown(EventGameShutdown event){
		invoker.setSmoothLighting(prevSmoothLighting);
	}
	
}
