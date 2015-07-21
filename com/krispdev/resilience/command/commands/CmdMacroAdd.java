package com.krispdev.resilience.command.commands;

import org.lwjgl.input.Keyboard;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.command.objects.Macro;

public class CmdMacroAdd extends Command{
	
	public CmdMacroAdd(){
		super("macro add ", "[Key] [Command]", "Adds a macro");
	}

	@Override
	public boolean recieveCommand(String cmd) throws Exception {
		String args[] = cmd.split("macro add ");
		String keybind[] = args[1].split(" ");
		String args2[] = args[1].split(keybind[0]);
		if(Character.isWhitespace(args2[1].charAt(0))){
			args2[1] = args2[1].replaceFirst(" ", "");
		}
		Macro.macroList.add(new Macro(Keyboard.getKeyIndex(keybind[0].trim().toUpperCase()), args2[1]));
		Resilience.getInstance().getLogger().infoChat("Added a macro to \"\247b"+keybind[0]+"\247f\" that will say \"\247b"+args2[1]+"\247f\" in the chat");
		Resilience.getInstance().getFileManager().saveMacros();
		return true;
	}
	
}
