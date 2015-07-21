package com.krispdev.resilience;

import java.awt.Font;
import java.util.Random;

import net.minecraft.util.Session;

import com.krispdev.resilience.command.Command;
import com.krispdev.resilience.event.EventManager;
import com.krispdev.resilience.event.events.player.EventGameShutdown;
import com.krispdev.resilience.file.FileManager;
import com.krispdev.resilience.gui.objects.ClickGui;
import com.krispdev.resilience.irc.MainIRCManager;
import com.krispdev.resilience.logger.ResilienceLogger;
import com.krispdev.resilience.module.ModuleManager;
import com.krispdev.resilience.module.values.Values;
import com.krispdev.resilience.online.irc.extern.BotManager;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.utilities.XrayUtils;
import com.krispdev.resilience.utilities.font.TTFRenderer;
import com.krispdev.resilience.wrappers.MethodInvoker;
import com.krispdev.resilience.wrappers.Wrapper;

public class Resilience {

	/**
	 * The singleton class holding all instances of different things
	 */
	
	private static Resilience instance = null;
	private ResilienceLogger logger = null;
	private Wrapper wrapper = null;
	private MethodInvoker invoker = null;
	private ModuleManager moduleManager = null;
	private EventManager eventManager = null;
	private ClickGui clickGui = null;
	private com.krispdev.resilience.gui2.objects.ClickGui clickGui2 = null;
	private FileManager fileManager = null;
	private Values values = null;
	private XrayUtils xrayUtils = null;
	private BotManager ircManager = null;
	private MainIRCManager chatIRCManager = null;
	
	/**
	 * Fonts
	 */
	
	private TTFRenderer panelTitleFont = null;
	private TTFRenderer buttonExtraFont = null;
	private TTFRenderer standardFont = null;
	private TTFRenderer modListFont = null;
	private TTFRenderer radarFont = null;
	private TTFRenderer chatFont = null;
	private TTFRenderer waypointFont = null;
	private TTFRenderer largeFont = null;
	private TTFRenderer xLargeFont = null;
	
	/**
	 * Client defining
	 */
	
	private String name = "Resilience";
	
	private String version = "1.6.1 Release";
	
	private String author = "Krisp_";
	
	private boolean isFirstTime = false;
	
	private boolean enabled = true;
	
	private boolean isFirstTimeOnline = false;
	
	private boolean newAccount = false;
	
	/**
	 * Other
	 */
	
	private String onlineStatus = "Servers Offline";
	
	/**
	 * The start method calls the instantiation method inside the module manager and basically prepares the client
	 * for startup. The way in which these methods are called is important as a lot of the methods and fields in
	 * other objects called later reference objects that need to be instantiated before them.
	 */
	
	public void start(){
		//Utils.setSessionData("krisphf@gmail.com", "AquilinoJalava273");
		//getWrapper().getMinecraft().session = new Session("NoahGarliot", "", "");
		
		String result = Utils.sendGetRequest("http://resilience.krispdev.com/isUser.php?ign="+getInvoker().getSessionUsername());
		if(!result.equals("err")){
			onlineStatus = "Servers Online";
			boolean first = Boolean.parseBoolean(result);
			if(!first){
				setNewAccount(true);
			}
		}
		getModuleManager().instantiateModules();
		getValues().initValues();
		try{
			getFileManager().init();
		}catch (Exception e1){
			e1.printStackTrace();
		}
		Command.instantiateCommands();
		Utils.initDonators();
		getModuleManager().setModuleState("Target Players", true);
		getModuleManager().setModuleState("Target Mobs", true);
		getModuleManager().setModuleState("Target Animals", true);
		EventGameShutdown forClassLoadingPurposes = new EventGameShutdown(0);
		
		String server = Utils.getSiteContent("http://resilience.krispdev.com/ResilienceOnlineChatServer");
		if(!server.equals("") && !server.equals("ERR")){
			getValues().ircChatServer = server;
		}
		
		String channel = Utils.getSiteContent("http://resilience.krispdev.com/IRCChannel");
		if(!channel.equals("") && !channel.equals("ERR")){
			getValues().ircChannel = channel;
		}
		getIRCChatManager();
		
		Random rand = new Random();
		
		getValues().userChannel = "#USER"+Math.abs(rand.nextLong());
		getIRCManager();
		if(getValues().ircEnabled){
			getIRCChatManager();
		}
	}
	
	/**
	 * This, kids, is a singleton. The only proper one I've seen in a long time.
	 */
	
	public static Resilience getInstance(){
		if(instance == null){
			instance = new Resilience();
		}
		return instance;
	}
	
	public ResilienceLogger getLogger(){
		if(logger == null){
			logger = new ResilienceLogger();
		}
		return logger;
	}
	
	public Wrapper getWrapper(){
		if(wrapper == null){
			wrapper = new Wrapper();
		}
		return wrapper;
	}
	
	public MethodInvoker getInvoker(){
		if(invoker == null){
			invoker = new MethodInvoker();
		}
		return invoker;
	}
	
	public ModuleManager getModuleManager(){
		if(moduleManager == null){
			moduleManager = new ModuleManager();
		}
		return moduleManager;
	}
	
	public EventManager getEventManager(){
		if(eventManager == null){
			eventManager = new EventManager();
		}
		return eventManager;
	}
	
	public ClickGui getClickGui(){
		if(clickGui == null){
			clickGui = new ClickGui();
		}
		return clickGui;
	}
	
	public com.krispdev.resilience.gui2.objects.ClickGui getClickGui2(){
		if(clickGui2 == null){
			clickGui2 = new com.krispdev.resilience.gui2.objects.ClickGui();
		}
		return clickGui2;
	}
	
	public FileManager getFileManager(){
		if(fileManager == null){
			fileManager = new FileManager();
		}
		return fileManager;
	}
	
	public Values getValues(){
		if(values == null){
			values = new Values();
		}
		return values;
	}
	
	public XrayUtils getXrayUtils(){
		if(xrayUtils == null){
			xrayUtils = new XrayUtils();
		}
		return xrayUtils;
	}
	
	public BotManager getIRCManager(){
		if(ircManager == null){
			ircManager = new BotManager(getInvoker().getSessionUsername());
			new Thread(ircManager).start();
		}
		return ircManager;
	}

	public MainIRCManager getIRCChatManager(){
		if(chatIRCManager == null){
			chatIRCManager = new MainIRCManager(getInvoker().getSessionUsername());
			new Thread(chatIRCManager).start();
		}
		return chatIRCManager;
	}
	
	/**
	 * Fonts
	 */
	public TTFRenderer getStandardFont(){
		if(standardFont == null){
			standardFont = new TTFRenderer("Arial", Font.PLAIN, 18);
		}
		
		return standardFont;
	}
	
	public TTFRenderer getPanelTitleFont(){
		if(panelTitleFont == null){
			panelTitleFont = new TTFRenderer("Arial", Font.PLAIN, 23);
		}
		return panelTitleFont;
	}
	
	public TTFRenderer getButtonExtraFont(){
		if(buttonExtraFont == null){
			buttonExtraFont = new TTFRenderer("Arial", Font.PLAIN, 16);
		}
		return buttonExtraFont;
	}
	
	public TTFRenderer getModListFont(){
		if(modListFont == null){
			modListFont = new TTFRenderer("Arial", Font.PLAIN, 20);
		}
		return modListFont;
	}
	
	public TTFRenderer getRadarFont(){
		if(radarFont == null){
			radarFont = new TTFRenderer("Arial", Font.PLAIN, 10);
		}
		return radarFont;
	}
	
	public TTFRenderer getChatFont(){
		if(chatFont == null){
			chatFont = new TTFRenderer("Arial", Font.PLAIN, 19);
		}
		return chatFont;
	}
	
	public TTFRenderer getWaypointFont(){
		if(waypointFont == null){
			waypointFont = new TTFRenderer("Arial", Font.PLAIN, 40);
		}
		return waypointFont;
	}
	
	public TTFRenderer getLargeFont(){
		if(largeFont == null){
			largeFont = new TTFRenderer("Arial", Font.PLAIN, 30);
		}
		return largeFont;
	}
	
	public TTFRenderer getLargeItalicFont(){
		if(largeFont == null){
			largeFont = new TTFRenderer("Arial", Font.ITALIC, 30);
		}
		return largeFont;
	}
	
	public TTFRenderer getXLargeFont(){
		if(xLargeFont == null){
			xLargeFont = new TTFRenderer("Arial", Font.PLAIN, 45);
		}
		return xLargeFont;
	}
	
	/**
	 * Client defining
	 */
	
	public String getCmdPrefix(){
		return getValues().cmdPrefix.getValue();
	}
	
	public String getIRCPrefix(){
		return getValues().ircPrefix.getValue();
	}
	
	public String getVersion(){
		return version;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isFirstTime(){
		return isFirstTime;
	}
	
	public void setFirstTime(){
		isFirstTime = true;
	}

	public boolean isFirstTimeOnline(){
		return isFirstTimeOnline;
	}
	
	public void setFirstTimeOnline(boolean state){
		isFirstTimeOnline = state;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public void setEnabled(boolean flag){
		enabled = flag;
	}
	
	public void setCmdPrefix(String prefix){
		getValues().cmdPrefix.setValue(prefix);
	}

	public void setIRCPrefix(String prefix) {
		getValues().ircPrefix.setValue(prefix);
	}

	public void setNewAccount(boolean state) {
		newAccount = state;
	}
	
	public boolean isNewAccount(){
		return newAccount;
	}
	
	public String getOnlineStatus(){
		return onlineStatus;
	}
	
}
