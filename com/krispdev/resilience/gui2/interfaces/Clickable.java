package com.krispdev.resilience.gui2.interfaces;

public interface Clickable {

	void mouseUp(float x, float y, int btn);
	void onAreaClicked(float x, float y, int btn);
	boolean onClick(float x, float y, int btn);
	boolean onComponentClick(float x, float y, int btn);
	boolean overArea(float x, float y);
	
}
