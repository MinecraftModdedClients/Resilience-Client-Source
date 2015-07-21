package com.krispdev.resilience.gui.objects.components;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.krispdev.resilience.gui.objects.interfaces.Clickable;
import com.krispdev.resilience.gui.objects.interfaces.Viewable;
import com.krispdev.resilience.gui.objects.sliders.VerticalScrollSlider;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class ScrollArea implements Viewable, Clickable{
	
	private ArrayList<Component> components = new ArrayList<Component>();
	private NumberValue scrollValue;
	private VerticalScrollSlider slider;
	
	private int scrollFactor;
	private Rectangle area;
	private int oldScroll = 0;
	
	public ScrollArea(Rectangle area){
		this.area = area;
		this.scrollValue = new NumberValue(0, 0, 100, "Scroll", false);
		this.slider = new VerticalScrollSlider(scrollValue, (int)area.getMinX(), (int)area.getMinY(), 10, (int)area.getMaxY()/2-4*2);
	}
	
	@Override
	public void draw(int x, int y){
		if(components.size() < 1) return;
		
		int neededSize = (int) components.get(components.size()-1).getOY1();
		float scrollPercent = scrollValue.getValue();
		scrollFactor = (int)-((scrollPercent*0.01)*neededSize);
		
		Utils.drawRect((float)area.getMinX(), (float)area.getMinY(), (float)area.getMaxX(), (float)area.getMaxY(), 0x99000000);
		
		int comSize = (int) (components.get(0).getOY1()-components.get(0).getOY());
		boolean shouldApplyScroll = scrollFactor % comSize == 0;
		
		if(shouldApplyScroll){
			oldScroll = scrollFactor/10;
		}
		
		for(Component c : components){
			c.setX((int) (c.getOX()+area.getMinX()));
			c.setY((int) (c.getOY()+area.getMinY())+oldScroll);
			c.setX1((int) (c.getOX1()+area.getMinX()));
			c.setY1((int) (c.getOY1()+area.getMinY())+oldScroll);
			if(c.shouldDrawFromScroll(area, scrollFactor, 4)){
				c.draw(x, y);
			}
		}
		
		slider.setX((int)area.getMaxX()-10);
		slider.setY((int)area.getMinY());
		
		slider.draw(x, y);
	}
	
	public void addComponent(Component component1){
		components.add(component1);
	}
	
	public void setX(int newX){
		this.area.x = newX;
	}
	
	public void setY(int newY){
		this.area.y = newY;
	}
	
	public void setX1(int newX1){
		this.area.width = newX1;
	}
	
	public void setY1(int newY1){
		this.area.height = newY1;
	}

	@Override
	public boolean onClick(int x, int y, int btn) {
		slider.mouseClicked(x, y, btn);
		return false;
	}

	@Override
	public void onMouseButtonUp(int x, int y, int btn) {
		slider.mouseMovedOrUp(x, y, btn);
	}

	public int getX() {
		return (int)area.getX();
	}
	
	public int getY() {
		return (int)area.getY();
	}
	
	public int getX1() {
		return (int)area.getWidth()+(int)area.getX();
	}
	
	public int getY1() {
		return (int)area.getHeight()+(int)area.getY();
	}
	
	public void clearComponents(){
		this.components.clear();
	}
	
}
