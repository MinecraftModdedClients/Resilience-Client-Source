package com.krispdev.resilience.module.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventGameShutdown;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleBrightness extends DefaultModule{
	
	private float prevGammaSetting = -1;
	private float target = 8;
	private float fadeSpeed = 0.1F;
	
	private boolean shouldFadeOut = false;
	
	public ModuleBrightness(){
		super("Brightness", Keyboard.KEY_B, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Lights up the world");
	}

	@Override
	public void onEnable(){
		if(Resilience.getInstance().getWrapper().getWorld() != null && Resilience.getInstance().getWrapper().getPlayer() != null){
			prevGammaSetting = invoker.getGammaSetting();
			shouldFadeOut = false;
		}
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		fadeSpeed = 0.3F;
		
		float gammaSetting = invoker.getGammaSetting();
		
		if(gammaSetting < target && !shouldFadeOut){
			if(gammaSetting < 1){
				gammaSetting = 1;
			}
			invoker.setGammaSetting(gammaSetting+fadeSpeed);
		}
		
		if(shouldFadeOut){
			if(gammaSetting > 1){
				if(gammaSetting - fadeSpeed < 1){
					invoker.setGammaSetting(1);
					Resilience.getInstance().getEventManager().unregister(this);
					shouldFadeOut = false;
					return;
				}else{
					invoker.setGammaSetting(gammaSetting-fadeSpeed);
				}
			}else{
				shouldFadeOut = false;
				Resilience.getInstance().getEventManager().unregister(this);
			}
		}
	}
	
	@Override
	public void onDisable(){
		shouldFadeOut = true;
	}
	
	@Override
	public void onGameShutdown(EventGameShutdown event){
		if(prevGammaSetting != -1){
			invoker.setGammaSetting(prevGammaSetting);
		}
	}
	
}
