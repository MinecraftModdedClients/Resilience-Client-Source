package net.minecraft.client.settings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;

public class KeyBinding implements Comparable
{
    private static final List keybindArray = new ArrayList();
    private static final IntHashMap hash = new IntHashMap();
    private static final Set keybindSet = new HashSet();
    private final String keyDescription;
    private final int keyCodeDefault;
    private final String keyCategory;
    private int keyCode;

    /** because _303 wanted me to call it that(Caironater) */
    public boolean pressed;
    private int presses;
    private static final String __OBFID = "CL_00000628";

    public static void onTick(int par0)
    {
        if (par0 != 0)
        {
            KeyBinding var1 = (KeyBinding)hash.lookup(par0);

            if (var1 != null)
            {
                ++var1.presses;
            }
        }
    }

    public static void setKeyBindState(int par0, boolean par1)
    {
        if (par0 != 0)
        {
            KeyBinding var2 = (KeyBinding)hash.lookup(par0);

            if (var2 != null)
            {
                var2.pressed = par1;
            }
        }
    }

    public static void unPressAllKeys()
    {
        Iterator var0 = keybindArray.iterator();

        while (var0.hasNext())
        {
            KeyBinding var1 = (KeyBinding)var0.next();
            var1.unpressKey();
        }
    }

    public static void resetKeyBindingArrayAndHash()
    {
        hash.clearMap();
        Iterator var0 = keybindArray.iterator();

        while (var0.hasNext())
        {
            KeyBinding var1 = (KeyBinding)var0.next();
            hash.addKey(var1.keyCode, var1);
        }
    }

    public static Set func_151467_c()
    {
        return keybindSet;
    }

    public KeyBinding(String p_i45001_1_, int p_i45001_2_, String p_i45001_3_)
    {
        this.keyDescription = p_i45001_1_;
        this.keyCode = p_i45001_2_;
        this.keyCodeDefault = p_i45001_2_;
        this.keyCategory = p_i45001_3_;
        keybindArray.add(this);
        hash.addKey(p_i45001_2_, this);
        keybindSet.add(p_i45001_3_);
    }

    public boolean getIsKeyPressed()
    {
        return this.pressed;
    }

    public String getKeyCategory()
    {
        return this.keyCategory;
    }

    public boolean isPressed()
    {
        if (this.presses == 0)
        {
            return false;
        }
        else
        {
            --this.presses;
            return true;
        }
    }

    private void unpressKey()
    {
        this.presses = 0;
        this.pressed = false;
    }

    public String getKeyDescription()
    {
        return this.keyDescription;
    }

    public int getKeyCodeDefault()
    {
        return this.keyCodeDefault;
    }

    public int getKeyCode()
    {
        return this.keyCode;
    }

    public void setKeyCode(int p_151462_1_)
    {
        this.keyCode = p_151462_1_;
    }

    public int compareTo(KeyBinding p_151465_1_)
    {
        int var2 = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_151465_1_.keyCategory, new Object[0]));

        if (var2 == 0)
        {
            var2 = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_151465_1_.keyDescription, new Object[0]));
        }

        return var2;
    }

    public int compareTo(Object par1Obj)
    {
        return this.compareTo((KeyBinding)par1Obj);
    }
}
