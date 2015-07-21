package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDragon extends RenderLiving
{
    private static final ResourceLocation enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
    private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
    private static final ResourceLocation enderDragonEyesTextures = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");

    /** An instance of the dragon model in RenderDragon */
    protected ModelDragon modelDragon;
    private static final String __OBFID = "CL_00000988";

    public RenderDragon()
    {
        super(new ModelDragon(0.0F), 0.5F);
        this.modelDragon = (ModelDragon)this.mainModel;
        this.setRenderPassModel(this.mainModel);
    }

    protected void rotateCorpse(EntityDragon par1EntityDragon, float par2, float par3, float par4)
    {
        float var5 = (float)par1EntityDragon.getMovementOffsets(7, par4)[0];
        float var6 = (float)(par1EntityDragon.getMovementOffsets(5, par4)[1] - par1EntityDragon.getMovementOffsets(10, par4)[1]);
        GL11.glRotatef(-var5, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.0F, 1.0F);

        if (par1EntityDragon.deathTime > 0)
        {
            float var7 = ((float)par1EntityDragon.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
            var7 = MathHelper.sqrt_float(var7);

            if (var7 > 1.0F)
            {
                var7 = 1.0F;
            }

            GL11.glRotatef(var7 * this.getDeathMaxRotation(par1EntityDragon), 0.0F, 0.0F, 1.0F);
        }
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityDragon par1EntityDragon, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        if (par1EntityDragon.deathTicks > 0)
        {
            float var8 = (float)par1EntityDragon.deathTicks / 200.0F;
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glAlphaFunc(GL11.GL_GREATER, var8);
            this.bindTexture(enderDragonExplodingTextures);
            this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glDepthFunc(GL11.GL_EQUAL);
        }

        this.bindEntityTexture(par1EntityDragon);
        this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);

        if (par1EntityDragon.hurtTime > 0)
        {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
            this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityDragon par1EntityDragon, double par2, double par4, double par6, float par8, float par9)
    {
        BossStatus.setBossStatus(par1EntityDragon, false);
        super.doRender((EntityLiving)par1EntityDragon, par2, par4, par6, par8, par9);

        if (par1EntityDragon.healingEnderCrystal != null)
        {
            float var10 = (float)par1EntityDragon.healingEnderCrystal.innerRotation + par9;
            float var11 = MathHelper.sin(var10 * 0.2F) / 2.0F + 0.5F;
            var11 = (var11 * var11 + var11) * 0.2F;
            float var12 = (float)(par1EntityDragon.healingEnderCrystal.posX - par1EntityDragon.posX - (par1EntityDragon.prevPosX - par1EntityDragon.posX) * (double)(1.0F - par9));
            float var13 = (float)((double)var11 + par1EntityDragon.healingEnderCrystal.posY - 1.0D - par1EntityDragon.posY - (par1EntityDragon.prevPosY - par1EntityDragon.posY) * (double)(1.0F - par9));
            float var14 = (float)(par1EntityDragon.healingEnderCrystal.posZ - par1EntityDragon.posZ - (par1EntityDragon.prevPosZ - par1EntityDragon.posZ) * (double)(1.0F - par9));
            float var15 = MathHelper.sqrt_float(var12 * var12 + var14 * var14);
            float var16 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6);
            GL11.glRotatef((float)(-Math.atan2((double)var14, (double)var12)) * 180.0F / (float)Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef((float)(-Math.atan2((double)var15, (double)var13)) * 180.0F / (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
            Tessellator var17 = Tessellator.instance;
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_CULL_FACE);
            this.bindTexture(enderDragonCrystalBeamTextures);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            float var18 = 0.0F - ((float)par1EntityDragon.ticksExisted + par9) * 0.01F;
            float var19 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14) / 32.0F - ((float)par1EntityDragon.ticksExisted + par9) * 0.01F;
            var17.startDrawing(5);
            byte var20 = 8;

            for (int var21 = 0; var21 <= var20; ++var21)
            {
                float var22 = MathHelper.sin((float)(var21 % var20) * (float)Math.PI * 2.0F / (float)var20) * 0.75F;
                float var23 = MathHelper.cos((float)(var21 % var20) * (float)Math.PI * 2.0F / (float)var20) * 0.75F;
                float var24 = (float)(var21 % var20) * 1.0F / (float)var20;
                var17.setColorOpaque_I(0);
                var17.addVertexWithUV((double)(var22 * 0.2F), (double)(var23 * 0.2F), 0.0D, (double)var24, (double)var19);
                var17.setColorOpaque_I(16777215);
                var17.addVertexWithUV((double)var22, (double)var23, (double)var16, (double)var24, (double)var18);
            }

            var17.draw();
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glShadeModel(GL11.GL_FLAT);
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityDragon par1EntityDragon)
    {
        return enderDragonTextures;
    }

    protected void renderEquippedItems(EntityDragon par1EntityDragon, float par2)
    {
        super.renderEquippedItems(par1EntityDragon, par2);
        Tessellator var3 = Tessellator.instance;

        if (par1EntityDragon.deathTicks > 0)
        {
            RenderHelper.disableStandardItemLighting();
            float var4 = ((float)par1EntityDragon.deathTicks + par2) / 200.0F;
            float var5 = 0.0F;

            if (var4 > 0.8F)
            {
                var5 = (var4 - 0.8F) / 0.2F;
            }

            Random var6 = new Random(432L);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -1.0F, -2.0F);

            for (int var7 = 0; (float)var7 < (var4 + var4 * var4) / 2.0F * 60.0F; ++var7)
            {
                GL11.glRotatef(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(var6.nextFloat() * 360.0F + var4 * 90.0F, 0.0F, 0.0F, 1.0F);
                var3.startDrawing(6);
                float var8 = var6.nextFloat() * 20.0F + 5.0F + var5 * 10.0F;
                float var9 = var6.nextFloat() * 2.0F + 1.0F + var5 * 2.0F;
                var3.setColorRGBA_I(16777215, (int)(255.0F * (1.0F - var5)));
                var3.addVertex(0.0D, 0.0D, 0.0D);
                var3.setColorRGBA_I(16711935, 0);
                var3.addVertex(-0.866D * (double)var9, (double)var8, (double)(-0.5F * var9));
                var3.addVertex(0.866D * (double)var9, (double)var8, (double)(-0.5F * var9));
                var3.addVertex(0.0D, (double)var8, (double)(1.0F * var9));
                var3.addVertex(-0.866D * (double)var9, (double)var8, (double)(-0.5F * var9));
                var3.draw();
            }

            GL11.glPopMatrix();
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            RenderHelper.enableStandardItemLighting();
        }
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityDragon par1EntityDragon, int par2, float par3)
    {
        if (par2 == 1)
        {
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }

        if (par2 != 0)
        {
            return -1;
        }
        else
        {
            this.bindTexture(enderDragonEyesTextures);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_EQUAL);
            char var4 = 61680;
            int var5 = var4 % 65536;
            int var6 = var4 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var5 / 1.0F, (float)var6 / 1.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            return 1;
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityDragon)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.shouldRenderPass((EntityDragon)par1EntityLivingBase, par2, par3);
    }

    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.renderEquippedItems((EntityDragon)par1EntityLivingBase, par2);
    }

    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
        this.rotateCorpse((EntityDragon)par1EntityLivingBase, par2, par3, par4);
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.renderModel((EntityDragon)par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityDragon)par1Entity, par2, par4, par6, par8, par9);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityDragon)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityDragon)par1Entity, par2, par4, par6, par8, par9);
    }
}
