package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdXrayClear extends Command{
	
	public CmdXrayClear(){
		super("xray clear", "", "Clears the xray list");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(!cmd.startsWith("xray clear")) return false;
		Resilience.getInstance().getXrayUtils().xrayBlocks.clear();
		Resilience.getInstance().getLogger().infoChat("Cleared the xray list");
		Resilience.getInstance().getFileManager().saveXrayBlocks();
		if(Resilience.getInstance().getXrayUtils().xrayEnabled){
			Resilience.getInstance().getInvoker().loadRenderers();
		}
		return true;
	}
	
}
