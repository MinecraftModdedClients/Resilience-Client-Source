package com.krispdev.resilience.interfaces;

public interface Toggleable {
	
	void onEnable();
	void onDisable();
	void onToggle();
	void toggle();
	void setEnabled(boolean flag);
	
}
