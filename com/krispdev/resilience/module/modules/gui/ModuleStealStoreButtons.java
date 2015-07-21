package com.krispdev.resilience.module.modules.gui;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleStealStoreButtons extends DefaultModule{
	
	public ModuleStealStoreButtons(){
		super("Steal Store Buttons", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.GUI);
		this.setDescription("An option for the steal/store buttons above chests");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().stealStoreButtonsEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().stealStoreButtonsEnabled = false;
	}
	
}
