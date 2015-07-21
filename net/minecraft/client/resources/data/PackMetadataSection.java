package net.minecraft.client.resources.data;

public class PackMetadataSection implements IMetadataSection
{
    private final String packDescription;
    private final int packFormat;
    private static final String __OBFID = "CL_00001112";

    public PackMetadataSection(String par1Str, int par2)
    {
        this.packDescription = par1Str;
        this.packFormat = par2;
    }

    public String getPackDescription()
    {
        return this.packDescription;
    }

    public int getPackFormat()
    {
        return this.packFormat;
    }
}
