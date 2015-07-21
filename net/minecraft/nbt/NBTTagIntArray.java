package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase
{
    /** The array of saved integers */
    private int[] intArray;
    private static final String __OBFID = "CL_00001221";

    NBTTagIntArray() {}

    public NBTTagIntArray(int[] p_i45132_1_)
    {
        this.intArray = p_i45132_1_;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeInt(this.intArray.length);

        for (int var2 = 0; var2 < this.intArray.length; ++var2)
        {
            par1DataOutput.writeInt(this.intArray[var2]);
        }
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput, int par2) throws IOException
    {
        int var3 = par1DataInput.readInt();
        this.intArray = new int[var3];

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.intArray[var4] = par1DataInput.readInt();
        }
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getId()
    {
        return (byte)11;
    }

    public String toString()
    {
        String var1 = "[";
        int[] var2 = this.intArray;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            int var5 = var2[var4];
            var1 = var1 + var5 + ",";
        }

        return var1 + "]";
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy()
    {
        int[] var1 = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, var1, 0, this.intArray.length);
        return new NBTTagIntArray(var1);
    }

    public boolean equals(Object par1Obj)
    {
        return super.equals(par1Obj) ? Arrays.equals(this.intArray, ((NBTTagIntArray)par1Obj).intArray) : false;
    }

    public int hashCode()
    {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }

    public int[] func_150302_c()
    {
        return this.intArray;
    }
}
