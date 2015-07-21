package com.krispdev.resilience.utilities.value.values;

import com.krispdev.resilience.event.events.player.EventValueChange;
import com.krispdev.resilience.utilities.value.Value;

public class BoolValue extends Value{

	private boolean state;
	
	public BoolValue(String name, boolean state){
		super(name);
		this.state = state;
	}
	
	public boolean getState(){
		return state;
	}
	
	public void setState(boolean state){
		final EventValueChange eventChange = new EventValueChange(this);
		eventChange.onEvent();
		
		if(!eventChange.isCancelled()){
			this.state = state;
		}else{
			eventChange.setCancelled(false);
		}
	}
	
}
