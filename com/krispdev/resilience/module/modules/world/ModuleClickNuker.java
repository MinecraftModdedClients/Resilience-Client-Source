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

public class ModuleClickNuker extends DefaultModule{
	
	private int xPos = -1, yPos = -1, zPos = -1;
	private Block selected;
	
	public ModuleClickNuker(){
		super("Click Nuker", 0, NoCheatMode.INCOMPATIBLE);
		this.setDescription("Automatically destroys blocks around you when you click");
		this.setCategory(ModuleCategory.WORLD);
	}

	@Override
	public void onBlockClicked(EventBlockClick event){
		if(!invoker.isInCreativeMode()) return;
		for(int y = (int) Resilience.getInstance().getValues().nukerRadius.getValue(); y >= (int) -Resilience.getInstance().getValues().nukerRadius.getValue(); y--)
		{
			for(int z = (int) -Resilience.getInstance().getValues().nukerRadius.getValue(); z <= Resilience.getInstance().getValues().nukerRadius.getValue(); z++)
			{
				for(int x = (int) -Resilience.getInstance().getValues().nukerRadius.getValue(); x <= Resilience.getInstance().getValues().nukerRadius.getValue(); x++)
				{
					xPos = (int) Math.round(event.getX() + x);
					yPos = (int) ((int) Math.round(event.getY() + y));
					zPos = (int) Math.round(event.getZ() + z);

					Block block = invoker.getBlock(xPos, yPos, zPos);
					
					invoker.sendPacket(new C07PacketPlayerDigging(0, xPos, yPos, zPos, 1));
					invoker.sendPacket(new C07PacketPlayerDigging(2, xPos, yPos, zPos, 1));
				}
			}
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
