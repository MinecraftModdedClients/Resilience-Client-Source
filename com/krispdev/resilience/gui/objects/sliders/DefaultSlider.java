package com.krispdev.resilience.gui.objects.sliders;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class DefaultSlider {
	
	private NumberValue value;
	private float min;
	private float max;
	private int posX;
	private int posY;
	private boolean dragging = false;
	private int width = 100;
	private int height = 20;
	private int lastClickX;
	private int dragX=0;
	private DefaultPanel panel;
	private boolean round;
	
	public DefaultSlider(NumberValue value, float min, float max, int posX, int posY, DefaultPanel valuePanel, boolean round){
		this.value = value;
		this.min = min;
		this.max = max;
		this.posX = posX;
		this.posY = posY;
		this.panel = valuePanel;
		this.round = round;
		setVal(value.getValue());
	}
	
	public void draw(int i, int j){
		width = 99;
		if(dragging){
			drag(i);
		}
		if(dragX<0){
			dragX = 0;
		}
		if(dragX > width-5){
			dragX=width-5;
		}
		
		value.setValue((dragX / ((width-5) / (max - min))) + min);
	}
	
	public void drag(int i){
		dragX = i - lastClickX;
	}
	
	public void setVal(float val){
		val -= min;
		float dif = max-min;
		float f = (width-5) / dif;
		value.setValue(val);
		dragX = (int)(f * val);
	}
	
	public boolean mouseClicked(int i, int j, int k){
		if(k==0){
			if(i>=posX+panel.getDragX() && i<= posX+6+panel.getDragX()+94 && j>= posY+panel.getDragY()+20 && j<= posY+panel.getDragY()+34){
				dragX = i - (panel.getDragX() + posX + 1);
				lastClickX = i - dragX;
				dragging = true;
				return true;
			}else{
				dragging = false;
			}
		}
		return false;
	}
	
	public void mouseMovedOrUp(int i, int j, int k){
		if(k==0){
			dragging = false;
			Resilience.getInstance().getFileManager().saveConfigs();
		}
	}
	
	public int getDragX(){
		return dragX;
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
		return i >= posX+panel.getDragX() && i <= posX+6+panel.getDragX()+94 && j >= posY+panel.getDragY()+20 && j<= posY+34+panel.getDragY();
	}
	
	public boolean overNob(int i, int j){
		return i >= posX+panel.getDragX() && i <= posX+6+panel.getDragX()+94 && j >= posY+panel.getDragY()+20 && j<= posY+34+panel.getDragY();
	}
	
	public DefaultPanel getPanel(){
		return panel;
	}
	
}
