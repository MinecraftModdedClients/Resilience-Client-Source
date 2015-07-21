package com.krispdev.resilience.gui2.objects.basic.theme;

public class ThemeColour {
	
	private int idleColour;
	private int hoverIdleColour;
	private int enableColour;
	private int hoverEnableColour;
	private int mouseDownIdle;
	private int mouseDownEnable;
	
	public ThemeColour(
			int idleColour,
			int hoverIdleColour,
			int enableColour,
			int hoverEnableColour,
			int mouseDownIdle,
			int mouseDownEnable){
		
		this.idleColour = idleColour;
		this.hoverIdleColour = hoverIdleColour;
		this.enableColour = enableColour;
		this.hoverEnableColour = hoverEnableColour;
		this.mouseDownIdle = mouseDownIdle;
		this.mouseDownEnable = mouseDownEnable;
		
	}
	
	public ThemeColour(int colour){
		this(colour, colour, colour, colour, colour, colour);
	}
	
	public ThemeColour(int enableColour, int disableColour){
		this(disableColour, disableColour, enableColour, enableColour, disableColour, enableColour);
	}
	
	public ThemeColour(int colour, int hoverIdleColour, int enableColour, int hoverEnableColour){
		this(colour, hoverIdleColour, enableColour, hoverEnableColour, colour, colour);
	}
	
	public int getIdleColour(){
		return idleColour;
	}
	
	public int getIdleColourHovered(){
		return hoverIdleColour;
	}
	
	public int getEnabledColour(){
		return enableColour;
	}
	
	public int getEnabledColourHovered(){
		return hoverEnableColour;
	}
	
	public int getMouseDownIdleColour(){
		return mouseDownIdle;
	}
	
	public int getMouseDownEnabledColour(){
		return mouseDownEnable;
	}
	
}
