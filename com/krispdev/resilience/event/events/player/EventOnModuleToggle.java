package com.krispdev.resilience.event.events.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.Cancellable;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;
import com.krispdev.resilience.module.modules.DefaultModule;

public class EventOnModuleToggle extends Cancellable implements Event{

	private DefaultModule module;
	
	public EventOnModuleToggle(DefaultModule module){
		this.module = module;
	}
	
	@Override
	public void onEvent() {
		if(!Resilience.getInstance().isEnabled()) return;
		try{
			for(Listener l : Resilience.getInstance().getEventManager().eventListeners){
				if(l instanceof ModListener){
					ModListener mod = (ModListener) l;
					mod.onModuleToggle(this);
				}
			}
		}catch(Exception e){}
	}
	
	public DefaultModule getModule(){
		return module;
	}
	
}
