package com.krispdev.resilience.module.modules.movement;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleSafeWalk extends DefaultModule{
	
	public ModuleSafeWalk(){
		super("SafeWalk", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Prevents you from falling off cliffs");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().safeWalkEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().safeWalkEnabled = false;
	}
	
}
