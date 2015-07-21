package com.krispdev.resilience.module.values;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.command.objects.Waypoint;
import com.krispdev.resilience.online.gui.GuiGroupChat;
import com.krispdev.resilience.utilities.value.Value;
import com.krispdev.resilience.utilities.value.values.BoolValue;
import com.krispdev.resilience.utilities.value.values.NumberValue;
import com.krispdev.resilience.utilities.value.values.StringValue;

public class Values {
	
	public ArrayList<Value> values = new ArrayList<Value>();
	public ArrayList<NumberValue> numValues = new ArrayList<NumberValue>();
	public ArrayList<BoolValue> boolValues = new ArrayList<BoolValue>();
	public ArrayList<StringValue> strValues = new ArrayList<StringValue>();
	
	public Waypoint deathWaypoint;
	
	public NumberValue antiAFKSeconds = new NumberValue(60, 1, 120, "AntiAFK Delay (Sec.)", true);
	public NumberValue autoSoupHealth = new NumberValue(14, 1, 19, "AutoSoup Threshold", true);
	public NumberValue flySpeed = new NumberValue(2, 0, 10, "Flight Speed", false);
	public NumberValue speed = new NumberValue(11, 3, 15, "KillAura Speed", false);
	public NumberValue range = new NumberValue(3.9F, 2.5F, 6, "KillAura Range", false);
	public NumberValue nukerRadius = new NumberValue(4, 2, 6, "Nuker Radius", true);
	public NumberValue fastBreakSpeed = new NumberValue(0.8F, 0.1F, 0.9F, "FastBreak Speed", false);
	public NumberValue timerSpeed = new NumberValue(2, 0.1F, 10, "Speed Multiplier", false);
	public NumberValue highJumpMultiplier = new NumberValue(3, 2, 30, "HighJump Multiplier", true);
	public NumberValue stepHeight = new NumberValue(2, 1, 30, "Step Height", true);
	public NumberValue searchRange = new NumberValue(100, 40, 300, "Search Range", true);
	public NumberValue buttonSize = new NumberValue(1, 1, 2, "GUI Button Size", true);
	public NumberValue regenSpeed = new NumberValue(2000, 50, 3000, "Regen Speed", true);
	
	public BoolValue players = new BoolValue("Attack Players", true);
	public BoolValue mobs = new BoolValue("Attack Mobs", true);
	public BoolValue animals = new BoolValue("Attack Animals", true);
	public BoolValue invisibles = new BoolValue("Attack Invisibles", false);
	public BoolValue propBlocks = new BoolValue("Attack PropBlocks", false);
	public BoolValue safeMode = new BoolValue("Safe Mode", false);
	public BoolValue chestESPTracers = new BoolValue("Draw ChestESP Tracers", false);
	
	public StringValue nameProtectAlias = new StringValue("NameProtect Alias", "SetNameProtectAlias");
	public StringValue cmdPrefix = new StringValue("Command Prefix", ".");
	public StringValue ircPrefix = new StringValue("IRC Prefix", "@");

	public void initValues(){
		add(new Value("=-=-=-=Number Settings=-=-=-="));
		
		add(autoSoupHealth);
		add(antiAFKSeconds);
		add(fastBreakSpeed);
		add(flySpeed);
		add(highJumpMultiplier);
		add(nukerRadius);
		add(timerSpeed);
		add(searchRange);
		add(speed);
		add(stepHeight);
		add(range);
		add(regenSpeed);
		
		add(new Value("=-=-=-=True/False Settings=-=-=-="));
		
		add(chestESPTracers);
		add(players);
		add(mobs);
		add(animals);
		
		add(new Value("=-=-=-=Other Settings=-=-=-="));
		
		add(nameProtectAlias);
		add(cmdPrefix);
		add(ircPrefix);
	}
	
	public boolean isDonator(String username){
		try{
			URL url = new URL("http://resilience.krispdev.com/Rerererencedonatorsx789");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String temp;
			while((temp = in.readLine()) != null){
				String args[] = temp.split("BITCHEZBECRAYCRAY123WAYOVER30CHAR");
				if(username.equalsIgnoreCase(args[3])) return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isAccountBanned(){
		try{
			URL url = new URL("http://resilience.krispdev.com/BannedMembers");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String temp;
			while((temp = in.readLine()) != null){
				String args[] = temp.split("~SPLITCHAR~");
				if(args[0].trim().equalsIgnoreCase(Resilience.getInstance().getInvoker().getSessionUsername())){
					banReason=args[1];
					banTime=args[2];
					return true;
				}
			}
			return false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean needsUpdate(){
		try{
	    	URL url = new URL("http://krispdev.com/Resilience-Version");
	    	BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String text = in.readLine();
			if(Integer.parseInt(text) > UPDATE_VERSION){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	private void add(Value v){
		if(v instanceof NumberValue){
			numValues.add((NumberValue)v);
		}else if(v instanceof BoolValue){
			boolValues.add((BoolValue)v);
		}else if(v instanceof StringValue){
			strValues.add((StringValue)v);
		}
		values.add(v);
	}

	public boolean enabledModsEnabled = false;
	public boolean flightEnabled = false;
	public boolean niceChatEnabled = false;
	public boolean nameProtectEnabled = false;
	public boolean caveFinderEnabled = false;
	public boolean namesEnabled = false;
	public boolean noFireEffectEnabled = false;
	public boolean potionEffectsEnabled = false;
	public boolean fastBreakEnabled = false;
	public boolean ircEnabled = false;
	public boolean killAuraEnabled = false;
	public boolean autoBlockEnabled = false;
	public boolean noHurtcamEnabled = false;
	public boolean freecamEnabled = false;
	public boolean highJumpEnabled = false;
	public boolean jesusEnabled = false;
	public boolean noSlowdownEnabled = false;
	public boolean autoRespawnEnabled = false;
	public boolean antiBlindessEnabled = false;
	public boolean antiNauseaEnabled = false;
	public boolean safeWalkEnabled = false;
	public boolean autoChestStealEnabled = false;
	public boolean wireFrameEnabled = false;
	public boolean stealStoreButtonsEnabled = false;
	
	public List<Double[]> breadcrumbPosList = new ArrayList<Double[]>();
	public List<Double[]> trackPosList = new ArrayList<Double[]>();
	
	public String trackName = "No one to track";
	public String banReason = "No reason given. Contact krisphf@gmail.com for details.";
	public String banTime = "Infinite";
	
    public final ResourceLocation altBackground = new ResourceLocation("textures/blocks/stone.png");
    
    public final int UPDATE_VERSION = 29;
	
    public ArrayList<Float[]> searchIds = new ArrayList<Float[]>();
    
    public int ticksForSearch = 70;
    public int version = 4;
    
    public String onlinePassword;
    public String ircChannel = "#ResilienceMCChan";
    public String ircChatServer = "irc.nickreboot.com";
    public String userChannel;
    
    public GuiGroupChat userChat;
}
