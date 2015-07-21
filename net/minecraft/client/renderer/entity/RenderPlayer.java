package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPlayer extends RendererLivingEntity
{
    private static final ResourceLocation steveTextures = new ResourceLocation("textures/entity/steve.png");
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;
    private static final String __OBFID = "CL_00001020";

    public RenderPlayer()
    {
        super(new ModelBiped(0.0F), 0.5F);
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0F);
        this.modelArmor = new ModelBiped(0.5F);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3)
    {
        ItemStack var4 = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);

        if (var4 != null)
        {
            Item var5 = var4.getItem();

            if (var5 instanceof ItemArmor)
            {
                ItemArmor var6 = (ItemArmor)var5;
                this.bindTexture(RenderBiped.func_110857_a(var6, par2));
                ModelBiped var7 = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
                var7.bipedHead.showModel = par2 == 0;
                var7.bipedHeadwear.showModel = par2 == 0;
                var7.bipedBody.showModel = par2 == 1 || par2 == 2;
                var7.bipedRightArm.showModel = par2 == 1;
                var7.bipedLeftArm.showModel = par2 == 1;
                var7.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
                var7.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
                this.setRenderPassModel(var7);
                var7.onGround = this.mainModel.onGround;
                var7.isRiding = this.mainModel.isRiding;
                var7.isChild = this.mainModel.isChild;

                if (var6.getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH)
                {
                    int var8 = var6.getColor(var4);
                    float var9 = (float)(var8 >> 16 & 255) / 255.0F;
                    float var10 = (float)(var8 >> 8 & 255) / 255.0F;
                    float var11 = (float)(var8 & 255) / 255.0F;
                    GL11.glColor3f(var9, var10, var11);

                    if (var4.isItemEnchanted())
                    {
                        return 31;
                    }

                    return 16;
                }

                GL11.glColor3f(1.0F, 1.0F, 1.0F);

                if (var4.isItemEnchanted())
                {
                    return 15;
                }

                return 1;
            }
        }

        return -1;
    }

    protected void func_82408_c(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3)
    {
        ItemStack var4 = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);

        if (var4 != null)
        {
            Item var5 = var4.getItem();

            if (var5 instanceof ItemArmor)
            {
                this.bindTexture(RenderBiped.func_110858_a((ItemArmor)var5, par2, "overlay"));
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        ItemStack var10 = par1AbstractClientPlayer.inventory.getCurrentItem();
        this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = var10 != null ? 1 : 0;

        if (var10 != null && par1AbstractClientPlayer.getItemInUseCount() > 0)
        {
            EnumAction var11 = var10.getItemUseAction();

            if (var11 == EnumAction.block)
            {
                this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 3;
            }
            else if (var11 == EnumAction.bow)
            {
                this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = true;
            }
        }

        this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = par1AbstractClientPlayer.isSneaking();
        double var13 = par4 - (double)par1AbstractClientPlayer.yOffset;

        if (par1AbstractClientPlayer.isSneaking() && !(par1AbstractClientPlayer instanceof EntityPlayerSP))
        {
            var13 -= 0.125D;
        }

        super.doRender((EntityLivingBase)par1AbstractClientPlayer, par2, var13, par6, par8, par9);
        this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
        this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
        this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractClientPlayer par1AbstractClientPlayer)
    {
        return par1AbstractClientPlayer.getLocationSkin();
    }

    protected void renderEquippedItems(AbstractClientPlayer par1AbstractClientPlayer, float par2)
    {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        super.renderEquippedItems(par1AbstractClientPlayer, par2);
        super.renderArrowsStuckInEntity(par1AbstractClientPlayer, par2);
        ItemStack var3 = par1AbstractClientPlayer.inventory.armorItemInSlot(3);

        if (var3 != null)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625F);
            float var4;

            if (var3.getItem() instanceof ItemBlock)
            {
                if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType()))
                {
                    var4 = 0.625F;
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(var4, -var4, -var4);
                }

                this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var3, 0);
            }
            else if (var3.getItem() == Items.skull)
            {
                var4 = 1.0625F;
                GL11.glScalef(var4, -var4, -var4);
                String var5 = "";

                if (var3.hasTagCompound() && var3.getTagCompound().func_150297_b("SkullOwner", 8))
                {
                    var5 = var3.getTagCompound().getString("SkullOwner");
                }

                TileEntitySkullRenderer.field_147536_b.func_147530_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, var3.getItemDamage(), var5);
            }

            GL11.glPopMatrix();
        }

        float var7;

        if (par1AbstractClientPlayer.getCommandSenderName().equals("deadmau5") && par1AbstractClientPlayer.getTextureSkin().isTextureUploaded())
        {
            this.bindTexture(par1AbstractClientPlayer.getLocationSkin());

            for (int var20 = 0; var20 < 2; ++var20)
            {
                float var22 = par1AbstractClientPlayer.prevRotationYaw + (par1AbstractClientPlayer.rotationYaw - par1AbstractClientPlayer.prevRotationYaw) * par2 - (par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2);
                float var6 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par2;
                GL11.glPushMatrix();
                GL11.glRotatef(var22, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(var6, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.375F * (float)(var20 * 2 - 1), 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.375F, 0.0F);
                GL11.glRotatef(-var6, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-var22, 0.0F, 1.0F, 0.0F);
                var7 = 1.3333334F;
                GL11.glScalef(var7, var7, var7);
                this.modelBipedMain.renderEars(0.0625F);
                GL11.glPopMatrix();
            }
        }

        boolean var21 = par1AbstractClientPlayer.getTextureCape().isTextureUploaded();
        float var11;

        if (var21 && !par1AbstractClientPlayer.isInvisible() && !par1AbstractClientPlayer.getHideCape())
        {
            this.bindTexture(par1AbstractClientPlayer.getLocationCape());
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double var25 = par1AbstractClientPlayer.field_71091_bM + (par1AbstractClientPlayer.field_71094_bP - par1AbstractClientPlayer.field_71091_bM) * (double)par2 - (par1AbstractClientPlayer.prevPosX + (par1AbstractClientPlayer.posX - par1AbstractClientPlayer.prevPosX) * (double)par2);
            double var26 = par1AbstractClientPlayer.field_71096_bN + (par1AbstractClientPlayer.field_71095_bQ - par1AbstractClientPlayer.field_71096_bN) * (double)par2 - (par1AbstractClientPlayer.prevPosY + (par1AbstractClientPlayer.posY - par1AbstractClientPlayer.prevPosY) * (double)par2);
            double var9 = par1AbstractClientPlayer.field_71097_bO + (par1AbstractClientPlayer.field_71085_bR - par1AbstractClientPlayer.field_71097_bO) * (double)par2 - (par1AbstractClientPlayer.prevPosZ + (par1AbstractClientPlayer.posZ - par1AbstractClientPlayer.prevPosZ) * (double)par2);
            var11 = par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2;
            double var12 = (double)MathHelper.sin(var11 * (float)Math.PI / 180.0F);
            double var14 = (double)(-MathHelper.cos(var11 * (float)Math.PI / 180.0F));
            float var16 = (float)var26 * 10.0F;

            if (var16 < -6.0F)
            {
                var16 = -6.0F;
            }

            if (var16 > 32.0F)
            {
                var16 = 32.0F;
            }

            float var17 = (float)(var25 * var12 + var9 * var14) * 100.0F;
            float var18 = (float)(var25 * var14 - var9 * var12) * 100.0F;

            if (var17 < 0.0F)
            {
                var17 = 0.0F;
            }

            float var19 = par1AbstractClientPlayer.prevCameraYaw + (par1AbstractClientPlayer.cameraYaw - par1AbstractClientPlayer.prevCameraYaw) * par2;
            var16 += MathHelper.sin((par1AbstractClientPlayer.prevDistanceWalkedModified + (par1AbstractClientPlayer.distanceWalkedModified - par1AbstractClientPlayer.prevDistanceWalkedModified) * par2) * 6.0F) * 32.0F * var19;

            if (par1AbstractClientPlayer.isSneaking())
            {
                var16 += 25.0F;
            }

            GL11.glRotatef(6.0F + var17 / 2.0F + var16, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var18 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-var18 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            this.modelBipedMain.renderCloak(0.0625F);
            GL11.glPopMatrix();
        }

        ItemStack var23 = par1AbstractClientPlayer.inventory.getCurrentItem();

        if (var23 != null)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            if (par1AbstractClientPlayer.fishEntity != null)
            {
                var23 = new ItemStack(Items.stick);
            }

            EnumAction var24 = null;

            if (par1AbstractClientPlayer.getItemInUseCount() > 0)
            {
                var24 = var23.getItemUseAction();
            }

            if (var23.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var23.getItem()).getRenderType()))
            {
                var7 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                var7 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-var7, -var7, var7);
            }
            else if (var23.getItem() == Items.bow)
            {
                var7 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var7, -var7, var7);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else if (var23.getItem().isFull3D())
            {
                var7 = 0.625F;

                if (var23.getItem().shouldRotateAroundWhenRendering())
                {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                if (par1AbstractClientPlayer.getItemInUseCount() > 0 && var24 == EnumAction.block)
                {
                    GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                }

                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(var7, -var7, var7);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                var7 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(var7, var7, var7);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            float var10;
            float var29;
            int var28;

            if (var23.getItem().requiresMultipleRenderPasses())
            {
                for (var28 = 0; var28 <= 1; ++var28)
                {
                    int var8 = var23.getItem().getColorFromItemStack(var23, var28);
                    var29 = (float)(var8 >> 16 & 255) / 255.0F;
                    var10 = (float)(var8 >> 8 & 255) / 255.0F;
                    var11 = (float)(var8 & 255) / 255.0F;
                    GL11.glColor4f(var29, var10, var11, 1.0F);
                    this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var23, var28);
                }
            }
            else
            {
                var28 = var23.getItem().getColorFromItemStack(var23, 0);
                float var27 = (float)(var28 >> 16 & 255) / 255.0F;
                var29 = (float)(var28 >> 8 & 255) / 255.0F;
                var10 = (float)(var28 & 255) / 255.0F;
                GL11.glColor4f(var27, var29, var10, 1.0F);
                this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var23, 0);
            }

            GL11.glPopMatrix();
        }
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(AbstractClientPlayer par1AbstractClientPlayer, float par2)
    {
        float var3 = 0.9375F;
        GL11.glScalef(var3, var3, var3);
    }

    protected void func_96449_a(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, String par8Str, float par9, double par10)
    {
        if (par10 < 100.0D)
        {
            Scoreboard var12 = par1AbstractClientPlayer.getWorldScoreboard();
            ScoreObjective var13 = var12.func_96539_a(2);

            if (var13 != null)
            {
                Score var14 = var12.func_96529_a(par1AbstractClientPlayer.getCommandSenderName(), var13);

                if (par1AbstractClientPlayer.isPlayerSleeping())
                {
                    this.func_147906_a(par1AbstractClientPlayer, var14.getScorePoints() + " " + var13.getDisplayName(), par2, par4 - 1.5D, par6, 64);
                }
                else
                {
                    this.func_147906_a(par1AbstractClientPlayer, var14.getScorePoints() + " " + var13.getDisplayName(), par2, par4, par6, 64);
                }

                par4 += (double)((float)this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par9);
            }
        }

        super.func_96449_a(par1AbstractClientPlayer, par2, par4, par6, par8Str, par9, par10);
    }

    public void renderFirstPersonArm(EntityPlayer par1EntityPlayer)
    {
        float var2 = 1.0F;
        GL11.glColor3f(var2, var2, var2);
        this.modelBipedMain.onGround = 0.0F;
        this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, par1EntityPlayer);
        this.modelBipedMain.bipedRightArm.render(0.0625F);
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6)
    {
        if (par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping())
        {
            super.renderLivingAt(par1AbstractClientPlayer, par2 + (double)par1AbstractClientPlayer.field_71079_bU, par4 + (double)par1AbstractClientPlayer.field_71082_cx, par6 + (double)par1AbstractClientPlayer.field_71089_bV);
        }
        else
        {
            super.renderLivingAt(par1AbstractClientPlayer, par2, par4, par6);
        }
    }

    protected void rotateCorpse(AbstractClientPlayer par1AbstractClientPlayer, float par2, float par3, float par4)
    {
        if (par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping())
        {
            GL11.glRotatef(par1AbstractClientPlayer.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.getDeathMaxRotation(par1AbstractClientPlayer), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        }
        else
        {
            super.rotateCorpse(par1AbstractClientPlayer, par2, par3, par4);
        }
    }

    protected void func_96449_a(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, String par8Str, float par9, double par10)
    {
        this.func_96449_a((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6, par8Str, par9, par10);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.preRenderCallback((AbstractClientPlayer)par1EntityLivingBase, par2);
    }

    protected void func_82408_c(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        this.func_82408_c((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.shouldRenderPass((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
    }

    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.renderEquippedItems((AbstractClientPlayer)par1EntityLivingBase, par2);
    }

    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
        this.rotateCorpse((AbstractClientPlayer)par1EntityLivingBase, par2, par3, par4);
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6)
    {
        this.renderLivingAt((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((AbstractClientPlayer)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((AbstractClientPlayer)par1Entity, par2, par4, par6, par8, par9);
    }
}
