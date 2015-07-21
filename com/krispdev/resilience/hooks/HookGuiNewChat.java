package com.krispdev.resilience.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.relations.Friend;
import com.krispdev.resilience.utilities.Utils;

public class HookGuiNewChat extends GuiNewChat{

	public HookGuiNewChat(Minecraft par1Minecraft) {
		super(par1Minecraft);
	}

	@Override
	public void func_146230_a(int p_146230_1_)
	    {
	        if (this.field_146247_f.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN)
	        {
	            int var2 = this.func_146232_i();
	            boolean var3 = false;
	            int var4 = 0;
	            int var5 = this.field_146253_i.size();
	            float var6 = this.field_146247_f.gameSettings.chatOpacity * 0.9F + 0.1F;

	            if (var5 > 0)
	            {
	                if (this.func_146241_e())
	                {
	                    var3 = true;
	                }

	                float var7 = this.func_146244_h();
	                int var8 = MathHelper.ceiling_float_int((float)this.func_146228_f() / var7);
	                GL11.glPushMatrix();
	                GL11.glTranslatef(2.0F, 20.0F, 0.0F);
	                GL11.glScalef(var7, var7, 1.0F);
	                int var9;
	                int var11;
	                int var14;

	                for (var9 = 0; var9 + this.field_146250_j < this.field_146253_i.size() && var9 < var2; ++var9)
	                {
	                    ChatLine var10 = (ChatLine)this.field_146253_i.get(var9 + this.field_146250_j);

	                    if (var10 != null)
	                    {
	                        var11 = p_146230_1_ - var10.getUpdatedCounter();

	                        if (var11 < 200 || var3)
	                        {
	                            double var12 = (double)var11 / 200.0D;
	                            var12 = 1.0D - var12;
	                            var12 *= 10.0D;

	                            if (var12 < 0.0D)
	                            {
	                                var12 = 0.0D;
	                            }

	                            if (var12 > 1.0D)
	                            {
	                                var12 = 1.0D;
	                            }

	                            var12 *= var12;
	                            var14 = (int)(255.0D * var12);

	                            if (var3)
	                            {
	                                var14 = 255;
	                            }

	                            var14 = (int)((float)var14 * var6);
	                            ++var4;

	                            if (var14 > 3)
	                            {
	                                byte var15 = 0;
	                                int var16 = -var9 * (Resilience.getInstance().getValues().niceChatEnabled ? 12 : 9);
	                                if(Resilience.getInstance().getValues().niceChatEnabled){
	                                	Utils.drawRect(var15, var16 - 12, var15 + var8 + 4, var16, var14/2 << 24);
	                                }else{
	                                	drawRect(var15, var16 - 9, var15 + var8 + 4, var16, var14 / 2 << 24);
	                                }
	                                String var17 = var10.func_151461_a().getFormattedText();
	                                var17 = var17.replaceAll("§r", "");
	                                for(Friend friend : Friend.friendList){
	                                	if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(var17, friend.getName())){
	                                		var17 = var17.replaceAll("(?i)"+friend.getName(), friend.getAlias());
	                                	}
	                                }
	                                
	                                if(Resilience.getInstance().getValues().nameProtectEnabled){
	                                	var17 = var17.replaceAll("§k", "");
	                                	var17 = var17.replaceAll("§l", "");
	                                	var17 = var17.replaceAll("§m", "");
	                                	var17 = var17.replaceAll("§n", "");
	                                	var17 = var17.replaceAll("§o", "");
	                                }
	                                
	                                if(Resilience.getInstance().getValues().nameProtectEnabled && org.apache.commons.lang3.StringUtils.containsIgnoreCase(var17, Resilience.getInstance().getInvoker().getSessionUsername())){
	                                	 var17 = var17.replaceAll("(?i)"+Resilience.getInstance().getInvoker().getSessionUsername(), Resilience.getInstance().getValues().nameProtectAlias.getValue());	
	                                }
	                                if(Resilience.getInstance().getValues().niceChatEnabled){
	                                	Resilience.getInstance().getChatFont().drawStringWithShadow(var17.replaceAll("§", "\247"), var15+2, var16 - 12, 16777215 + (var14 << 24));
	                                }else{
	                                	this.field_146247_f.fontRenderer.drawStringWithShadow(var17, var15, var16 - 8, 16777215 + (var14 << 24));
	                                }
	                                GL11.glDisable(GL11.GL_ALPHA_TEST);
	                            }
	                        }
	                    }
	                }

	                if (var3)
	                {
	                    var9 = this.field_146247_f.fontRenderer.FONT_HEIGHT;
	                    GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
	                    int var18 = var5 * var9 + var5;
	                    var11 = var4 * var9 + var4;
	                    int var20 = this.field_146250_j * var11 / var5;
	                    int var13 = var11 * var11 / var18;

	                    if (var18 != var11)
	                    {
	                        var14 = var20 > 0 ? 170 : 96;
	                        int var19 = this.field_146251_k ? 13382451 : 3355562;
	                        drawRect(0, -var20, 2, -var20 - var13, var19 + (var14 << 24));
	                        drawRect(2, -var20, 1, -var20 - var13, 13421772 + (var14 << 24));
	                    }
	                }

	                GL11.glPopMatrix();
	            }
	        }
	    }
	
}
