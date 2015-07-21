package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdIRCPrefixChange extends Command{
	
	public CmdIRCPrefixChange(){
		super("ircprefix set ", "[New Prefix]", "Sets a new IRC prefix");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		Resilience.getInstance().setIRCPrefix(cmd.split("ircprefix set ")[1]);
		Resilience.getInstance().getLogger().infoChat("Set the IRC prefix to "+cmd.split("ircprefix set ")[1]);
		return true;
	}
	
}
