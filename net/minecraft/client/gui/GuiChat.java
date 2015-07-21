package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiChat extends GuiScreen
{
    private static final Logger logger = LogManager.getLogger();
    private String field_146410_g = "";
    private int field_146416_h = -1;
    private boolean field_146417_i;
    private boolean field_146414_r;
    private int field_146413_s;
    private List field_146412_t = new ArrayList();
    private URI field_146411_u;
    protected GuiTextField field_146415_a;
    private String field_146409_v = "";
    private static final String __OBFID = "CL_00000682";

    public GuiChat() {}

    public GuiChat(String par1Str)
    {
        this.field_146409_v = par1Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.field_146416_h = this.mc.ingameGUI.getChatGUI().func_146238_c().size();
        this.field_146415_a = new GuiTextField(this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
        this.field_146415_a.func_146203_f(100);
        this.field_146415_a.func_146185_a(false);
        this.field_146415_a.setFocused(true);
        this.field_146415_a.setText(this.field_146409_v);
        this.field_146415_a.func_146205_d(false);
    }

    /**
     * "Called when the screen is unloaded. Used to disable keyboard repeat events."
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_146415_a.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        this.field_146414_r = false;

        if (par2 == 15)
        {
            this.func_146404_p_();
        }
        else
        {
            this.field_146417_i = false;
        }

        if (par2 == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (par2 != 28 && par2 != 156)
        {
            if (par2 == 200)
            {
                this.func_146402_a(-1);
            }
            else if (par2 == 208)
            {
                this.func_146402_a(1);
            }
            else if (par2 == 201)
            {
                this.mc.ingameGUI.getChatGUI().func_146229_b(this.mc.ingameGUI.getChatGUI().func_146232_i() - 1);
            }
            else if (par2 == 209)
            {
                this.mc.ingameGUI.getChatGUI().func_146229_b(-this.mc.ingameGUI.getChatGUI().func_146232_i() + 1);
            }
            else
            {
                this.field_146415_a.textboxKeyTyped(par1, par2);
            }
        }
        else
        {
            String var3 = this.field_146415_a.getText().trim();

            if (var3.length() > 0)
            {
                this.func_146403_a(var3);
            }

            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    public void func_146403_a(String p_146403_1_)
    {
        this.mc.ingameGUI.getChatGUI().func_146239_a(p_146403_1_);
        this.mc.thePlayer.sendChatMessage(p_146403_1_);
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();

        if (var1 != 0)
        {
            if (var1 > 1)
            {
                var1 = 1;
            }

            if (var1 < -1)
            {
                var1 = -1;
            }

            if (!isShiftKeyDown())
            {
                var1 *= 7;
            }

            this.mc.ingameGUI.getChatGUI().func_146229_b(var1);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0 && this.mc.gameSettings.chatLinks)
        {
            IChatComponent var4 = this.mc.ingameGUI.getChatGUI().func_146236_a(Mouse.getX(), Mouse.getY());

            if (var4 != null)
            {
                ClickEvent var5 = var4.getChatStyle().getChatClickEvent();

                if (var5 != null)
                {
                    if (isShiftKeyDown())
                    {
                        this.field_146415_a.func_146191_b(var4.getUnformattedTextForChat());
                    }
                    else
                    {
                        URI var6;

                        if (var5.getAction() == ClickEvent.Action.OPEN_URL)
                        {
                            try
                            {
                                var6 = new URI(var5.getValue());

                                if (this.mc.gameSettings.chatLinksPrompt)
                                {
                                    this.field_146411_u = var6;
                                    this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, var5.getValue(), 0, false));
                                }
                                else
                                {
                                    this.func_146407_a(var6);
                                }
                            }
                            catch (URISyntaxException var7)
                            {
                                logger.error("Can\'t open url for " + var5, var7);
                            }
                        }
                        else if (var5.getAction() == ClickEvent.Action.OPEN_FILE)
                        {
                            var6 = (new File(var5.getValue())).toURI();
                            this.func_146407_a(var6);
                        }
                        else if (var5.getAction() == ClickEvent.Action.SUGGEST_COMMAND)
                        {
                            this.field_146415_a.setText(var5.getValue());
                        }
                        else if (var5.getAction() == ClickEvent.Action.RUN_COMMAND)
                        {
                            this.func_146403_a(var5.getValue());
                        }
                        else
                        {
                            logger.error("Don\'t know how to handle " + var5);
                        }
                    }

                    return;
                }
            }
        }

        this.field_146415_a.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par2 == 0)
        {
            if (par1)
            {
                this.func_146407_a(this.field_146411_u);
            }

            this.field_146411_u = null;
            this.mc.displayGuiScreen(this);
        }
    }

    private void func_146407_a(URI p_146407_1_)
    {
        try
        {
            Class var2 = Class.forName("java.awt.Desktop");
            Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            var2.getMethod("browse", new Class[] {URI.class}).invoke(var3, new Object[] {p_146407_1_});
        }
        catch (Throwable var4)
        {
            logger.error("Couldn\'t open link", var4);
        }
    }

    public void func_146404_p_()
    {
        String var3;

        if (this.field_146417_i)
        {
            this.field_146415_a.func_146175_b(this.field_146415_a.func_146197_a(-1, this.field_146415_a.func_146198_h(), false) - this.field_146415_a.func_146198_h());

            if (this.field_146413_s >= this.field_146412_t.size())
            {
                this.field_146413_s = 0;
            }
        }
        else
        {
            int var1 = this.field_146415_a.func_146197_a(-1, this.field_146415_a.func_146198_h(), false);
            this.field_146412_t.clear();
            this.field_146413_s = 0;
            String var2 = this.field_146415_a.getText().substring(var1).toLowerCase();
            var3 = this.field_146415_a.getText().substring(0, this.field_146415_a.func_146198_h());
            this.func_146405_a(var3, var2);

            if (this.field_146412_t.isEmpty())
            {
                return;
            }

            this.field_146417_i = true;
            this.field_146415_a.func_146175_b(var1 - this.field_146415_a.func_146198_h());
        }

        if (this.field_146412_t.size() > 1)
        {
            StringBuilder var4 = new StringBuilder();

            for (Iterator var5 = this.field_146412_t.iterator(); var5.hasNext(); var4.append(var3))
            {
                var3 = (String)var5.next();

                if (var4.length() > 0)
                {
                    var4.append(", ");
                }
            }

            this.mc.ingameGUI.getChatGUI().func_146234_a(new ChatComponentText(var4.toString()), 1);
        }

        this.field_146415_a.func_146191_b((String)this.field_146412_t.get(this.field_146413_s++));
    }

    private void func_146405_a(String p_146405_1_, String p_146405_2_)
    {
        if (p_146405_1_.length() >= 1)
        {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_));
            this.field_146414_r = true;
        }
    }

    public void func_146402_a(int p_146402_1_)
    {
        int var2 = this.field_146416_h + p_146402_1_;
        int var3 = this.mc.ingameGUI.getChatGUI().func_146238_c().size();

        if (var2 < 0)
        {
            var2 = 0;
        }

        if (var2 > var3)
        {
            var2 = var3;
        }

        if (var2 != this.field_146416_h)
        {
            if (var2 == var3)
            {
                this.field_146416_h = var3;
                this.field_146415_a.setText(this.field_146410_g);
            }
            else
            {
                if (this.field_146416_h == var3)
                {
                    this.field_146410_g = this.field_146415_a.getText();
                }

                this.field_146415_a.setText((String)this.mc.ingameGUI.getChatGUI().func_146238_c().get(var2));
                this.field_146416_h = var2;
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.field_146415_a.drawTextBox();
        IChatComponent var4 = this.mc.ingameGUI.getChatGUI().func_146236_a(Mouse.getX(), Mouse.getY());

        if (var4 != null && var4.getChatStyle().getChatHoverEvent() != null)
        {
            HoverEvent var5 = var4.getChatStyle().getChatHoverEvent();

            if (var5.getAction() == HoverEvent.Action.SHOW_ITEM)
            {
                ItemStack var6 = null;

                try
                {
                    NBTBase var7 = JsonToNBT.func_150315_a(var5.getValue().getUnformattedText());

                    if (var7 != null && var7 instanceof NBTTagCompound)
                    {
                        var6 = ItemStack.loadItemStackFromNBT((NBTTagCompound)var7);
                    }
                }
                catch (NBTException var11)
                {
                    ;
                }

                if (var6 != null)
                {
                    this.func_146285_a(var6, par1, par2);
                }
                else
                {
                    this.func_146279_a(EnumChatFormatting.RED + "Invalid Item!", par1, par2);
                }
            }
            else if (var5.getAction() == HoverEvent.Action.SHOW_TEXT)
            {
                this.func_146279_a(var5.getValue().getFormattedText(), par1, par2);
            }
            else if (var5.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT)
            {
                StatBase var13 = StatList.func_151177_a(var5.getValue().getUnformattedText());

                if (var13 != null)
                {
                    IChatComponent var12 = var13.func_150951_e();
                    ChatComponentTranslation var8 = new ChatComponentTranslation("stats.tooltip.type." + (var13.isAchievement() ? "achievement" : "statistic"), new Object[0]);
                    var8.getChatStyle().setItalic(Boolean.valueOf(true));
                    String var9 = var13 instanceof Achievement ? ((Achievement)var13).getDescription() : null;
                    ArrayList var10 = Lists.newArrayList(new String[] {var12.getFormattedText(), var8.getFormattedText()});

                    if (var9 != null)
                    {
                        var10.addAll(this.fontRendererObj.listFormattedStringToWidth(var9, 150));
                    }

                    this.func_146283_a(var10, par1, par2);
                }
                else
                {
                    this.func_146279_a(EnumChatFormatting.RED + "Invalid statistic/achievement!", par1, par2);
                }
            }

            GL11.glDisable(GL11.GL_LIGHTING);
        }

        super.drawScreen(par1, par2, par3);
    }

    public void func_146406_a(String[] p_146406_1_)
    {
        if (this.field_146414_r)
        {
            this.field_146417_i = false;
            this.field_146412_t.clear();
            String[] var2 = p_146406_1_;
            int var3 = p_146406_1_.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                String var5 = var2[var4];

                if (var5.length() > 0)
                {
                    this.field_146412_t.add(var5);
                }
            }

            if (this.field_146412_t.size() > 0)
            {
                this.field_146417_i = true;
                this.func_146404_p_();
            }
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
