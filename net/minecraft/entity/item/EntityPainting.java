package net.minecraft.entity.item;

import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityPainting extends EntityHanging
{
    public EntityPainting.EnumArt art;
    private static final String __OBFID = "CL_00001556";

    public EntityPainting(World par1World)
    {
        super(par1World);
    }

    public EntityPainting(World par1World, int par2, int par3, int par4, int par5)
    {
        super(par1World, par2, par3, par4, par5);
        ArrayList var6 = new ArrayList();
        EntityPainting.EnumArt[] var7 = EntityPainting.EnumArt.values();
        int var8 = var7.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            EntityPainting.EnumArt var10 = var7[var9];
            this.art = var10;
            this.setDirection(par5);

            if (this.onValidSurface())
            {
                var6.add(var10);
            }
        }

        if (!var6.isEmpty())
        {
            this.art = (EntityPainting.EnumArt)var6.get(this.rand.nextInt(var6.size()));
        }

        this.setDirection(par5);
    }

    public EntityPainting(World par1World, int par2, int par3, int par4, int par5, String par6Str)
    {
        this(par1World, par2, par3, par4, par5);
        EntityPainting.EnumArt[] var7 = EntityPainting.EnumArt.values();
        int var8 = var7.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            EntityPainting.EnumArt var10 = var7[var9];

            if (var10.title.equals(par6Str))
            {
                this.art = var10;
                break;
            }
        }

        this.setDirection(par5);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setString("Motive", this.art.title);
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        String var2 = par1NBTTagCompound.getString("Motive");
        EntityPainting.EnumArt[] var3 = EntityPainting.EnumArt.values();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            EntityPainting.EnumArt var6 = var3[var5];

            if (var6.title.equals(var2))
            {
                this.art = var6;
            }
        }

        if (this.art == null)
        {
            this.art = EntityPainting.EnumArt.Kebab;
        }

        super.readEntityFromNBT(par1NBTTagCompound);
    }

    public int getWidthPixels()
    {
        return this.art.sizeX;
    }

    public int getHeightPixels()
    {
        return this.art.sizeY;
    }

    /**
     * Called when this entity is broken. Entity parameter may be null.
     */
    public void onBroken(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayer)
        {
            EntityPlayer var2 = (EntityPlayer)par1Entity;

            if (var2.capabilities.isCreativeMode)
            {
                return;
            }
        }

        this.entityDropItem(new ItemStack(Items.painting), 0.0F);
    }

    public static enum EnumArt
    {
        Kebab("Kebab", 0, "Kebab", 16, 16, 0, 0),
        Aztec("Aztec", 1, "Aztec", 16, 16, 16, 0),
        Alban("Alban", 2, "Alban", 16, 16, 32, 0),
        Aztec2("Aztec2", 3, "Aztec2", 16, 16, 48, 0),
        Bomb("Bomb", 4, "Bomb", 16, 16, 64, 0),
        Plant("Plant", 5, "Plant", 16, 16, 80, 0),
        Wasteland("Wasteland", 6, "Wasteland", 16, 16, 96, 0),
        Pool("Pool", 7, "Pool", 32, 16, 0, 32),
        Courbet("Courbet", 8, "Courbet", 32, 16, 32, 32),
        Sea("Sea", 9, "Sea", 32, 16, 64, 32),
        Sunset("Sunset", 10, "Sunset", 32, 16, 96, 32),
        Creebet("Creebet", 11, "Creebet", 32, 16, 128, 32),
        Wanderer("Wanderer", 12, "Wanderer", 16, 32, 0, 64),
        Graham("Graham", 13, "Graham", 16, 32, 16, 64),
        Match("Match", 14, "Match", 32, 32, 0, 128),
        Bust("Bust", 15, "Bust", 32, 32, 32, 128),
        Stage("Stage", 16, "Stage", 32, 32, 64, 128),
        Void("Void", 17, "Void", 32, 32, 96, 128),
        SkullAndRoses("SkullAndRoses", 18, "SkullAndRoses", 32, 32, 128, 128),
        Wither("Wither", 19, "Wither", 32, 32, 160, 128),
        Fighters("Fighters", 20, "Fighters", 64, 32, 0, 96),
        Pointer("Pointer", 21, "Pointer", 64, 64, 0, 192),
        Pigscene("Pigscene", 22, "Pigscene", 64, 64, 64, 192),
        BurningSkull("BurningSkull", 23, "BurningSkull", 64, 64, 128, 192),
        Skeleton("Skeleton", 24, "Skeleton", 64, 48, 192, 64),
        DonkeyKong("DonkeyKong", 25, "DonkeyKong", 64, 48, 192, 112);
        public static final int maxArtTitleLength = "SkullAndRoses".length();
        public final String title;
        public final int sizeX;
        public final int sizeY;
        public final int offsetX;
        public final int offsetY;

        private static final EntityPainting.EnumArt[] $VALUES = new EntityPainting.EnumArt[]{Kebab, Aztec, Alban, Aztec2, Bomb, Plant, Wasteland, Pool, Courbet, Sea, Sunset, Creebet, Wanderer, Graham, Match, Bust, Stage, Void, SkullAndRoses, Wither, Fighters, Pointer, Pigscene, BurningSkull, Skeleton, DonkeyKong};
        private static final String __OBFID = "CL_00001557";

        private EnumArt(String par1Str, int par2, String par3Str, int par4, int par5, int par6, int par7)
        {
            this.title = par3Str;
            this.sizeX = par4;
            this.sizeY = par5;
            this.offsetX = par6;
            this.offsetY = par7;
        }
    }
}
