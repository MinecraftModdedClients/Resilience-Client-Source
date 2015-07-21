package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public interface IChatComponent extends Iterable
{
    IChatComponent setChatStyle(ChatStyle var1);

    ChatStyle getChatStyle();

    /**
     * Appends the given text to the end of this component.
     */
    IChatComponent appendText(String var1);

    /**
     * Appends the given component to the end of this one.
     */
    IChatComponent appendSibling(IChatComponent var1);

    /**
     * Gets the text of this component, without any special formatting codes added, for chat.  TODO: why is this two
     * different methods?
     */
    String getUnformattedTextForChat();

    /**
     * Gets the text of this component, without any special formatting codes added.  TODO: why is this two different
     * methods?
     */
    String getUnformattedText();

    /**
     * Gets the text of this component, with formatting codes added for rendering.
     */
    String getFormattedText();

    /**
     * Gets the sibling components of this one.
     */
    List getSiblings();

    /**
     * Creates a copy of this component.  Almost a deep copy, except the style is shallow-copied.
     */
    IChatComponent createCopy();

    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final Gson field_150700_a;
        private static final String __OBFID = "CL_00001263";

        public IChatComponent deserialize(JsonElement p_150698_1_, Type p_150698_2_, JsonDeserializationContext p_150698_3_)
        {
            if (p_150698_1_.isJsonPrimitive())
            {
                return new ChatComponentText(p_150698_1_.getAsString());
            }
            else if (!p_150698_1_.isJsonObject())
            {
                if (p_150698_1_.isJsonArray())
                {
                    JsonArray var11 = p_150698_1_.getAsJsonArray();
                    IChatComponent var12 = null;
                    Iterator var13 = var11.iterator();

                    while (var13.hasNext())
                    {
                        JsonElement var16 = (JsonElement)var13.next();
                        IChatComponent var17 = this.deserialize(var16, var16.getClass(), p_150698_3_);

                        if (var12 == null)
                        {
                            var12 = var17;
                        }
                        else
                        {
                            var12.appendSibling(var17);
                        }
                    }

                    return var12;
                }
                else
                {
                    throw new JsonParseException("Don\'t know how to turn " + p_150698_1_.toString() + " into a Component");
                }
            }
            else
            {
                JsonObject var4 = p_150698_1_.getAsJsonObject();
                Object var5;

                if (var4.has("text"))
                {
                    var5 = new ChatComponentText(var4.get("text").getAsString());
                }
                else
                {
                    if (!var4.has("translate"))
                    {
                        throw new JsonParseException("Don\'t know how to turn " + p_150698_1_.toString() + " into a Component");
                    }

                    String var6 = var4.get("translate").getAsString();

                    if (var4.has("with"))
                    {
                        JsonArray var7 = var4.getAsJsonArray("with");
                        Object[] var8 = new Object[var7.size()];

                        for (int var9 = 0; var9 < var8.length; ++var9)
                        {
                            var8[var9] = this.deserialize(var7.get(var9), p_150698_2_, p_150698_3_);

                            if (var8[var9] instanceof ChatComponentText)
                            {
                                ChatComponentText var10 = (ChatComponentText)var8[var9];

                                if (var10.getChatStyle().isEmpty() && var10.getSiblings().isEmpty())
                                {
                                    var8[var9] = var10.getChatComponentText_TextValue();
                                }
                            }
                        }

                        var5 = new ChatComponentTranslation(var6, var8);
                    }
                    else
                    {
                        var5 = new ChatComponentTranslation(var6, new Object[0]);
                    }
                }

                if (var4.has("extra"))
                {
                    JsonArray var14 = var4.getAsJsonArray("extra");

                    if (var14.size() <= 0)
                    {
                        throw new JsonParseException("Unexpected empty array of components");
                    }

                    for (int var15 = 0; var15 < var14.size(); ++var15)
                    {
                        ((IChatComponent)var5).appendSibling(this.deserialize(var14.get(var15), p_150698_2_, p_150698_3_));
                    }
                }

                ((IChatComponent)var5).setChatStyle((ChatStyle)p_150698_3_.deserialize(p_150698_1_, ChatStyle.class));
                return (IChatComponent)var5;
            }
        }

        private void func_150695_a(ChatStyle p_150695_1_, JsonObject p_150695_2_, JsonSerializationContext p_150695_3_)
        {
            JsonElement var4 = p_150695_3_.serialize(p_150695_1_);

            if (var4.isJsonObject())
            {
                JsonObject var5 = (JsonObject)var4;
                Iterator var6 = var5.entrySet().iterator();

                while (var6.hasNext())
                {
                    Entry var7 = (Entry)var6.next();
                    p_150695_2_.add((String)var7.getKey(), (JsonElement)var7.getValue());
                }
            }
        }

        public JsonElement serialize(IChatComponent p_150697_1_, Type p_150697_2_, JsonSerializationContext p_150697_3_)
        {
            if (p_150697_1_ instanceof ChatComponentText && p_150697_1_.getChatStyle().isEmpty() && p_150697_1_.getSiblings().isEmpty())
            {
                return new JsonPrimitive(((ChatComponentText)p_150697_1_).getChatComponentText_TextValue());
            }
            else
            {
                JsonObject var4 = new JsonObject();

                if (!p_150697_1_.getChatStyle().isEmpty())
                {
                    this.func_150695_a(p_150697_1_.getChatStyle(), var4, p_150697_3_);
                }

                if (!p_150697_1_.getSiblings().isEmpty())
                {
                    JsonArray var5 = new JsonArray();
                    Iterator var6 = p_150697_1_.getSiblings().iterator();

                    while (var6.hasNext())
                    {
                        IChatComponent var7 = (IChatComponent)var6.next();
                        var5.add(this.serialize(var7, var7.getClass(), p_150697_3_));
                    }

                    var4.add("extra", var5);
                }

                if (p_150697_1_ instanceof ChatComponentText)
                {
                    var4.addProperty("text", ((ChatComponentText)p_150697_1_).getChatComponentText_TextValue());
                }
                else
                {
                    if (!(p_150697_1_ instanceof ChatComponentTranslation))
                    {
                        throw new IllegalArgumentException("Don\'t know how to serialize " + p_150697_1_ + " as a Component");
                    }

                    ChatComponentTranslation var11 = (ChatComponentTranslation)p_150697_1_;
                    var4.addProperty("translate", var11.getKey());

                    if (var11.getFormatArgs() != null && var11.getFormatArgs().length > 0)
                    {
                        JsonArray var12 = new JsonArray();
                        Object[] var13 = var11.getFormatArgs();
                        int var8 = var13.length;

                        for (int var9 = 0; var9 < var8; ++var9)
                        {
                            Object var10 = var13[var9];

                            if (var10 instanceof IChatComponent)
                            {
                                var12.add(this.serialize((IChatComponent)var10, var10.getClass(), p_150697_3_));
                            }
                            else
                            {
                                var12.add(new JsonPrimitive(String.valueOf(var10)));
                            }
                        }

                        var4.add("with", var12);
                    }
                }

                return var4;
            }
        }

        public static String func_150696_a(IChatComponent p_150696_0_)
        {
            return field_150700_a.toJson(p_150696_0_);
        }

        public static IChatComponent func_150699_a(String p_150699_0_)
        {
            return (IChatComponent)field_150700_a.fromJson(p_150699_0_, IChatComponent.class);
        }

        public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
        {
            return this.serialize((IChatComponent)par1Obj, par2Type, par3JsonSerializationContext);
        }

        static
        {
            GsonBuilder var0 = new GsonBuilder();
            var0.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer());
            var0.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
            var0.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
            field_150700_a = var0.create();
        }
    }
}
