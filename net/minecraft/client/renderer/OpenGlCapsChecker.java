package net.minecraft.client.renderer;

import org.lwjgl.opengl.GLContext;

public class OpenGlCapsChecker
{
    private static final String __OBFID = "CL_00000649";

    /**
     * Checks if we support OpenGL occlusion.
     */
    public static boolean checkARBOcclusion()
    {
        return GLContext.getCapabilities().GL_ARB_occlusion_query;
    }
}
