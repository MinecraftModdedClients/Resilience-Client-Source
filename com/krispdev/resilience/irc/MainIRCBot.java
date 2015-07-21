package com.krispdev.resilience.irc;

import java.util.ArrayList;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.irc.src.PircBot;
import com.krispdev.resilience.online.gui.GuiGroupChat;

public class MainIRCBot extends PircBot{
	
	public MainIRCBot(String username){
		this.setName(username);
	}
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message){
		
		if(channel.equals(Resilience.getInstance().getValues().ircChannel)){
			if(!Resilience.getInstance().getValues().ircEnabled) return;
			
			boolean krisp = sender.equals("Krisp_");
			boolean bluscream = sender.equals("Bluscream");
			boolean nick = sender.startsWith("XxXN");
			
			if(nick){
				sender = sender.replaceFirst("XxXN", "");
			}
			
			if(sender.equalsIgnoreCase("Krisp_")){
				sender = "Krisp";
			}
			
			boolean vip = Donator.isDonator(sender, 5);
			
			Resilience.getInstance().getLogger().irc(sender+": "+message);
			Resilience.getInstance().getLogger().ircChat((nick ? "\247f[\2473NickName\247f]\247b " : "")+(krisp?"\247f[\247cOwner\247f] \247b":vip ? "\247f[\2476VIP\247f]\247b ": bluscream ? "\247f[\2473MOD\247f]\247b" : "\247b")+sender+"\2478:"+(krisp ?"\247c " : vip ? "\2476 " : "\247f ")+message);
		}
	}
	
	@Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
		if(!Resilience.getInstance().getValues().ircEnabled) return;
		
		boolean krisp = sender.equals("Krisp_");
		boolean nick = sender.startsWith("XxXN");
		
		if(nick){
			sender = sender.replaceFirst("XxXN", "");
		}
		
		if(sender.equalsIgnoreCase("Krisp_")){
			sender = "Krisp";
		}
		
		boolean vip = Donator.isDonator(sender, 5);
		
		Resilience.getInstance().getLogger().irc(sender+": "+message);
		Resilience.getInstance().getLogger().ircChat("\247c[PM] "+(nick ? "\247f[\2473NickName\247f]\247b " : "")+(krisp?"\247f[\247cOwner\247f] \247b":vip ? "\247f[\2476VIP\247f]\247b ":"\247b")+sender+"\2478:"+(krisp ?"\247c " : vip ? "\2476 " : "\247f ")+message);
	}
	
	@Override
	protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
		if(!Resilience.getInstance().getValues().ircEnabled) return;
		if(kickerNick.equalsIgnoreCase("Krisp_")){
			kickerNick = "Krisp";
		}
		
		if(recipientNick.equalsIgnoreCase(this.getNick())){
			Resilience.getInstance().getLogger().ircChat("\247cYou have been kicked by \247f"+kickerNick+" \247cfor \247f"+reason);
			Resilience.getInstance().getLogger().ircChat("\247cRestart minecraft to get back into the IRC");
		}else{
			Resilience.getInstance().getLogger().ircChat(recipientNick+"\247c has been kicked by \247f"+kickerNick+" \247cfor \247f"+reason);
		}
	}

}
