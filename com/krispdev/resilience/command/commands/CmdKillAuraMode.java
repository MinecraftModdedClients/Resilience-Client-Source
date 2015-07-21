package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdKillAuraMode extends Command{
	
	public CmdKillAuraMode(){
		super("killaura mode ", "[Players/Mobs/Animals/All]", "Sets the KillAura target mode");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String args[] = cmd.split("mode ");
		if(args[1].trim().equalsIgnoreCase("players")){
			Resilience.getInstance().getValues().players.setState(true);
			Resilience.getInstance().getValues().mobs.setState(false);
			Resilience.getInstance().getValues().animals.setState(false);
			
			Resilience.getInstance().getFileManager().saveConfigs();
			Resilience.getInstance().getLogger().infoChat("Set the KillAura mode to \247bPlayers");
			return true;
		}else if(args[1].trim().equalsIgnoreCase("mobs")){
			Resilience.getInstance().getValues().players.setState(false);
			Resilience.getInstance().getValues().mobs.setState(true);
			Resilience.getInstance().getValues().animals.setState(false);
			
			Resilience.getInstance().getFileManager().saveConfigs();
			Resilience.getInstance().getLogger().infoChat("Set the KillAura mode to \247bMobs");
			return true;
		}else if(args[1].trim().equalsIgnoreCase("animals")){
			Resilience.getInstance().getValues().animals.setState(true);
			Resilience.getInstance().getValues().players.setState(false);
			Resilience.getInstance().getValues().mobs.setState(false);
			
			Resilience.getInstance().getFileManager().saveConfigs();
			Resilience.getInstance().getLogger().infoChat("Set the KillAura mode to \247bAnimals");
			return true;
		}else if(args[1].trim().equalsIgnoreCase("all")){
			Resilience.getInstance().getValues().players.setState(true);
			Resilience.getInstance().getValues().mobs.setState(true);
			Resilience.getInstance().getValues().animals.setState(true);
			
			Resilience.getInstance().getFileManager().saveConfigs();
			Resilience.getInstance().getLogger().infoChat("Set the KillAura mode to \247bAll");
			return true;
		}
		
		Resilience.getInstance().getLogger().warningChat("Unknown mode \""+args[1]+"\"");
		return true;
	}
	
}
