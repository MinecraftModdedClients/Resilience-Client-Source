package com.krispdev.resilience.gui.objects.screens;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.other.TextRadarComponent;
import com.krispdev.resilience.gui.objects.sliders.DefaultSlider;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Utils;

public class TextRadarPanel  extends DefaultPanel{

	private int count = 17;
	ArrayList<TextRadarComponent> inOrder = new ArrayList<TextRadarComponent>();
	
	public TextRadarPanel(String title, int x, int y, int x1, int y1, boolean visible) {
		super(title, x, y, x1, y1, visible);
	}
	
	@Override
	public void draw(int i, int j){
		TextRadarComponent.players.clear();
		inOrder.clear();
		super.draw(i, j);
		for(Object o : Resilience.getInstance().getInvoker().getEntityList()){
			if((Entity)o instanceof EntityOtherPlayerMP){
				EntityOtherPlayerMP otherPlayer;
				otherPlayer = (EntityOtherPlayerMP) o;
				if(!Resilience.getInstance().getInvoker().getPlayerName(otherPlayer).equalsIgnoreCase(Resilience.getInstance().getInvoker().getSessionUsername())){
					inOrder.add(new TextRadarComponent(Resilience.getInstance().getInvoker().stripControlCodes(Resilience.getInstance().getInvoker().getPlayerName(otherPlayer)), 
							(int) Resilience.getInstance().getInvoker().getDistanceToEntity(otherPlayer, Resilience.getInstance().getWrapper().getPlayer()), 
							getX() + 3, getY() + count, FriendManager.isFriend(otherPlayer.func_145748_c_().getUnformattedText()),this));
				}
			}
		}
		
		Collections.sort(inOrder);
		for(TextRadarComponent rad : inOrder){
			TextRadarComponent.players.add(new TextRadarComponent(rad.getName(), (int) rad.getBlocksAway(), getX() + 3, count + getY() + 4, FriendManager.isFriend(rad.getName()), this));
			count+=15;
		}
		
		if(inOrder.size() != 0 && isExtended()){
			Utils.drawRect(getX(), getY() + 17, getX1(), getY() + (15 * inOrder.size() + 20.5F), 0x99040404);
		}
		for(TextRadarComponent radar1 : TextRadarComponent.players){
			if(isExtended()){
				radar1.draw(i, j);
			}
		}
		count = 16;
	}
	
	@Override
	public boolean onClick(int i, int j, int k){
		if(super.onClick(i, j, k)){
			return true;
		}
		
		for(TextRadarComponent radar : TextRadarComponent.players){
			if(radar.mouseClicked(i, j, k)){
				return true;
			}
		}
		
		return false;
	}
	
}
