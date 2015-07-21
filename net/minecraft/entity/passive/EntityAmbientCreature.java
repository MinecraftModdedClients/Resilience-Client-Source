package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals
{
    private static final String __OBFID = "CL_00001636";

    public EntityAmbientCreature(World par1World)
    {
        super(par1World);
    }

    public boolean allowLeashing()
    {
        return false;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    protected boolean interact(EntityPlayer par1EntityPlayer)
    {
        return false;
    }
}
