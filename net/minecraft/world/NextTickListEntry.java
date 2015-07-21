package net.minecraft.world;

import net.minecraft.block.Block;

public class NextTickListEntry implements Comparable
{
    /** The id number for the next tick entry */
    private static long nextTickEntryID;
    private final Block field_151352_g;

    /** X position this tick is occuring at */
    public int xCoord;

    /** Y position this tick is occuring at */
    public int yCoord;

    /** Z position this tick is occuring at */
    public int zCoord;

    /** Time this tick is scheduled to occur at */
    public long scheduledTime;
    public int priority;

    /** The id of the tick entry */
    private long tickEntryID;
    private static final String __OBFID = "CL_00000156";

    public NextTickListEntry(int p_i45370_1_, int p_i45370_2_, int p_i45370_3_, Block p_i45370_4_)
    {
        this.tickEntryID = (long)(nextTickEntryID++);
        this.xCoord = p_i45370_1_;
        this.yCoord = p_i45370_2_;
        this.zCoord = p_i45370_3_;
        this.field_151352_g = p_i45370_4_;
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof NextTickListEntry))
        {
            return false;
        }
        else
        {
            NextTickListEntry var2 = (NextTickListEntry)par1Obj;
            return this.xCoord == var2.xCoord && this.yCoord == var2.yCoord && this.zCoord == var2.zCoord && Block.isEqualTo(this.field_151352_g, var2.field_151352_g);
        }
    }

    public int hashCode()
    {
        return (this.xCoord * 1024 * 1024 + this.zCoord * 1024 + this.yCoord) * 256;
    }

    /**
     * Sets the scheduled time for this tick entry
     */
    public NextTickListEntry setScheduledTime(long par1)
    {
        this.scheduledTime = par1;
        return this;
    }

    public void setPriority(int par1)
    {
        this.priority = par1;
    }

    public int compareTo(NextTickListEntry par1NextTickListEntry)
    {
        return this.scheduledTime < par1NextTickListEntry.scheduledTime ? -1 : (this.scheduledTime > par1NextTickListEntry.scheduledTime ? 1 : (this.priority != par1NextTickListEntry.priority ? this.priority - par1NextTickListEntry.priority : (this.tickEntryID < par1NextTickListEntry.tickEntryID ? -1 : (this.tickEntryID > par1NextTickListEntry.tickEntryID ? 1 : 0))));
    }

    public String toString()
    {
        return Block.getIdFromBlock(this.field_151352_g) + ": (" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + "), " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
    }

    public Block func_151351_a()
    {
        return this.field_151352_g;
    }

    public int compareTo(Object par1Obj)
    {
        return this.compareTo((NextTickListEntry)par1Obj);
    }
}
