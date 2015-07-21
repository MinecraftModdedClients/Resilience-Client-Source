package com.krispdev.resilience.hooks;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.gui.screens.GuiResilienceMain;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class HookGuiMainMenu extends GuiMainMenu {
	
    private final ResourceLocation backgroundImage = new ResourceLocation("assets/minecraft/textures/gui/title/resilience-background.jpg");
    private final ResourceLocation titleImage = new ResourceLocation("assets/minecraft/textures/gui/title/resilience-title.png");
	
    private static String version = "1.7.6";
	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	private static boolean hasAsked = false;
	private static boolean hasClickedRes = false;
	
	@Override
    public void initGui()
    {	
        new Thread(){
        	public void run(){
        		Utils.sendGetRequest("http://resilience.krispdev.com/updateStatus.php?ign="+Resilience.getInstance().getInvoker().getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&status=Not playing multiplayer");
        	}
        }.start();
		mc.gameSettings.guiScale = 2;
		if(Resilience.getInstance().isEnabled()){
			invoker.addButton(this, new ResilienceButton(1, 1, 1, this.invoker.getWidth()/3+1, 20,I18n.format("menu.singleplayer", new Object[0])));
	        invoker.addButton(this, new ResilienceButton(69, this.invoker.getWidth()/3+3, 1, this.invoker.getWidth()/3+3, 41, Resilience.getInstance().getName()));
	        invoker.addButton(this, new ResilienceButton(2, this.invoker.getWidth()/3*2+7, 1, this.invoker.getWidth()/3-7, 20, I18n.format("menu.multiplayer", new Object[0])));
	        invoker.addButton(this, new ResilienceButton(199, this.invoker.getWidth()/2-50, this.invoker.getHeight()-93, 100, 20, "Change to 1.7.9"));
	        invoker.addButton(this, new ResilienceButton(0, 1, 22, invoker.getWidth()/3+1, 20, I18n.format("menu.options", new Object[0])));
	        invoker.addButton(this, new ResilienceButton(4, invoker.getWidth() / 3*2+7, 22, invoker.getWidth()/3-7, 20, I18n.format("menu.quit", new Object[0])));
	        invoker.addButton(this, new ResilienceButton(70, invoker.getWidth()/2-50, invoker.getHeight()-24.5F, 100, 20, "Suggest"));
		}else{
			super.initGui();
		}
	}
	
	@Override
    protected void addSingleplayerMultiplayerButtons(int par1, int par2){super.addSingleplayerMultiplayerButtons(par1, par2);}
	
	@Override
    protected void actionPerformed(GuiButton btn)
    {
    	if(invoker.getId(btn) == 69){
    		this.mc.displayGuiScreen(GuiResilienceMain.screen);
    		hasClickedRes = true;
    	}else if(invoker.getId(btn) == 70){
    		Sys.openURL("http://resilience.krispdev.com/suggest");
    	}else if(invoker.getId(btn) == 199){
			btn.displayString = "Change to "+version;
    		if(version.equals("1.7.9")){
    			version = "1.7.6";
    			Resilience.getInstance().getValues().version = 4;
    		}else if(version.equals("1.7.6")){
    			version = "1.7.9";
    			Resilience.getInstance().getValues().version = 5;
    		}
    	}//else if(invoker.getId(btn) == 124124){
    		//this.func_140005_i();
    	//}
    	super.actionPerformed(btn);
    }
	
	@Override
    public void drawScreen(int par1, int par2, float par3)
    {
		if(Resilience.getInstance().isEnabled()){
	        this.drawGradientRect(0, 0, this.invoker.getWidth(), this.invoker.getHeight(), 0xffe4e4e4, 0xff3333ff);
	        try{
	        	Image img = new Image(backgroundImage.getResourcePath());
	        	img.draw(0, 0, invoker.getWidth(), invoker.getHeight());
	        }catch(SlickException e){
	        	e.printStackTrace();
	        }
	        try{
	        	Image img = new Image(titleImage.getResourcePath());
	        	GL11.glEnable(GL11.GL_BLEND);
	        	img.draw(this.invoker.getWidth()/2-90, this.invoker.getHeight()/2-60, 200, 80);
	        }catch(SlickException e){
	        	e.printStackTrace();
	        }
	        Utils.drawBetterRect(this.invoker.getWidth()/2-150, this.invoker.getHeight()-68, this.invoker.getWidth()/2+150, this.invoker.getHeight()-29, 0x88ffffff, 0x66000000);
	        Resilience.getInstance().getStandardFont().drawCenteredString("Version: \2477"+version, this.invoker.getWidth()/2, this.invoker.getHeight()-65, 0xffffffff);
	        Resilience.getInstance().getStandardFont().drawCenteredString("Resilience Online Status: "+(Resilience.getInstance().getOnlineStatus().equals("Servers Online")?"\247b":"\247c")+Resilience.getInstance().getOnlineStatus(), this.invoker.getWidth()/2, this.invoker.getHeight()-54.5F, 0xffffffff);
	        Resilience.getInstance().getStandardFont().drawCenteredString("Account status: "+(Donator.isDonator(this.mc.session.getUsername(), 5) ? "\247bUpgraded" : "\2478Not Upgraded"), this.invoker.getWidth()/2, this.invoker.getHeight()-44, 0xffffffff);
	        String title = "\2473"+Resilience.getInstance().getName()+"\247f v"+Resilience.getInstance().getVersion();
	        Resilience.getInstance().getStandardFont().drawStringWithShadow(title, 4, this.invoker.getHeight()-14, 0xffffffff);
	        Resilience.getInstance().getStandardFont().drawStringWithShadow("by Krisp", this.invoker.getWidth()-4-Resilience.getInstance().getStandardFont().getWidth("by Krisp"), this.invoker.getHeight()-14, 0xff3333ff);
	        //if (this.field_92025_p != null && this.field_92025_p.length() > 0)
	        //{
	            //drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
	            //this.drawString(this.fontRendererObj, this.field_92025_p, this.field_92022_t, this.field_92021_u, -1);
	            //this.drawString(this.fontRendererObj, this.field_146972_A, (this.invoker.getWidth() - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).field_146129_i - 12, -1);
	        //}
	        
	        int var4;

	        for (var4 = 0; var4 < this.buttonList.size(); ++var4)
	        {
	            ((GuiButton)this.buttonList.get(var4)).drawButton(this.mc, par1, par2);
	        }

	        for (var4 = 0; var4 < this.labelList.size(); ++var4)
	        {
	            ((GuiLabel)this.labelList.get(var4)).func_146159_a(this.mc, par1, par2);
	        }
	        
	        if(!hasClickedRes && Resilience.getInstance().isFirstTimeOnline()){
	        	Utils.drawLine(invoker.getWidth()/2, 50, invoker.getWidth()/2+130, 60, 2, 0xff0000ff);
	        	Resilience.getInstance().getStandardFont().drawString("To use \247bResilience Online\247f, click on Resilience!", invoker.getWidth()/2+34, 62, 0xffffffff);
	        }else{
	        	Resilience.getInstance().setFirstTimeOnline(false);
	        }
		}else{
			super.drawScreen(par1, par2, par3);
		}
    }
	
}
