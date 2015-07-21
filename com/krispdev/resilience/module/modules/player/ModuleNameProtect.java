package com.krispdev.resilience.module.modules.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleNameProtect extends DefaultModule{
	
	public ModuleNameProtect(){
		super("NameProtect", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.PLAYER);
		this.setDescription("Protects your name");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().nameProtectEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().nameProtectEnabled = false;
	}
	
}
