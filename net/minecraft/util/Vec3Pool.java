package net.minecraft.util;

import java.util.ArrayList;
import java.util.List;

public class Vec3Pool
{
    private final int truncateArrayResetThreshold;
    private final int minimumSize;

    /** items at and above nextFreeSpace are assumed to be available */
    private final List vec3Cache = new ArrayList();

    /**
     * The number of elements we consider "in the pool".  Elements already in the backing store whose indices are at or
     * above this value may have their components reset at any time.  TODO: this variable should really be named
     * "poolSize", but I'm not renaming it right now in case any mods are actually (inappropriately) using it instead of
     * using the accessor method..
     */
    private int nextFreeSpace;
    private int maximumSizeSinceLastTruncation;
    private int resetCount;
    private static final String __OBFID = "CL_00000613";

    public Vec3Pool(int par1, int par2)
    {
        this.truncateArrayResetThreshold = par1;
        this.minimumSize = par2;
    }

    /**
     * extends the pool if all vecs are currently "out"
     */
    public Vec3 getVecFromPool(double par1, double par3, double par5)
    {
        if (this.skipCache())
        {
            return new Vec3(this, par1, par3, par5);
        }
        else
        {
            Vec3 var7;

            if (this.nextFreeSpace >= this.vec3Cache.size())
            {
                var7 = new Vec3(this, par1, par3, par5);
                this.vec3Cache.add(var7);
            }
            else
            {
                var7 = (Vec3)this.vec3Cache.get(this.nextFreeSpace);
                var7.setComponents(par1, par3, par5);
            }

            ++this.nextFreeSpace;
            return var7;
        }
    }

    /**
     * Will truncate the array everyN clears to the maximum size observed since the last truncation.
     */
    public void clear()
    {
        if (!this.skipCache())
        {
            if (this.nextFreeSpace > this.maximumSizeSinceLastTruncation)
            {
                this.maximumSizeSinceLastTruncation = this.nextFreeSpace;
            }

            if (this.resetCount++ == this.truncateArrayResetThreshold)
            {
                int var1 = Math.max(this.maximumSizeSinceLastTruncation, this.vec3Cache.size() - this.minimumSize);

                while (this.vec3Cache.size() > var1)
                {
                    this.vec3Cache.remove(var1);
                }

                this.maximumSizeSinceLastTruncation = 0;
                this.resetCount = 0;
            }

            this.nextFreeSpace = 0;
        }
    }

    public void clearAndFreeCache()
    {
        if (!this.skipCache())
        {
            this.nextFreeSpace = 0;
            this.vec3Cache.clear();
        }
    }

    /**
     * Gets the number of elements in the pool.
     */
    public int getPoolSize()
    {
        return this.vec3Cache.size();
    }

    /**
     * Get the next free space
     */
    public int getNextFreeSpace()
    {
        return this.nextFreeSpace;
    }

    /**
     * True if we should skip caching altogether, possibly because this class is not thread-safe.
     */
    private boolean skipCache()
    {
        return this.minimumSize < 0 || this.truncateArrayResetThreshold < 0;
    }
}
