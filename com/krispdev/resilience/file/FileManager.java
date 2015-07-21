package com.krispdev.resilience.file;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.account.Account;
import com.krispdev.resilience.command.objects.Macro;
import com.krispdev.resilience.command.objects.Waypoint;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.relations.Enemy;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.utilities.XrayBlock;
import com.krispdev.resilience.utilities.value.Value;
import com.krispdev.resilience.utilities.value.values.BoolValue;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.utilities.value.values.StringValue;

public class FileManager {
	
	public ThreadUpdateGame dF;
	
	public boolean loadGui = true;
	public boolean loadKeybinds = true;
	public boolean loadEnabledMods = true;
	public boolean loadXrayBlocks = true;
	public boolean loadEnemies = true;
	public boolean loadFriends = true;
	public boolean loadConfigs = true;
	public boolean loadWaypoints = true;
	public boolean loadMacros = true;
	
	public boolean shouldAsk = false;
	
	public boolean wasUsingOldOnline = false;
	
	private File mainDir = new File(Resilience.getInstance().getName());
	private File accountsDir = new File(mainDir+File.separator+"Accounts");
	
	public File gui = new File(mainDir+File.separator+"GuiSettings.res");
	public File configs = new File(mainDir+File.separator+"Configs.res");
	public File keybinds = new File(mainDir+File.separator+"Keybinds.res");
	public File enabledMods = new File(mainDir+File.separator+"EnabledMods.res");
	public File firstTime = new File(mainDir+File.separator+"FirstTime.res");
	public File friends = new File(mainDir+File.separator+"Friends.res");
	public File enemies = new File(mainDir+File.separator+"Enemies.res");
	public File macros = new File(mainDir+File.separator+"Macros.res");
	public File waypoints = new File(mainDir+File.separator+"Waypoints.res");
	public File xray = new File(mainDir+File.separator+"XrayBlocks.res");
	public File ask = new File(mainDir+File.separator+"DonationAsk.res");
	public File accounts = new File(accountsDir+File.separator+"Accounts.res");
	public File online1 = new File(mainDir+File.separator+"Online.res");
	public File resOnline = new File(mainDir+File.separator+"ResilienceOnline.res");
	
	File[] oldFiles = new File[]{
		new File(mainDir+File.separator+"Keybinds.txt"),
		new File(mainDir+File.separator+"GuiSettings.txt"),
		new File(mainDir+File.separator+"EnabledMods.txt"),
		new File(mainDir+File.separator+"XrayBlocks.txt"),
		new File(mainDir+File.separator+"Enemies.txt"),
		new File(mainDir+File.separator+"Friends.txt"),
		new File(mainDir+File.separator+"Configs.txt"),
		new File(mainDir+File.separator+"Waypoints.txt"),
		new File(mainDir+File.separator+"FIRST_TIME_FILE_CHECKER"),
		new File(mainDir+File.separator+"Macros.txt"),
		new File(mainDir+File.separator+"FIRST_TIME_FILE_CHECK")
	};
	
	public void init() throws Exception{
		if(!mainDir.exists()){
			mainDir.mkdir();
			Resilience.getInstance().getLogger().info("Created the main client directory");
		}
		if(!accountsDir.exists()){
			accountsDir.mkdir();
		}
		if(!ask.exists()){
			saveOptions("3");
		}
		if(!firstTime.exists()){
			firstTime.createNewFile();
			
			Resilience.getInstance().setFirstTime();
			
			for(File file : oldFiles){
				if(file.exists()){
					file.delete();
				}
			}
		}
		if(!keybinds.exists()){
			saveBinds();
			Resilience.getInstance().getLogger().info("Created Keybinds.res");
		}
		if(!gui.exists()){
			try{
				gui.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
			Resilience.getInstance().getLogger().info("Created GuiSettings.res");
		}
		if(!configs.exists()){
			Resilience.getInstance().getValues().speed.setValue(11);
			Resilience.getInstance().getValues().range.setValue(4);
			saveConfigs();
			Resilience.getInstance().getLogger().info("Created Configs.res");
		}
		if(!enabledMods.exists()){
			saveEnabledMods(new String[]{
					"Enabled Mods",
					"NiceChat",
					"Target Players",
					"Target Mobs",
					"Target Animals",
					"IRC",
					"Steal Store Buttons",
					"NoCheat"
			});
			Resilience.getInstance().getLogger().info("Created EnabledMods.res");
		}
		if(!friends.exists()){
			saveFriends();
			Resilience.getInstance().getLogger().info("Created Friends.res");
		}
		if(!enemies.exists()){
			saveEnemies();
			Resilience.getInstance().getLogger().info("Created Enemies.res");
		}
		if(!macros.exists()){
			saveMacros();
			Resilience.getInstance().getLogger().info("Created Macros.res");
		}
		if(!waypoints.exists()){
			saveWaypoints();
			Resilience.getInstance().getLogger().info("Created Waypoints.res");
		}
		if(!xray.exists()){
			saveXrayBlocks(new String[]{
				"56",
				"57",
				"14",
				"15",
				"16",
				"41",
				"42",
				"73",
				"74",
				"152",
				"173",
				"129",
				"133",
				"10",
				"11",
				"8",
				"9"
			});
			Resilience.getInstance().getLogger().info("Saved XrayBlocks.res");
		}
		if(!resOnline.exists() && firstTime.exists()){
			Resilience.getInstance().setFirstTimeOnline(true);
			resOnline.createNewFile();
		}			
		if(!accounts.exists()){
			saveAccounts();
			Resilience.getInstance().getLogger().info("Saved Accounts.res");
		}
		
		loadConfigs();
		
		if(loadKeybinds){
			loadBinds();
		}
		
		if(loadEnabledMods){
			loadEnabledMods();
		}
		
		if(loadFriends){
			loadFriends();
		}
		
		if(loadEnemies){
			loadEnemies();
		}
		
		if(loadMacros){
			loadMacros();
		}
		
		if(loadWaypoints){
			loadWaypoints();
		}
		
		if(loadXrayBlocks){
			loadXrayBlocks();
		}
		
		int num = loadOptions();
		
		if(num != -1){
			saveOptions(String.valueOf(num+1));
			shouldAsk = num > 2 && !Resilience.getInstance().getValues().isDonator(Resilience.getInstance().getInvoker().getSessionUsername());
			
			if(shouldAsk){
				saveOptions("0");
			}
		}
	}
	
	public void saveGui(String ... presets){
		ArrayList<String> toPrint = new ArrayList<String>();
		
		for(String line : presets){
			toPrint.add(line);
		}
		for(DefaultPanel panel : Resilience.getInstance().getClickGui().windows){
			toPrint.add(panel.getTitle()+":"+panel.getDragX()+":"+panel.getDragY()+":"+panel.isExtended()+":"+panel.isPinned()+":"+panel.isVisible());
		}
		FileUtils.print(toPrint, gui, true);
	}
	
	public void loadGui(){
		ArrayList<String> lines = FileUtils.read(gui, true);
		
		for(DefaultPanel panel : ClickGui.windows){
			for(String s : lines){
				String args[] = s.split(":");
				if(panel.getTitle().equalsIgnoreCase(args[0])){
					panel.setDragX(Integer.parseInt(args[1]));
					panel.setDragY(Integer.parseInt(args[2]));
					panel.setExtended(Boolean.parseBoolean(args[3]));
					panel.setPinned(Boolean.parseBoolean(args[4]));
					panel.setVisible(Boolean.parseBoolean(args[5]));
				}
			}
		}
	}
	
	public void saveConfigs(String ... presets){
		ArrayList<String> toPrint = new ArrayList<String>();
		
		for(String line : presets){
			toPrint.add(line);
		}
		for(Value value : Resilience.getInstance().getValues().values){
			if(value instanceof NumberValue){
				NumberValue numValue = (NumberValue) value;
				toPrint.add(value.getName()+":"+(numValue.shouldRound()? (int)numValue.getValue() : numValue.getValue()));
			}else if(value instanceof BoolValue){
				BoolValue tfValue = (BoolValue) value;
				toPrint.add(value.getName()+":"+tfValue.getState());
			}else if(value instanceof StringValue){
				StringValue strValue = (StringValue) value;
				toPrint.add(strValue.getName()+":"+strValue.getValue());
			}else{
				toPrint.add(value.getName());
			}
		}
		FileUtils.print(toPrint, configs, true);
	}
	
	public void loadConfigs() throws Exception{
		ArrayList<String> lines = FileUtils.read(configs, true);
		for(String s : lines){
			String args[] = s.split(":");
			for(Value value : Resilience.getInstance().getValues().values){
				if(value.getName().equalsIgnoreCase(args[0])){
					if(value instanceof NumberValue){
						for(NumberValue numValue : Resilience.getInstance().getValues().numValues){
							if(numValue.getName().equalsIgnoreCase(args[0].trim())){
								numValue.setValue(Float.parseFloat(args[1]));
							}
						}
					}else if(value instanceof BoolValue){
						for(BoolValue boolValue : Resilience.getInstance().getValues().boolValues){
							if(boolValue.getName().equalsIgnoreCase(args[0].trim())){
								boolValue.setState(Boolean.parseBoolean(args[1]));
							}
						}
					}else if(value instanceof StringValue){
						for(StringValue strValue : Resilience.getInstance().getValues().strValues){
							if(strValue.getName().equalsIgnoreCase(args[0].trim())){
								strValue.setValue(args[1]);
							}
						}
					}
				}
			}
		}
	}
	
	public void saveBinds(String ... presets){
		ArrayList<String> toPrint = new ArrayList<String>();
		
		for(String line : presets){
			toPrint.add(line);
		}
		for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
			toPrint.add(mod.getName()+":"+Keyboard.getKeyName(mod.getKeyCode()));
		}
		FileUtils.print(toPrint, keybinds, true);
	}
	
	public void loadBinds(){
		ArrayList<String> lines = FileUtils.read(keybinds, true);
		
		for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
			for(String s : lines){
				String[] args = s.split(":");
				if(mod.getName().equalsIgnoreCase(args[0].trim())){
					mod.setKeyBind(Keyboard.getKeyIndex(args[1]));
				}
			}
		}
	}
	
	public void saveEnabledMods(String ... presets){
		ArrayList<String> toPrint = new ArrayList<String>();
		
		for(String line : presets){
			toPrint.add(line);
		}
		for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
			if(mod.isEnabled() && !(mod.getCategory() == ModuleCategory.GUI) && !(mod.getCategory() == ModuleCategory.NONE) && mod.shouldSave()){
				toPrint.add(mod.getName());
			}
		}
		FileUtils.print(toPrint, enabledMods, false);
	}
	
	public void loadEnabledMods(){
		ArrayList<String> lines = FileUtils.read(enabledMods, true);
		for(String s : lines){
			for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
				if(mod.getName().equalsIgnoreCase(s.trim())){
					mod.setEnabled(true);
				}
			}
		}
	}
	
	public void saveFriends(String ... presets){
		ArrayList<String> toPrint = new ArrayList<String>();
		
		for(String line : presets){
			toPrint.add(line);
		}
		for(Friend friend : Friend.friendList){
			toPrint.add(friend.getName()+":"+friend.getAlias());
		}
		FileUtils.print(toPrint, friends, true);
	}
	
	public void loadFriends(){
		ArrayList<String> lines = FileUtils.read(friends, true);
		
		for(String line : lines){
			String args[] = line.split(":");
			
			if(args.length < 1){
				Friend.friendList.add(new Friend(args[0], args[0]));
			}else{
				Friend.friendList.add(new Friend(args[0], args[1]));
			}
		}
	}

	public void saveEnemies(String ... presets) {
		ArrayList<String> toPrint = new ArrayList<String>();

		for(String line : presets){
			toPrint.add(line);
		}
		for(Enemy enemy : Enemy.enemyList){
			toPrint.add(enemy.getName());
		}
		FileUtils.print(toPrint, enemies, true);
	}
	
	public void loadEnemies(){
		ArrayList<String> lines = FileUtils.read(enemies, true);
		
		for(String line : lines){
			Enemy.enemyList.add(new Enemy(line));
		}
	}
	
	public void saveMacros(String ... presets){
		ArrayList<String> toPrint = new ArrayList<String>();

		for(String line : presets){
			toPrint.add(line);
		}
		for(Macro macro : Macro.macroList){
			toPrint.add(macro.getCommand()+":"+macro.getKey());
		}
		FileUtils.print(toPrint, macros, true);
	}
	
	public void loadMacros(){
		ArrayList<String> lines = FileUtils.read(macros, true);
		
		for(String line : lines){
			String args[] = line.split(":");
			Macro.macroList.add(new Macro(Integer.parseInt(args[1]), args[0]));
		}
	}

	public void saveWaypoints(String ... presets){
		ArrayList<String> toPrint = new ArrayList<String>();
		
		for(String line : presets){
			toPrint.add(line);
		}
		for(Waypoint w : Waypoint.waypointsList){
			toPrint.add(w.getName()+":"+w.getX()+":"+w.getY()+":"+w.getZ()+":"+w.getR()+":"+w.getG()+":"+w.getB());
		}
		FileUtils.print(toPrint, waypoints, true);
	}
	
	public void loadWaypoints(){
		ArrayList<String> lines = FileUtils.read(waypoints, true);
		for(String s : lines){
			String args[] = s.split(":");
			Waypoint.waypointsList.add(new Waypoint(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Float.parseFloat(args[4]),Float.parseFloat(args[6]),Float.parseFloat(args[5])));
		}
	}
	
	public void loadXrayBlocks(){
		ArrayList<String> lines = FileUtils.read(xray, true);
		for(String s : lines){
			Resilience.getInstance().getXrayUtils().xrayBlocks.add(new XrayBlock(Integer.parseInt(s)));
		}
	}
	
	public void saveXrayBlocks(String ... presets){
		ArrayList<String> toPrint = new ArrayList<String>();
		
		for(String line : presets){
			toPrint.add(line);
		}
		for(XrayBlock xrayBlock : Resilience.getInstance().getXrayUtils().xrayBlocks){
			toPrint.add(String.valueOf(xrayBlock.getId()));
		}
		FileUtils.print(toPrint, xray, true);
	}
	
	public void saveOptions(String num){
		ArrayList<String> toPrint = new ArrayList<String>();
		
		toPrint.add(num);
		FileUtils.print(toPrint, ask, false);
	}
	
	public int loadOptions(){
		ArrayList<String> line = FileUtils.read(ask, false);
		
		return Integer.parseInt(line.get(0));
	}
	
	public void downloadFile(File dir, URL fileLocation){
		dF = new ThreadUpdateGame(fileLocation, dir);
		dF.start();
	}

	public void saveAccounts(String ... presets){
		ArrayList<String> toPrint = new ArrayList<String>();
		
		for(String line : presets){
			toPrint.add(line);
		}
		for(Account acc : Account.accountList){
			toPrint.add(acc.getUsername()+":"+acc.getPassword());
		}
		FileUtils.print(toPrint, accounts, true);
	}
	
	public void loadAccounts(){
		ArrayList<String> lines = FileUtils.read(accounts, true);
		if(lines == null) return;
		for(String s : lines){
			String args[] = s.split(":");
			if(args.length > 1 && !args[0].equals("") && args[0] != null && args[1] != null && !args[1].equals("")){
				Account.accountList.add(new Account(args[0], args[1]));
			}else if(args.length == 1 && !args[0].equals("") && args[0] != null){
				Account.accountList.add(new Account(args[0], ""));
			}
		}
	}
	
}
