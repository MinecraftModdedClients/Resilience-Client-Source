package net.minecraft.client.particle;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class EntityFootStepFX extends EntityFX
{
    private static final ResourceLocation field_110126_a = new ResourceLocation("textures/particle/footprint.png");
    private int footstepAge;
    private int footstepMaxAge;
    private TextureManager currentFootSteps;
    private static final String __OBFID = "CL_00000908";

    public EntityFootStepFX(TextureManager par1TextureManager, World par2World, double par3, double par5, double par7)
    {
        super(par2World, par3, par5, par7, 0.0D, 0.0D, 0.0D);
        this.currentFootSteps = par1TextureManager;
        this.motionX = this.motionY = this.motionZ = 0.0D;
        this.footstepMaxAge = 200;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float var8 = ((float)this.footstepAge + par2) / (float)this.footstepMaxAge;
        var8 *= var8;
        float var9 = 2.0F - var8 * 2.0F;

        if (var9 > 1.0F)
        {
            var9 = 1.0F;
        }

        var9 *= 0.2F;
        GL11.glDisable(GL11.GL_LIGHTING);
        float var10 = 0.125F;
        float var11 = (float)(this.posX - interpPosX);
        float var12 = (float)(this.posY - interpPosY);
        float var13 = (float)(this.posZ - interpPosZ);
        float var14 = this.worldObj.getLightBrightness(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
        this.currentFootSteps.bindTexture(field_110126_a);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setColorRGBA_F(var14, var14, var14, var9);
        par1Tessellator.addVertexWithUV((double)(var11 - var10), (double)var12, (double)(var13 + var10), 0.0D, 1.0D);
        par1Tessellator.addVertexWithUV((double)(var11 + var10), (double)var12, (double)(var13 + var10), 1.0D, 1.0D);
        par1Tessellator.addVertexWithUV((double)(var11 + var10), (double)var12, (double)(var13 - var10), 1.0D, 0.0D);
        par1Tessellator.addVertexWithUV((double)(var11 - var10), (double)var12, (double)(var13 - var10), 0.0D, 0.0D);
        par1Tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        ++this.footstepAge;

        if (this.footstepAge == this.footstepMaxAge)
        {
            this.setDead();
        }
    }

    public int getFXLayer()
    {
        return 3;
    }
}
