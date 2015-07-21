package net.minecraft.tileentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public abstract class MobSpawnerBaseLogic
{
    /** The delay to spawn. */
    public int spawnDelay = 20;
    private String mobID = "Pig";

    /** List of minecart to spawn. */
    private List minecartToSpawn;
    private MobSpawnerBaseLogic.WeightedRandomMinecart randomMinecart;
    public double field_98287_c;
    public double field_98284_d;
    private int minSpawnDelay = 200;
    private int maxSpawnDelay = 800;

    /** A counter for spawn tries. */
    private int spawnCount = 4;
    private Entity field_98291_j;
    private int maxNearbyEntities = 6;

    /** The distance from which a player activates the spawner. */
    private int activatingRangeFromPlayer = 16;

    /** The range coefficient for spawning entities around. */
    private int spawnRange = 4;
    private static final String __OBFID = "CL_00000129";

    /**
     * Gets the entity name that should be spawned.
     */
    public String getEntityNameToSpawn()
    {
        if (this.getRandomMinecart() == null)
        {
            if (this.mobID.equals("Minecart"))
            {
                this.mobID = "MinecartRideable";
            }

            return this.mobID;
        }
        else
        {
            return this.getRandomMinecart().minecartName;
        }
    }

    public void setMobID(String par1Str)
    {
        this.mobID = par1Str;
    }

    /**
     * Returns true if there's a player close enough to this mob spawner to activate it.
     */
    public boolean canRun()
    {
        return this.getSpawnerWorld().getClosestPlayer((double)this.getSpawnerX() + 0.5D, (double)this.getSpawnerY() + 0.5D, (double)this.getSpawnerZ() + 0.5D, (double)this.activatingRangeFromPlayer) != null;
    }

    public void updateSpawner()
    {
        if (this.canRun())
        {
            double var5;

            if (this.getSpawnerWorld().isClient)
            {
                double var1 = (double)((float)this.getSpawnerX() + this.getSpawnerWorld().rand.nextFloat());
                double var3 = (double)((float)this.getSpawnerY() + this.getSpawnerWorld().rand.nextFloat());
                var5 = (double)((float)this.getSpawnerZ() + this.getSpawnerWorld().rand.nextFloat());
                this.getSpawnerWorld().spawnParticle("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
                this.getSpawnerWorld().spawnParticle("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);

                if (this.spawnDelay > 0)
                {
                    --this.spawnDelay;
                }

                this.field_98284_d = this.field_98287_c;
                this.field_98287_c = (this.field_98287_c + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0D;
            }
            else
            {
                if (this.spawnDelay == -1)
                {
                    this.resetTimer();
                }

                if (this.spawnDelay > 0)
                {
                    --this.spawnDelay;
                    return;
                }

                boolean var12 = false;

                for (int var2 = 0; var2 < this.spawnCount; ++var2)
                {
                    Entity var13 = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());

                    if (var13 == null)
                    {
                        return;
                    }

                    int var4 = this.getSpawnerWorld().getEntitiesWithinAABB(var13.getClass(), AxisAlignedBB.getAABBPool().getAABB((double)this.getSpawnerX(), (double)this.getSpawnerY(), (double)this.getSpawnerZ(), (double)(this.getSpawnerX() + 1), (double)(this.getSpawnerY() + 1), (double)(this.getSpawnerZ() + 1)).expand((double)(this.spawnRange * 2), 4.0D, (double)(this.spawnRange * 2))).size();

                    if (var4 >= this.maxNearbyEntities)
                    {
                        this.resetTimer();
                        return;
                    }

                    var5 = (double)this.getSpawnerX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * (double)this.spawnRange;
                    double var7 = (double)(this.getSpawnerY() + this.getSpawnerWorld().rand.nextInt(3) - 1);
                    double var9 = (double)this.getSpawnerZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * (double)this.spawnRange;
                    EntityLiving var11 = var13 instanceof EntityLiving ? (EntityLiving)var13 : null;
                    var13.setLocationAndAngles(var5, var7, var9, this.getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);

                    if (var11 == null || var11.getCanSpawnHere())
                    {
                        this.func_98265_a(var13);
                        this.getSpawnerWorld().playAuxSFX(2004, this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ(), 0);

                        if (var11 != null)
                        {
                            var11.spawnExplosionParticle();
                        }

                        var12 = true;
                    }
                }

                if (var12)
                {
                    this.resetTimer();
                }
            }
        }
    }

    public Entity func_98265_a(Entity par1Entity)
    {
        if (this.getRandomMinecart() != null)
        {
            NBTTagCompound var2 = new NBTTagCompound();
            par1Entity.writeToNBTOptional(var2);
            Iterator var3 = this.getRandomMinecart().field_98222_b.func_150296_c().iterator();

            while (var3.hasNext())
            {
                String var4 = (String)var3.next();
                NBTBase var5 = this.getRandomMinecart().field_98222_b.getTag(var4);
                var2.setTag(var4, var5.copy());
            }

            par1Entity.readFromNBT(var2);

            if (par1Entity.worldObj != null)
            {
                par1Entity.worldObj.spawnEntityInWorld(par1Entity);
            }

            NBTTagCompound var11;

            for (Entity var10 = par1Entity; var2.func_150297_b("Riding", 10); var2 = var11)
            {
                var11 = var2.getCompoundTag("Riding");
                Entity var12 = EntityList.createEntityByName(var11.getString("id"), par1Entity.worldObj);

                if (var12 != null)
                {
                    NBTTagCompound var6 = new NBTTagCompound();
                    var12.writeToNBTOptional(var6);
                    Iterator var7 = var11.func_150296_c().iterator();

                    while (var7.hasNext())
                    {
                        String var8 = (String)var7.next();
                        NBTBase var9 = var11.getTag(var8);
                        var6.setTag(var8, var9.copy());
                    }

                    var12.readFromNBT(var6);
                    var12.setLocationAndAngles(var10.posX, var10.posY, var10.posZ, var10.rotationYaw, var10.rotationPitch);

                    if (par1Entity.worldObj != null)
                    {
                        par1Entity.worldObj.spawnEntityInWorld(var12);
                    }

                    var10.mountEntity(var12);
                }

                var10 = var12;
            }
        }
        else if (par1Entity instanceof EntityLivingBase && par1Entity.worldObj != null)
        {
            ((EntityLiving)par1Entity).onSpawnWithEgg((IEntityLivingData)null);
            this.getSpawnerWorld().spawnEntityInWorld(par1Entity);
        }

        return par1Entity;
    }

    private void resetTimer()
    {
        if (this.maxSpawnDelay <= this.minSpawnDelay)
        {
            this.spawnDelay = this.minSpawnDelay;
        }
        else
        {
            int var10003 = this.maxSpawnDelay - this.minSpawnDelay;
            this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(var10003);
        }

        if (this.minecartToSpawn != null && this.minecartToSpawn.size() > 0)
        {
            this.setRandomMinecart((MobSpawnerBaseLogic.WeightedRandomMinecart)WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.minecartToSpawn));
        }

        this.func_98267_a(1);
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.mobID = par1NBTTagCompound.getString("EntityId");
        this.spawnDelay = par1NBTTagCompound.getShort("Delay");

        if (par1NBTTagCompound.func_150297_b("SpawnPotentials", 9))
        {
            this.minecartToSpawn = new ArrayList();
            NBTTagList var2 = par1NBTTagCompound.getTagList("SpawnPotentials", 10);

            for (int var3 = 0; var3 < var2.tagCount(); ++var3)
            {
                this.minecartToSpawn.add(new MobSpawnerBaseLogic.WeightedRandomMinecart(var2.getCompoundTagAt(var3)));
            }
        }
        else
        {
            this.minecartToSpawn = null;
        }

        if (par1NBTTagCompound.func_150297_b("SpawnData", 10))
        {
            this.setRandomMinecart(new MobSpawnerBaseLogic.WeightedRandomMinecart(par1NBTTagCompound.getCompoundTag("SpawnData"), this.mobID));
        }
        else
        {
            this.setRandomMinecart((MobSpawnerBaseLogic.WeightedRandomMinecart)null);
        }

        if (par1NBTTagCompound.func_150297_b("MinSpawnDelay", 99))
        {
            this.minSpawnDelay = par1NBTTagCompound.getShort("MinSpawnDelay");
            this.maxSpawnDelay = par1NBTTagCompound.getShort("MaxSpawnDelay");
            this.spawnCount = par1NBTTagCompound.getShort("SpawnCount");
        }

        if (par1NBTTagCompound.func_150297_b("MaxNearbyEntities", 99))
        {
            this.maxNearbyEntities = par1NBTTagCompound.getShort("MaxNearbyEntities");
            this.activatingRangeFromPlayer = par1NBTTagCompound.getShort("RequiredPlayerRange");
        }

        if (par1NBTTagCompound.func_150297_b("SpawnRange", 99))
        {
            this.spawnRange = par1NBTTagCompound.getShort("SpawnRange");
        }

        if (this.getSpawnerWorld() != null && this.getSpawnerWorld().isClient)
        {
            this.field_98291_j = null;
        }
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setString("EntityId", this.getEntityNameToSpawn());
        par1NBTTagCompound.setShort("Delay", (short)this.spawnDelay);
        par1NBTTagCompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
        par1NBTTagCompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        par1NBTTagCompound.setShort("SpawnCount", (short)this.spawnCount);
        par1NBTTagCompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
        par1NBTTagCompound.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
        par1NBTTagCompound.setShort("SpawnRange", (short)this.spawnRange);

        if (this.getRandomMinecart() != null)
        {
            par1NBTTagCompound.setTag("SpawnData", this.getRandomMinecart().field_98222_b.copy());
        }

        if (this.getRandomMinecart() != null || this.minecartToSpawn != null && this.minecartToSpawn.size() > 0)
        {
            NBTTagList var2 = new NBTTagList();

            if (this.minecartToSpawn != null && this.minecartToSpawn.size() > 0)
            {
                Iterator var3 = this.minecartToSpawn.iterator();

                while (var3.hasNext())
                {
                    MobSpawnerBaseLogic.WeightedRandomMinecart var4 = (MobSpawnerBaseLogic.WeightedRandomMinecart)var3.next();
                    var2.appendTag(var4.func_98220_a());
                }
            }
            else
            {
                var2.appendTag(this.getRandomMinecart().func_98220_a());
            }

            par1NBTTagCompound.setTag("SpawnPotentials", var2);
        }
    }

    public Entity func_98281_h()
    {
        if (this.field_98291_j == null)
        {
            Entity var1 = EntityList.createEntityByName(this.getEntityNameToSpawn(), (World)null);
            var1 = this.func_98265_a(var1);
            this.field_98291_j = var1;
        }

        return this.field_98291_j;
    }

    /**
     * Sets the delay to minDelay if parameter given is 1, else return false.
     */
    public boolean setDelayToMin(int par1)
    {
        if (par1 == 1 && this.getSpawnerWorld().isClient)
        {
            this.spawnDelay = this.minSpawnDelay;
            return true;
        }
        else
        {
            return false;
        }
    }

    public MobSpawnerBaseLogic.WeightedRandomMinecart getRandomMinecart()
    {
        return this.randomMinecart;
    }

    public void setRandomMinecart(MobSpawnerBaseLogic.WeightedRandomMinecart par1WeightedRandomMinecart)
    {
        this.randomMinecart = par1WeightedRandomMinecart;
    }

    public abstract void func_98267_a(int var1);

    public abstract World getSpawnerWorld();

    public abstract int getSpawnerX();

    public abstract int getSpawnerY();

    public abstract int getSpawnerZ();

    public class WeightedRandomMinecart extends WeightedRandom.Item
    {
        public final NBTTagCompound field_98222_b;
        public final String minecartName;
        private static final String __OBFID = "CL_00000130";

        public WeightedRandomMinecart(NBTTagCompound par2NBTTagCompound)
        {
            super(par2NBTTagCompound.getInteger("Weight"));
            NBTTagCompound var3 = par2NBTTagCompound.getCompoundTag("Properties");
            String var4 = par2NBTTagCompound.getString("Type");

            if (var4.equals("Minecart"))
            {
                if (var3 != null)
                {
                    switch (var3.getInteger("Type"))
                    {
                        case 0:
                            var4 = "MinecartRideable";
                            break;

                        case 1:
                            var4 = "MinecartChest";
                            break;

                        case 2:
                            var4 = "MinecartFurnace";
                    }
                }
                else
                {
                    var4 = "MinecartRideable";
                }
            }

            this.field_98222_b = var3;
            this.minecartName = var4;
        }

        public WeightedRandomMinecart(NBTTagCompound par2NBTTagCompound, String par3Str)
        {
            super(1);

            if (par3Str.equals("Minecart"))
            {
                if (par2NBTTagCompound != null)
                {
                    switch (par2NBTTagCompound.getInteger("Type"))
                    {
                        case 0:
                            par3Str = "MinecartRideable";
                            break;

                        case 1:
                            par3Str = "MinecartChest";
                            break;

                        case 2:
                            par3Str = "MinecartFurnace";
                    }
                }
                else
                {
                    par3Str = "MinecartRideable";
                }
            }

            this.field_98222_b = par2NBTTagCompound;
            this.minecartName = par3Str;
        }

        public NBTTagCompound func_98220_a()
        {
            NBTTagCompound var1 = new NBTTagCompound();
            var1.setTag("Properties", this.field_98222_b);
            var1.setString("Type", this.minecartName);
            var1.setInteger("Weight", this.itemWeight);
            return var1;
        }
    }
}
