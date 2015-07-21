package com.krispdev.resilience.module.modules.world;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleWireFrame extends DefaultModule{
	
	public ModuleWireFrame(){
		super("WireFrame", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.WORLD);
		this.setDescription("Makes the world render wire-blocks");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().wireFrameEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().wireFrameEnabled = false;
	}
	
}
