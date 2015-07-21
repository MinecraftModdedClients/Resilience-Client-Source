package com.krispdev.resilience.module.modules.movement;

import java.text.DecimalFormat;
import java.util.ArrayList;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnOutwardPacket;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleBlink extends DefaultModule{
	
	private ArrayList<Packet> savedPackets = new ArrayList<Packet>();
	private Vector3f firstPosition;
	
	public ModuleBlink(){
		super("Blink", Keyboard.KEY_V, NoCheatMode.SEMICOMPATIBLE);
		this.setCategory(ModuleCategory.MOVEMENT);
		this.setDescription("Allows you to teleport around by enabling/disabling blink");
		this.setSave(false);
	}

	@Override
	public void onOutwardPacket(EventOnOutwardPacket event){
		Packet packet = event.getPacket();
		
		if(packet instanceof C03PacketPlayer){
			savedPackets.add(packet);
			event.setCancelled(true);
		}
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		float x = (float)invoker.getPosX() - firstPosition.x;
		float y = (float)invoker.getPosY() - firstPosition.y;
		float z = (float)invoker.getPosZ() - firstPosition.z;
		
		this.setDisplayName("Blink ("+new DecimalFormat("#.#").format(Math.sqrt(x*x + y*y + z*z))+")");
	}
	
	@Override
	public void onEnable() {
		if(invoker.getWrapper().getPlayer() != null){
			firstPosition = new Vector3f((float)invoker.getPosX(), (float)invoker.getPosY(), (float)invoker.getPosZ());
		}
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
		
		this.setDisplayName("Blink");
		
		for(Packet packet : savedPackets){
			invoker.sendPacket(packet);
		}
		
		savedPackets.clear();
	}
	
}
