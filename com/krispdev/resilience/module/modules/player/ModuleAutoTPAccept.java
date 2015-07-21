package com.krispdev.resilience.module.modules.player;

import net.minecraft.network.play.server.S02PacketChat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventPacketReceive;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.relations.FriendManager;

public class ModuleAutoTPAccept extends DefaultModule{
	
	public ModuleAutoTPAccept(){
		super("Auto TP Accept", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.PLAYER);
		this.setDescription("Automatically accepts TP requests from friends");
	}

	@Override
	public void onPacketReceive(EventPacketReceive event){
		if(event.getPacket() instanceof S02PacketChat){
			S02PacketChat p = (S02PacketChat) event.getPacket();
			
			try{
				String line = p.func_148915_c().getUnformattedText();
				String[] spaceArray = line.split(" ");
				for(Friend friend : Friend.friendList){
					if(spaceArray[0].toLowerCase().contains(friend.getName().toLowerCase())){
						String lineAfterSpace = line.replace(spaceArray[0], "");
						if(lineAfterSpace.trim().equals("has requested to teleport to you.")){
							invoker.sendChatMessage("/tpaccept");
						}
					}
				}
				if(FriendManager.isFriend(spaceArray[0])){
					
				}
			}catch(Exception e){
				e.printStackTrace();
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
