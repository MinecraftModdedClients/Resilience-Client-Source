package com.krispdev.resilience.gui.objects.other;

import java.util.ArrayList;
import java.util.List;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.screens.TextRadarPanel;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Utils;

public class TextRadarComponent implements Comparable<TextRadarComponent>{
	
	public static List<TextRadarComponent> players = new ArrayList<TextRadarComponent>();
	
	private String name;
	private int blocksAway;
	private float posX;
	private float yPos;
	private boolean isFriend;
	private String tempName = name;
	private TextRadarPanel panel;
	
	public TextRadarComponent(String name, int blocksAway, int x, int y, boolean isFriend, TextRadarPanel panel){
		this.name = name;
		this.tempName = name;
		this.blocksAway = blocksAway;
		this.posX = x;
		this.yPos = y;
		this.panel = panel;
	}
	
	public void draw(int x, int y){
		for(Friend friend : Friend.friendList){
    		if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(tempName, friend.getName())){
    			tempName = tempName.replaceAll("(?i)"+friend.getName(), friend.getAlias());
    		}
    	}
		Utils.drawBetterRect(posX, yPos, posX + 104, yPos + 13, 0xbb010101, 0xbb262626);
		if(FriendManager.isFriend(name)){
			Resilience.getInstance().getStandardFont().drawString(tempName, (int)posX + 2, (int)yPos+0.5F, 0xff55FFFF);
		}else{
			Resilience.getInstance().getStandardFont().drawString(name, (int)posX + 2, (int)yPos+0.5F, 0xffffffff);
		}
		Utils.drawBetterRect(posX + 91, yPos + 1, posX + 103.5, yPos + 12.5, 0xff000000, FriendManager.isFriend(name) ? 0xbb4c4c4c : 0xbb262626);
		if(FriendManager.isFriend(name))
		{			
			Resilience.getInstance().getStandardFont().drawString("F", (int)posX + 94.5F, (int)yPos+1, 0xff55FFFF);
		}else{
			Resilience.getInstance().getStandardFont().drawString("F", (int)posX + 94.5F, (int)yPos+1, 0xffffffff);
		}
		Resilience.getInstance().getStandardFont().drawString(""+blocksAway, (int)posX + 88.5F - Resilience.getInstance().getStandardFont().getWidth(""+blocksAway), (int)yPos+1, 0xffffffff);
	}
	
	public boolean mouseClicked(int x, int y, int b){
		if(x >= posX + 91 && x <= posX + 103.5 && y >= yPos + 1 && y <= yPos + 12.5){
			Resilience.getInstance().getClickGui().focusWindow(panel);
			if(FriendManager.isFriend(name)){
				for(Friend friend : Friend.friendList){
					if(friend.getName().trim().equalsIgnoreCase(name) || friend.getAlias().trim().equalsIgnoreCase(name)){
						Friend.friendList.remove(Friend.friendList.indexOf(friend));
						Resilience.getInstance().getFileManager().saveFriends();
					}
				}
			}else{
				Friend.friendList.add(new Friend(name, name));
			}
			return true;
		}
		return false;
	}
	
	public int getBlocksAway(){
		return blocksAway;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public int compareTo(TextRadarComponent arg0) {
		return this.blocksAway - arg0.blocksAway;
	}

}
