package net.minecraft.util;

public class ChatAllowedCharacters
{
    /**
     * Array of the special characters that are allowed in any text drawing of Minecraft.
     */
    public static final char[] allowedCharacters = new char[] {'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};
    private static final String __OBFID = "CL_00001606";

    public static boolean isAllowedCharacter(char par0)
    {
        return par0 != 167 && par0 >= 32 && par0 != 127;
    }

    /**
     * Filter string by only keeping those characters for which isAllowedCharacter() returns true.
     */
    public static String filerAllowedCharacters(String par0Str)
    {
        StringBuilder var1 = new StringBuilder();
        char[] var2 = par0Str.toCharArray();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            char var5 = var2[var4];

            if (isAllowedCharacter(var5))
            {
                var1.append(var5);
            }
        }

        return var1.toString();
    }
}
