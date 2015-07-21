package com.krispdev.resilience.module.modules.misc;

import net.minecraft.util.MovingObjectPosition;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAutoMine extends DefaultModule{
	
	public ModuleAutoMine(){
		super("AutoMine", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Automatically mines when you hover over a block");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		MovingObjectPosition hover = invoker.getObjectMouseOver();
		
		if(hover.typeOfHit != null){
			if(hover.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
				invoker.setKeyBindAttackPressed(true);
			}
		}else{
			invoker.setKeyBindAttackPressed(false);
		}
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		if(Resilience.getInstance().getWrapper().getGameSettings() != null && Resilience.getInstance().getWrapper().getPlayer() != null){
			invoker.setKeyBindAttackPressed(false);
		}
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
