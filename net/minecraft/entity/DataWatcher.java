package net.minecraft.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ReportedException;
import org.apache.commons.lang3.ObjectUtils;

public class DataWatcher
{
    private final Entity field_151511_a;

    /** When isBlank is true the DataWatcher is not watching any objects */
    private boolean isBlank = true;
    private static final HashMap dataTypes = new HashMap();
    private final Map watchedObjects = new HashMap();

    /** true if one or more object was changed */
    private boolean objectChanged;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final String __OBFID = "CL_00001559";

    public DataWatcher(Entity p_i45313_1_)
    {
        this.field_151511_a = p_i45313_1_;
    }

    /**
     * adds a new object to dataWatcher to watch, to update an already existing object see updateObject. Arguments: data
     * Value Id, Object to add
     */
    public void addObject(int par1, Object par2Obj)
    {
        Integer var3 = (Integer)dataTypes.get(par2Obj.getClass());

        if (var3 == null)
        {
            throw new IllegalArgumentException("Unknown data type: " + par2Obj.getClass());
        }
        else if (par1 > 31)
        {
            throw new IllegalArgumentException("Data value id is too big with " + par1 + "! (Max is " + 31 + ")");
        }
        else if (this.watchedObjects.containsKey(Integer.valueOf(par1)))
        {
            throw new IllegalArgumentException("Duplicate id value for " + par1 + "!");
        }
        else
        {
            DataWatcher.WatchableObject var4 = new DataWatcher.WatchableObject(var3.intValue(), par1, par2Obj);
            this.lock.writeLock().lock();
            this.watchedObjects.put(Integer.valueOf(par1), var4);
            this.lock.writeLock().unlock();
            this.isBlank = false;
        }
    }

    /**
     * Add a new object for the DataWatcher to watch, using the specified data type.
     */
    public void addObjectByDataType(int par1, int par2)
    {
        DataWatcher.WatchableObject var3 = new DataWatcher.WatchableObject(par2, par1, (Object)null);
        this.lock.writeLock().lock();
        this.watchedObjects.put(Integer.valueOf(par1), var3);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }

    /**
     * gets the bytevalue of a watchable object
     */
    public byte getWatchableObjectByte(int par1)
    {
        return ((Byte)this.getWatchedObject(par1).getObject()).byteValue();
    }

    public short getWatchableObjectShort(int par1)
    {
        return ((Short)this.getWatchedObject(par1).getObject()).shortValue();
    }

    /**
     * gets a watchable object and returns it as a Integer
     */
    public int getWatchableObjectInt(int par1)
    {
        return ((Integer)this.getWatchedObject(par1).getObject()).intValue();
    }

    public float getWatchableObjectFloat(int par1)
    {
        return ((Float)this.getWatchedObject(par1).getObject()).floatValue();
    }

    /**
     * gets a watchable object and returns it as a String
     */
    public String getWatchableObjectString(int par1)
    {
        return (String)this.getWatchedObject(par1).getObject();
    }

    /**
     * Get a watchable object as an ItemStack.
     */
    public ItemStack getWatchableObjectItemStack(int par1)
    {
        return (ItemStack)this.getWatchedObject(par1).getObject();
    }

    /**
     * is threadsafe, unless it throws an exception, then
     */
    private DataWatcher.WatchableObject getWatchedObject(int par1)
    {
        this.lock.readLock().lock();
        DataWatcher.WatchableObject var2;

        try
        {
            var2 = (DataWatcher.WatchableObject)this.watchedObjects.get(Integer.valueOf(par1));
        }
        catch (Throwable var6)
        {
            CrashReport var4 = CrashReport.makeCrashReport(var6, "Getting synched entity data");
            CrashReportCategory var5 = var4.makeCategory("Synched entity data");
            var5.addCrashSection("Data ID", Integer.valueOf(par1));
            throw new ReportedException(var4);
        }

        this.lock.readLock().unlock();
        return var2;
    }

    /**
     * updates an already existing object
     */
    public void updateObject(int par1, Object par2Obj)
    {
        DataWatcher.WatchableObject var3 = this.getWatchedObject(par1);

        if (ObjectUtils.notEqual(par2Obj, var3.getObject()))
        {
            var3.setObject(par2Obj);
            this.field_151511_a.func_145781_i(par1);
            var3.setWatched(true);
            this.objectChanged = true;
        }
    }

    public void setObjectWatched(int par1)
    {
        this.getWatchedObject(par1).watched = true;
        this.objectChanged = true;
    }

    public boolean hasChanges()
    {
        return this.objectChanged;
    }

    /**
     * Writes the list of watched objects (entity attribute of type {byte, short, int, float, string, ItemStack,
     * ChunkCoordinates}) to the specified PacketBuffer
     */
    public static void writeWatchedListToPacketBuffer(List p_151507_0_, PacketBuffer p_151507_1_) throws IOException
    {
        if (p_151507_0_ != null)
        {
            Iterator var2 = p_151507_0_.iterator();

            while (var2.hasNext())
            {
                DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject)var2.next();
                writeWatchableObjectToPacketBuffer(p_151507_1_, var3);
            }
        }

        p_151507_1_.writeByte(127);
    }

    public List getChanged()
    {
        ArrayList var1 = null;

        if (this.objectChanged)
        {
            this.lock.readLock().lock();
            Iterator var2 = this.watchedObjects.values().iterator();

            while (var2.hasNext())
            {
                DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject)var2.next();

                if (var3.isWatched())
                {
                    var3.setWatched(false);

                    if (var1 == null)
                    {
                        var1 = new ArrayList();
                    }

                    var1.add(var3);
                }
            }

            this.lock.readLock().unlock();
        }

        this.objectChanged = false;
        return var1;
    }

    public void func_151509_a(PacketBuffer p_151509_1_) throws IOException
    {
        this.lock.readLock().lock();
        Iterator var2 = this.watchedObjects.values().iterator();

        while (var2.hasNext())
        {
            DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject)var2.next();
            writeWatchableObjectToPacketBuffer(p_151509_1_, var3);
        }

        this.lock.readLock().unlock();
        p_151509_1_.writeByte(127);
    }

    public List getAllWatched()
    {
        ArrayList var1 = null;
        this.lock.readLock().lock();
        DataWatcher.WatchableObject var3;

        for (Iterator var2 = this.watchedObjects.values().iterator(); var2.hasNext(); var1.add(var3))
        {
            var3 = (DataWatcher.WatchableObject)var2.next();

            if (var1 == null)
            {
                var1 = new ArrayList();
            }
        }

        this.lock.readLock().unlock();
        return var1;
    }

    /**
     * Writes a watchable object (entity attribute of type {byte, short, int, float, string, ItemStack,
     * ChunkCoordinates}) to the specified PacketBuffer
     */
    private static void writeWatchableObjectToPacketBuffer(PacketBuffer p_151510_0_, DataWatcher.WatchableObject p_151510_1_) throws IOException
    {
        int var2 = (p_151510_1_.getObjectType() << 5 | p_151510_1_.getDataValueId() & 31) & 255;
        p_151510_0_.writeByte(var2);

        switch (p_151510_1_.getObjectType())
        {
            case 0:
                p_151510_0_.writeByte(((Byte)p_151510_1_.getObject()).byteValue());
                break;

            case 1:
                p_151510_0_.writeShort(((Short)p_151510_1_.getObject()).shortValue());
                break;

            case 2:
                p_151510_0_.writeInt(((Integer)p_151510_1_.getObject()).intValue());
                break;

            case 3:
                p_151510_0_.writeFloat(((Float)p_151510_1_.getObject()).floatValue());
                break;

            case 4:
                p_151510_0_.writeStringToBuffer((String)p_151510_1_.getObject());
                break;

            case 5:
                ItemStack var4 = (ItemStack)p_151510_1_.getObject();
                p_151510_0_.writeItemStackToBuffer(var4);
                break;

            case 6:
                ChunkCoordinates var3 = (ChunkCoordinates)p_151510_1_.getObject();
                p_151510_0_.writeInt(var3.posX);
                p_151510_0_.writeInt(var3.posY);
                p_151510_0_.writeInt(var3.posZ);
        }
    }

    /**
     * Reads a list of watched objects (entity attribute of type {byte, short, int, float, string, ItemStack,
     * ChunkCoordinates}) from the supplied PacketBuffer
     */
    public static List readWatchedListFromPacketBuffer(PacketBuffer p_151508_0_) throws IOException
    {
        ArrayList var1 = null;

        for (byte var2 = p_151508_0_.readByte(); var2 != 127; var2 = p_151508_0_.readByte())
        {
            if (var1 == null)
            {
                var1 = new ArrayList();
            }

            int var3 = (var2 & 224) >> 5;
            int var4 = var2 & 31;
            DataWatcher.WatchableObject var5 = null;

            switch (var3)
            {
                case 0:
                    var5 = new DataWatcher.WatchableObject(var3, var4, Byte.valueOf(p_151508_0_.readByte()));
                    break;

                case 1:
                    var5 = new DataWatcher.WatchableObject(var3, var4, Short.valueOf(p_151508_0_.readShort()));
                    break;

                case 2:
                    var5 = new DataWatcher.WatchableObject(var3, var4, Integer.valueOf(p_151508_0_.readInt()));
                    break;

                case 3:
                    var5 = new DataWatcher.WatchableObject(var3, var4, Float.valueOf(p_151508_0_.readFloat()));
                    break;

                case 4:
                    var5 = new DataWatcher.WatchableObject(var3, var4, p_151508_0_.readStringFromBuffer(32767));
                    break;

                case 5:
                    var5 = new DataWatcher.WatchableObject(var3, var4, p_151508_0_.readItemStackFromBuffer());
                    break;

                case 6:
                    int var6 = p_151508_0_.readInt();
                    int var7 = p_151508_0_.readInt();
                    int var8 = p_151508_0_.readInt();
                    var5 = new DataWatcher.WatchableObject(var3, var4, new ChunkCoordinates(var6, var7, var8));
            }

            var1.add(var5);
        }

        return var1;
    }

    public void updateWatchedObjectsFromList(List par1List)
    {
        this.lock.writeLock().lock();
        Iterator var2 = par1List.iterator();

        while (var2.hasNext())
        {
        	try{
            	DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject)var2.next();
                DataWatcher.WatchableObject var4 = (DataWatcher.WatchableObject)this.watchedObjects.get(Integer.valueOf(var3.getDataValueId()));

                if (var4 != null)
                {
                    var4.setObject(var3.getObject());
                    this.field_151511_a.func_145781_i(var3.getDataValueId());
                }
        	}catch(Exception e){}
        }

        this.lock.writeLock().unlock();
        this.objectChanged = true;
    }

    public boolean getIsBlank()
    {
        return this.isBlank;
    }

    public void func_111144_e()
    {
        this.objectChanged = false;
    }

    static
    {
        dataTypes.put(Byte.class, Integer.valueOf(0));
        dataTypes.put(Short.class, Integer.valueOf(1));
        dataTypes.put(Integer.class, Integer.valueOf(2));
        dataTypes.put(Float.class, Integer.valueOf(3));
        dataTypes.put(String.class, Integer.valueOf(4));
        dataTypes.put(ItemStack.class, Integer.valueOf(5));
        dataTypes.put(ChunkCoordinates.class, Integer.valueOf(6));
    }

    public static class WatchableObject
    {
        private final int objectType;
        private final int dataValueId;
        private Object watchedObject;
        private boolean watched;
        private static final String __OBFID = "CL_00001560";

        public WatchableObject(int par1, int par2, Object par3Obj)
        {
            this.dataValueId = par2;
            this.watchedObject = par3Obj;
            this.objectType = par1;
            this.watched = true;
        }

        public int getDataValueId()
        {
            return this.dataValueId;
        }

        public void setObject(Object par1Obj)
        {
            this.watchedObject = par1Obj;
        }

        public Object getObject()
        {
            return this.watchedObject;
        }

        public int getObjectType()
        {
            return this.objectType;
        }

        public boolean isWatched()
        {
            return this.watched;
        }

        public void setWatched(boolean par1)
        {
            this.watched = par1;
        }
    }
}
