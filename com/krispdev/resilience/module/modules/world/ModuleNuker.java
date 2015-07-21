package com.krispdev.resilience.module.modules.world;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventBlockClick;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.utilities.RenderUtils;

public class ModuleNuker extends DefaultModule{
	
	private boolean shouldSelect;
	private int xPos = -1, yPos = -1, zPos = -1;
	private Block selected;
	private ArrayList<Integer[]> positions = new ArrayList<Integer[]>();
	
	public ModuleNuker(){
		super("Nuker", Keyboard.KEY_N, NoCheatMode.INCOMPATIBLE);
		this.setDescription("Automatically destroys blocks around you");
		this.setCategory(ModuleCategory.WORLD);
	}

	@Override
	public void onBlockClicked(EventBlockClick event){
		if(invoker.isInCreativeMode()) return;
		if(selected == null){
			Resilience.getInstance().getLogger().infoChat("Now nuking "+event.getBlock().getLocalizedName());
		}
		Block previous = selected;
		selected = event.getBlock();
		if(previous != null && selected != null && !previous.getLocalizedName().equalsIgnoreCase(selected.getLocalizedName())){
			Resilience.getInstance().getLogger().infoChat("Now nuking "+selected.getLocalizedName());
		}
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		shouldSelect = !invoker.isInCreativeMode();
		positions.clear();
		
		for(int y = (int) Resilience.getInstance().getValues().nukerRadius.getValue(); y >= (int) -Resilience.getInstance().getValues().nukerRadius.getValue(); y--)
		{
			for(int z = (int) -Resilience.getInstance().getValues().nukerRadius.getValue(); z <= Resilience.getInstance().getValues().nukerRadius.getValue(); z++)
			{
				for(int x = (int) -Resilience.getInstance().getValues().nukerRadius.getValue(); x <= Resilience.getInstance().getValues().nukerRadius.getValue(); x++)
				{
					xPos = (int) Math.round(invoker.getPosX() + x);
					yPos = (int) ((int) Math.round(invoker.getPosY() + y)-invoker.getEntityHeight(Resilience.getInstance().getWrapper().getPlayer())/2);
					zPos = (int) Math.round(invoker.getPosZ() + z);

					Block block = invoker.getBlock(xPos, yPos, zPos);
					
					if(shouldSelect){
						if(block != null && selected != null && invoker.getIdFromBlock(selected) == invoker.getIdFromBlock(block)){
							positions.add(new Integer[]{xPos, yPos, zPos});
							invoker.sendPacket(new C07PacketPlayerDigging(0, xPos, yPos, zPos, 1));
							invoker.sendPacket(new C07PacketPlayerDigging(2, xPos, yPos, zPos, 1));
						}
					}else{
						if(invoker.getIdFromBlock(block) != 0)
						{
							positions.add(new Integer[]{xPos, yPos, zPos});
							invoker.sendPacket(new C07PacketPlayerDigging(0, xPos, yPos, zPos, 1));
							invoker.sendPacket(new C07PacketPlayerDigging(2, xPos, yPos, zPos, 1));
						}	
					}
				}
			}
		}
	}
	
	@Override
	public void onRender(EventOnRender event){
		for(Integer[] pos : positions){
			RenderUtils.drawESP(false, pos[0] - invoker.getRenderPosX(), pos[1] - invoker.getRenderPosY(), pos[2] - invoker.getRenderPosZ(), pos[0]+1 - invoker.getRenderPosX(), pos[1]+1 - invoker.getRenderPosY(), pos[2]+1 - invoker.getRenderPosZ(), 0.5, 0.5, 1, 0.15, 0.5, 0.5, 1, 0.15);
		}
	}
	
	@Override
	public void onEnable() {
		Resilience.getInstance().getEventManager().register(this);
	}

	@Override
	public void onDisable() {
		Resilience.getInstance().getEventManager().unregister(this);
	}
	
}
