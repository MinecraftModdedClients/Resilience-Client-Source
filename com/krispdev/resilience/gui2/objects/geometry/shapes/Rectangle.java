package com.krispdev.resilience.gui2.objects.geometry.shapes;

import com.krispdev.resilience.gui2.objects.geometry.Shape;

public class Rectangle implements Shape{
	
	private float x,y,x1,y1,width,height;
	
	public Rectangle(float x, float y, float x1, float y1){
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.width = x1-x;
		this.height = y1-y;
	}
	
	public boolean overArea(float pX, float pY){
		return pX >= x && pX <= x1 && pY >= y && pY <= y1; 
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getX1(){
		return x1;
	}
	
	public float getY1(){
		return y1;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void setX1(float x1){
		this.x1 = x1;
	}
	
	public void setY1(float y1){
		this.y1 = y1;
	}
	
	public void setHeight(float height){
		this.height = height;
	}
	
	public void setWidth(float width){
		this.width = width;
	}

	@Override
	public void scale(float sX, float sY) {
		this.x *= sX;
		this.y *= sY;
		this.x1 *= sX;
		this.y1 *= sY;
		this.width *= sX;
		this.height *= sY;
	}

	@Override
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
		this.x1 += x+this.width;
		this.y1 += y+this.height;
	}

	@Override
	public Rectangle getNewScale(float sX, float sY) {
		return new Rectangle(x*sX, y*sY, x1*sX, y1*sY);
	}

	@Override
	public Rectangle getNewTranslate(float tX, float tY) {
		return new Rectangle(x+tX, y+tY, x1+tX, y1+tY);
	}
	
}
