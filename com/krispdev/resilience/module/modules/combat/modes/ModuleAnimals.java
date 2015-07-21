package com.krispdev.resilience.module.modules.combat.modes;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAnimals extends DefaultModule{
	
	public ModuleAnimals(){
		super("Target Animals", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT_EXTENSION);
		this.setDescription("Do you want combat mods to target animals?");
		this.setVisible(false);
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().animals.setState(true);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().animals.setState(false);
	}
	
}
