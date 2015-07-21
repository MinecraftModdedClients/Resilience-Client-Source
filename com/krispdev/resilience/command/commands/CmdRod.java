package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.command.Command;

public class CmdRod extends Command{
	
	public CmdRod(){
		super("rod ", "[Amount of Rods]", "Setting this to a high number may crash the server");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		return false;
	}
	
}
