package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdSearchDel extends Command{
	
	public CmdSearchDel(){
		super("search del ", "[Block Id]", "Deletes a block from the search list");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		float id = Float.parseFloat(cmd.split("del ")[1]);
		
		for(int iterator = 0; iterator < Resilience.getInstance().getValues().searchIds.size(); iterator++){
			if(Resilience.getInstance().getValues().searchIds.get(iterator)[0] == id){
				Resilience.getInstance().getValues().searchIds.remove(iterator);
				Resilience.getInstance().getValues().ticksForSearch = 71;
				Resilience.getInstance().getLogger().infoChat("Removed block from the search list");
				return true;
			}
		}
		Resilience.getInstance().getLogger().warningChat("Block not found!");
		return true;
	}
	
}
