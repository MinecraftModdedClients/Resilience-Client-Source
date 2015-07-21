package net.minecraft.entity.ai;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntitySenses
{
    EntityLiving entityObj;

    /** Cache of entities which we can see */
    List seenEntities = new ArrayList();

    /** Cache of entities which we cannot see */
    List unseenEntities = new ArrayList();
    private static final String __OBFID = "CL_00001628";

    public EntitySenses(EntityLiving par1EntityLiving)
    {
        this.entityObj = par1EntityLiving;
    }

    /**
     * Clears canSeeCachePositive and canSeeCacheNegative.
     */
    public void clearSensingCache()
    {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }

    /**
     * Checks, whether 'our' entity can see the entity given as argument (true) or not (false), caching the result.
     */
    public boolean canSee(Entity par1Entity)
    {
        if (this.seenEntities.contains(par1Entity))
        {
            return true;
        }
        else if (this.unseenEntities.contains(par1Entity))
        {
            return false;
        }
        else
        {
            this.entityObj.worldObj.theProfiler.startSection("canSee");
            boolean var2 = this.entityObj.canEntityBeSeen(par1Entity);
            this.entityObj.worldObj.theProfiler.endSection();

            if (var2)
            {
                this.seenEntities.add(par1Entity);
            }
            else
            {
                this.unseenEntities.add(par1Entity);
            }

            return var2;
        }
    }
}
