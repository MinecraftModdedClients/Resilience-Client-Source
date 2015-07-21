package com.krispdev.resilience.module.modules.combat.modes;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleSafeMode extends DefaultModule{
	
	public ModuleSafeMode(){
		super("Safe Mode", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT_EXTENSION);
		this.setDescription("Makes KillAura only attack people in ");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().safeMode.setState(true);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().safeMode.setState(false);
	}
	
}
