package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.relations.Friend;

public class CmdFriendClear extends Command{
	
	public CmdFriendClear(){
		super("friends clear", "", "Clears the friend list");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		Friend.friendList.clear();
		Resilience.getInstance().getFileManager().saveFriends();
		Resilience.getInstance().getLogger().infoChat("Cleared the friend list");
		return true;
	}
	
}
