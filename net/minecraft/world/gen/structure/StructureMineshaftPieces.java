package net.minecraft.world.gen.structure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureMineshaftPieces
{
    /** List of contents that can generate in Mineshafts. */
    private static final WeightedRandomChestContent[] mineshaftChestContents = new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.dye, 4, 4, 9, 5), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1), new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1)};
    private static final String __OBFID = "CL_00000444";

    public static void func_143048_a()
    {
        MapGenStructureIO.func_143031_a(StructureMineshaftPieces.Corridor.class, "MSCorridor");
        MapGenStructureIO.func_143031_a(StructureMineshaftPieces.Cross.class, "MSCrossing");
        MapGenStructureIO.func_143031_a(StructureMineshaftPieces.Room.class, "MSRoom");
        MapGenStructureIO.func_143031_a(StructureMineshaftPieces.Stairs.class, "MSStairs");
    }

    private static StructureComponent getRandomComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        int var7 = par1Random.nextInt(100);
        StructureBoundingBox var8;

        if (var7 >= 80)
        {
            var8 = StructureMineshaftPieces.Cross.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);

            if (var8 != null)
            {
                return new StructureMineshaftPieces.Cross(par6, par1Random, var8, par5);
            }
        }
        else if (var7 >= 70)
        {
            var8 = StructureMineshaftPieces.Stairs.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);

            if (var8 != null)
            {
                return new StructureMineshaftPieces.Stairs(par6, par1Random, var8, par5);
            }
        }
        else
        {
            var8 = StructureMineshaftPieces.Corridor.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);

            if (var8 != null)
            {
                return new StructureMineshaftPieces.Corridor(par6, par1Random, var8, par5);
            }
        }

        return null;
    }

    private static StructureComponent getNextMineShaftComponent(StructureComponent par0StructureComponent, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 8)
        {
            return null;
        }
        else if (Math.abs(par3 - par0StructureComponent.getBoundingBox().minX) <= 80 && Math.abs(par5 - par0StructureComponent.getBoundingBox().minZ) <= 80)
        {
            StructureComponent var8 = getRandomComponent(par1List, par2Random, par3, par4, par5, par6, par7 + 1);

            if (var8 != null)
            {
                par1List.add(var8);
                var8.buildComponent(par0StructureComponent, par1List, par2Random);
            }

            return var8;
        }
        else
        {
            return null;
        }
    }

    public static class Cross extends StructureComponent
    {
        private int corridorDirection;
        private boolean isMultipleFloors;
        private static final String __OBFID = "CL_00000446";

        public Cross() {}

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
        {
            par1NBTTagCompound.setBoolean("tf", this.isMultipleFloors);
            par1NBTTagCompound.setInteger("D", this.corridorDirection);
        }

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
        {
            this.isMultipleFloors = par1NBTTagCompound.getBoolean("tf");
            this.corridorDirection = par1NBTTagCompound.getInteger("D");
        }

        public Cross(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.corridorDirection = par4;
            this.boundingBox = par3StructureBoundingBox;
            this.isMultipleFloors = par3StructureBoundingBox.getYSize() > 3;
        }

        public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
        {
            StructureBoundingBox var6 = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);

            if (par1Random.nextInt(4) == 0)
            {
                var6.maxY += 4;
            }

            switch (par5)
            {
                case 0:
                    var6.minX = par2 - 1;
                    var6.maxX = par2 + 3;
                    var6.maxZ = par4 + 4;
                    break;

                case 1:
                    var6.minX = par2 - 4;
                    var6.minZ = par4 - 1;
                    var6.maxZ = par4 + 3;
                    break;

                case 2:
                    var6.minX = par2 - 1;
                    var6.maxX = par2 + 3;
                    var6.minZ = par4 - 4;
                    break;

                case 3:
                    var6.maxX = par2 + 4;
                    var6.minZ = par4 - 1;
                    var6.maxZ = par4 + 3;
            }

            return StructureComponent.findIntersecting(par0List, var6) != null ? null : var6;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            int var4 = this.getComponentType();

            switch (this.corridorDirection)
            {
                case 0:
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, var4);
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, var4);
                    break;

                case 1:
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, var4);
                    break;

                case 2:
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, var4);
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, var4);
                    break;

                case 3:
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, var4);
            }

            if (this.isMultipleFloors)
            {
                if (par3Random.nextBoolean())
                {
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, 2, var4);
                }

                if (par3Random.nextBoolean())
                {
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, 1, var4);
                }

                if (par3Random.nextBoolean())
                {
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, 3, var4);
                }

                if (par3Random.nextBoolean())
                {
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, 0, var4);
                }
            }
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox))
            {
                return false;
            }
            else
            {
                if (this.isMultipleFloors)
                {
                    this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
                }
                else
                {
                    this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
                }

                this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks, Blocks.air, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks, Blocks.air, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks, Blocks.air, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks, Blocks.air, false);

                for (int var4 = this.boundingBox.minX; var4 <= this.boundingBox.maxX; ++var4)
                {
                    for (int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; ++var5)
                    {
                        if (this.func_151548_a(par1World, var4, this.boundingBox.minY - 1, var5, par3StructureBoundingBox).getMaterial() == Material.air)
                        {
                            this.func_151550_a(par1World, Blocks.planks, 0, var4, this.boundingBox.minY - 1, var5, par3StructureBoundingBox);
                        }
                    }
                }

                return true;
            }
        }
    }

    public static class Room extends StructureComponent
    {
        private List roomsLinkedToTheRoom = new LinkedList();
        private static final String __OBFID = "CL_00000447";

        public Room() {}

        public Room(int par1, Random par2Random, int par3, int par4)
        {
            super(par1);
            this.boundingBox = new StructureBoundingBox(par3, 50, par4, par3 + 7 + par2Random.nextInt(6), 54 + par2Random.nextInt(6), par4 + 7 + par2Random.nextInt(6));
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            int var4 = this.getComponentType();
            int var6 = this.boundingBox.getYSize() - 3 - 1;

            if (var6 <= 0)
            {
                var6 = 1;
            }

            int var5;
            StructureComponent var7;
            StructureBoundingBox var8;

            for (var5 = 0; var5 < this.boundingBox.getXSize(); var5 += 4)
            {
                var5 += par3Random.nextInt(this.boundingBox.getXSize());

                if (var5 + 3 > this.boundingBox.getXSize())
                {
                    break;
                }

                var7 = StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + var5, this.boundingBox.minY + par3Random.nextInt(var6) + 1, this.boundingBox.minZ - 1, 2, var4);

                if (var7 != null)
                {
                    var8 = var7.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.minZ, var8.maxX, var8.maxY, this.boundingBox.minZ + 1));
                }
            }

            for (var5 = 0; var5 < this.boundingBox.getXSize(); var5 += 4)
            {
                var5 += par3Random.nextInt(this.boundingBox.getXSize());

                if (var5 + 3 > this.boundingBox.getXSize())
                {
                    break;
                }

                var7 = StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX + var5, this.boundingBox.minY + par3Random.nextInt(var6) + 1, this.boundingBox.maxZ + 1, 0, var4);

                if (var7 != null)
                {
                    var8 = var7.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.maxZ - 1, var8.maxX, var8.maxY, this.boundingBox.maxZ));
                }
            }

            for (var5 = 0; var5 < this.boundingBox.getZSize(); var5 += 4)
            {
                var5 += par3Random.nextInt(this.boundingBox.getZSize());

                if (var5 + 3 > this.boundingBox.getZSize())
                {
                    break;
                }

                var7 = StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par3Random.nextInt(var6) + 1, this.boundingBox.minZ + var5, 1, var4);

                if (var7 != null)
                {
                    var8 = var7.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, var8.minY, var8.minZ, this.boundingBox.minX + 1, var8.maxY, var8.maxZ));
                }
            }

            for (var5 = 0; var5 < this.boundingBox.getZSize(); var5 += 4)
            {
                var5 += par3Random.nextInt(this.boundingBox.getZSize());

                if (var5 + 3 > this.boundingBox.getZSize())
                {
                    break;
                }

                var7 = StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par3Random.nextInt(var6) + 1, this.boundingBox.minZ + var5, 3, var4);

                if (var7 != null)
                {
                    var8 = var7.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, var8.minY, var8.minZ, this.boundingBox.maxX, var8.maxY, var8.maxZ));
                }
            }
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox))
            {
                return false;
            }
            else
            {
                this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.dirt, Blocks.air, true);
                this.func_151549_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
                Iterator var4 = this.roomsLinkedToTheRoom.iterator();

                while (var4.hasNext())
                {
                    StructureBoundingBox var5 = (StructureBoundingBox)var4.next();
                    this.func_151549_a(par1World, par3StructureBoundingBox, var5.minX, var5.maxY - 2, var5.minZ, var5.maxX, var5.maxY, var5.maxZ, Blocks.air, Blocks.air, false);
                }

                this.func_151547_a(par1World, par3StructureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air, false);
                return true;
            }
        }

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
        {
            NBTTagList var2 = new NBTTagList();
            Iterator var3 = this.roomsLinkedToTheRoom.iterator();

            while (var3.hasNext())
            {
                StructureBoundingBox var4 = (StructureBoundingBox)var3.next();
                var2.appendTag(var4.func_151535_h());
            }

            par1NBTTagCompound.setTag("Entrances", var2);
        }

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
        {
            NBTTagList var2 = par1NBTTagCompound.getTagList("Entrances", 11);

            for (int var3 = 0; var3 < var2.tagCount(); ++var3)
            {
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var2.func_150306_c(var3)));
            }
        }
    }

    public static class Corridor extends StructureComponent
    {
        private boolean hasRails;
        private boolean hasSpiders;
        private boolean spawnerPlaced;
        private int sectionCount;
        private static final String __OBFID = "CL_00000445";

        public Corridor() {}

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
        {
            par1NBTTagCompound.setBoolean("hr", this.hasRails);
            par1NBTTagCompound.setBoolean("sc", this.hasSpiders);
            par1NBTTagCompound.setBoolean("hps", this.spawnerPlaced);
            par1NBTTagCompound.setInteger("Num", this.sectionCount);
        }

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
        {
            this.hasRails = par1NBTTagCompound.getBoolean("hr");
            this.hasSpiders = par1NBTTagCompound.getBoolean("sc");
            this.spawnerPlaced = par1NBTTagCompound.getBoolean("hps");
            this.sectionCount = par1NBTTagCompound.getInteger("Num");
        }

        public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
            this.hasRails = par2Random.nextInt(3) == 0;
            this.hasSpiders = !this.hasRails && par2Random.nextInt(23) == 0;

            if (this.coordBaseMode != 2 && this.coordBaseMode != 0)
            {
                this.sectionCount = par3StructureBoundingBox.getXSize() / 5;
            }
            else
            {
                this.sectionCount = par3StructureBoundingBox.getZSize() / 5;
            }
        }

        public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
        {
            StructureBoundingBox var6 = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);
            int var7;

            for (var7 = par1Random.nextInt(3) + 2; var7 > 0; --var7)
            {
                int var8 = var7 * 5;

                switch (par5)
                {
                    case 0:
                        var6.maxX = par2 + 2;
                        var6.maxZ = par4 + (var8 - 1);
                        break;

                    case 1:
                        var6.minX = par2 - (var8 - 1);
                        var6.maxZ = par4 + 2;
                        break;

                    case 2:
                        var6.maxX = par2 + 2;
                        var6.minZ = par4 - (var8 - 1);
                        break;

                    case 3:
                        var6.maxX = par2 + (var8 - 1);
                        var6.maxZ = par4 + 2;
                }

                if (StructureComponent.findIntersecting(par0List, var6) == null)
                {
                    break;
                }
            }

            return var7 > 0 ? var6 : null;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            int var4 = this.getComponentType();
            int var5 = par3Random.nextInt(4);

            switch (this.coordBaseMode)
            {
                case 0:
                    if (var5 <= 1)
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, var4);
                    }
                    else if (var5 == 2)
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ - 3, 1, var4);
                    }
                    else
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ - 3, 3, var4);
                    }

                    break;

                case 1:
                    if (var5 <= 1)
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
                    }
                    else if (var5 == 2)
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ - 1, 2, var4);
                    }
                    else
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ + 1, 0, var4);
                    }

                    break;

                case 2:
                    if (var5 <= 1)
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, var4);
                    }
                    else if (var5 == 2)
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ, 1, var4);
                    }
                    else
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ, 3, var4);
                    }

                    break;

                case 3:
                    if (var5 <= 1)
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
                    }
                    else if (var5 == 2)
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.minZ - 1, 2, var4);
                    }
                    else
                    {
                        StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + par3Random.nextInt(3), this.boundingBox.maxZ + 1, 0, var4);
                    }
            }

            if (var4 < 8)
            {
                int var6;
                int var7;

                if (this.coordBaseMode != 2 && this.coordBaseMode != 0)
                {
                    for (var6 = this.boundingBox.minX + 3; var6 + 3 <= this.boundingBox.maxX; var6 += 5)
                    {
                        var7 = par3Random.nextInt(5);

                        if (var7 == 0)
                        {
                            StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, var6, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4 + 1);
                        }
                        else if (var7 == 1)
                        {
                            StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, var6, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4 + 1);
                        }
                    }
                }
                else
                {
                    for (var6 = this.boundingBox.minZ + 3; var6 + 3 <= this.boundingBox.maxZ; var6 += 5)
                    {
                        var7 = par3Random.nextInt(5);

                        if (var7 == 0)
                        {
                            StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, var6, 1, var4 + 1);
                        }
                        else if (var7 == 1)
                        {
                            StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, var6, 3, var4 + 1);
                        }
                    }
                }
            }
        }

        protected boolean generateStructureChestContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8)
        {
            int var9 = this.getXWithOffset(par4, par6);
            int var10 = this.getYWithOffset(par5);
            int var11 = this.getZWithOffset(par4, par6);

            if (par2StructureBoundingBox.isVecInside(var9, var10, var11) && par1World.getBlock(var9, var10, var11).getMaterial() == Material.air)
            {
                int var12 = par3Random.nextBoolean() ? 1 : 0;
                par1World.setBlock(var9, var10, var11, Blocks.rail, this.func_151555_a(Blocks.rail, var12), 2);
                EntityMinecartChest var13 = new EntityMinecartChest(par1World, (double)((float)var9 + 0.5F), (double)((float)var10 + 0.5F), (double)((float)var11 + 0.5F));
                WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, var13, par8);
                par1World.spawnEntityInWorld(var13);
                return true;
            }
            else
            {
                return false;
            }
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox))
            {
                return false;
            }
            else
            {
                boolean var4 = false;
                boolean var5 = true;
                boolean var6 = false;
                boolean var7 = true;
                int var8 = this.sectionCount * 5 - 1;
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 1, var8, Blocks.air, Blocks.air, false);
                this.func_151551_a(par1World, par3StructureBoundingBox, par2Random, 0.8F, 0, 2, 0, 2, 2, var8, Blocks.air, Blocks.air, false);

                if (this.hasSpiders)
                {
                    this.func_151551_a(par1World, par3StructureBoundingBox, par2Random, 0.6F, 0, 0, 0, 2, 1, var8, Blocks.web, Blocks.air, false);
                }

                int var9;
                int var10;

                for (var9 = 0; var9 < this.sectionCount; ++var9)
                {
                    var10 = 2 + var9 * 5;
                    this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, var10, 0, 1, var10, Blocks.fence, Blocks.air, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, 2, 0, var10, 2, 1, var10, Blocks.fence, Blocks.air, false);

                    if (par2Random.nextInt(4) == 0)
                    {
                        this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, var10, 0, 2, var10, Blocks.planks, Blocks.air, false);
                        this.func_151549_a(par1World, par3StructureBoundingBox, 2, 2, var10, 2, 2, var10, Blocks.planks, Blocks.air, false);
                    }
                    else
                    {
                        this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, var10, 2, 2, var10, Blocks.planks, Blocks.air, false);
                    }

                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 0, 2, var10 - 1, Blocks.web, 0);
                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 2, 2, var10 - 1, Blocks.web, 0);
                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 0, 2, var10 + 1, Blocks.web, 0);
                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 2, 2, var10 + 1, Blocks.web, 0);
                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 0, 2, var10 - 2, Blocks.web, 0);
                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 2, 2, var10 - 2, Blocks.web, 0);
                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 0, 2, var10 + 2, Blocks.web, 0);
                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 2, 2, var10 + 2, Blocks.web, 0);
                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, var10 - 1, Blocks.torch, 0);
                    this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, var10 + 1, Blocks.torch, 0);

                    if (par2Random.nextInt(100) == 0)
                    {
                        this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 2, 0, var10 - 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.mineshaftChestContents, new WeightedRandomChestContent[] {Items.enchanted_book.func_92114_b(par2Random)}), 3 + par2Random.nextInt(4));
                    }

                    if (par2Random.nextInt(100) == 0)
                    {
                        this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 0, 0, var10 + 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.mineshaftChestContents, new WeightedRandomChestContent[] {Items.enchanted_book.func_92114_b(par2Random)}), 3 + par2Random.nextInt(4));
                    }

                    if (this.hasSpiders && !this.spawnerPlaced)
                    {
                        int var11 = this.getYWithOffset(0);
                        int var12 = var10 - 1 + par2Random.nextInt(3);
                        int var13 = this.getXWithOffset(1, var12);
                        var12 = this.getZWithOffset(1, var12);

                        if (par3StructureBoundingBox.isVecInside(var13, var11, var12))
                        {
                            this.spawnerPlaced = true;
                            par1World.setBlock(var13, var11, var12, Blocks.mob_spawner, 0, 2);
                            TileEntityMobSpawner var14 = (TileEntityMobSpawner)par1World.getTileEntity(var13, var11, var12);

                            if (var14 != null)
                            {
                                var14.func_145881_a().setMobID("CaveSpider");
                            }
                        }
                    }
                }

                for (var9 = 0; var9 <= 2; ++var9)
                {
                    for (var10 = 0; var10 <= var8; ++var10)
                    {
                        byte var16 = -1;
                        Block var17 = this.func_151548_a(par1World, var9, var16, var10, par3StructureBoundingBox);

                        if (var17.getMaterial() == Material.air)
                        {
                            byte var18 = -1;
                            this.func_151550_a(par1World, Blocks.planks, 0, var9, var18, var10, par3StructureBoundingBox);
                        }
                    }
                }

                if (this.hasRails)
                {
                    for (var9 = 0; var9 <= var8; ++var9)
                    {
                        Block var15 = this.func_151548_a(par1World, 1, -1, var9, par3StructureBoundingBox);

                        if (var15.getMaterial() != Material.air && var15.func_149730_j())
                        {
                            this.func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.7F, 1, 0, var9, Blocks.rail, this.func_151555_a(Blocks.rail, 0));
                        }
                    }
                }

                return true;
            }
        }
    }

    public static class Stairs extends StructureComponent
    {
        private static final String __OBFID = "CL_00000449";

        public Stairs() {}

        public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {}

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {}

        public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
        {
            StructureBoundingBox var6 = new StructureBoundingBox(par2, par3 - 5, par4, par2, par3 + 2, par4);

            switch (par5)
            {
                case 0:
                    var6.maxX = par2 + 2;
                    var6.maxZ = par4 + 8;
                    break;

                case 1:
                    var6.minX = par2 - 8;
                    var6.maxZ = par4 + 2;
                    break;

                case 2:
                    var6.maxX = par2 + 2;
                    var6.minZ = par4 - 8;
                    break;

                case 3:
                    var6.maxX = par2 + 8;
                    var6.maxZ = par4 + 2;
            }

            return StructureComponent.findIntersecting(par0List, var6) != null ? null : var6;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            int var4 = this.getComponentType();

            switch (this.coordBaseMode)
            {
                case 0:
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
                    break;

                case 1:
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, var4);
                    break;

                case 2:
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
                    break;

                case 3:
                    StructureMineshaftPieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, var4);
            }
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox))
            {
                return false;
            }
            else
            {
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 2, 7, 1, Blocks.air, Blocks.air, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 7, 2, 2, 8, Blocks.air, Blocks.air, false);

                for (int var4 = 0; var4 < 5; ++var4)
                {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5 - var4 - (var4 < 4 ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, Blocks.air, Blocks.air, false);
                }

                return true;
            }
        }
    }
}
