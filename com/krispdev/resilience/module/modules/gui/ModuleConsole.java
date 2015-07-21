package com.krispdev.resilience.module.modules.gui;

import net.minecraft.client.gui.GuiChat;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.gui.screens.ResilienceConsole;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleConsole extends DefaultModule{

	public ModuleConsole(){
		super("Console", Keyboard.KEY_MINUS, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.GUI);
		this.setDescription("Type commands and make the client do stuff");
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onUpdate(EventOnUpdate event){
        if(Keyboard.isKeyDown(getKeyCode()) && invoker.getCurrentScreen() == null){
        	invoker.displayScreen(new ResilienceConsole());
        }
	}
	
	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}
	
}
