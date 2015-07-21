package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractResourcePack implements IResourcePack
{
    private static final Logger resourceLog = LogManager.getLogger();
    public final File resourcePackFile;
    private static final String __OBFID = "CL_00001072";

    public AbstractResourcePack(File par1File)
    {
        this.resourcePackFile = par1File;
    }

    private static String locationToName(ResourceLocation par0ResourceLocation)
    {
        return String.format("%s/%s/%s", new Object[] {"assets", par0ResourceLocation.getResourceDomain(), par0ResourceLocation.getResourcePath()});
    }

    protected static String getRelativeName(File par0File, File par1File)
    {
        return par0File.toURI().relativize(par1File.toURI()).getPath();
    }

    public InputStream getInputStream(ResourceLocation par1ResourceLocation) throws IOException
    {
        return this.getInputStreamByName(locationToName(par1ResourceLocation));
    }

    public boolean resourceExists(ResourceLocation par1ResourceLocation)
    {
        return this.hasResourceName(locationToName(par1ResourceLocation));
    }

    protected abstract InputStream getInputStreamByName(String var1) throws IOException;

    protected abstract boolean hasResourceName(String var1);

    protected void logNameNotLowercase(String par1Str)
    {
        resourceLog.warn("ResourcePack: ignored non-lowercase namespace: %s in %s", new Object[] {par1Str, this.resourcePackFile});
    }

    public IMetadataSection getPackMetadata(IMetadataSerializer par1MetadataSerializer, String par2Str) throws IOException
    {
        return readMetadata(par1MetadataSerializer, this.getInputStreamByName("pack.mcmeta"), par2Str);
    }

    static IMetadataSection readMetadata(IMetadataSerializer par0MetadataSerializer, InputStream par1InputStream, String par2Str)
    {
        JsonObject var3 = null;
        BufferedReader var4 = null;

        try
        {
            var4 = new BufferedReader(new InputStreamReader(par1InputStream, Charsets.UTF_8));
            var3 = (new JsonParser()).parse(var4).getAsJsonObject();
        }
        finally
        {
            IOUtils.closeQuietly(var4);
        }

        return par0MetadataSerializer.parseMetadataSection(par2Str, var3);
    }

    public BufferedImage getPackImage() throws IOException
    {
        return ImageIO.read(this.getInputStreamByName("pack.png"));
    }

    public String getPackName()
    {
        return this.resourcePackFile.getName();
    }
}
