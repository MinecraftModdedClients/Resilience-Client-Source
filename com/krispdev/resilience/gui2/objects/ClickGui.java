package com.krispdev.resilience.gui2.objects;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Mouse;

import com.krispdev.resilience.gui2.objects.basic.Container;
import com.krispdev.resilience.gui2.objects.basic.defaults.panel.DefaultPanel;
import com.krispdev.resilience.gui2.objects.basic.theme.themes.ResilienceTheme;
import com.krispdev.resilience.gui2.objects.geometry.Shape;

public class ClickGui extends GuiScreen{
	
	public static ArrayList<Container> containers = new ArrayList<Container>();
	
	private Container focusedContainer;
	
	public ClickGui(){
		containers.add(new DefaultPanel(4F,4F,new ResilienceTheme(),"Test Panel"));
	}
	
	public void instantiateContainers(){
		
	}
	
	public boolean isMouseDownOverArea(Container container, Shape shape, float x, float y, int btn){
		if(shape.overArea(x, y) && Mouse.isButtonDown(btn)){
			int containerIndex = containers.indexOf(container);
			if(containerIndex == -1){
				return true;
			}
			
			for(int index=0; index<containers.size(); index++){
				if(index >= containerIndex){
					return true;
				}
				Container c = containers.get(index);
				
				if(c.overArea(x, y)){
					return false;
				}
			}
		}
		
		return false;
	}
	
	public boolean isOverArea(Container container, Shape shape, float x, float y){
		if(shape.overArea(x, y)){
			int containerIndex = containers.indexOf(container);
			if(containerIndex == -1){
				return true;
			}
			
			for(int index=0; index<containers.size(); index++){
				if(index >= containerIndex){
					return true;
				}
				Container c = containers.get(index);
				
				if(c.overArea(x, y)){
					return false;
				}
			}
		}
		
		return false;
	}
	
	public boolean isFocused(Container container){
		return focusedContainer == container;
	}
	
	@Override
	public void drawScreen(int x, int y, float f){
		for(Container container : containers){
			container.runDraw(x, y);
		}
	}
	
	@Override
	public void mouseClicked(int x, int y, int m){
		for(Container container : containers){
			if(container.onComponentClick(x, y, m)){
				focusedContainer = container;
				break;
			}
		}
	}
	
	@Override
	public void mouseMovedOrUp(int x, int y, int m){
		for(Container container : containers){
			container.mouseUp(x, y, m);
		}
	}
	
}
