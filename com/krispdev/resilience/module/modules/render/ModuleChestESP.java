package com.krispdev.resilience.module.modules.render;

import net.minecraft.tileentity.TileEntityChest;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.gui.objects.buttons.ModOptionBox;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;

public class ModuleChestESP extends DefaultModule{

	private ModOptionBox drawTracers;
	
	public ModuleChestESP() {
		super("ChestESP", Keyboard.KEY_F6, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Draws a crossed box around chests");
		
		drawTracers = new ModOptionBox("Draw Tracers",0,0,false);
		guiExtras.add(drawTracers);
	}

	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
	@Override
	public void onRender(EventOnRender event){
		for(Object o : invoker.getTileEntityList()){
			if(o instanceof TileEntityChest){
				TileEntityChest chest = (TileEntityChest) o;
				
				RenderUtils.drawESP(true,
						invoker.getChestX(chest) - invoker.getRenderPosX(), 
						invoker.getChestY(chest) - invoker.getRenderPosY(), 
						invoker.getChestZ(chest) - invoker.getRenderPosZ(), 
						invoker.getChestX(chest)+1 - invoker.getRenderPosX(), 
						invoker.getChestY(chest)+1 - invoker.getRenderPosY(), 
						invoker.getChestZ(chest)+1 - invoker.getRenderPosZ(), 
						0.3, 0.48, 1, 0.183, 
						0.3, 0.48, 1, 1);
				
				if(drawTracers.isChecked()){
					RenderUtils.drawTracer(0, 0, 0, invoker.getChestX(chest)+0.5 - invoker.getRenderPosX(), invoker.getChestY(chest)+0.5 - invoker.getRenderPosY(), invoker.getChestZ(chest)+0.5 - invoker.getRenderPosZ(), 0.3, 0.48, 1, 1);
				}
			}
		}
	}

}
