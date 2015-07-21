package com.krispdev.resilience.account;

import java.util.ArrayList;

public class Account {
	
	public static ArrayList<Account> accountList = new ArrayList<Account>();
	
	private String username;
	private String password;
	private boolean premium;
	
	public Account(String username){
		if(username.equalsIgnoreCase("krisp")){
			username += "_NotRealKrisp";
		}
		this.username = username;
		this.password = "";
		this.premium = false;
	}
	
	public Account(String username, String password){
		if(username.equalsIgnoreCase("krisp")){
			username += "_NotRealKrisp";
		}
		this.username = username;
		this.password = password;
		if(password != null && !password.equals("")){
			this.premium = true;
		}else{
			this.premium = false;
		}
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public boolean isPremium(){
		return premium;
	}
}
