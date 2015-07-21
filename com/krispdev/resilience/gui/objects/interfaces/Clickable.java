package com.krispdev.resilience.gui.objects.interfaces;

public interface Clickable {
	
	boolean onClick(int x, int y, int btn);
	
	void onMouseButtonUp(int x, int y, int btn);
	
}
