package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdIRCUnnick extends Command{

	public CmdIRCUnnick() {
		super("irc unnick", "", "Unnicks you to your normal name in the IRC");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		Resilience.getInstance().getIRCChatManager().bot.changeNick(Resilience.getInstance().getInvoker().getSessionUsername());
		return true;
	}

}
