package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.relations.Enemy;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.relations.FriendManager;

public class CmdFriendAdd extends Command{

	public CmdFriendAdd(){
		super("friend add ", "[Username]", "Adds a friend to the friend list");
	}
	
	public boolean recieveCommand(String cmd){
		String check[] = cmd.split(" ");
		if(check.length > 3){
			return false;
		}
		String args[] = cmd.split("add ");
		for(Enemy enemy : Enemy.enemyList){
			if(enemy.getName().equalsIgnoreCase(args[1])){
				Enemy.enemyList.remove(Enemy.enemyList.indexOf(enemy));
				Resilience.getInstance().getFileManager().saveEnemies();
				break;
			}
		}
		if(!FriendManager.isFriend(args[1])){
			Friend.friendList.add(new Friend(args[1], args[1]));
			Resilience.getInstance().getFileManager().saveFriends();
			Resilience.getInstance().getLogger().infoChat("Friend added to the friend list");
			return true;
		}else{
			Resilience.getInstance().getLogger().warningChat("User already on the friend list");
			return true;
		}
	}
}
