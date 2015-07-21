package com.krispdev.resilience.gui.objects.sliders;

import java.text.DecimalFormat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class ModuleValueSlider extends DefaultSlider{

	public ModuleValueSlider(NumberValue value, float min, float max, int posX, int posY, DefaultPanel valuePanel, boolean round) {
		super(value, min, max, posX, posY, valuePanel, round);
	}
	
	@Override
	public void draw(int i, int j){
		boolean overThingy = i >= getPosX()+getPanel().getDragX()+getDragX() && i <= getPosX()+getDragX()+6+getPanel().getDragX() && j >= getPosY()+getPanel().getDragY()+20 && j <= getPosY()+34+getPanel().getDragY();
		boolean overSlider = i >= getPosX()+getPanel().getDragX() && i <= getPosX()+6+getPanel().getDragX()+94 && j >= getPosY()+getPanel().getDragY()+20 && j<= getPosY()+34+getPanel().getDragY();
		
		Utils.drawBetterRect(getPosX()+getPanel().getDragX(), getPosY()+getPanel().getDragY()+20, getPosX()+6+getPanel().getDragX()+94, getPosY()+34+getPanel().getDragY(), 0xff000000, overSlider ? 0x88002852 : 0x88002f52);
		Utils.drawBetterRect(getPosX()+getPanel().getDragX()+getDragX(), getPosY()+getPanel().getDragY()+20, getPosX()+getDragX()+6+getPanel().getDragX(), getPosY()+34+getPanel().getDragY(), 0xff000000, overThingy ? 0xbbb5b5ff : 0xbb9a9aff);
		Resilience.getInstance().getButtonExtraFont().drawCenteredString(getValue().getName()+" - "+(shouldRound() ? new DecimalFormat("#").format(getValue().getValue()) : new DecimalFormat("#.#").format(getValue().getValue())), (6+94)/2+getPanel().getDragX()+getPosX(), getPosY()+getPanel().getDragY()+22, 0xffeeeeee);
	
		super.draw(i, j);
	}
	
}
