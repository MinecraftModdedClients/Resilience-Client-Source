package com.krispdev.resilience.gui.objects.screens;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.relations.EnemyManager;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Utils;

import com.krispdev.resilience.Resilience;

public class GuiRadarPanel extends DefaultPanel{

	private int length = 112;
	
	public GuiRadarPanel(String title, int x, int y, int x1, int y1, boolean visible) {
		super(title, x, y, x1, y1, visible);
	}

	@Override
	public void draw(int i, int j){
		length = 110+23;
		super.draw(i, j);
		
		if(isExtended()){
			
			Utils.drawRect(getX(), getY() + 17, getX1(), getY() + length, 0x99040404);
			
			Utils.drawRect(getX()+length/2-1, getY() + (length)/2-1+17/2+1, getX()+length/2-1, getY() + ((length)/2+1) + 17/2+1, 0xff9999ff);
			
			Utils.drawSmallHL(getX()+0.5F, getY()+length/2+17/2, getX()+length-0.5F, 0x44e4e4e4);
			Utils.drawSmallVL(getY()+length, getX()+length/2, getY()+17.5F, 0x44e4e4e4);
			Utils.drawSmallHL(getX(), getY()+17, getX()+length, 0x44e4e4e4);
			Utils.drawSmallVL(getY()+length, getX(), getY()+17.5F, 0x44e4e4e4);
			Utils.drawSmallHL(getX(), getY()+length, getX()+length, 0x44e4e4e4);
			Utils.drawSmallVL(getY()+length, getX()+length-0.5F, getY()+17.5F, 0x44e4e4e4);
			for(Object o : Resilience.getInstance().getInvoker().getEntityList()){
				if(o instanceof EntityOtherPlayerMP){
					
					EntityOtherPlayerMP entity = (EntityOtherPlayerMP) o;
					double diffX = Resilience.getInstance().getInvoker().getPosX() - Resilience.getInstance().getInvoker().getPosX(entity);
					double diffZ = Resilience.getInstance().getInvoker().getPosZ() - Resilience.getInstance().getInvoker().getPosZ(entity);
					double xzDiff = Math.sqrt((diffX*diffX) + (diffZ*diffZ));
						
					double angleDiff = Utils.wrapAngleTo180_double(Resilience.getInstance().getInvoker().getRotationYaw()-(( Math.atan2(diffZ, diffX)*180.0D)/Math.PI));
					double finalX = Math.cos(Math.toRadians(angleDiff)) * xzDiff * 2;
					double finalY = -Math.sin(Math.toRadians(angleDiff)) * xzDiff * 2;
					
					GL11.glPushMatrix();
					GL11.glTranslatef(getX() + length/2, getY() + length/2+17/2, 0 );
					if (xzDiff < 55 && !Resilience.getInstance().getInvoker().getPlayerName(entity).equalsIgnoreCase(Resilience.getInstance().getInvoker().getSessionUsername())){
						Resilience.getInstance().getRadarFont().drawCenteredString(Resilience.getInstance().getInvoker().getPlayerName(entity) + " \247f[\247b"+(int)Resilience.getInstance().getInvoker().getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), entity)+"\247f]", (float)finalX / 2, (float)finalY / 2+2, FriendManager.isFriend(Resilience.getInstance().getInvoker().getPlayerName(entity)) ? 0xff5555ff : EnemyManager.isEnemy(Resilience.getInstance().getInvoker().getPlayerName(entity)) ? 0xffff0000 : 0xffffffff);
						Utils.drawRect((float)finalX / 2, (float)finalY / 2, 2 + (float)finalX/2, 2+(float)finalY/2, FriendManager.isFriend(Resilience.getInstance().getInvoker().getPlayerName(entity)) ? 0xff444499 : EnemyManager.isEnemy(Resilience.getInstance().getInvoker().getPlayerName(entity)) ? 0xffff0000 : 0xffffffff);
						GL11.glScalef( 0.5F, 0.5F, 0.5F );
						EntityPlayer p = (EntityPlayer) entity;
						GL11.glScalef( 1F, 0.5F, 1F );
					}
					GL11.glPopMatrix();
				}
			}
		}
	}

}
