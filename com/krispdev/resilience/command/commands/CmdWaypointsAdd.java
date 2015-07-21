package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Waypoint;

public class CmdWaypointsAdd extends Command{
	
	public CmdWaypointsAdd(){
		super("waypoints add ", "[name]", "Adds a waypoint at your coordinates");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(!cmd.startsWith("waypoints add")) return false;
		String check[] = cmd.split(" ");
		if(check.length > 3){
			return false;
		}
		String args[] = cmd.split("add ");
		for(Waypoint w : Waypoint.waypointsList){
			if(w.getName().equalsIgnoreCase(check[2])){
				Resilience.getInstance().getLogger().warningChat("Already waypoint with name \247b" + w.getName());
				return true;
			}
		}
		Waypoint.waypointsList.add(new Waypoint(args[1], (int) Resilience.getInstance().getInvoker().getPosX(), (int) Resilience.getInstance().getInvoker().getPosY(), (int) Resilience.getInstance().getInvoker().getPosZ(), 1, 1, 1));
		Resilience.getInstance().getFileManager().saveWaypoints();
		Resilience.getInstance().getLogger().infoChat("Successfully added waypoint \247b"+args[1]);
		return true;
	}
	
}
