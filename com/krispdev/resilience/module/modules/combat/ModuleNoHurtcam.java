package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleNoHurtcam extends DefaultModule{

	public ModuleNoHurtcam(){
		super("NoHurtcam", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Prevents the hurtcam when you get hit");
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().noHurtcamEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().noHurtcamEnabled = false;
	}
	
}
