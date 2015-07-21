package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBucket extends Item
{
    /** field for checking if the bucket has been filled. */
    private Block isFull;
    private static final String __OBFID = "CL_00000000";

    public ItemBucket(Block p_i45331_1_)
    {
        this.maxStackSize = 1;
        this.isFull = p_i45331_1_;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        boolean var4 = this.isFull == Blocks.air;
        MovingObjectPosition var5 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, var4);

        if (var5 == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (var5.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int var6 = var5.blockX;
                int var7 = var5.blockY;
                int var8 = var5.blockZ;

                if (!par2World.canMineBlock(par3EntityPlayer, var6, var7, var8))
                {
                    return par1ItemStack;
                }

                if (var4)
                {
                    if (!par3EntityPlayer.canPlayerEdit(var6, var7, var8, var5.sideHit, par1ItemStack))
                    {
                        return par1ItemStack;
                    }

                    Material var9 = par2World.getBlock(var6, var7, var8).getMaterial();
                    int var10 = par2World.getBlockMetadata(var6, var7, var8);

                    if (var9 == Material.water && var10 == 0)
                    {
                        par2World.setBlockToAir(var6, var7, var8);
                        return this.func_150910_a(par1ItemStack, par3EntityPlayer, Items.water_bucket);
                    }

                    if (var9 == Material.lava && var10 == 0)
                    {
                        par2World.setBlockToAir(var6, var7, var8);
                        return this.func_150910_a(par1ItemStack, par3EntityPlayer, Items.lava_bucket);
                    }
                }
                else
                {
                    if (this.isFull == Blocks.air)
                    {
                        return new ItemStack(Items.bucket);
                    }

                    if (var5.sideHit == 0)
                    {
                        --var7;
                    }

                    if (var5.sideHit == 1)
                    {
                        ++var7;
                    }

                    if (var5.sideHit == 2)
                    {
                        --var8;
                    }

                    if (var5.sideHit == 3)
                    {
                        ++var8;
                    }

                    if (var5.sideHit == 4)
                    {
                        --var6;
                    }

                    if (var5.sideHit == 5)
                    {
                        ++var6;
                    }

                    if (!par3EntityPlayer.canPlayerEdit(var6, var7, var8, var5.sideHit, par1ItemStack))
                    {
                        return par1ItemStack;
                    }

                    if (this.tryPlaceContainedLiquid(par2World, var6, var7, var8) && !par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        return new ItemStack(Items.bucket);
                    }
                }
            }

            return par1ItemStack;
        }
    }

    private ItemStack func_150910_a(ItemStack p_150910_1_, EntityPlayer p_150910_2_, Item p_150910_3_)
    {
        if (p_150910_2_.capabilities.isCreativeMode)
        {
            return p_150910_1_;
        }
        else if (--p_150910_1_.stackSize <= 0)
        {
            return new ItemStack(p_150910_3_);
        }
        else
        {
            if (!p_150910_2_.inventory.addItemStackToInventory(new ItemStack(p_150910_3_)))
            {
                p_150910_2_.dropPlayerItemWithRandomChoice(new ItemStack(p_150910_3_, 1, 0), false);
            }

            return p_150910_1_;
        }
    }

    /**
     * Attempts to place the liquid contained inside the bucket.
     */
    public boolean tryPlaceContainedLiquid(World par1World, int par2, int par3, int par4)
    {
        if (this.isFull == Blocks.air)
        {
            return false;
        }
        else
        {
            Material var5 = par1World.getBlock(par2, par3, par4).getMaterial();
            boolean var6 = !var5.isSolid();

            if (!par1World.isAirBlock(par2, par3, par4) && !var6)
            {
                return false;
            }
            else
            {
                if (par1World.provider.isHellWorld && this.isFull == Blocks.flowing_water)
                {
                    par1World.playSoundEffect((double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);

                    for (int var7 = 0; var7 < 8; ++var7)
                    {
                        par1World.spawnParticle("largesmoke", (double)par2 + Math.random(), (double)par3 + Math.random(), (double)par4 + Math.random(), 0.0D, 0.0D, 0.0D);
                    }
                }
                else
                {
                    if (!par1World.isClient && var6 && !var5.isLiquid())
                    {
                        par1World.func_147480_a(par2, par3, par4, true);
                    }

                    par1World.setBlock(par2, par3, par4, this.isFull, 0, 3);
                }

                return true;
            }
        }
    }
}
