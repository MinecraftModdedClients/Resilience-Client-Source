package com.krispdev.resilience.module.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.game.EntityUtils;

public class ModuleForceField extends DefaultModule{
	
	private EntityUtils entityUtils = new EntityUtils();
	
	public ModuleForceField(){
		super("ForceField", 0, NoCheatMode.INCOMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("For unprotected servers. Attacks entities.");
	}
	
	@Override
	public void onEnable(){
		Resilience.getInstance().getEventManager().register(this);
		if(Resilience.getInstance().getWrapper().getPlayer() == null || Resilience.getInstance().getWrapper().getWorld() == null) return;
		Resilience.getInstance().getLogger().warningChat("ForceField does not bypass protection on most servers. Use KillAura instead!");
	}
	
	public void onUpdate(EventOnUpdate event){
		try {
			for(Object o : invoker.getEntityList()){
				EntityLivingBase entity = null; 
				if(o instanceof EntityLivingBase) entity = (EntityLivingBase) o;
				if(entity != null && entityUtils.isWithinRange(4.2F, entity) && !entityUtils.isEntityFriend(entity) && !entityUtils.isEntityDead(entity) && !entityUtils.isThePlayer(entity)){
					entityUtils.hitEntity(entity, Resilience.getInstance().getValues().autoBlockEnabled, true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
