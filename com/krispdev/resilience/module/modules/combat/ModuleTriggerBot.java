package com.krispdev.resilience.module.modules.combat;

import net.minecraft.entity.Entity;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.utilities.game.EntityUtils;

public class ModuleTriggerBot extends DefaultModule{

	private EntityUtils entityUtils = new EntityUtils();
	private Timer timer = new Timer();
	
	public ModuleTriggerBot(){
		super("TriggerBot", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically hits the entity you hover over");
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		if(invoker.getObjectMouseOver() != null){
			Entity e = invoker.getObjectMouseOver().entityHit;
		
			if(e!=null){
				if(entityUtils.canHit(e, Resilience.getInstance().getValues().range.getValue()) && !entityUtils.isEntityFriend(e)){
					if(timer.hasTimePassed(1000/(long)Resilience.getInstance().getValues().speed.getValue())){
						entityUtils.hitEntity(e, Resilience.getInstance().getValues().autoBlockEnabled, true);
					}
				}
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
