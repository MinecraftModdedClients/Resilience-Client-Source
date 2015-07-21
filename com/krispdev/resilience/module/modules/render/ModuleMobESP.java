package com.krispdev.resilience.module.modules.render;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;

public class ModuleMobESP extends DefaultModule{
	
	public ModuleMobESP(){
		super("MobESP", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Draws colour coded boxes around mobs and animals");
	}

	@Override
	public void onRender(EventOnRender event){
		for(Object o : invoker.getEntityList()){
			if(o instanceof EntityPlayer) continue;
			if(o instanceof EntityLivingBase){
				EntityLivingBase entity = (EntityLivingBase) o;
				
				boolean mob = (entity instanceof EntityMob);
				
				RenderUtils.drawESP(false,
						invoker.getBoundboxMinX(entity) - invoker.getRenderPosX() - 0.1, 
						invoker.getBoundboxMinY(entity) - invoker.getRenderPosY() - 0.1, 
						invoker.getBoundboxMinZ(entity) - invoker.getRenderPosZ() - 0.1, 
						invoker.getBoundboxMaxX(entity) - invoker.getRenderPosX() + 0.1, 
						invoker.getBoundboxMaxY(entity) - invoker.getRenderPosY() + 0.1, 
						invoker.getBoundboxMaxZ(entity) - invoker.getRenderPosZ() + 0.1, 
						1, 1, 1, 0.19, mob ? 1 : 0, mob ? 0 : 1, mob ? 0 : 1, 1);
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
