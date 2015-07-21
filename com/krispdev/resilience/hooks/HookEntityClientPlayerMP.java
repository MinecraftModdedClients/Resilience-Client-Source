package com.krispdev.resilience.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.Session;
import net.minecraft.world.World;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.event.events.player.EventHealthUpdate;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.module.modules.movement.ModuleFlight;

public class HookEntityClientPlayerMP extends EntityClientPlayerMP{

	private int cooldownTimer = 0;
	private String prevMessage = "";
	
	public HookEntityClientPlayerMP(Minecraft p_i45064_1_, World p_i45064_2_, Session p_i45064_3_, NetHandlerPlayClient netClientHandler, StatFileWriter p_i45064_5_) {
		super(p_i45064_1_, p_i45064_2_, p_i45064_3_, netClientHandler, p_i45064_5_);
	}
	
	@Override
	public void onUpdate(){
		if(cooldownTimer > 0){
			cooldownTimer--;
		}
		final EventOnUpdate updateEvent = new EventOnUpdate(this);
		updateEvent.onEvent();
		if(!updateEvent.isCancelled()){
			super.onUpdate();
		}else{
			updateEvent.setCancelled(false);
			return;
		}
	}
	
    @Override
    public void sendChatMessage(String s)
    {	
        if(s.startsWith(Resilience.getInstance().getCmdPrefix()) && Resilience.getInstance().isEnabled()){
        	for(Command cmd : Command.cmdList){
        		try{
        			String replaced = s.replaceFirst(Resilience.getInstance().getCmdPrefix(), "");
        			String[] inputWords = replaced.split(" ");
            		if(replaced.startsWith(cmd.getWords())){
            			try{
            				if(cmd.recieveCommand(s.replaceFirst(Resilience.getInstance().getCmdPrefix(), ""))){
            					break;
            				}
            			}catch(Exception e){
            				Resilience.getInstance().getLogger().warningChat("\247cInternal error! \247fSyntax: \247b"+cmd.getWords().concat(cmd.getExtras()));
            			}
            		}else if(s.replace(Resilience.getInstance().getCmdPrefix(), "").toLowerCase().startsWith(cmd.getFirstWord())){
            		}
        		}catch(Exception ex){
        			Resilience.getInstance().getLogger().warningChat("Reset the command prefix to \".\" due to strange internal exception!");
        			Resilience.getInstance().setCmdPrefix(".");
        		}
        	}
        }else{
        	if(s.startsWith(Resilience.getInstance().getIRCPrefix()) && Resilience.getInstance().isEnabled()){
        		try{
        			if(!Resilience.getInstance().getValues().ircEnabled){
        				Resilience.getInstance().getLogger().warningChat("Please enable \"IRC\" to chat in the IRC!");
        				return;
        			}
        			
        			String msg = s.replaceFirst(Resilience.getInstance().getIRCPrefix(), "");
        			
        			if(cooldownTimer < 2){
        				cooldownTimer = 30;
        				if(!msg.trim().equalsIgnoreCase(prevMessage)){
        					prevMessage = msg;
                			Resilience.getInstance().getIRCChatManager().bot.sendMessage(Resilience.getInstance().getValues().ircChannel, s.replaceFirst(Resilience.getInstance().getIRCPrefix(), ""));
                			System.out.println("sending message to "+Resilience.getInstance().getValues().ircChannel);
                			String msgToPlace = Resilience.getInstance().getIRCChatManager().bot.getNick();
                			
                			boolean nick = msgToPlace.startsWith("XxXN");
                			
                			if(nick){
                				msgToPlace = msgToPlace.replaceFirst("XxXN", "");
                			}
                			msgToPlace.replaceFirst("Krisp_", "Krisp");
                			
                			boolean krisp = msgToPlace.equals("Krisp_");
                			boolean vip = Donator.isDonator(msgToPlace, 5);
                			
                			Resilience.getInstance().getLogger().irc(msgToPlace+": "+msg);
                			Resilience.getInstance().getLogger().ircChat((nick ? "\247f[\2473NickName\247f]\247b " : "")+(krisp?"\247f[\247cOwner\247f] \247b":vip ? "\247f[\2476VIP\247f]\247b ":"\247b")+msgToPlace+"\2478:"+(krisp ?"\247c " : vip ? "\2476 " : "\247f ")+msg);
        				}else{
        					Resilience.getInstance().getLogger().warningChat("Please don't send the same message twice in a row!");
        				}
        			}else{
        				Resilience.getInstance().getLogger().warningChat("Please wait a bit between IRC chats!");
        			}
        			
        		}catch(Exception e){
        			Resilience.getInstance().getLogger().warningChat("Error in IRC. Have you enabled \"IRC\"? To be safe, we have reset the IRC prefix to \"@\"");
        			Resilience.getInstance().setIRCPrefix("@");
        			e.printStackTrace();
        		}
        	}else{
        		super.sendChatMessage(s);
        	}
        }
        
    }
	
	@Override
	public void sendMotionUpdates(){
		if(Resilience.getInstance().getValues().freecamEnabled) return;
		
		float prevPitch = Resilience.getInstance().getInvoker().getRotationPitch();
		float prevYaw = Resilience.getInstance().getInvoker().getRotationYaw();
		final EventPreMotion eventPre = new EventPreMotion(this);
		eventPre.onEvent();
		if(!eventPre.isCancelled()){
			super.sendMotionUpdates();
		}else{
			eventPre.setCancelled(false);
			return;
		}
		Resilience.getInstance().getInvoker().setRotationPitch(prevPitch);
		Resilience.getInstance().getInvoker().setRotationYaw(prevYaw);
		final EventPostMotion eventPost = new EventPostMotion(this);
		eventPost.onEvent();
	}
	
    @Override
    public void moveEntity(double par1, double par3, double par5)
	{
		super.moveEntity(par1, par3, par5);
		if(Resilience.getInstance().getValues().flightEnabled)
		{
			this.inWater = false;
		}
	}
	
    @Override
	public boolean handleWaterMovement() {
		if(Resilience.getInstance().getValues().flightEnabled) {
			return false;
		}
		return super.handleWaterMovement();
	}
	
    @Override
    public void setHealth(float health){
    	final EventHealthUpdate eventHealth = new EventHealthUpdate(health);
    	eventHealth.onEvent();
    	super.setHealth(health);
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
    	if(Resilience.getInstance().getValues().freecamEnabled){
    		return false;
    	}

        return super.isEntityInsideOpaqueBlock();
    }
    
    @Override
    protected boolean func_145771_j(double par1, double par3, double par5)
    {
    	if(Resilience.getInstance().getValues().freecamEnabled){
    		return false;
    	}

        return super.func_145771_j(par1, par3, par5);
    }
    
}
