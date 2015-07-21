package com.krispdev.resilience.module.modules.combat;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.utilities.game.InventoryUtils;

public class ModuleAutoArmour extends DefaultModule{
	
	int[] ids = new int[]{298,299,300,301,314,315,316,317,302,303,304,305,306,307,308,309,310,311,312,313};
	
	int[] bootIds = new int[]{313, 309, 305, 317, 301};
	int[] pantIds = new int[]{312, 308, 304, 316, 300};
	int[] chestIds = new int[]{311, 307, 303, 315, 299};
	int[] helmIds = new int[]{310, 306, 302, 314, 298};
	
	private InventoryUtils utils = new InventoryUtils();
	private int prevSlot = -1;
	private Timer timer = new Timer();
	
	public ModuleAutoArmour(){
		super("AutoArmor", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically puts on armor when your old armor breaks");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		if(!timer.hasTimePassed(170)) return;
		timer.reset();
		
		if(prevSlot != -1){
			invoker.setInvSlot(prevSlot);
			prevSlot = -1;
		}
		
		boolean boots = true;
		boolean pants = true;
		boolean shirt = true;
		boolean helm = true;
		
		for(int i=0; i<invoker.getArmourInventory().length; i++){
			if(i==0){
				if(invoker.getArmourInventory()[i] == null){
					boots = false;
				}
			}
			if(i==1 && invoker.getArmourInventory()[i] == null){
				pants = false;
			}
			if(i==2 && invoker.getArmourInventory()[i] == null){
				shirt = false;
			}
			if(i==3 && invoker.getArmourInventory()[i] == null){
				helm = false;
			}
		}
		
		if(!boots){
			boolean hot = false;
			boolean inv = false;
			
			int slot = -1;
			for(int id : bootIds){
				int invSlot = utils.getSlotOfInvItem(id);
				if(invSlot != -1){
					inv = true;
					slot = invSlot;
					break;
				}else{
					int newSlot = utils.getSlotOfHotbarItem(id);
					if(newSlot != -1){
						hot = true;
						slot = newSlot;
						break;
					}
				}
			}
			
			if(slot != -1 && inv){
				utils.click(slot, 1);
			}else if(slot != -1 && hot){
				prevSlot = invoker.getCurInvSlot();
				
				invoker.setInvSlot(slot);
				invoker.sendUseItem(invoker.getCurrentItem(), Resilience.getInstance().getWrapper().getPlayer());
			}
		}
		
		if(!pants){
			boolean hot = false;
			boolean inv = false;
			
			int slot = -1;
			for(int id : pantIds){
				int invSlot = utils.getSlotOfInvItem(id);
				if(invSlot != -1){
					inv = true;
					slot = invSlot;
					break;
				}else{
					int newSlot = utils.getSlotOfHotbarItem(id);
					if(newSlot != -1){
						hot = true;
						slot = newSlot;
						break;
					}
				}
			}
			
			if(slot != -1 && inv){
				utils.click(slot, 1);
			}else if(slot != -1 && hot){
				prevSlot = invoker.getCurInvSlot();
				
				invoker.setInvSlot(slot);
				invoker.sendUseItem(invoker.getCurrentItem(), Resilience.getInstance().getWrapper().getPlayer());
			}
		}
		
		if(!shirt){
			boolean hot = false;
			boolean inv = false;
			
			int slot = -1;
			for(int id : chestIds){
				int invSlot = utils.getSlotOfInvItem(id);
				if(invSlot != -1){
					inv = true;
					slot = invSlot;
					break;
				}else{
					int newSlot = utils.getSlotOfHotbarItem(id);
					if(newSlot != -1){
						hot = true;
						slot = newSlot;
						break;
					}
				}
			}
			
			if(slot != -1 && inv){
				utils.click(slot, 1);
			}else if(slot != -1 && hot){
				prevSlot = invoker.getCurInvSlot();
				
				invoker.setInvSlot(slot);
				invoker.sendUseItem(invoker.getCurrentItem(), Resilience.getInstance().getWrapper().getPlayer());
			}
		}

		if(!helm){
			boolean hot = false;
			boolean inv = false;
			
			int slot = -1;
			for(int id : helmIds){
				int invSlot = utils.getSlotOfInvItem(id);
				if(invSlot != -1){
					inv = true;
					slot = invSlot;
					break;
				}else{
					int newSlot = utils.getSlotOfHotbarItem(id);
					if(newSlot != -1){
						hot = true;
						slot = newSlot;
						break;
					}
				}
			}

			if(slot != -1 && inv){
				utils.click(slot, 1);
			}else if(slot != -1 && hot){
				prevSlot = invoker.getCurInvSlot();
				
				invoker.setInvSlot(slot);
				invoker.sendUseItem(invoker.getCurrentItem(), Resilience.getInstance().getWrapper().getPlayer());
			}
		}
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
