package net.minecraft.client.resources.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;

public class IMetadataSerializer
{
    private final IRegistry metadataSectionSerializerRegistry = new RegistrySimple();
    private final GsonBuilder gsonBuilder = new GsonBuilder();

    /**
     * Cached Gson instance. Set to null when more sections are registered, and then re-created from the builder.
     */
    private Gson gson;
    private static final String __OBFID = "CL_00001101";

    public void registerMetadataSectionType(IMetadataSectionSerializer par1MetadataSectionSerializer, Class par2Class)
    {
        this.metadataSectionSerializerRegistry.putObject(par1MetadataSectionSerializer.getSectionName(), new IMetadataSerializer.Registration(par1MetadataSectionSerializer, par2Class, null));
        this.gsonBuilder.registerTypeAdapter(par2Class, par1MetadataSectionSerializer);
        this.gson = null;
    }

    public IMetadataSection parseMetadataSection(String par1Str, JsonObject par2JsonObject)
    {
        if (par1Str == null)
        {
            throw new IllegalArgumentException("Metadata section name cannot be null");
        }
        else if (!par2JsonObject.has(par1Str))
        {
            return null;
        }
        else if (!par2JsonObject.get(par1Str).isJsonObject())
        {
            throw new IllegalArgumentException("Invalid metadata for \'" + par1Str + "\' - expected object, found " + par2JsonObject.get(par1Str));
        }
        else
        {
            IMetadataSerializer.Registration var3 = (IMetadataSerializer.Registration)this.metadataSectionSerializerRegistry.getObject(par1Str);

            if (var3 == null)
            {
                throw new IllegalArgumentException("Don\'t know how to handle metadata section \'" + par1Str + "\'");
            }
            else
            {
                return (IMetadataSection)this.getGson().fromJson(par2JsonObject.getAsJsonObject(par1Str), var3.field_110500_b);
            }
        }
    }

    /**
     * Returns a Gson instance with type adapters registered for metadata sections.
     */
    private Gson getGson()
    {
        if (this.gson == null)
        {
            this.gson = this.gsonBuilder.create();
        }

        return this.gson;
    }

    class Registration
    {
        final IMetadataSectionSerializer field_110502_a;
        final Class field_110500_b;
        private static final String __OBFID = "CL_00001103";

        private Registration(IMetadataSectionSerializer par2MetadataSectionSerializer, Class par3Class)
        {
            this.field_110502_a = par2MetadataSectionSerializer;
            this.field_110500_b = par3Class;
        }

        Registration(IMetadataSectionSerializer par2MetadataSectionSerializer, Class par3Class, Object par4MetadataSerializerEmptyAnon)
        {
            this(par2MetadataSectionSerializer, par3Class);
        }
    }
}
