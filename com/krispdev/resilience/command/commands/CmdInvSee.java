package com.krispdev.resilience.command.commands;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.StringUtils;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdInvSee extends Command{
	
	public CmdInvSee(){
		super("invsee ", "[Player]", "Shows you the player's item in hand and enchantments");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String args[] = cmd.split("invsee ");
		for(Object o : Resilience.getInstance().getInvoker().getEntityList()){
			if(o instanceof EntityOtherPlayerMP){
				EntityOtherPlayerMP player = (EntityOtherPlayerMP)o;
				if(Resilience.getInstance().getInvoker().getPlayerName(player).equalsIgnoreCase(args[1].trim())){
					Resilience.getInstance().getWrapper().getInGameGui().displayInv(player);
					Resilience.getInstance().getLogger().infoChat("Now viewing "+args[1]);
					return true;
				}
			}
		}
		Resilience.getInstance().getLogger().warningChat("Error! Player "+args[1]+" not found.");
		return true;
	}
	
}
