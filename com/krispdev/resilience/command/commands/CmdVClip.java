package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdVClip extends Command{

	public CmdVClip(){
		super("vclip ", "[Amount]", "Teleports you up/down a specified amount");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String args[] = cmd.split("vclip ");
		int posY = Integer.parseInt(args[1].trim());
		mc.thePlayer.setLocationAndAngles(mc.thePlayer.posX, posY+mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
		Resilience.getInstance().getLogger().infoChat("Teleported you "+(posY<0 ? "down ":"up ")+Math.abs(posY)+" block"+(Math.abs(posY)==1?"":"s"));
		return true;
	}
	
}
