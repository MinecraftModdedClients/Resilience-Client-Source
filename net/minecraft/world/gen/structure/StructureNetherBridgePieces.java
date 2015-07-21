package net.minecraft.world.gen.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureNetherBridgePieces
{
    private static final StructureNetherBridgePieces.PieceWeight[] primaryComponents = new StructureNetherBridgePieces.PieceWeight[] {new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Straight.class, 30, 0, true), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing3.class, 10, 4), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing.class, 10, 4), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Stairs.class, 10, 3), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Throne.class, 5, 2), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Entrance.class, 5, 1)};
    private static final StructureNetherBridgePieces.PieceWeight[] secondaryComponents = new StructureNetherBridgePieces.PieceWeight[] {new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor5.class, 25, 0, true), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing2.class, 15, 5), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor2.class, 5, 10), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor.class, 5, 10), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor3.class, 10, 3, true), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor4.class, 7, 2), new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.NetherStalkRoom.class, 5, 2)};
    private static final String __OBFID = "CL_00000453";

    public static void func_143049_a()
    {
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Crossing3.class, "NeBCr");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.End.class, "NeBEF");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Straight.class, "NeBS");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor3.class, "NeCCS");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor4.class, "NeCTB");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Entrance.class, "NeCE");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Crossing2.class, "NeSCSC");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor.class, "NeSCLT");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor5.class, "NeSC");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Corridor2.class, "NeSCRT");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.NetherStalkRoom.class, "NeCSR");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Throne.class, "NeMT");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Crossing.class, "NeRC");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Stairs.class, "NeSR");
        MapGenStructureIO.func_143031_a(StructureNetherBridgePieces.Start.class, "NeStart");
    }

    private static StructureNetherBridgePieces.Piece createNextComponentRandom(StructureNetherBridgePieces.PieceWeight par0StructureNetherBridgePieceWeight, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        Class var8 = par0StructureNetherBridgePieceWeight.weightClass;
        Object var9 = null;

        if (var8 == StructureNetherBridgePieces.Straight.class)
        {
            var9 = StructureNetherBridgePieces.Straight.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Crossing3.class)
        {
            var9 = StructureNetherBridgePieces.Crossing3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Crossing.class)
        {
            var9 = StructureNetherBridgePieces.Crossing.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Stairs.class)
        {
            var9 = StructureNetherBridgePieces.Stairs.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Throne.class)
        {
            var9 = StructureNetherBridgePieces.Throne.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Entrance.class)
        {
            var9 = StructureNetherBridgePieces.Entrance.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Corridor5.class)
        {
            var9 = StructureNetherBridgePieces.Corridor5.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Corridor2.class)
        {
            var9 = StructureNetherBridgePieces.Corridor2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Corridor.class)
        {
            var9 = StructureNetherBridgePieces.Corridor.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Corridor3.class)
        {
            var9 = StructureNetherBridgePieces.Corridor3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Corridor4.class)
        {
            var9 = StructureNetherBridgePieces.Corridor4.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.Crossing2.class)
        {
            var9 = StructureNetherBridgePieces.Crossing2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (var8 == StructureNetherBridgePieces.NetherStalkRoom.class)
        {
            var9 = StructureNetherBridgePieces.NetherStalkRoom.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }

        return (StructureNetherBridgePieces.Piece)var9;
    }

    public static class Crossing3 extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000454";

        public Crossing3() {}

        public Crossing3(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        protected Crossing3(Random par1Random, int par2, int par3)
        {
            super(0);
            this.coordBaseMode = par1Random.nextInt(4);

            switch (this.coordBaseMode)
            {
                case 0:
                case 2:
                    this.boundingBox = new StructureBoundingBox(par2, 64, par3, par2 + 19 - 1, 73, par3 + 19 - 1);
                    break;

                default:
                    this.boundingBox = new StructureBoundingBox(par2, 64, par3, par2 + 19 - 1, 73, par3 + 19 - 1);
            }
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 8, 3, false);
            this.getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 3, 8, false);
            this.getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 3, 8, false);
        }

        public static StructureNetherBridgePieces.Crossing3 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -8, -3, 0, 19, 10, 19, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Crossing3(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 3, 0, 11, 4, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 7, 18, 4, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 7, 18, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 8, 18, 7, 10, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 5, 0, 7, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 5, 11, 7, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 0, 11, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 11, 11, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 7, 7, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 7, 18, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 11, 7, 5, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 11, 18, 5, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 2, 0, 11, 2, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 2, 13, 11, 2, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 0, 0, 11, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 0, 15, 11, 1, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            int var4;
            int var5;

            for (var4 = 7; var4 <= 11; ++var4)
            {
                for (var5 = 0; var5 <= 2; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, 18 - var5, par3StructureBoundingBox);
                }
            }

            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 7, 5, 2, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 13, 2, 7, 18, 2, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 7, 3, 1, 11, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 15, 0, 7, 18, 1, 11, Blocks.nether_brick, Blocks.nether_brick, false);

            for (var4 = 0; var4 <= 2; ++var4)
            {
                for (var5 = 7; var5 <= 11; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, 18 - var4, -1, var5, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    public static class Straight extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000456";

        public Straight() {}

        public Straight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 1, 3, false);
        }

        public static StructureNetherBridgePieces.Straight createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 19, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Straight(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 0, 4, 4, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 0, 3, 7, 18, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 2, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 13, 4, 2, 18, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 15, 4, 1, 18, Blocks.nether_brick, Blocks.nether_brick, false);

            for (int var4 = 0; var4 <= 4; ++var4)
            {
                for (int var5 = 0; var5 <= 2; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, 18 - var5, par3StructureBoundingBox);
                }
            }

            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 14, 0, 4, 14, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 1, 17, 0, 4, 17, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 4, 4, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 14, 4, 4, 14, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 1, 17, 4, 4, 17, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            return true;
        }
    }

    public static class Crossing2 extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000460";

        public Crossing2() {}

        public Crossing2(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 1, 0, true);
            this.getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
            this.getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
        }

        public static StructureNetherBridgePieces.Crossing2 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Crossing2(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 0, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 4, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);

            for (int var4 = 0; var4 <= 4; ++var4)
            {
                for (int var5 = 0; var5 <= 4; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    public static class Entrance extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000459";

        public Entrance() {}

        public Entrance(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 5, 3, true);
        }

        public static StructureNetherBridgePieces.Entrance createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -5, -3, 0, 13, 14, 13, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Entrance(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            int var4;

            for (var4 = 1; var4 <= 11; var4 += 2)
            {
                this.func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 0, var4, 11, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 12, var4, 11, 12, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 10, var4, 0, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 12, 10, var4, 12, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 12, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, 0, 13, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, 12, 13, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 12, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, var4 + 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, var4 + 1, par3StructureBoundingBox);
            }

            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 12, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, 0, par3StructureBoundingBox);

            for (var4 = 3; var4 <= 9; var4 += 2)
            {
                this.func_151549_a(par1World, par3StructureBoundingBox, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 11, 7, var4, 11, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            }

            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            int var5;

            for (var4 = 4; var4 <= 8; ++var4)
            {
                for (var5 = 0; var5 <= 2; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, 12 - var5, par3StructureBoundingBox);
                }
            }

            for (var4 = 0; var4 <= 2; ++var4)
            {
                for (var5 = 4; var5 <= 8; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, 12 - var4, -1, var5, par3StructureBoundingBox);
                }
            }

            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 5, 5, 7, 5, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 1, 6, 6, 4, 6, Blocks.air, Blocks.air, false);
            this.func_151550_a(par1World, Blocks.nether_brick, 0, 6, 0, 6, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.flowing_lava, 0, 6, 5, 6, par3StructureBoundingBox);
            var4 = this.getXWithOffset(6, 6);
            var5 = this.getYWithOffset(5);
            int var6 = this.getZWithOffset(6, 6);

            if (par3StructureBoundingBox.isVecInside(var4, var5, var6))
            {
                par1World.scheduledUpdatesAreImmediate = true;
                Blocks.flowing_lava.updateTick(par1World, var4, var5, var6, par2Random);
                par1World.scheduledUpdatesAreImmediate = false;
            }

            return true;
        }
    }

    static class PieceWeight
    {
        public Class weightClass;
        public final int field_78826_b;
        public int field_78827_c;
        public int field_78824_d;
        public boolean field_78825_e;
        private static final String __OBFID = "CL_00000467";

        public PieceWeight(Class par1Class, int par2, int par3, boolean par4)
        {
            this.weightClass = par1Class;
            this.field_78826_b = par2;
            this.field_78824_d = par3;
            this.field_78825_e = par4;
        }

        public PieceWeight(Class par1Class, int par2, int par3)
        {
            this(par1Class, par2, par3, false);
        }

        public boolean func_78822_a(int par1)
        {
            return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
        }

        public boolean func_78823_a()
        {
            return this.field_78824_d == 0 || this.field_78827_c < this.field_78824_d;
        }
    }

    public static class Throne extends StructureNetherBridgePieces.Piece
    {
        private boolean hasSpawner;
        private static final String __OBFID = "CL_00000465";

        public Throne() {}

        public Throne(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143011_b(par1NBTTagCompound);
            this.hasSpawner = par1NBTTagCompound.getBoolean("Mob");
        }

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Mob", this.hasSpawner);
        }

        public static StructureNetherBridgePieces.Throne createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 8, 9, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Throne(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 7, 7, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 0, 0, 5, 1, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 1, 5, 2, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 2, 5, 3, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 3, 5, 4, 7, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 0, 1, 4, 2, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 0, 5, 4, 2, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 2, 1, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 5, 2, 5, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 3, 0, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 5, 3, 6, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 5, 8, 5, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 1, 6, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 5, 6, 3, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            int var4;
            int var5;

            if (!this.hasSpawner)
            {
                var4 = this.getYWithOffset(5);
                var5 = this.getXWithOffset(3, 5);
                int var6 = this.getZWithOffset(3, 5);

                if (par3StructureBoundingBox.isVecInside(var5, var4, var6))
                {
                    this.hasSpawner = true;
                    par1World.setBlock(var5, var4, var6, Blocks.mob_spawner, 0, 2);
                    TileEntityMobSpawner var7 = (TileEntityMobSpawner)par1World.getTileEntity(var5, var4, var6);

                    if (var7 != null)
                    {
                        var7.func_145881_a().setMobID("Blaze");
                    }
                }
            }

            for (var4 = 0; var4 <= 6; ++var4)
            {
                for (var5 = 0; var5 <= 6; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    public static class Corridor5 extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000462";

        public Corridor5() {}

        public Corridor5(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 1, 0, true);
        }

        public static StructureNetherBridgePieces.Corridor5 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Corridor5(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);

            for (int var4 = 0; var4 <= 4; ++var4)
            {
                for (int var5 = 0; var5 <= 4; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    abstract static class Piece extends StructureComponent
    {
        protected static final WeightedRandomChestContent[] field_111019_a = new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5), new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3)};
        private static final String __OBFID = "CL_00000466";

        public Piece() {}

        protected Piece(int par1)
        {
            super(par1);
        }

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {}

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {}

        private int getTotalWeight(List par1List)
        {
            boolean var2 = false;
            int var3 = 0;
            StructureNetherBridgePieces.PieceWeight var5;

            for (Iterator var4 = par1List.iterator(); var4.hasNext(); var3 += var5.field_78826_b)
            {
                var5 = (StructureNetherBridgePieces.PieceWeight)var4.next();

                if (var5.field_78824_d > 0 && var5.field_78827_c < var5.field_78824_d)
                {
                    var2 = true;
                }
            }

            return var2 ? var3 : -1;
        }

        private StructureNetherBridgePieces.Piece getNextComponent(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, List par3List, Random par4Random, int par5, int par6, int par7, int par8, int par9)
        {
            int var10 = this.getTotalWeight(par2List);
            boolean var11 = var10 > 0 && par9 <= 30;
            int var12 = 0;

            while (var12 < 5 && var11)
            {
                ++var12;
                int var13 = par4Random.nextInt(var10);
                Iterator var14 = par2List.iterator();

                while (var14.hasNext())
                {
                    StructureNetherBridgePieces.PieceWeight var15 = (StructureNetherBridgePieces.PieceWeight)var14.next();
                    var13 -= var15.field_78826_b;

                    if (var13 < 0)
                    {
                        if (!var15.func_78822_a(par9) || var15 == par1ComponentNetherBridgeStartPiece.theNetherBridgePieceWeight && !var15.field_78825_e)
                        {
                            break;
                        }

                        StructureNetherBridgePieces.Piece var16 = StructureNetherBridgePieces.createNextComponentRandom(var15, par3List, par4Random, par5, par6, par7, par8, par9);

                        if (var16 != null)
                        {
                            ++var15.field_78827_c;
                            par1ComponentNetherBridgeStartPiece.theNetherBridgePieceWeight = var15;

                            if (!var15.func_78823_a())
                            {
                                par2List.remove(var15);
                            }

                            return var16;
                        }
                    }
                }
            }

            return StructureNetherBridgePieces.End.func_74971_a(par3List, par4Random, par5, par6, par7, par8, par9);
        }

        private StructureComponent getNextComponent(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, int par6, int par7, int par8, boolean par9)
        {
            if (Math.abs(par4 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minX) <= 112 && Math.abs(par6 - par1ComponentNetherBridgeStartPiece.getBoundingBox().minZ) <= 112)
            {
                List var10 = par1ComponentNetherBridgeStartPiece.primaryWeights;

                if (par9)
                {
                    var10 = par1ComponentNetherBridgeStartPiece.secondaryWeights;
                }

                StructureNetherBridgePieces.Piece var11 = this.getNextComponent(par1ComponentNetherBridgeStartPiece, var10, par2List, par3Random, par4, par5, par6, par7, par8 + 1);

                if (var11 != null)
                {
                    par2List.add(var11);
                    par1ComponentNetherBridgeStartPiece.field_74967_d.add(var11);
                }

                return var11;
            }
            else
            {
                return StructureNetherBridgePieces.End.func_74971_a(par2List, par3Random, par4, par5, par6, par7, par8);
            }
        }

        protected StructureComponent getNextComponentNormal(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
        {
            switch (this.coordBaseMode)
            {
                case 0:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType(), par6);

                case 1:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType(), par6);

                case 2:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType(), par6);

                case 3:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType(), par6);

                default:
                    return null;
            }
        }

        protected StructureComponent getNextComponentX(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
        {
            switch (this.coordBaseMode)
            {
                case 0:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType(), par6);

                case 1:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType(), par6);

                case 2:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType(), par6);

                case 3:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType(), par6);

                default:
                    return null;
            }
        }

        protected StructureComponent getNextComponentZ(StructureNetherBridgePieces.Start par1ComponentNetherBridgeStartPiece, List par2List, Random par3Random, int par4, int par5, boolean par6)
        {
            switch (this.coordBaseMode)
            {
                case 0:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType(), par6);

                case 1:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType(), par6);

                case 2:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType(), par6);

                case 3:
                    return this.getNextComponent(par1ComponentNetherBridgeStartPiece, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType(), par6);

                default:
                    return null;
            }
        }

        protected static boolean isAboveGround(StructureBoundingBox par0StructureBoundingBox)
        {
            return par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10;
        }
    }

    public static class Start extends StructureNetherBridgePieces.Crossing3
    {
        public StructureNetherBridgePieces.PieceWeight theNetherBridgePieceWeight;
        public List primaryWeights;
        public List secondaryWeights;
        public ArrayList field_74967_d = new ArrayList();
        private static final String __OBFID = "CL_00000470";

        public Start() {}

        public Start(Random par1Random, int par2, int par3)
        {
            super(par1Random, par2, par3);
            this.primaryWeights = new ArrayList();
            StructureNetherBridgePieces.PieceWeight[] var4 = StructureNetherBridgePieces.primaryComponents;
            int var5 = var4.length;
            int var6;
            StructureNetherBridgePieces.PieceWeight var7;

            for (var6 = 0; var6 < var5; ++var6)
            {
                var7 = var4[var6];
                var7.field_78827_c = 0;
                this.primaryWeights.add(var7);
            }

            this.secondaryWeights = new ArrayList();
            var4 = StructureNetherBridgePieces.secondaryComponents;
            var5 = var4.length;

            for (var6 = 0; var6 < var5; ++var6)
            {
                var7 = var4[var6];
                var7.field_78827_c = 0;
                this.secondaryWeights.add(var7);
            }
        }

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143011_b(par1NBTTagCompound);
        }

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143012_a(par1NBTTagCompound);
        }
    }

    public static class Stairs extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000469";

        public Stairs() {}

        public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 6, 2, false);
        }

        public static StructureNetherBridgePieces.Stairs createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 11, 7, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Stairs(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 10, 6, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 1, 8, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 0, 6, 8, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 1, 0, 8, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 1, 6, 8, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 6, 5, 8, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 2, 0, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 3, 2, 6, 5, 2, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 3, 4, 6, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151550_a(par1World, Blocks.nether_brick, 0, 5, 2, 5, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 5, 4, 3, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 2, 5, 3, 4, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 2, 5, 2, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 5, 1, 6, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 7, 1, 5, 7, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 8, 2, 6, 8, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 6, 0, 4, 8, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);

            for (int var4 = 0; var4 <= 6; ++var4)
            {
                for (int var5 = 0; var5 <= 6; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    public static class Corridor2 extends StructureNetherBridgePieces.Piece
    {
        private boolean field_111020_b;
        private static final String __OBFID = "CL_00000463";

        public Corridor2() {}

        public Corridor2(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
            this.field_111020_b = par2Random.nextInt(3) == 0;
        }

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143011_b(par1NBTTagCompound);
            this.field_111020_b = par1NBTTagCompound.getBoolean("Chest");
        }

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Chest", this.field_111020_b);
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
        }

        public static StructureNetherBridgePieces.Corridor2 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Corridor2(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 2, 4, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
            int var4;
            int var5;

            if (this.field_111020_b)
            {
                var4 = this.getYWithOffset(2);
                var5 = this.getXWithOffset(1, 3);
                int var6 = this.getZWithOffset(1, 3);

                if (par3StructureBoundingBox.isVecInside(var5, var4, var6))
                {
                    this.field_111020_b = false;
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 1, 2, 3, field_111019_a, 2 + par2Random.nextInt(4));
                }
            }

            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);

            for (var4 = 0; var4 <= 4; ++var4)
            {
                for (var5 = 0; var5 <= 4; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    public static class Corridor3 extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000457";

        public Corridor3() {}

        public Corridor3(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 1, 0, true);
        }

        public static StructureNetherBridgePieces.Corridor3 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 14, 10, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Corridor3(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            int var4 = this.func_151555_a(Blocks.nether_brick_stairs, 2);

            for (int var5 = 0; var5 <= 9; ++var5)
            {
                int var6 = Math.max(1, 7 - var5);
                int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
                int var8 = var5;
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, var5, 4, var6, var5, Blocks.nether_brick, Blocks.nether_brick, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 1, var6 + 1, var5, 3, var7 - 1, var5, Blocks.air, Blocks.air, false);

                if (var5 <= 6)
                {
                    this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, 1, var6 + 1, var5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, 2, var6 + 1, var5, par3StructureBoundingBox);
                    this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, 3, var6 + 1, var5, par3StructureBoundingBox);
                }

                this.func_151549_a(par1World, par3StructureBoundingBox, 0, var7, var5, 4, var7, var5, Blocks.nether_brick, Blocks.nether_brick, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, var6 + 1, var5, 0, var7 - 1, var5, Blocks.nether_brick, Blocks.nether_brick, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 4, var6 + 1, var5, 4, var7 - 1, var5, Blocks.nether_brick, Blocks.nether_brick, false);

                if ((var5 & 1) == 0)
                {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 0, var6 + 2, var5, 0, var6 + 3, var5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                    this.func_151549_a(par1World, par3StructureBoundingBox, 4, var6 + 2, var5, 4, var6 + 3, var5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                }

                for (int var9 = 0; var9 <= 4; ++var9)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var9, -1, var8, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    public static class Corridor4 extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000458";

        public Corridor4() {}

        public Corridor4(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            byte var4 = 1;

            if (this.coordBaseMode == 1 || this.coordBaseMode == 2)
            {
                var4 = 5;
            }

            this.getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, var4, par3Random.nextInt(8) > 0);
            this.getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, var4, par3Random.nextInt(8) > 0);
        }

        public static StructureNetherBridgePieces.Corridor4 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -3, 0, 0, 9, 7, 9, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Corridor4(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 8, 5, 8, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 8, 6, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 2, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 0, 8, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 0, 1, 4, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 3, 0, 7, 4, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 8, 2, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 1, 4, 2, 2, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 1, 4, 7, 2, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 8, 8, 3, 8, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 6, 0, 3, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 3, 6, 8, 3, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 3, 4, 8, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 5, 2, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 3, 5, 7, 5, 5, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 4, 5, 1, 5, 5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 4, 5, 7, 5, 5, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);

            for (int var4 = 0; var4 <= 5; ++var4)
            {
                for (int var5 = 0; var5 <= 8; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var5, -1, var4, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    public static class End extends StructureNetherBridgePieces.Piece
    {
        private int fillSeed;
        private static final String __OBFID = "CL_00000455";

        public End() {}

        public End(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
            this.fillSeed = par2Random.nextInt();
        }

        public static StructureNetherBridgePieces.End func_74971_a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 8, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.End(par6, par1Random, var7, par5) : null;
        }

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143011_b(par1NBTTagCompound);
            this.fillSeed = par1NBTTagCompound.getInteger("Seed");
        }

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setInteger("Seed", this.fillSeed);
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            Random var4 = new Random((long)this.fillSeed);
            int var5;
            int var6;
            int var7;

            for (var5 = 0; var5 <= 4; ++var5)
            {
                for (var6 = 3; var6 <= 4; ++var6)
                {
                    var7 = var4.nextInt(8);
                    this.func_151549_a(par1World, par3StructureBoundingBox, var5, var6, 0, var5, var6, var7, Blocks.nether_brick, Blocks.nether_brick, false);
                }
            }

            var5 = var4.nextInt(8);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, var5, Blocks.nether_brick, Blocks.nether_brick, false);
            var5 = var4.nextInt(8);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, var5, Blocks.nether_brick, Blocks.nether_brick, false);

            for (var5 = 0; var5 <= 4; ++var5)
            {
                var6 = var4.nextInt(5);
                this.func_151549_a(par1World, par3StructureBoundingBox, var5, 2, 0, var5, 2, var6, Blocks.nether_brick, Blocks.nether_brick, false);
            }

            for (var5 = 0; var5 <= 4; ++var5)
            {
                for (var6 = 0; var6 <= 1; ++var6)
                {
                    var7 = var4.nextInt(3);
                    this.func_151549_a(par1World, par3StructureBoundingBox, var5, var6, 0, var5, var6, var7, Blocks.nether_brick, Blocks.nether_brick, false);
                }
            }

            return true;
        }
    }

    public static class NetherStalkRoom extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000464";

        public NetherStalkRoom() {}

        public NetherStalkRoom(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 5, 3, true);
            this.getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 5, 11, true);
        }

        public static StructureNetherBridgePieces.NetherStalkRoom createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -5, -3, 0, 13, 14, 13, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.NetherStalkRoom(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.nether_brick, Blocks.nether_brick, false);
            int var4;

            for (var4 = 1; var4 <= 11; var4 += 2)
            {
                this.func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 0, var4, 11, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, var4, 10, 12, var4, 11, 12, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 0, 10, var4, 0, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 12, 10, var4, 12, 11, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, var4, 13, 12, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, 0, 13, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick, 0, 12, 13, var4, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 0, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, var4 + 1, 13, 12, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, var4 + 1, par3StructureBoundingBox);
                this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, var4 + 1, par3StructureBoundingBox);
            }

            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 12, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 0, 13, 0, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_fence, 0, 12, 13, 0, par3StructureBoundingBox);

            for (var4 = 3; var4 <= 9; var4 += 2)
            {
                this.func_151549_a(par1World, par3StructureBoundingBox, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
                this.func_151549_a(par1World, par3StructureBoundingBox, 11, 7, var4, 11, 8, var4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            }

            var4 = this.func_151555_a(Blocks.nether_brick_stairs, 3);
            int var5;
            int var6;
            int var7;

            for (var5 = 0; var5 <= 6; ++var5)
            {
                var6 = var5 + 4;

                for (var7 = 5; var7 <= 7; ++var7)
                {
                    this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, var7, 5 + var5, var6, par3StructureBoundingBox);
                }

                if (var6 >= 5 && var6 <= 8)
                {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 5, 5, var6, 7, var5 + 4, var6, Blocks.nether_brick, Blocks.nether_brick, false);
                }
                else if (var6 >= 9 && var6 <= 10)
                {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 5, 8, var6, 7, var5 + 4, var6, Blocks.nether_brick, Blocks.nether_brick, false);
                }

                if (var5 >= 1)
                {
                    this.func_151549_a(par1World, par3StructureBoundingBox, 5, 6 + var5, var6, 7, 9 + var5, var6, Blocks.air, Blocks.air, false);
                }
            }

            for (var5 = 5; var5 <= 7; ++var5)
            {
                this.func_151550_a(par1World, Blocks.nether_brick_stairs, var4, var5, 12, 11, par3StructureBoundingBox);
            }

            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 13, 12, 7, 13, 12, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 2, 3, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 9, 3, 5, 10, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 4, 2, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 5, 2, 10, 5, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 5, 9, 10, 5, 10, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 10, 5, 4, 10, 5, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            var5 = this.func_151555_a(Blocks.nether_brick_stairs, 0);
            var6 = this.func_151555_a(Blocks.nether_brick_stairs, 1);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 9, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var6, 4, 5, 10, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 2, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 3, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 9, par3StructureBoundingBox);
            this.func_151550_a(par1World, Blocks.nether_brick_stairs, var5, 8, 5, 10, par3StructureBoundingBox);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 4, 4, 4, 4, 8, Blocks.soul_sand, Blocks.soul_sand, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 4, 4, 9, 4, 8, Blocks.soul_sand, Blocks.soul_sand, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 5, 4, 4, 5, 8, Blocks.nether_wart, Blocks.nether_wart, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 8, 5, 4, 9, 5, 8, Blocks.nether_wart, Blocks.nether_wart, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.nether_brick, Blocks.nether_brick, false);
            int var8;

            for (var7 = 4; var7 <= 8; ++var7)
            {
                for (var8 = 0; var8 <= 2; ++var8)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var7, -1, var8, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var7, -1, 12 - var8, par3StructureBoundingBox);
                }
            }

            for (var7 = 0; var7 <= 2; ++var7)
            {
                for (var8 = 4; var8 <= 8; ++var8)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var7, -1, var8, par3StructureBoundingBox);
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, 12 - var7, -1, var8, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    public static class Crossing extends StructureNetherBridgePieces.Piece
    {
        private static final String __OBFID = "CL_00000468";

        public Crossing() {}

        public Crossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentNormal((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 2, 0, false);
            this.getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 2, false);
            this.getNextComponentZ((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 2, false);
        }

        public static StructureNetherBridgePieces.Crossing createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -2, 0, 0, 7, 9, 7, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Crossing(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 6, 7, 6, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 1, 6, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 6, 1, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 0, 6, 6, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 5, 2, 6, 6, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 6, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 5, 0, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 0, 6, 6, 1, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 2, 5, 6, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 6, 0, 4, 6, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 6, 6, 4, 6, 6, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 2, 5, 6, 4, 5, 6, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 2, 0, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 5, 2, 0, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 6, 2, 6, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 6, 5, 2, 6, 5, 4, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);

            for (int var4 = 0; var4 <= 6; ++var4)
            {
                for (int var5 = 0; var5 <= 6; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }

    public static class Corridor extends StructureNetherBridgePieces.Piece
    {
        private boolean field_111021_b;
        private static final String __OBFID = "CL_00000461";

        public Corridor() {}

        public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
        {
            super(par1);
            this.coordBaseMode = par4;
            this.boundingBox = par3StructureBoundingBox;
            this.field_111021_b = par2Random.nextInt(3) == 0;
        }

        protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143011_b(par1NBTTagCompound);
            this.field_111021_b = par1NBTTagCompound.getBoolean("Chest");
        }

        protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
        {
            super.func_143012_a(par1NBTTagCompound);
            par1NBTTagCompound.setBoolean("Chest", this.field_111021_b);
        }

        public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
        {
            this.getNextComponentX((StructureNetherBridgePieces.Start)par1StructureComponent, par2List, par3Random, 0, 1, true);
        }

        public static StructureNetherBridgePieces.Corridor createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
        {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
            return isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new StructureNetherBridgePieces.Corridor(par6, par1Random, var7, par5) : null;
        }

        public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
        {
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence, Blocks.nether_brick_fence, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 2, 4, 3, 5, 4, Blocks.nether_brick, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
            this.func_151549_a(par1World, par3StructureBoundingBox, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence, Blocks.nether_brick, false);
            int var4;
            int var5;

            if (this.field_111021_b)
            {
                var4 = this.getYWithOffset(2);
                var5 = this.getXWithOffset(3, 3);
                int var6 = this.getZWithOffset(3, 3);

                if (par3StructureBoundingBox.isVecInside(var5, var4, var6))
                {
                    this.field_111021_b = false;
                    this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, field_111019_a, 2 + par2Random.nextInt(4));
                }
            }

            this.func_151549_a(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.nether_brick, Blocks.nether_brick, false);

            for (var4 = 0; var4 <= 4; ++var4)
            {
                for (var5 = 0; var5 <= 4; ++var5)
                {
                    this.func_151554_b(par1World, Blocks.nether_brick, 0, var4, -1, var5, par3StructureBoundingBox);
                }
            }

            return true;
        }
    }
}
