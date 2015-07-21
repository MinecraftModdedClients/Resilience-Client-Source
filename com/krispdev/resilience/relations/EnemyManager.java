package com.krispdev.resilience.relations;

import net.minecraft.util.StringUtils;

public class EnemyManager {
	
	public static boolean isEnemy(String username){
		for(Enemy friend : Enemy.enemyList){
			if(friend.getName().trim().equalsIgnoreCase(StringUtils.stripControlCodes(username.trim()))){
				return true;
			}
		}
		return false;
	}
	
}
