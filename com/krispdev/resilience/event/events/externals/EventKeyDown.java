package com.krispdev.resilience.event.events.externals;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.Cancellable;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.interfaces.Bindable;

public class EventKeyDown extends Cancellable implements Event{
	
	private final int keycode;
	
	public EventKeyDown(int keycode){
		this.keycode = keycode;
	}
	
	@Override
	public void onEvent() {
		try{
			for(Listener l : Resilience.getInstance().getEventManager().gameListeners){
				if(l instanceof Bindable){
					Bindable mod = (Bindable) l;
					mod.onKeyDown(keycode);
				}
			}
		}catch(Exception e){}
	}
	
}
