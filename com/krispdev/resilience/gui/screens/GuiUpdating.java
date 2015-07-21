package com.krispdev.resilience.gui.screens;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.file.ThreadUpdateGame;
import com.krispdev.resilience.utilities.Utils;

public class GuiUpdating extends GuiScreen{
	
	private int count = 0;
	private ThreadUpdateGame downloadFile;
	public static boolean isDone = false;
	public static boolean isDownloading = false;
	public static boolean isExtracting = false;
	public static boolean isDeleting = false;
	
	public void initGui(){
		try {
			URL obj = new URL("http://krispdev.com/updateDownloadIncrement.php?username="+mc.session.getUsername()+"&update="+Resilience.getInstance().getValues().UPDATE_VERSION);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.getResponseCode();
			con.disconnect();
			
            Resilience.getInstance().getFileManager().downloadFile(new File("Resilience.zip"), new URL("http://resilience.krispdev.com/Resilience.zip"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void drawScreen(int i, int j, float f){
		drawRect(0, 0, Resilience.getInstance().getInvoker().getWidth(), Resilience.getInstance().getInvoker().getHeight(), 0xff202020);
		Utils.drawRect(50, Resilience.getInstance().getInvoker().getHeight()-40, Resilience.getInstance().getInvoker().getWidth()-50, Resilience.getInstance().getInvoker().getHeight()-15, 0xff434343);
		float onePixel = (float)(Resilience.getInstance().getInvoker().getWidth()-100)/100F;
		Utils.drawRect(50, Resilience.getInstance().getInvoker().getHeight()-40, onePixel*downloadFile.getPercentDone()+50, Resilience.getInstance().getInvoker().getHeight()-15, 0xff3333ff);
		super.drawScreen(i, j, f);
		Resilience.getInstance().getPanelTitleFont().drawCenteredString(isDone ? "Done. Please restart your game for changes to take effect." : isDownloading ? "Downloading - "+Math.round(ThreadUpdateGame.getPercentDone())+"%" : isExtracting ? "Extracting - "+Math.round(ThreadUpdateGame.getPercentDone())+"%" : isDeleting ? "Deleting residue zip files..." : "Starting...", Resilience.getInstance().getInvoker().getWidth()/2, 4, 0xffffffff);
	}
	
}