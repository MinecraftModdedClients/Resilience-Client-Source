package com.krispdev.resilience.gui.screens;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringUtils;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.account.GuiAccountScreen;
import com.krispdev.resilience.donate.Donator;
import com.krispdev.resilience.file.FileUtils;
import com.krispdev.resilience.gui.objects.buttons.CheckBox;
import com.krispdev.resilience.gui.objects.buttons.ResilienceButton;
import com.krispdev.resilience.hooks.HookGuiMainMenu;
import com.krispdev.resilience.online.gui.GuiFriendManager;
import com.krispdev.resilience.online.gui.GuiLogin;
import com.krispdev.resilience.online.gui.GuiOnlineDonate;
import com.krispdev.resilience.online.gui.GuiRegister;
import com.krispdev.resilience.utilities.Utils;

public class GuiResilienceMain extends GuiScreen{
	
	public static GuiResilienceMain screen = new GuiResilienceMain(new HookGuiMainMenu());
	private ArrayList<String> updateData = new ArrayList<String>();
	private int count = 0;
	private GuiScreen parentScreen;
	private ArrayList<Object[]> links = new ArrayList<Object[]>();
	private int scroll = 0, maxScroll = 0;
	private String loading = "";
	
	public GuiResilienceMain(GuiScreen parent){
		parentScreen = parent;
		getUpdateData();
	}
	
	public void initGui(){
		this.buttonList.clear();
		CheckBox.checkBoxList.clear();
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(6, 4, 50.5F, 132, 20, "\247bOnline"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(1, 4, 50.5F+24*5, 132, 20, "Info"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(2, 4, 50.5F+24, 132, 20, "Options"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(3, 4, 50.5F+24*2, 132, 20, "Accounts"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(4, 4, 50.5F+24*3, 132, 20, "Donate!"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(5, 4, 50.5F+24*4, 132, 20, "Managers"));
		Resilience.getInstance().getInvoker().addButton(this, new ResilienceButton(0, 4, 50.5F+24*6, 132, 20, "Back"));
	}
	
	public void getUpdateData(){
		new Thread(){
			public void run(){
				try{
					updateData.add("Loading...");
			    	URL url = new URL("http://krispdev.com/Resilience-Update-Info");
			    	BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					String text;
			    	while((text = in.readLine()) != null){
						updateData.add(text);
					}
					updateData.remove(updateData.indexOf("Loading..."));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	@Override
	public void actionPerformed(GuiButton btn){
		if(Resilience.getInstance().getInvoker().getId(btn) == 0){
			Resilience.getInstance().getInvoker().displayScreen(parentScreen);
		}else if(Resilience.getInstance().getInvoker().getId(btn) == 1){
			Resilience.getInstance().getInvoker().displayScreen(new GuiInfo(this));
		}else if(Resilience.getInstance().getInvoker().getId(btn) == 2){
			Resilience.getInstance().getInvoker().displayScreen(new GuiResilienceOptions(this));
		}else if(Resilience.getInstance().getInvoker().getId(btn) == 3){
			Resilience.getInstance().getInvoker().displayScreen(GuiAccountScreen.guiScreen);
		}else if(Resilience.getInstance().getInvoker().getId(btn) == 4){
			Resilience.getInstance().getInvoker().displayScreen(GuiDonateCredits.guiDonate);
		}else if(Resilience.getInstance().getInvoker().getId(btn) == 5){
			Resilience.getInstance().getInvoker().displayScreen(new GuiManagerSelect(this));
		}else if(Resilience.getInstance().getInvoker().getId(btn) == 6){
			new Thread(){
				public void run(){
					if(!Resilience.getInstance().isNewAccount()){
						loading = "Loading...";
						String result = Utils.sendGetRequest("http://resilience.krispdev.com/updateOnline.php?ign="+Resilience.getInstance().getInvoker().getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&online=true&channel="+Resilience.getInstance().getValues().userChannel);
						
						if(result.equals("")){
							if(Donator.isDonator(Resilience.getInstance().getInvoker().getSessionUsername(), 5)){
								Resilience.getInstance().getInvoker().displayScreen(new GuiFriendManager(GuiResilienceMain.screen));
							}else{
								Resilience.getInstance().getInvoker().displayScreen(new GuiOnlineDonate(new GuiFriendManager(GuiResilienceMain.screen)));
							}
						}else{
							if(Donator.isDonator(Resilience.getInstance().getInvoker().getSessionUsername(), 5)){
								Resilience.getInstance().getInvoker().displayScreen(new GuiLogin(GuiResilienceMain.screen, false));
							}else{
								Resilience.getInstance().getInvoker().displayScreen(new GuiOnlineDonate(new GuiFriendManager(GuiResilienceMain.screen)));
							}
						}
						
						loading = "";
					}else{
						Resilience.getInstance().getInvoker().displayScreen(new GuiRegister(GuiResilienceMain.screen, true, false));
					}
				}
			}.start();
		}
	}
	
	public void drawScreen(int i, int j, float f){
		scroll += Mouse.getDWheel()/12;
		if(scroll < maxScroll){
			scroll = maxScroll;
		}
		if(scroll > 0){
			scroll = 0;
		}
		links.clear();
		drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 0xff000000);
		drawRect(140, 50, Resilience.getInstance().getInvoker().getWidth()-8, Resilience.getInstance().getInvoker().getHeight()-8, 0xff101010);
		Resilience.getInstance().getLargeFont().drawString("Change Logs:", 148, 54, 0xff00ffff);
		Resilience.getInstance().getLargeFont().drawCenteredString(loading, Resilience.getInstance().getInvoker().getWidth()/2, 8, 0xff00ffff);
		Resilience.getInstance().getXLargeFont().drawString(Resilience.getInstance().getName(), 4, 1, 0xff0033ff);
		Utils.drawSmallHL(140, 74, Resilience.getInstance().getInvoker().getWidth()-8, 0xffffffff);
		try{
			for(String s : updateData){
				if(78+count+scroll < Resilience.getInstance().getInvoker().getHeight()-8-10 && 78+count+scroll > 70){
					Resilience.getInstance().getStandardFont().drawString(s.replaceAll("&", "\247"), 148, 78+count+scroll, 0xffffffff);
					if(s.contains("http://")){
						links.add(new Object[]{new Rectangle(148, 78+count-4+scroll, (int)Resilience.getInstance().getStandardFont().getWidth(StringUtils.stripControlCodes(s))+148, count+4+scroll), s});
					}
				}
				count+=12;
			}
		}catch(Exception e){}
		maxScroll = -(78+count+4-(Resilience.getInstance().getInvoker().getHeight()-8));
		count = 0;
		if(true){
		}
		super.drawScreen(i, j, f);
	}
	
	@Override
	public void mouseClicked(int x, int y, int btn){
		for(Object[] o : links){
			Rectangle r = (Rectangle) o[0];
			if(x >= r.getX() && x <= r.getMaxX() && y >= r.getY() && y <= r.getMaxY()){
				Sys.openURL((String)o[1]);
				break;
			}
		}
		super.mouseClicked(x, y, btn);
	}
	
}
