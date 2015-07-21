package net.minecraft.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SharedMonsterAttributes
{
    private static final Logger logger = LogManager.getLogger();
    public static final IAttribute maxHealth = (new RangedAttribute("generic.maxHealth", 20.0D, 0.0D, Double.MAX_VALUE)).setDescription("Max Health").setShouldWatch(true);
    public static final IAttribute followRange = (new RangedAttribute("generic.followRange", 32.0D, 0.0D, 2048.0D)).setDescription("Follow Range");
    public static final IAttribute knockbackResistance = (new RangedAttribute("generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).setDescription("Knockback Resistance");
    public static final IAttribute movementSpeed = (new RangedAttribute("generic.movementSpeed", 0.699999988079071D, 0.0D, Double.MAX_VALUE)).setDescription("Movement Speed").setShouldWatch(true);
    public static final IAttribute attackDamage = new RangedAttribute("generic.attackDamage", 2.0D, 0.0D, Double.MAX_VALUE);
    private static final String __OBFID = "CL_00001695";

    /**
     * Creates an NBTTagList from a BaseAttributeMap, including all its AttributeInstances
     */
    public static NBTTagList writeBaseAttributeMapToNBT(BaseAttributeMap par0BaseAttributeMap)
    {
        NBTTagList var1 = new NBTTagList();
        Iterator var2 = par0BaseAttributeMap.getAllAttributes().iterator();

        while (var2.hasNext())
        {
            IAttributeInstance var3 = (IAttributeInstance)var2.next();
            var1.appendTag(writeAttributeInstanceToNBT(var3));
        }

        return var1;
    }

    /**
     * Creates an NBTTagCompound from an AttributeInstance, including its AttributeModifiers
     */
    private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance par0AttributeInstance)
    {
        NBTTagCompound var1 = new NBTTagCompound();
        IAttribute var2 = par0AttributeInstance.getAttribute();
        var1.setString("Name", var2.getAttributeUnlocalizedName());
        var1.setDouble("Base", par0AttributeInstance.getBaseValue());
        Collection var3 = par0AttributeInstance.func_111122_c();

        if (var3 != null && !var3.isEmpty())
        {
            NBTTagList var4 = new NBTTagList();
            Iterator var5 = var3.iterator();

            while (var5.hasNext())
            {
                AttributeModifier var6 = (AttributeModifier)var5.next();

                if (var6.isSaved())
                {
                    var4.appendTag(writeAttributeModifierToNBT(var6));
                }
            }

            var1.setTag("Modifiers", var4);
        }

        return var1;
    }

    /**
     * Creates an NBTTagCompound from an AttributeModifier
     */
    private static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier par0AttributeModifier)
    {
        NBTTagCompound var1 = new NBTTagCompound();
        var1.setString("Name", par0AttributeModifier.getName());
        var1.setDouble("Amount", par0AttributeModifier.getAmount());
        var1.setInteger("Operation", par0AttributeModifier.getOperation());
        var1.setLong("UUIDMost", par0AttributeModifier.getID().getMostSignificantBits());
        var1.setLong("UUIDLeast", par0AttributeModifier.getID().getLeastSignificantBits());
        return var1;
    }

    public static void func_151475_a(BaseAttributeMap p_151475_0_, NBTTagList p_151475_1_)
    {
        for (int var2 = 0; var2 < p_151475_1_.tagCount(); ++var2)
        {
            NBTTagCompound var3 = p_151475_1_.getCompoundTagAt(var2);
            IAttributeInstance var4 = p_151475_0_.getAttributeInstanceByName(var3.getString("Name"));

            if (var4 != null)
            {
                applyModifiersToAttributeInstance(var4, var3);
            }
            else
            {
                logger.warn("Ignoring unknown attribute \'" + var3.getString("Name") + "\'");
            }
        }
    }

    private static void applyModifiersToAttributeInstance(IAttributeInstance par0AttributeInstance, NBTTagCompound par1NBTTagCompound)
    {
        par0AttributeInstance.setBaseValue(par1NBTTagCompound.getDouble("Base"));

        if (par1NBTTagCompound.func_150297_b("Modifiers", 9))
        {
            NBTTagList var2 = par1NBTTagCompound.getTagList("Modifiers", 10);

            for (int var3 = 0; var3 < var2.tagCount(); ++var3)
            {
                AttributeModifier var4 = readAttributeModifierFromNBT(var2.getCompoundTagAt(var3));
                AttributeModifier var5 = par0AttributeInstance.getModifier(var4.getID());

                if (var5 != null)
                {
                    par0AttributeInstance.removeModifier(var5);
                }

                par0AttributeInstance.applyModifier(var4);
            }
        }
    }

    /**
     * Creates an AttributeModifier from an NBTTagCompound
     */
    public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound par0NBTTagCompound)
    {
        UUID var1 = new UUID(par0NBTTagCompound.getLong("UUIDMost"), par0NBTTagCompound.getLong("UUIDLeast"));
        return new AttributeModifier(var1, par0NBTTagCompound.getString("Name"), par0NBTTagCompound.getDouble("Amount"), par0NBTTagCompound.getInteger("Operation"));
    }
}
