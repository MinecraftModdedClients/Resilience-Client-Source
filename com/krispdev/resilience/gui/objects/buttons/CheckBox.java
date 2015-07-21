package com.krispdev.resilience.gui.objects.buttons;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.utilities.Utils;

public class CheckBox {
	
	public static ArrayList<CheckBox> checkBoxList = new ArrayList<CheckBox>();
	
	private float x,y;
	private boolean checked;
	
	public CheckBox(float x, float y, boolean checked){
		this.x = x;
		this.y = y;
		this.checked = checked;
		checkBoxList.add(this);
	}
	
	public void draw(int i, int j){
		boolean overBox = i >= x && i <= x+8 && j >= y && j <= y+8;
		Utils.drawBetterRect(x, y, x+8, y+8, 0xff333333, overBox && Mouse.isButtonDown(0) ? 0xff090909 : overBox ? 0xff121212 : 0xff101010);
		if(checked){
			GL11.glPushMatrix();
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glColor3f(0,0.5F,1);
			GL11.glLineWidth(1.5F);
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex2f(x+1, y+1);
			GL11.glVertex2f(x+4, y+6);
			GL11.glVertex2f(x+10, y-5);
			GL11.glEnd();
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}
	
	public boolean clicked(int i, int j){
		if(i >= x && i <= x+8 && j >= y && j <= y+8){
			checked = !checked;
			return true;
		}
		return false;
	}
	
	public boolean isChecked(){
		return checked;
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

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
