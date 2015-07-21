package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.relations.Enemy;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.relations.FriendManager;

public class CmdFriendAddAlias extends Command{
	
	public CmdFriendAddAlias(){
		super("friend add ", "[Username] [Alias]", "Adds a friend to the friend list with the specified alias");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String args[] = cmd.split(" ");
		if(args.length <= 3){
			return false;
		}
		for(Enemy enemy : Enemy.enemyList){
			if(enemy.getName().equalsIgnoreCase(args[2])){
				Enemy.enemyList.remove(Enemy.enemyList.indexOf(enemy));
				Resilience.getInstance().getFileManager().saveEnemies();
				break;
			}
		}
		if(!FriendManager.isFriend(args[2])){
			Friend.friendList.add(new Friend(args[2], args[3]));
			Resilience.getInstance().getFileManager().saveFriends();
			Resilience.getInstance().getLogger().infoChat("User added to the friend list");
			return true;
		}else{
			Resilience.getInstance().getLogger().warningChat("User already on the friend list");
			return true;
		}
	}

}
