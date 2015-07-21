package com.krispdev.resilience.module.modules.combat;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventHealthUpdate;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.game.InventoryUtils;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class ModuleAutoSoup extends DefaultModule{
	
	private boolean shouldSoup = false;
	private MethodInvoker invo = Resilience.getInstance().getInvoker();
	private InventoryUtils invUtils = new InventoryUtils();
	
	//Bowl = 281, soup = 282
	
	private int prevSlot = -1;
	private int soupId = 282, bowlId = 281;
	
	public ModuleAutoSoup(){
		super("AutoSoup", Keyboard.KEY_O, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically eats soup when health is low. (For KitPvP)");
	}
	
	@Override
	public void onHealthUpdate(EventHealthUpdate e) {
		if(e.getHealth() < Resilience.getInstance().getValues().autoSoupHealth.getValue()){
			shouldSoup = true;
		}else{
			shouldSoup = false;
		}
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		
		if(shouldSoup){
			
			if(prevSlot == -1){
				prevSlot = invo.getCurInvSlot();
			}
			
			int slotHotbar = invUtils.getSlotOfHotbarItem(soupId);

			if(slotHotbar != -1){
				invo.setInvSlot(slotHotbar);
				invUtils.sendItemUse(invo.getItemAtSlot(slotHotbar));
			}else{
				int invSlot = invUtils.getSlotOfInvItem(soupId);
		
				if(invSlot != -1){
					
					int freeSlot = invUtils.getFreeSlotInInv(bowlId);
					int freeHotbarSlot = invUtils.getFreeSlotInHotbar(0);
					
					if(freeHotbarSlot != -1){
						invUtils.click(freeSlot, 1);
						invUtils.click(invSlot, 1);
					}else{
						int hotBarSlotBad = invUtils.getSlotOfHotbarItem(bowlId);
						
						if(hotBarSlotBad != -1){
							invo.dropItemStack(hotBarSlotBad);
							invUtils.click(invSlot, 1);
							invUtils.sendItemUse(invo.getItemAtSlot(invSlot));
						}
						
						invUtils.click(invSlot, 1);
						invUtils.click(freeSlot, 1);
					}
				}
			}
			
		}else{
			if(prevSlot != -1 && invo.getCurInvSlot() != prevSlot){
				invo.setInvSlot(prevSlot);
				prevSlot = -1;
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
