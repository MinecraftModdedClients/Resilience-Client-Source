package com.krispdev.resilience.gui.objects.screens;

import java.util.ArrayList;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.buttons.DefaultButton;
import com.krispdev.resilience.gui.objects.interfaces.Clickable;
import com.krispdev.resilience.gui.objects.interfaces.Dragable;
import com.krispdev.resilience.gui.objects.sliders.DefaultSlider;
import com.krispdev.resilience.utilities.Utils;

public class DefaultPanel implements Dragable, Clickable{
	
	/**
	 * This is the default panel. It holds certain properties that allow it to be dragged. Interfaces Dragable and Clickable are involved.
	 */
	
	public ArrayList<DefaultButton> buttons = new ArrayList<DefaultButton>();
	public ArrayList<DefaultSlider> sliders = new ArrayList<DefaultSlider>();
	
	private boolean isPinned = false;
	private boolean isExtended = false;
	private boolean isDragging = false;
	private boolean isFocused = false;
	private boolean isVisible = false;
	private int lastClickX, lastClickY;
	private int x, y, x1, y1;
	private int dragX, dragY;
	private String title;
	
	private int color1 = 0xbb020202;
	
	private int color3 = 0xbb0072ec, color4 = 0xbb82c0ff;
	
	/**
	 * The constructor automatically adds the newly created instance to the list of windows residing in ClickGui.java.
	 * Not the best idea, and I will change it when I need to.
	 */
	
	public DefaultPanel(String title, int x, int y, int x1, int y1, boolean visible){
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.title = title;
		this.isVisible = visible;
		ClickGui.windows.add(this);
	}
	
	/**
	 * Draws the top part of the panel and updates drag.
	 * @param i Mouse x
	 * @param j Mouse y
	 */
	
	public void draw(int i, int j){
		if(!isVisible) return;
		if(isDragging){
			drag(i, j);
		}
		Utils.drawRect(getX(), getY(), getX1(), getY() + 17, color1);
		Utils.drawRect(getX1()-14, getY()+14, getX1()-3, getY()+3, isExtended() ? color3 : color4);
		Utils.drawRect(getX1()-27, getY()+14, getX1()-16, getY()+3, isPinned() ? color3 : color4);
		
		Resilience.getInstance().getPanelTitleFont().drawString(getTitle(), getX() + 4, getY()+1, 0xffffffff);
	}
	
	/**
	 * Called on click and performs the necessary calculations to decide whether to focus, extend, or pin the window
	 * @param i Mouse x
	 * @param j Mouse y
	 * @param k Mouse button (0=left, middle=2, right=1)
	 */
	
	public boolean onClick(int i, int j, int k){
		if(!isVisible) return false;
		boolean hasClicked = false;
		if(i <= getX1() && i >= getX() && j <= getY()+17 && j >= getY() && k==0){
			Resilience.getInstance().getClickGui().focusWindow(this);
			isDragging = true;
			lastClickX = i - dragX;
			lastClickY = j - dragY;
			hasClicked = true;
		}
		if(i >= getX1()-15 && j <= getY()+15 && i <= getX1()-2 && j >= getY()+2){
			ClickGui.hasOpened = true;
			setExtended(!isExtended());
			hasClicked = true;
		}
		if(i >= getX1()-27 && j <= getY()+15 && i <= getX1()-16 && j >= getY()+2){
			ClickGui.hasPinned = true;
			setPinned(!isPinned());
			hasClicked = true;
		}
		return hasClicked;
	}
	
	/**
	 * Sets the dragging to false on GUI close
	 */
	
	public void onGuiClosed(){
		isDragging = false;
	}
	
	public void onMouseButtonUp(int i, int j, int k){
		if(k == 0){
			isDragging = false;
		}
	}
	
	public void keyTyped(char c, int i){
		if(!isVisible) return;
	}
	
	public int getX(){
		return x + dragX;
	}
	
	public int getY(){
		return y + dragY;
	}
	
	public int getX1(){
		return x1 + dragX;
	}
	
	public int getY1(){
		return y1 + dragY;
	}
	
	public void drag(int x, int y){
		if(!isVisible) return;
		dragX = x - lastClickX;
		dragY = y - lastClickY;
	}
	
	public int getDragX(){
		return dragX;
	}
	
	public int getDragY(){
		return dragY;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setDragX(int i){
		dragX = i;
	}
	
	public void setDragY(int i){
		dragY = i;
	}
	
	public boolean isPinned(){
		return isPinned;
	}
	
	public boolean isExtended(){
		return isExtended;
	}
	
	public void setPinned(boolean flag){
		isPinned = flag;
	}
	
	public void setExtended(boolean flag){
		isExtended = flag;
	}
	
	public boolean isFocused(){
		return isFocused;
	}
	
	public boolean isVisible(){
		return isVisible;
	}
	
	public void setFocused(boolean flag){
		isFocused = flag;
	}
	
	public int getOriginalX(){
		return x;
	}
	
	public int getOriginalY(){
		return y;
	}
	
	public int getOriginalX1(){
		return x1;
	}
	
	public int getOriginalY1(){
		return y1;
	}
	
	public void setVisible(boolean visible){
		isVisible = visible;
	}
}
