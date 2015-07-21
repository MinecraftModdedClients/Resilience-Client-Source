package com.krispdev.resilience.utilities;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.game.EntityUtils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public final class RenderUtils {
	
	private static MethodInvoker invoker = Resilience.getInstance().getInvoker();
	private static EntityUtils entityUtils = new EntityUtils();
	
	public static void setup3DLightlessModel(){
		Resilience.getInstance().getInvoker().setEntityLight(false);
		GL11.glEnable(3042);
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
	}
	
	public static void shutdown3DLightlessModel(){
		GL11.glDisable(3042);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		Resilience.getInstance().getInvoker().setEntityLight(true);
	}
	
	public static void drawOutlinedCrossedBoundingBox(AxisAlignedBB aa)
	{
		Tessellator t = Tessellator.instance;
		t.startDrawing(3);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.draw();
		t.startDrawing(3);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.draw();
		t.startDrawing(1);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.draw();
		t.startDrawing(1);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.draw();
	}
	
	public static void drawOutlinedBoundingBox(AxisAlignedBB aa)
	{
		Tessellator t = Tessellator.instance;
		t.startDrawing(3);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.draw();
		t.startDrawing(3);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.draw();
		t.startDrawing(1);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.draw();
	}
	
	public static void drawBoundingBox(AxisAlignedBB aa)
	{
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(aa.minX, aa.maxY, aa.maxZ);
		t.addVertex(aa.minX, aa.minY, aa.maxZ);
		t.addVertex(aa.minX, aa.maxY, aa.minZ);
		t.addVertex(aa.minX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.minZ);
		t.addVertex(aa.maxX, aa.minY, aa.minZ);
		t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		t.addVertex(aa.maxX, aa.minY, aa.maxZ);
		t.draw();
	}
	
	public static void drawESP(boolean crossed, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double r, double g, double b, double a, double r2, double g2, double b2, double a2){
		GL11.glPushMatrix();
		setup3DLightlessModel();
		invoker.setEntityLight(false);
		GL11.glColor4d(r, g, b, a);
		drawBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
		GL11.glColor4d(r2, g2, b2, a2);
		GL11.glLineWidth(0.5F);
		if(crossed) {
			drawOutlinedCrossedBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
		}else{
			drawOutlinedBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
		}
		invoker.setEntityLight(true);
		shutdown3DLightlessModel();
		GL11.glPopMatrix();
	}
	
	public static void drawPlayerESP(EntityPlayer player){
		if(entityUtils.isThePlayer(player)) return;
		boolean friend = entityUtils.isEntityFriend(player);
		boolean enemy = entityUtils.isEntityEnemy(player);
		drawESP(true, player.boundingBox.minX - invoker.getRenderPosX() - 0.1, 
				player.boundingBox.minY - invoker.getRenderPosY(), 
				player.boundingBox.minZ - invoker.getRenderPosZ() - 0.1, 
				player.boundingBox.maxX - invoker.getRenderPosX() + 0.1, 
				player.boundingBox.maxY+0.15 - invoker.getRenderPosY(), 
				player.boundingBox.maxZ - invoker.getRenderPosZ() + 0.1, 
				friend ? 0.2 : enemy ? 1 : 1, (friend || enemy) ? 0.2 : 1, enemy ? 0.2 : 1, 0.19, friend ? 0.5 : enemy ? 1 : 1, (friend || enemy) ? 0.5 : 1, enemy ? 0.5 : 1, 1);
	}
	
	public static void drawTracer(double bX, double bY, double bZ, double eX, double eY, double eZ, double r, double g, double b, double alpha){
		GL11.glPushMatrix();
		setup3DLightlessModel();
		
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glColor4d(r, g, b, alpha);
		GL11.glLineWidth(1);
		
		GL11.glBegin(GL11.GL_LINE_LOOP);
		
			GL11.glVertex3d(bX, bY, bZ);
			GL11.glVertex3d(eX, eY, eZ);
		
		GL11.glEnd();
		
		shutdown3DLightlessModel();
		GL11.glPopMatrix();
	}
	
}
