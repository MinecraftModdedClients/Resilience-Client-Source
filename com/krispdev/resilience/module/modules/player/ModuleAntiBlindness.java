package com.krispdev.resilience.module.modules.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAntiBlindness extends DefaultModule{

	public ModuleAntiBlindness(){
		super("Anti Blindess", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.PLAYER);
		this.setDescription("Prevents the blindness potion effect");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().antiBlindessEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().antiBlindessEnabled = false;
	}
	
}
