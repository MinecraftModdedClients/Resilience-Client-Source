package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdIRCNick extends Command{
	
	public CmdIRCNick(){
		super("irc nick ", "[New Nick Name]", "Sets the IRC nickname.");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String nick = "XxXN"+cmd.split("irc nick ")[1].trim();
		
		if(nick.equalsIgnoreCase("Krisp") || nick.toLowerCase().contains("krisp") || nick.toLowerCase().contains("kirsp") || nick.toLowerCase().contains("owner")){
			Resilience.getInstance().getLogger().warningChat("But, but, but... You're not Krisp! :O");
			return true;
		}
	    
		Resilience.getInstance().getIRCChatManager().bot.changeNick(nick);
		Resilience.getInstance().getLogger().infoChat("Set your IRC nickname to" + cmd.split("irc nick")[1]);
		return true;
	}
	
}
