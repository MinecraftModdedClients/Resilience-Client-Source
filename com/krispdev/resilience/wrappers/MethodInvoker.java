package com.krispdev.resilience.wrappers;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.hooks.HookGuiIngame;
import com.mojang.authlib.GameProfile;

public class MethodInvoker {
	
    private RenderItem renderItem = new RenderItem();
	
	private Wrapper wrapper = Resilience.getInstance().getWrapper();
	
	private String entityLivingBaseLoc = "net.minecraft.entity.EntityLivingBase";
	
	public void sendChatMessage(String msg){
		wrapper.getPlayer().sendChatMessage(msg);
	}
	
	public void addChatMessage(String str){
		Object chat = new ChatComponentText(str);
		if(str != null){
    		wrapper.getMinecraft().ingameGUI.getChatGUI().func_146227_a((IChatComponent)chat);	
		}	
	}
	
	public float getRotationYaw(){
		return wrapper.getPlayer().rotationYaw;
	}
	
	public float getRotationPitch(){
		return wrapper.getPlayer().rotationPitch;
	}
	
	public void setRotationYaw(float yaw){
		wrapper.getPlayer().rotationYaw = yaw;
	}
	
	public void setRotationPitch(float pitch){
		wrapper.getPlayer().rotationPitch = pitch;
	}
	
	public void setSprinting(boolean sprinting){
		wrapper.getPlayer().setSprinting(sprinting);
	}
	
	public boolean isOnLadder(){
		return wrapper.getPlayer().isOnLadder();
	}
	
	public float moveForward(){
		return wrapper.getPlayer().moveForward;
	}
	
	public boolean isCollidedHorizontally(){
		return wrapper.getPlayer().isCollidedHorizontally;
	}

	public void setMotionX(double x){
		wrapper.getPlayer().motionX = x;
	}
	
	public void setMotionY(double y){
		wrapper.getPlayer().motionY = y;
	}
	
	public void setMotionZ(double z){
		wrapper.getPlayer().motionZ = z;
	}
	
	public double getMotionX(){
		return wrapper.getPlayer().motionX;
	}
	
	public double getMotionY(){
		return wrapper.getPlayer().motionY;
	}
	
	public double getMotionZ(){
		return wrapper.getPlayer().motionZ;
	}
	
	public void setLandMovementFactor(float newFactor){
		try{
			Class elb = Class.forName(entityLivingBaseLoc);
			Field landMovement = elb.getDeclaredField("landMovementFactor");
			landMovement.setAccessible(true);
			landMovement.set(wrapper.getPlayer(), newFactor);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setJumpMovementFactor(float newFactor){
		try{
			Class elb = Class.forName(entityLivingBaseLoc);
			Field landMovement = elb.getDeclaredField("jumpMovementFactor");
			landMovement.setAccessible(true);
			landMovement.set(wrapper.getPlayer(), newFactor);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public float getGammaSetting(){
		return wrapper.getGameSettings().gammaSetting;
	}
	
	public void setGammaSetting(float newSetting){
		wrapper.getGameSettings().gammaSetting = newSetting;
	}
	
	public int getJumpCode(){
		return wrapper.getGameSettings().keyBindJump.getKeyCode();
	}
	
	public int getForwardCode(){
		return wrapper.getGameSettings().keyBindForward.getKeyCode();
	}
	
	public void setJumpKeyPressed(boolean pressed){
		wrapper.getGameSettings().keyBindJump.pressed = pressed;
	}
	
	public void setForwardKeyPressed(boolean pressed){
		wrapper.getGameSettings().keyBindForward.pressed = pressed;
	}
	
	public void setUseItemKeyPressed(boolean pressed){
		wrapper.getGameSettings().keyBindUseItem.pressed = pressed;
	}
	
	public int getSneakCode(){
		return wrapper.getGameSettings().keyBindSneak.getKeyCode();
	}
	
	public synchronized void displayScreen(GuiScreen screen){
		wrapper.getMinecraft().displayGuiScreen(screen);
	}
	
	public List getEntityList(){
		return wrapper.getWorld().loadedEntityList;
	}
	
	public float getDistanceToEntity(Entity from, Entity to){
		return from.getDistanceToEntity(to);
	}
	
	public boolean isEntityDead(Entity e){
		return e.isDead;
	}
	
	public boolean canEntityBeSeen(Entity e){
		return wrapper.getPlayer().canEntityBeSeen(e);
	}
	
	public void attackEntity(Entity e){
		wrapper.getPlayerController().attackEntity(wrapper.getPlayer(), e);
	}
	
	public void swingItem(){
		wrapper.getPlayer().swingItem();
	}
	
	public float getEyeHeight(){
		return wrapper.getPlayer().getEyeHeight();
	}
	
	public float getEyeHeight(Entity e){
		return e.getEyeHeight();
	}
	
	public double getPosX(){
		return wrapper.getPlayer().posX;
	}
	
	public double getPosY(){
		return wrapper.getPlayer().posY;
	}
	
	public double getPosZ(){
		return wrapper.getPlayer().posZ;
	}
	
	public double getPosX(Entity e){
		return e.posX;
	}
	
	public double getPosY(Entity e){
		return e.posY;
	}
	
	public double getPosZ(Entity e){
		return e.posZ;
	}
	
	public void setInvSlot(int slot){
		wrapper.getPlayer().inventory.currentItem = slot;
	}
	
	public int getCurInvSlot(){
		return wrapper.getPlayer().inventory.currentItem;
	}
	
	public ItemStack getCurrentItem(){
		return wrapper.getPlayer().getCurrentEquippedItem();
	}
	
	public ItemStack getItemAtSlot(int slot){
		return wrapper.getPlayer().inventoryContainer.getSlot(slot).getStack();
	}
	
	public ItemStack getItemAtSlotHotbar(int slot){
		return wrapper.getPlayer().inventory.getStackInSlot(slot);
	}
	
	public int getIdFromItem(Item item){
		return Item.getIdFromItem(item);
	}
	
	public void clickWindow(int slot, int mode, int button, EntityPlayer player){
		wrapper.getPlayerController().windowClick(player.inventoryContainer.windowId, slot, button, mode, player);
	}
	
	public void clickWindow(int id, int slot, int mode, int button, EntityPlayer player){
		wrapper.getPlayerController().windowClick(id, slot, button, mode, player);
	}
	
	public void sendUseItem(ItemStack itemStack, EntityPlayer player){
		wrapper.getPlayerController().sendUseItem(player, wrapper.getWorld(), itemStack);
	}
	
	public Item getItemById(int id){
		return Item.getItemById(id);
	}
	
	public void dropItemStack(int slot){
		for(int i=0; i<wrapper.getPlayer().inventory.getStackInSlot(slot).stackSize; i++){
			wrapper.getPlayer().dropOneItem(false);
		}
	}
	
	public int getPacketVelocityEntityId(S12PacketEntityVelocity p){
		return p.func_149412_c();
	}
	
	public Entity getEntityById(int id){
		return wrapper.getWorld().getEntityByID(id);
	}
	
	public int getXMovePacketVel(S12PacketEntityVelocity p){
		return p.func_149411_d();
	}
	
	public int getYMovePacketVel(S12PacketEntityVelocity p){
		return p.func_149410_e();
	}
	
	public int getZMovePacketVel(S12PacketEntityVelocity p){
		return p.func_149409_f();
	}
	
	public void rightClick(){
		wrapper.getMinecraft().func_147121_ag();
	}
	
	public void leftClick(){
		wrapper.getMinecraft().func_147116_af();
	}
	
	public void setKeyBindAttackPressed(boolean flag){
		wrapper.getGameSettings().keyBindAttack.pressed = flag;
	}
	
	public MovingObjectPosition getObjectMouseOver(){
		return wrapper.getMinecraft().objectMouseOver;
	}
	
	public Block getBlock(int x, int y, int z){
		return wrapper.getWorld().getBlock(x, y, z);
	}
	
	public float getStrVsBlock(ItemStack item, Block block){
		return item.func_150997_a(block);
	}
	
	public void useItemRightClick(ItemStack item){
		item.useItemRightClick(wrapper.getWorld(), wrapper.getPlayer());
	}
	
	public ItemStack[] getArmourInventory(){
		return wrapper.getPlayer().inventory.armorInventory;
	}
	
	public void enableStandardItemLighting(){
		RenderHelper.enableStandardItemLighting();
	}
	
	public void disableStandardItemLighting(){
		RenderHelper.disableStandardItemLighting();
	}
	
	public void renderItemIntoGUI(ItemStack is, int x, int y){
		renderItem.renderItemIntoGUI(wrapper.getFontRenderer(), wrapper.getRenderEngine(), is, x, y);
	}
	
	public void renderItemOverlayIntoGUI(ItemStack is, int x, int y){
		renderItem.renderItemOverlayIntoGUI(wrapper.getFontRenderer(), wrapper.getRenderEngine(), is, x, y);
	}
	
	public String stripControlCodes(String s){
		return StringUtils.stripControlCodes(s);
	}
	
	public String getPlayerName(EntityPlayer player){
		return player.getCommandSenderName();
	}
	
	public String getSessionUsername(){
		return wrapper.getSession().getUsername();
	}
	
	public double getBoundboxMinY(Entity e){
		return e.boundingBox.minY;
	}
	
	public double getBoundboxMaxY(Entity e){
		return e.boundingBox.maxY;
	}
	
	public double getBoundboxMinX(Entity e){
		return e.boundingBox.minX;
	}
	
	public double getBoundboxMaxX(Entity e){
		return e.boundingBox.maxX;
	}
	
	public double getBoundboxMinZ(Entity e){
		return e.boundingBox.minZ;
	}
	
	public double getBoundboxMaxZ(Entity e){
		return e.boundingBox.maxZ;
	}
	
	public int getDisplayHeight(){
		return wrapper.getMinecraft().displayHeight;
	}
	
	public int getDisplayWidth(){
		return wrapper.getMinecraft().displayWidth;
	}
	
	public GuiScreen getCurrentScreen(){
		return wrapper.getMinecraft().currentScreen;
	}
	
	public void setupOverlayRendering(){
		wrapper.getEntityRenderer().setupOverlayRendering();
	}
	
	public int getScaledWidth(ScaledResolution sr){
		return sr.getScaledWidth();
	}
	
	public int getScaledHeight(ScaledResolution sr){
		return sr.getScaledHeight();
	}
	
	public void setEntityLight(boolean state){
		if(state){wrapper.getEntityRenderer().enableLightmap(1);}else{wrapper.getEntityRenderer().disableLightmap(1);}
	}
	
	public ServerData getCurrentServerData(){
		return wrapper.getMinecraft().currentServerData;
	}
	
	public boolean isInCreativeMode(){
		return wrapper.getPlayerController().isInCreativeMode();
	}
	
	public void setStackDisplayName(ItemStack is, String s){
		is.setStackDisplayName(s);
	}
	
	public String getItemDisplayName(ItemStack is){
		return is.getDisplayName();
	}
	
	public void sendPacket(Packet p){
		wrapper.getPlayer().sendQueue.addToSendQueue(p);
	}
	
	public Enchantment[] getEnchantList(){
		return Enchantment.enchantmentsList;
	}
	
	public void addEnchantment(ItemStack is, Enchantment e, int level){
		is.addEnchantment(e, 127);
	}
	
	public double getLastTickPosX(Entity e){
		return e.lastTickPosX;
	}
	
	public double getLastTickPosY(Entity e){
		return e.lastTickPosY;
	}
	
	public double getLastTickPosZ(Entity e){
		return e.lastTickPosZ;
	}
	
	public float getEntityHeight(Entity e){
		return e.height;
	}
	
	public double getRenderPosX(){
		return RenderManager.renderPosX;
	}
	
	public double getRenderPosY(){
		return RenderManager.renderPosY;
	}
	
	public double getRenderPosZ(){
		return RenderManager.renderPosZ;
	}
	
	public float getPlayerViewX(){
		return RenderManager.instance.playerViewX;
	}
	
	public float getPlayerViewY(){
		return RenderManager.instance.playerViewY;
	}
	
	public void loadRenderers(){
		if(wrapper.getMinecraft().renderGlobal != null){
			wrapper.getMinecraft().renderGlobal.loadRenderers();
		}
	}
	
	public void setSmoothLighting(int mode){
		wrapper.getGameSettings().ambientOcclusion = mode;
	}
	
	public int getSmoothLighting(){
		return wrapper.getGameSettings() == null ? 2 : wrapper.getGameSettings().ambientOcclusion;
	}

	public int getIdFromBlock(Block block) {
		return Block.getIdFromBlock(block);
	}
	
	public List getTileEntityList(){
		return wrapper.getWorld().field_147482_g;
	}
	
	public int getChestX(TileEntityChest chest){
		return chest.field_145851_c;
	}
	
	public int getChestY(TileEntityChest chest){
		return chest.field_145848_d;
	}
	
	public int getChestZ(TileEntityChest chest){
		return chest.field_145849_e;
	}
	
	public boolean isBurning(){
		return wrapper.getPlayer().isBurning();
	}
	
	public void setRightDelayTimer(int delay){
		wrapper.getMinecraft().rightClickDelayTimer = delay;
	}
	
	public int getItemInUseDuration(){
		return wrapper.getPlayer().getItemInUseDuration();
	}

	public boolean isOnGround() {
		return wrapper.getPlayer().onGround;
	}

	public boolean isInWater() {
		return wrapper.getPlayer().isInWater();
	}

	public void setFallDistance(float f) {
		wrapper.getPlayer().fallDistance = f;
	}

	public void setOnGround(boolean b) {
		wrapper.getPlayer().onGround = b;
	}
	
	public int getFoodLevel(){
		return wrapper.getPlayer().getFoodStats().getFoodLevel();
	}
	
	public float getHealth(){
		return wrapper.getPlayer().getHealth();
	}
	
	public void removeEntityFromWorld(int id){
		wrapper.getWorld().removeEntityFromWorld(id);
	}
	
	public void addEntityToWorld(Entity e, int id){
		wrapper.getWorld().addEntityToWorld(id, e);
	}
	
	public void setTimerSpeed(float speed){
		wrapper.getMinecraft().timer.timerSpeed = speed;
	}
	
	public void jump(){
		wrapper.getPlayer().jump();
	}
	
	public GameProfile getGameProfile(EntityPlayer ep){
		return ep.field_146106_i;
	}
	
	public void setGameProfile(GameProfile profile, EntityPlayer ep){
		ep.field_146106_i = profile; 
	}
	
	public void setPositionAndRotation(Entity e, double x, double y, double z, float yaw, float pitch){
		e.setPositionAndRotation(x, y, z, yaw, pitch);
	}
	
	public void copyInventory(EntityPlayer from, EntityPlayer to){
		from.inventory.copyInventory(to.inventory);
	}
	
	public void setNoClip(boolean state){
		wrapper.getPlayer().noClip = state;
	}

	public void setSneakKeyPressed(boolean pressed) {
		wrapper.getGameSettings().keyBindSneak.pressed = pressed;
	}

	public boolean isSneaking(){
		return wrapper.getPlayer().isSneaking();
	}

	public void setStepHeight(float value){
		wrapper.getPlayer().stepHeight = value;
	}
	
	public int getWidth(){
		return getDisplayWidth()/2;
	}
	
	public int getHeight(){
		return getDisplayHeight()/2;
	}
	
	public int getId(GuiButton btn){
		return btn.id;
	}
	
	public void addButton(GuiScreen screen, GuiButton btn){
		screen.buttonList.add(btn);
	}
	
	public void clearButtons(GuiScreen screen){
		screen.buttonList.clear();
	}
	
	public Wrapper getWrapper(){
		return wrapper;
	}

	public MovingObjectPosition rayTraceBlocks(Vec3 vecFromPool, Vec3 vecFromPool2) {
		return wrapper.getWorld().rayTraceBlocks(vecFromPool, vecFromPool2);
	}
	
	public Block getTileEntityBlock(EntityFallingBlock b){
		return b.func_145805_f();
	}
	
	public float getFallDistance(Entity e){
		return e.fallDistance;
	}
	
	public boolean isInvisible(Entity e){
		return e.isInvisible();
	}
	
	public HookGuiIngame getIngameGUI(){
		return wrapper.getMinecraft().ingameGUI;
	}
	
}
