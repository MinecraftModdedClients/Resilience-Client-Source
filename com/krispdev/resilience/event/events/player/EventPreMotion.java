package com.krispdev.resilience.event.events.player;

import net.minecraft.entity.player.EntityPlayer;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.Cancellable;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;

public class EventPreMotion extends Cancellable implements Event{
	
	private EntityPlayer ep;
	
	public EventPreMotion(EntityPlayer ep){
		this.ep = ep;
	}
	
	public void onEvent(){
		if(!Resilience.getInstance().isEnabled()) return;
		try{
			for(Listener l : Resilience.getInstance().getEventManager().eventListeners){
				if(l instanceof ModListener){
					ModListener mod = (ModListener) l;
					mod.onPreMotion(this);
				}
			}
		}catch(Exception e){}
	}
	
}
