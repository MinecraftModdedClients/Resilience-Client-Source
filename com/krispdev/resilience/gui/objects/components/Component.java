package com.krispdev.resilience.gui.objects.components;

import java.awt.Rectangle;

import com.krispdev.resilience.gui.objects.interfaces.Clickable;
import com.krispdev.resilience.gui.objects.interfaces.Viewable;

public class Component implements Clickable, Viewable{
	
	private float x,y,x1,y1,oX,oY,oX1,oY1;
	
	public Component(float x, float y, float x1, float y1){
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.oX = x;
		this.oY = y;
		this.oX1 = x1;
		this.oY1 = y1;
	}
	
	public void onComponentClicked(int x, int y, int btn){}

	public void draw(int mX, int mY){}
	
	@Override
	public boolean onClick(int x, int y, int btn) {
		if(x >= this.x && x <= this.x1 && y >= this.y && y <= this.y1){
			onComponentClicked(x,y,btn);
			return true;
		}
		return false;
	}

	@Override
	public void onMouseButtonUp(int x, int y, int btn) {}
	
	public boolean shouldDrawFromScroll(Rectangle scrollArea, int scrollFactor, float error){
		return x>=scrollArea.getMinX() && y+-error >= scrollArea.getMinY() && y1+error <= scrollArea.getMaxY();
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getY1(){
		return y1;
	}
	
	public float getX1(){
		return x1;
	}
	
	public float getOX(){
		return oX;
	}
	
	public float getOY(){
		return oY;
	}
	
	public float getOY1(){
		return oY1;
	}
	
	public float getOX1(){
		return oX1;
	}
	
	public void setX(int newX){
		this.x = newX;
	}
	
	public void setY(int newY){
		this.y = newY;
	}
	
	public void setX1(int newX1){
		this.x1 = newX1;
	}
	
	public void setY1(int newY1){
		this.y1 = newY1;
	}
	
}
