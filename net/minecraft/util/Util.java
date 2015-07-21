package net.minecraft.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class Util
{
    /**
     * Matches a UUID string, such as "b2a72a80-d078-4ea4-ae43-fcaf95707a76".  Will not match UUIDs containing upper-
     * case letters.
     */
    private static final Pattern uuidPattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
    private static final String __OBFID = "CL_00001633";

    public static Util.EnumOS getOSType()
    {
        String var0 = System.getProperty("os.name").toLowerCase();
        return var0.contains("win") ? Util.EnumOS.WINDOWS : (var0.contains("mac") ? Util.EnumOS.MACOS : (var0.contains("solaris") ? Util.EnumOS.SOLARIS : (var0.contains("sunos") ? Util.EnumOS.SOLARIS : (var0.contains("linux") ? Util.EnumOS.LINUX : (var0.contains("unix") ? Util.EnumOS.LINUX : Util.EnumOS.UNKNOWN)))));
    }

    /**
     * Determines whether or not the given parameter can be parsed as a UUID.
     */
    public static boolean isUUIDString(String p_147172_0_)
    {
        return uuidPattern.matcher(p_147172_0_).matches();
    }

    /**
     * Parses the given string as a UUID, or returns null if the string could not be parsed.
     */
    public static UUID tryGetUUIDFromString(String p_147173_0_)
    {
        if (p_147173_0_ == null)
        {
            return null;
        }
        else if (isUUIDString(p_147173_0_))
        {
            return UUID.fromString(p_147173_0_);
        }
        else
        {
            if (p_147173_0_.length() == 32)
            {
                String var1 = p_147173_0_.substring(0, 8) + "-" + p_147173_0_.substring(8, 12) + "-" + p_147173_0_.substring(12, 16) + "-" + p_147173_0_.substring(16, 20) + "-" + p_147173_0_.substring(20, 32);

                if (isUUIDString(var1))
                {
                    return UUID.fromString(var1);
                }
            }

            return null;
        }
    }

    public static enum EnumOS
    {
        LINUX("LINUX", 0),
        SOLARIS("SOLARIS", 1),
        WINDOWS("WINDOWS", 2),
        MACOS("MACOS", 3),
        UNKNOWN("UNKNOWN", 4);

        private static final Util.EnumOS[] $VALUES = new Util.EnumOS[]{LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN};
        private static final String __OBFID = "CL_00001660";

        private EnumOS(String par1Str, int par2) {}
    }
}
