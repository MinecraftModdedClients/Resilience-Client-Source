package com.krispdev.resilience.module.modules.combat;

import net.minecraft.network.play.client.C03PacketPlayer;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleRegen extends DefaultModule{
	
	private boolean shouldHeal = true;
	
	public ModuleRegen(){
		super("Regen", 0, NoCheatMode.INCOMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Regenerates your health");
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		if(invoker.getFoodLevel()>17 && !invoker.isInCreativeMode() && invoker.getHealth() < 19 && invoker.isOnGround() && shouldHeal){
			shouldHeal = false;
			new Thread(){
				public void run(){
					for(short s=0; s<Resilience.getInstance().getValues().regenSpeed.getValue(); s++){
						invoker.sendPacket(new C03PacketPlayer());
					}
					shouldHeal = true;
				}
			}.start();
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
