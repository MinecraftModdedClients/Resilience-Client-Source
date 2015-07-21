package com.krispdev.resilience.command.commands;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.module.modules.DefaultModule;

public class CmdBindRemove extends Command{
	
	public CmdBindRemove() {
		super("bind remove ", "[Mod]", "Removes the keybind from a mod");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String args[] = cmd.split(" ");
		String modName = args[2];
		for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
			if(mod.getName().equalsIgnoreCase(modName)){
				mod.setKeyBind(0);
				Resilience.getInstance().getLogger().infoChat("Removed the keybind from \247b"+mod.getName()+"\247f. Next time right click on the mod's button in the GUI and change the bind from there!");
				Resilience.getInstance().getFileManager().saveBinds();
				return true;
			}
		}
		Resilience.getInstance().getLogger().warningChat("Mod not found: \247c"+modName+"\247f. \247bTry right clicking on the mod's button in the GUI and changing it from there!");
		return true;
	}
	
}
