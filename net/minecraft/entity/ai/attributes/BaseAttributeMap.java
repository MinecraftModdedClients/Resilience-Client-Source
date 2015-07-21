package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.management.LowerStringMap;

public abstract class BaseAttributeMap
{
    protected final Map attributes = new HashMap();
    protected final Map attributesByName = new LowerStringMap();
    private static final String __OBFID = "CL_00001566";

    public IAttributeInstance getAttributeInstance(IAttribute par1Attribute)
    {
        return (IAttributeInstance)this.attributes.get(par1Attribute);
    }

    public IAttributeInstance getAttributeInstanceByName(String par1Str)
    {
        return (IAttributeInstance)this.attributesByName.get(par1Str);
    }

    /**
     * Registers an attribute with this AttributeMap, returns a modifiable AttributeInstance associated with this map
     */
    public abstract IAttributeInstance registerAttribute(IAttribute var1);

    public Collection getAllAttributes()
    {
        return this.attributesByName.values();
    }

    public void addAttributeInstance(ModifiableAttributeInstance par1ModifiableAttributeInstance) {}

    public void removeAttributeModifiers(Multimap par1Multimap)
    {
        Iterator var2 = par1Multimap.entries().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();
            IAttributeInstance var4 = this.getAttributeInstanceByName((String)var3.getKey());

            if (var4 != null)
            {
                var4.removeModifier((AttributeModifier)var3.getValue());
            }
        }
    }

    public void applyAttributeModifiers(Multimap par1Multimap)
    {
        Iterator var2 = par1Multimap.entries().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();
            IAttributeInstance var4 = this.getAttributeInstanceByName((String)var3.getKey());

            if (var4 != null)
            {
                var4.removeModifier((AttributeModifier)var3.getValue());
                var4.applyModifier((AttributeModifier)var3.getValue());
            }
        }
    }
}
