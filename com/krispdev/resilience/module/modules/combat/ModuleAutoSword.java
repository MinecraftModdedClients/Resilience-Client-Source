package com.krispdev.resilience.module.modules.combat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleAutoSword extends DefaultModule{

	public ModuleAutoSword(){
		super("AutoSword", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically switches to your swords when you hit an entity");
	}
	
	@Override
	public void onClick(EventOnClick event){
		if(event.getButton() == 0 && Resilience.getInstance().getWrapper().getPlayer() != null && invoker.getObjectMouseOver() != null && invoker.getObjectMouseOver().typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY){
			for(int i=0; i<9; i++){
				if(invoker.getItemAtSlotHotbar(i) == null) continue;
				Item item = invoker.getItemAtSlotHotbar(i).getItem();
				if(item != null && item instanceof ItemSword){
					invoker.setInvSlot(i);
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
