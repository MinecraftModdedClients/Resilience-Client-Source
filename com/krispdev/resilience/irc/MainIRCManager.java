package com.krispdev.resilience.irc;

import com.krispdev.resilience.Resilience;

public class MainIRCManager extends Thread{

	private String server = "irc.freenode.net";
	public MainIRCBot bot;
	public String username;
	
	public MainIRCManager(String username){
		this.username = username;
		bot = new MainIRCBot(username);
	}
	
	@Override
	public void run() {
		try{
			bot.setVerbose(true);
			bot.connect(server);
			bot.joinChannel(Resilience.getInstance().getValues().ircChannel);
		}catch(Exception e){
			e.printStackTrace();
			bot.disconnect();
		}
	}
	
	public MainIRCBot getBot(){
		return bot;
	}
	
}
