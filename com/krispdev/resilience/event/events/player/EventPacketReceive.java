package com.krispdev.resilience.event.events.player;

import net.minecraft.network.Packet;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.Cancellable;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;

public class EventPacketReceive extends Cancellable implements Event{
	
	private Packet packet;
	
	public EventPacketReceive(Packet packet){
		this.packet = packet;
	}
	
	@Override
	public void onEvent(){
		if(!Resilience.getInstance().isEnabled()) return;
		try{
			for(Listener l : Resilience.getInstance().getEventManager().eventListeners){
				if(l instanceof ModListener){
					ModListener mod = (ModListener) l;
					mod.onPacketReceive(this);
				}
			}
		}catch(Exception e){}
	}
	
	public Packet getPacket(){
		return packet;
	}

}
