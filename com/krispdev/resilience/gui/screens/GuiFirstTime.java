package com.krispdev.resilience.gui.screens;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.CheckBox;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.hooks.HookGuiMainMenu;

public class GuiFirstTime extends GuiScreen{
	
	private CheckBox loadEnabledMods; 
	private CheckBox loadGuiSettings;
	private CheckBox loadKeybinds;
	private CheckBox loadXrayBlocks;
	private CheckBox loadFriends;
	private CheckBox loadEnemies;
	private CheckBox loadConfigs;
	private CheckBox loadWaypoints;
	private CheckBox loadMacros;
	private CheckBox useGlobalCustomButtons;
	
	public void initGui(){
		CheckBox.checkBoxList.clear();
		this.buttonList.clear();
		this.buttonList.add(new ResilienceButton(0, Resilience.getInstance().getInvoker().getWidth()-58, Resilience.getInstance().getInvoker().getHeight() - 28, 50, 20, "Done"));
		loadEnabledMods = new CheckBox(8, 8+14*4, Resilience.getInstance().getFileManager().loadEnabledMods);
		loadGuiSettings = new CheckBox(8, 8+14*5, Resilience.getInstance().getFileManager().loadGui);
		loadKeybinds = new CheckBox(8, 8+14*6, Resilience.getInstance().getFileManager().loadKeybinds);
		loadXrayBlocks = new CheckBox(8, 8+14*7, Resilience.getInstance().getFileManager().loadXrayBlocks);
		loadFriends = new CheckBox(8, 8+14*8, Resilience.getInstance().getFileManager().loadFriends);
		loadEnemies = new CheckBox(8, 8+14*9, Resilience.getInstance().getFileManager().loadEnemies);
		loadConfigs = new CheckBox(8, 8+14*10, Resilience.getInstance().getFileManager().loadConfigs);
		loadWaypoints = new CheckBox(8, 8+14*11, Resilience.getInstance().getFileManager().loadWaypoints);
		loadMacros = new CheckBox(8, 8+14*12, Resilience.getInstance().getFileManager().loadMacros);
		//useGlobalCustomButtons = new CheckBox(8, 8+14*12, Resilience.getInstance().allowCustomButtons);
	}
	
	public void drawScreen(int i, int j, float f){
		drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 0xff202020);
		for(CheckBox box : CheckBox.checkBoxList){
			box.draw(i, j);
		}
		Resilience.getInstance().getPanelTitleFont().drawCenteredString("Welcome to Resilience!", Resilience.getInstance().getInvoker().getWidth()/2, 8, 0xff0055FF);
		Resilience.getInstance().getStandardFont().drawCenteredString("Since this is your first time, please configure your client.", Resilience.getInstance().getInvoker().getWidth()/2, 8+18, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawCenteredString("You can change any of these settings in the client options at any time.", Resilience.getInstance().getInvoker().getWidth()/2, 8+14*2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Load Enabled Mods", 20, 8+14*4-2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Load GUI Settings", 20, 8+14*5-2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Load Keybinds", 20, 8+14*6-2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Load Xray Blocks", 20, 8+14*7-2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Load Friends", 20, 8+14*8-2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Load Enemies", 20, 8+14*9-2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Load Configs", 20, 8+14*10-2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Load Waypoints", 20, 8+14*11-2, 0xffffffff);
		Resilience.getInstance().getStandardFont().drawString("Load Macros", 20, 8+14*12-2, 0xffffffff);
		super.drawScreen(i, j, f);
		//TODO Resilience.getInstance().getStandardFont().drawString("Use global custom buttons", 20, 8+14*12-2, 0xffffffff);
	}
	
	public void actionPerformed(GuiButton btn){
		mc.displayGuiScreen(new HookGuiMainMenu());
	}
	
	public void mouseClicked(int i, int j, int k){
		for(CheckBox box : CheckBox.checkBoxList){
			box.clicked(i, j);
		}
		Resilience.getInstance().getFileManager().loadEnabledMods = loadEnabledMods.isChecked();
		Resilience.getInstance().getFileManager().loadGui = loadGuiSettings.isChecked();
		Resilience.getInstance().getFileManager().loadKeybinds = loadKeybinds.isChecked();
		Resilience.getInstance().getFileManager().loadXrayBlocks = loadXrayBlocks.isChecked();
		Resilience.getInstance().getFileManager().loadFriends = loadFriends.isChecked();
		Resilience.getInstance().getFileManager().loadEnemies = loadEnemies.isChecked();
		Resilience.getInstance().getFileManager().loadConfigs = loadConfigs.isChecked();
		Resilience.getInstance().getFileManager().loadWaypoints = loadWaypoints.isChecked();
		//Resilience.getInstance().allowCustomButtons = useGlobalCustomButtons.isChecked();
		Resilience.getInstance().getFileManager().saveConfigs();
		super.mouseClicked(i, j, k);
	}
}
