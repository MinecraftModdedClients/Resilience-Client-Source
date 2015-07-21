package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSnowMan extends RenderLiving
{
    private static final ResourceLocation snowManTextures = new ResourceLocation("textures/entity/snowman.png");

    /** A reference to the Snowman model in RenderSnowMan. */
    private ModelSnowMan snowmanModel;
    private static final String __OBFID = "CL_00001025";

    public RenderSnowMan()
    {
        super(new ModelSnowMan(), 0.5F);
        this.snowmanModel = (ModelSnowMan)super.mainModel;
        this.setRenderPassModel(this.snowmanModel);
    }

    protected void renderEquippedItems(EntitySnowman par1EntitySnowman, float par2)
    {
        super.renderEquippedItems(par1EntitySnowman, par2);
        ItemStack var3 = new ItemStack(Blocks.pumpkin, 1);

        if (var3.getItem() instanceof ItemBlock)
        {
            GL11.glPushMatrix();
            this.snowmanModel.head.postRender(0.0625F);

            if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType()))
            {
                float var4 = 0.625F;
                GL11.glTranslatef(0.0F, -0.34375F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var4, -var4, var4);
            }

            this.renderManager.itemRenderer.renderItem(par1EntitySnowman, var3, 0);
            GL11.glPopMatrix();
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySnowman par1EntitySnowman)
    {
        return snowManTextures;
    }

    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.renderEquippedItems((EntitySnowman)par1EntityLivingBase, par2);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntitySnowman)par1Entity);
    }
}
