package com.krispdev.resilience.gui.objects.screens;

import java.text.DecimalFormat;

import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.Utils;

public class InfoPanel extends DefaultPanel{
	
	public InfoPanel(String name, int x, int y, int x1, int y1, boolean visible){
		super(name, x, y, x1, y1, visible);
	}
	
	@Override
	public void draw(int x, int y){
		super.draw(x, y);
		if(isExtended()){
			Utils.drawRect(getX(), getY() + 17, getX1(), getY() + 94, 0x99040404);
			Resilience.getInstance().getStandardFont().drawString("X: \247b"+new DecimalFormat("#.##").format(Resilience.getInstance().getInvoker().getPosX()), getX()+4.5F, getY()+17+3, 0xffffffff);
			Resilience.getInstance().getStandardFont().drawString("Y: \247b"+new DecimalFormat("#.##").format(Resilience.getInstance().getInvoker().getPosY()), getX()+4.5F, getY()+17+3+12, 0xffffffff);
			Resilience.getInstance().getStandardFont().drawString("Z: \247b"+new DecimalFormat("#.##").format(Resilience.getInstance().getInvoker().getPosZ()), getX()+4.5F, getY()+17+3+12*2, 0xffffffff);
		
			int yaw = MathHelper.floor_double((double)(Resilience.getInstance().getInvoker().getRotationYaw() * 4.0F / 360.0F) + 0.5D) & 3;
			
			char firstC = Direction.directions[yaw].charAt(0);
			String firstLetter = String.valueOf(firstC).toUpperCase();
			String direction = firstLetter+Direction.directions[yaw].toLowerCase().replaceFirst(String.valueOf(firstC).toLowerCase(), "");
			
			Resilience.getInstance().getStandardFont().drawString("Direction: \247b" + direction, getX()+4.5F, getY()+17+3+12*3, 0xffffffff);
			
            int floorX = MathHelper.floor_double(Resilience.getInstance().getInvoker().getPosX());
            int floorY = MathHelper.floor_double(Resilience.getInstance().getInvoker().getPosY());
            int floorZ = MathHelper.floor_double(Resilience.getInstance().getInvoker().getPosZ());
            
            Chunk currentChunck = Resilience.getInstance().getWrapper().getWorld().getChunkFromBlockCoords(floorX, floorZ);
            BiomeGenBase biome = currentChunck.getBiomeGenForWorldCoords(floorX & 15, floorZ & 15, Resilience.getInstance().getWrapper().getWorld().getWorldChunkManager());
            
            String biomeName = biome.biomeName;
            
            Resilience.getInstance().getStandardFont().drawString("Biome: \247b"+biomeName, getX()+4.5F, getY()+17+3+12*4, 0xffffffff);
            Resilience.getInstance().getStandardFont().drawString("FPS: \247b"+Resilience.getInstance().getWrapper().getMinecraft().debugFPS, getX()+4.5F, getY()+17+3+12*5, 0xffffffff);
		}
	}
	
}
