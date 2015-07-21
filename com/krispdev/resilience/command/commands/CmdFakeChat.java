package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdFakeChat extends Command{
	
	public CmdFakeChat(){
		super("fakechat ", "[message]", "Makes a fake chat message appear. Can use & color codes");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String args[] = cmd.split("fakechat ");
		Resilience.getInstance().getInvoker().addChatMessage(args[1].replaceAll("&", "\247"));
		return true;
	}
	
}
