package com.krispdev.resilience.event.events.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.Cancellable;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;

public class EventGameShutdown extends Cancellable implements Event{
	
	private long nanoTime;
	
	public EventGameShutdown(long nanoTime){
		this.nanoTime = nanoTime;
	}
	
	@Override
	public void onEvent() {
		if(!Resilience.getInstance().isEnabled()) return;
		try{
			for(Listener l : Resilience.getInstance().getEventManager().eventListeners){
				if(l instanceof ModListener){
					ModListener mod = (ModListener) l;
					mod.onGameShutdown(this);
				}
			}
		}catch(Exception e){}		
	}
	
	public long getNanoTime(){
		return nanoTime;
	}
	
}
