package com.krispdev.resilience.gui2.objects.geometry;

public interface Shape {
		
	boolean overArea(float x, float y);
	void setX(float x);
	void setY(float y);
	void scale(float sX, float sY);
	void translate(float x, float y);
	Shape getNewScale(float sX, float sY);
	Shape getNewTranslate(float x, float y);
	float getX();
	float getY();
	
}
