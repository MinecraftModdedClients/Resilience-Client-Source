package com.krispdev.resilience.module.modules.player;

import net.minecraft.client.entity.EntityOtherPlayerMP;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.events.player.EventValueChange;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;
import com.mojang.authlib.GameProfile;

public class ModuleFreecam extends DefaultModule{
	
	private EntityOtherPlayerMP entity = null;
	private double freecamX, freecamY, freecamZ, freecamPitch, freecamYaw;
	
	public ModuleFreecam(){
		super("Freecam", Keyboard.KEY_V, NoCheatMode.COMPATIBLE);
		this.setCategory(ModuleCategory.PLAYER);
		this.setDescription("Allows you to walk outside of your body and fly through walls");
		this.setSave(false);
	}
	
	@Override
	public void onEnable()
	{	
		try{
			Resilience.getInstance().getValues().freecamEnabled = true;
			Resilience.getInstance().getEventManager().register(this);
			freecamX = invoker.getPosX();
			freecamY = invoker.getPosY();
			freecamZ = invoker.getPosZ();
			freecamPitch = invoker.getRotationPitch();
			freecamYaw = invoker.getRotationYaw();
			entity = new EntityOtherPlayerMP(Resilience.getInstance().getWrapper().getWorld(),  new GameProfile("",Resilience.getInstance().getValues().nameProtectEnabled ? Resilience.getInstance().getValues().nameProtectAlias.getValue() : invoker.getSessionUsername()));
			invoker.setPositionAndRotation(entity, invoker.getPosX(), invoker.getPosY() - invoker.getEntityHeight(Resilience.getInstance().getWrapper().getPlayer()) + 0.17, invoker.getPosZ(), invoker.getRotationYaw(), invoker.getRotationPitch());
			invoker.copyInventory(entity, Resilience.getInstance().getWrapper().getPlayer());
			invoker.addEntityToWorld(entity, 69);
		}catch(Exception e){}
	}
	
	@Override
	public void onValueChange(EventValueChange event){
		if(event.getValue() == Resilience.getInstance().getValues().nameProtectAlias){
			invoker.setGameProfile(new GameProfile("", Resilience.getInstance().getValues().nameProtectAlias.getValue()), entity);
		}
	}
	
	@Override
	public void onDisable()
	{
		try{
			Resilience.getInstance().getValues().freecamEnabled = false;
			Resilience.getInstance().getEventManager().unregister(this);
			invoker.removeEntityFromWorld(69);
			invoker.setPositionAndRotation(Resilience.getInstance().getWrapper().getPlayer(), freecamX, freecamY, freecamZ, (float) freecamYaw, (float) freecamPitch);
			invoker.setNoClip(false);
		}catch(Exception e){}
	}
	
	@Override
	public void onUpdate(EventOnUpdate event){
		if(entity != null)
		{	
			if(Resilience.getInstance().getValues().flightEnabled){
				invoker.setNoClip(true);
				invoker.setOnGround(false);
			}else{
				invoker.setNoClip(false);
			}
		}
	}
}
