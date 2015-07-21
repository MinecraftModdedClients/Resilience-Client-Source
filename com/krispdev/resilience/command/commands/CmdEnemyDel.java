package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.relations.Enemy;

public class CmdEnemyDel extends Command{
	
	public CmdEnemyDel(){
		super("enemy del ", "[Username]", "Deletes an enemy from the enemy list");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String args[] = cmd.split("del ");
		for(Enemy enemy : Enemy.enemyList){
			if(enemy.getName().trim().equalsIgnoreCase(args[1])){
				Enemy.enemyList.remove(Enemy.enemyList.indexOf(enemy));
				Resilience.getInstance().getFileManager().saveEnemies();
				Resilience.getInstance().getLogger().infoChat("Deleted "+args[1]+" from the enemy list");
				return true;
			}
		}
		Resilience.getInstance().getLogger().warningChat("User not found!");
		return true;
	}
}
