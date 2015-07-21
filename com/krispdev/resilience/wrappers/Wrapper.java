package com.krispdev.resilience.wrappers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Session;

import com.krispdev.resilience.hooks.HookGuiIngame;

public class Wrapper {
	
	public Minecraft getMinecraft(){
		return Minecraft.getMinecraft();
	}
	
	public FontRenderer getFontRenderer(){
		return getMinecraft().fontRenderer;
	}
	
	public PlayerControllerMP getPlayerController(){
		return getMinecraft().playerController;
	}
	
	public EntityClientPlayerMP getPlayer(){
		return getMinecraft().thePlayer;
	}
	
	public WorldClient getWorld(){
		return getMinecraft().theWorld;
	}
	
	public GameSettings getGameSettings(){
		return getMinecraft().gameSettings;
	}
	
	public TextureManager getRenderEngine(){
		return getMinecraft().renderEngine;
	}
	
	public Session getSession(){
		return getMinecraft().session;
	}
	
	public EntityRenderer getEntityRenderer(){
		return getMinecraft().entityRenderer;
	}
	
	public HookGuiIngame getInGameGui(){
		return getMinecraft().ingameGUI;
	}
	
}
