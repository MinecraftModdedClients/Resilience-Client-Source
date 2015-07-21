package com.krispdev.resilience.event.events.player;

import net.minecraft.entity.player.EntityPlayer;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;

public class EventPostMotion implements Event{
	
	private EntityPlayer player;
	
	public EventPostMotion(EntityPlayer player){
		this.player = player;
	}
	
	public EntityPlayer getPlayer(){
		return player;
	}

	@Override
	public void onEvent() {
		if(!Resilience.getInstance().isEnabled()) return;
		try{
			for(Listener l : Resilience.getInstance().getEventManager().eventListeners){
				if(l instanceof ModListener){
					ModListener mod = (ModListener) l;
					mod.onPostMotion(this);
				}
			}
		}catch(Exception e){}
	}
	
}
