package com.krispdev.resilience.utilities;

import java.util.ArrayList;

public class XrayUtils{

	public ArrayList<XrayBlock> xrayBlocks = new ArrayList<XrayBlock>();
	
	public boolean xrayEnabled = false;
	
	public boolean shouldRenderBlock(XrayBlock block){
		for(XrayBlock xBlock : xrayBlocks){
			if(xBlock.getId() == block.getId()){
				return true;
			}
		}
		return false;
	}
	
	public boolean shouldRenderBlock(int block){
		for(XrayBlock xBlock : xrayBlocks){
			if(xBlock.getId() == block){
				return true;
			}
		}
		return false;
	}
	
	public boolean removeBlock(int block){
		for(XrayBlock xBlock : xrayBlocks){
			if(xBlock.getId() == block){
				xrayBlocks.remove(xrayBlocks.indexOf(xBlock));
				return true;
			}
		}
		return false;
	}

	public boolean addBlock(int block) {
		for(XrayBlock xBlock : xrayBlocks){
			if(xBlock.getId() == block){
				return false;
			}
		}
		xrayBlocks.add(new XrayBlock(block));
		return true;
	}
	
}
