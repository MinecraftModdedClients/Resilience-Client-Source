package com.krispdev.resilience.event.events.player;

import net.minecraft.block.Block;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.Cancellable;
import com.krispdev.resilience.event.events.Event;
import com.krispdev.resilience.event.listeners.Listener;
import com.krispdev.resilience.event.listeners.ModListener;

public class EventBlockClick extends Cancellable implements Event{
	
	private Block block;
	
	private int x, y, z, side;
	
	public EventBlockClick(int x, int y, int z, int side, Block block){
		this.block = block;
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.side = side;
	}

	@Override
	public void onEvent() {
		if(!Resilience.getInstance().isEnabled()) return;
		try{
			for(Listener l : Resilience.getInstance().getEventManager().eventListeners){
				if(l instanceof ModListener){
					ModListener mod = (ModListener) l;
					mod.onBlockClicked(this);
				}
			}
		}catch(Exception e){}
	}
	
	public Block getBlock(){
		return block;
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
	
	public int getSide(){
		return side;
	}
	
}