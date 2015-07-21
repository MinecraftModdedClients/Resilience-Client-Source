package com.krispdev.resilience.utilities.value.values;

import com.krispdev.resilience.event.events.player.EventValueChange;
import com.krispdev.resilience.utilities.value.Value;

public class NumberValue extends Value{

	private float value;
	private float min;
	private float max;
	private boolean round;
	
	public NumberValue(float value, float min, float max, String name, boolean round){
		super(name);
		this.value = value;
		this.max = max;
		this.min = min;
		this.round = round;
	}
	
	public float getValue(){
		return value;
	}
	
	public float getMin(){
		return min;
	}
	
	public float getMax(){
		return max;
	}
	
	public boolean shouldRound(){
		return round;
	}
	
	public void setValue(float value){
		final EventValueChange eventChange = new EventValueChange(this);
		eventChange.onEvent();
		
		if(!eventChange.isCancelled()){
			this.value = value;
		}else{
			eventChange.setCancelled(false);
		}
	}
	
}
