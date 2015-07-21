package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleNoSlowdown extends DefaultModule{
	
	public ModuleNoSlowdown(){
		super("NoSlowdown", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Prevents slowdown from soulsand and water");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().noSlowdownEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().noSlowdownEnabled = false;
	}
	
}
