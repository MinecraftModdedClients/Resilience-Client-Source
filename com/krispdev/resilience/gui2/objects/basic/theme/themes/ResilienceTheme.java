package com.krispdev.resilience.gui2.objects.basic.theme.themes;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui2.objects.basic.theme.Theme;
import com.krispdev.resilience.gui2.objects.basic.theme.ThemeColour;
import com.krispdev.resilience.gui2.objects.geometry.shapes.Rectangle;

public class ResilienceTheme extends Theme{

	public ResilienceTheme(){
		super(
				"Resilience",
				new ThemeColour(0xbb020202), 
				new ThemeColour(0xbb0072ec, 0xbb82c0ff), 
				new ThemeColour(0xbb0072ec, 0xbb82c0ff), 
				new ThemeColour(0x99040404),
				0xffffffff, 
				0xffffffff,
				new Rectangle(0, 0, 110, 17), 
				new Rectangle(83, 3, 94, 14),
				new Rectangle(96, 3, 107, 14), 
				new Rectangle(0, 0, 110, 17),
				Resilience.getInstance().getPanelTitleFont()
				);
	}
	
	
	
}
