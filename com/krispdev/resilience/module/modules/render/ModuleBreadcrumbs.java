package com.krispdev.resilience.module.modules.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;

public class ModuleBreadcrumbs extends DefaultModule{
	
	public ModuleBreadcrumbs(){
		super("Breadcrumbs", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.RENDER);
		this.setDescription("Draws a line behind you");
		Resilience.getInstance().getEventManager().register(this);
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		if(invoker.getMotionX() != 0 || invoker.getMotionY() != 0 || invoker.getMotionZ() != 0){
			Resilience.getInstance().getValues().breadcrumbPosList.add(new Double[]{invoker.getPosX(), invoker.getPosY(), invoker.getPosZ()});
		}
	}

	@Override
	public void onRender(EventOnRender render){
		if(!isEnabled()) return;
		GL11.glPushMatrix();
		RenderUtils.setup3DLightlessModel();
		GL11.glLineWidth(1);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for(Double[] pos : Resilience.getInstance().getValues().breadcrumbPosList) {
			GL11.glColor4f(0.0F, 0, 1.0F, 0.7f);
			GL11.glVertex3d(pos[0] - invoker.getRenderPosX(), pos[1]  - invoker.getRenderPosY() - invoker.getEntityHeight(Resilience.getInstance().getWrapper().getPlayer()), pos[2] - invoker.getRenderPosZ());
		}
		GL11.glEnd();
		RenderUtils.shutdown3DLightlessModel();
		GL11.glPopMatrix();
	}
	
	@Override
	public void onEnable(){}

	@Override
	public void onDisable(){}
	
}
