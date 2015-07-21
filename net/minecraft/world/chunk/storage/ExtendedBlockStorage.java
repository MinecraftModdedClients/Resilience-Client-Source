package net.minecraft.world.chunk.storage;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.NibbleArray;

public class ExtendedBlockStorage
{
    /**
     * Contains the bottom-most Y block represented by this ExtendedBlockStorage. Typically a multiple of 16.
     */
    private int yBase;

    /**
     * A total count of the number of non-air blocks in this block storage's Chunk.
     */
    private int blockRefCount;

    /**
     * Contains the number of blocks in this block storage's parent chunk that require random ticking. Used to cull the
     * Chunk from random tick updates for performance reasons.
     */
    private int tickRefCount;

    /**
     * Contains the least significant 8 bits of each block ID belonging to this block storage's parent Chunk.
     */
    private byte[] blockLSBArray;

    /**
     * Contains the most significant 4 bits of each block ID belonging to this block storage's parent Chunk.
     */
    private NibbleArray blockMSBArray;

    /**
     * Stores the metadata associated with blocks in this ExtendedBlockStorage.
     */
    private NibbleArray blockMetadataArray;

    /** The NibbleArray containing a block of Block-light data. */
    private NibbleArray blocklightArray;

    /** The NibbleArray containing a block of Sky-light data. */
    private NibbleArray skylightArray;
    private static final String __OBFID = "CL_00000375";

    public ExtendedBlockStorage(int par1, boolean par2)
    {
        this.yBase = par1;
        this.blockLSBArray = new byte[4096];
        this.blockMetadataArray = new NibbleArray(this.blockLSBArray.length, 4);
        this.blocklightArray = new NibbleArray(this.blockLSBArray.length, 4);

        if (par2)
        {
            this.skylightArray = new NibbleArray(this.blockLSBArray.length, 4);
        }
    }

    public Block func_150819_a(int p_150819_1_, int p_150819_2_, int p_150819_3_)
    {
        int var4 = this.blockLSBArray[p_150819_2_ << 8 | p_150819_3_ << 4 | p_150819_1_] & 255;

        if (this.blockMSBArray != null)
        {
            var4 |= this.blockMSBArray.get(p_150819_1_, p_150819_2_, p_150819_3_) << 8;
        }

        return Block.getBlockById(var4);
    }

    public void func_150818_a(int p_150818_1_, int p_150818_2_, int p_150818_3_, Block p_150818_4_)
    {
        int var5 = this.blockLSBArray[p_150818_2_ << 8 | p_150818_3_ << 4 | p_150818_1_] & 255;

        if (this.blockMSBArray != null)
        {
            var5 |= this.blockMSBArray.get(p_150818_1_, p_150818_2_, p_150818_3_) << 8;
        }

        Block var6 = Block.getBlockById(var5);

        if (var6 != Blocks.air)
        {
            --this.blockRefCount;

            if (var6.getTickRandomly())
            {
                --this.tickRefCount;
            }
        }

        if (p_150818_4_ != Blocks.air)
        {
            ++this.blockRefCount;

            if (p_150818_4_.getTickRandomly())
            {
                ++this.tickRefCount;
            }
        }

        int var7 = Block.getIdFromBlock(p_150818_4_);
        this.blockLSBArray[p_150818_2_ << 8 | p_150818_3_ << 4 | p_150818_1_] = (byte)(var7 & 255);

        if (var7 > 255)
        {
            if (this.blockMSBArray == null)
            {
                this.blockMSBArray = new NibbleArray(this.blockLSBArray.length, 4);
            }

            this.blockMSBArray.set(p_150818_1_, p_150818_2_, p_150818_3_, (var7 & 3840) >> 8);
        }
        else if (this.blockMSBArray != null)
        {
            this.blockMSBArray.set(p_150818_1_, p_150818_2_, p_150818_3_, 0);
        }
    }

    /**
     * Returns the metadata associated with the block at the given coordinates in this ExtendedBlockStorage.
     */
    public int getExtBlockMetadata(int par1, int par2, int par3)
    {
        return this.blockMetadataArray.get(par1, par2, par3);
    }

    /**
     * Sets the metadata of the Block at the given coordinates in this ExtendedBlockStorage to the given metadata.
     */
    public void setExtBlockMetadata(int par1, int par2, int par3, int par4)
    {
        this.blockMetadataArray.set(par1, par2, par3, par4);
    }

    /**
     * Returns whether or not this block storage's Chunk is fully empty, based on its internal reference count.
     */
    public boolean isEmpty()
    {
        return this.blockRefCount == 0;
    }

    /**
     * Returns whether or not this block storage's Chunk will require random ticking, used to avoid looping through
     * random block ticks when there are no blocks that would randomly tick.
     */
    public boolean getNeedsRandomTick()
    {
        return this.tickRefCount > 0;
    }

    /**
     * Returns the Y location of this ExtendedBlockStorage.
     */
    public int getYLocation()
    {
        return this.yBase;
    }

    /**
     * Sets the saved Sky-light value in the extended block storage structure.
     */
    public void setExtSkylightValue(int par1, int par2, int par3, int par4)
    {
        this.skylightArray.set(par1, par2, par3, par4);
    }

    /**
     * Gets the saved Sky-light value in the extended block storage structure.
     */
    public int getExtSkylightValue(int par1, int par2, int par3)
    {
        return this.skylightArray.get(par1, par2, par3);
    }

    /**
     * Sets the saved Block-light value in the extended block storage structure.
     */
    public void setExtBlocklightValue(int par1, int par2, int par3, int par4)
    {
        this.blocklightArray.set(par1, par2, par3, par4);
    }

    /**
     * Gets the saved Block-light value in the extended block storage structure.
     */
    public int getExtBlocklightValue(int par1, int par2, int par3)
    {
        return this.blocklightArray.get(par1, par2, par3);
    }

    public void removeInvalidBlocks()
    {
        this.blockRefCount = 0;
        this.tickRefCount = 0;

        for (int var1 = 0; var1 < 16; ++var1)
        {
            for (int var2 = 0; var2 < 16; ++var2)
            {
                for (int var3 = 0; var3 < 16; ++var3)
                {
                    Block var4 = this.func_150819_a(var1, var2, var3);

                    if (var4 != Blocks.air)
                    {
                        ++this.blockRefCount;

                        if (var4.getTickRandomly())
                        {
                            ++this.tickRefCount;
                        }
                    }
                }
            }
        }
    }

    public byte[] getBlockLSBArray()
    {
        return this.blockLSBArray;
    }

    public void clearMSBArray()
    {
        this.blockMSBArray = null;
    }

    /**
     * Returns the block ID MSB (bits 11..8) array for this storage array's Chunk.
     */
    public NibbleArray getBlockMSBArray()
    {
        return this.blockMSBArray;
    }

    public NibbleArray getMetadataArray()
    {
        return this.blockMetadataArray;
    }

    /**
     * Returns the NibbleArray instance containing Block-light data.
     */
    public NibbleArray getBlocklightArray()
    {
        return this.blocklightArray;
    }

    /**
     * Returns the NibbleArray instance containing Sky-light data.
     */
    public NibbleArray getSkylightArray()
    {
        return this.skylightArray;
    }

    /**
     * Sets the array of block ID least significant bits for this ExtendedBlockStorage.
     */
    public void setBlockLSBArray(byte[] par1ArrayOfByte)
    {
        this.blockLSBArray = par1ArrayOfByte;
    }

    /**
     * Sets the array of blockID most significant bits (blockMSBArray) for this ExtendedBlockStorage.
     */
    public void setBlockMSBArray(NibbleArray par1NibbleArray)
    {
        this.blockMSBArray = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray of block metadata (blockMetadataArray) for this ExtendedBlockStorage.
     */
    public void setBlockMetadataArray(NibbleArray par1NibbleArray)
    {
        this.blockMetadataArray = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray instance used for Block-light values in this particular storage block.
     */
    public void setBlocklightArray(NibbleArray par1NibbleArray)
    {
        this.blocklightArray = par1NibbleArray;
    }

    /**
     * Sets the NibbleArray instance used for Sky-light values in this particular storage block.
     */
    public void setSkylightArray(NibbleArray par1NibbleArray)
    {
        this.skylightArray = par1NibbleArray;
    }

    /**
     * Called by a Chunk to initialize the MSB array if getBlockMSBArray returns null. Returns the newly-created
     * NibbleArray instance.
     */
    public NibbleArray createBlockMSBArray()
    {
        this.blockMSBArray = new NibbleArray(this.blockLSBArray.length, 4);
        return this.blockMSBArray;
    }
}
