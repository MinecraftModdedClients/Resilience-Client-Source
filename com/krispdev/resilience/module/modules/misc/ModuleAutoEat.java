package com.krispdev.resilience.module.modules.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.game.InventoryUtils;

public class ModuleAutoEat extends DefaultModule{
	
	private InventoryUtils utils = new InventoryUtils();
	private boolean goOnce = false;
	private int prevSlot = -1;
	private boolean finished = false;
	
	public ModuleAutoEat(){
		super("AutoEat", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Automatically eats food when you're hungry");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		if(prevSlot != -1 && finished && goOnce){
			invoker.setInvSlot(prevSlot);
			invoker.setUseItemKeyPressed(false);
			goOnce = false;
		}
		if(invoker.getFoodLevel() < 18){
			for(int i=0; i<9; i++){
				ItemStack item = invoker.getItemAtSlotHotbar(i);
				if(item != null && item.getItem() instanceof ItemFood){
					prevSlot = invoker.getCurInvSlot();
					invoker.setInvSlot(i);
					invoker.setUseItemKeyPressed(true);
					if(invoker.getFoodLevel() > 16){
						goOnce = true;
						finished = true;
					}
				}
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
