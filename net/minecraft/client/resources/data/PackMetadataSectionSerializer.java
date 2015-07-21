package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;

public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer
{
    private static final String __OBFID = "CL_00001113";

    public PackMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
    {
        JsonObject var4 = par1JsonElement.getAsJsonObject();
        String var5 = JsonUtils.getJsonObjectStringFieldValue(var4, "description");
        int var6 = JsonUtils.getJsonObjectIntegerFieldValue(var4, "pack_format");
        return new PackMetadataSection(var5, var6);
    }

    public JsonElement serialize(PackMetadataSection par1PackMetadataSection, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
    {
        JsonObject var4 = new JsonObject();
        var4.addProperty("pack_format", Integer.valueOf(par1PackMetadataSection.getPackFormat()));
        var4.addProperty("description", par1PackMetadataSection.getPackDescription());
        return var4;
    }

    /**
     * The name of this section type as it appears in JSON.
     */
    public String getSectionName()
    {
        return "pack";
    }

    public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
    {
        return this.serialize((PackMetadataSection)par1Obj, par2Type, par3JsonSerializationContext);
    }
}
