package com.krispdev.resilience.gui2.objects.basic;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.gui2.interfaces.Clickable;
import com.krispdev.resilience.gui2.interfaces.Dragable;
import com.krispdev.resilience.gui2.interfaces.Holdable;
import com.krispdev.resilience.gui2.interfaces.Visible;
import com.krispdev.resilience.gui2.objects.geometry.Shape;
import com.krispdev.resilience.gui2.objects.geometry.shapes.Rectangle;

public class Container implements Dragable, Clickable, Holdable, Visible{
	
	private boolean dragging = false;
	private Shape area;
	private float dragX;
	private float dragY;
	private float startX;
	private float startY;
	private float lastXDrag, lastYDrag;
	private ArrayList<Component> componentList = new ArrayList<Component>();
	
	public Container(Shape area){
		this.area = area;
		startX = area.getX();
		startY = area.getY();
	}

	@Override
	public void mouseUp(float x, float y, int btn) {
		dragging = false;
	}

	@Override
	public void onAreaClicked(float x, float y, int btn) {}

	@Override
	public boolean onComponentClick(float x, float y, int btn) {
		return onClick(x, y, btn);
	}
	
	@Override
	public boolean onClick(float x, float y, int btn) {
		return false;
	}

	@Override
	public boolean overArea(float x, float y) {
		return area.overArea(x, y);
	}

	@Override
	public void drag(float x, float y) {
		x -= lastXDrag;
		y -= lastYDrag;
		area.setX(x);
		area.setY(y);
		dragX = x;
		dragY = y;
		if(area instanceof Rectangle){
			Rectangle rect = (Rectangle) area;
			rect.setX1(x+rect.getWidth());
			rect.setY1(y+rect.getHeight());
		}
		for(Component c : componentList){
			c.getArea().setX(x);
			c.getArea().setY(y);
			if(c.getArea() instanceof Rectangle){
				Rectangle rect = (Rectangle) c.getArea();
				rect.setX1(x+rect.getWidth());
				rect.setY1(y+rect.getHeight());
			}
		}
	}

	@Override
	public void updateDrag(float x, float y) {
		if(dragging){
			drag(x, y);
		}
	}

	@Override
	public void addComponent(Component c) {
		componentList.add(c);
	}

	@Override
	public void removeComponent(Component c) {
		boolean success = false;
		do{
			try{
				componentList.remove(componentList.indexOf(c));
				success = true;
			}catch(Exception e){}
		}while(!success);
	}

	public void runDraw(float x, float y){
		updateDrag(x, y);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(dragX+startX, dragY+startY, 0);
		draw(x, y);
		GL11.glPopMatrix();
	}
	
	protected void setDragging(boolean dragging){
		this.dragging = dragging;
	}
	
	public Shape getArea(){
		return area.getNewTranslate((dragX == 0 ? 0 : startX), (dragY == 0 ? 0 : startY));
	}
	
	public boolean isRectangle(){
		return area instanceof Rectangle;
	}
	
	public Rectangle getRectArea(){
		assert area instanceof Rectangle;
		assert area != null;
		Rectangle rect = (Rectangle) area;
		
		return rect.getNewTranslate((dragX == 0 ? 0 : startX), (dragY == 0 ? 0 : startY));
	}
	
	@Override
	public void draw(float x, float y) {}
	
	public void setLastXDrag(float x){
		this.lastXDrag = x;
	}
	
	public void setLastYDrag(float y){
		this.lastYDrag = y;
	}
	
	public float getDragX(){
		return dragX;
	}
	
	public float getDragY(){
		return dragY;
	}
	
	public ArrayList<Component> getComponents(){
		return componentList;
	}
	
}
