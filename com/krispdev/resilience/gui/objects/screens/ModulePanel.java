package com.krispdev.resilience.gui.objects.screens;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.DefaultButton;
import com.krispdev.resilience.gui.objects.buttons.ModuleButton;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Utils;

public class ModulePanel extends DefaultPanel{

	private int count = 0;
	private int buttonSize = 15;
	
	public ModulePanel(String title, int x, int y, int x1, int y1, boolean visible, ModuleCategory category){
		
		super(title, x, y, x1, y1, visible);
		
		for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
			if(mod.getCategory() == category){
				addButton(mod);
			}
		}
	}
	
	private void addButton(DefaultModule mod){
		buttons.add(new ModuleButton(getX()+4-3, getY()+(buttonSize*count)+1-4, getX1()-4+3, getY()+(buttonSize*count)-2-4, this, mod));
		count++;
	}
	
	@Override
	public void draw(int i, int j){
		super.draw(i, j);
		
		if(isExtended()){
			Utils.drawRect(getX(), getY() + 17, getX1(), getY() + (buttons.size()*buttonSize) + 18, 0x99040404);
			for(DefaultButton btn : buttons){
				btn.draw(i, j);
				if(btn instanceof ModuleButton){
					ModuleButton button = (ModuleButton)btn;
					if(i>=btn.getX() + getDragX()&&i<=btn.getX1() + getDragX()&&j>=btn.getY() + getDragY() + 21&&j<=btn.getY1() + getDragY() + 38){
						button.setOverButton(true);
					}else{
						button.setOverButton(false);
					}
				}
			}
		}
	}
	
	@Override
	public boolean onClick(int i, int j, int k){
		if(isExtended()){
			for(DefaultButton btn : buttons){
				if(btn.onClick(i, j, k)){
					return true;
				}
			}
		}
		if(super.onClick(i, j, k)){
			return true;
		}
		return false;
	}
	
	@Override
	public void keyTyped(char c, int i){
		for(DefaultButton btn : buttons){
			btn.keyTyped(c, i);
		}
	}
	
}
