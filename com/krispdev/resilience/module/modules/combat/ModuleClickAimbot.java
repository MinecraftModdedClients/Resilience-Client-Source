package com.krispdev.resilience.module.modules.combat;

import net.minecraft.entity.Entity;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.game.EntityUtils;

public class ModuleClickAimbot extends DefaultModule{
	
	private Entity selectedEntity;
	private EntityUtils utils = new EntityUtils();
	
	public ModuleClickAimbot(){
		super("Click Aimbot", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically aims at the closest entity when you click");
	}

	@Override
	public void onClick(EventOnClick event){
		if(event.getButton() == 0){
			selectedEntity = utils.getClosestEntity(invoker.getWrapper().getPlayer(), Resilience.getInstance().getValues().players.getState(), Resilience.getInstance().getValues().mobs.getState(), Resilience.getInstance().getValues().animals.getState(), Resilience.getInstance().getValues().invisibles.getState(), Resilience.getInstance().getValues().propBlocks.getState());
			
			if(selectedEntity != null && utils.isWithinRange(4, selectedEntity) && utils.canHit(selectedEntity) && !utils.isEntityFriend(selectedEntity)){
				utils.faceEntity(selectedEntity);
				utils.hitEntity(selectedEntity);
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
