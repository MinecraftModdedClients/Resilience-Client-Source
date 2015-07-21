package com.krispdev.resilience.module.modules.misc;

import net.minecraft.network.play.server.S02PacketChat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.events.player.EventPacketReceive;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Timer;

public class ModuleClientTips extends DefaultModule{
	
	private Timer timer = new Timer();
	private int interval = 5000;
	private int index;
	
	private String[] idleTips = new String[]{
			"Did you know that you can change binds and see descriptions by right clicking a button in the GUI?",
			"Using the console isn't something only to be used by advanced players. Hit the \"Minus\" (\"-\") button and you're able to customize the client in many ways!",
			"Ever want to know what kind of enchantments a player has before you PvP them? .invsee will show you their armour and they're item in hand!",
			"See a player on a map but don't know where they are or what they're doing? .remoteview will show you where they are and what they're looking at!",
			"You can add friends by typing \"friend add [Username]\" in the console, by using the mod \"Middle Click Friends\", or by clicking the \"F\" button in the Text Radar! Friends will not be attacked by KillAura.",
			"Ever want to know where your friends are playing? Add them with the Resilience Online feature and you'll be able to see just that!"
	};
	
	public ModuleClientTips(){
		super("Client Tips", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Gives tips about the client every 5 minutes, or after an event.");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		interval = 300*1000;
		if(timer.hasTimePassed(interval)){
			timer.reset();
			if(index >= idleTips.length){
				index = 0;
			}
			Resilience.getInstance().getLogger().infoChat("\247f[\247bTIP\247f] \2476"+idleTips[index]);
			index++;
		}
	}
	
	@Override
	public void onPacketReceive(EventPacketReceive event){
		if(event.getPacket() instanceof S02PacketChat){
			S02PacketChat p = (S02PacketChat) event.getPacket();
			
			String line = p.func_148915_c().getUnformattedText();
			if(line.toLowerCase().contains(".help") || line.toLowerCase().contains(".legit") && !line.contains(invoker.getSessionUsername())){
				Resilience.getInstance().getLogger().infoChat("\247f[\247bTIP\247f] \247c"+"Did someone just tell you to type \".help\"? Don't worry! You can type \".say .help\" and it will say it in the chat!");
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
