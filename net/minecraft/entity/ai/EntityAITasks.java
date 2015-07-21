package net.minecraft.entity.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAITasks
{
    private static final Logger logger = LogManager.getLogger();

    /** A list of EntityAITaskEntrys in EntityAITasks. */
    private List taskEntries = new ArrayList();

    /** A list of EntityAITaskEntrys that are currently being executed. */
    private List executingTaskEntries = new ArrayList();

    /** Instance of Profiler. */
    private final Profiler theProfiler;
    private int tickCount;
    private int tickRate = 3;
    private static final String __OBFID = "CL_00001588";

    public EntityAITasks(Profiler par1Profiler)
    {
        this.theProfiler = par1Profiler;
    }

    public void addTask(int par1, EntityAIBase par2EntityAIBase)
    {
        this.taskEntries.add(new EntityAITasks.EntityAITaskEntry(par1, par2EntityAIBase));
    }

    /**
     * removes the indicated task from the entity's AI tasks.
     */
    public void removeTask(EntityAIBase par1EntityAIBase)
    {
        Iterator var2 = this.taskEntries.iterator();

        while (var2.hasNext())
        {
            EntityAITasks.EntityAITaskEntry var3 = (EntityAITasks.EntityAITaskEntry)var2.next();
            EntityAIBase var4 = var3.action;

            if (var4 == par1EntityAIBase)
            {
                if (this.executingTaskEntries.contains(var3))
                {
                    var4.resetTask();
                    this.executingTaskEntries.remove(var3);
                }

                var2.remove();
            }
        }
    }

    public void onUpdateTasks()
    {
        ArrayList var1 = new ArrayList();
        Iterator var2;
        EntityAITasks.EntityAITaskEntry var3;

        if (this.tickCount++ % this.tickRate == 0)
        {
            var2 = this.taskEntries.iterator();

            while (var2.hasNext())
            {
                var3 = (EntityAITasks.EntityAITaskEntry)var2.next();
                boolean var4 = this.executingTaskEntries.contains(var3);

                if (var4)
                {
                    if (this.canUse(var3) && this.canContinue(var3))
                    {
                        continue;
                    }

                    var3.action.resetTask();
                    this.executingTaskEntries.remove(var3);
                }

                if (this.canUse(var3) && var3.action.shouldExecute())
                {
                    var1.add(var3);
                    this.executingTaskEntries.add(var3);
                }
            }
        }
        else
        {
            var2 = this.executingTaskEntries.iterator();

            while (var2.hasNext())
            {
                var3 = (EntityAITasks.EntityAITaskEntry)var2.next();

                if (!var3.action.continueExecuting())
                {
                    var3.action.resetTask();
                    var2.remove();
                }
            }
        }

        this.theProfiler.startSection("goalStart");
        var2 = var1.iterator();

        while (var2.hasNext())
        {
            var3 = (EntityAITasks.EntityAITaskEntry)var2.next();
            this.theProfiler.startSection(var3.action.getClass().getSimpleName());
            var3.action.startExecuting();
            this.theProfiler.endSection();
        }

        this.theProfiler.endSection();
        this.theProfiler.startSection("goalTick");
        var2 = this.executingTaskEntries.iterator();

        while (var2.hasNext())
        {
            var3 = (EntityAITasks.EntityAITaskEntry)var2.next();
            var3.action.updateTask();
        }

        this.theProfiler.endSection();
    }

    /**
     * Determine if a specific AI Task should continue being executed.
     */
    private boolean canContinue(EntityAITasks.EntityAITaskEntry par1EntityAITaskEntry)
    {
        this.theProfiler.startSection("canContinue");
        boolean var2 = par1EntityAITaskEntry.action.continueExecuting();
        this.theProfiler.endSection();
        return var2;
    }

    /**
     * Determine if a specific AI Task can be executed, which means that all running higher (= lower int value) priority
     * tasks are compatible with it or all lower priority tasks can be interrupted.
     */
    private boolean canUse(EntityAITasks.EntityAITaskEntry par1EntityAITaskEntry)
    {
        this.theProfiler.startSection("canUse");
        Iterator var2 = this.taskEntries.iterator();

        while (var2.hasNext())
        {
            EntityAITasks.EntityAITaskEntry var3 = (EntityAITasks.EntityAITaskEntry)var2.next();

            if (var3 != par1EntityAITaskEntry)
            {
                if (par1EntityAITaskEntry.priority >= var3.priority)
                {
                    if (this.executingTaskEntries.contains(var3) && !this.areTasksCompatible(par1EntityAITaskEntry, var3))
                    {
                        this.theProfiler.endSection();
                        return false;
                    }
                }
                else if (this.executingTaskEntries.contains(var3) && !var3.action.isInterruptible())
                {
                    this.theProfiler.endSection();
                    return false;
                }
            }
        }

        this.theProfiler.endSection();
        return true;
    }

    /**
     * Returns whether two EntityAITaskEntries can be executed concurrently
     */
    private boolean areTasksCompatible(EntityAITasks.EntityAITaskEntry par1EntityAITaskEntry, EntityAITasks.EntityAITaskEntry par2EntityAITaskEntry)
    {
        return (par1EntityAITaskEntry.action.getMutexBits() & par2EntityAITaskEntry.action.getMutexBits()) == 0;
    }

    class EntityAITaskEntry
    {
        public EntityAIBase action;
        public int priority;
        private static final String __OBFID = "CL_00001589";

        public EntityAITaskEntry(int par2, EntityAIBase par3EntityAIBase)
        {
            this.priority = par2;
            this.action = par3EntityAIBase;
        }
    }
}
