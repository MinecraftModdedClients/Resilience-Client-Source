package com.krispdev.resilience.gui.objects.buttons;

import com.krispdev.resilience.Resilience;

public class ModOptionBox {
	
	private String text;
	private float x,y;
	private CheckBox checkBox;
	
	public ModOptionBox(String text, float y, float x, boolean startChecked){
		this.text = text;
		this.x = x;
		this.y = y;
		checkBox = new CheckBox(x,y,startChecked);
	}
	
	public void draw(int mX, int mY){
		checkBox.draw((int)this.x, (int)this.y);
		Resilience.getInstance().getStandardFont().drawString(text, x+12, y-2, 0xffffffff);
	}
	
	public boolean clicked(int x, int y){
		if(checkBox.clicked(x, y)){
			return true;
		}
		return false;
	}
	
	public boolean isChecked(){
		return checkBox == null ? false : checkBox.isChecked();
	}
	
	public void setX(int x){
		this.x = x;
		checkBox.setX(x);
	}
	
	public void setY(int y){
		this.y = y;
		checkBox.setY(y);
	}

	public void setChecked(boolean checked) {
		this.checkBox.setChecked(checked);
	}
	
}
