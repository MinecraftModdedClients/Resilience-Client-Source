package com.krispdev.resilience.gui.objects.screens;

import java.util.ArrayList;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.components.Component;
import com.krispdev.resilience.gui.objects.components.XrayBlockComponent;
import com.krispdev.resilience.utilities.Utils;

public class XrayBlocksPanel extends DefaultPanel{

	private ArrayList<Component> components = new ArrayList<Component>();
	private int[] blockIds = new int[]{1,2,3,4,5,7,8,9,10,11,12,13,14,15,16,21,22,23,29,33,41,42,46,47,48,49,52,56,57,58,73,116,129,133,152,158};
	
	public XrayBlocksPanel(String title, int x, int y, int x1, int y1, boolean visible) {
		super(title, x, y, x1, y1, visible);
	
		int size = 22;
		int xPos = 4;
		int yPos = 4;
		for(int i=0; i<blockIds.length; i++){
			components.add(new XrayBlockComponent(xPos+getOriginalX(), yPos+17+getOriginalY(), xPos+size+getOriginalX(), yPos+size+17+getOriginalY(), blockIds[i], Resilience.getInstance().getXrayUtils().shouldRenderBlock(blockIds[i])));
			xPos+=size+4;
			if(xPos+size+4 >= 240){
				xPos = 4;
				yPos += size+4;
			}
		}
	}
	
	@Override
	public void draw(int x, int y){
		if(isExtended()){
			Utils.drawRect(getX(), getY() + 17, getX1(), getY() + 22*5+15, 0x99040404);
			for(Component c : components){
				c.setX((int) (c.getOX()+getDragX()));
				c.setY((int) (c.getOY()+getDragY()));
				c.setX1((int) (c.getOX1()+getDragX()));
				c.setY1((int) (c.getOY1()+getDragY()));
				c.draw(x, y);
			}
		}
		super.draw(x, y);
	}
	
	@Override
	public boolean onClick(int x, int y, int btn){
		if(isExtended()){
			for(Component c : components){
				if(c.onClick(x, y, btn)){
					return true;
				}
			}
		}
		if(super.onClick(x, y, btn)){
			return true;
		}
		return false;
	}
	
	@Override
	public void onMouseButtonUp(int x, int y, int btn){
		super.onMouseButtonUp(x, y, btn);
	}
	
}
