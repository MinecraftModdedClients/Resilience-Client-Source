package com.krispdev.resilience.gui.objects.interfaces;

public interface Dragable {
	
	void drag(int x, int y);
	
	void setDragX(int x);
	
	void setDragY(int y);
	
	int getDragX();
	
	int getDragY();
	
}
