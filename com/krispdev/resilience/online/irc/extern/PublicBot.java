package com.krispdev.resilience.online.irc.extern;

import java.util.ArrayList;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.irc.src.PircBot;
import com.krispdev.resilience.online.gui.GuiGroupChat;

public class PublicBot extends PircBot{
	
	public String lastLine;
	
	private ArrayList<String> chatLines = new ArrayList<String>();
	private ArrayList<GuiGroupChat> guis = new ArrayList<GuiGroupChat>();
	private int toRemove = -1;
	
	public PublicBot(String username){
		this.setName(username);
	}
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message){
		chatLines.add(sender+": "+message);
		
		if(toRemove != -1){
			guis.remove(toRemove);
			toRemove = -1;
		}
		
		if(Resilience.getInstance().getValues().userChat != null){
			Resilience.getInstance().getValues().userChat.addLine(sender+": "+message);
		}
		
		for(GuiGroupChat gui : guis){
			if(gui.getChannel().equals(channel) && !gui.getChannel().equals(Resilience.getInstance().getValues().userChannel)){
				gui.addLine(sender+": "+message);
			}
		}
	}
	
	@Override
    protected void onJoin(String channel, String sender, String login, String hostname) {
		if(channel.equals(Resilience.getInstance().getValues().userChannel) && Resilience.getInstance().getValues().userChat == null && !sender.equals(this.getName())){
			Resilience.getInstance().getValues().userChat = new GuiGroupChat("My",Resilience.getInstance().getValues().userChannel, Resilience.getInstance().getIRCManager(), true);
			guis.add(Resilience.getInstance().getValues().userChat);
		}
		
		for(GuiGroupChat gui : guis){
			if(gui.getChannel().equals(channel)){
				gui.addLine(sender+" joined the chat!");
			}
		}
	}
	
	@Override
    protected void onPart(String channel, String sender, String login, String hostname) {
		for(GuiGroupChat gui : guis){
			if(gui.getChannel().equals(channel)){
				gui.addLine(sender+" left the chat.");
			}
		}
	}
	
	public ArrayList<String> getChatLines(){
		return chatLines;
	}
	
	public String getLastLine(){
		return lastLine;
	}

	public void addGui(GuiGroupChat guiGroupChat){
		guis.add(guiGroupChat);
	}
	
	public void removeGui(GuiGroupChat guiGroupChat){
		toRemove = guis.indexOf(guiGroupChat);
	}
	
}
