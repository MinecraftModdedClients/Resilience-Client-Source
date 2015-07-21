package com.krispdev.resilience.module.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.utilities.game.EntityUtils;

public class ModuleKillAura extends DefaultModule{
	
	private EntityUtils entityUtils = new EntityUtils();
	
	private Timer timer = new Timer();
	
	private Entity target = null;
	
	public ModuleKillAura(){
		super("KillAura", Keyboard.KEY_R, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically attacks entities");
	}

	@Override
	public void onPreMotion(EventPreMotion event){
		target = null;
		target = entityUtils.getClosestEntity(Resilience.getInstance().getWrapper().getPlayer(), Resilience.getInstance().getValues().players.getState(), Resilience.getInstance().getValues().mobs.getState(), Resilience.getInstance().getValues().animals.getState(), Resilience.getInstance().getValues().invisibles.getState(), Resilience.getInstance().getValues().propBlocks.getState());
		if(target != null && entityUtils.canHit(target) && (Resilience.getInstance().getValues().safeMode.getState() ? entityUtils.withinRotation(target, 90) : true)){
			invoker.setSprinting(false);
			entityUtils.faceEntity(target);
		}
	}
	
	@Override
	public void onPostMotion(EventPostMotion event){
		if(target != null && timer.hasTimePassed(1000/(long) Resilience.getInstance().getValues().speed.getValue()) && entityUtils.isWithinRange(Resilience.getInstance().getValues().range.getValue(),  target) && entityUtils.canHit(target) && (Resilience.getInstance().getValues().safeMode.getState() ? entityUtils.withinRotation(target, 90) : true)){
			entityUtils.hitEntity(target, Resilience.getInstance().getValues().autoBlockEnabled, true);
			timer.reset();
		}
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getValues().killAuraEnabled = true;
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().killAuraEnabled = false;
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
