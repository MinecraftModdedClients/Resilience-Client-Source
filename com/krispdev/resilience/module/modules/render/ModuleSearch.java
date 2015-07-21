package com.krispdev.resilience.module.modules.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;

public class ModuleSearch extends DefaultModule{
	
	private ArrayList<Float[]> blocksList = new ArrayList<Float[]>();
	
	public ModuleSearch(){
		super("Search", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Lights up blocks that you say should be lit up");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		Resilience.getInstance().getValues().ticksForSearch++;
		if(Resilience.getInstance().getValues().ticksForSearch > 70){
			Resilience.getInstance().getValues().ticksForSearch = 0;
			blocksList.clear();
			new Thread(){
				int radius = (int)Resilience.getInstance().getValues().searchRange.getValue();
				public void run(){
					for(int x=-radius/2; x<radius/2; x++){
						for(int y=-radius/2; y<radius/2; y++){
							for(int z=-radius/2; z<radius/2; z++){
								
								int posX = (int) (invoker.getPosX() + x);
								int posY = (int) (invoker.getPosY() + y);
								int posZ = (int) (invoker.getPosZ() + z);
								
								Block block = invoker.getBlock(posX, posY, posZ);
								
								for(Float[] id : Resilience.getInstance().getValues().searchIds){
									if(invoker.getIdFromBlock(block) == (int)id[0].floatValue()){
										blocksList.add(new Float[]{(float) posX,(float) posY,(float) posZ,id[1],id[2],id[3]});
									}
								}	
							}
						}
					}
				}
			}.start();
		}
	}
	
	@Override
	public void onRender(EventOnRender event){
		for(Float[] coords : blocksList){
			GL11.glPushMatrix();
			RenderUtils.setup3DLightlessModel();
			
			RenderUtils.drawESP(false, coords[0]-invoker.getRenderPosX(), coords[1]-invoker.getRenderPosY(), coords[2]-invoker.getRenderPosZ(), coords[0]+1-invoker.getRenderPosX(), coords[1]+1-invoker.getRenderPosY(), coords[2]+1-invoker.getRenderPosZ(), coords[3], coords[4], coords[5], 0.19, coords[3], coords[4], coords[5], 1);
			
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
