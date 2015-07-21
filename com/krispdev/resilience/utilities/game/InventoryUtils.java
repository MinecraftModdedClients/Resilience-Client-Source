package com.krispdev.resilience.utilities.game;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class InventoryUtils {
	
	private MethodInvoker in = Resilience.getInstance().getInvoker();
	
	public int getSlotOfHotbarItem(int itemId){
		for(int i=0; i<9; i++){
			ItemStack is = in.getItemAtSlotHotbar(i);
			if(is != null && in.getIdFromItem(is.getItem()) == itemId){
				return i;
			}
		}
		return -1;
	}
	
	public int getSlotOfInvItem(int itemId){
		for(int i=9; i<36; i++){
			ItemStack is = in.getItemAtSlot(i);
			if(is != null && in.getIdFromItem(is.getItem()) == itemId){
				return i;
			}
		}
		return -1;
	}
	
	public int getFreeSlotInHotbar(int itemId){
		for(int i=0; i<9; i++){
			if(in.getItemAtSlot(i) == null){
				return i;
			}
			if(in.getItemAtSlot(i) != null){
				ItemStack itemAtSlot = in.getItemAtSlotHotbar(itemId);
				Item item = in.getItemById(itemId);				
				int idInSlot = in.getIdFromItem(itemAtSlot.getItem());
				if(itemAtSlot != null && itemAtSlot != null && idInSlot == itemId && itemAtSlot.stackSize < item.getItemStackLimit()){
					return i;
				}
			}else{
				return i;
			}
		}
		return -1;
	}
	
	public int getFreeSlotInInv(int itemId){
		for(int i=36; i<45; i++){
			if(in.getItemAtSlot(i) == null){
				return i;
			}
			if(in.getItemAtSlot(i) != null){
				ItemStack itemAtSlot = null;
				itemAtSlot = in.getItemAtSlot(i);
				if(itemAtSlot != null){
					Item item = in.getItemById(itemId);
					int idInSlot = in.getIdFromItem(itemAtSlot.getItem());
					if(itemAtSlot != null && item != null && idInSlot == itemId && itemAtSlot.stackSize < item.getItemStackLimit()){
						return i;
					}
				}
			}
		}
		return -1;
	}
	
	public void click(int slot, int mode){
		in.clickWindow(slot, mode, 0, Resilience.getInstance().getWrapper().getPlayer());
	}
	
	public void sendItemUse(ItemStack itemStack){
		in.sendUseItem(itemStack, Resilience.getInstance().getWrapper().getPlayer());
	}
	
}
