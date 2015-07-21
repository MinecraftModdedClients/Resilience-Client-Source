package com.krispdev.resilience.gui.objects.sliders;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class VerticalScrollSlider {
	
	private NumberValue value;
	private float min;
	private float max;
	private int posX;
	private int posY;
	private boolean dragging = false;
	private int width = 99;
	private int height = 20;
	private int lastClickY;
	private int dragY=0;
	private boolean round;
	
	public VerticalScrollSlider(NumberValue value, int posX, int posY, int posX1, int posY1){
		this.value = value;
		this.min = value.getMin();
		this.max = value.getMax();
		this.posX = posX;
		this.posY = posY;
		this.width = posX1;
		this.height = posY1-5;
		setVal(value.getValue());
	}
	
	public void draw(int i, int j){
		if(dragging){
			drag(j);
		}
		if(dragY<0){
			dragY = 0;
		}
		if(dragY > height-10){
			dragY=height-10;
		}

		Utils.drawBetterRect(getPosX(), getPosY(), getPosX()+width, getPosY()+height, 0xff000000, 0x88002f52);
		Utils.drawBetterRect(getPosX(), getPosY()+getDragY(), getPosX()+width, getPosY()+getDragY()+10, 0xff000000, 0xbb9a9aff);
		
		value.setValue((dragY / ((height) / (max - min))) + min);
	}
	
	public void drag(int i){
		dragY = i - lastClickY;
	}
	
	public void setVal(float val){
		val -= min;
		float dif = max-min;
		float f = height / dif;
		value.setValue(val);
		dragY = (int)(f * val);
	}
	
	public void mouseClicked(int i, int j, int k){
		if(k==0){
			if(i>=posX && i<= posX+width && j>= posY && j<= posY+height){
				dragY = j - (posY + 1);
				lastClickY = j - dragY;
				dragging = true;
			}else{
				dragging = false;
			}
		}
	}
	
	public void mouseMovedOrUp(int i, int j, int k){
		if(k==0){
			dragging = false;
		}
	}
	
	public int getDragY(){
		return dragY;
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public NumberValue getValue(){
		return value;
	}
	
	public boolean shouldRound(){
		return round;
	}
	
	public boolean overSlider(int i, int j){
		return i >= posX && i <= posX+width && j >= posY && j<= posY+height;
	}
	
	public boolean overNob(int i, int j){
		return i >= posX && i <= posX+width && j >= posY && j<= posY+height;
	}

	public void setX(int newX) {
		this.posX = newX;
	}
	
	public void setY(int newY) {
		this.posY = newY;
	}
	
}
