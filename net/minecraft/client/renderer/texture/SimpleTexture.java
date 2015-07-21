package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture extends AbstractTexture
{
    private static final Logger logger = LogManager.getLogger();
    protected final ResourceLocation textureLocation;
    private static final String __OBFID = "CL_00001052";

    public SimpleTexture(ResourceLocation par1ResourceLocation)
    {
        this.textureLocation = par1ResourceLocation;
    }

    public void loadTexture(IResourceManager par1ResourceManager) throws IOException
    {
        this.func_147631_c();
        InputStream var2 = null;

        try
        {
            IResource var3 = par1ResourceManager.getResource(this.textureLocation);
            var2 = var3.getInputStream();
            BufferedImage var4 = ImageIO.read(var2);
            boolean var5 = false;
            boolean var6 = false;

            if (var3.hasMetadata())
            {
                try
                {
                    TextureMetadataSection var7 = (TextureMetadataSection)var3.getMetadata("texture");

                    if (var7 != null)
                    {
                        var5 = var7.getTextureBlur();
                        var6 = var7.getTextureClamp();
                    }
                }
                catch (RuntimeException var11)
                {
                    logger.warn("Failed reading metadata of: " + this.textureLocation, var11);
                }
            }

            TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), var4, var5, var6);
        }
        finally
        {
            if (var2 != null)
            {
                var2.close();
            }
        }
    }
}
