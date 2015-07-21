package com.krispdev.resilience.gui.objects.buttons;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.gui.objects.screens.ModulePanel;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Utils;

public class ModuleButton extends DefaultButton{

	private DefaultModule mod;
	private ModulePanel panel;
	
	private boolean overButton = false;
	
	private int colour1 = 0x552582C9, colour2 = 0x55555555;
	private int colour3 = 0x55449bdd, colour4 = 0x55676767;
	private int colour5 = 0x551d669e, colour6 = 0x550065d1;
	private int colour7 = 0x444e4eff;
	
	private int slideCount;
	private int textLength = 0;
	private int modExtraCount = 0;
	
	private boolean infoOpened = false;
	private boolean changeBindOpen = false;
	
	public ModuleButton(int x, int y, int x1, int y1, ModulePanel panel, DefaultModule mod) {
		super(x, y, x1, y1);
		
		this.panel = panel;
		this.mod = mod;
		slideCount  = (int) -Resilience.getInstance().getButtonExtraFont().getWidth(mod.getDescription());
	}
	
	@Override
	public void draw(int i, int j){
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		colour7 = 0xbb000000;
		boolean overChange = i >= getX1() + panel.getDragX() + 6 && i <= getX1() + panel.getDragX() + 48 && j >= getY() + panel.getDragY() + 78 && j <= getY() + panel.getDragY() + 91;
		boolean overClear = i >= getX1() + panel.getDragX() + 49 && i <= getX1() + panel.getDragX() + 90 && j >= getY() + panel.getDragY() + 78 && j <= getY() + panel.getDragY() + 91;
		boolean overModName = i >= getX1() + panel.getDragX() + 6 && i <= getX1() + panel.getDragX() + 90 && j >= getY() + panel.getDragY() + 21 && j <= getY() + panel.getDragY() + 36;
		boolean overToggle =  i >= getX1() + panel.getDragX() + 6 && i <= getX1() + panel.getDragX() + 90 && j >= getY() + panel.getDragY() + 37 && j <= getY() + panel.getDragY() + 51;
		
		boolean isMouseDownOverToggle = Mouse.isButtonDown(0) && overToggle && this.panel.isFocused();
		boolean isMouseDownOverButton = Mouse.isButtonDown(0) && overButton && this.panel.isFocused();
		
		if(mod.isEnabled()){
			Utils.drawRect(getX() + panel.getDragX(), getY() + panel.getDragY() + 21, getX1() + panel.getDragX(), getY1() + panel.getDragY() + 38, overButton ? isMouseDownOverButton ? colour5 : colour3 : colour1);
		}else{
			Utils.drawRect(getX() + panel.getDragX(), getY() + panel.getDragY() + 21, getX1() + panel.getDragX(), getY1() + panel.getDragY() + 38, overButton ? isMouseDownOverButton ? colour6 : colour4 : colour2);
		}
		Resilience.getInstance().getStandardFont().drawCenteredString(mod.getName(), (getX() + getX1())/2+panel.getDragX(), getY() + panel.getDragY() + 23,  0xffffffff);
		
		if(infoOpened){
			
			Resilience.getInstance().getPanelTitleFont().drawString("<", getX1() + panel.getDragX() - 9, getY() + panel.getDragY() + 21, 0xffffffff);
			
			Utils.drawRect(getX1() + panel.getDragX() + 6, panel.getDragY() + getY() + 21, getX1() + panel.getDragX() + 90, panel.getDragY() + getY() + 91 + 12*modExtraCount + (mod.guiExtras.size() == 0 ? 0 : 8), colour7);
			modExtraCount = 0;
			Utils.drawSmallHL(getX1() + panel.getDragX() + 6, getY() + panel.getDragY() + 21, getX1() + panel.getDragX() + 90, 0xff6868ff);
			Utils.drawSmallHL(getX1() + panel.getDragX() + 6, panel.getDragY() + getY() + 91 + 12*modExtraCount + (mod.guiExtras.size() == 0 ? 0 : 20), getX1() + panel.getDragX() + 90, 0xff6868ff);
			Utils.drawSmallHL(getX1() + panel.getDragX() + 6, getY() + panel.getDragY() + 36, getX1() + panel.getDragX() + 90, 0x66b5b5ff);
			Utils.drawSmallHL(getX1() + panel.getDragX() + 6, getY() + panel.getDragY() + 51, getX1() + panel.getDragX() + 90, 0x66b5b5ff);
			Utils.drawSmallHL(getX1() + panel.getDragX() + 6, getY() + panel.getDragY() + 91, getX1() + panel.getDragX() + 90, (mod.guiExtras.size() == 0) ? 0 : 0x66b5b5ff);
			
			Utils.drawRect(getX1() + panel.getDragX() + 90, getY() + panel.getDragY() + 21, getX1() + panel.getDragX() + 90 +Resilience.getInstance().getButtonExtraFont().getWidth(mod.getDescription())+slideCount, getY() + panel.getDragY() + 36, 0xcc000000);
			
			if(overModName){
				Utils.drawRect(getX1() + panel.getDragX() + 6, getY() + panel.getDragY() + 21, getX1() + panel.getDragX() + 90, getY() + panel.getDragY() + 36, 0x22ffffff);
				if(slideCount < 4){
					if(slideCount+12 > 4){
						slideCount = 4;
					}else{
						slideCount+=12;
					}
				}else{
					ClickGui.hasHovered = true;
					Resilience.getInstance().getButtonExtraFont().drawString(mod.getDescription(), getX1() + panel.getDragX() + 92, getY() + panel.getDragY() + 23, 0xffb5b5ff);
				}
			}else if(slideCount > -Resilience.getInstance().getButtonExtraFont().getWidth(mod.getDescription())){
				if(slideCount-12 < -Resilience.getInstance().getButtonExtraFont().getWidth(mod.getDescription())){
					slideCount = (int) -Resilience.getInstance().getButtonExtraFont().getWidth(mod.getDescription());
				}else{
					slideCount-=12;
				}
			}

			
			if(overToggle){
				Utils.drawRect(getX1() + panel.getDragX() + 6, getY() + panel.getDragY() + 36, getX1() + panel.getDragX() + 90, getY() + panel.getDragY() + 51, isMouseDownOverToggle ? 0x22dddddd : 0x22ffffff);
			}
			
			Resilience.getInstance().getStandardFont().drawCenteredString(mod.getName(), getX1() + 96/2 + panel.getDragX(), getY() + panel.getDragY() + 23, 0xffe2e2e2);
			Resilience.getInstance().getStandardFont().drawCenteredString(mod.isEnabled() ? "Enabled" : "Disabled", getX1() + panel.getDragX() + 96/2, getY() + panel.getDragY() + 38, mod.isEnabled() ? 0xff00ff00 : 0xffff0000);
			Resilience.getInstance().getStandardFont().drawCenteredString("Current Keybind:", getX1() + panel.getDragX() + 96/2, getY() + panel.getDragY() + 53, 0xffb5b5ff);
			Resilience.getInstance().getStandardFont().drawCenteredString(Keyboard.getKeyName(mod.getKeyCode()), getX1() + panel.getDragX() + 96/2,  getY() + panel.getDragY() + 65, 0xffffffff);
			
			Utils.drawRect(getX1() + panel.getDragX() + 6, getY() + panel.getDragY() + 78, getX1() + panel.getDragX() + 48, getY() + panel.getDragY() + 91, overChange ? 0x44d3d3ff : 0x44b9b9ff);
			Resilience.getInstance().getButtonExtraFont().drawCenteredString("CHANGE", getX1() + panel.getDragX() + (6+46)/2, getY() + panel.getDragY() + 80, 0xffffffff);
			
			Utils.drawSmallVL(getY() + panel.getDragY() + 78, getX1() + panel.getDragX() + 48, getY() + panel.getDragY() + 91, 0xffffffff);
			
			Utils.drawRect(getX1() + panel.getDragX() + 48, getY() + panel.getDragY() + 78, getX1() + panel.getDragX() + 90, getY() + panel.getDragY() + 91, overClear ? 0x44d3d3ff : 0x44b9b9ff);
			Resilience.getInstance().getButtonExtraFont().drawCenteredString("CLEAR", getX1() + panel.getDragX() + (48+90)/2, getY() + panel.getDragY() + 80, 0xffffffff);
			
			if(changeBindOpen){
				Utils.drawBetterRect(Resilience.getInstance().getClickGui().getWidth()/2-75, Resilience.getInstance().getClickGui().getHeight()/2-50,  Resilience.getInstance().getClickGui().getWidth()/2+75, Resilience.getInstance().getClickGui().getHeight()/2+50, 0x660065d1, 0x99030303);
				Resilience.getInstance().getPanelTitleFont().drawCenteredString("Press the Key to Bind", Resilience.getInstance().getClickGui().getWidth()/2, Resilience.getInstance().getClickGui().getHeight()/2-40, 0xffffffff);
				Resilience.getInstance().getPanelTitleFont().drawCenteredString(">>          ENTER          <<", Resilience.getInstance().getClickGui().getWidth()/2, Resilience.getInstance().getClickGui().getHeight()/2-12, 0xffe4e4e4);
			}
			
			for(ModOptionBox box : mod.guiExtras){
				box.setX(getX1()+panel.getDragX()+10);
				box.setY(panel.getDragY()+getY()+12*modExtraCount+100);
				box.draw(i, j);
				modExtraCount++;
			}
		}else{
			Resilience.getInstance().getPanelTitleFont().drawString(">", getX1() + panel.getDragX() - 9, getY() + panel.getDragY() + 21, 0xffffffff);
		}
		GL11.glPopMatrix();
	}
	
	@Override
	public boolean onClick(int i, int j, int k){
		if(changeBindOpen) return false;
		if(overButton && k == 0){
			panel.setFocused(true);
			if(panel.isFocused()){
				mod.toggle();
			}
			return true;
		}else if(i>=getX() + panel.getDragX()&&i<=getX1() + panel.getDragX()&&j>=getY() + panel.getDragY() + 21&&j<=getY1() + panel.getDragY() + 38 && k == 1){
			infoOpened = !infoOpened;
			ClickGui.hasRightClicked = true;
			Resilience.getInstance().getClickGui().focusWindow(panel);
			for(DefaultPanel panel : Resilience.getInstance().getClickGui().windows){
				if(panel instanceof ModulePanel){
					ModulePanel modulePanel = (ModulePanel)panel;
					for(DefaultButton button : modulePanel.buttons){
						if(button instanceof ModuleButton){
							ModuleButton btn = (ModuleButton)button;
							if(btn != this){
								btn.setInfoOpened(false);
							}
						}
					}
				}
			}
			return true;
		}else if(i >= getX1() + panel.getDragX() + 6 && i <= getX1() + panel.getDragX() + 48 && j >= getY() + panel.getDragY() + 78 && j <= getY() + panel.getDragY() + 91 && infoOpened){
			changeBindOpen = true;
			panel.setFocused(true);
			return true;
		}else if(i >= getX1() + panel.getDragX() + 49 && i <= getX1() + panel.getDragX() + 90 && j >= getY() + panel.getDragY() + 78 && j <= getY() + panel.getDragY() + 91 && infoOpened && mod.getCategory() != ModuleCategory.GUI){
			mod.setKeyBind(0);
			Resilience.getInstance().getFileManager().saveBinds();
			panel.setFocused(true);
			return true;
		}else if(i >= getX1() + panel.getDragX() + 6 && i <= getX1() + panel.getDragX() + 90 && j >= getY() + panel.getDragY() + 36 && j <= getY() + panel.getDragY() + 51 && infoOpened && mod.getCategory() != ModuleCategory.GUI){
			mod.toggle();
			panel.setFocused(true);
			return true;
		}
		if(infoOpened){
			for(ModOptionBox box : mod.guiExtras){
				if(box.clicked(i, j)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void keyTyped(char c, int i){
		if(changeBindOpen){
			if(i != 1){
				mod.setKeyBind(i);
				Resilience.getInstance().getFileManager().saveBinds();
			}
			changeBindOpen = false;
		}
	}
	
	public void setInfoOpened(boolean flag){
		this.infoOpened = flag;
	}
	
	public void setOverButton(boolean flag){
		this.overButton = flag;
	}
	
}
