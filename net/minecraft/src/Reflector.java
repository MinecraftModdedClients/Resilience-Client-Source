package net.minecraft.src;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class Reflector
{
    public static ReflectorClass ModLoader = new ReflectorClass("ModLoader");
    public static ReflectorMethod ModLoader_renderWorldBlock = new ReflectorMethod(ModLoader, "renderWorldBlock");
    public static ReflectorMethod ModLoader_renderInvBlock = new ReflectorMethod(ModLoader, "renderInvBlock");
    public static ReflectorMethod ModLoader_renderBlockIsItemFull3D = new ReflectorMethod(ModLoader, "renderBlockIsItemFull3D");
    public static ReflectorMethod ModLoader_registerServer = new ReflectorMethod(ModLoader, "registerServer");
    public static ReflectorMethod ModLoader_getCustomAnimationLogic = new ReflectorMethod(ModLoader, "getCustomAnimationLogic");
    public static ReflectorClass FMLRenderAccessLibrary = new ReflectorClass("net.minecraft.src.FMLRenderAccessLibrary");
    public static ReflectorMethod FMLRenderAccessLibrary_renderWorldBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderWorldBlock");
    public static ReflectorMethod FMLRenderAccessLibrary_renderInventoryBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderInventoryBlock");
    public static ReflectorMethod FMLRenderAccessLibrary_renderItemAsFull3DBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderItemAsFull3DBlock");
    public static ReflectorClass LightCache = new ReflectorClass("LightCache");
    public static ReflectorField LightCache_cache = new ReflectorField(LightCache, "cache");
    public static ReflectorMethod LightCache_clear = new ReflectorMethod(LightCache, "clear");
    public static ReflectorClass BlockCoord = new ReflectorClass("BlockCoord");
    public static ReflectorMethod BlockCoord_resetPool = new ReflectorMethod(BlockCoord, "resetPool");
    public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
    public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
    public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
    public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
    public static ReflectorMethod ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
    public static ReflectorMethod ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
    public static ReflectorMethod ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
    public static ReflectorMethod ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
    public static ReflectorMethod ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
    public static ReflectorMethod ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
    public static ReflectorMethod ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
    public static ReflectorClass MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
    public static ReflectorMethod MinecraftForgeClient_getRenderPass = new ReflectorMethod(MinecraftForgeClient, "getRenderPass");
    public static ReflectorMethod MinecraftForgeClient_getItemRenderer = new ReflectorMethod(MinecraftForgeClient, "getItemRenderer");
    public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
    public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
    public static ReflectorMethod ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
    public static ReflectorMethod ForgeHooksClient_renderEquippedItem = new ReflectorMethod(ForgeHooksClient, "renderEquippedItem");
    public static ReflectorMethod ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
    public static ReflectorMethod ForgeHooksClient_onTextureLoadPre = new ReflectorMethod(ForgeHooksClient, "onTextureLoadPre");
    public static ReflectorMethod ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
    public static ReflectorClass FMLCommonHandler = new ReflectorClass("cpw.mods.fml.common.FMLCommonHandler");
    public static ReflectorMethod FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
    public static ReflectorMethod FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
    public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
    public static ReflectorClass FMLClientHandler = new ReflectorClass("cpw.mods.fml.client.FMLClientHandler");
    public static ReflectorMethod FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
    public static ReflectorMethod FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
    public static ReflectorClass ItemRenderType = new ReflectorClass("net.minecraftforge.client.IItemRenderer$ItemRenderType");
    public static ReflectorField ItemRenderType_EQUIPPED = new ReflectorField(ItemRenderType, "EQUIPPED");
    public static ReflectorClass ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
    public static ReflectorMethod ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
    public static ReflectorMethod ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
    public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(ForgeWorldProvider, "getWeatherRenderer");
    public static ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
    public static ReflectorMethod IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
    public static ReflectorClass DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
    public static ReflectorMethod DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
    public static ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
    public static ReflectorConstructor WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[] {World.class});
    public static ReflectorClass EventBus = new ReflectorClass("cpw.mods.fml.common.eventhandler.EventBus");
    public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post");
    public static ReflectorClass ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
    public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(ChunkWatchEvent_UnWatch, new Class[] {ChunkCoordIntPair.class, EntityPlayerMP.class});
    public static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
    public static ReflectorMethod ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
    public static ReflectorMethod ForgeBlock_isBedFoot = new ReflectorMethod(ForgeBlock, "isBedFoot");
    public static ReflectorMethod ForgeBlock_canRenderInPass = new ReflectorMethod(ForgeBlock, "canRenderInPass");
    public static ReflectorMethod ForgeBlock_hasTileEntity = new ReflectorMethod(ForgeBlock, "hasTileEntity", new Class[] {Integer.TYPE});
    public static ReflectorClass ForgeEntity = new ReflectorClass(Entity.class);
    public static ReflectorField ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
    public static ReflectorField ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
    public static ReflectorMethod ForgeEntity_shouldRenderInPass = new ReflectorMethod(ForgeEntity, "shouldRenderInPass");
    public static ReflectorMethod ForgeEntity_canRiderInteract = new ReflectorMethod(ForgeEntity, "canRiderInteract");
    public static ReflectorClass ForgeTileEntity = new ReflectorClass(TileEntity.class);
    public static ReflectorMethod ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(ForgeTileEntity, "shouldRenderInPass");
    public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(ForgeTileEntity, "getRenderBoundingBox");
    public static ReflectorClass ForgeItem = new ReflectorClass(Item.class);
    public static ReflectorMethod ForgeItem_onEntitySwing = new ReflectorMethod(ForgeItem, "onEntitySwing");
    public static ReflectorClass ForgePotionEffect = new ReflectorClass(PotionEffect.class);
    public static ReflectorMethod ForgePotionEffect_isCurativeItem = new ReflectorMethod(ForgePotionEffect, "isCurativeItem");
    public static ReflectorClass ForgeItemStack = new ReflectorClass(ItemStack.class);
    public static ReflectorMethod ForgeItemStack_hasEffect = new ReflectorMethod(ForgeItemStack, "hasEffect", new Class[] {Integer.TYPE});

    public static void callVoid(ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return;
            }

            e.invoke((Object)null, params);
        }
        catch (Throwable var3)
        {
            handleException(var3, (Object)null, refMethod, params);
        }
    }

    public static boolean callBoolean(ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return false;
            }
            else
            {
                Boolean retVal = (Boolean)e.invoke((Object)null, params);
                return retVal.booleanValue();
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, refMethod, params);
            return false;
        }
    }

    public static int callInt(ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return 0;
            }
            else
            {
                Integer retVal = (Integer)e.invoke((Object)null, params);
                return retVal.intValue();
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, refMethod, params);
            return 0;
        }
    }

    public static float callFloat(ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return 0.0F;
            }
            else
            {
                Float retVal = (Float)e.invoke((Object)null, params);
                return retVal.floatValue();
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, refMethod, params);
            return 0.0F;
        }
    }

    public static String callString(ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return null;
            }
            else
            {
                String retVal = (String)e.invoke((Object)null, params);
                return retVal;
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, refMethod, params);
            return null;
        }
    }

    public static Object call(ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return null;
            }
            else
            {
                Object retVal = e.invoke((Object)null, params);
                return retVal;
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, refMethod, params);
            return null;
        }
    }

    public static void callVoid(Object obj, ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            if (obj == null)
            {
                return;
            }

            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return;
            }

            e.invoke(obj, params);
        }
        catch (Throwable var4)
        {
            handleException(var4, obj, refMethod, params);
        }
    }

    public static boolean callBoolean(Object obj, ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return false;
            }
            else
            {
                Boolean retVal = (Boolean)e.invoke(obj, params);
                return retVal.booleanValue();
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, obj, refMethod, params);
            return false;
        }
    }

    public static int callInt(Object obj, ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return 0;
            }
            else
            {
                Integer retVal = (Integer)e.invoke(obj, params);
                return retVal.intValue();
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, obj, refMethod, params);
            return 0;
        }
    }

    public static float callFloat(Object obj, ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return 0.0F;
            }
            else
            {
                Float retVal = (Float)e.invoke(obj, params);
                return retVal.floatValue();
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, obj, refMethod, params);
            return 0.0F;
        }
    }

    public static String callString(Object obj, ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return null;
            }
            else
            {
                String retVal = (String)e.invoke(obj, params);
                return retVal;
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, obj, refMethod, params);
            return null;
        }
    }

    public static Object call(Object obj, ReflectorMethod refMethod, Object ... params)
    {
        try
        {
            Method e = refMethod.getTargetMethod();

            if (e == null)
            {
                return null;
            }
            else
            {
                Object retVal = e.invoke(obj, params);
                return retVal;
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, obj, refMethod, params);
            return null;
        }
    }

    public static Object getFieldValue(ReflectorField refField)
    {
        return getFieldValue((Object)null, refField);
    }

    public static Object getFieldValue(Object obj, ReflectorField refField)
    {
        try
        {
            Field e = refField.getTargetField();

            if (e == null)
            {
                return null;
            }
            else
            {
                Object value = e.get(obj);
                return value;
            }
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
            return null;
        }
    }

    public static void setFieldValue(ReflectorField refField, Object value)
    {
        setFieldValue((Object)null, refField, value);
    }

    public static void setFieldValue(Object obj, ReflectorField refField, Object value)
    {
        try
        {
            Field e = refField.getTargetField();

            if (e == null)
            {
                return;
            }

            e.set(obj, value);
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
        }
    }

    public static void postForgeBusEvent(ReflectorConstructor constr, Object ... params)
    {
        try
        {
            Object e = getFieldValue(MinecraftForge_EVENT_BUS);

            if (e == null)
            {
                return;
            }

            Constructor c = constr.getTargetConstructor();

            if (c == null)
            {
                return;
            }

            Object event = c.newInstance(params);
            callVoid(e, EventBus_post, new Object[] {event});
        }
        catch (Throwable var5)
        {
            var5.printStackTrace();
        }
    }

    public static boolean matchesTypes(Class[] pTypes, Class[] cTypes)
    {
        if (pTypes.length != cTypes.length)
        {
            return false;
        }
        else
        {
            for (int i = 0; i < cTypes.length; ++i)
            {
                Class pType = pTypes[i];
                Class cType = cTypes[i];

                if (pType != cType)
                {
                    return false;
                }
            }

            return true;
        }
    }

    private static void dbgCall(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params, Object retVal)
    {
        String className = refMethod.getTargetMethod().getDeclaringClass().getName();
        String methodName = refMethod.getTargetMethod().getName();
        String staticStr = "";

        if (isStatic)
        {
            staticStr = " static";
        }

        Config.dbg(callType + staticStr + " " + className + "." + methodName + "(" + Config.arrayToString(params) + ") => " + retVal);
    }

    private static void dbgCallVoid(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params)
    {
        String className = refMethod.getTargetMethod().getDeclaringClass().getName();
        String methodName = refMethod.getTargetMethod().getName();
        String staticStr = "";

        if (isStatic)
        {
            staticStr = " static";
        }

        Config.dbg(callType + staticStr + " " + className + "." + methodName + "(" + Config.arrayToString(params) + ")");
    }

    private static void dbgFieldValue(boolean isStatic, String accessType, ReflectorField refField, Object val)
    {
        String className = refField.getTargetField().getDeclaringClass().getName();
        String fieldName = refField.getTargetField().getName();
        String staticStr = "";

        if (isStatic)
        {
            staticStr = " static";
        }

        Config.dbg(accessType + staticStr + " " + className + "." + fieldName + " => " + val);
    }

    private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params)
    {
        if (e instanceof InvocationTargetException)
        {
            e.printStackTrace();
        }
        else
        {
            if (e instanceof IllegalArgumentException)
            {
                Config.warn("*** IllegalArgumentException ***");
                Config.warn("Method: " + refMethod.getTargetMethod());
                Config.warn("Object: " + obj);
                Config.warn("Parameter classes: " + Config.arrayToString(getClasses(params)));
                Config.warn("Parameters: " + Config.arrayToString(params));
            }

            Config.warn("*** Exception outside of method ***");
            Config.warn("Method deactivated: " + refMethod.getTargetMethod());
            refMethod.deactivate();
            e.printStackTrace();
        }
    }

    private static Object[] getClasses(Object[] objs)
    {
        if (objs == null)
        {
            return new Class[0];
        }
        else
        {
            Class[] classes = new Class[objs.length];

            for (int i = 0; i < classes.length; ++i)
            {
                Object obj = objs[i];

                if (obj != null)
                {
                    classes[i] = obj.getClass();
                }
            }

            return classes;
        }
    }

    public static Field getFieldByType(Class cls, Class fieldType)
    {
        try
        {
            Field[] e = cls.getDeclaredFields();

            for (int i = 0; i < e.length; ++i)
            {
                Field field = e[i];

                if (field.getType() == fieldType)
                {
                    field.setAccessible(true);
                    return field;
                }
            }

            return null;
        }
        catch (Exception var5)
        {
            return null;
        }
    }
}
