package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdTrackClear extends Command{

	public CmdTrackClear(){
		super("track clear", "", "Clears the track line");
	}
	
	public boolean recieveCommand(String cmd){
		Resilience.getInstance().getValues().trackPosList.clear();
		Resilience.getInstance().getLogger().infoChat("Cleared the track line");
		return true;
	}
}
