package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute
{
    private final String unlocalizedName;
    private final double defaultValue;
    private boolean shouldWatch;
    private static final String __OBFID = "CL_00001565";

    protected BaseAttribute(String par1Str, double par2)
    {
        this.unlocalizedName = par1Str;
        this.defaultValue = par2;

        if (par1Str == null)
        {
            throw new IllegalArgumentException("Name cannot be null!");
        }
    }

    public String getAttributeUnlocalizedName()
    {
        return this.unlocalizedName;
    }

    public double getDefaultValue()
    {
        return this.defaultValue;
    }

    public boolean getShouldWatch()
    {
        return this.shouldWatch;
    }

    public BaseAttribute setShouldWatch(boolean par1)
    {
        this.shouldWatch = par1;
        return this;
    }

    public int hashCode()
    {
        return this.unlocalizedName.hashCode();
    }
}
