package com.krispdev.resilience.event.listeners;

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

public interface ModListener extends Listener{
	
	void onPreMotion(EventPreMotion e);
	void onUpdate(EventOnUpdate e);
	void onPostMotion(EventPostMotion e);
	void onHealthUpdate(EventHealthUpdate e);
	void onPacketReceive(EventPacketReceive e);
	void onBlockClicked(EventBlockClick e);
	void onGameShutdown(EventGameShutdown e);
	void onRender(EventOnRender e);
	void onClick(EventOnClick e);
	void onValueChange(EventValueChange e);
	void onOutwardPacket(EventOnOutwardPacket e);
	void onModuleToggle(EventOnModuleToggle e);
	
}
