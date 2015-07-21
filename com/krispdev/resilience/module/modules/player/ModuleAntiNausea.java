package com.krispdev.resilience.module.modules.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAntiNausea extends DefaultModule{

	public ModuleAntiNausea(){
		super("Anti Nausea", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.PLAYER);
		this.setDescription("Prevens the nausea potion effect");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().antiNauseaEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().antiNauseaEnabled = false;
	}
	
}
