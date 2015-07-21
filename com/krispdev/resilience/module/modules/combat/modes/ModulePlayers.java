package com.krispdev.resilience.module.modules.combat.modes;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModulePlayers extends DefaultModule{

	public ModulePlayers(){
		super("Target Players", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT_EXTENSION);
		this.setDescription("Do you want combat mods to target players?");
		this.setVisible(false);
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().players.setState(true);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().players.setState(false);
	}
	
}
