package com.krispdev.resilience.module.modules.misc;

import java.io.IOException;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.irc.src.IrcException;
import com.krispdev.resilience.irc.src.NickAlreadyInUseException;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleIRC extends DefaultModule{
	
	public ModuleIRC(){
		super("IRC", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Enables/Disables the Internet Relay Chat ingame");
	}

	@Override
	public void onEnable() {
		new Thread(){
			public void run(){
				Resilience.getInstance().getIRCManager().bot.joinChannel(Resilience.getInstance().getValues().ircChannel);
			}
		}.start();

		Resilience.getInstance().getValues().ircEnabled = true;
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getValues().ircEnabled = false;
		Resilience.getInstance().getIRCManager().bot.partChannel(Resilience.getInstance().getValues().ircChannel);
	}
	
}
