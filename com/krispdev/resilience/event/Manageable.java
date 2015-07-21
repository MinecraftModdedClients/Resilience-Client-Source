package com.krispdev.resilience.event;

import com.krispdev.resilience.event.listeners.Listener;

public interface Manageable {
	
	void register(Listener l);
	void unregister(Listener l);
	void clear();
	
}
