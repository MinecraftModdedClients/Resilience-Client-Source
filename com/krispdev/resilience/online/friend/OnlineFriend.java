package com.krispdev.resilience.online.friend;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.Utils;

public class OnlineFriend {
	
	private boolean online;
	private String username, status;
	private String mainURL = "http://resilience.krispdev.com/";
	private String channel;
	
	public OnlineFriend(String username, String status, String channel, boolean online){
		this.username = username;
		this.status = status;
		this.online = online;
		this.channel = channel;
	}
	
	public void update(){
		new Thread(){
			public void run(){
				try {
					String info = Utils.sendGetRequest(mainURL+"getFriend.php?ign="+Resilience.getInstance().getInvoker().getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&friend="+username);
					String[] splitInfo = info.split(":");
					online = Boolean.parseBoolean(splitInfo[1]);
					status = splitInfo[2];
					channel = splitInfo[3];
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getStatus(){
		return status;
	}
	
	public boolean isOnline(){
		return online;
	}

	public String getChannel() {
		return channel;
	}
	
}
