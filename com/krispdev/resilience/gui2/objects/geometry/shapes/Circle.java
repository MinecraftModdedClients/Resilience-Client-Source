package com.krispdev.resilience.gui2.objects.geometry.shapes;

import com.krispdev.resilience.gui2.objects.geometry.Shape;

public class Circle implements Shape{
	
	private double radius, diameter, circumference;
	private float x,y; 
	
	public Circle(double radius, float x, float y){
		this.radius = radius;
		this.x = x;
		this.y = y;
		this.diameter = calculateDiameter(radius);
		this.circumference = calculateCircumferenceR(radius);
	}
	
	public boolean overArea(float pX, float pY){
		float distAway = (float) Math.hypot(pX, pY);
 		return distAway <= x+radius && distAway >= x && distAway <= y+radius && distAway >= y; 
	}
	
	public double calculateDiameter(double radius){
		return radius*2;
	}
	
	public double calculateCircumferenceR(double radius){
		return calculateDiameter(radius)*Math.PI;
	}
	
	public double calculateCircumferenceD(double diameter){
		return diameter*Math.PI;
	}
	
	public double getRadius(){
		return radius;
	}
	
	public void setRadius(double radius){
		this.radius = radius;
		this.diameter = calculateDiameter(radius);
		this.circumference = calculateCircumferenceR(radius);
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}

	@Override
	public void scale(float sX, float sY) {
		x *= sX;
		y *= sY;
	}

	@Override
	public void translate(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Circle getNewScale(float sX, float sY) {
		return new Circle(radius*sX, x*sX, y*sY);
	}

	@Override
	public Circle getNewTranslate(float x, float y) {
		return new Circle(radius, x, y);
	}

}
