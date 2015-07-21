package com.krispdev.resilience.command.commands;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.StringUtils;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdRemoteView extends Command{
	
	public CmdRemoteView(){
		super("remoteview", " [Player]", "Renders the selected view. Toggle on/off by typing it again.");
	}

	//TODO Invoker stuff
	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(mc.renderViewEntity != mc.thePlayer){
			mc.renderViewEntity = mc.thePlayer;
			Resilience.getInstance().getLogger().infoChat("Now viewing from your player");
			return true;
		}
		String args[] = cmd.split("remoteview ");
		for(Object o : Resilience.getInstance().getInvoker().getEntityList()){
			if(o instanceof EntityOtherPlayerMP){
				EntityOtherPlayerMP otherPlayer = (EntityOtherPlayerMP) o;
				if(Resilience.getInstance().getInvoker().getPlayerName(otherPlayer).equalsIgnoreCase(args[1].trim())){
					mc.renderViewEntity = otherPlayer;
					Resilience.getInstance().getLogger().infoChat("Now viewing from \247b"+Resilience.getInstance().getInvoker().getPlayerName(otherPlayer)+"\247f's perspective");
					return true;
				}
			}
		}
		Resilience.getInstance().getLogger().warningChat("Error, player not found");
		return false;
	}
	
}
