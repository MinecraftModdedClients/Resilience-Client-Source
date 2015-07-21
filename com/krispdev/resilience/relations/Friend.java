package com.krispdev.resilience.relations;

import java.util.ArrayList;

public class Friend{
	
	public static ArrayList<Friend> friendList = new ArrayList<Friend>();
	
	private String name;
	private String alias;
	
	public Friend(String name, String alias){
		this.name = name;
		this.alias = alias;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getAlias(){
		return this.alias;
	}
	
	public void setAlias(String alias){
		this.alias = alias;
	}
}
