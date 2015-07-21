package net.minecraft.entity.item;

import java.util.Iterator;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityItem extends Entity
{
    private static final Logger logger = LogManager.getLogger();

    /**
     * The age of this EntityItem (used to animate it up and down as well as expire it)
     */
    public int age;
    public int delayBeforeCanPickup;

    /** The health of this EntityItem. (For example, damage for tools) */
    private int health;
    private String field_145801_f;
    private String field_145802_g;

    /** The EntityItem's random initial float height. */
    public float hoverStart;
    private static final String __OBFID = "CL_00001669";

    public EntityItem(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.health = 5;
        this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(par2, par4, par6);
        this.rotationYaw = (float)(Math.random() * 360.0D);
        this.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.motionY = 0.20000000298023224D;
        this.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
    }

    public EntityItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
    {
        this(par1World, par2, par4, par6);
        this.setEntityItemStack(par8ItemStack);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public EntityItem(World par1World)
    {
        super(par1World);
        this.health = 5;
        this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
        this.setSize(0.25F, 0.25F);
        this.yOffset = this.height / 2.0F;
    }

    protected void entityInit()
    {
        this.getDataWatcher().addObjectByDataType(10, 5);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.getEntityItem() == null)
        {
            this.setDead();
        }
        else
        {
            super.onUpdate();

            if (this.delayBeforeCanPickup > 0)
            {
                --this.delayBeforeCanPickup;
            }

            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033D;
            this.noClip = this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            boolean var1 = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;

            if (var1 || this.ticksExisted % 25 == 0)
            {
                if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava)
                {
                    this.motionY = 0.20000000298023224D;
                    this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
                    this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
                    this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
                }

                if (!this.worldObj.isClient)
                {
                    this.searchForOtherItemsNearby();
                }
            }

            float var2 = 0.98F;

            if (this.onGround)
            {
                var2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.98F;
            }

            this.motionX *= (double)var2;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= (double)var2;

            if (this.onGround)
            {
                this.motionY *= -0.5D;
            }

            ++this.age;

            if (!this.worldObj.isClient && this.age >= 6000)
            {
                this.setDead();
            }
        }
    }

    /**
     * Looks for other itemstacks nearby and tries to stack them together
     */
    private void searchForOtherItemsNearby()
    {
        Iterator var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5D, 0.0D, 0.5D)).iterator();

        while (var1.hasNext())
        {
            EntityItem var2 = (EntityItem)var1.next();
            this.combineItems(var2);
        }
    }

    /**
     * Tries to merge this item with the item passed as the parameter. Returns true if successful. Either this item or
     * the other item will  be removed from the world.
     */
    public boolean combineItems(EntityItem par1EntityItem)
    {
        if (par1EntityItem == this)
        {
            return false;
        }
        else if (par1EntityItem.isEntityAlive() && this.isEntityAlive())
        {
            ItemStack var2 = this.getEntityItem();
            ItemStack var3 = par1EntityItem.getEntityItem();

            if (var3.getItem() != var2.getItem())
            {
                return false;
            }
            else if (var3.hasTagCompound() ^ var2.hasTagCompound())
            {
                return false;
            }
            else if (var3.hasTagCompound() && !var3.getTagCompound().equals(var2.getTagCompound()))
            {
                return false;
            }
            else if (var3.getItem() == null)
            {
                return false;
            }
            else if (var3.getItem().getHasSubtypes() && var3.getItemDamage() != var2.getItemDamage())
            {
                return false;
            }
            else if (var3.stackSize < var2.stackSize)
            {
                return par1EntityItem.combineItems(this);
            }
            else if (var3.stackSize + var2.stackSize > var3.getMaxStackSize())
            {
                return false;
            }
            else
            {
                var3.stackSize += var2.stackSize;
                par1EntityItem.delayBeforeCanPickup = Math.max(par1EntityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
                par1EntityItem.age = Math.min(par1EntityItem.age, this.age);
                par1EntityItem.setEntityItemStack(var3);
                this.setDead();
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * sets the age of the item so that it'll despawn one minute after it has been dropped (instead of five). Used when
     * items are dropped from players in creative mode
     */
    public void setAgeToCreativeDespawnTime()
    {
        this.age = 4800;
    }

    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean handleWaterMovement()
    {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    /**
     * Will deal the specified amount of damage to the entity if the entity isn't immune to fire damage. Args:
     * amountDamage
     */
    protected void dealFireDamage(int par1)
    {
        this.attackEntityFrom(DamageSource.inFire, (float)par1);
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
        else if (this.getEntityItem() != null && this.getEntityItem().getItem() == Items.nether_star && par1DamageSource.isExplosion())
        {
            return false;
        }
        else
        {
            this.setBeenAttacked();
            this.health = (int)((float)this.health - par2);

            if (this.health <= 0)
            {
                this.setDead();
            }

            return false;
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("Health", (short)((byte)this.health));
        par1NBTTagCompound.setShort("Age", (short)this.age);

        if (this.func_145800_j() != null)
        {
            par1NBTTagCompound.setString("Thrower", this.field_145801_f);
        }

        if (this.func_145798_i() != null)
        {
            par1NBTTagCompound.setString("Owner", this.field_145802_g);
        }

        if (this.getEntityItem() != null)
        {
            par1NBTTagCompound.setTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.health = par1NBTTagCompound.getShort("Health") & 255;
        this.age = par1NBTTagCompound.getShort("Age");

        if (par1NBTTagCompound.hasKey("Owner"))
        {
            this.field_145802_g = par1NBTTagCompound.getString("Owner");
        }

        if (par1NBTTagCompound.hasKey("Thrower"))
        {
            this.field_145801_f = par1NBTTagCompound.getString("Thrower");
        }

        NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));

        if (this.getEntityItem() == null)
        {
            this.setDead();
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (!this.worldObj.isClient)
        {
            ItemStack var2 = this.getEntityItem();
            int var3 = var2.stackSize;

            if (this.delayBeforeCanPickup == 0 && (this.field_145802_g == null || 6000 - this.age <= 200 || this.field_145802_g.equals(par1EntityPlayer.getCommandSenderName())) && par1EntityPlayer.inventory.addItemStackToInventory(var2))
            {
                if (var2.getItem() == Item.getItemFromBlock(Blocks.log))
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
                }

                if (var2.getItem() == Item.getItemFromBlock(Blocks.log2))
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
                }

                if (var2.getItem() == Items.leather)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.killCow);
                }

                if (var2.getItem() == Items.diamond)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.diamonds);
                }

                if (var2.getItem() == Items.blaze_rod)
                {
                    par1EntityPlayer.triggerAchievement(AchievementList.blazeRod);
                }

                if (var2.getItem() == Items.diamond && this.func_145800_j() != null)
                {
                    EntityPlayer var4 = this.worldObj.getPlayerEntityByName(this.func_145800_j());

                    if (var4 != null && var4 != par1EntityPlayer)
                    {
                        var4.triggerAchievement(AchievementList.field_150966_x);
                    }
                }

                this.worldObj.playSoundAtEntity(par1EntityPlayer, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.onItemPickup(this, var3);

                if (var2.stackSize <= 0)
                {
                    this.setDead();
                }
            }
        }
    }

    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    public String getCommandSenderName()
    {
        return StatCollector.translateToLocal("item." + this.getEntityItem().getUnlocalizedName());
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }

    /**
     * Teleports the entity to another dimension. Params: Dimension number to teleport to
     */
    public void travelToDimension(int par1)
    {
        super.travelToDimension(par1);

        if (!this.worldObj.isClient)
        {
            this.searchForOtherItemsNearby();
        }
    }

    /**
     * Returns the ItemStack corresponding to the Entity (Note: if no item exists, will log an error but still return an
     * ItemStack containing Block.stone)
     */
    public ItemStack getEntityItem()
    {
        ItemStack var1 = this.getDataWatcher().getWatchableObjectItemStack(10);

        if (var1 == null)
        {
            if (this.worldObj != null)
            {
                logger.error("Item entity " + this.getEntityId() + " has no item?!");
            }

            return new ItemStack(Blocks.stone);
        }
        else
        {
            return var1;
        }
    }

    /**
     * Sets the ItemStack for this entity
     */
    public void setEntityItemStack(ItemStack par1ItemStack)
    {
        this.getDataWatcher().updateObject(10, par1ItemStack);
        this.getDataWatcher().setObjectWatched(10);
    }

    public String func_145798_i()
    {
        return this.field_145802_g;
    }

    public void func_145797_a(String p_145797_1_)
    {
        this.field_145802_g = p_145797_1_;
    }

    public String func_145800_j()
    {
        return this.field_145801_f;
    }

    public void func_145799_b(String p_145799_1_)
    {
        this.field_145801_f = p_145799_1_;
    }
}
