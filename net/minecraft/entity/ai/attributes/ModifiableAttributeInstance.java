package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ModifiableAttributeInstance implements IAttributeInstance
{
    /** The BaseAttributeMap this attributeInstance can be found in */
    private final BaseAttributeMap attributeMap;

    /** The Attribute this is an instance of */
    private final IAttribute genericAttribute;
    private final Map mapByOperation = Maps.newHashMap();
    private final Map mapByName = Maps.newHashMap();
    private final Map mapByUUID = Maps.newHashMap();
    private double baseValue;
    private boolean needsUpdate = true;
    private double cachedValue;
    private static final String __OBFID = "CL_00001567";

    public ModifiableAttributeInstance(BaseAttributeMap par1BaseAttributeMap, IAttribute par2Attribute)
    {
        this.attributeMap = par1BaseAttributeMap;
        this.genericAttribute = par2Attribute;
        this.baseValue = par2Attribute.getDefaultValue();

        for (int var3 = 0; var3 < 3; ++var3)
        {
            this.mapByOperation.put(Integer.valueOf(var3), new HashSet());
        }
    }

    /**
     * Get the Attribute this is an instance of
     */
    public IAttribute getAttribute()
    {
        return this.genericAttribute;
    }

    public double getBaseValue()
    {
        return this.baseValue;
    }

    public void setBaseValue(double par1)
    {
        if (par1 != this.getBaseValue())
        {
            this.baseValue = par1;
            this.flagForUpdate();
        }
    }

    public Collection getModifiersByOperation(int par1)
    {
        return (Collection)this.mapByOperation.get(Integer.valueOf(par1));
    }

    public Collection func_111122_c()
    {
        HashSet var1 = new HashSet();

        for (int var2 = 0; var2 < 3; ++var2)
        {
            var1.addAll(this.getModifiersByOperation(var2));
        }

        return var1;
    }

    /**
     * Returns attribute modifier, if any, by the given UUID
     */
    public AttributeModifier getModifier(UUID par1UUID)
    {
        return (AttributeModifier)this.mapByUUID.get(par1UUID);
    }

    public void applyModifier(AttributeModifier par1AttributeModifier)
    {
        if (this.getModifier(par1AttributeModifier.getID()) != null)
        {
            throw new IllegalArgumentException("Modifier is already applied on this attribute!");
        }
        else
        {
            Object var2 = (Set)this.mapByName.get(par1AttributeModifier.getName());

            if (var2 == null)
            {
                var2 = new HashSet();
                this.mapByName.put(par1AttributeModifier.getName(), var2);
            }

            ((Set)this.mapByOperation.get(Integer.valueOf(par1AttributeModifier.getOperation()))).add(par1AttributeModifier);
            ((Set)var2).add(par1AttributeModifier);
            this.mapByUUID.put(par1AttributeModifier.getID(), par1AttributeModifier);
            this.flagForUpdate();
        }
    }

    private void flagForUpdate()
    {
        this.needsUpdate = true;
        this.attributeMap.addAttributeInstance(this);
    }

    public void removeModifier(AttributeModifier par1AttributeModifier)
    {
        for (int var2 = 0; var2 < 3; ++var2)
        {
            Set var3 = (Set)this.mapByOperation.get(Integer.valueOf(var2));
            var3.remove(par1AttributeModifier);
        }

        Set var4 = (Set)this.mapByName.get(par1AttributeModifier.getName());

        if (var4 != null)
        {
            var4.remove(par1AttributeModifier);

            if (var4.isEmpty())
            {
                this.mapByName.remove(par1AttributeModifier.getName());
            }
        }

        this.mapByUUID.remove(par1AttributeModifier.getID());
        this.flagForUpdate();
    }

    public void removeAllModifiers()
    {
        Collection var1 = this.func_111122_c();

        if (var1 != null)
        {
            ArrayList var4 = new ArrayList(var1);
            Iterator var2 = var4.iterator();

            while (var2.hasNext())
            {
                AttributeModifier var3 = (AttributeModifier)var2.next();
                this.removeModifier(var3);
            }
        }
    }

    public double getAttributeValue()
    {
        if (this.needsUpdate)
        {
            this.cachedValue = this.computeValue();
            this.needsUpdate = false;
        }

        return this.cachedValue;
    }

    private double computeValue()
    {
        double var1 = this.getBaseValue();
        AttributeModifier var4;

        for (Iterator var3 = this.getModifiersByOperation(0).iterator(); var3.hasNext(); var1 += var4.getAmount())
        {
            var4 = (AttributeModifier)var3.next();
        }

        double var7 = var1;
        Iterator var5;
        AttributeModifier var6;

        for (var5 = this.getModifiersByOperation(1).iterator(); var5.hasNext(); var7 += var1 * var6.getAmount())
        {
            var6 = (AttributeModifier)var5.next();
        }

        for (var5 = this.getModifiersByOperation(2).iterator(); var5.hasNext(); var7 *= 1.0D + var6.getAmount())
        {
            var6 = (AttributeModifier)var5.next();
        }

        return this.genericAttribute.clampValue(var7);
    }
}
