package com.krispdev.resilience.module.modules.render;

import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;

public class ModulePlayerESP extends DefaultModule{

	public ModulePlayerESP(){
		super("PlayerESP", Keyboard.KEY_P, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Draws a box around players");
	}

	@Override
	public void onRender(EventOnRender event){
		for(Object o : invoker.getEntityList()){
			if(o instanceof EntityPlayer){
				RenderUtils.drawPlayerESP((EntityPlayer)o);
			}
		}
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
