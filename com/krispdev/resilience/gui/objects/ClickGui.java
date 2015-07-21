package com.krispdev.resilience.gui.objects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.objects.screens.ArmourStatusPanel;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.gui.objects.screens.GuiRadarPanel;
import com.krispdev.resilience.gui.objects.screens.InfoPanel;
import com.krispdev.resilience.gui.objects.screens.ModulePanel;
import com.krispdev.resilience.gui.objects.screens.TextRadarPanel;
import com.krispdev.resilience.gui.objects.screens.ValuePanel;
import com.krispdev.resilience.gui.objects.screens.XrayBlocksPanel;
import com.krispdev.resilience.gui.screens.IngameMenu;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.value.values.NumberValue;

public class ClickGui extends GuiScreen{

	public final ModulePanel world = new ModulePanel("World", 120, 4, 114*2, 150, true, ModuleCategory.WORLD);
	public final ModulePanel combat = new ModulePanel("Combat", 4, 23, 114, 17*2, true, ModuleCategory.COMBAT);
	public final ModulePanel player = new ModulePanel("Player" ,120, 42, 114*2, 17*3, true, ModuleCategory.PLAYER);
	public final ModulePanel guiPanel = new ModulePanel("GUI", 4, 61, 114, 17*4, true, ModuleCategory.GUI);
	public final ModulePanel movement = new ModulePanel("Movement", 4, 4, 114, 17, true, ModuleCategory.MOVEMENT);
	public final ModulePanel misc = new ModulePanel("Misc", 120, 23, 114*2, 17*2, true, ModuleCategory.MISC);
	public final ModulePanel render = new ModulePanel("Render", 4, 42, 114, 17*3, true, ModuleCategory.RENDER);
	public final ValuePanel values = new ValuePanel("Mod Values", 120, 61, 114*2, 17*4, true, Resilience.getInstance().getValues().numValues.toArray(new NumberValue[Resilience.getInstance().getValues().numValues.size()]));
	public final TextRadarPanel textRadar = new TextRadarPanel("Text Radar", 4, 80, 114, 17*5, true);
	public final GuiRadarPanel guiRadar = new GuiRadarPanel("Gui Radar", 120, 80, 114*2 + 50/2, 17*5, true);
	public final ArmourStatusPanel armorStatus = new ArmourStatusPanel("Armor Status", 4, 99, 114, 17*6, true);
	public final ModulePanel combatOptions = new ModulePanel("Combat [More]", 120, 99, 114*2, 17*6, true, ModuleCategory.COMBAT_EXTENSION);
	public final InfoPanel info = new InfoPanel("Player Info", 4, 118, 114, 17*7, true);
	public final XrayBlocksPanel xrayPanel = new XrayBlocksPanel("Xray Blocks", 120, 118, 358, 17*7, true);
	
	public static boolean hasRightClicked = false, hasHovered = false, hasOpened = false, hasPinned = false;
	
	//private ResilienceButton enableButton;
	
	/**
	 * The list holding all panels. Note that this is not static as it doesn't need to be
	 * due to the singleton pattern in Resilience.java
	 */
	public static List<DefaultPanel> windows = new ArrayList<DefaultPanel>();
	
	public ClickGui(){
		Resilience.getInstance().getFileManager().loadGui();
	}
	
	/**
	 * Focuses the selected panel
	 * @param panel The panel to be focused
	 */
	public void focusWindow(DefaultPanel panel){
		if(windows.contains(panel)){
			windows.remove(windows.indexOf(panel));
			windows.add(windows.size(), panel);
			for(DefaultPanel window : windows){
				window.setFocused(false);
			}
			panel.setFocused(true);
		}
	}
	
	/**
	 * Overides the GuiScreen method initGui, which is used to add components to the screen
	 */
	
	@Override
	public void initGui(){
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, 4, Resilience.getInstance().getInvoker().getHeight()-24, 100, 20, "Resilience Menu"));
	}
	
	/**
	 * Draws the screen, and all the default windows
	 */
	
	@Override
	public void drawScreen(int i, int j, float f){
		this.drawDefaultBackground();
		for(DefaultPanel panel : windows){
			panel.draw(i, j);
		}
		if(!hasRightClicked && Resilience.getInstance().isFirstTime()){
			Utils.drawBetterRect(getWidth()/2-125, getHeight()/2-50,  getWidth()/2+125, getHeight()/2+50, 0xdd010101, 0xdd040404);
			Resilience.getInstance().getPanelTitleFont().drawCenteredString(""+"\247bTutorial [1/2]", getWidth()/2, getHeight()/2-45, 0xffffffff);
			Resilience.getInstance().getStandardFont().drawCenteredString("\247b"+"Welcome to Resilience!", getWidth()/2, getHeight()/2-25, 0xffffffff);
			Resilience.getInstance().getStandardFont().drawCenteredString((hasOpened ? "\2477" : "\247b" )+"To open a GUI panel hit the top right box.", getWidth()/2, getHeight()/2-13, 0xffffffff);
			Resilience.getInstance().getStandardFont().drawCenteredString((hasPinned ? "\2477" : "\247b" )+"To \"pin\" (so it's seen ingame) hit the top left box.", getWidth()/2, getHeight()/2-1, 0xffffffff);
			Resilience.getInstance().getStandardFont().drawCenteredString("\247cTo continue:", getWidth()/2, getHeight()/2-1+12, 0xffffffff);
			Resilience.getInstance().getStandardFont().drawCenteredString("\247bRight click a button to get info & customization on the mod!", getWidth()/2, getHeight()/2-1+12*2, 0xffffffff);
		}
		super.drawScreen(i, j, f);
	}
	
	/**
	 * Called during mouse click. Not sure why there's a try-catch block here, but I'm too scared to remove it...
	 */
	
	@Override
	public void mouseClicked(int i, int j, int k){
		try{
			super.mouseClicked(i, j, k);
			for(DefaultPanel panel : windows){
				if(panel.onClick(i, j, k)){
					 break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void actionPerformed(GuiButton btn){
		if(Resilience.getInstance().getInvoker().getId(btn) == 1){
			Resilience.getInstance().getInvoker().displayScreen(new IngameMenu(this));
		}
	}
	
	/**
	 * When the mouse button is released. Also saves the GUI
	 */
	
	@Override
	public void mouseMovedOrUp(int i, int j, int k){
		for(DefaultPanel panel : windows){
			panel.onMouseButtonUp(i, j, k);
		}
		Resilience.getInstance().getFileManager().saveGui();
		super.mouseMovedOrUp(i, j, k);
	}
	
	/**
	 * When a key is typed...
	 */
	
	@Override
	public void keyTyped(char c, int i){
		for(DefaultPanel panel : windows){
			panel.keyTyped(c, i);
		}
		super.keyTyped(c, i);
	}
	
	@Override
	public void onGuiClosed(){
		Resilience.getInstance().getModuleManager().setModuleState("Gui", false);;
	}
	
	/**
	 * Gives the width of the screen 
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Gives the height of the screen
	 */
	public int getHeight(){
		return height;
	}
}
