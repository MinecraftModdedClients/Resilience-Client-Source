package com.krispdev.resilience.command.commands;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdEnchant extends Command{
	
	public CmdEnchant(){
		super("enchant", "", "Forces max enchantments on an item in creative");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(Resilience.getInstance().getInvoker().isInCreativeMode()){
			ItemStack item = Resilience.getInstance().getInvoker().getCurrentItem();
			if(item != null){
				for(Enchantment e : Resilience.getInstance().getInvoker().getEnchantList()){
					if(e != null){
						Resilience.getInstance().getInvoker().addEnchantment(item, e, 127);
					}
				}
				Resilience.getInstance().getLogger().infoChat("Enchanted your "+item.getDisplayName());
				return true;
			}else{
				Resilience.getInstance().getLogger().warningChat("Error! No item in hand found");
				return true;
			}
		}else{
			Resilience.getInstance().getLogger().warningChat("Error! Player must be in creative mode");
			return true;
		}
	}
	
}
