package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderPainting extends Render
{
    private static final ResourceLocation field_110807_a = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
    private static final String __OBFID = "CL_00001018";

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityPainting par1EntityPainting, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(par2, par4, par6);
        GL11.glRotatef(par8, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        this.bindEntityTexture(par1EntityPainting);
        EntityPainting.EnumArt var10 = par1EntityPainting.art;
        float var11 = 0.0625F;
        GL11.glScalef(var11, var11, var11);
        this.func_77010_a(par1EntityPainting, var10.sizeX, var10.sizeY, var10.offsetX, var10.offsetY);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityPainting par1EntityPainting)
    {
        return field_110807_a;
    }

    private void func_77010_a(EntityPainting par1EntityPainting, int par2, int par3, int par4, int par5)
    {
        float var6 = (float)(-par2) / 2.0F;
        float var7 = (float)(-par3) / 2.0F;
        float var8 = 0.5F;
        float var9 = 0.75F;
        float var10 = 0.8125F;
        float var11 = 0.0F;
        float var12 = 0.0625F;
        float var13 = 0.75F;
        float var14 = 0.8125F;
        float var15 = 0.001953125F;
        float var16 = 0.001953125F;
        float var17 = 0.7519531F;
        float var18 = 0.7519531F;
        float var19 = 0.0F;
        float var20 = 0.0625F;

        for (int var21 = 0; var21 < par2 / 16; ++var21)
        {
            for (int var22 = 0; var22 < par3 / 16; ++var22)
            {
                float var23 = var6 + (float)((var21 + 1) * 16);
                float var24 = var6 + (float)(var21 * 16);
                float var25 = var7 + (float)((var22 + 1) * 16);
                float var26 = var7 + (float)(var22 * 16);
                this.func_77008_a(par1EntityPainting, (var23 + var24) / 2.0F, (var25 + var26) / 2.0F);
                float var27 = (float)(par4 + par2 - var21 * 16) / 256.0F;
                float var28 = (float)(par4 + par2 - (var21 + 1) * 16) / 256.0F;
                float var29 = (float)(par5 + par3 - var22 * 16) / 256.0F;
                float var30 = (float)(par5 + par3 - (var22 + 1) * 16) / 256.0F;
                Tessellator var31 = Tessellator.instance;
                var31.startDrawingQuads();
                var31.setNormal(0.0F, 0.0F, -1.0F);
                var31.addVertexWithUV((double)var23, (double)var26, (double)(-var8), (double)var28, (double)var29);
                var31.addVertexWithUV((double)var24, (double)var26, (double)(-var8), (double)var27, (double)var29);
                var31.addVertexWithUV((double)var24, (double)var25, (double)(-var8), (double)var27, (double)var30);
                var31.addVertexWithUV((double)var23, (double)var25, (double)(-var8), (double)var28, (double)var30);
                var31.setNormal(0.0F, 0.0F, 1.0F);
                var31.addVertexWithUV((double)var23, (double)var25, (double)var8, (double)var9, (double)var11);
                var31.addVertexWithUV((double)var24, (double)var25, (double)var8, (double)var10, (double)var11);
                var31.addVertexWithUV((double)var24, (double)var26, (double)var8, (double)var10, (double)var12);
                var31.addVertexWithUV((double)var23, (double)var26, (double)var8, (double)var9, (double)var12);
                var31.setNormal(0.0F, 1.0F, 0.0F);
                var31.addVertexWithUV((double)var23, (double)var25, (double)(-var8), (double)var13, (double)var15);
                var31.addVertexWithUV((double)var24, (double)var25, (double)(-var8), (double)var14, (double)var15);
                var31.addVertexWithUV((double)var24, (double)var25, (double)var8, (double)var14, (double)var16);
                var31.addVertexWithUV((double)var23, (double)var25, (double)var8, (double)var13, (double)var16);
                var31.setNormal(0.0F, -1.0F, 0.0F);
                var31.addVertexWithUV((double)var23, (double)var26, (double)var8, (double)var13, (double)var15);
                var31.addVertexWithUV((double)var24, (double)var26, (double)var8, (double)var14, (double)var15);
                var31.addVertexWithUV((double)var24, (double)var26, (double)(-var8), (double)var14, (double)var16);
                var31.addVertexWithUV((double)var23, (double)var26, (double)(-var8), (double)var13, (double)var16);
                var31.setNormal(-1.0F, 0.0F, 0.0F);
                var31.addVertexWithUV((double)var23, (double)var25, (double)var8, (double)var18, (double)var19);
                var31.addVertexWithUV((double)var23, (double)var26, (double)var8, (double)var18, (double)var20);
                var31.addVertexWithUV((double)var23, (double)var26, (double)(-var8), (double)var17, (double)var20);
                var31.addVertexWithUV((double)var23, (double)var25, (double)(-var8), (double)var17, (double)var19);
                var31.setNormal(1.0F, 0.0F, 0.0F);
                var31.addVertexWithUV((double)var24, (double)var25, (double)(-var8), (double)var18, (double)var19);
                var31.addVertexWithUV((double)var24, (double)var26, (double)(-var8), (double)var18, (double)var20);
                var31.addVertexWithUV((double)var24, (double)var26, (double)var8, (double)var17, (double)var20);
                var31.addVertexWithUV((double)var24, (double)var25, (double)var8, (double)var17, (double)var19);
                var31.draw();
            }
        }
    }

    private void func_77008_a(EntityPainting par1EntityPainting, float par2, float par3)
    {
        int var4 = MathHelper.floor_double(par1EntityPainting.posX);
        int var5 = MathHelper.floor_double(par1EntityPainting.posY + (double)(par3 / 16.0F));
        int var6 = MathHelper.floor_double(par1EntityPainting.posZ);

        if (par1EntityPainting.hangingDirection == 2)
        {
            var4 = MathHelper.floor_double(par1EntityPainting.posX + (double)(par2 / 16.0F));
        }

        if (par1EntityPainting.hangingDirection == 1)
        {
            var6 = MathHelper.floor_double(par1EntityPainting.posZ - (double)(par2 / 16.0F));
        }

        if (par1EntityPainting.hangingDirection == 0)
        {
            var4 = MathHelper.floor_double(par1EntityPainting.posX - (double)(par2 / 16.0F));
        }

        if (par1EntityPainting.hangingDirection == 3)
        {
            var6 = MathHelper.floor_double(par1EntityPainting.posZ + (double)(par2 / 16.0F));
        }

        int var7 = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(var4, var5, var6, 0);
        int var8 = var7 % 65536;
        int var9 = var7 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var8, (float)var9);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityPainting)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityPainting)par1Entity, par2, par4, par6, par8, par9);
    }
}
