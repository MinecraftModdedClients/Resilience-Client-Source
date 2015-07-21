package com.krispdev.resilience.module.modules.render;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleNoFireEffect extends DefaultModule{
	
	public ModuleNoFireEffect(){
		super("NoFireEffect", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Stops the fire effect");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().noFireEffectEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().noFireEffectEnabled = false;
	}
	
}
