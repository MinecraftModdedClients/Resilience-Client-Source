package net.minecraft.client.resources;

import java.io.File;
import java.io.FileNotFoundException;

public class ResourcePackFileNotFoundException extends FileNotFoundException
{
    private static final String __OBFID = "CL_00001086";

    public ResourcePackFileNotFoundException(File par1File, String par2Str)
    {
        super(String.format("\'%s\' in ResourcePack \'%s\'", new Object[] {par2Str, par1File}));
    }
}
