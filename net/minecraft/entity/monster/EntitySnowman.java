package net.minecraft.entity.monster;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
    private static final String __OBFID = "CL_00001650";

    public EntitySnowman(World par1World)
    {
        super(par1World);
        this.setSize(0.4F, 1.8F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25D, 20, 10.0F));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);

        if (this.isWet())
        {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }

        if (this.worldObj.getBiomeGenForCoords(var1, var3).getFloatTemperature(var1, var2, var3) > 1.0F)
        {
            this.attackEntityFrom(DamageSource.onFire, 1.0F);
        }

        for (int var4 = 0; var4 < 4; ++var4)
        {
            var1 = MathHelper.floor_double(this.posX + (double)((float)(var4 % 2 * 2 - 1) * 0.25F));
            var2 = MathHelper.floor_double(this.posY);
            var3 = MathHelper.floor_double(this.posZ + (double)((float)(var4 / 2 % 2 * 2 - 1) * 0.25F));

            if (this.worldObj.getBlock(var1, var2, var3).getMaterial() == Material.air && this.worldObj.getBiomeGenForCoords(var1, var3).getFloatTemperature(var1, var2, var3) < 0.8F && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, var1, var2, var3))
            {
                this.worldObj.setBlock(var1, var2, var3, Blocks.snow_layer);
            }
        }
    }

    protected Item func_146068_u()
    {
        return Items.snowball;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = this.rand.nextInt(16);

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.func_145779_a(Items.snowball, 1);
        }
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
    {
        EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
        double var4 = par1EntityLivingBase.posX - this.posX;
        double var6 = par1EntityLivingBase.posY + (double)par1EntityLivingBase.getEyeHeight() - 1.100000023841858D - var3.posY;
        double var8 = par1EntityLivingBase.posZ - this.posZ;
        float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8) * 0.2F;
        var3.setThrowableHeading(var4, var6 + (double)var10, var8, 1.6F, 12.0F);
        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(var3);
    }
}
