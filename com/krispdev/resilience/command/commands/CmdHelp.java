package com.krispdev.resilience.command.commands;

import java.util.ArrayList;

import com.jcraft.jogg.Page;
import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;

public class CmdHelp extends Command{

	private int resultsPerPage = 4;
	private ArrayList<Page> pages = new ArrayList<Page>();
	
	public CmdHelp(){
		super("help", " [Page #]", "Gives command help");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String args[] = cmd.split("help ");
		
		int page = Integer.parseInt(args[1].trim());
		
		if(page == 0){
			Resilience.getInstance().getLogger().warningChat("Page value cannot be zero");
			return true;
		}
		
		pages.clear();
		
		int iterator = 0;
		int pageNum = 0;
		
		int numOfPages = 0;

		if(Command.cmdList.size() % resultsPerPage != 0){
			numOfPages = Command.cmdList.size()/resultsPerPage + 2;
		}else{
			numOfPages = Command.cmdList.size()/resultsPerPage + 1;
		}
		
		for(int i=1; i<=numOfPages; i++){
			pages.add(new Page(i));
		}
		
		for(Command command : Command.cmdList){
			iterator++;
			if(iterator >= resultsPerPage){
				pageNum++;
				iterator = 0;
			}
			pages.get(pageNum).commands.add(command);
		}

		if(page > pages.size()){
			Resilience.getInstance().getLogger().warningChat("Page value too high! Maximum: "+pages.size());
			return true;
		}else{
			Resilience.getInstance().getLogger().infoChat("\2477Showing page "+page+"/"+(numOfPages));
			Resilience.getInstance().getInvoker().addChatMessage("");
			for(Command com : pages.get(page-1).commands){
				Resilience.getInstance().getInvoker().addChatMessage("\247b"+Resilience.getInstance().getCmdPrefix()+com.getWords()+com.getExtras()+" \247f- "+com.getDesc());
			}
			return true;
		}
	}
	
	private class Page{
		private int number;
		public ArrayList<Command> commands = new ArrayList<Command>();
		
		public Page(int number){
			this.number = number;
		}
	}
}
