package net.minecraft.init;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Bootstrap
{
    private static boolean field_151355_a = false;
    private static final String __OBFID = "CL_00001397";

    static void func_151353_a()
    {
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new BehaviorProjectileDispense()
        {
            private static final String __OBFID = "CL_00001398";
            protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
            {
                EntityArrow var3 = new EntityArrow(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
                var3.canBePickedUp = 1;
                return var3;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new BehaviorProjectileDispense()
        {
            private static final String __OBFID = "CL_00001404";
            protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
            {
                return new EntityEgg(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new BehaviorProjectileDispense()
        {
            private static final String __OBFID = "CL_00001405";
            protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
            {
                return new EntitySnowball(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new BehaviorProjectileDispense()
        {
            private static final String __OBFID = "CL_00001406";
            protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
            {
                return new EntityExpBottle(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
            }
            protected float func_82498_a()
            {
                return super.func_82498_a() * 0.5F;
            }
            protected float func_82500_b()
            {
                return super.func_82500_b() * 1.25F;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.potionitem, new IBehaviorDispenseItem()
        {
            private final BehaviorDefaultDispenseItem field_150843_b = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00001407";
            public ItemStack dispense(IBlockSource par1IBlockSource, final ItemStack par2ItemStack)
            {
                return ItemPotion.isSplash(par2ItemStack.getItemDamage()) ? (new BehaviorProjectileDispense()
                {
                    private static final String __OBFID = "CL_00001408";
                    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
                    {
                        return new EntityPotion(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ(), par2ItemStack.copy());
                    }
                    protected float func_82498_a()
                    {
                        return super.func_82498_a() * 0.5F;
                    }
                    protected float func_82500_b()
                    {
                        return super.func_82500_b() * 1.25F;
                    }
                }).dispense(par1IBlockSource, par2ItemStack): this.field_150843_b.dispense(par1IBlockSource, par2ItemStack);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new BehaviorDefaultDispenseItem()
        {
            private static final String __OBFID = "CL_00001410";
            public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
            {
                EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
                double var4 = par1IBlockSource.getX() + (double)var3.getFrontOffsetX();
                double var6 = (double)((float)par1IBlockSource.getYInt() + 0.2F);
                double var8 = par1IBlockSource.getZ() + (double)var3.getFrontOffsetZ();
                Entity var10 = ItemMonsterPlacer.spawnCreature(par1IBlockSource.getWorld(), par2ItemStack.getItemDamage(), var4, var6, var8);

                if (var10 instanceof EntityLivingBase && par2ItemStack.hasDisplayName())
                {
                    ((EntityLiving)var10).setCustomNameTag(par2ItemStack.getDisplayName());
                }

                par2ItemStack.splitStack(1);
                return par2ItemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new BehaviorDefaultDispenseItem()
        {
            private static final String __OBFID = "CL_00001411";
            public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
            {
                EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
                double var4 = par1IBlockSource.getX() + (double)var3.getFrontOffsetX();
                double var6 = (double)((float)par1IBlockSource.getYInt() + 0.2F);
                double var8 = par1IBlockSource.getZ() + (double)var3.getFrontOffsetZ();
                EntityFireworkRocket var10 = new EntityFireworkRocket(par1IBlockSource.getWorld(), var4, var6, var8, par2ItemStack);
                par1IBlockSource.getWorld().spawnEntityInWorld(var10);
                par2ItemStack.splitStack(1);
                return par2ItemStack;
            }
            protected void playDispenseSound(IBlockSource par1IBlockSource)
            {
                par1IBlockSource.getWorld().playAuxSFX(1002, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new BehaviorDefaultDispenseItem()
        {
            private static final String __OBFID = "CL_00001412";
            public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
            {
                EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
                IPosition var4 = BlockDispenser.func_149939_a(par1IBlockSource);
                double var5 = var4.getX() + (double)((float)var3.getFrontOffsetX() * 0.3F);
                double var7 = var4.getY() + (double)((float)var3.getFrontOffsetX() * 0.3F);
                double var9 = var4.getZ() + (double)((float)var3.getFrontOffsetZ() * 0.3F);
                World var11 = par1IBlockSource.getWorld();
                Random var12 = var11.rand;
                double var13 = var12.nextGaussian() * 0.05D + (double)var3.getFrontOffsetX();
                double var15 = var12.nextGaussian() * 0.05D + (double)var3.getFrontOffsetY();
                double var17 = var12.nextGaussian() * 0.05D + (double)var3.getFrontOffsetZ();
                var11.spawnEntityInWorld(new EntitySmallFireball(var11, var5, var7, var9, var13, var15, var17));
                par2ItemStack.splitStack(1);
                return par2ItemStack;
            }
            protected void playDispenseSound(IBlockSource par1IBlockSource)
            {
                par1IBlockSource.getWorld().playAuxSFX(1009, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new BehaviorDefaultDispenseItem()
        {
            private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00001413";
            public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
            {
                EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
                World var4 = par1IBlockSource.getWorld();
                double var5 = par1IBlockSource.getX() + (double)((float)var3.getFrontOffsetX() * 1.125F);
                double var7 = par1IBlockSource.getY() + (double)((float)var3.getFrontOffsetY() * 1.125F);
                double var9 = par1IBlockSource.getZ() + (double)((float)var3.getFrontOffsetZ() * 1.125F);
                int var11 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
                int var12 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
                int var13 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
                Material var14 = var4.getBlock(var11, var12, var13).getMaterial();
                double var15;

                if (Material.water.equals(var14))
                {
                    var15 = 1.0D;
                }
                else
                {
                    if (!Material.air.equals(var14) || !Material.water.equals(var4.getBlock(var11, var12 - 1, var13).getMaterial()))
                    {
                        return this.field_150842_b.dispense(par1IBlockSource, par2ItemStack);
                    }

                    var15 = 0.0D;
                }

                EntityBoat var17 = new EntityBoat(var4, var5, var7 + var15, var9);
                var4.spawnEntityInWorld(var17);
                par2ItemStack.splitStack(1);
                return par2ItemStack;
            }
            protected void playDispenseSound(IBlockSource par1IBlockSource)
            {
                par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
            }
        });
        BehaviorDefaultDispenseItem var0 = new BehaviorDefaultDispenseItem()
        {
            private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00001399";
            public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
            {
                ItemBucket var3 = (ItemBucket)par2ItemStack.getItem();
                int var4 = par1IBlockSource.getXInt();
                int var5 = par1IBlockSource.getYInt();
                int var6 = par1IBlockSource.getZInt();
                EnumFacing var7 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());

                if (var3.tryPlaceContainedLiquid(par1IBlockSource.getWorld(), var4 + var7.getFrontOffsetX(), var5 + var7.getFrontOffsetY(), var6 + var7.getFrontOffsetZ()))
                {
                    par2ItemStack.func_150996_a(Items.bucket);
                    par2ItemStack.stackSize = 1;
                    return par2ItemStack;
                }
                else
                {
                    return this.field_150841_b.dispense(par1IBlockSource, par2ItemStack);
                }
            }
        };
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, var0);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, var0);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem()
        {
            private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00001400";
            public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
            {
                EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
                World var4 = par1IBlockSource.getWorld();
                int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
                int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
                int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
                Material var8 = var4.getBlock(var5, var6, var7).getMaterial();
                int var9 = var4.getBlockMetadata(var5, var6, var7);
                Item var10;

                if (Material.water.equals(var8) && var9 == 0)
                {
                    var10 = Items.water_bucket;
                }
                else
                {
                    if (!Material.lava.equals(var8) || var9 != 0)
                    {
                        return super.dispenseStack(par1IBlockSource, par2ItemStack);
                    }

                    var10 = Items.lava_bucket;
                }

                var4.setBlockToAir(var5, var6, var7);

                if (--par2ItemStack.stackSize == 0)
                {
                    par2ItemStack.func_150996_a(var10);
                    par2ItemStack.stackSize = 1;
                }
                else if (((TileEntityDispenser)par1IBlockSource.getBlockTileEntity()).func_146019_a(new ItemStack(var10)) < 0)
                {
                    this.field_150840_b.dispense(par1IBlockSource, new ItemStack(var10));
                }

                return par2ItemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new BehaviorDefaultDispenseItem()
        {
            private boolean field_150839_b = true;
            private static final String __OBFID = "CL_00001401";
            protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
            {
                EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
                World var4 = par1IBlockSource.getWorld();
                int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
                int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
                int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();

                if (var4.isAirBlock(var5, var6, var7))
                {
                    var4.setBlock(var5, var6, var7, Blocks.fire);

                    if (par2ItemStack.attemptDamageItem(1, var4.rand))
                    {
                        par2ItemStack.stackSize = 0;
                    }
                }
                else if (var4.getBlock(var5, var6, var7) == Blocks.tnt)
                {
                    Blocks.tnt.onBlockDestroyedByPlayer(var4, var5, var6, var7, 1);
                    var4.setBlockToAir(var5, var6, var7);
                }
                else
                {
                    this.field_150839_b = false;
                }

                return par2ItemStack;
            }
            protected void playDispenseSound(IBlockSource par1IBlockSource)
            {
                if (this.field_150839_b)
                {
                    par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
                }
                else
                {
                    par1IBlockSource.getWorld().playAuxSFX(1001, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new BehaviorDefaultDispenseItem()
        {
            private boolean field_150838_b = true;
            private static final String __OBFID = "CL_00001402";
            protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
            {
                if (par2ItemStack.getItemDamage() == 15)
                {
                    EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
                    World var4 = par1IBlockSource.getWorld();
                    int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
                    int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
                    int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();

                    if (ItemDye.func_150919_a(par2ItemStack, var4, var5, var6, var7))
                    {
                        if (!var4.isClient)
                        {
                            var4.playAuxSFX(2005, var5, var6, var7, 0);
                        }
                    }
                    else
                    {
                        this.field_150838_b = false;
                    }

                    return par2ItemStack;
                }
                else
                {
                    return super.dispenseStack(par1IBlockSource, par2ItemStack);
                }
            }
            protected void playDispenseSound(IBlockSource par1IBlockSource)
            {
                if (this.field_150838_b)
                {
                    par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
                }
                else
                {
                    par1IBlockSource.getWorld().playAuxSFX(1001, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new BehaviorDefaultDispenseItem()
        {
            private static final String __OBFID = "CL_00001403";
            protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
            {
                EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
                World var4 = par1IBlockSource.getWorld();
                int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
                int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
                int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
                EntityTNTPrimed var8 = new EntityTNTPrimed(var4, (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), (double)((float)var7 + 0.5F), (EntityLivingBase)null);
                var4.spawnEntityInWorld(var8);
                --par2ItemStack.stackSize;
                return par2ItemStack;
            }
        });
    }

    public static void func_151354_b()
    {
        if (!field_151355_a)
        {
            field_151355_a = true;
            Block.registerBlocks();
            BlockFire.func_149843_e();
            Item.registerItems();
            StatList.func_151178_a();
            func_151353_a();
        }
    }
}
