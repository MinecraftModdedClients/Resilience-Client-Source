package com.krispdev.resilience.command.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.net.InetAddress;

import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.server.MinecraftServer;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdGetIP extends Command{
	
	public CmdGetIP(){
		super("getip", "", "Copy's the server IP to your clipboard as well as displaying it");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(mc.currentServerData != null){	
		ServerAddress serverAddress = new ServerAddress(mc.currentServerData.serverIP, mc.currentServerData.field_82821_f);
		Resilience.getInstance().getLogger().infoChat("Server IP \247b"+InetAddress.getByName(serverAddress.getIP()).getHostAddress()+" \247f(\247b"+mc.currentServerData.serverIP+"\247f)"+" has been copied to you clipboard.");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(InetAddress.getByName(serverAddress.getIP()).getHostAddress()), null);
		}else{
			Resilience.getInstance().getLogger().warningChat("Error, Server not found!");
		}
		return true;
	}
	
}
