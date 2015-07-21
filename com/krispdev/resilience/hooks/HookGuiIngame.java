package com.krispdev.resilience.hooks;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.gui.objects.screens.DefaultPanel;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;

public class HookGuiIngame extends GuiIngame{

	private int arrayListCount=0;
	private boolean go = true;
	public boolean display = false;
	private EntityPlayer toView;
	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	private Wrapper wrapper = Resilience.getInstance().getWrapper();
	private boolean once = true;
	private int ticks = 0;
	private String notifyString = "";
	private int notifyTicks = 0;
	
	public HookGuiIngame(Minecraft minecraft) {
		super(minecraft);
	}
	
	@Override
    public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
    {
		boolean wasExtended = false;
		ticks++;
		if(ticks == 5){
			wasExtended = Resilience.getInstance().getClickGui().values.isExtended();
			Resilience.getInstance().getClickGui().values.setExtended(true);
			Resilience.getInstance().getModuleManager().setModuleState("GUI", true);
		}
		if(ticks == 20){
			Resilience.getInstance().getClickGui().values.setExtended(wasExtended);
		}
		if(once && !Resilience.getInstance().isFirstTime()){
			once = false;
			Resilience.getInstance().getLogger().infoChat("Want to know how to chat in the IRC? Put the \"@\" sign before your message!");
		}
    	if(display){invoker.displayScreen(new GuiInventory(toView));display=false;};
    	if(go){
    		if(Resilience.getInstance().isFirstTime()){
    			Resilience.getInstance().getLogger().infoChat("Welcome to "+Resilience.getInstance().getName()+"!");
    			Resilience.getInstance().getLogger().infoChat("To open the GUI hit the \"Right Shift\" key, and to open the console hit the \"Minus\" key (\"-\").");
    			Resilience.getInstance().getLogger().infoChat("Remember to right click a button to get tons of great options :D");
    			Resilience.getInstance().getLogger().infoChat("To chat in the IRC, type \"@\" before the message.");
    			Resilience.getInstance().getLogger().infoChat("Enjoy! - Krisp");
    		}
    		go=false;
    	}
    	int prevArrayListCount = arrayListCount;
    	arrayListCount=0;
        ScaledResolution var5 = new ScaledResolution(wrapper.getGameSettings(), invoker.getDisplayWidth(), invoker.getDisplayHeight());
        int var6 = invoker.getScaledWidth(var5);
        int var7 = invoker.getScaledHeight(var5);
        FontRenderer var8 = wrapper.getFontRenderer();
        invoker.setupOverlayRendering();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        if(Resilience.getInstance().isEnabled()){
            for(DefaultPanel panel : ClickGui.windows){
            	if(panel.isPinned() && !(invoker.getCurrentScreen() instanceof ClickGui)){
            		GL11.glPushMatrix();
            		GL11.glDisable(GL11.GL_LIGHTING);
            		panel.draw(-1, -1);
            		GL11.glPopMatrix();
            	}
            }
            int width = 0;
            
            if(Resilience.getInstance().getValues().enabledModsEnabled){
            	for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
            		if(mod.isEnabled() && mod.getCategory() != ModuleCategory.GUI && mod.isVisible()){
            			int realWidth = (int)Resilience.getInstance().getModListFont().getWidth(mod.getDisplayName());
            			if(realWidth > width){
            				width = realWidth+8;
            			}
            		}
            	}
            }
            
            if(Resilience.getInstance().getValues().enabledModsEnabled){
        		GL11.glPushMatrix();
        		GL11.glDisable(GL11.GL_LIGHTING);
        		if(prevArrayListCount > 0){
    				drawRect(0,0,75,prevArrayListCount*12+4, 0x88000000);
        		}
    			GL11.glPopMatrix();
            }
            if(Resilience.getInstance().getValues().enabledModsEnabled){
            	for(DefaultModule mod : Resilience.getInstance().getModuleManager().moduleList){
            		if(mod.isEnabled() && mod.getCategory() != ModuleCategory.GUI && mod.isVisible()){
                		GL11.glPushMatrix();
                		GL11.glDisable(GL11.GL_LIGHTING);
            	        Resilience.getInstance().getModListFont().drawString(mod.getDisplayName(), 2, arrayListCount*12+2, 0xff9ab3ff);
                		GL11.glPopMatrix();
            	        arrayListCount++;
            		}
            	}
            }
            
            if(Resilience.getInstance().getValues().noFireEffectEnabled && invoker.isBurning()){
            	Resilience.getInstance().getWrapper().getFontRenderer().drawStringWithShadow("You're on fire", invoker.getDisplayWidth()/2-Resilience.getInstance().getWrapper().getFontRenderer().getStringWidth("You're on fire")-4, invoker.getDisplayHeight()/2-Resilience.getInstance().getWrapper().getFontRenderer().FONT_HEIGHT-4, 0xffff0000);
            }
            
            if(Resilience.getInstance().getValues().potionEffectsEnabled){
            	renderPotions();
            }
            
            if(notifyTicks >= 0){
            	notifyTicks--;
            	Resilience.getInstance().getStandardFont().drawCenteredString("\247b"+notifyString, invoker.getWidth()/2, 4, 0xffffffff);
            }
        }
       
        super.renderGameOverlay(par1, par2, par3, par4);
    }
	
    private void renderPotions()
    {
    	GL11.glPushMatrix();
        int var1 = Resilience.getInstance().getValues().enabledModsEnabled ? 76 : 1;
        int var2 = 0;
        boolean var3 = true;
        Collection var4 = wrapper.getPlayer().getActivePotionEffects();

        if (!var4.isEmpty())
        {
			ResourceLocation rL = new ResourceLocation("textures/gui/container/inventory.png");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            int var5 = 33;

            if (var4.size() > 5)
            {
                var5 = 132 / (var4.size() - 1);
            }

            for (Iterator var6 = wrapper.getPlayer().getActivePotionEffects().iterator(); var6.hasNext(); var2 += var5)
            {
                PotionEffect var7 = (PotionEffect)var6.next();
                Potion var8 = Potion.potionTypes[var7.getPotionID()];
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                wrapper.getMinecraft().getTextureManager().bindTexture(rL);
                //this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

                if (var8.hasStatusIcon())
                {
                    int var9 = var8.getStatusIconIndex();
                    this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }

                String var11 = I18n.format(var8.getName(), new Object[0]);

                if (var7.getAmplifier() == 1)
                {
                    var11 = var11 + " II";
                }
                else if (var7.getAmplifier() == 2)
                {
                    var11 = var11 + " III";
                }
                else if (var7.getAmplifier() == 3)
                {
                    var11 = var11 + " IV";
                }

                wrapper.getFontRenderer().drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6, 16777215);
                String var10 = Potion.getDurationString(var7);
                wrapper.getFontRenderer().drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6 + 10, 8355711);
            }
        }
        GL11.glPopMatrix();
    }
	
    public void displayInv(EntityPlayer e){
    	toView = e;
		display = true;
    }

	public void notify(String string, int ticks) {
		notifyString = string;
		this.notifyTicks = ticks;
	}
	
}
