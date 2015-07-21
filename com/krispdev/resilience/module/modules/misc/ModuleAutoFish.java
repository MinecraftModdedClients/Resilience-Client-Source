package com.krispdev.resilience.module.modules.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventPacketReceive;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAutoFish extends DefaultModule{
	
	public ModuleAutoFish(){
		super("AutoFish", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Automatically fishes for you; casts and recasts.");
	}
	
	@Override
	public void onPacketReceive(EventPacketReceive event){
		Packet packet = event.getPacket();
		if(packet instanceof S12PacketEntityVelocity){
			S12PacketEntityVelocity p = (S12PacketEntityVelocity) packet;
			
			Entity e = invoker.getEntityById(invoker.getPacketVelocityEntityId(p));
			
			if(e instanceof EntityFishHook){
				int x = invoker.getXMovePacketVel(p);
				int y = invoker.getYMovePacketVel(p);
				int z = invoker.getZMovePacketVel(p);
				
				if(x == 0 && y != 0 && z == 0){
					new Thread(){
						public void run(){
							try{
								Thread.sleep(40);
								invoker.rightClick();
								Thread.sleep(700);
								invoker.rightClick();
							}catch(InterruptedException e){
								e.printStackTrace();
							}
						}
					}.start();
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
