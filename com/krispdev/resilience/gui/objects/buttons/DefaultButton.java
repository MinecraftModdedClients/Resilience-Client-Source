package com.krispdev.resilience.gui.objects.buttons;

public class DefaultButton {
	
	private int x, y, x1, y1;
	
	public DefaultButton(int x, int y, int x1, int y1){
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	public void draw(int i, int j){}
	
	public boolean onClick(int i, int j, int k){return false;}
	
	public void keyTyped(char c, int i){}
	
	public void mouseMovedOrUp(int i, int j, int k){}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getX1(){
		return x1;
	}
	
	public int getY1(){
		return y1;
	}
	
}
