package com.krispdev.resilience.gui.objects.screens;

import net.minecraft.item.ItemStack;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.Utils;

public class ArmourStatusPanel extends DefaultPanel{

	private int count = 4;
	
	public ArmourStatusPanel(String title, int x, int y, int x1, int y1, boolean visible) {
		super(title, x, y, x1, y1, visible);
	}

	@Override
	public void draw(int i, int j){
		count = 0;
		if(isExtended()){
			Utils.drawRect(getX(), getY() + 17, getX1(), getY() + 50, 0x99040404);
			for(ItemStack item : Resilience.getInstance().getInvoker().getArmourInventory()){
				if(item!=null){
					Utils.drawItemTag(getX()+count+21*4, getY()+17+8, item);
				}
				count-=25;
			}
		}
		super.draw(i, j);
	}
	
}
