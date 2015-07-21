package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Waypoint;

public class CmdWaypointsDel extends Command{
	
	public CmdWaypointsDel(){
		super("waypoints del ", "[name]", "Deletes the specified waypoint");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(!cmd.startsWith("waypoints del")) return false;
		String args[] = cmd.split("del ");
		for(Waypoint w : Waypoint.waypointsList){
			if(w.getName().equalsIgnoreCase(args[1])){
				Waypoint.waypointsList.remove(Waypoint.waypointsList.indexOf(w));
				Resilience.getInstance().getFileManager().saveWaypoints();
				Resilience.getInstance().getLogger().infoChat("Successfully deleted waypoint \"" + args[1]+"\"");
				return true;
			}
		}
		Resilience.getInstance().getLogger().infoChat("Waypoint \""+args[1]+"\" not found");
		return true;
	}
	
}
