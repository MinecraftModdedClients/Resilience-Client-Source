package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Waypoint;

public class CmdWaypointsAddColour extends Command{
	
	public CmdWaypointsAddColour(){
		super("waypoints add ", "[name] [%r] [%g] [%b]", "Adds a waypoint at your current coords (with color)");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		if(!cmd.startsWith("waypoints add")) return false;
		String check[] = cmd.split(" ");
		if(check.length < 6){
			return false;
		}
		for(Waypoint w : Waypoint.waypointsList){
			if(w.getName().equalsIgnoreCase(check[2])){
				Resilience.getInstance().getLogger().warningChat("Already waypoint with name \247b" + w.getName());
				return true;
			}
		}
		Waypoint.waypointsList.add(new Waypoint(check[2], (int) Math.round(Resilience.getInstance().getInvoker().getPosX()), (int) Math.round(Resilience.getInstance().getInvoker().getPosY()), (int) Math.round(Resilience.getInstance().getInvoker().getPosZ()), Float.parseFloat(check[3])/100, Float.parseFloat(check[5])/100, Float.parseFloat(check[4])/100));
		Resilience.getInstance().getFileManager().saveWaypoints();
		Resilience.getInstance().getLogger().infoChat("Successfully added waypoint \247b"+check[2]);
		return true;
	}
	
}
