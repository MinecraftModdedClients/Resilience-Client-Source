package com.krispdev.resilience.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.lwjgl.Sys;

import com.krispdev.resilience.gui.screens.GuiUpdating;

public class ThreadUpdateGame extends Thread{
	
	private URL url;
	private File location;
	private static float percent = 0;
	private float sizeDone;
	
	public ThreadUpdateGame(URL url, File location){
		this.url = url;
		this.location = location;
	}
	
	@Override
	public void run() {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try
        {
        	GuiUpdating.isDownloading = true;
            in = new BufferedInputStream(url.openStream());
            fout = new FileOutputStream(location);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1)
            {
            	Thread.sleep(1);
                fout.write(data, 0, count);
                sizeDone = location.length();
                percent = (sizeDone/con.getContentLength()*100);
            }
            con.disconnect();
        }catch(Exception e){
        	Sys.openURL("http://resilience.krispdev.com/downloads");
        	e.printStackTrace();
        }
        finally
        {
            if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            if (fout != null)
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            percent = 0;
        }
        GuiUpdating.isExtracting = true;
        GuiUpdating.isDownloading = false;
        String version = "Resilience";
        try{
        File dir = new File("versions"+File.separator+version);
        if(!dir.exists()){
        	dir.mkdirs();
        }
        File jar = new File("versions"+File.separator+"Resilience"+File.separator+version+".jar");
        File zip = new File("Resilience.zip");
        OutputStream out1 = new FileOutputStream("versions"+File.separator+"Resilience"+File.separator+version+".jar");
        OutputStream out2 = new FileOutputStream("versions"+File.separator+"Resilience"+File.separator+version+".json");
        ZipInputStream in2 = new ZipInputStream(new BufferedInputStream(new FileInputStream("Resilience.zip")));
        ZipEntry entry = null;
        while ((entry = in2.getNextEntry()) != null) {
            if (entry.getName().equals("Resilience.jar")) {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in2.read(buffer)) != -1) {
                    out1.write(buffer, 0, len);
                    if(percent < 100){
                    	percent = (float)jar.length()/(float)zip.length()*100F;
                    }
                }
                out1.close();
            }else if(entry.getName().equals("Resilience.json")){
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in2.read(buffer)) != -1) {
                    out2.write(buffer, 0, len);
                }
                out2.close();
            }
        }
        in2.close();
        GuiUpdating.isExtracting = false;
        GuiUpdating.isDeleting = true;
        location.delete();
        GuiUpdating.isDone = true;
        }catch(Exception e){
        	Sys.openURL("http://resilience.krispdev.com/downloads");
        	e.printStackTrace();
        }
	}
	
	public static float getPercentDone(){
		return percent;
	}
}
