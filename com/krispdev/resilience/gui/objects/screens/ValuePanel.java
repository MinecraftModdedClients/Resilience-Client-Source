package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.gui.objects.sliders.DefaultSlider;
import com.krispdev.resilience.gui.objects.sliders.ModuleValueSlider;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class ValuePanel extends DefaultPanel{

	private int count = 0;
	
	public ValuePanel(String title, int x, int y, int x1, int y1, boolean visible, NumberValue ... val) {
		super(title, x, y, x1, y1, visible);
		
		for(NumberValue value : val){
			addSlider(value);
		}
		this.setExtended(true);
	}
	
	private void addSlider(NumberValue val){
		sliders.add(new ModuleValueSlider(val, val.getMin(), val.getMax(), getX()+4, getY()+(18*count), this, val.shouldRound()));
		count++;
	}
	
	@Override
	public void draw(int i, int j){
		super.draw(i, j);
		if(isExtended()){
			Utils.drawRect(getX(), getY() + 17, getX1(), getY() + (sliders.size()*18) + 19, 0x99040404);
			for(DefaultSlider slider : sliders){
				slider.draw(i, j);
			}
		}
	}

	@Override
	public boolean onClick(int i, int j, int k){
		for(DefaultSlider slider : sliders){
			if(slider.mouseClicked(i, j, k)){
				return true;
			}
		}
		if(super.onClick(i, j, k)){
			return true;
		}
		return false;
	}
	
	@Override
	public void onMouseButtonUp(int i, int j, int k){
		for(DefaultSlider slider : sliders){
			slider.mouseMovedOrUp(i, j, k);
		}
		super.onMouseButtonUp(i, j, k);
	}
	
}
