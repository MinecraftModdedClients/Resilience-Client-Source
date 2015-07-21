package com.krispdev.resilience.gui2.objects.basic.theme;

import com.krispdev.resilience.gui2.objects.geometry.shapes.Rectangle;
import com.krispdev.resilience.utilities.font.TTFRenderer;

public abstract class Theme {
	
	private String name;
	private ThemeColour panelColour;
	private ThemeColour pinButtonColour;
	private ThemeColour expandButtonColour;
	private ThemeColour expandedColour;
	private int panelTextColour, buttonTextColour;
	private TTFRenderer font;
	
	/**
	 * Size defining
	 */
	
	private Rectangle panelSize;
	private Rectangle pinButtonSize;
	private Rectangle expandButtonSize;
	private Rectangle buttonSize;
	
	public Theme(String name, ThemeColour panelColour, ThemeColour pinButtonColour, ThemeColour expandButtonColour, ThemeColour expandedColour, int buttonTextColour, int panelTextColour, Rectangle panelSize, Rectangle pinButtonSize, Rectangle expandButtonSize, Rectangle buttonSize, TTFRenderer font){
		this.name = name;
		this.panelColour = panelColour;
		this.pinButtonColour = pinButtonColour;
		this.expandButtonColour = expandButtonColour;
		this.expandedColour = expandedColour;
		this.panelTextColour = panelTextColour;
		this.buttonTextColour = buttonTextColour;
		this.buttonSize = buttonSize;
		this.panelSize = panelSize;
		this.pinButtonSize = pinButtonSize;
		this.expandButtonSize = expandButtonSize;
		this.font = font;
	}
	
	public ThemeColour getPanelColour(){
		return panelColour;
	}
	
	public ThemeColour getPinButtonColour(){
		return pinButtonColour;
	}
	
	public ThemeColour getExpandButtonColour(){
		return expandButtonColour;
	}
	
	public ThemeColour getExpandedColour(){
		return expandedColour;
	}
	
	public Rectangle getPanelSize(){
		return panelSize;
	}
	
	public Rectangle getPinButtonSize(){
		return pinButtonSize;
	}
	
	public Rectangle getExpandButtonSize(){
		return expandButtonSize;
	}
	
	public Rectangle getButtonSize(){
		return buttonSize;
	}
	
	public String getName(){
		return name;
	}
	
	public TTFRenderer getFont(){
		return font;
	}
	
}
