package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.storage.WorldInfo;

public final class WorldSettings
{
    /** The seed for the map. */
    private final long seed;

    /** The EnumGameType. */
    private final WorldSettings.GameType theGameType;

    /**
     * Switch for the map features. 'true' for enabled, 'false' for disabled.
     */
    private final boolean mapFeaturesEnabled;

    /** True if hardcore mode is enabled */
    private final boolean hardcoreEnabled;
    private final WorldType terrainType;

    /** True if Commands (cheats) are allowed. */
    private boolean commandsAllowed;

    /** True if the Bonus Chest is enabled. */
    private boolean bonusChestEnabled;
    private String field_82751_h;
    private static final String __OBFID = "CL_00000147";

    public WorldSettings(long par1, WorldSettings.GameType par3EnumGameType, boolean par4, boolean par5, WorldType par6WorldType)
    {
        this.field_82751_h = "";
        this.seed = par1;
        this.theGameType = par3EnumGameType;
        this.mapFeaturesEnabled = par4;
        this.hardcoreEnabled = par5;
        this.terrainType = par6WorldType;
    }

    public WorldSettings(WorldInfo par1WorldInfo)
    {
        this(par1WorldInfo.getSeed(), par1WorldInfo.getGameType(), par1WorldInfo.isMapFeaturesEnabled(), par1WorldInfo.isHardcoreModeEnabled(), par1WorldInfo.getTerrainType());
    }

    /**
     * Enables the bonus chest.
     */
    public WorldSettings enableBonusChest()
    {
        this.bonusChestEnabled = true;
        return this;
    }

    /**
     * Enables Commands (cheats).
     */
    public WorldSettings enableCommands()
    {
        this.commandsAllowed = true;
        return this;
    }

    public WorldSettings func_82750_a(String par1Str)
    {
        this.field_82751_h = par1Str;
        return this;
    }

    /**
     * Returns true if the Bonus Chest is enabled.
     */
    public boolean isBonusChestEnabled()
    {
        return this.bonusChestEnabled;
    }

    /**
     * Returns the seed for the world.
     */
    public long getSeed()
    {
        return this.seed;
    }

    /**
     * Gets the game type.
     */
    public WorldSettings.GameType getGameType()
    {
        return this.theGameType;
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean getHardcoreEnabled()
    {
        return this.hardcoreEnabled;
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean isMapFeaturesEnabled()
    {
        return this.mapFeaturesEnabled;
    }

    public WorldType getTerrainType()
    {
        return this.terrainType;
    }

    /**
     * Returns true if Commands (cheats) are allowed.
     */
    public boolean areCommandsAllowed()
    {
        return this.commandsAllowed;
    }

    /**
     * Gets the GameType by ID
     */
    public static WorldSettings.GameType getGameTypeById(int par0)
    {
        return WorldSettings.GameType.getByID(par0);
    }

    public String func_82749_j()
    {
        return this.field_82751_h;
    }

    public static enum GameType
    {
        NOT_SET("NOT_SET", 0, -1, ""),
        SURVIVAL("SURVIVAL", 1, 0, "survival"),
        CREATIVE("CREATIVE", 2, 1, "creative"),
        ADVENTURE("ADVENTURE", 3, 2, "adventure");
        int id;
        String name;

        private static final WorldSettings.GameType[] $VALUES = new WorldSettings.GameType[]{NOT_SET, SURVIVAL, CREATIVE, ADVENTURE};
        private static final String __OBFID = "CL_00000148";

        private GameType(String par1Str, int par2, int par3, String par4Str)
        {
            this.id = par3;
            this.name = par4Str;
        }

        public int getID()
        {
            return this.id;
        }

        public String getName()
        {
            return this.name;
        }

        public void configurePlayerCapabilities(PlayerCapabilities par1PlayerCapabilities)
        {
            if (this == CREATIVE)
            {
                par1PlayerCapabilities.allowFlying = true;
                par1PlayerCapabilities.isCreativeMode = true;
                par1PlayerCapabilities.disableDamage = true;
            }
            else
            {
                par1PlayerCapabilities.allowFlying = false;
                par1PlayerCapabilities.isCreativeMode = false;
                par1PlayerCapabilities.disableDamage = false;
                par1PlayerCapabilities.isFlying = false;
            }

            par1PlayerCapabilities.allowEdit = !this.isAdventure();
        }

        public boolean isAdventure()
        {
            return this == ADVENTURE;
        }

        public boolean isCreative()
        {
            return this == CREATIVE;
        }

        public boolean isSurvivalOrAdventure()
        {
            return this == SURVIVAL || this == ADVENTURE;
        }

        public static WorldSettings.GameType getByID(int par0)
        {
            WorldSettings.GameType[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3)
            {
                WorldSettings.GameType var4 = var1[var3];

                if (var4.id == par0)
                {
                    return var4;
                }
            }

            return SURVIVAL;
        }

        public static WorldSettings.GameType getByName(String par0Str)
        {
            WorldSettings.GameType[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3)
            {
                WorldSettings.GameType var4 = var1[var3];

                if (var4.name.equals(par0Str))
                {
                    return var4;
                }
            }

            return SURVIVAL;
        }
    }
}
