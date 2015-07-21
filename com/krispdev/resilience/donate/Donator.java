package com.krispdev.resilience.donate;

import java.util.ArrayList;
import java.util.List;

public class Donator implements Comparable<Donator>{
	
	public static List<Donator> donatorList = new ArrayList<Donator>();
	
	private String nick,message,ign;
	private float amount;
	
	public Donator(String nick, String message, float amount, String ign){
		this.nick = nick;
		this.message = message;
		this.amount = amount;
		this.ign = ign;
	}
	
	public String getNickname(){
		return nick;
	}
	
	public String getMessage(){
		return message;
	}
	
	public float getAmount(){
		return amount;
	}
	
	public String getIGN(){
		return ign;
	}

	@Override
	public int compareTo(Donator arg0) {
		return (int) (arg0.getAmount() - getAmount());
	}
	
	public static boolean isDonator(String str, float amount)
	{
		for(Donator d : donatorList){
			if(d.getIGN().equalsIgnoreCase(str) && d.getAmount() >= amount){
				return true;
			}
		}
		return false;
	}
}
