package com.krispdev.resilience.module;

import java.util.ArrayList;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.module.modules.DefaultModule;

public class ModuleManager {
	
	public ArrayList<DefaultModule> moduleList = new ArrayList<DefaultModule>();
		
	public void instantiateModules(){
		add("combat.ModuleAimbot");
		add("movement.ModuleAntiAFK");
		add("combat.ModuleAutoArmour");
		add("player.ModuleAntiBlindness");
		add("combat.ModuleAutoBlock");
		add("misc.ModuleAutoChestSteal");
		add("misc.ModuleClientTips");
		add("misc.ModuleAutoEat");
		add("misc.ModuleAutoFish");
		add("movement.ModuleAutoJump");
		add("misc.ModuleAutoMine");
		add("combat.ModuleAutoQuit");
		add("player.ModuleAutoRespawn");
		add("combat.ModuleAutoSoup");
		add("combat.ModuleAutoSword");
		add("misc.ModuleAutoTool");
		add("player.ModuleAutoTPAccept");
		add("movement.ModuleAutoWalk");
		add("movement.ModuleBlink");
		add("combat.ModuleBowAimbot");
		add("render.ModuleBreadcrumbs");
		add("render.ModuleBrightness");
		add("world.ModuleCaveFinder");
		add("render.ModuleChestESP");
		add("combat.ModuleClickAimbot");
		add("world.ModuleClickNuker");
		add("gui.ModuleConsole");
		add("combat.ModuleCriticals");
		add("misc.ModuleEnabledMods");
		add("combat.ModuleFastBow");
		add("world.ModuleFastBreak");
		add("misc.ModuleFastEat");
		add("movement.ModuleFastLadder");
		add("world.ModuleFastPlace");
		add("movement.ModuleFlight");
		add("combat.ModuleForceField");
		add("player.ModuleFreecam");
		add("movement.ModuleGlide");
		add("gui.ModuleGui");
		add("movement.ModuleHighJump");
		add("movement.ModuleInfiniteJump");
		add("misc.ModuleIRC");
		add("movement.ModuleJesus");
		add("combat.ModuleKillAura");
		add("misc.ModuleMiddleClickFriends");
		add("render.ModuleMobESP");
		add("render.ModuleNames");
		add("player.ModuleNameProtect");
		add("player.ModuleAntiNausea");
		add("misc.ModuleNiceChat");
		add("misc.ModuleNoCheat");
		add("movement.ModuleNoFall");
		add("render.ModuleNoFireEffect");
		add("combat.ModuleNoHurtcam");
		add("combat.ModuleNoKnockback");
		add("movement.ModuleNoSlowdown");
		add("world.ModuleNuker");
		add("movement.ModulePhaze");
		add("render.ModulePlayerESP");
		add("render.ModulePotionEffects");
		add("render.ModuleProphuntESP");
		add("combat.ModuleRegen");
		add("player.ModuleRetard");
		add("movement.ModuleSafeWalk");
		add("render.ModuleSearch");
		add("movement.ModuleSneak");
		add("movement.ModuleSpider");
		add("movement.ModuleSprint");
		add("gui.ModuleStealStoreButtons");
		add("movement.ModuleStep");
		add("player.ModuleTimer");
		add("render.ModuleTracers");
		add("render.ModuleTrack");
		add("render.ModuleTrajectories");
		add("combat.ModuleTriggerBot");
		add("misc.ModuleTwerk");
		add("render.ModuleWaypoints");
		add("world.ModuleWireFrame");
		add("world.ModuleXray");
		
		add("combat.modes.ModulePlayers");
		add("combat.modes.ModuleMobs");
		add("combat.modes.ModuleAnimals");
		add("combat.modes.ModuleInvisibles");
		add("combat.modes.ModulePropBlocks");
		add("combat.modes.ModuleSafeMode");
		
		Resilience.getInstance().getLogger().info("\n===Fully NCP Modules===\n");
		
		for(DefaultModule module : moduleList){
			if(module.getNoCheatMode() == NoCheatMode.COMPATIBLE){
				System.out.println(module.getName());
			}
		}
		
		Resilience.getInstance().getLogger().info("\n===Semi NCP Modules===\n");
		
		for(DefaultModule module : moduleList){
			if(module.getNoCheatMode() == NoCheatMode.SEMICOMPATIBLE){
				System.out.println(module.getName());
			}
		}
		
		Resilience.getInstance().getLogger().info("\n===Non NCP Modules===\n");
		
		for(DefaultModule module : moduleList){
			if(module.getNoCheatMode() == NoCheatMode.INCOMPATIBLE){
				System.out.println(module.getName());
			}
		}
		
		Resilience.getInstance().getLogger().info("\n===Vanilla Only Modules===\n");
		
		for(DefaultModule module : moduleList){
			if(module.getNoCheatMode() == NoCheatMode.VANILLAONLY){
				System.out.println(module.getName());
			}
		}
		
		Resilience.getInstance().getLogger().info("Loaded "+moduleList.size()+" modules!");
	}
	
	private void add(String moduleName){
		try{
			Class clazz = Class.forName("com.krispdev.resilience.module.modules."+moduleName);
			if(clazz.getSuperclass() == DefaultModule.class){
				DefaultModule module = (DefaultModule) clazz.newInstance();
				Resilience.getInstance().getLogger().info("Loaded module "+module.getName());
				moduleList.add(module);
			}
		}catch(ClassNotFoundException e){
			Resilience.getInstance().getLogger().warning("Error loading module: "+moduleName);
			e.printStackTrace();
		} catch (InstantiationException e) {
			Resilience.getInstance().getLogger().warning("Error loading module: "+moduleName);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Resilience.getInstance().getLogger().warning("Error loading module: "+moduleName);
			e.printStackTrace();
		}
	}
	
	public void setModuleState(String modName, boolean state){
		for(DefaultModule mod : moduleList){
			if(mod.getName().equalsIgnoreCase(modName.trim())){
				mod.setEnabled(state);
				return;
			}
		}
		Resilience.getInstance().getLogger().warning("Module not found when attempting to "+(state ? "enable" : "disable")+" it: "+modName);
	}
	
}
