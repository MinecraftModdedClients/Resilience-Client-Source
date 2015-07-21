package com.krispdev.resilience.module.modules.render;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.RenderManager;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.utilities.game.EntityUtils;

public class ModuleTracers extends DefaultModule{

	private EntityUtils entityUtils = new EntityUtils();
	
	public ModuleTracers(){
		super("Tracers", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Draws lines to all players around you");
	}

	public void onRender(EventOnRender event){
		for(Object o : invoker.getEntityList()){
			if(o instanceof EntityOtherPlayerMP){
				EntityOtherPlayerMP player = (EntityOtherPlayerMP) o;
				double posX = invoker.getLastTickPosX(player) - RenderManager.renderPosX;
				double posY = invoker.getLastTickPosY(player) - RenderManager.renderPosY;
				double posZ = invoker.getLastTickPosZ(player) - RenderManager.renderPosZ;
				
				boolean friend = entityUtils.isEntityFriend(player);
				boolean enemy = entityUtils.isEntityEnemy(player);
				
				RenderUtils.drawTracer(0, 0, 0, posX, posY+invoker.getEntityHeight(player)/2, posZ, friend ? 0 : 1, (friend || enemy) ? 0 : 1, enemy ? 0 : 1, 1);
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
