package com.krispdev.resilience.command;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.commands.CmdAllOff;
import com.krispdev.resilience.command.commands.CmdAntiAfkSet;
import com.krispdev.resilience.command.commands.CmdBindChange;
import com.krispdev.resilience.command.commands.CmdBindRemove;
import com.krispdev.resilience.command.commands.CmdBreadcrumbsClear;
import com.krispdev.resilience.command.commands.CmdEnchant;
import com.krispdev.resilience.command.commands.CmdEnemiesClear;
import com.krispdev.resilience.command.commands.CmdEnemyAdd;
import com.krispdev.resilience.command.commands.CmdEnemyDel;
import com.krispdev.resilience.command.commands.CmdFakeChat;
import com.krispdev.resilience.command.commands.CmdFriendAdd;
import com.krispdev.resilience.command.commands.CmdFriendAddAlias;
import com.krispdev.resilience.command.commands.CmdFriendClear;
import com.krispdev.resilience.command.commands.CmdFriendDel;
import com.krispdev.resilience.command.commands.CmdGetIP;
import com.krispdev.resilience.command.commands.CmdHelp;
import com.krispdev.resilience.command.commands.CmdIRCNick;
import com.krispdev.resilience.command.commands.CmdIRCPrefixChange;
import com.krispdev.resilience.command.commands.CmdIRCUnnick;
import com.krispdev.resilience.command.commands.CmdInvSee;
import com.krispdev.resilience.command.commands.CmdKillAuraMode;
import com.krispdev.resilience.command.commands.CmdMacroAdd;
import com.krispdev.resilience.command.commands.CmdMacroClear;
import com.krispdev.resilience.command.commands.CmdNameProtectSet;
import com.krispdev.resilience.command.commands.CmdPrefixChange;
import com.krispdev.resilience.command.commands.CmdReName;
import com.krispdev.resilience.command.commands.CmdReload;
import com.krispdev.resilience.command.commands.CmdRemoteView;
import com.krispdev.resilience.command.commands.CmdSay;
import com.krispdev.resilience.command.commands.CmdSearchAdd;
import com.krispdev.resilience.command.commands.CmdSearchClear;
import com.krispdev.resilience.command.commands.CmdSearchDel;
import com.krispdev.resilience.command.commands.CmdToggle;
import com.krispdev.resilience.command.commands.CmdTrackClear;
import com.krispdev.resilience.command.commands.CmdTrackSet;
import com.krispdev.resilience.command.commands.CmdVClip;
import com.krispdev.resilience.command.commands.CmdWaypointsAdd;
import com.krispdev.resilience.command.commands.CmdWaypointsAddColour;
import com.krispdev.resilience.command.commands.CmdWaypointsClear;
import com.krispdev.resilience.command.commands.CmdWaypointsDel;
import com.krispdev.resilience.command.commands.CmdWaypointsList;
import com.krispdev.resilience.command.commands.CmdXrayAdd;
import com.krispdev.resilience.command.commands.CmdXrayClear;
import com.krispdev.resilience.command.commands.CmdXrayDel;
import com.krispdev.resilience.command.commands.CmdXrayReset;

public abstract class Command {
	
	private String words;
	private String extras;
	private String desc;
	
	protected Minecraft mc = Resilience.getInstance().getWrapper().getMinecraft();
	
	public static ArrayList<Command> cmdList = new ArrayList<Command>();
	
	public Command(String words, String extras, String desc){
		this.words = words;
		this.extras = extras;
		this.desc = desc;
	}
	
	public static void instantiateCommands(){
		add(new CmdAllOff());
		add(new CmdAntiAfkSet());
		add(new CmdBindChange());
		add(new CmdBindRemove());
		add(new CmdBreadcrumbsClear());
		add(new CmdEnchant());
		add(new CmdEnemyAdd());
		add(new CmdEnemyDel());
		add(new CmdEnemiesClear());
		add(new CmdFakeChat());
		add(new CmdFriendAdd());
		add(new CmdFriendAddAlias());
		add(new CmdFriendClear());
		add(new CmdFriendDel());
		add(new CmdGetIP());
		add(new CmdHelp());
		add(new CmdInvSee());
		add(new CmdIRCNick());
		add(new CmdIRCPrefixChange());
		add(new CmdIRCUnnick());
		add(new CmdKillAuraMode());
		add(new CmdMacroAdd());
		add(new CmdMacroClear());
		add(new CmdReName());
		add(new CmdNameProtectSet());
		add(new CmdPrefixChange());
		add(new CmdReload());
		add(new CmdRemoteView());
		add(new CmdSay());
		add(new CmdSearchAdd());
		add(new CmdSearchDel());
		add(new CmdSearchClear());
		add(new CmdToggle());
		add(new CmdTrackSet());
		add(new CmdTrackClear());
		add(new CmdVClip());
		add(new CmdWaypointsAdd());
		add(new CmdWaypointsAddColour());
		add(new CmdWaypointsClear());
		add(new CmdWaypointsDel());
		add(new CmdWaypointsList());
		add(new CmdXrayAdd());
		add(new CmdXrayDel());
		add(new CmdXrayClear());
		add(new CmdXrayReset());
	}
	
	private static void add(Command cmd){
		cmdList.add(cmd);
	}
	
	public abstract boolean recieveCommand(String cmd) throws Exception;  
	
	public String getWords(){
		return words;
	}
	
	public String getExtras(){
		return extras;
	}
	
	public String getDesc(){
		return desc;
	}
	
	protected void setWords(String words){
		this.words = words;
	}
	
	protected void setExtras(String extras){
		this.extras = extras;
	}
	
	public String getFirstWord(){
		String[] wordArray = words.split(" ");
		return wordArray[0];
	}
}
