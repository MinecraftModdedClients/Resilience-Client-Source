package net.minecraft.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;

public class EntityAINearestAttackableTarget extends EntityAITarget
{
    private final Class targetClass;
    private final int targetChance;

    /** Instance of EntityAINearestAttackableTargetSorter. */
    private final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;

    /**
     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
     * restrictions)
     */
    private final IEntitySelector targetEntitySelector;
    private EntityLivingBase targetEntity;
    private static final String __OBFID = "CL_00001620";

    public EntityAINearestAttackableTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4)
    {
        this(par1EntityCreature, par2Class, par3, par4, false);
    }

    public EntityAINearestAttackableTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5)
    {
        this(par1EntityCreature, par2Class, par3, par4, par5, (IEntitySelector)null);
    }

    public EntityAINearestAttackableTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, final IEntitySelector par6IEntitySelector)
    {
        super(par1EntityCreature, par4, par5);
        this.targetClass = par2Class;
        this.targetChance = par3;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(par1EntityCreature);
        this.setMutexBits(1);
        this.targetEntitySelector = new IEntitySelector()
        {
            private static final String __OBFID = "CL_00001621";
            public boolean isEntityApplicable(Entity par1Entity)
            {
                return !(par1Entity instanceof EntityLivingBase) ? false : (par6IEntitySelector != null && !par6IEntitySelector.isEntityApplicable(par1Entity) ? false : EntityAINearestAttackableTarget.this.isSuitableTarget((EntityLivingBase)par1Entity, false));
            }
        };
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
        {
            return false;
        }
        else
        {
            double var1 = this.getTargetDistance();
            List var3 = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(var1, 4.0D, var1), this.targetEntitySelector);
            Collections.sort(var3, this.theNearestAttackableTargetSorter);

            if (var3.isEmpty())
            {
                return false;
            }
            else
            {
                this.targetEntity = (EntityLivingBase)var3.get(0);
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

    public static class Sorter implements Comparator
    {
        private final Entity theEntity;
        private static final String __OBFID = "CL_00001622";

        public Sorter(Entity par1Entity)
        {
            this.theEntity = par1Entity;
        }

        public int compare(Entity par1Entity, Entity par2Entity)
        {
            double var3 = this.theEntity.getDistanceSqToEntity(par1Entity);
            double var5 = this.theEntity.getDistanceSqToEntity(par2Entity);
            return var3 < var5 ? -1 : (var3 > var5 ? 1 : 0);
        }

        public int compare(Object par1Obj, Object par2Obj)
        {
            return this.compare((Entity)par1Obj, (Entity)par2Obj);
        }
    }
}
