package com.krispdev.resilience.module.modules.combat;

import net.minecraft.entity.Entity;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.game.EntityUtils;

public class ModuleAimbot extends DefaultModule{
	
	private Entity target;
	private EntityUtils entityUtils = new EntityUtils();
	
	public ModuleAimbot(){
		super("Aimbot", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically aims at the closest target");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		target = entityUtils.getClosestEntity(Resilience.getInstance().getWrapper().getPlayer(), Resilience.getInstance().getValues().players.getState(), Resilience.getInstance().getValues().mobs.getState(), Resilience.getInstance().getValues().animals.getState(), Resilience.getInstance().getValues().invisibles.getState(), Resilience.getInstance().getValues().propBlocks.getState());
		try{
			if(target != null && entityUtils.isWithinRange(Resilience.getInstance().getValues().range.getValue(), target)){
				entityUtils.faceEntity(target);
			}
		}catch(Exception e){}
	}
	
	@Override
	public void onEnable(){
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable(){
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
