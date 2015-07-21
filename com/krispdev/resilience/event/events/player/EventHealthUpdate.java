package com.krispdev.resilience.event.events.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;

public class EventHealthUpdate implements Event{

	private float health;
	
	public EventHealthUpdate(float health) {
		this.health = health;
	}

	@Override
	public void onEvent() {
		if(!Resilience.getInstance().isEnabled()) return;
		try{
			for(Listener l : Resilience.getInstance().getEventManager().eventListeners){
				if(l instanceof ModListener){
					ModListener mod = (ModListener) l;
					mod.onHealthUpdate(this);
				}
			}
		}catch(Exception e){}		
	}

	public float getHealth() {
		return health;
	}
	
}
