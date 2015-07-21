package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.utilities.XrayBlock;

public class CmdXrayAdd extends Command{
	
	public CmdXrayAdd(){
		super("xray add ", "[Block ID]", "Adds the specified block to the xray list");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(!cmd.startsWith("xray add")) return false;
		String args[] = cmd.split("add ");
		if(!containsId(Integer.parseInt(args[1]))){
			Resilience.getInstance().getXrayUtils().xrayBlocks.add(new XrayBlock(Integer.parseInt(args[1])));	
			Resilience.getInstance().getLogger().infoChat("Added block " + args[1] + " to the xray list");
			Resilience.getInstance().getFileManager().saveXrayBlocks();
			if(Resilience.getInstance().getXrayUtils().xrayEnabled){
				Resilience.getInstance().getInvoker().loadRenderers();
			}
		}else{
			Resilience.getInstance().getLogger().warningChat("Block already on the xray list!");
		}
		return true;
	}
	
	public boolean containsId(int id){
		for(XrayBlock block : Resilience.getInstance().getXrayUtils().xrayBlocks){
			if(block.getId() == id) return true;
		}
		return false;
	}
	
}
