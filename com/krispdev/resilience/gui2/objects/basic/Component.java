package com.krispdev.resilience.gui2.objects.basic;

import com.krispdev.resilience.gui2.interfaces.Clickable;
import com.krispdev.resilience.gui2.interfaces.Dragable;
import com.krispdev.resilience.gui2.objects.geometry.Shape;
import com.krispdev.resilience.gui2.objects.geometry.shapes.Rectangle;

public abstract class Component implements Clickable{
	
	private Container container;
	private Shape area;
	
	public Component(Container container, Shape area){
		this.container = container;
		this.area = area;
	}

	@Override
	public boolean onClick(float x, float y, int btn) {
		if(overArea(x,y)){
			onAreaClicked(x,y,btn);
			return true;
		}
		return false;
	}

	@Override
	public boolean overArea(float x, float y) {
		return area.overArea(x, y);
	}

	@Override
	public void mouseUp(float x, float y, int btn) {}

	@Override
	public void onAreaClicked(float x, float y, int btn) {}
	
	public Shape getArea(){
		return area;
	}
	
}
