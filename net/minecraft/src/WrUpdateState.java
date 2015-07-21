package net.minecraft.src;

import java.util.HashSet;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.ChunkCache;

public class WrUpdateState
{
    public ChunkCache chunkcache = null;
    public RenderBlocks renderblocks = null;
    public HashSet setOldEntityRenders = null;
    int viewEntityPosX = 0;
    int viewEntityPosY = 0;
    int viewEntityPosZ = 0;
    public int renderPass = 0;
    public int y = 0;
    public boolean flag = false;
    public boolean hasRenderedBlocks = false;
    public boolean hasGlList = false;
}
