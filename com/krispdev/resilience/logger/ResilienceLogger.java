package com.krispdev.resilience.logger;

import com.krispdev.resilience.Resilience;

public class ResilienceLogger {

	public void plain(String s){
		System.out.println("["+Resilience.getInstance().getName()+"] "+s);
	}
	
	public void info(String s){
		System.out.println("["+Resilience.getInstance().getName()+"] [INFO] "+s);
	}
	
	public void warning(String s){
		System.out.println("["+Resilience.getInstance().getName()+"] [WARNING] "+s);
	}
	
	public void irc(String s){
		System.out.println("["+Resilience.getInstance().getName()+"] [IRC] "+s);
	}
	
	public void plainChat(String s){
		Resilience.getInstance().getInvoker().addChatMessage("\247f[\2473"+Resilience.getInstance().getName()+"\247f] "+s);
	}
	
	public void infoChat(String s){
		Resilience.getInstance().getInvoker().addChatMessage("\247f[\2473"+Resilience.getInstance().getName()+"\247f] [\247bINFO\247f] "+s);
	}
	
	public void warningChat(String s){
		Resilience.getInstance().getInvoker().addChatMessage("\247f[\2473"+Resilience.getInstance().getName()+"\247f] [\247cWARNING\247f] "+s);
	}
	
	public void ircChat(String s){
		Resilience.getInstance().getInvoker().addChatMessage("\247f[\247bIRC\247f] "+s);
	}
	
}
