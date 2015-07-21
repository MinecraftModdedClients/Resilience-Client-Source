package net.minecraft.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Explosion
{
    /** whether or not the explosion sets fire to blocks around it */
    public boolean isFlaming;

    /** whether or not this explosion spawns smoke particles */
    public boolean isSmoking = true;
    private int field_77289_h = 16;
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;

    /** A list of ChunkPositions of blocks affected by this explosion */
    public List affectedBlockPositions = new ArrayList();
    private Map field_77288_k = new HashMap();
    private static final String __OBFID = "CL_00000134";

    public Explosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9)
    {
        this.worldObj = par1World;
        this.exploder = par2Entity;
        this.explosionSize = par9;
        this.explosionX = par3;
        this.explosionY = par5;
        this.explosionZ = par7;
    }

    /**
     * Does the first part of the explosion (destroy blocks)
     */
    public void doExplosionA()
    {
        float var1 = this.explosionSize;
        HashSet var2 = new HashSet();
        int var3;
        int var4;
        int var5;
        double var15;
        double var17;
        double var19;

        for (var3 = 0; var3 < this.field_77289_h; ++var3)
        {
            for (var4 = 0; var4 < this.field_77289_h; ++var4)
            {
                for (var5 = 0; var5 < this.field_77289_h; ++var5)
                {
                    if (var3 == 0 || var3 == this.field_77289_h - 1 || var4 == 0 || var4 == this.field_77289_h - 1 || var5 == 0 || var5 == this.field_77289_h - 1)
                    {
                        double var6 = (double)((float)var3 / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double var8 = (double)((float)var4 / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double var10 = (double)((float)var5 / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
                        var6 /= var12;
                        var8 /= var12;
                        var10 /= var12;
                        float var14 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        var15 = this.explosionX;
                        var17 = this.explosionY;
                        var19 = this.explosionZ;

                        for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F)
                        {
                            int var22 = MathHelper.floor_double(var15);
                            int var23 = MathHelper.floor_double(var17);
                            int var24 = MathHelper.floor_double(var19);
                            Block var25 = this.worldObj.getBlock(var22, var23, var24);

                            if (var25.getMaterial() != Material.air)
                            {
                                float var26 = this.exploder != null ? this.exploder.func_145772_a(this, this.worldObj, var22, var23, var24, var25) : var25.getExplosionResistance(this.exploder);
                                var14 -= (var26 + 0.3F) * var21;
                            }

                            if (var14 > 0.0F && (this.exploder == null || this.exploder.func_145774_a(this, this.worldObj, var22, var23, var24, var25, var14)))
                            {
                                var2.add(new ChunkPosition(var22, var23, var24));
                            }

                            var15 += var6 * (double)var21;
                            var17 += var8 * (double)var21;
                            var19 += var10 * (double)var21;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(var2);
        this.explosionSize *= 2.0F;
        var3 = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
        var4 = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
        var5 = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
        int var29 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
        int var7 = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
        int var30 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
        List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getAABBPool().getAABB((double)var3, (double)var5, (double)var7, (double)var4, (double)var29, (double)var30));
        Vec3 var31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);

        for (int var11 = 0; var11 < var9.size(); ++var11)
        {
            Entity var32 = (Entity)var9.get(var11);
            double var13 = var32.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

            if (var13 <= 1.0D)
            {
                var15 = var32.posX - this.explosionX;
                var17 = var32.posY + (double)var32.getEyeHeight() - this.explosionY;
                var19 = var32.posZ - this.explosionZ;
                double var34 = (double)MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);

                if (var34 != 0.0D)
                {
                    var15 /= var34;
                    var17 /= var34;
                    var19 /= var34;
                    double var33 = (double)this.worldObj.getBlockDensity(var31, var32.boundingBox);
                    double var35 = (1.0D - var13) * var33;
                    var32.attackEntityFrom(DamageSource.setExplosionSource(this), (float)((int)((var35 * var35 + var35) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D)));
                    double var27 = EnchantmentProtection.func_92092_a(var32, var35);
                    var32.motionX += var15 * var27;
                    var32.motionY += var17 * var27;
                    var32.motionZ += var19 * var27;

                    if (var32 instanceof EntityPlayer)
                    {
                        this.field_77288_k.put((EntityPlayer)var32, this.worldObj.getWorldVec3Pool().getVecFromPool(var15 * var35, var17 * var35, var19 * var35));
                    }
                }
            }
        }

        this.explosionSize = var1;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void doExplosionB(boolean par1)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

        if (this.explosionSize >= 2.0F && this.isSmoking)
        {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }

        Iterator var2;
        ChunkPosition var3;
        int var4;
        int var5;
        int var6;
        Block var7;

        if (this.isSmoking)
        {
            var2 = this.affectedBlockPositions.iterator();

            while (var2.hasNext())
            {
                var3 = (ChunkPosition)var2.next();
                var4 = var3.field_151329_a;
                var5 = var3.field_151327_b;
                var6 = var3.field_151328_c;
                var7 = this.worldObj.getBlock(var4, var5, var6);

                if (par1)
                {
                    double var8 = (double)((float)var4 + this.worldObj.rand.nextFloat());
                    double var10 = (double)((float)var5 + this.worldObj.rand.nextFloat());
                    double var12 = (double)((float)var6 + this.worldObj.rand.nextFloat());
                    double var14 = var8 - this.explosionX;
                    double var16 = var10 - this.explosionY;
                    double var18 = var12 - this.explosionZ;
                    double var20 = (double)MathHelper.sqrt_double(var14 * var14 + var16 * var16 + var18 * var18);
                    var14 /= var20;
                    var16 /= var20;
                    var18 /= var20;
                    double var22 = 0.5D / (var20 / (double)this.explosionSize + 0.1D);
                    var22 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                    var14 *= var22;
                    var16 *= var22;
                    var18 *= var22;
                    this.worldObj.spawnParticle("explode", (var8 + this.explosionX * 1.0D) / 2.0D, (var10 + this.explosionY * 1.0D) / 2.0D, (var12 + this.explosionZ * 1.0D) / 2.0D, var14, var16, var18);
                    this.worldObj.spawnParticle("smoke", var8, var10, var12, var14, var16, var18);
                }

                if (var7.getMaterial() != Material.air)
                {
                    if (var7.canDropFromExplosion(this))
                    {
                        var7.dropBlockAsItemWithChance(this.worldObj, var4, var5, var6, this.worldObj.getBlockMetadata(var4, var5, var6), 1.0F / this.explosionSize, 0);
                    }

                    this.worldObj.setBlock(var4, var5, var6, Blocks.air, 0, 3);
                    var7.onBlockDestroyedByExplosion(this.worldObj, var4, var5, var6, this);
                }
            }
        }

        if (this.isFlaming)
        {
            var2 = this.affectedBlockPositions.iterator();

            while (var2.hasNext())
            {
                var3 = (ChunkPosition)var2.next();
                var4 = var3.field_151329_a;
                var5 = var3.field_151327_b;
                var6 = var3.field_151328_c;
                var7 = this.worldObj.getBlock(var4, var5, var6);
                Block var24 = this.worldObj.getBlock(var4, var5 - 1, var6);

                if (var7.getMaterial() == Material.air && var24.func_149730_j() && this.explosionRNG.nextInt(3) == 0)
                {
                    this.worldObj.setBlock(var4, var5, var6, Blocks.fire);
                }
            }
        }
    }

    public Map func_77277_b()
    {
        return this.field_77288_k;
    }

    /**
     * Returns either the entity that placed the explosive block, the entity that caused the explosion or null.
     */
    public EntityLivingBase getExplosivePlacedBy()
    {
        return this.exploder == null ? null : (this.exploder instanceof EntityTNTPrimed ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : (this.exploder instanceof EntityLivingBase ? (EntityLivingBase)this.exploder : null));
    }
}
