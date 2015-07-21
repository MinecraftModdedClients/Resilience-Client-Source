package net.minecraft.world.gen.structure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenVillage extends MapGenStructure
{
    /** A list of all the biomes villages can spawn in. */
    public static final List villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] {BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.field_150588_X});

    /** World terrain type, 0 for normal, 1 for flat map */
    private int terrainType;
    private int field_82665_g;
    private int field_82666_h;
    private static final String __OBFID = "CL_00000514";

    public MapGenVillage()
    {
        this.field_82665_g = 32;
        this.field_82666_h = 8;
    }

    public MapGenVillage(Map par1Map)
    {
        this();
        Iterator var2 = par1Map.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();

            if (((String)var3.getKey()).equals("size"))
            {
                this.terrainType = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.terrainType, 0);
            }
            else if (((String)var3.getKey()).equals("distance"))
            {
                this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_82665_g, this.field_82666_h + 1);
            }
        }
    }

    public String func_143025_a()
    {
        return "Village";
    }

    protected boolean canSpawnStructureAtCoords(int par1, int par2)
    {
        int var3 = par1;
        int var4 = par2;

        if (par1 < 0)
        {
            par1 -= this.field_82665_g - 1;
        }

        if (par2 < 0)
        {
            par2 -= this.field_82665_g - 1;
        }

        int var5 = par1 / this.field_82665_g;
        int var6 = par2 / this.field_82665_g;
        Random var7 = this.worldObj.setRandomSeed(var5, var6, 10387312);
        var5 *= this.field_82665_g;
        var6 *= this.field_82665_g;
        var5 += var7.nextInt(this.field_82665_g - this.field_82666_h);
        var6 += var7.nextInt(this.field_82665_g - this.field_82666_h);

        if (var3 == var5 && var4 == var6)
        {
            boolean var8 = this.worldObj.getWorldChunkManager().areBiomesViable(var3 * 16 + 8, var4 * 16 + 8, 0, villageSpawnBiomes);

            if (var8)
            {
                return true;
            }
        }

        return false;
    }

    protected StructureStart getStructureStart(int par1, int par2)
    {
        return new MapGenVillage.Start(this.worldObj, this.rand, par1, par2, this.terrainType);
    }

    public static class Start extends StructureStart
    {
        private boolean hasMoreThanTwoComponents;
        private static final String __OBFID = "CL_00000515";

        public Start() {}

        public Start(World par1World, Random par2Random, int par3, int par4, int par5)
        {
            super(par3, par4);
            List var6 = StructureVillagePieces.getStructureVillageWeightedPieceList(par2Random, par5);
            StructureVillagePieces.Start var7 = new StructureVillagePieces.Start(par1World.getWorldChunkManager(), 0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2, var6, par5);
            this.components.add(var7);
            var7.buildComponent(var7, this.components, par2Random);
            List var8 = var7.field_74930_j;
            List var9 = var7.field_74932_i;
            int var10;

            while (!var8.isEmpty() || !var9.isEmpty())
            {
                StructureComponent var11;

                if (var8.isEmpty())
                {
                    var10 = par2Random.nextInt(var9.size());
                    var11 = (StructureComponent)var9.remove(var10);
                    var11.buildComponent(var7, this.components, par2Random);
                }
                else
                {
                    var10 = par2Random.nextInt(var8.size());
                    var11 = (StructureComponent)var8.remove(var10);
                    var11.buildComponent(var7, this.components, par2Random);
                }
            }

            this.updateBoundingBox();
            var10 = 0;
            Iterator var13 = this.components.iterator();

            while (var13.hasNext())
            {
                StructureComponent var12 = (StructureComponent)var13.next();

                if (!(var12 instanceof StructureVillagePieces.Road))
                {
                    ++var10;
                }
            }

            this.hasMoreThanTwoComponents = var10 > 2;
        }

        public boolean isSizeableStructure()
        {
            return this.hasMoreThanTwoComponents;
        }

        public void func_143022_a(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143022_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
        }

        public void func_143017_b(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143017_b(par1NBTTagCompound);
            this.hasMoreThanTwoComponents = par1NBTTagCompound.getBoolean("Valid");
        }
    }
}
