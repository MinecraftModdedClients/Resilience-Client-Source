package com.krispdev.resilience.utilities;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.BufferUtils.createFloatBuffer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Session;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.donate.Donator;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

public final class Utils {
	
	private static ByteBuffer boxSides;
	private static int cube;
	
	/**
	 * Draws a rectangle. An exact copy of the method in Gui.java, only the parameters are changed to floats
	 * because it allows greater precision.
	 */
	
    public static final void drawRect(float par0, float par1, float par2, float par3, int par4)
    {
        float var5;

        if (par0 < par2)
        {
            var5 = par0;
            par0 = par2;
            par2 = var5;
        }

        if (par1 < par3)
        {
            var5 = par1;
            par1 = par3;
            par3 = var5;
        }

        float var10 = (float)(par4 >> 24 & 255) / 255.0F;
        float var6 = (float)(par4 >> 16 & 255) / 255.0F;
        float var7 = (float)(par4 >> 8 & 255) / 255.0F;
        float var8 = (float)(par4 & 255) / 255.0F;
        Tessellator var9 = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(var6, var7, var8, var10);
        var9.startDrawingQuads();
        var9.addVertex((double)par0, (double)par3, 0.0D);
        var9.addVertex((double)par2, (double)par3, 0.0D);
        var9.addVertex((double)par2, (double)par1, 0.0D);
        var9.addVertex((double)par0, (double)par1, 0.0D);
        var9.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static final void drawBetterRect(double x, double y, double x1, double y1, int color2, int color)
	{
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
		drawRect((int)x, (int)y, (int)x1, (int)y1, color);
	    GL11.glScalef(0.5f, 0.5f, 0.5f);
	    drawRect((int)x * 2 - 1,(int) y * 2, (int)x * 2, (int)y1 * 2 - 1, color2);
	    drawRect((int)x * 2, (int)y * 2 - 1, (int)x1 * 2, (int)y * 2, color2);
	    drawRect((int)x1 * 2,(int) y * 2, (int)x1 * 2 + 1, (int)y1 * 2 - 1, color2);
	    drawRect((int)x * 2, (int)y1 * 2 - 1, (int)x1 * 2, (int)y1 * 2, color2);
        GL11.glDisable(GL11.GL_BLEND);
	    GL11.glScalef(2F, 2F, 2F);
	}
	
    public static final void drawLine(float x, float y, float x1, float y1, float width, int colour){
		float red = (float)(colour >> 16 & 0xFF) / 255F;
		float green = (float)(colour >> 8 & 0xFF) / 255F;
		float blue = (float)(colour & 0xFF) / 255F;
		float alpha = (float)(colour >> 24 & 0xFF) / 255F;
    	
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glLineWidth(width);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x1, y1);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
    
	public static final void drawSmallHL(float x, float y, float x2, int colour){
		drawRect(x, y, x2, y+0.5F, colour);
	}
	
	public static final void drawSmallVL(float y, float x, float y2, int colour){
		drawRect(x, y, x+0.5F, y2, colour);
	}
	
	public static final String setSessionData(String user, String pass) {
        YggdrasilAuthenticationService authentication = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)authentication.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(user);
        auth.setPassword(pass);
        try{
            auth.logIn();
            Resilience.getInstance().getWrapper().getMinecraft().session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId(), auth.getAuthenticatedToken());
            return "\247bSuccess!";
        }catch(AuthenticationException e){
        	return "\247cBad Login";
        }
	}
	
	public static final void drawItemTag(int x, int y, ItemStack item) {  
		GL11.glPushMatrix();
		GL11.glDisable(3042);
		GL11.glEnable(32826);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDepthMask(true);
		GL11.glDisable(2929);
		try{
			Resilience.getInstance().getInvoker().renderItemIntoGUI(item, x, y);
			Resilience.getInstance().getInvoker().renderItemOverlayIntoGUI(item, x, y);
		}catch(Exception e){}
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	
	}
	
	public static void drawCircle(float x, float y, double radius, int colour) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		radius *= 2;
		x *= 2;
		y *= 2;
		
		float red = (float) (colour >> 16 & 0xff) / 255F;
		float green = (float) (colour >> 8 & 0xff) / 255F;
		float blue = (float) (colour & 0xff) / 255F;
		float alpha = (float) (colour >> 24 & 0xff) / 255F;
		
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int i = 0; i <= 360; i++) {
			double x1 = Math.sin((i * Math.PI / 180)) * radius;
			double y1 = Math.cos((i * Math.PI / 180)) * radius;
			GL11.glVertex2d(x + x1, y + y1);
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glScalef(2F, 2F, 2F);
	}
	
    public static final float sqrt_double(double par0){
        return (float)Math.sqrt(par0);
    }
    
    public static final double wrapAngleTo180_double(double par0)
    {
        par0 %= 360.0D;

        if (par0 >= 180.0D)
        {
            par0 -= 360.0D;
        }

        if (par0 < -180.0D)
        {
            par0 += 360.0D;
        }

        return par0;
    }
	
    public static final void initDonators(){
    	new Thread(){
    		public void run(){
    	    	try{
    				Donator.donatorList.clear();
    				URL url = new URL("http://resilience.krispdev.com/Rerererencedonatorsx789");
    				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    				String temp;
    				while((temp = in.readLine()) != null){
    					String args[] = temp.split("BITCHEZBECRAYCRAY123WAYOVER30CHAR");
    					if(Float.parseFloat(args[2]) >= 5){
    						Donator.donatorList.add(new Donator(args[0],args[1],Float.parseFloat(args[2]),args[3]));
    					}
    				}
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    		}
    	}.start();
    }
    
	public static AxisAlignedBB getAABB(int x, int y, int z)
	{
		final Entity p = Minecraft.getMinecraft().thePlayer;
		final double var8 = p.lastTickPosX + (p.posX - p.lastTickPosX);
        final double var10 = p.lastTickPosY + (p.posY - p.lastTickPosY);
        final double var12 = p.lastTickPosZ + (p.posZ - p.lastTickPosZ);
        final float var6 = 0.002F;
        Block block = Minecraft.getMinecraft().theWorld.getBlock(x, y, z);
        return block.getSelectedBoundingBoxFromPool(Minecraft.getMinecraft().theWorld, x, y, z).expand((double)var6, (double)var6, (double)var6).getOffsetBoundingBox(-var8, -var10, -var12);
	}
	
	public static ByteBuffer getSides(){
		boxSides = createByteBuffer(24);
		boxSides.put(new byte[] {0, 3, 2, 1, 2, 5, 6, 1, 6, 7, 0, 1, 0, 7, 4, 3, 4, 7, 6, 5, 2, 3, 4, 5});
		boxSides.flip();
		cube = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, cube);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, getBox(-0.5F, -0.5F, -0.5F, 0.5F, 0.5F, 0.5F), GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return boxSides;
	}
	
	public static FloatBuffer getBox(AxisAlignedBB bound)
	{
		return getBox((float)bound.minX, (float)bound.minY, (float)bound.minZ, (float)bound.maxX, (float)bound.maxY, (float)bound.maxZ);
	}
	
	public static FloatBuffer getBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		final FloatBuffer vertices = createFloatBuffer(24);
		vertices.put(new float[] {minX, minY, minZ, maxX, minY, minZ, maxX, maxY, minZ, minX, maxY, minZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, minY, maxZ, minX, minY, maxZ});
		vertices.flip();
		return vertices;
	}	
	
	private static String returnString;
	
	public static final String sendGetRequest(String ur){
		String url1 = ur.replaceAll(" ", "%20").replaceAll("#", "%23");
		
		try {
			
			URL url = new URL(url1);
			
			URL obj = url;
			 
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			con.setRequestMethod("GET");
	 
			int responseCode = con.getResponseCode();
	 
			BufferedReader in = new BufferedReader(
			new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			returnString = response.toString();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "err";
		}
		
		if(returnString == null){
			return "null";
		}
		return returnString;
	}
	
	public static final void setOnline(boolean flag){
		final boolean online = flag;
		
		System.out.println("sending with channel: "+Resilience.getInstance().getValues().userChannel);
		
		String result = sendGetRequest("http://resilience.krispdev.com/updateOnline.php?ign="+Resilience.getInstance().getInvoker().getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&online="+online+"&channel="+Resilience.getInstance().getValues().userChannel);
		if(!flag){
			String result2 = sendGetRequest("http://resilience.krispdev.com/updateStatus.php?ign="+Resilience.getInstance().getInvoker().getSessionUsername()+"&password="+Resilience.getInstance().getValues().onlinePassword+"&status=Logging in...");
		}
	}
	
	public static final String getSiteContent(String u){
		try{
	        URL url = new URL(u);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(url.openStream()));

	        String inputLine;
	        String finalResult = "";
	        while ((inputLine = in.readLine()) != null){
	        	finalResult += inputLine;
	        }
	        
	        in.close();
	        return finalResult;
		}catch(Exception e){
			return "ERR";
		}
	}
	
	private static String server;
	private static String username;
	private static State state;
	
	public enum State {
		REGISTER,
		UNREGISTER,
		UPDATE
	}
    
}
