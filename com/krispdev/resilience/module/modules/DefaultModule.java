package com.krispdev.resilience.module.modules;

import java.util.ArrayList;
import java.util.List;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventBlockClick;
import com.krispdev.resilience.event.events.player.EventGameShutdown;
import com.krispdev.resilience.event.events.player.EventHealthUpdate;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.event.events.player.EventOnModuleToggle;
import com.krispdev.resilience.event.events.player.EventOnRender;
import com.krispdev.resilience.event.events.player.EventOnUpdate;
import com.krispdev.resilience.event.events.player.EventOnOutwardPacket;
import com.krispdev.resilience.event.events.player.EventPacketReceive;
import com.krispdev.resilience.event.events.player.EventPostMotion;
import com.krispdev.resilience.event.events.player.EventPreMotion;
import com.krispdev.resilience.event.events.player.EventValueChange;
import com.krispdev.resilience.event.listeners.ModListener;
import com.krispdev.resilience.gui.objects.buttons.ModOptionBox;
import com.krispdev.resilience.interfaces.Bindable;
import com.krispdev.resilience.interfaces.Toggleable;
import com.krispdev.resilience.module.categories.ModuleCategory;
import com.krispdev.resilience.module.categories.NoCheatMode;
import com.krispdev.resilience.wrappers.MethodInvoker;

public abstract class DefaultModule implements ModListener, Bindable, Toggleable{
	
	public List<ModOptionBox> guiExtras = new ArrayList<ModOptionBox>();
	
	protected MethodInvoker invoker = Resilience.getInstance().getInvoker();
	
	private String name;
	private String displayName;
	private boolean enabled;
	private String desc;
	private int keyCode;
	private ModuleCategory category;
	private boolean visible;
	private boolean save;
	private NoCheatMode noCheatMode;
	
	public DefaultModule(String name, int keyCode, NoCheatMode noCheatMode){
		this.name = name;
		this.displayName = name;
		this.keyCode = keyCode;
		this.noCheatMode = noCheatMode;
		this.desc = "No description found";
		this.enabled = false;
		this.visible = true;
		this.category = ModuleCategory.NONE;
		this.save = true;
		Resilience.getInstance().getEventManager().registerGameListener(this);
	}
	
	public String getName(){
		return name;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public String getDescription(){
		return desc;
	}
	
	public int getKeyCode(){
		return keyCode;
	}
	
	public ModuleCategory getCategory(){
		return category;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public void setName(String s){
		this.name = s;
	}
	
	public void setDisplayName(String s){
		this.displayName = s;
	}
	
	public void setDescription(String s){
		desc = s;
	}
	
	public void setKeyBind(int keyCode){
		this.keyCode = keyCode;
	}
	
	public void setCategory(ModuleCategory c){
		this.category = c;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public void setSave(boolean save){
		this.save = save;
	}
	
	public boolean shouldSave(){
		return save;
	}
	
	@Override
	public void onUpdate(EventOnUpdate e) {}

	@Override
	public void onPreMotion(EventPreMotion e) {}

	@Override
	public void onPostMotion(EventPostMotion e) {}
	
	@Override
	public void onHealthUpdate(EventHealthUpdate e) {}

	@Override
	public void onPacketReceive(EventPacketReceive e){}
	
	@Override
	public void onBlockClicked(EventBlockClick e){}
	
	@Override
	public void onGameShutdown(EventGameShutdown e){}
	
	@Override
	public void onRender(EventOnRender e){}
	
	@Override
	public void onClick(EventOnClick e){}
	
	@Override
	public void onValueChange(EventValueChange event){}
	
	@Override
	public void onOutwardPacket(EventOnOutwardPacket event){}
	
	@Override
	public void onModuleToggle(EventOnModuleToggle event){}
	
	@Override
	public abstract void onEnable();

	@Override
	public abstract void onDisable();

	@Override
	public void onToggle() {}

	@Override
	public void toggle() {
		final EventOnModuleToggle eventToggle = new EventOnModuleToggle(this);
		eventToggle.onEvent();
		
		if(eventToggle.isCancelled()){
			return;
		}
		
		this.enabled = !enabled;
		onToggle();
		if(enabled){
			onEnable();
		}else{
			onDisable();
		}
		if(Resilience.getInstance().isEnabled()){
			Resilience.getInstance().getFileManager().saveEnabledMods();
		}
	}

	@Override
	public void setEnabled(boolean flag) {
		final EventOnModuleToggle eventToggle = new EventOnModuleToggle(this);
		eventToggle.onEvent();
		
		if(eventToggle.isCancelled()){
			return;
		}
		
		this.enabled = flag;
		onToggle();
		if(enabled){
			onEnable();
		}else{
			onDisable();
		}
		if(Resilience.getInstance().isEnabled()){
			Resilience.getInstance().getFileManager().saveEnabledMods();
		}
	}

	@Override
	public void onKeyDown(int keyCode) {
		if(!Resilience.getInstance().isEnabled() && !(this.getCategory() == ModuleCategory.GUI)) return;
		if(keyCode == this.keyCode) toggle();
	}

	public NoCheatMode getNoCheatMode(){
		return noCheatMode;
	}
	
}
