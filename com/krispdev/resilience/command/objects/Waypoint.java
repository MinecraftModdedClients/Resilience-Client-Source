package com.krispdev.resilience.command.objects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderManager;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.RenderUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class Waypoint {
	
	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	
	public static List<Waypoint> waypointsList = new ArrayList<Waypoint>();
	
	private int x;
	private int y;
	private int z;
	
	private float r;
	private float g;
	private float b;
	
	String name;
	
	public Waypoint(String name, int x, int y, int z, float r, float b, float g){
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.g = g;
		this.b = b;
		this.name = name;
	}
	
	public void draw(){
		invoker.setEntityLight(false);
		
		RenderUtils.drawESP(false, 
				x - invoker.getRenderPosX()-0.5, 
				y - invoker.getRenderPosY()-0.5, 
				z - invoker.getRenderPosZ()-0.5, 
				x - invoker.getRenderPosX()+0.5, 
				y - invoker.getRenderPosY()+0.5, 
				z - invoker.getRenderPosZ()+0.5, 
				r, g, b, 0.183, r, g, b, 1);
		
		float f = 3;

		int color = 0xFFFFFFFF;  

		float scale = f / 150;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(x - invoker.getRenderPosX()), (float)(y - invoker.getRenderPosY()) + 1F, (float)(z - invoker.getRenderPosZ()));
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-invoker.getPlayerViewY(), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(invoker.getPlayerViewX(), 1.0F, 0.0F, 0.0F);

		GL11.glScalef(-scale, -scale, scale);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		byte byte0 = 0;
		int i = (int) (Resilience.getInstance().getWaypointFont().getWidth(name) / 2);
		Resilience.getInstance().getWaypointFont().drawString(name, -Resilience.getInstance().getWaypointFont().getWidth(name)/2, byte0, color);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		
		try
		{
			GL11.glPushMatrix();
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glLineWidth(1);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(2929);
			GL11.glDepthMask(false);
	        GL11.glLineWidth(1F);
	        double posX = x - RenderManager.renderPosX;
	        double posY = y - RenderManager.renderPosY;
	        double posZ = z - RenderManager.renderPosZ;
	    	GL11.glColor4d(r, g, b, 1);
	        GL11.glBegin(GL11.GL_LINE_LOOP);
	    	GL11.glVertex3d(0, 0, 0);
	    	GL11.glVertex3d(posX, posY, posZ);
	    	GL11.glEnd();
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(2929);
			GL11.glDepthMask(true);
			GL11.glDisable(3042);
			GL11.glPopMatrix();
		}catch(Exception e) {}
		
		invoker.setEntityLight(true);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	public float getR(){
		return r;
	}
	
	public float getG(){
		return g;
	}
	
	public float getB(){
		return b;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return "Name: \247b"+name+" \247fX: \247b"+x+" \247fY: \247b"+y+" \247fZ: \247b"+z;
	}
	
}
