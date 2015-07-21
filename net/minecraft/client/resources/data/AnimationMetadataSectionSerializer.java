package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer
{
    private static final String __OBFID = "CL_00001107";

    public AnimationMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
    {
        ArrayList var4 = Lists.newArrayList();
        JsonObject var5 = JsonUtils.getJsonElementAsJsonObject(par1JsonElement, "metadata section");
        int var6 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "frametime", 1);

        if (var6 != 1)
        {
            Validate.inclusiveBetween(Integer.valueOf(1), Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(var6), "Invalid default frame time", new Object[0]);
        }

        int var8;

        if (var5.has("frames"))
        {
            try
            {
                JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var5, "frames");

                for (var8 = 0; var8 < var7.size(); ++var8)
                {
                    JsonElement var9 = var7.get(var8);
                    AnimationFrame var10 = this.parseAnimationFrame(var8, var9);

                    if (var10 != null)
                    {
                        var4.add(var10);
                    }
                }
            }
            catch (ClassCastException var11)
            {
                throw new JsonParseException("Invalid animation->frames: expected array, was " + var5.get("frames"), var11);
            }
        }

        int var12 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "width", -1);
        var8 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "height", -1);

        if (var12 != -1)
        {
            Validate.inclusiveBetween(Integer.valueOf(1), Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(var12), "Invalid width", new Object[0]);
        }

        if (var8 != -1)
        {
            Validate.inclusiveBetween(Integer.valueOf(1), Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(var8), "Invalid height", new Object[0]);
        }

        return new AnimationMetadataSection(var4, var12, var8, var6);
    }

    private AnimationFrame parseAnimationFrame(int par1, JsonElement par2JsonElement)
    {
        if (par2JsonElement.isJsonPrimitive())
        {
            return new AnimationFrame(JsonUtils.getJsonElementIntegerValue(par2JsonElement, "frames[" + par1 + "]"));
        }
        else if (par2JsonElement.isJsonObject())
        {
            JsonObject var3 = JsonUtils.getJsonElementAsJsonObject(par2JsonElement, "frames[" + par1 + "]");
            int var4 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var3, "time", -1);

            if (var3.has("time"))
            {
                Validate.inclusiveBetween(Integer.valueOf(1), Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(var4), "Invalid frame time", new Object[0]);
            }

            int var5 = JsonUtils.getJsonObjectIntegerFieldValue(var3, "index");
            Validate.inclusiveBetween(Integer.valueOf(0), Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(var5), "Invalid frame index", new Object[0]);
            return new AnimationFrame(var5, var4);
        }
        else
        {
            return null;
        }
    }

    public JsonElement serialize(AnimationMetadataSection par1AnimationMetadataSection, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
    {
        JsonObject var4 = new JsonObject();
        var4.addProperty("frametime", Integer.valueOf(par1AnimationMetadataSection.getFrameTime()));

        if (par1AnimationMetadataSection.getFrameWidth() != -1)
        {
            var4.addProperty("width", Integer.valueOf(par1AnimationMetadataSection.getFrameWidth()));
        }

        if (par1AnimationMetadataSection.getFrameHeight() != -1)
        {
            var4.addProperty("height", Integer.valueOf(par1AnimationMetadataSection.getFrameHeight()));
        }

        if (par1AnimationMetadataSection.getFrameCount() > 0)
        {
            JsonArray var5 = new JsonArray();

            for (int var6 = 0; var6 < par1AnimationMetadataSection.getFrameCount(); ++var6)
            {
                if (par1AnimationMetadataSection.frameHasTime(var6))
                {
                    JsonObject var7 = new JsonObject();
                    var7.addProperty("index", Integer.valueOf(par1AnimationMetadataSection.getFrameIndex(var6)));
                    var7.addProperty("time", Integer.valueOf(par1AnimationMetadataSection.getFrameTimeSingle(var6)));
                    var5.add(var7);
                }
                else
                {
                    var5.add(new JsonPrimitive(Integer.valueOf(par1AnimationMetadataSection.getFrameIndex(var6))));
                }
            }

            var4.add("frames", var5);
        }

        return var4;
    }

    /**
     * The name of this section type as it appears in JSON.
     */
    public String getSectionName()
    {
        return "animation";
    }

    public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
    {
        return this.serialize((AnimationMetadataSection)par1Obj, par2Type, par3JsonSerializationContext);
    }
}
