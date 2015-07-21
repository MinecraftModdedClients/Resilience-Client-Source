package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Waypoint;

public class CmdWaypointsList extends Command{
	
	public CmdWaypointsList(){
		super("waypoints list", "", "Lists all your waypoints");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(!cmd.startsWith("waypoints list")) return false;
		for(Waypoint w : Waypoint.waypointsList){
			if(w!=null){
				Resilience.getInstance().getLogger().infoChat(w.toString());
			}
		}
		return true;
	}
	
}
