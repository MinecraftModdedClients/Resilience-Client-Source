package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

public class RenderItemFrame extends Render
{
    private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
    private final RenderBlocks field_147916_f = new RenderBlocks();
    private final Minecraft field_147917_g = Minecraft.getMinecraft();
    private IIcon field_94147_f;
    private static final String __OBFID = "CL_00001002";

    public void updateIcons(IIconRegister par1IconRegister)
    {
        this.field_94147_f = par1IconRegister.registerIcon("itemframe_background");
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityItemFrame par1EntityItemFrame, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        double var10 = par1EntityItemFrame.posX - par2 - 0.5D;
        double var12 = par1EntityItemFrame.posY - par4 - 0.5D;
        double var14 = par1EntityItemFrame.posZ - par6 - 0.5D;
        int var16 = par1EntityItemFrame.field_146063_b + Direction.offsetX[par1EntityItemFrame.hangingDirection];
        int var17 = par1EntityItemFrame.field_146064_c;
        int var18 = par1EntityItemFrame.field_146062_d + Direction.offsetZ[par1EntityItemFrame.hangingDirection];
        GL11.glTranslated((double)var16 - var10, (double)var17 - var12, (double)var18 - var14);

        if (par1EntityItemFrame.getDisplayedItem() != null && par1EntityItemFrame.getDisplayedItem().getItem() == Items.filled_map)
        {
            this.func_147915_b(par1EntityItemFrame);
        }
        else
        {
            this.renderFrameItemAsBlock(par1EntityItemFrame);
        }

        this.func_82402_b(par1EntityItemFrame);
        GL11.glPopMatrix();
        this.func_147914_a(par1EntityItemFrame, par2 + (double)((float)Direction.offsetX[par1EntityItemFrame.hangingDirection] * 0.3F), par4 - 0.25D, par6 + (double)((float)Direction.offsetZ[par1EntityItemFrame.hangingDirection] * 0.3F));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityItemFrame par1EntityItemFrame)
    {
        return null;
    }

    private void func_147915_b(EntityItemFrame p_147915_1_)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(p_147915_1_.rotationYaw, 0.0F, 1.0F, 0.0F);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        Block var2 = Blocks.planks;
        float var3 = 0.0625F;
        float var4 = 1.0F;
        float var5 = var4 / 2.0F;
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F - var5 + 0.0625F), (double)(0.5F - var5 + 0.0625F), (double)var3, (double)(0.5F + var5 - 0.0625F), (double)(0.5F + var5 - 0.0625F));
        this.field_147916_f.setOverrideBlockTexture(this.field_94147_f);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        this.field_147916_f.clearOverrideBlockTexture();
        this.field_147916_f.unlockBlockBounds();
        GL11.glPopMatrix();
        this.field_147916_f.setOverrideBlockTexture(Blocks.planks.getIcon(1, 2));
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F - var5), (double)(var3 + 1.0E-4F), (double)(var3 + 0.5F - var5), (double)(0.5F + var5));
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F + var5 - var3), (double)(0.5F - var5), (double)(var3 + 1.0E-4F), (double)(0.5F + var5), (double)(0.5F + var5));
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F - var5), (double)var3, (double)(0.5F + var5), (double)(var3 + 0.5F - var5));
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F + var5 - var3), (double)var3, (double)(0.5F + var5), (double)(0.5F + var5));
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        this.field_147916_f.unlockBlockBounds();
        this.field_147916_f.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }

    /**
     * Render the item frame's item as a block.
     */
    private void renderFrameItemAsBlock(EntityItemFrame par1EntityItemFrame)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        Block var2 = Blocks.planks;
        float var3 = 0.0625F;
        float var4 = 0.75F;
        float var5 = var4 / 2.0F;
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F - var5 + 0.0625F), (double)(0.5F - var5 + 0.0625F), (double)(var3 * 0.5F), (double)(0.5F + var5 - 0.0625F), (double)(0.5F + var5 - 0.0625F));
        this.field_147916_f.setOverrideBlockTexture(this.field_94147_f);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        this.field_147916_f.clearOverrideBlockTexture();
        this.field_147916_f.unlockBlockBounds();
        GL11.glPopMatrix();
        this.field_147916_f.setOverrideBlockTexture(Blocks.planks.getIcon(1, 2));
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F - var5), (double)(var3 + 1.0E-4F), (double)(var3 + 0.5F - var5), (double)(0.5F + var5));
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F + var5 - var3), (double)(0.5F - var5), (double)(var3 + 1.0E-4F), (double)(0.5F + var5), (double)(0.5F + var5));
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F - var5), (double)var3, (double)(0.5F + var5), (double)(var3 + 0.5F - var5));
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F + var5 - var3), (double)var3, (double)(0.5F + var5), (double)(0.5F + var5));
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        this.field_147916_f.unlockBlockBounds();
        this.field_147916_f.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }

    private void func_82402_b(EntityItemFrame par1EntityItemFrame)
    {
        ItemStack var2 = par1EntityItemFrame.getDisplayedItem();

        if (var2 != null)
        {
            EntityItem var3 = new EntityItem(par1EntityItemFrame.worldObj, 0.0D, 0.0D, 0.0D, var2);
            Item var4 = var3.getEntityItem().getItem();
            var3.getEntityItem().stackSize = 1;
            var3.hoverStart = 0.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.453125F * (float)Direction.offsetX[par1EntityItemFrame.hangingDirection], -0.18F, -0.453125F * (float)Direction.offsetZ[par1EntityItemFrame.hangingDirection]);
            GL11.glRotatef(180.0F + par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef((float)(-90 * par1EntityItemFrame.getRotation()), 0.0F, 0.0F, 1.0F);

            switch (par1EntityItemFrame.getRotation())
            {
                case 1:
                    GL11.glTranslatef(-0.16F, -0.16F, 0.0F);
                    break;

                case 2:
                    GL11.glTranslatef(0.0F, -0.32F, 0.0F);
                    break;

                case 3:
                    GL11.glTranslatef(0.16F, -0.16F, 0.0F);
            }

            if (var4 == Items.filled_map)
            {
                this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
                Tessellator var5 = Tessellator.instance;
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                float var6 = 0.0078125F;
                GL11.glScalef(var6, var6, var6);

                switch (par1EntityItemFrame.getRotation())
                {
                    case 0:
                        GL11.glTranslatef(-64.0F, -87.0F, -1.5F);
                        break;

                    case 1:
                        GL11.glTranslatef(-66.5F, -84.5F, -1.5F);
                        break;

                    case 2:
                        GL11.glTranslatef(-64.0F, -82.0F, -1.5F);
                        break;

                    case 3:
                        GL11.glTranslatef(-61.5F, -84.5F, -1.5F);
                }

                GL11.glNormal3f(0.0F, 0.0F, -1.0F);
                MapData var7 = Items.filled_map.getMapData(var3.getEntityItem(), par1EntityItemFrame.worldObj);
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);

                if (var7 != null)
                {
                    this.field_147917_g.entityRenderer.getMapItemRenderer().func_148250_a(var7, true);
                }
            }
            else
            {
                if (var4 == Items.compass)
                {
                    TextureManager var12 = Minecraft.getMinecraft().getTextureManager();
                    var12.bindTexture(TextureMap.locationItemsTexture);
                    TextureAtlasSprite var14 = ((TextureMap)var12.getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Items.compass.getIconIndex(var3.getEntityItem()).getIconName());

                    if (var14 instanceof TextureCompass)
                    {
                        TextureCompass var15 = (TextureCompass)var14;
                        double var8 = var15.currentAngle;
                        double var10 = var15.angleDelta;
                        var15.currentAngle = 0.0D;
                        var15.angleDelta = 0.0D;
                        var15.updateCompass(par1EntityItemFrame.worldObj, par1EntityItemFrame.posX, par1EntityItemFrame.posZ, (double)MathHelper.wrapAngleTo180_float((float)(180 + par1EntityItemFrame.hangingDirection * 90)), false, true);
                        var15.currentAngle = var8;
                        var15.angleDelta = var10;
                    }
                }

                RenderItem.renderInFrame = true;
                RenderManager.instance.func_147940_a(var3, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderItem.renderInFrame = false;

                if (var4 == Items.compass)
                {
                    TextureAtlasSprite var13 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Items.compass.getIconIndex(var3.getEntityItem()).getIconName());

                    if (var13.getFrameCount() > 0)
                    {
                        var13.updateAnimation();
                    }
                }
            }

            GL11.glPopMatrix();
        }
    }

    protected void func_147914_a(EntityItemFrame p_147914_1_, double p_147914_2_, double p_147914_4_, double p_147914_6_)
    {
        if (Minecraft.isGuiEnabled() && p_147914_1_.getDisplayedItem() != null && p_147914_1_.getDisplayedItem().hasDisplayName() && this.renderManager.field_147941_i == p_147914_1_)
        {
            float var8 = 1.6F;
            float var9 = 0.016666668F * var8;
            double var10 = p_147914_1_.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float var12 = p_147914_1_.isSneaking() ? 32.0F : 64.0F;

            if (var10 < (double)(var12 * var12))
            {
                String var13 = p_147914_1_.getDisplayedItem().getDisplayName();

                if (p_147914_1_.isSneaking())
                {
                    FontRenderer var14 = this.getFontRendererFromRenderManager();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)p_147914_2_ + 0.0F, (float)p_147914_4_ + p_147914_1_.height + 0.5F, (float)p_147914_6_);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                    GL11.glScalef(-var9, -var9, var9);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
                    GL11.glDepthMask(false);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    Tessellator var15 = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    var15.startDrawingQuads();
                    int var16 = var14.getStringWidth(var13) / 2;
                    var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    var15.addVertex((double)(-var16 - 1), -1.0D, 0.0D);
                    var15.addVertex((double)(-var16 - 1), 8.0D, 0.0D);
                    var15.addVertex((double)(var16 + 1), 8.0D, 0.0D);
                    var15.addVertex((double)(var16 + 1), -1.0D, 0.0D);
                    var15.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDepthMask(true);
                    var14.drawString(var13, -var14.getStringWidth(var13) / 2, 0, 553648127);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                }
                else
                {
                }
            }
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityItemFrame)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityItemFrame)par1Entity, par2, par4, par6, par8, par9);
    }
}
