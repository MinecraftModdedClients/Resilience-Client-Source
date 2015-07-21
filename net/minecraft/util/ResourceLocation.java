package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class ResourceLocation
{
    private final String resourceDomain;
    private final String resourcePath;
    private static final String __OBFID = "CL_00001082";

    public ResourceLocation(String par1Str, String par2Str)
    {
        Validate.notNull(par2Str);

        if (par1Str != null && par1Str.length() != 0)
        {
            this.resourceDomain = par1Str;
        }
        else
        {
            this.resourceDomain = "minecraft";
        }

        this.resourcePath = par2Str;
    }

    public ResourceLocation(String par1Str)
    {
        String var2 = "minecraft";
        String var3 = par1Str;
        int var4 = par1Str.indexOf(58);

        if (var4 >= 0)
        {
            var3 = par1Str.substring(var4 + 1, par1Str.length());

            if (var4 > 1)
            {
                var2 = par1Str.substring(0, var4);
            }
        }

        this.resourceDomain = var2.toLowerCase();
        this.resourcePath = var3;
    }

    public String getResourcePath()
    {
        return this.resourcePath;
    }

    public String getResourceDomain()
    {
        return this.resourceDomain;
    }

    public String toString()
    {
        return this.resourceDomain + ":" + this.resourcePath;
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }
        else if (!(par1Obj instanceof ResourceLocation))
        {
            return false;
        }
        else
        {
            ResourceLocation var2 = (ResourceLocation)par1Obj;
            return this.resourceDomain.equals(var2.resourceDomain) && this.resourcePath.equals(var2.resourcePath);
        }
    }

    public int hashCode()
    {
        return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
    }
}
