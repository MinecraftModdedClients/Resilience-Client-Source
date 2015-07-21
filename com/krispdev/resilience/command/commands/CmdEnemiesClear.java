package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.relations.Enemy;

public class CmdEnemiesClear extends Command{
	
	public CmdEnemiesClear(){
		super("enemies clear", "", "Clears the enemy list");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		Enemy.enemyList.clear();
		Resilience.getInstance().getLogger().infoChat("Cleared the enemy list");
		return true;
	}
	
}
