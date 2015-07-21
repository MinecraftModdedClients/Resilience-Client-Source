package com.krispdev.resilience.module.modules.gui;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleGui extends DefaultModule{
	
	public ModuleGui(){
		super("Gui", Keyboard.KEY_RSHIFT, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.GUI);
		this.setDescription("The user interface for the client");
	}

	@Override
	public void onEnable() {
		if(Resilience.getInstance().getWrapper().getWorld() == null || Resilience.getInstance().getWrapper().getPlayer() == null){
			return;
		}
		invoker.displayScreen(Resilience.getInstance().getClickGui());
	}

	@Override
	public void onDisable() {}
	
}
