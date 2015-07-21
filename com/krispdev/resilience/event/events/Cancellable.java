package com.krispdev.resilience.event.events;

public class Cancellable {
	
	private boolean isCancelled = false;
	
	public boolean isCancelled(){
		return isCancelled;
	}
	
	public void setCancelled(boolean flag){
		isCancelled = flag;
	}
	
}
