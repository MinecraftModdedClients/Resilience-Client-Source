package net.minecraft.util;

import com.mojang.authlib.GameProfile;

public class Session
{
    private final String username;
    private final String playerID;
    private final String token;
    private static final String __OBFID = "CL_00000659";

    public Session(String p_i45006_1_, String p_i45006_2_, String p_i45006_3_)
    {
        this.username = p_i45006_1_;
        this.playerID = p_i45006_2_;
        this.token = p_i45006_3_;
    }

    public String getSessionID()
    {
        return "token:" + this.token + ":" + this.playerID;
    }

    public String getPlayerID()
    {
        return this.playerID;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getToken()
    {
        return this.token;
    }

    public GameProfile func_148256_e()
    {
        return new GameProfile(this.getPlayerID(), this.getUsername());
    }
}
