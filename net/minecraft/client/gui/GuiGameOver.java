package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.opengl.GL11;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.objects.Waypoint;
import com.krispdev.resilience.hooks.HookGuiMainMenu;

public class GuiGameOver extends GuiScreen
{
    private int field_146347_a;
    private boolean field_146346_f = false;
    private static final String __OBFID = "CL_00000690";

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {	
    	ArrayList<Waypoint> badWaypoints = new ArrayList<Waypoint>();
    	
		for(Waypoint w : Waypoint.waypointsList){
			if(w.getName().equals("Death Waypoint")){
				badWaypoints.add(w);
			}
		}
		
		for(Waypoint w : badWaypoints){
			Waypoint.waypointsList.remove(Waypoint.waypointsList.indexOf(w));
		}
		
		badWaypoints.clear();
    	
    	Resilience.getInstance().getValues().deathWaypoint = new Waypoint("Death Waypoint", (int)Resilience.getInstance().getInvoker().getPosX(), (int)Resilience.getInstance().getInvoker().getPosY(), (int)Resilience.getInstance().getInvoker().getPosZ(), 1, 0 , 0);
		Waypoint.waypointsList.add(Resilience.getInstance().getValues().deathWaypoint);
    	Resilience.getInstance().getLogger().info("Added a death waypoint: "+Resilience.getInstance().getValues().deathWaypoint.toString());
    	Resilience.getInstance().getFileManager().saveWaypoints();
    	
        this.buttonList.clear();

        if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
        {
            if (this.mc.isIntegratedServerRunning())
            {
                this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
            }
            else
            {
                this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
            }
        }
        else
        {
            this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));

            if (this.mc.getSession() == null)
            {
                ((GuiButton)this.buttonList.get(1)).enabled = false;
            }
        }

        GuiButton var2;

        for (Iterator var1 = this.buttonList.iterator(); var1.hasNext(); var2.enabled = false)
        {
            var2 = (GuiButton)var1.next();
        }
        
    	if(Resilience.getInstance().getValues().autoRespawnEnabled){
            this.mc.thePlayer.respawnPlayer();
    	}
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        switch (p_146284_1_.id)
        {
            case 0:
                this.mc.thePlayer.respawnPlayer();
                this.mc.displayGuiScreen((GuiScreen)null);
                break;

            case 1:
                GuiYesNo var2 = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
                this.mc.displayGuiScreen(var2);
                var2.func_146350_a(20);
        }
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par1)
        {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld((WorldClient)null);
            this.mc.displayGuiScreen(new HookGuiMainMenu());
        }
        else
        {
            this.mc.thePlayer.respawnPlayer();
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        boolean var4 = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
        String var5 = var4 ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]);
        this.drawCenteredString(this.fontRendererObj, var5, this.width / 2 / 2, 30, 16777215);
        GL11.glPopMatrix();

        if (var4)
        {
            this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), this.width / 2, 144, 16777215);
        }

        this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), this.width / 2, 100, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146347_a;
        GuiButton var2;

        if (this.field_146347_a == 20)
        {
            for (Iterator var1 = this.buttonList.iterator(); var1.hasNext(); var2.enabled = true)
            {
                var2 = (GuiButton)var1.next();
            }
        }
    }
}
