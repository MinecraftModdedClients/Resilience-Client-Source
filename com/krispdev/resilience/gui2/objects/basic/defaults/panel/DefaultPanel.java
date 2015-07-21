package com.krispdev.resilience.gui2.objects.basic.defaults.panel;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui2.interfaces.Dragable;
import com.krispdev.resilience.gui2.objects.basic.Component;
import com.krispdev.resilience.gui2.objects.basic.Container;
import com.krispdev.resilience.gui2.objects.basic.theme.Theme;
import com.krispdev.resilience.gui2.objects.basic.theme.themes.ResilienceTheme;
import com.krispdev.resilience.gui2.objects.geometry.shapes.Rectangle;
import com.krispdev.resilience.utilities.Utils;

public class DefaultPanel extends Container implements Dragable{

	private Theme theme;
	
	private boolean expanded;
	private boolean pinned;
	private boolean visible;
	
	private float lastDragX;
	private float lastDragY;
	
	private String title;
	
	public DefaultPanel(float sX, float sY, Theme theme, String title) {
		super(theme.getPanelSize().getNewTranslate(sX, sY));
		this.theme = theme;
		this.title = title;
	}
	
	@Override
	public void draw(float x, float y){
		this.theme = new ResilienceTheme();
		
		Utils.drawRect(theme.getPanelSize().getX(), theme.getPanelSize().getY(), theme.getPanelSize().getX1(), theme.getPanelSize().getY1(), 
				Resilience.getInstance().getClickGui2().isMouseDownOverArea(this, theme.getPanelSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y, 0) ? theme.getPanelColour().getMouseDownIdleColour() :
				Resilience.getInstance().getClickGui2().isOverArea(this, theme.getPanelSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y) ? theme.getPanelColour().getIdleColourHovered() : theme.getPanelColour().getIdleColour()
				);
		
		Utils.drawRect(theme.getExpandButtonSize().getX(), theme.getExpandButtonSize().getY(), theme.getExpandButtonSize().getX1(), theme.getExpandButtonSize().getY1(), 
				Resilience.getInstance().getClickGui2().isMouseDownOverArea(this, theme.getExpandButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y, 0) && expanded ? theme.getExpandButtonColour().getMouseDownEnabledColour() :
				Resilience.getInstance().getClickGui2().isOverArea(this, theme.getExpandButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y) && expanded ? theme.getExpandButtonColour().getEnabledColourHovered() :
				expanded ? theme.getExpandButtonColour().getEnabledColour() :
				Resilience.getInstance().getClickGui2().isMouseDownOverArea(this, theme.getExpandButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y, 0) ? theme.getExpandButtonColour().getMouseDownIdleColour() :
				Resilience.getInstance().getClickGui2().isOverArea(this, theme.getExpandButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y) ? theme.getExpandButtonColour().getIdleColourHovered() :
				theme.getExpandButtonColour().getIdleColour()
				);
		
		Utils.drawRect(theme.getPinButtonSize().getX(), theme.getPinButtonSize().getY(), theme.getPinButtonSize().getX1(), theme.getPinButtonSize().getY1(), 
				Resilience.getInstance().getClickGui2().isMouseDownOverArea(this, theme.getPinButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y, 0) && pinned ? theme.getPinButtonColour().getMouseDownEnabledColour() :
				Resilience.getInstance().getClickGui2().isOverArea(this, theme.getPinButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y) && pinned ? theme.getPinButtonColour().getEnabledColourHovered() :
				pinned ? theme.getPinButtonColour().getEnabledColour() :
				Resilience.getInstance().getClickGui2().isMouseDownOverArea(this, theme.getPinButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y, 0) ? theme.getPinButtonColour().getMouseDownIdleColour() :
				Resilience.getInstance().getClickGui2().isOverArea(this, theme.getPinButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()), x, y) ? theme.getPinButtonColour().getIdleColourHovered() :
				theme.getPinButtonColour().getIdleColour()
				);
		
		float yFactor = ((theme.getPanelSize().getY()+theme.getPanelSize().getY1())/2)-theme.getFont().getHeight(title)/2;
		theme.getFont().drawString(title, (int)theme.getPanelSize().getX()+(int)yFactor, (int)yFactor, 0xffffffff);
		
		
	}
	
	@Override
	public boolean onClick(float x, float y, int btn){
		if(theme.getExpandButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()).overArea(x, y)){
			expanded = !expanded;
			return true;
		}
		
		if(theme.getPinButtonSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()).overArea(x, y)){
			pinned = !pinned;
			return true;
		}
		
		if(theme.getPanelSize().getNewTranslate(getRectArea().getX(), getRectArea().getY()).overArea(x, y)){
			setLastXDrag(x-getDragX());
			setLastYDrag(y-getDragY());
			setDragging(true);
			return true;
		}
		
		return false;
	}
	
	public Theme getTheme(){
		return theme;
	}
	
	public boolean isExpanded(){
		return expanded;
	}
	
	public boolean isPinned(){
		return pinned;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public void setExpanded(boolean expanded){
		this.expanded = expanded;
	}
	
	public void setPinned(boolean pinned){
		this.pinned = pinned;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
}
