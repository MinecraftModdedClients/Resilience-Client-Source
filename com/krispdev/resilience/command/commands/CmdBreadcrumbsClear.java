package com.krispdev.resilience.command.commands;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdBreadcrumbsClear extends Command{
	
	public CmdBreadcrumbsClear(){
		super("breadcrumbs clear", "", "Clears the breadcrumb trail");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		Resilience.getInstance().getValues().breadcrumbPosList.clear();
		Resilience.getInstance().getLogger().infoChat("Cleared the breadcrumb trail");
		return true;
	}
	
}
