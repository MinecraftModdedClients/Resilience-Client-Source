package com.krispdev.resilience.event;

import java.util.ArrayList;

import com.krispdev.resilience.event.listeners.Listener;

public class EventManager implements Manageable{

	public ArrayList<Listener> eventListeners = new ArrayList<Listener>();
	public ArrayList<Listener> gameListeners = new ArrayList<Listener>();
	
	public void registerGameListener(Listener l){
		if(!gameListeners.contains(l)){
			gameListeners.add(l);
		}
	}
	
	public void unregisterGameListener(Listener l){
		try{
			if(gameListeners.contains(l)){
				gameListeners.remove(gameListeners.indexOf(l));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void register(Listener l) {
		if(!eventListeners.contains(l)){
			eventListeners.add(l);
		}
	}

	@Override
	public void unregister(Listener l) {
		try{
			if(eventListeners.contains(l)){
				eventListeners.remove(eventListeners.indexOf(l));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void clear() {
		eventListeners.clear();
	}
		
}
