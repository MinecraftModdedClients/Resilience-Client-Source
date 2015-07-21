package net.minecraft.client.gui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiMerchant extends GuiContainer
{
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation field_147038_v = new ResourceLocation("textures/gui/container/villager.png");
    private IMerchant field_147037_w;
    private GuiMerchant.MerchantButton field_147043_x;
    private GuiMerchant.MerchantButton field_147042_y;
    private int field_147041_z;
    private String field_147040_A;
    private static final String __OBFID = "CL_00000762";

    public GuiMerchant(InventoryPlayer par1InventoryPlayer, IMerchant par2IMerchant, World par3World, String par4Str)
    {
        super(new ContainerMerchant(par1InventoryPlayer, par2IMerchant, par3World));
        this.field_147037_w = par2IMerchant;
        this.field_147040_A = par4Str != null && par4Str.length() >= 1 ? par4Str : I18n.format("entity.Villager.name", new Object[0]);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        int var1 = (this.width - this.field_146999_f) / 2;
        int var2 = (this.height - this.field_147000_g) / 2;
        this.buttonList.add(this.field_147043_x = new GuiMerchant.MerchantButton(1, var1 + 120 + 27, var2 + 24 - 1, true));
        this.buttonList.add(this.field_147042_y = new GuiMerchant.MerchantButton(2, var1 + 36 - 19, var2 + 24 - 1, false));
        this.field_147043_x.enabled = false;
        this.field_147042_y.enabled = false;
    }

    protected void func_146979_b(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString(this.field_147040_A, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(this.field_147040_A) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        MerchantRecipeList var1 = this.field_147037_w.getRecipes(this.mc.thePlayer);

        if (var1 != null)
        {
            this.field_147043_x.enabled = this.field_147041_z < var1.size() - 1;
            this.field_147042_y.enabled = this.field_147041_z > 0;
        }
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        boolean var2 = false;

        if (p_146284_1_ == this.field_147043_x)
        {
            ++this.field_147041_z;
            var2 = true;
        }
        else if (p_146284_1_ == this.field_147042_y)
        {
            --this.field_147041_z;
            var2 = true;
        }

        if (var2)
        {
            ((ContainerMerchant)this.field_147002_h).setCurrentRecipeIndex(this.field_147041_z);
            ByteBuf var3 = Unpooled.buffer();

            try
            {
                var3.writeInt(this.field_147041_z);
                this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", var3));
            }
            catch (Exception var8)
            {
                logger.error("Couldn\'t send trade info", var8);
            }
            finally
            {
                var3.release();
            }
        }
    }

    protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_147038_v);
        int var4 = (this.width - this.field_146999_f) / 2;
        int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        MerchantRecipeList var6 = this.field_147037_w.getRecipes(this.mc.thePlayer);

        if (var6 != null && !var6.isEmpty())
        {
            int var7 = this.field_147041_z;
            MerchantRecipe var8 = (MerchantRecipe)var6.get(var7);

            if (var8.isRecipeDisabled())
            {
                this.mc.getTextureManager().bindTexture(field_147038_v);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                this.drawTexturedModalRect(this.field_147003_i + 83, this.field_147009_r + 21, 212, 0, 28, 21);
                this.drawTexturedModalRect(this.field_147003_i + 83, this.field_147009_r + 51, 212, 0, 28, 21);
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);
        MerchantRecipeList var4 = this.field_147037_w.getRecipes(this.mc.thePlayer);

        if (var4 != null && !var4.isEmpty())
        {
            int var5 = (this.width - this.field_146999_f) / 2;
            int var6 = (this.height - this.field_147000_g) / 2;
            int var7 = this.field_147041_z;
            MerchantRecipe var8 = (MerchantRecipe)var4.get(var7);
            GL11.glPushMatrix();
            ItemStack var9 = var8.getItemToBuy();
            ItemStack var10 = var8.getSecondItemToBuy();
            ItemStack var11 = var8.getItemToSell();
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glEnable(GL11.GL_LIGHTING);
            itemRender.zLevel = 100.0F;
            itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var9, var5 + 36, var6 + 24);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var9, var5 + 36, var6 + 24);

            if (var10 != null)
            {
                itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var5 + 62, var6 + 24);
                itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var5 + 62, var6 + 24);
            }

            itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var11, var5 + 120, var6 + 24);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var11, var5 + 120, var6 + 24);
            itemRender.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);

            if (this.func_146978_c(36, 24, 16, 16, par1, par2))
            {
                this.func_146285_a(var9, par1, par2);
            }
            else if (var10 != null && this.func_146978_c(62, 24, 16, 16, par1, par2))
            {
                this.func_146285_a(var10, par1, par2);
            }
            else if (this.func_146978_c(120, 24, 16, 16, par1, par2))
            {
                this.func_146285_a(var11, par1, par2);
            }

            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
        }
    }

    public IMerchant func_147035_g()
    {
        return this.field_147037_w;
    }

    static class MerchantButton extends GuiButton
    {
        private final boolean field_146157_o;
        private static final String __OBFID = "CL_00000763";

        public MerchantButton(int par1, int par2, int par3, boolean par4)
        {
            super(par1, par2, par3, 12, 19, "");
            this.field_146157_o = par4;
        }

        public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
        {
            if (this.field_146125_m)
            {
                p_146112_1_.getTextureManager().bindTexture(GuiMerchant.field_147038_v);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                boolean var4 = p_146112_2_ >= this.field_146128_h && p_146112_3_ >= this.field_146129_i && p_146112_2_ < this.field_146128_h + this.field_146120_f && p_146112_3_ < this.field_146129_i + this.field_146121_g;
                int var5 = 0;
                int var6 = 176;

                if (!this.enabled)
                {
                    var6 += this.field_146120_f * 2;
                }
                else if (var4)
                {
                    var6 += this.field_146120_f;
                }

                if (!this.field_146157_o)
                {
                    var5 += this.field_146121_g;
                }

                this.drawTexturedModalRect(this.field_146128_h, this.field_146129_i, var6, var5, this.field_146120_f, this.field_146121_g);
            }
        }
    }
}
