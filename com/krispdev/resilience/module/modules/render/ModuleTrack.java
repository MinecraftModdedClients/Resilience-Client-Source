package com.krispdev.resilience.module.modules.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.EntityOtherPlayerMP;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;

public class ModuleTrack extends DefaultModule{
	
	public ModuleTrack(){
		super("Track", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Draws a line behind the specified player");
	}

	@Override
	public void onUpdate(EventOnUpdate event){
		for(Object o : invoker.getEntityList()){
			if(o instanceof EntityOtherPlayerMP){
				EntityOtherPlayerMP player = (EntityOtherPlayerMP) o;
				
				if(invoker.getPlayerName(player).equalsIgnoreCase(Resilience.getInstance().getValues().trackName)){
					Resilience.getInstance().getValues().trackPosList.add(new Double[]{invoker.getPosX(player), invoker.getPosY(player), invoker.getPosZ(player)});
				}
			}
		}
	}
	
	@Override
	public void onRender(EventOnRender event){
		GL11.glPushMatrix();
		RenderUtils.setup3DLightlessModel();
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for(Double[] pos : Resilience.getInstance().getValues().trackPosList) {
			GL11.glColor4f(1, 0, 0, 0.7f);
			GL11.glVertex3d(pos[0] - invoker.getRenderPosX(), pos[1]  - invoker.getRenderPosY(), pos[2] - invoker.getRenderPosZ());
		}
		GL11.glEnd();
		RenderUtils.shutdown3DLightlessModel();
		GL11.glPopMatrix();
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
