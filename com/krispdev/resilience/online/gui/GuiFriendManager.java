package com.krispdev.resilience.online.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.online.friend.OnlineFriend;
import com.krispdev.resilience.online.irc.extern.BotManager;
import com.krispdev.resilience.utilities.Timer;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class GuiFriendManager extends GuiScreen{

	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	private GuiScreen parent;
	public static ArrayList<OnlineFriend> friends = new ArrayList<OnlineFriend>();
	public static ArrayList<String> requests = new ArrayList<String>();
	private GuiFriendSlot friendSlot;
	public String state = "You have no pending requests";
	private Timer timer = new Timer();
	private Timer timer2 = new Timer();
	private int index;
	private ResilienceButton toggleButton;
	
	public GuiFriendManager(GuiScreen parent){
		friends.clear();
		this.parent = parent;
		state = "Loading...";
		update();
	}
	
	@Override
	public void initGui(){
		friendSlot = new GuiFriendSlot(invoker.getWrapper().getMinecraft(), this);
		friendSlot.registerScrollButtons(7, 8);
		invoker.addButton(this, new ResilienceButton(1, width/2-100, height-54+1+2, 200, 20, "Join"));
		invoker.addButton(this, new ResilienceButton(2, width/2-100, height-30+1+2, 200, 20, "Refresh"));
		invoker.addButton(this, new ResilienceButton(3, width/2+104, height-30+1+2, 100, 20, "Remove"));
		invoker.addButton(this, new ResilienceButton(4, width/2-204, height-30+1+2, 100, 20, "Add Friend"));
		invoker.addButton(this, new ResilienceButton(7, width/2-204, height-53+2, 100, 20, "Chat"));
		invoker.addButton(this, toggleButton = new ResilienceButton(8, width/2+104, height-53+2, 100, 20, "Go Offline"));
		invoker.addButton(this, new ResilienceButton(5, width/2-60-60, 14, 60, 15, "Accept"));
		invoker.addButton(this, new ResilienceButton(6, width/2+60, 14, 60, 15, "Deny"));
		invoker.addButton(this, new ResilienceButton(0, 4, 4, 50, 20, "Back"));
	}
	
	@Override
	public void drawScreen(int i, int j, float f){
		try{
			for(OnlineFriend friend : friends){
				if(friend.getUsername().equals("")){
					friends.remove(friends.indexOf(friend));
				}
			}
		}catch(Exception e){}
		
		if(timer.hasTimePassed(10000) && friends.size() > 0){
			timer.reset();
			try{
				friends.get(index);
			}catch(Exception e){
				index = 0;
			}
			OnlineFriend friend = friends.get(index);
			friend.update();
			
			index++;
		}
		
		if(friendSlot != null){
			friendSlot.drawScreen(i, j, f);
		}
		
		Resilience.getInstance().getStandardFont().drawCenteredString(state, invoker.getWidth()/2, 1.5F, 0xffffffff);
        
		int var4;

        for (var4 = 0; var4 < this.buttonList.size(); ++var4)
        {
        	if(invoker.getId(((GuiButton)this.buttonList.get(var4))) == 5 || invoker.getId(((GuiButton)this.buttonList.get(var4))) == 6){
            	if(requests.size() > 0){
        			((GuiButton)this.buttonList.get(var4)).drawButton(this.mc, i, j);
            	}
            }else{
            	((GuiButton)this.buttonList.get(var4)).drawButton(this.mc, i, j);
            }
        }
	}
	
	@Override
	public void actionPerformed(GuiButton btn){
		try{
			if(invoker.getId(btn) == 0){
				invoker.displayScreen(parent);
			}else if(invoker.getId(btn) == 1){
				OnlineFriend friend = friends.get(friendSlot.getSelectedSlot());
				if(friend.isOnline()){
					if(friend.getStatus().startsWith("Playing on ")){
						String server[] = friend.getStatus().split("Playing on ");
						String serverIPNonSplit = server[1];
						String[] serverIP = serverIPNonSplit.split(",port,");
						
						invoker.displayScreen(new GuiConnecting(this, invoker.getWrapper().getMinecraft(), new ServerData("Friend's Server", serverIP[0]+":"+serverIP[1])));
					}else{
						state = "User is not playing on a server!";
					}
				}else{
					state = "User is not online!";
				}
			}else if(invoker.getId(btn) == 5){
				if(requests.size() > 0){
					String result = Utils.sendGetRequest("http://resilience.krispdev.com/acceptRequest.php?ign="+invoker.getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&request="+requests.get(0));
					requests.remove(0);
					update();
					
					if(requests.size() > 0){
						state = "You have a friend request from \247b"+requests.get(0);
					}else{
						state = "You have no pending friend requests";
					}
				}
			}else if(invoker.getId(btn) == 6){
				if(requests.size() > 0){
					String result = Utils.sendGetRequest("http://resilience.krispdev.com/denyRequest.php?ign="+invoker.getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&request="+requests.get(0));
					requests.remove(0);
					update();
					
					if(requests.size() > 0){
						state = "You have a friend request from \247b"+requests.get(0);
					}else{
						state = "You have no pending friend requests";
					}
				}
			}else if(invoker.getId(btn) == 2){
				update();
			}else if(invoker.getId(btn) == 4){
				invoker.displayScreen(new GuiAddFriend(this));
			}else if(invoker.getId(btn) == 3){
				OnlineFriend friend = friends.get(friendSlot.getSelectedSlot());
				String result = Utils.sendGetRequest("http://resilience.krispdev.com/denyFriend.php?ign="+invoker.getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&friend="+friend.getUsername());
				update();
				
				if(result.equals("")){
					state = "Removed friend!";
				}else{
					state = "Error removing friend! Please try again later";
				}
			}else if(invoker.getId(btn) == 7){
				OnlineFriend friend = friends.get(friendSlot.getSelectedSlot());
				if(!friend.isOnline()){
					state = "User is not online!";
					return;
				}
				friend.update();
				GuiGroupChat groupChat = new GuiGroupChat(friend.getUsername(), friend.getChannel(), Resilience.getInstance().getIRCManager(), false);
			}else if(invoker.getId(btn) == 8){
				if(toggleButton.displayString.equals("Go Offline")){
					toggleButton.displayString = "Go Online";
					Utils.setOnline(false);
				}else{
					toggleButton.displayString = "Go Offline";
					Utils.setOnline(true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void update(){
		System.out.println(Resilience.getInstance().getValues().userChannel);
		friends.clear();
		requests.clear();
		new Thread(){
			public void run(){
				state = "Loading...";
				friends.clear();
				requests.clear();
				if(true){
					String result = Utils.sendGetRequest("http://resilience.krispdev.com/getFriends.php?ign="+invoker.getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword);
					if(!result.equals("")){
						try{
							String friends[] = result.split(";");
							for(String friend : friends){
								String info[] = friend.split(":");
								try{
									GuiFriendManager.friends.add(new OnlineFriend(info[0], info[2], info[3], Boolean.parseBoolean(info[1])));
								}catch(Exception e){
								}
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				
				String result = Utils.sendGetRequest("http://resilience.krispdev.com/getRequests.php?ign="+invoker.getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword);
				if(!result.equals("")){
					if(result.startsWith(":")){
						result = result.replaceFirst(":", "");
					}
					try{
						String requests[] = result.split(":");
						for(String request : requests){
							GuiFriendManager.requests.add(request);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				if(requests.size() > 0){
					state = "You have a friend request from \247b"+requests.get(0);
				}else{
					state = "You have no pending friend requests";
				}
			}
		}.start();
	}
	
}
