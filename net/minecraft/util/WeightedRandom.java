package net.minecraft.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class WeightedRandom
{
    private static final String __OBFID = "CL_00001503";

    /**
     * Returns the total weight of all items in a collection.
     */
    public static int getTotalWeight(Collection par0Collection)
    {
        int var1 = 0;
        WeightedRandom.Item var3;

        for (Iterator var2 = par0Collection.iterator(); var2.hasNext(); var1 += var3.itemWeight)
        {
            var3 = (WeightedRandom.Item)var2.next();
        }

        return var1;
    }

    /**
     * Returns a random choice from the input items, with a total weight value.
     */
    public static WeightedRandom.Item getRandomItem(Random par0Random, Collection par1Collection, int par2)
    {
        if (par2 <= 0)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            int var3 = par0Random.nextInt(par2);
            Iterator var4 = par1Collection.iterator();
            WeightedRandom.Item var5;

            do
            {
                if (!var4.hasNext())
                {
                    return null;
                }

                var5 = (WeightedRandom.Item)var4.next();
                var3 -= var5.itemWeight;
            }
            while (var3 >= 0);

            return var5;
        }
    }

    /**
     * Returns a random choice from the input items.
     */
    public static WeightedRandom.Item getRandomItem(Random par0Random, Collection par1Collection)
    {
        return getRandomItem(par0Random, par1Collection, getTotalWeight(par1Collection));
    }

    /**
     * Returns the total weight of all items in a array.
     */
    public static int getTotalWeight(WeightedRandom.Item[] par0ArrayOfWeightedRandomItem)
    {
        int var1 = 0;
        WeightedRandom.Item[] var2 = par0ArrayOfWeightedRandomItem;
        int var3 = par0ArrayOfWeightedRandomItem.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            WeightedRandom.Item var5 = var2[var4];
            var1 += var5.itemWeight;
        }

        return var1;
    }

    /**
     * Returns a random choice from the input array of items, with a total weight value.
     */
    public static WeightedRandom.Item getRandomItem(Random par0Random, WeightedRandom.Item[] par1ArrayOfWeightedRandomItem, int par2)
    {
        if (par2 <= 0)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            int var3 = par0Random.nextInt(par2);
            WeightedRandom.Item[] var4 = par1ArrayOfWeightedRandomItem;
            int var5 = par1ArrayOfWeightedRandomItem.length;

            for (int var6 = 0; var6 < var5; ++var6)
            {
                WeightedRandom.Item var7 = var4[var6];
                var3 -= var7.itemWeight;

                if (var3 < 0)
                {
                    return var7;
                }
            }

            return null;
        }
    }

    /**
     * Returns a random choice from the input items.
     */
    public static WeightedRandom.Item getRandomItem(Random par0Random, WeightedRandom.Item[] par1ArrayOfWeightedRandomItem)
    {
        return getRandomItem(par0Random, par1ArrayOfWeightedRandomItem, getTotalWeight(par1ArrayOfWeightedRandomItem));
    }

    public static class Item
    {
        protected int itemWeight;
        private static final String __OBFID = "CL_00001504";

        public Item(int par1)
        {
            this.itemWeight = par1;
        }
    }
}
