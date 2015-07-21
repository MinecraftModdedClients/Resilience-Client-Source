package com.krispdev.resilience.utilities.game;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;

import com.krispdev.resilience.Resilience;
import com.krispdev.resilience.event.events.player.EventOnClick;
import com.krispdev.resilience.relations.EnemyManager;
import com.krispdev.resilience.relations.FriendManager;
import com.krispdev.resilience.utilities.Utils;
import com.krispdev.resilience.wrappers.MethodInvoker;

public class EntityUtils {
	
	private MethodInvoker invoker = Resilience.getInstance().getInvoker();
	
	public Entity getClosestEntity(Entity from, boolean players, boolean mobs, boolean animals, boolean invisibles, boolean propBlocks){
		Entity prevEntity = null;
		for(Object o : invoker.getEntityList()){
			if(o instanceof EntityLivingBase){
				EntityLivingBase entity = (EntityLivingBase) o;
				
				if(entity == null || isThePlayer(entity)) continue;
				
				if(prevEntity == null){
					if(isEntityFriend(entity)) continue;
					prevEntity = entity;
				}else{
					
					if(isEntityFriend(entity)) continue;
					
					if(entity instanceof EntityOtherPlayerMP && players){
						if(!invisibles && invoker.isInvisible(entity)) continue;
						if(isCloser(entity, prevEntity, 2)){
							prevEntity = entity;
						}
					}	
					
					if(entity instanceof EntityMob && !(entity instanceof EntityHorse || entity instanceof EntityAnimal) && mobs){
						if(isCloser(entity, prevEntity, 1)){
							prevEntity = entity;
						}
					}

					if((entity instanceof EntityAnimal || entity instanceof EntityHorse) && animals){
						if(isCloser(entity, prevEntity, 0)){
							prevEntity = entity;
						}
					}
				}
			}
			if(o instanceof EntityFallingBlock && propBlocks){
				System.out.println(prevEntity + " because "+ propBlocks);
				EntityFallingBlock entity = (EntityFallingBlock) o;
				if(entity != null){
					if(isCloser(entity, prevEntity, 0)){
						prevEntity = entity;
					}
				}
			}
		}
		if(prevEntity != null){
			return prevEntity;
		}
		return null;
	}

	public boolean isCloser(Entity now, Entity first, float error){
		if(first.getClass().isAssignableFrom(now.getClass())){
			return invoker.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), now) < invoker.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), first);
		}
		return invoker.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), now) < invoker.getDistanceToEntity(Resilience.getInstance().getWrapper().getPlayer(), first)+error;
	}
	
	public boolean canHit(Entity e){
		return e != null && !invoker.isEntityDead(e) && invoker.canEntityBeSeen(e) && isWithinRange(6, e);
	}
	
	public boolean canHit(Entity e, float range){
		return e != null && !invoker.isEntityDead(e) && invoker.canEntityBeSeen(e) && isWithinRange(range, e);
	}
	
	public boolean isWithinRange(float range, Entity e){
		return invoker.getDistanceToEntity(e, Resilience.getInstance().getWrapper().getPlayer()) <= range;
	}
	
	public void hitEntity(Entity e){
		invoker.attackEntity(e);
		invoker.swingItem();
	}
	
	public void hitEntity(Entity e, boolean block, boolean auto){
		invoker.attackEntity(e);
    	final EventOnClick eventClick = new EventOnClick(0, auto);
    	eventClick.onEvent();
		invoker.swingItem();
		if(block && invoker.getCurrentItem().getItem() instanceof ItemSword){
			invoker.useItemRightClick(invoker.getCurrentItem());
		}
	}
	
	public boolean isThePlayer(Entity e){
		if(e == null || Resilience.getInstance().getWrapper().getPlayer() == null) return false;
		if(e != Resilience.getInstance().getWrapper().getPlayer()){
			return false;
		}
		return true;
	}
	
	public boolean isEntityFriend(Entity e){
		if(e instanceof EntityOtherPlayerMP){
			EntityOtherPlayerMP player = (EntityOtherPlayerMP) e;
			
			return FriendManager.isFriend(invoker.getPlayerName(player));
		}
		return false;
	}
	
	public boolean isEntityEnemy(Entity e){
		if(e instanceof EntityOtherPlayerMP){
			EntityOtherPlayerMP player = (EntityOtherPlayerMP) e;
			
			return EnemyManager.isEnemy(invoker.getPlayerName(player));
		}
		return false;
	}
	
    public void faceEntity(Entity e)
    {
        double var4 = invoker.getPosX(e) - invoker.getPosX();
        double var8 = invoker.getPosZ(e) - invoker.getPosZ();
        double var6;

        if (e instanceof EntityLivingBase)
        {
            EntityLivingBase entity = (EntityLivingBase)e;
            var6 = invoker.getPosY(entity) + (double)invoker.getEyeHeight(entity) - (invoker.getPosY() + (double)invoker.getEyeHeight());
        }
        else
        {
            var6 = (invoker.getBoundboxMinY(e) + invoker.getBoundboxMaxY(e)) / 2.0D - (invoker.getPosY() + (invoker.getEyeHeight()));
        }

        double var14 = (double)Utils.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float)(-(Math.atan2(var6, var14) * 180.0D / Math.PI));
        invoker.setRotationPitch(var13);
        invoker.setRotationYaw(var12);
    }
    
    public boolean withinRotation(Entity e, float rotation){
        double var4 = invoker.getPosX(e) - invoker.getPosX();
        double var8 = invoker.getPosZ(e) - invoker.getPosZ();
        double var6;

        if (e instanceof EntityLivingBase)
        {
            EntityLivingBase entity = (EntityLivingBase)e;
            var6 = invoker.getPosY(entity) + (double)invoker.getEyeHeight(entity) - (invoker.getPosY() + (double)invoker.getEyeHeight());
        }
        else
        {
            var6 = (invoker.getBoundboxMinY(e) + invoker.getBoundboxMaxY(e)) / 2.0D - (invoker.getPosY() + (invoker.getEyeHeight()));
        }
        
        double var14 = (double)Utils.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float)(-(Math.atan2(var6, var14) * 180.0D / Math.PI));
        
        float totalRotation = MathHelper.wrapAngleTo180_float(invoker.getRotationYaw() - var12);
        
        return Math.abs(totalRotation) <= rotation;
    }
    
	public boolean isEntityDead(Entity entity) {
		return entity.isDead;
	}
}
