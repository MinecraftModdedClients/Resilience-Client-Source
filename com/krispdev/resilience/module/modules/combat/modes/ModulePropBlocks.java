package com.krispdev.resilience.module.modules.combat.modes;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModulePropBlocks extends DefaultModule{
	
	public ModulePropBlocks(){
		super("Target PropBlocks", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT_EXTENSION);
		this.setDescription("Do you want combat mods to target Prophunt blocks?");
		this.setVisible(false);
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().propBlocks.setState(true);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().propBlocks.setState(false);
	}
	
}
