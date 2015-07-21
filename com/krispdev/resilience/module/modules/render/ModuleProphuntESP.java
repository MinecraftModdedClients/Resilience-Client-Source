package com.krispdev.resilience.module.modules.render;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;

public class ModuleProphuntESP extends DefaultModule{
	
	private ArrayList<EntityFallingBlock> entities = new ArrayList<EntityFallingBlock>();
	private int[] badIds = new int[]{78};
	private int ticks = 0;
	
	public ModuleProphuntESP(){
		super("Prophunt ESP", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Draws a coloured box around Prohunt objects");
	}

	@Override
	public void onRender(EventOnRender event){
		entities.clear();
		for(Object o : invoker.getEntityList()){
			if(o instanceof EntityFallingBlock){
				EntityFallingBlock e = (EntityFallingBlock) o;
				if(!entities.contains(e)){	
					entities.add(e);
				}
			}
		}
		for(EntityFallingBlock e : entities){
			Block block = invoker.getBlock((int)invoker.getPosX(e)-1, (int)invoker.getPosY(e), (int)invoker.getPosZ(e));
			//if(invoker.getIdFromBlock(block) == 0) continue;
			GL11.glPushMatrix();
			RenderUtils.setup3DLightlessModel();
			RenderUtils.drawESP(false, invoker.getPosX(e)-invoker.getRenderPosX()-0.5, invoker.getPosY(e)-invoker.getRenderPosY()-0.5, invoker.getPosZ(e)-invoker.getRenderPosZ()-0.5, invoker.getPosX(e)-invoker.getRenderPosX()+0.5, invoker.getPosY(e)-invoker.getRenderPosY()+0.5, invoker.getPosZ(e)-invoker.getRenderPosZ()+0.5, 0.5, 0.5, 1, 0.19, 0.5, 0.5, 1, 1);
			RenderUtils.shutdown3DLightlessModel();
			GL11.glPopMatrix();
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
