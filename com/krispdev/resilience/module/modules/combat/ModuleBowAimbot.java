package com.krispdev.resilience.module.modules.combat;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.utilities.game.EntityUtils;

public class ModuleBowAimbot extends DefaultModule{
	
	/**
	 * ALL CREDITS TO FLARE. USED WITH PERMISSION FROM AAROW!
	 */
	
	private float pitch,yaw;
	private EntityLivingBase e;
	private EntityUtils entityUtils = new EntityUtils();
	
	public ModuleBowAimbot(){
		super("BowAimbot", 0, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.COMBAT);
		this.setDescription("Automatically aims your bow at entities");
	}
	
	@Override
	public void onPreMotion(EventPreMotion event){
		if(invoker.getCurrentItem() != null)
		{
			if(invoker.getCurrentItem().getItem() instanceof ItemBow)
			{
				e = getCursorEntity();

				if(e == null) return;
				pitch = invoker.getRotationPitch();
				yaw = invoker.getRotationYaw();
				this.silentAim(e);
			}
		}
	}
	
	@Override
	public void onPostMotion(EventPostMotion event)
	{
		if(e != null && invoker.getCurrentItem() != null && invoker.getCurrentItem().getItem() instanceof ItemBow)
		{
			invoker.setRotationPitch(pitch);
			invoker.setRotationYaw(yaw);
		}
	}
	
	public void silentAim(EntityLivingBase e)
	{
		int bowCurrentCharge = invoker.getItemInUseDuration();
		float velocity = (float)bowCurrentCharge / 20.0F;
		velocity = (velocity * velocity + velocity * 2.0F) / 3.0F;
		
		if (velocity < 0.1D)
		{
			return;
		}
		
		if (velocity > 1.0F)
		{
			velocity = 1.0F;
		}
		
		double x = invoker.getPosX(e) - invoker.getPosX();
		double z = invoker.getPosZ(e) - invoker.getPosZ();
		double h = (invoker.getPosY(e) + (double)invoker.getEyeHeight(e)) - (invoker.getPosY() + (double)invoker.getEyeHeight());
		double h1 = Math.sqrt(x * x + z * z);
		double h2 = Math.sqrt(h1 * h1 + h * h);
		float f = (float)((Math.atan2(z, x) * 180D) / Math.PI) - 90F;
		
		float traj = -this.getTrajAngleSolutionLow((float)h1, (float)h, velocity);
		
		invoker.setRotationPitch(traj);
		invoker.setRotationYaw(f);
		
	}
	
	private float getTrajAngleSolutionLow(float d3, float d1, float velocity)
	{
		float g = (float) 0.006F;
	    float sqrt = (velocity*velocity*velocity*velocity) - (g * (g * (d3*d3) + 2F * d1 * (velocity*velocity)));
	    return (float) Math.toDegrees(Math.atan(((velocity*velocity) - Math.sqrt(sqrt)) / (g*d3)));
	}
	
	public EntityLivingBase getCursorEntity()
	{
		EntityLivingBase poorEntity = null;
		double distance = 1000;
		
		for(Object o : invoker.getEntityList())
		{
			if(!(o instanceof Entity)){continue;}
			final Entity e = (Entity)o;
			
			if(e instanceof EntityLivingBase && !entityUtils.isThePlayer(e))
			{
				if(e.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer()) > 140 || !invoker.canEntityBeSeen(e) || ((EntityLivingBase)e).deathTime > 0 || entityUtils.isEntityFriend(e))
				{
					continue;
				}
				
				if(poorEntity == null)
				{
					poorEntity = (EntityLivingBase)e;
				}
				
				double x = e.posX - invoker.getPosX();
				double z = e.posZ - invoker.getPosY();
				double h = (invoker.getPosY() + (double)invoker.getEyeHeight()) - (e.posY + (double)invoker.getEntityHeight(e));
				double h1 = Math.sqrt(x * x + z * z);
				float f = (float)((Math.atan2(z, x) * 180D) / Math.PI) - 90F;
				float f1 = (float)((Math.atan2(h, h1) * 180D) / Math.PI);
				
				double xdist = this.getDistanceBetweenAngles(f, invoker.getRotationYaw() % 360);
				double ydist = this.getDistanceBetweenAngles(f1, invoker.getRotationPitch() % 360);
				
				double dist = Math.sqrt((xdist * xdist) + (ydist * ydist));
				
				if(dist < distance)
				{
					poorEntity = (EntityLivingBase)e;
					distance = dist;
				}
			}
			
		}
		return poorEntity;
	}
	
	private float getDistanceBetweenAngles(float par1, float par2)
	{
	        float angle = (Math.abs(par1 - par2)) % 360;
	        if(angle > 180)
	        {
	            angle = (360 - angle);
	        }
	        return angle;
	}
	
	@Override
	public void onRender(EventOnRender event)
	{
		if(e != null && this.isEnabled())
		{
			GL11.glPushMatrix();
			RenderUtils.setup3DLightlessModel();
			double posX = ((e.lastTickPosX + (e.posX - e.lastTickPosX) - RenderManager.instance.renderPosX));
			double posY = ((e.lastTickPosY + 1 + (e.posY - e.lastTickPosY) - RenderManager.instance.renderPosY));
			double posZ = ((e.lastTickPosZ + (e.posZ - e.lastTickPosZ) - RenderManager.instance.renderPosZ));
			GL11.glColor4f(0.0F, 0.0F, 1.0F, 1);
			GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex3d(0, 0, 0);
			GL11.glVertex3d(posX, posY, posZ);
			GL11.glEnd();
			RenderUtils.shutdown3DLightlessModel();
	        GL11.glPopMatrix();
		}
	}
	
	@Override
	public void onEnable(){
		Resilience.getInstance().getEventManager().register(this);
	}
	
	@Override
	public void onDisable(){
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
