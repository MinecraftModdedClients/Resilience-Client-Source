package net.minecraft.util;

public class Tuple
{
    /** First Object in the Tuple */
    private Object first;

    /** Second Object in the Tuple */
    private Object second;
    private static final String __OBFID = "CL_00001502";

    public Tuple(Object par1Obj, Object par2Obj)
    {
        this.first = par1Obj;
        this.second = par2Obj;
    }

    /**
     * Get the first Object in the Tuple
     */
    public Object getFirst()
    {
        return this.first;
    }

    /**
     * Get the second Object in the Tuple
     */
    public Object getSecond()
    {
        return this.second;
    }
}
