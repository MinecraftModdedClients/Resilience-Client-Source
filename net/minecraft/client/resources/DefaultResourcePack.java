package net.minecraft.client.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class DefaultResourcePack implements IResourcePack
{
    public static final Set defaultResourceDomains = ImmutableSet.of("minecraft");
    private final Map mapResourceFiles = Maps.newHashMap();
    private final File fileAssets;
    private static final String __OBFID = "CL_00001073";

    public DefaultResourcePack(File par1File)
    {
        this.fileAssets = par1File;
        this.readAssetsDir(this.fileAssets);
    }

    public InputStream getInputStream(ResourceLocation par1ResourceLocation) throws IOException
    {
        InputStream var2 = this.getResourceStream(par1ResourceLocation);

        if (var2 != null)
        {
            return var2;
        }
        else
        {
            File var3 = (File)this.mapResourceFiles.get(par1ResourceLocation.toString());

            if (var3 != null)
            {
                return new FileInputStream(var3);
            }
            else
            {
                throw new FileNotFoundException(par1ResourceLocation.getResourcePath());
            }
        }
    }

    private InputStream getResourceStream(ResourceLocation par1ResourceLocation)
    {
        return DefaultResourcePack.class.getResourceAsStream("/assets/minecraft/" + par1ResourceLocation.getResourcePath());
    }

    public void addResourceFile(String par1Str, File par2File)
    {
        this.mapResourceFiles.put((new ResourceLocation(par1Str)).toString(), par2File);
    }

    public boolean resourceExists(ResourceLocation par1ResourceLocation)
    {
        return this.getResourceStream(par1ResourceLocation) != null || this.mapResourceFiles.containsKey(par1ResourceLocation.toString());
    }

    public Set getResourceDomains()
    {
        return defaultResourceDomains;
    }

    public void readAssetsDir(File par1File)
    {
        if (par1File.isDirectory())
        {
            File[] var2 = par1File.listFiles();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                File var5 = var2[var4];
                this.readAssetsDir(var5);
            }
        }
        else
        {
            this.addResourceFile(AbstractResourcePack.getRelativeName(this.fileAssets, par1File), par1File);
        }
    }

    public IMetadataSection getPackMetadata(IMetadataSerializer par1MetadataSerializer, String par2Str) throws IOException
    {
        try
        {
            return AbstractResourcePack.readMetadata(par1MetadataSerializer, new FileInputStream(new File(this.fileAssets, "pack.mcmeta")), par2Str);
        }
        catch (FileNotFoundException var4)
        {
            return null;
        }
    }

    public BufferedImage getPackImage() throws IOException
    {
        return ImageIO.read(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath()));
    }

    public String getPackName()
    {
        return "Default";
    }
}
