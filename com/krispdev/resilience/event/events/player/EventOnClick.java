package com.krispdev.resilience.event.events.player;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;

public class EventOnClick implements Event{

	private int btn = 0;
	private boolean auto;
	
	public EventOnClick(int btn, boolean auto){
		this.btn = btn;
		this.auto = auto;
	}
	
	@Override
	public void onEvent() {
		if(!Resilience.getInstance().isEnabled()) return;
		try{
			for(Listener l : Resilience.getInstance().getEventManager().eventListeners){
				if(l instanceof ModListener){
					ModListener mod = (ModListener) l;
					mod.onClick(this);
				}
			}
		}catch(Exception e){}
	}

	public int getButton(){
		return btn;
	}
	
	public boolean isAutoClick(){
		return auto;
	}
	
}
