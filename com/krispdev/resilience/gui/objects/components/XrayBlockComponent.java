package com.krispdev.resilience.gui.objects.components;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.utilities.Utils;

public class XrayBlockComponent extends Component{

	private int blockId;
	private boolean enabled;
	
	public XrayBlockComponent(float x, float y, float x1, float y1, int blockId, boolean enabled) {
		super(x, y, x1, y1);
		this.blockId = blockId;
		this.enabled = enabled;
	}
	
	@Override
	public void draw(int x, int y){
		boolean overComponent = x >= this.getX() && x <= this.getX1() && y >= this.getY() && y <= this.getY1();
		boolean mouseDownOverComponent = overComponent && Mouse.isButtonDown(0); 
		if(enabled){
			Utils.drawRect(getX(), getY(), getX1(), getY1(), mouseDownOverComponent ? 0x551d669e : overComponent ? 0x55449bdd : 0x552582C9);
		}else{
			Utils.drawRect(getX(), getY(), getX1(), getY1(), mouseDownOverComponent ? 0x550065d1 : overComponent ? 0x55676767 : 0x55555555);
		}
		ItemStack is = new ItemStack(Block.getBlockById(blockId));
		GL11.glPushMatrix();
		GL11.glDisable(3042);
		GL11.glEnable(32826);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDepthMask(true);
		GL11.glDisable(2929);
		try{
			Resilience.getInstance().getInvoker().renderItemIntoGUI(is, (int)getX()+3, (int)getY()+3);
			Resilience.getInstance().getInvoker().renderItemOverlayIntoGUI(is, (int)getX()+3, (int)getY()+3);
		}catch(Exception e){}
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}
	
	@Override
	public void onComponentClicked(int x, int y, int btn){
		if(btn == 0){
			enabled =! enabled;
			
			if(!enabled){
				Resilience.getInstance().getXrayUtils().removeBlock(blockId);
				if(Resilience.getInstance().getXrayUtils().xrayEnabled){
					Resilience.getInstance().getInvoker().loadRenderers();
				}
				Resilience.getInstance().getFileManager().saveXrayBlocks();
			}else{
				Resilience.getInstance().getXrayUtils().addBlock(blockId);
				if(Resilience.getInstance().getXrayUtils().xrayEnabled){
					Resilience.getInstance().getInvoker().loadRenderers();
				}
				Resilience.getInstance().getFileManager().saveXrayBlocks();
			}
		}
	}
	
}
