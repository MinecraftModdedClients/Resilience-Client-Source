package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdReload extends Command{
	
	public CmdReload(){
		super("reload", "", "Reloads the client configurations");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		Resilience.getInstance().getFileManager().init();
		Resilience.getInstance().getLogger().infoChat("Reload successful!");
		return true;
	}
	
}
