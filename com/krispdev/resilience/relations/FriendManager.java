package com.krispdev.resilience.relations;

import net.minecraft.util.StringUtils;

public class FriendManager {
	
	public static boolean isFriend(String username){
		for(Friend friend : Friend.friendList){
			if(friend.getName().trim().equalsIgnoreCase(StringUtils.stripControlCodes(username.trim()))){
				return true;
			}
		}
		return false;
	}
	
}
