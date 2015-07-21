package net.minecraft.world;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;

public class WorldManager implements IWorldAccess
{
    /** Reference to the MinecraftServer object. */
    private MinecraftServer mcServer;

    /** The WorldServer object. */
    private WorldServer theWorldServer;
    private static final String __OBFID = "CL_00001433";

    public WorldManager(MinecraftServer par1MinecraftServer, WorldServer par2WorldServer)
    {
        this.mcServer = par1MinecraftServer;
        this.theWorldServer = par2WorldServer;
    }

    /**
     * Spawns a particle. Arg: particleType, x, y, z, velX, velY, velZ
     */
    public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {}

    /**
     * Called on all IWorldAccesses when an entity is created or loaded. On client worlds, starts downloading any
     * necessary textures. On server worlds, adds the entity to the entity tracker.
     */
    public void onEntityCreate(Entity par1Entity)
    {
        this.theWorldServer.getEntityTracker().addEntityToTracker(par1Entity);
    }

    /**
     * Called on all IWorldAccesses when an entity is unloaded or destroyed. On client worlds, releases any downloaded
     * textures. On server worlds, removes the entity from the entity tracker.
     */
    public void onEntityDestroy(Entity par1Entity)
    {
        this.theWorldServer.getEntityTracker().removeEntityFromAllTrackingPlayers(par1Entity);
    }

    /**
     * Plays the specified sound. Arg: soundName, x, y, z, volume, pitch
     */
    public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
    {
        this.mcServer.getConfigurationManager().func_148541_a(par2, par4, par6, par8 > 1.0F ? (double)(16.0F * par8) : 16.0D, this.theWorldServer.provider.dimensionId, new S29PacketSoundEffect(par1Str, par2, par4, par6, par8, par9));
    }

    /**
     * Plays sound to all near players except the player reference given
     */
    public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, double par3, double par5, double par7, float par9, float par10)
    {
        this.mcServer.getConfigurationManager().func_148543_a(par1EntityPlayer, par3, par5, par7, par9 > 1.0F ? (double)(16.0F * par9) : 16.0D, this.theWorldServer.provider.dimensionId, new S29PacketSoundEffect(par2Str, par3, par5, par7, par9, par10));
    }

    /**
     * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing. Args: min x, min y,
     * min z, max x, max y, max z
     */
    public void markBlockRangeForRenderUpdate(int p_147585_1_, int p_147585_2_, int p_147585_3_, int p_147585_4_, int p_147585_5_, int p_147585_6_) {}

    /**
     * On the client, re-renders the block. On the server, sends the block to the client (which will re-render it),
     * including the tile entity description packet if applicable. Args: x, y, z
     */
    public void markBlockForUpdate(int p_147586_1_, int p_147586_2_, int p_147586_3_)
    {
        this.theWorldServer.getPlayerManager().func_151250_a(p_147586_1_, p_147586_2_, p_147586_3_);
    }

    /**
     * On the client, re-renders this block. On the server, does nothing. Used for lighting updates.
     */
    public void markBlockForRenderUpdate(int p_147588_1_, int p_147588_2_, int p_147588_3_) {}

    /**
     * Plays the specified record. Arg: recordName, x, y, z
     */
    public void playRecord(String par1Str, int par2, int par3, int par4) {}

    /**
     * Plays a pre-canned sound effect along with potentially auxiliary data-driven one-shot behaviour (particles, etc).
     */
    public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
    {
        this.mcServer.getConfigurationManager().func_148543_a(par1EntityPlayer, (double)par3, (double)par4, (double)par5, 64.0D, this.theWorldServer.provider.dimensionId, new S28PacketEffect(par2, par3, par4, par5, par6, false));
    }

    public void broadcastSound(int par1, int par2, int par3, int par4, int par5)
    {
        this.mcServer.getConfigurationManager().func_148540_a(new S28PacketEffect(par1, par2, par3, par4, par5, true));
    }

    /**
     * Starts (or continues) destroying a block with given ID at the given coordinates for the given partially destroyed
     * value
     */
    public void destroyBlockPartially(int p_147587_1_, int p_147587_2_, int p_147587_3_, int p_147587_4_, int p_147587_5_)
    {
        Iterator var6 = this.mcServer.getConfigurationManager().playerEntityList.iterator();

        while (var6.hasNext())
        {
            EntityPlayerMP var7 = (EntityPlayerMP)var6.next();

            if (var7 != null && var7.worldObj == this.theWorldServer && var7.getEntityId() != p_147587_1_)
            {
                double var8 = (double)p_147587_2_ - var7.posX;
                double var10 = (double)p_147587_3_ - var7.posY;
                double var12 = (double)p_147587_4_ - var7.posZ;

                if (var8 * var8 + var10 * var10 + var12 * var12 < 1024.0D)
                {
                    var7.playerNetServerHandler.sendPacket(new S25PacketBlockBreakAnim(p_147587_1_, p_147587_2_, p_147587_3_, p_147587_4_, p_147587_5_));
                }
            }
        }
    }

    public void onStaticEntitiesChanged() {}
}
