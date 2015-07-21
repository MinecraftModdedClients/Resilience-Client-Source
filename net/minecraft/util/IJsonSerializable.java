package net.minecraft.util;

import com.google.gson.JsonElement;

public interface IJsonSerializable
{
    /**
     * Gets the JsonElement that can be serialized.
     */
    JsonElement getSerializableElement();
}
