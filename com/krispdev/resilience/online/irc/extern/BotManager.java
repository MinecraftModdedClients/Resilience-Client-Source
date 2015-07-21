package com.krispdev.resilience.online.irc.extern;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.online.gui.GuiGroupChat;

public class BotManager extends Thread{
	
	private String server = Resilience.getInstance().getValues().ircChatServer;
	public PublicBot bot;
	public String username;
	
	public BotManager(String username){
		this.username = username;
		bot = new PublicBot(username);
	}
	
	@Override
	public void run() {
		try{
			bot.setVerbose(true);
			bot.connect(server);
			bot.joinChannel(Resilience.getInstance().getValues().userChannel);
		}catch(Exception e){
			e.printStackTrace();
			bot.disconnect();
		}
	}
	
	public PublicBot getBot(){
		return bot;
	}
	
}
