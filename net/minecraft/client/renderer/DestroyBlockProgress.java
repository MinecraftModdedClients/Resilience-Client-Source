package net.minecraft.client.renderer;

public class DestroyBlockProgress
{
    /**
     * entity ID of the player associated with this partially destroyed Block. Used to identify the Blocks in the client
     * Renderer, max 1 per player on a server
     */
    private final int miningPlayerEntId;
    private final int partialBlockX;
    private final int partialBlockY;
    private final int partialBlockZ;

    /**
     * damage ranges from 1 to 10. -1 causes the client to delete the partial block renderer.
     */
    private int partialBlockProgress;

    /**
     * keeps track of how many ticks this PartiallyDestroyedBlock already exists
     */
    private int createdAtCloudUpdateTick;
    private static final String __OBFID = "CL_00001427";

    public DestroyBlockProgress(int par1, int par2, int par3, int par4)
    {
        this.miningPlayerEntId = par1;
        this.partialBlockX = par2;
        this.partialBlockY = par3;
        this.partialBlockZ = par4;
    }

    public int getPartialBlockX()
    {
        return this.partialBlockX;
    }

    public int getPartialBlockY()
    {
        return this.partialBlockY;
    }

    public int getPartialBlockZ()
    {
        return this.partialBlockZ;
    }

    /**
     * inserts damage value into this partially destroyed Block. -1 causes client renderer to delete it, otherwise
     * ranges from 1 to 10
     */
    public void setPartialBlockDamage(int par1)
    {
        if (par1 > 10)
        {
            par1 = 10;
        }

        this.partialBlockProgress = par1;
    }

    public int getPartialBlockDamage()
    {
        return this.partialBlockProgress;
    }

    /**
     * saves the current Cloud update tick into the PartiallyDestroyedBlock
     */
    public void setCloudUpdateTick(int par1)
    {
        this.createdAtCloudUpdateTick = par1;
    }

    /**
     * retrieves the 'date' at which the PartiallyDestroyedBlock was created
     */
    public int getCreationCloudUpdateTick()
    {
        return this.createdAtCloudUpdateTick;
    }
}
