package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;

public class OpenGlHelper
{
    public static boolean openGL21;

    /**
     * An OpenGL constant corresponding to GL_TEXTURE0, used when setting data pertaining to auxiliary OpenGL texture
     * units.
     */
    public static int defaultTexUnit;

    /**
     * An OpenGL constant corresponding to GL_TEXTURE1, used when setting data pertaining to auxiliary OpenGL texture
     * units.
     */
    public static int lightmapTexUnit;
    public static boolean anisotropicFilteringSupported;
    public static int anisotropicFilteringMax;

    /**
     * True if the renderer supports multitextures and the OpenGL version != 1.3
     */
    private static boolean useMultitextureARB;
    private static boolean openGL14;
    public static boolean framebufferSupported;
    public static boolean shadersSupported;
    public static float lastBrightnessX = 0.0F;
    public static float lastBrightnessY = 0.0F;
    private static final String __OBFID = "CL_00001179";

    /**
     * Initializes the texture constants to be used when rendering lightmap values
     */
    public static void initializeTextures()
    {
        Config.initDisplay();
        useMultitextureARB = GLContext.getCapabilities().GL_ARB_multitexture && !GLContext.getCapabilities().OpenGL13;

        if (useMultitextureARB)
        {
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
        }
        else
        {
            defaultTexUnit = 33984;
            lightmapTexUnit = 33985;
        }

        openGL14 = GLContext.getCapabilities().OpenGL14;
        framebufferSupported = openGL14 && GLContext.getCapabilities().GL_ARB_framebuffer_object;
        anisotropicFilteringSupported = GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic;
        anisotropicFilteringMax = (int)(anisotropicFilteringSupported ? GL11.glGetFloat(34047) : 0.0F);
        GameSettings.Options.ANISOTROPIC_FILTERING.setValueMax((float)anisotropicFilteringMax);
        openGL21 = GLContext.getCapabilities().OpenGL21;
        shadersSupported = framebufferSupported && openGL21;
    }

    /**
     * Sets the current lightmap texture to the specified OpenGL constant
     */
    public static void setActiveTexture(int par0)
    {
        if (useMultitextureARB)
        {
            ARBMultitexture.glActiveTextureARB(par0);
        }
        else
        {
            GL13.glActiveTexture(par0);
        }
    }

    /**
     * Sets the current lightmap texture to the specified OpenGL constant
     */
    public static void setClientActiveTexture(int par0)
    {
        if (useMultitextureARB)
        {
            ARBMultitexture.glClientActiveTextureARB(par0);
        }
        else
        {
            GL13.glClientActiveTexture(par0);
        }
    }

    /**
     * Sets the current coordinates of the given lightmap texture
     */
    public static void setLightmapTextureCoords(int par0, float par1, float par2)
    {
        if (useMultitextureARB)
        {
            ARBMultitexture.glMultiTexCoord2fARB(par0, par1, par2);
        }
        else
        {
            GL13.glMultiTexCoord2f(par0, par1, par2);
        }

        if (par0 == lightmapTexUnit)
        {
            lastBrightnessX = par1;
            lastBrightnessY = par2;
        }
    }

    public static void glBlendFunc(int p_148821_0_, int p_148821_1_, int p_148821_2_, int p_148821_3_)
    {
        if (openGL14)
        {
            GL14.glBlendFuncSeparate(p_148821_0_, p_148821_1_, p_148821_2_, p_148821_3_);
        }
        else
        {
            GL11.glBlendFunc(p_148821_0_, p_148821_1_);
        }
    }

    public static boolean isFramebufferEnabled()
    {
        return framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable;
    }
}
