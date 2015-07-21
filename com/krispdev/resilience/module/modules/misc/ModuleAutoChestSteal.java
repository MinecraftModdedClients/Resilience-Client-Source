package com.krispdev.resilience.module.modules.misc;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAutoChestSteal extends DefaultModule	{

	public ModuleAutoChestSteal(){
		super("Auto Chest Steal", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Automatically steals when you open a chest");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().autoChestStealEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().autoChestStealEnabled = false;
	}
	
}
