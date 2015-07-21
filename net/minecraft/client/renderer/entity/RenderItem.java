package net.minecraft.client.renderer.entity;

import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderItem extends Render
{
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private RenderBlocks field_147913_i = new RenderBlocks();

    /** The RNG used in RenderItem (for bobbing itemstacks on the ground) */
    private Random random = new Random();
    public boolean renderWithColor = true;

    /** Defines the zLevel of rendering of item on GUI. */
    public float zLevel;
    public static boolean renderInFrame;
    private static final String __OBFID = "CL_00001003";

    public RenderItem()
    {
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityItem par1EntityItem, double par2, double par4, double par6, float par8, float par9)
    {
        ItemStack var10 = par1EntityItem.getEntityItem();

        if (var10.getItem() != null)
        {
            this.bindEntityTexture(par1EntityItem);
            this.random.setSeed(187L);
            GL11.glPushMatrix();
            float var11 = MathHelper.sin(((float)par1EntityItem.age + par9) / 10.0F + par1EntityItem.hoverStart) * 0.1F + 0.1F;
            float var12 = (((float)par1EntityItem.age + par9) / 20.0F + par1EntityItem.hoverStart) * (180F / (float)Math.PI);
            byte var13 = 1;

            if (par1EntityItem.getEntityItem().stackSize > 1)
            {
                var13 = 2;
            }

            if (par1EntityItem.getEntityItem().stackSize > 5)
            {
                var13 = 3;
            }

            if (par1EntityItem.getEntityItem().stackSize > 20)
            {
                var13 = 4;
            }

            if (par1EntityItem.getEntityItem().stackSize > 40)
            {
                var13 = 5;
            }

            GL11.glTranslatef((float)par2, (float)par4 + var11, (float)par6);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            float var19;
            float var18;
            int var24;

            if (var10.getItemSpriteNumber() == 0 && var10.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var10.getItem()).getRenderType()))
            {
                Block var21 = Block.getBlockFromItem(var10.getItem());
                GL11.glRotatef(var12, 0.0F, 1.0F, 0.0F);

                if (renderInFrame)
                {
                    GL11.glScalef(1.25F, 1.25F, 1.25F);
                    GL11.glTranslatef(0.0F, 0.05F, 0.0F);
                    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                float var25 = 0.25F;
                var24 = var21.getRenderType();

                if (var24 == 1 || var24 == 19 || var24 == 12 || var24 == 2)
                {
                    var25 = 0.5F;
                }

                if (var21.getRenderBlockPass() > 0)
                {
                    GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                }

                GL11.glScalef(var25, var25, var25);

                for (int var26 = 0; var26 < var13; ++var26)
                {
                    GL11.glPushMatrix();

                    if (var26 > 0)
                    {
                        var18 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
                        var19 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
                        float var20 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
                        GL11.glTranslatef(var18, var19, var20);
                    }

                    this.field_147913_i.renderBlockAsItem(var21, var10.getItemDamage(), 1.0F);
                    GL11.glPopMatrix();
                }

                if (var21.getRenderBlockPass() > 0)
                {
                    GL11.glDisable(GL11.GL_BLEND);
                }
            }
            else
            {
                float var17;

                if (var10.getItemSpriteNumber() == 1 && var10.getItem().requiresMultipleRenderPasses())
                {
                    if (renderInFrame)
                    {
                        GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
                        GL11.glTranslatef(0.0F, -0.05F, 0.0F);
                    }
                    else
                    {
                        GL11.glScalef(0.5F, 0.5F, 0.5F);
                    }

                    for (int var23 = 0; var23 <= 1; ++var23)
                    {
                        this.random.setSeed(187L);
                        IIcon var22 = var10.getItem().getIconFromDamageForRenderPass(var10.getItemDamage(), var23);

                        if (this.renderWithColor)
                        {
                            var24 = var10.getItem().getColorFromItemStack(var10, var23);
                            var17 = (float)(var24 >> 16 & 255) / 255.0F;
                            var18 = (float)(var24 >> 8 & 255) / 255.0F;
                            var19 = (float)(var24 & 255) / 255.0F;
                            GL11.glColor4f(var17, var18, var19, 1.0F);
                            this.renderDroppedItem(par1EntityItem, var22, var13, par9, var17, var18, var19);
                        }
                        else
                        {
                            this.renderDroppedItem(par1EntityItem, var22, var13, par9, 1.0F, 1.0F, 1.0F);
                        }
                    }
                }
                else
                {
                    if (var10 != null && var10.getItem() instanceof ItemCloth)
                    {
                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                        GL11.glEnable(GL11.GL_BLEND);
                        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    }

                    if (renderInFrame)
                    {
                        GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
                        GL11.glTranslatef(0.0F, -0.05F, 0.0F);
                    }
                    else
                    {
                        GL11.glScalef(0.5F, 0.5F, 0.5F);
                    }

                    IIcon var14 = var10.getIconIndex();

                    if (this.renderWithColor)
                    {
                        int var15 = var10.getItem().getColorFromItemStack(var10, 0);
                        float var16 = (float)(var15 >> 16 & 255) / 255.0F;
                        var17 = (float)(var15 >> 8 & 255) / 255.0F;
                        var18 = (float)(var15 & 255) / 255.0F;
                        this.renderDroppedItem(par1EntityItem, var14, var13, par9, var16, var17, var18);
                    }
                    else
                    {
                        this.renderDroppedItem(par1EntityItem, var14, var13, par9, 1.0F, 1.0F, 1.0F);
                    }

                    if (var10 != null && var10.getItem() instanceof ItemCloth)
                    {
                        GL11.glDisable(GL11.GL_BLEND);
                    }
                }
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityItem par1EntityItem)
    {
        return this.renderManager.renderEngine.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
    }

    /**
     * Renders a dropped item
     */
    private void renderDroppedItem(EntityItem par1EntityItem, IIcon par2Icon, int par3, float par4, float par5, float par6, float par7)
    {
        Tessellator var8 = Tessellator.instance;

        if (par2Icon == null)
        {
            TextureManager var9 = Minecraft.getMinecraft().getTextureManager();
            ResourceLocation var10 = var9.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
            par2Icon = ((TextureMap)var9.getTexture(var10)).getAtlasSprite("missingno");
        }

        float var25 = ((IIcon)par2Icon).getMinU();
        float var26 = ((IIcon)par2Icon).getMaxU();
        float var11 = ((IIcon)par2Icon).getMinV();
        float var12 = ((IIcon)par2Icon).getMaxV();
        float var13 = 1.0F;
        float var14 = 0.5F;
        float var15 = 0.25F;
        float var17;

        if (this.renderManager.options.fancyGraphics)
        {
            GL11.glPushMatrix();

            if (renderInFrame)
            {
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                GL11.glRotatef((((float)par1EntityItem.age + par4) / 20.0F + par1EntityItem.hoverStart) * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            float var16 = 0.0625F;
            var17 = 0.021875F;
            ItemStack var18 = par1EntityItem.getEntityItem();
            int var19 = var18.stackSize;
            byte var24;

            if (var19 < 2)
            {
                var24 = 1;
            }
            else if (var19 < 16)
            {
                var24 = 2;
            }
            else if (var19 < 32)
            {
                var24 = 3;
            }
            else
            {
                var24 = 4;
            }

            GL11.glTranslatef(-var14, -var15, -((var16 + var17) * (float)var24 / 2.0F));

            for (int var20 = 0; var20 < var24; ++var20)
            {
                GL11.glTranslatef(0.0F, 0.0F, var16 + var17);

                if (var18.getItemSpriteNumber() == 0)
                {
                    this.bindTexture(TextureMap.locationBlocksTexture);
                }
                else
                {
                    this.bindTexture(TextureMap.locationItemsTexture);
                }

                GL11.glColor4f(par5, par6, par7, 1.0F);
                ItemRenderer.renderItemIn2D(var8, var26, var11, var25, var12, ((IIcon)par2Icon).getIconWidth(), ((IIcon)par2Icon).getIconHeight(), var16);

                if (var18.hasEffect())
                {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    this.renderManager.renderEngine.bindTexture(RES_ITEM_GLINT);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                    float var21 = 0.76F;
                    GL11.glColor4f(0.5F * var21, 0.25F * var21, 0.8F * var21, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float var22 = 0.125F;
                    GL11.glScalef(var22, var22, var22);
                    float var23 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(var23, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, var16);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(var22, var22, var22);
                    var23 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-var23, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, var16);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }
            }

            GL11.glPopMatrix();
        }
        else
        {
            for (int var27 = 0; var27 < par3; ++var27)
            {
                GL11.glPushMatrix();

                if (var27 > 0)
                {
                    var17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float var29 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float var28 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    GL11.glTranslatef(var17, var29, var28);
                }

                if (!renderInFrame)
                {
                    GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                }

                GL11.glColor4f(par5, par6, par7, 1.0F);
                var8.startDrawingQuads();
                var8.setNormal(0.0F, 1.0F, 0.0F);
                var8.addVertexWithUV((double)(0.0F - var14), (double)(0.0F - var15), 0.0D, (double)var25, (double)var12);
                var8.addVertexWithUV((double)(var13 - var14), (double)(0.0F - var15), 0.0D, (double)var26, (double)var12);
                var8.addVertexWithUV((double)(var13 - var14), (double)(1.0F - var15), 0.0D, (double)var26, (double)var11);
                var8.addVertexWithUV((double)(0.0F - var14), (double)(1.0F - var15), 0.0D, (double)var25, (double)var11);
                var8.draw();
                GL11.glPopMatrix();
            }
        }
    }

    /**
     * Renders the item's icon or block into the UI at the specified position.
     */
    public void renderItemIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5)
    {
        int var6 = par3ItemStack.getItemDamage();
        Object var7 = par3ItemStack.getIconIndex();
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        float var17;
        float var18;
        int var9;
        float var12;

        if (par3ItemStack.getItemSpriteNumber() == 0 && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(par3ItemStack.getItem()).getRenderType()))
        {
            par2TextureManager.bindTexture(TextureMap.locationBlocksTexture);
            Block var15 = Block.getBlockFromItem(par3ItemStack.getItem());
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(par4 - 2), (float)(par5 + 3), -3.0F + this.zLevel);
            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1.0F);
            GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            var9 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, 0);
            var18 = (float)(var9 >> 16 & 255) / 255.0F;
            var17 = (float)(var9 >> 8 & 255) / 255.0F;
            var12 = (float)(var9 & 255) / 255.0F;

            if (this.renderWithColor)
            {
                GL11.glColor4f(var18, var17, var12, 1.0F);
            }

            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            this.field_147913_i.useInventoryTint = this.renderWithColor;
            this.field_147913_i.renderBlockAsItem(var15, var6, 1.0F);
            this.field_147913_i.useInventoryTint = true;
            GL11.glPopMatrix();
        }
        else if (par3ItemStack.getItem().requiresMultipleRenderPasses())
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            par2TextureManager.bindTexture(TextureMap.locationItemsTexture);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(0, 0, 0, 0);
            GL11.glColorMask(false, false, false, true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Tessellator var8 = Tessellator.instance;
            var8.startDrawingQuads();
            var8.setColorOpaque_I(-1);
            var8.addVertex((double)(par4 - 2), (double)(par5 + 18), (double)this.zLevel);
            var8.addVertex((double)(par4 + 18), (double)(par5 + 18), (double)this.zLevel);
            var8.addVertex((double)(par4 + 18), (double)(par5 - 2), (double)this.zLevel);
            var8.addVertex((double)(par4 - 2), (double)(par5 - 2), (double)this.zLevel);
            var8.draw();
            GL11.glColorMask(true, true, true, true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);

            for (var9 = 0; var9 <= 1; ++var9)
            {
                IIcon var10 = par3ItemStack.getItem().getIconFromDamageForRenderPass(var6, var9);
                int var11 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, var9);
                var12 = (float)(var11 >> 16 & 255) / 255.0F;
                float var13 = (float)(var11 >> 8 & 255) / 255.0F;
                float var14 = (float)(var11 & 255) / 255.0F;

                if (this.renderWithColor)
                {
                    GL11.glColor4f(var12, var13, var14, 1.0F);
                }

                this.renderIcon(par4, par5, var10, 16, 16);
            }

            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_LIGHTING);
        }
        else
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            ResourceLocation var16 = par2TextureManager.getResourceLocation(par3ItemStack.getItemSpriteNumber());
            par2TextureManager.bindTexture(var16);

            if (var7 == null)
            {
                var7 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(var16)).getAtlasSprite("missingno");
            }

            var9 = par3ItemStack.getItem().getColorFromItemStack(par3ItemStack, 0);
            var18 = (float)(var9 >> 16 & 255) / 255.0F;
            var17 = (float)(var9 >> 8 & 255) / 255.0F;
            var12 = (float)(var9 & 255) / 255.0F;

            if (this.renderWithColor)
            {
                GL11.glColor4f(var18, var17, var12, 1.0F);
            }

            this.renderIcon(par4, par5, (IIcon)var7, 16, 16);
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    /**
     * Render the item's icon or block into the GUI, including the glint effect.
     */
    public void renderItemAndEffectIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, final ItemStack par3ItemStack, int par4, int par5)
    {
        if (par3ItemStack != null)
        {
            this.zLevel += 50.0F;

            try
            {
                this.renderItemIntoGUI(par1FontRenderer, par2TextureManager, par3ItemStack, par4, par5);
            }
            catch (Throwable var9)
            {
                CrashReport var7 = CrashReport.makeCrashReport(var9, "Rendering item");
                CrashReportCategory var8 = var7.makeCategory("Item being rendered");
                var8.addCrashSectionCallable("Item Type", new Callable()
                {
                    private static final String __OBFID = "CL_00001004";
                    public String call()
                    {
                        return String.valueOf(par3ItemStack.getItem());
                    }
                });
                var8.addCrashSectionCallable("Item Aux", new Callable()
                {
                    private static final String __OBFID = "CL_00001005";
                    public String call()
                    {
                        return String.valueOf(par3ItemStack.getItemDamage());
                    }
                });
                var8.addCrashSectionCallable("Item NBT", new Callable()
                {
                    private static final String __OBFID = "CL_00001006";
                    public String call()
                    {
                        return String.valueOf(par3ItemStack.getTagCompound());
                    }
                });
                var8.addCrashSectionCallable("Item Foil", new Callable()
                {
                    private static final String __OBFID = "CL_00001007";
                    public String call()
                    {
                        return String.valueOf(par3ItemStack.hasEffect());
                    }
                });
                throw new ReportedException(var7);
            }

            if (par3ItemStack.hasEffect())
            {
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDepthMask(false);
                par2TextureManager.bindTexture(RES_ITEM_GLINT);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
                this.renderGlint(par4 * 431278612 + par5 * 32178161, par4 - 2, par5 - 2, 20, 20);
                GL11.glDepthMask(true);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
            }

            this.zLevel -= 50.0F;
        }
    }

    private void renderGlint(int par1, int par2, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 2; ++var6)
        {
            OpenGlHelper.glBlendFunc(772, 1, 0, 0);
            float var7 = 0.00390625F;
            float var8 = 0.00390625F;
            float var9 = (float)(Minecraft.getSystemTime() % (long)(3000 + var6 * 1873)) / (3000.0F + (float)(var6 * 1873)) * 256.0F;
            float var10 = 0.0F;
            Tessellator var11 = Tessellator.instance;
            float var12 = 4.0F;

            if (var6 == 1)
            {
                var12 = -1.0F;
            }

            var11.startDrawingQuads();
            var11.addVertexWithUV((double)(par2 + 0), (double)(par3 + par5), (double)this.zLevel, (double)((var9 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
            var11.addVertexWithUV((double)(par2 + par4), (double)(par3 + par5), (double)this.zLevel, (double)((var9 + (float)par4 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
            var11.addVertexWithUV((double)(par2 + par4), (double)(par3 + 0), (double)this.zLevel, (double)((var9 + (float)par4) * var7), (double)((var10 + 0.0F) * var8));
            var11.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)this.zLevel, (double)((var9 + 0.0F) * var7), (double)((var10 + 0.0F) * var8));
            var11.draw();
        }
    }

    /**
     * Renders the item's overlay information. Examples being stack count or damage on top of the item's image at the
     * specified position.
     */
    public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5)
    {
        this.renderItemOverlayIntoGUI(par1FontRenderer, par2TextureManager, par3ItemStack, par4, par5, (String)null);
    }

    public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5, String par6Str)
    {
        if (par3ItemStack != null)
        {
            if (par3ItemStack.stackSize > 1 || par6Str != null)
            {
                String var7 = par6Str == null ? String.valueOf(par3ItemStack.stackSize) : par6Str;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_BLEND);
                par1FontRenderer.drawStringWithShadow(var7, par4 + 19 - 2 - par1FontRenderer.getStringWidth(var7), par5 + 6 + 3, 16777215);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }

            if (par3ItemStack.isItemDamaged())
            {
                int var12 = (int)Math.round(13.0D - (double)par3ItemStack.getItemDamageForDisplay() * 13.0D / (double)par3ItemStack.getMaxDamage());
                int var8 = (int)Math.round(255.0D - (double)par3ItemStack.getItemDamageForDisplay() * 255.0D / (double)par3ItemStack.getMaxDamage());
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_BLEND);
                Tessellator var9 = Tessellator.instance;
                int var10 = 255 - var8 << 16 | var8 << 8;
                int var11 = (255 - var8) / 4 << 16 | 16128;
                this.renderQuad(var9, par4 + 2, par5 + 13, 13, 2, 0);
                this.renderQuad(var9, par4 + 2, par5 + 13, 12, 1, var11);
                this.renderQuad(var9, par4 + 2, par5 + 13, var12, 1, var10);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Adds a quad to the tesselator at the specified position with the set width and height and color.  Args:
     * tessellator, x, y, width, height, color
     */
    private void renderQuad(Tessellator par1Tessellator, int par2, int par3, int par4, int par5, int par6)
    {
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setColorOpaque_I(par6);
        par1Tessellator.addVertex((double)(par2 + 0), (double)(par3 + 0), 0.0D);
        par1Tessellator.addVertex((double)(par2 + 0), (double)(par3 + par5), 0.0D);
        par1Tessellator.addVertex((double)(par2 + par4), (double)(par3 + par5), 0.0D);
        par1Tessellator.addVertex((double)(par2 + par4), (double)(par3 + 0), 0.0D);
        par1Tessellator.draw();
    }

    public void renderIcon(int par1, int par2, IIcon par3Icon, int par4, int par5)
    {
        Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.addVertexWithUV((double)(par1 + 0), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.getMinU(), (double)par3Icon.getMaxV());
        var6.addVertexWithUV((double)(par1 + par4), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.getMaxU(), (double)par3Icon.getMaxV());
        var6.addVertexWithUV((double)(par1 + par4), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.getMaxU(), (double)par3Icon.getMinV());
        var6.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.getMinU(), (double)par3Icon.getMinV());
        var6.draw();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityItem)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityItem)par1Entity, par2, par4, par6, par8, par9);
    }
}
