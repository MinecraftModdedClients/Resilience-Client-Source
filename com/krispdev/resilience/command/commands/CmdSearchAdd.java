package com.krispdev.resilience.command.commands;

import java.util.Random;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.utilities.XrayBlock;

public class CmdSearchAdd extends Command{
	
	private Random rand = new Random();
	
	public CmdSearchAdd(){
		super("search add ", "[Block Id]", "Adds a block to the search list");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		int r = rand.nextInt(100)+1;
		int g = rand.nextInt(100)+1;
		int b = rand.nextInt(100)+1;
		
		if(!containsId(Integer.parseInt(cmd.split("add ")[1].trim()))){
			Resilience.getInstance().getValues().searchIds.add(new Float[]{Float.parseFloat(cmd.split("add ")[1]), (float)r/100,(float)g/100,(float)b/100});
			Resilience.getInstance().getValues().ticksForSearch = 71;
			Resilience.getInstance().getLogger().infoChat("Added a block with id "+cmd.split("add ")[1]+" to the search list");
		}else{
			Resilience.getInstance().getLogger().warningChat("Block already on the list!");
		}
		
		return true;
	}
	
	public boolean containsId(float id){
		for(Float[] list : Resilience.getInstance().getValues().searchIds){
			if(list[0].floatValue() == id) return true;
		}
		return false;
	}
	
}
