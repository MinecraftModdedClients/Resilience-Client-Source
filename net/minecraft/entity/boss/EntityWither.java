package net.minecraft.entity.boss;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWither extends EntityMob implements IBossDisplayData, IRangedAttackMob
{
    private float[] field_82220_d = new float[2];
    private float[] field_82221_e = new float[2];
    private float[] field_82217_f = new float[2];
    private float[] field_82218_g = new float[2];
    private int[] field_82223_h = new int[2];
    private int[] field_82224_i = new int[2];
    private int field_82222_j;

    /** Selector used to determine the entities a wither boss should attack. */
    private static final IEntitySelector attackEntitySelector = new IEntitySelector()
    {
        private static final String __OBFID = "CL_00001662";
        public boolean isEntityApplicable(Entity par1Entity)
        {
            return par1Entity instanceof EntityLivingBase && ((EntityLivingBase)par1Entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
        }
    };
    private static final String __OBFID = "CL_00001661";

    public EntityWither(World par1World)
    {
        super(par1World);
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9F, 4.0F);
        this.isImmuneToFire = true;
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, attackEntitySelector));
        this.experienceValue = 50;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(0));
        this.dataWatcher.addObject(19, new Integer(0));
        this.dataWatcher.addObject(20, new Integer(0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Invul", this.func_82212_n());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.func_82215_s(par1NBTTagCompound.getInteger("Invul"));
    }

    public float getShadowSize()
    {
        return this.height / 8.0F;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.wither.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.wither.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.wither.death";
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        this.motionY *= 0.6000000238418579D;
        double var4;
        double var6;
        double var8;

        if (!this.worldObj.isClient && this.getWatchedTargetId(0) > 0)
        {
            Entity var1 = this.worldObj.getEntityByID(this.getWatchedTargetId(0));

            if (var1 != null)
            {
                if (this.posY < var1.posY || !this.isArmored() && this.posY < var1.posY + 5.0D)
                {
                    if (this.motionY < 0.0D)
                    {
                        this.motionY = 0.0D;
                    }

                    this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
                }

                double var2 = var1.posX - this.posX;
                var4 = var1.posZ - this.posZ;
                var6 = var2 * var2 + var4 * var4;

                if (var6 > 9.0D)
                {
                    var8 = (double)MathHelper.sqrt_double(var6);
                    this.motionX += (var2 / var8 * 0.5D - this.motionX) * 0.6000000238418579D;
                    this.motionZ += (var4 / var8 * 0.5D - this.motionZ) * 0.6000000238418579D;
                }
            }
        }

        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D)
        {
            this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * (180F / (float)Math.PI) - 90.0F;
        }

        super.onLivingUpdate();
        int var20;

        for (var20 = 0; var20 < 2; ++var20)
        {
            this.field_82218_g[var20] = this.field_82221_e[var20];
            this.field_82217_f[var20] = this.field_82220_d[var20];
        }

        int var21;

        for (var20 = 0; var20 < 2; ++var20)
        {
            var21 = this.getWatchedTargetId(var20 + 1);
            Entity var3 = null;

            if (var21 > 0)
            {
                var3 = this.worldObj.getEntityByID(var21);
            }

            if (var3 != null)
            {
                var4 = this.func_82214_u(var20 + 1);
                var6 = this.func_82208_v(var20 + 1);
                var8 = this.func_82213_w(var20 + 1);
                double var10 = var3.posX - var4;
                double var12 = var3.posY + (double)var3.getEyeHeight() - var6;
                double var14 = var3.posZ - var8;
                double var16 = (double)MathHelper.sqrt_double(var10 * var10 + var14 * var14);
                float var18 = (float)(Math.atan2(var14, var10) * 180.0D / Math.PI) - 90.0F;
                float var19 = (float)(-(Math.atan2(var12, var16) * 180.0D / Math.PI));
                this.field_82220_d[var20] = this.func_82204_b(this.field_82220_d[var20], var19, 40.0F);
                this.field_82221_e[var20] = this.func_82204_b(this.field_82221_e[var20], var18, 10.0F);
            }
            else
            {
                this.field_82221_e[var20] = this.func_82204_b(this.field_82221_e[var20], this.renderYawOffset, 10.0F);
            }
        }

        boolean var22 = this.isArmored();

        for (var21 = 0; var21 < 3; ++var21)
        {
            double var23 = this.func_82214_u(var21);
            double var5 = this.func_82208_v(var21);
            double var7 = this.func_82213_w(var21);
            this.worldObj.spawnParticle("smoke", var23 + this.rand.nextGaussian() * 0.30000001192092896D, var5 + this.rand.nextGaussian() * 0.30000001192092896D, var7 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);

            if (var22 && this.worldObj.rand.nextInt(4) == 0)
            {
                this.worldObj.spawnParticle("mobSpell", var23 + this.rand.nextGaussian() * 0.30000001192092896D, var5 + this.rand.nextGaussian() * 0.30000001192092896D, var7 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
            }
        }

        if (this.func_82212_n() > 0)
        {
            for (var21 = 0; var21 < 3; ++var21)
            {
                this.worldObj.spawnParticle("mobSpell", this.posX + this.rand.nextGaussian() * 1.0D, this.posY + (double)(this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
            }
        }
    }

    protected void updateAITasks()
    {
        int var1;

        if (this.func_82212_n() > 0)
        {
            var1 = this.func_82212_n() - 1;

            if (var1 <= 0)
            {
                this.worldObj.newExplosion(this, this.posX, this.posY + (double)this.getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
                this.worldObj.playBroadcastSound(1013, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
            }

            this.func_82215_s(var1);

            if (this.ticksExisted % 10 == 0)
            {
                this.heal(10.0F);
            }
        }
        else
        {
            super.updateAITasks();
            int var12;

            for (var1 = 1; var1 < 3; ++var1)
            {
                if (this.ticksExisted >= this.field_82223_h[var1 - 1])
                {
                    this.field_82223_h[var1 - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);

                    if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL || this.worldObj.difficultySetting == EnumDifficulty.HARD)
                    {
                        int var10001 = var1 - 1;
                        int var10003 = this.field_82224_i[var1 - 1];
                        this.field_82224_i[var10001] = this.field_82224_i[var1 - 1] + 1;

                        if (var10003 > 15)
                        {
                            float var2 = 10.0F;
                            float var3 = 5.0F;
                            double var4 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - (double)var2, this.posX + (double)var2);
                            double var6 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - (double)var3, this.posY + (double)var3);
                            double var8 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - (double)var2, this.posZ + (double)var2);
                            this.func_82209_a(var1 + 1, var4, var6, var8, true);
                            this.field_82224_i[var1 - 1] = 0;
                        }
                    }

                    var12 = this.getWatchedTargetId(var1);

                    if (var12 > 0)
                    {
                        Entity var14 = this.worldObj.getEntityByID(var12);

                        if (var14 != null && var14.isEntityAlive() && this.getDistanceSqToEntity(var14) <= 900.0D && this.canEntityBeSeen(var14))
                        {
                            this.func_82216_a(var1 + 1, (EntityLivingBase)var14);
                            this.field_82223_h[var1 - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
                            this.field_82224_i[var1 - 1] = 0;
                        }
                        else
                        {
                            this.func_82211_c(var1, 0);
                        }
                    }
                    else
                    {
                        List var13 = this.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0D, 8.0D, 20.0D), attackEntitySelector);

                        for (int var16 = 0; var16 < 10 && !var13.isEmpty(); ++var16)
                        {
                            EntityLivingBase var5 = (EntityLivingBase)var13.get(this.rand.nextInt(var13.size()));

                            if (var5 != this && var5.isEntityAlive() && this.canEntityBeSeen(var5))
                            {
                                if (var5 instanceof EntityPlayer)
                                {
                                    if (!((EntityPlayer)var5).capabilities.disableDamage)
                                    {
                                        this.func_82211_c(var1, var5.getEntityId());
                                    }
                                }
                                else
                                {
                                    this.func_82211_c(var1, var5.getEntityId());
                                }

                                break;
                            }

                            var13.remove(var5);
                        }
                    }
                }
            }

            if (this.getAttackTarget() != null)
            {
                this.func_82211_c(0, this.getAttackTarget().getEntityId());
            }
            else
            {
                this.func_82211_c(0, 0);
            }

            if (this.field_82222_j > 0)
            {
                --this.field_82222_j;

                if (this.field_82222_j == 0 && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
                {
                    var1 = MathHelper.floor_double(this.posY);
                    var12 = MathHelper.floor_double(this.posX);
                    int var15 = MathHelper.floor_double(this.posZ);
                    boolean var18 = false;

                    for (int var17 = -1; var17 <= 1; ++var17)
                    {
                        for (int var19 = -1; var19 <= 1; ++var19)
                        {
                            for (int var7 = 0; var7 <= 3; ++var7)
                            {
                                int var20 = var12 + var17;
                                int var9 = var1 + var7;
                                int var10 = var15 + var19;
                                Block var11 = this.worldObj.getBlock(var20, var9, var10);

                                if (var11.getMaterial() != Material.air && var11 != Blocks.bedrock && var11 != Blocks.end_portal && var11 != Blocks.end_portal_frame && var11 != Blocks.command_block)
                                {
                                    var18 = this.worldObj.func_147480_a(var20, var9, var10, true) || var18;
                                }
                            }
                        }
                    }

                    if (var18)
                    {
                        this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1012, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    }
                }
            }

            if (this.ticksExisted % 20 == 0)
            {
                this.heal(1.0F);
            }
        }
    }

    public void func_82206_m()
    {
        this.func_82215_s(220);
        this.setHealth(this.getMaxHealth() / 3.0F);
    }

    /**
     * Sets the Entity inside a web block.
     */
    public void setInWeb() {}

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int getTotalArmorValue()
    {
        return 4;
    }

    private double func_82214_u(int par1)
    {
        if (par1 <= 0)
        {
            return this.posX;
        }
        else
        {
            float var2 = (this.renderYawOffset + (float)(180 * (par1 - 1))) / 180.0F * (float)Math.PI;
            float var3 = MathHelper.cos(var2);
            return this.posX + (double)var3 * 1.3D;
        }
    }

    private double func_82208_v(int par1)
    {
        return par1 <= 0 ? this.posY + 3.0D : this.posY + 2.2D;
    }

    private double func_82213_w(int par1)
    {
        if (par1 <= 0)
        {
            return this.posZ;
        }
        else
        {
            float var2 = (this.renderYawOffset + (float)(180 * (par1 - 1))) / 180.0F * (float)Math.PI;
            float var3 = MathHelper.sin(var2);
            return this.posZ + (double)var3 * 1.3D;
        }
    }

    private float func_82204_b(float par1, float par2, float par3)
    {
        float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);

        if (var4 > par3)
        {
            var4 = par3;
        }

        if (var4 < -par3)
        {
            var4 = -par3;
        }

        return par1 + var4;
    }

    private void func_82216_a(int par1, EntityLivingBase par2EntityLivingBase)
    {
        this.func_82209_a(par1, par2EntityLivingBase.posX, par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight() * 0.5D, par2EntityLivingBase.posZ, par1 == 0 && this.rand.nextFloat() < 0.001F);
    }

    private void func_82209_a(int par1, double par2, double par4, double par6, boolean par8)
    {
        this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        double var9 = this.func_82214_u(par1);
        double var11 = this.func_82208_v(par1);
        double var13 = this.func_82213_w(par1);
        double var15 = par2 - var9;
        double var17 = par4 - var11;
        double var19 = par6 - var13;
        EntityWitherSkull var21 = new EntityWitherSkull(this.worldObj, this, var15, var17, var19);

        if (par8)
        {
            var21.setInvulnerable(true);
        }

        var21.posY = var11;
        var21.posX = var9;
        var21.posZ = var13;
        this.worldObj.spawnEntityInWorld(var21);
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.func_82216_a(0, par1EntityLivingBase);
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else if (par1DamageSource == DamageSource.drown)
        {
            return false;
        }
        else if (this.func_82212_n() > 0)
        {
            return false;
        }
        else
        {
            Entity var3;

            if (this.isArmored())
            {
                var3 = par1DamageSource.getSourceOfDamage();

                if (var3 instanceof EntityArrow)
                {
                    return false;
                }
            }

            var3 = par1DamageSource.getEntity();

            if (var3 != null && !(var3 instanceof EntityPlayer) && var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getCreatureAttribute() == this.getCreatureAttribute())
            {
                return false;
            }
            else
            {
                if (this.field_82222_j <= 0)
                {
                    this.field_82222_j = 20;
                }

                for (int var4 = 0; var4 < this.field_82224_i.length; ++var4)
                {
                    this.field_82224_i[var4] += 3;
                }

                return super.attackEntityFrom(par1DamageSource, par2);
            }
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        this.func_145779_a(Items.nether_star, 1);

        if (!this.worldObj.isClient)
        {
            Iterator var3 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50.0D, 100.0D, 50.0D)).iterator();

            while (var3.hasNext())
            {
                EntityPlayer var4 = (EntityPlayer)var3.next();
                var4.triggerAchievement(AchievementList.field_150964_J);
            }
        }
    }

    /**
     * Makes the entity despawn if requirements are reached
     */
    public void despawnEntity()
    {
        this.entityAge = 0;
    }

    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1) {}

    /**
     * adds a PotionEffect to the entity
     */
    public void addPotionEffect(PotionEffect par1PotionEffect) {}

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
    }

    public float func_82207_a(int par1)
    {
        return this.field_82221_e[par1];
    }

    public float func_82210_r(int par1)
    {
        return this.field_82220_d[par1];
    }

    public int func_82212_n()
    {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public void func_82215_s(int par1)
    {
        this.dataWatcher.updateObject(20, Integer.valueOf(par1));
    }

    /**
     * Returns the target entity ID if present, or -1 if not @param par1 The target offset, should be from 0-2
     */
    public int getWatchedTargetId(int par1)
    {
        return this.dataWatcher.getWatchableObjectInt(17 + par1);
    }

    public void func_82211_c(int par1, int par2)
    {
        this.dataWatcher.updateObject(17 + par1, Integer.valueOf(par2));
    }

    /**
     * Returns whether the wither is armored with its boss armor or not by checking whether its health is below half of
     * its maximum.
     */
    public boolean isArmored()
    {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    /**
     * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity par1Entity)
    {
        this.ridingEntity = null;
    }
}
