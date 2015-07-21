package com.krispdev.resilience.command.objects;

import java.util.ArrayList;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.interfaces.Bindable;

public class Macro implements Bindable, Listener{
	
	public static ArrayList<Macro> macroList = new ArrayList<Macro>();
	
	private int key;
	private String command;
	
	public Macro(int key, String command){
		this.key = key;
		this.command = command;
		Resilience.getInstance().getEventManager().registerGameListener(this);
	}
	
	public int getKey(){
		return key;
	}
	
	public String getCommand(){
		return command;
	}
	
	public void onKeyPress(int keyCode){
	}

	@Override
	public void onKeyDown(int keyCode) {
		if(keyCode == key && Resilience.getInstance().isEnabled()){
			Resilience.getInstance().getInvoker().sendChatMessage(command);
		}
	}
	
}
