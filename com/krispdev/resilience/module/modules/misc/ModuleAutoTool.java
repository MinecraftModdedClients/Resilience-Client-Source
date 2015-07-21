package com.krispdev.resilience.module.modules.misc;

import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventBlockClick;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAutoTool extends DefaultModule{
	
	public ModuleAutoTool(){
		super("AutoTool", Keyboard.KEY_F7, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.MISC);
		this.setDescription("Automatically switches to the best tool on click");
	}

	@Override
	public void onBlockClicked(EventBlockClick event){
		float compare = 1;
		int slot = -1;
			
		for(int i=0; i<9; i++){
			try{
				ItemStack item = invoker.getItemAtSlotHotbar(i);
				
				if(item != null){
					float strength = invoker.getStrVsBlock(item, event.getBlock());
					
					if(strength > compare){
						compare = strength;
						slot = i;
					}
				}
				
			}catch(Exception e){}
		}
		
		if(slot != -1){
			invoker.setInvSlot(slot);
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
