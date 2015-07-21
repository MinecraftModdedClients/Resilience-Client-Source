package com.krispdev.resilience.command.commands;

import net.minecraft.item.ItemStack;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdReName extends Command{
	
	public CmdReName(){
		super("rename ", "[Name]", "Renames your current item to a huge string in creative");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(Resilience.getInstance().getInvoker().isInCreativeMode()){
			String args[] = cmd.split("name ");
			ItemStack item = Resilience.getInstance().getInvoker().getCurrentItem();
			Resilience.getInstance().getInvoker().setStackDisplayName(item, args[1]);
			for(int i=0; i<100; i++){
				Resilience.getInstance().getInvoker().setStackDisplayName(item, Resilience.getInstance().getInvoker().getItemDisplayName(item).concat(args[1]));
			}
			Resilience.getInstance().getLogger().infoChat("Renamed your current item");
			return true;
		}else{
			Resilience.getInstance().getLogger().infoChat("Error! You must be in creative");
			return false;
		}
	}
	
}
