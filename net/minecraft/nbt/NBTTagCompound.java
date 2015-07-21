package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagCompound extends NBTBase
{
    private static final Logger logger = LogManager.getLogger();

    /**
     * The key-value pairs for the tag. Each key is a UTF string, each value is a tag.
     */
    private Map tagMap = new HashMap();
    private static final String __OBFID = "CL_00001215";

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput par1DataOutput) throws IOException
    {
        Iterator var2 = this.tagMap.keySet().iterator();

        while (var2.hasNext())
        {
            String var3 = (String)var2.next();
            NBTBase var4 = (NBTBase)this.tagMap.get(var3);
            func_150298_a(var3, var4, par1DataOutput);
        }

        par1DataOutput.writeByte(0);
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput, int par2) throws IOException
    {
        if (par2 > 512)
        {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        else
        {
            this.tagMap.clear();
            byte var3;

            while ((var3 = func_150300_a(par1DataInput)) != 0)
            {
                String var4 = func_150294_b(par1DataInput);
                NBTBase var5 = func_150293_a(var3, var4, par1DataInput, par2 + 1);
                this.tagMap.put(var4, var5);
            }
        }
    }

    public Set func_150296_c()
    {
        return this.tagMap.keySet();
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getId()
    {
        return (byte)10;
    }

    /**
     * Stores the given tag into the map with the given string key. This is mostly used to store tag lists.
     */
    public void setTag(String par1Str, NBTBase par2NBTBase)
    {
        this.tagMap.put(par1Str, par2NBTBase);
    }

    /**
     * Stores a new NBTTagByte with the given byte value into the map with the given string key.
     */
    public void setByte(String par1Str, byte par2)
    {
        this.tagMap.put(par1Str, new NBTTagByte(par2));
    }

    /**
     * Stores a new NBTTagShort with the given short value into the map with the given string key.
     */
    public void setShort(String par1Str, short par2)
    {
        this.tagMap.put(par1Str, new NBTTagShort(par2));
    }

    /**
     * Stores a new NBTTagInt with the given integer value into the map with the given string key.
     */
    public void setInteger(String par1Str, int par2)
    {
        this.tagMap.put(par1Str, new NBTTagInt(par2));
    }

    /**
     * Stores a new NBTTagLong with the given long value into the map with the given string key.
     */
    public void setLong(String par1Str, long par2)
    {
        this.tagMap.put(par1Str, new NBTTagLong(par2));
    }

    /**
     * Stores a new NBTTagFloat with the given float value into the map with the given string key.
     */
    public void setFloat(String par1Str, float par2)
    {
        this.tagMap.put(par1Str, new NBTTagFloat(par2));
    }

    /**
     * Stores a new NBTTagDouble with the given double value into the map with the given string key.
     */
    public void setDouble(String par1Str, double par2)
    {
        this.tagMap.put(par1Str, new NBTTagDouble(par2));
    }

    /**
     * Stores a new NBTTagString with the given string value into the map with the given string key.
     */
    public void setString(String par1Str, String par2Str)
    {
        this.tagMap.put(par1Str, new NBTTagString(par2Str));
    }

    /**
     * Stores a new NBTTagByteArray with the given array as data into the map with the given string key.
     */
    public void setByteArray(String par1Str, byte[] par2ArrayOfByte)
    {
        this.tagMap.put(par1Str, new NBTTagByteArray(par2ArrayOfByte));
    }

    /**
     * Stores a new NBTTagIntArray with the given array as data into the map with the given string key.
     */
    public void setIntArray(String par1Str, int[] par2ArrayOfInteger)
    {
        this.tagMap.put(par1Str, new NBTTagIntArray(par2ArrayOfInteger));
    }

    /**
     * Stores the given boolean value as a NBTTagByte, storing 1 for true and 0 for false, using the given string key.
     */
    public void setBoolean(String par1Str, boolean par2)
    {
        this.setByte(par1Str, (byte)(par2 ? 1 : 0));
    }

    /**
     * gets a generic tag with the specified name
     */
    public NBTBase getTag(String par1Str)
    {
        return (NBTBase)this.tagMap.get(par1Str);
    }

    public byte func_150299_b(String p_150299_1_)
    {
        NBTBase var2 = (NBTBase)this.tagMap.get(p_150299_1_);
        return var2 != null ? var2.getId() : 0;
    }

    /**
     * Returns whether the given string has been previously stored as a key in the map.
     */
    public boolean hasKey(String par1Str)
    {
        return this.tagMap.containsKey(par1Str);
    }

    public boolean func_150297_b(String p_150297_1_, int p_150297_2_)
    {
        byte var3 = this.func_150299_b(p_150297_1_);

        if (var3 == p_150297_2_)
        {
            return true;
        }
        else if (p_150297_2_ != 99)
        {
            if (var3 > 0)
            {
                logger.warn("NBT tag {} was of wrong type; expected {}, found {}", new Object[] {p_150297_1_, func_150283_g(p_150297_2_), func_150283_g(var3)});
            }

            return false;
        }
        else
        {
            return var3 == 1 || var3 == 2 || var3 == 3 || var3 == 4 || var3 == 5 || var3 == 6;
        }
    }

    /**
     * Retrieves a byte value using the specified key, or 0 if no such key was stored.
     */
    public byte getByte(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150290_f();
        }
        catch (ClassCastException var3)
        {
            return (byte)0;
        }
    }

    /**
     * Retrieves a short value using the specified key, or 0 if no such key was stored.
     */
    public short getShort(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150289_e();
        }
        catch (ClassCastException var3)
        {
            return (short)0;
        }
    }

    /**
     * Retrieves an integer value using the specified key, or 0 if no such key was stored.
     */
    public int getInteger(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150287_d();
        }
        catch (ClassCastException var3)
        {
            return 0;
        }
    }

    /**
     * Retrieves a long value using the specified key, or 0 if no such key was stored.
     */
    public long getLong(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? 0L : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150291_c();
        }
        catch (ClassCastException var3)
        {
            return 0L;
        }
    }

    /**
     * Retrieves a float value using the specified key, or 0 if no such key was stored.
     */
    public float getFloat(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? 0.0F : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150288_h();
        }
        catch (ClassCastException var3)
        {
            return 0.0F;
        }
    }

    /**
     * Retrieves a double value using the specified key, or 0 if no such key was stored.
     */
    public double getDouble(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? 0.0D : ((NBTBase.NBTPrimitive)this.tagMap.get(par1Str)).func_150286_g();
        }
        catch (ClassCastException var3)
        {
            return 0.0D;
        }
    }

    /**
     * Retrieves a string value using the specified key, or an empty string if no such key was stored.
     */
    public String getString(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? "" : ((NBTBase)this.tagMap.get(par1Str)).func_150285_a_();
        }
        catch (ClassCastException var3)
        {
            return "";
        }
    }

    /**
     * Retrieves a byte array using the specified key, or a zero-length array if no such key was stored.
     */
    public byte[] getByteArray(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? new byte[0] : ((NBTTagByteArray)this.tagMap.get(par1Str)).func_150292_c();
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.createCrashReport(par1Str, 7, var3));
        }
    }

    /**
     * Retrieves an int array using the specified key, or a zero-length array if no such key was stored.
     */
    public int[] getIntArray(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? new int[0] : ((NBTTagIntArray)this.tagMap.get(par1Str)).func_150302_c();
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.createCrashReport(par1Str, 11, var3));
        }
    }

    /**
     * Retrieves a NBTTagCompound subtag matching the specified key, or a new empty NBTTagCompound if no such key was
     * stored.
     */
    public NBTTagCompound getCompoundTag(String par1Str)
    {
        try
        {
            return !this.tagMap.containsKey(par1Str) ? new NBTTagCompound() : (NBTTagCompound)this.tagMap.get(par1Str);
        }
        catch (ClassCastException var3)
        {
            throw new ReportedException(this.createCrashReport(par1Str, 10, var3));
        }
    }

    /**
     * Gets the NBTTagList object with the given name. Args: name, NBTBase type
     */
    public NBTTagList getTagList(String p_150295_1_, int p_150295_2_)
    {
        try
        {
            if (this.func_150299_b(p_150295_1_) != 9)
            {
                return new NBTTagList();
            }
            else
            {
                NBTTagList var3 = (NBTTagList)this.tagMap.get(p_150295_1_);
                return var3.tagCount() > 0 && var3.func_150303_d() != p_150295_2_ ? new NBTTagList() : var3;
            }
        }
        catch (ClassCastException var4)
        {
            throw new ReportedException(this.createCrashReport(p_150295_1_, 9, var4));
        }
    }

    /**
     * Retrieves a boolean value using the specified key, or false if no such key was stored. This uses the getByte
     * method.
     */
    public boolean getBoolean(String par1Str)
    {
        return this.getByte(par1Str) != 0;
    }

    /**
     * Remove the specified tag.
     */
    public void removeTag(String par1Str)
    {
        this.tagMap.remove(par1Str);
    }

    public String toString()
    {
        String var1 = "{";
        String var3;

        for (Iterator var2 = this.tagMap.keySet().iterator(); var2.hasNext(); var1 = var1 + var3 + ':' + this.tagMap.get(var3) + ',')
        {
            var3 = (String)var2.next();
        }

        return var1 + "}";
    }

    /**
     * Return whether this compound has no tags.
     */
    public boolean hasNoTags()
    {
        return this.tagMap.isEmpty();
    }

    /**
     * Create a crash report which indicates a NBT read error.
     */
    private CrashReport createCrashReport(final String par1Str, final int par2, ClassCastException par3ClassCastException)
    {
        CrashReport var4 = CrashReport.makeCrashReport(par3ClassCastException, "Reading NBT data");
        CrashReportCategory var5 = var4.makeCategoryDepth("Corrupt NBT tag", 1);
        var5.addCrashSectionCallable("Tag type found", new Callable()
        {
            private static final String __OBFID = "CL_00001216";
            public String call()
            {
                return NBTBase.NBTTypes[((NBTBase)NBTTagCompound.this.tagMap.get(par1Str)).getId()];
            }
        });
        var5.addCrashSectionCallable("Tag type expected", new Callable()
        {
            private static final String __OBFID = "CL_00001217";
            public String call()
            {
                return NBTBase.NBTTypes[par2];
            }
        });
        var5.addCrashSection("Tag name", par1Str);
        return var4;
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        Iterator var2 = this.tagMap.keySet().iterator();

        while (var2.hasNext())
        {
            String var3 = (String)var2.next();
            var1.setTag(var3, ((NBTBase)this.tagMap.get(var3)).copy());
        }

        return var1;
    }

    public boolean equals(Object par1Obj)
    {
        if (super.equals(par1Obj))
        {
            NBTTagCompound var2 = (NBTTagCompound)par1Obj;
            return this.tagMap.entrySet().equals(var2.tagMap.entrySet());
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.tagMap.hashCode();
    }

    private static void func_150298_a(String p_150298_0_, NBTBase p_150298_1_, DataOutput p_150298_2_) throws IOException
    {
        p_150298_2_.writeByte(p_150298_1_.getId());

        if (p_150298_1_.getId() != 0)
        {
            p_150298_2_.writeUTF(p_150298_0_);
            p_150298_1_.write(p_150298_2_);
        }
    }

    private static byte func_150300_a(DataInput p_150300_0_) throws IOException
    {
        return p_150300_0_.readByte();
    }

    private static String func_150294_b(DataInput p_150294_0_) throws IOException
    {
        return p_150294_0_.readUTF();
    }

    static NBTBase func_150293_a(byte p_150293_0_, String p_150293_1_, DataInput p_150293_2_, int p_150293_3_)
    {
        NBTBase var4 = NBTBase.func_150284_a(p_150293_0_);

        try
        {
            var4.load(p_150293_2_, p_150293_3_);
            return var4;
        }
        catch (IOException var8)
        {
            CrashReport var6 = CrashReport.makeCrashReport(var8, "Loading NBT data");
            CrashReportCategory var7 = var6.makeCategory("NBT Tag");
            var7.addCrashSection("Tag name", p_150293_1_);
            var7.addCrashSection("Tag type", Byte.valueOf(p_150293_0_));
            throw new ReportedException(var6);
        }
    }
}
