package com.krispdev.resilience.module.modules.misc;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.relations.Enemy;
import com.krispdev.resilience.relations.EnemyManager;
import com.krispdev.resilience.relations.Friend;

public class ModuleMiddleClickFriends extends DefaultModule{
	
	public ModuleMiddleClickFriends(){
		super("Middle Click Friends", 0, NoCheatMode.COMPATIBLE);
		this.setVisible(false);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Adds the person you middle click on to your friends list");
	}

	@Override
	public void onClick(EventOnClick event){
		if(event.getButton() == 2){
			MovingObjectPosition obj = invoker.getObjectMouseOver();
			if(obj != null){
				if(obj.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY){
					Entity e = obj.entityHit;
					if(e instanceof EntityOtherPlayerMP){
						
						EntityOtherPlayerMP player = (EntityOtherPlayerMP) e;
						
						for(Friend friend : Friend.friendList){
							if(friend.getName().equalsIgnoreCase(invoker.getPlayerName(player))){
								Friend.friendList.remove(Friend.friendList.indexOf(friend));
								Resilience.getInstance().getFileManager().saveFriends();
								return;
							}
						}
						
						for(Enemy enemy : Enemy.enemyList){
							if(enemy.getName().equalsIgnoreCase(invoker.getPlayerName(player))){
								Enemy.enemyList.remove(Enemy.enemyList.indexOf(enemy));
								Resilience.getInstance().getFileManager().saveEnemies();
								break;
							}
						}
						
						Friend.friendList.add(new Friend(invoker.getPlayerName(player), invoker.getPlayerName(player)));
						Resilience.getInstance().getFileManager().saveFriends();
					}
				}
			}
		}
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
