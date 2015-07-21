package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Waypoint;

public class CmdWaypointsClear extends Command{

	public CmdWaypointsClear(){
		super("waypoints clear", "", "Clears all the waypoints");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(!cmd.startsWith("waypoints clear")) return false;
		if(cmd.equalsIgnoreCase("waypoints clear")){
			Waypoint.waypointsList.clear();
			Resilience.getInstance().getFileManager().saveWaypoints();
			Resilience.getInstance().getLogger().infoChat("Cleared all waypoints");
			return true;
		}
		return false;
	}
}
