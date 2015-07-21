package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart
{
    /** Mob spawner logic for this spawner minecart. */
    private final MobSpawnerBaseLogic mobSpawnerLogic = new MobSpawnerBaseLogic()
    {
        private static final String __OBFID = "CL_00001679";
        public void func_98267_a(int par1)
        {
            EntityMinecartMobSpawner.this.worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte)par1);
        }
        public World getSpawnerWorld()
        {
            return EntityMinecartMobSpawner.this.worldObj;
        }
        public int getSpawnerX()
        {
            return MathHelper.floor_double(EntityMinecartMobSpawner.this.posX);
        }
        public int getSpawnerY()
        {
            return MathHelper.floor_double(EntityMinecartMobSpawner.this.posY);
        }
        public int getSpawnerZ()
        {
            return MathHelper.floor_double(EntityMinecartMobSpawner.this.posZ);
        }
    };
    private static final String __OBFID = "CL_00001678";

    public EntityMinecartMobSpawner(World par1World)
    {
        super(par1World);
    }

    public EntityMinecartMobSpawner(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    public int getMinecartType()
    {
        return 4;
    }

    public Block func_145817_o()
    {
        return Blocks.mob_spawner;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.mobSpawnerLogic.readFromNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        this.mobSpawnerLogic.writeToNBT(par1NBTTagCompound);
    }

    public void handleHealthUpdate(byte par1)
    {
        this.mobSpawnerLogic.setDelayToMin(par1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }

    public MobSpawnerBaseLogic func_98039_d()
    {
        return this.mobSpawnerLogic;
    }
}
