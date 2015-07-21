package net.minecraft.client.shader;

import net.minecraft.client.util.JsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL20;

public class ShaderLinkHelper
{
    private static final Logger logger = LogManager.getLogger();
    private static ShaderLinkHelper staticShaderLinkHelper;
    private static final String __OBFID = "CL_00001045";

    public static void setNewStaticShaderLinkHelper()
    {
        staticShaderLinkHelper = new ShaderLinkHelper();
    }

    public static ShaderLinkHelper getStaticShaderLinkHelper()
    {
        return staticShaderLinkHelper;
    }

    public void func_148077_a(ShaderManager p_148077_1_)
    {
        p_148077_1_.func_147994_f().func_148054_b(p_148077_1_);
        p_148077_1_.func_147989_e().func_148054_b(p_148077_1_);
        GL20.glDeleteProgram(p_148077_1_.func_147986_h());
    }

    public int func_148078_c() throws JsonException
    {
        int var1 = GL20.glCreateProgram();

        if (var1 <= 0)
        {
            throw new JsonException("Could not create shader program (returned program ID " + var1 + ")");
        }
        else
        {
            return var1;
        }
    }

    public void func_148075_b(ShaderManager p_148075_1_)
    {
        p_148075_1_.func_147994_f().func_148056_a(p_148075_1_);
        p_148075_1_.func_147989_e().func_148056_a(p_148075_1_);
        GL20.glLinkProgram(p_148075_1_.func_147986_h());
        int var2 = GL20.glGetProgrami(p_148075_1_.func_147986_h(), 35714);

        if (var2 == 0)
        {
            logger.warn("Error encountered when linking program containing VS " + p_148075_1_.func_147989_e().func_148055_a() + " and FS " + p_148075_1_.func_147994_f().func_148055_a() + ". Log output:");
            logger.warn(GL20.glGetProgramInfoLog(p_148075_1_.func_147986_h(), 32768));
        }
    }
}
