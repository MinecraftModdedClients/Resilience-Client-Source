package com.krispdev.resilience.hooks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.ResourceLocation;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventBlockClick;

public class HookPlayerControllerMP extends PlayerControllerMP{

	public HookPlayerControllerMP(Minecraft p_i45062_1_, NetHandlerPlayClient netHandlerPlayClient) {
		super(p_i45062_1_, netHandlerPlayClient);
	}
	
	@Override
	public void clickBlock(int par1, int par2, int par3, int par4){
		final EventBlockClick eventClick = new EventBlockClick(par1, par2, par3, par4, Resilience.getInstance().getInvoker().getBlock(par1, par2, par3));
		
		eventClick.onEvent();
		
		if(eventClick.isCancelled()){
			eventClick.setCancelled(false);
		}else{
			super.clickBlock(par1, par2, par3, par4);
		}
	}
	
	@Override
	public void onPlayerDamageBlock(int par1, int par2, int par3, int par4){
		boolean fastBreak = Resilience.getInstance().getValues().fastBreakEnabled;
		
		this.syncCurrentPlayItem();

        if (this.blockHitDelay > 0)
        {
            --this.blockHitDelay;
        }
        else if (this.currentGameType.isCreative())
        {
            this.blockHitDelay = fastBreak ? 0 : 5;
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, par1, par2, par3, par4));
            clickBlockCreative(this.mc, this, par1, par2, par3, par4);
        }
        else
        {
            if (this.sameToolAndBlock(par1, par2, par3))
            {
                Block var5 = this.mc.theWorld.getBlock(par1, par2, par3);

                if (var5.getMaterial() == Material.air)
                {
                    this.isHittingBlock = false;
                    return;
                }

                this.curBlockDamageMP += var5.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, par1, par2, par3);

                if (this.stepSoundTickCounter % 4.0F == 0.0F)
                {
                    this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var5.stepSound.func_150498_e()), (var5.stepSound.func_150497_c() + 1.0F) / 8.0F, var5.stepSound.func_150494_d() * 0.5F, (float)par1 + 0.5F, (float)par2 + 0.5F, (float)par3 + 0.5F));
                }

                ++this.stepSoundTickCounter;

                if (this.curBlockDamageMP >= (fastBreak ? Resilience.getInstance().getValues().fastBreakSpeed.getValue() : 1.0F))
                {
                    this.isHittingBlock = false;
                    this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(2, par1, par2, par3, par4));
                    this.onPlayerDestroyBlock(par1, par2, par3, par4);
                    this.curBlockDamageMP = 0.0F;
                    this.stepSoundTickCounter = 0.0F;
                    this.blockHitDelay = fastBreak ? 0 : 5;
                }

                this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, (int)(this.curBlockDamageMP * 10.0F) - 1);
            }
            else
            {
                this.clickBlock(par1, par2, par3, par4);
            }
        }
	}
	
}
