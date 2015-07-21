package net.minecraft.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.server.MinecraftServer;

public class ServerScoreboard extends Scoreboard
{
    private final MinecraftServer scoreboardMCServer;
    private final Set field_96553_b = new HashSet();
    private ScoreboardSaveData field_96554_c;
    private static final String __OBFID = "CL_00001424";

    public ServerScoreboard(MinecraftServer par1MinecraftServer)
    {
        this.scoreboardMCServer = par1MinecraftServer;
    }

    public void func_96536_a(Score par1Score)
    {
        super.func_96536_a(par1Score);

        if (this.field_96553_b.contains(par1Score.func_96645_d()))
        {
            this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3CPacketUpdateScore(par1Score, 0));
        }

        this.func_96551_b();
    }

    public void func_96516_a(String par1Str)
    {
        super.func_96516_a(par1Str);
        this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3CPacketUpdateScore(par1Str));
        this.func_96551_b();
    }

    public void func_96530_a(int par1, ScoreObjective par2ScoreObjective)
    {
        ScoreObjective var3 = this.func_96539_a(par1);
        super.func_96530_a(par1, par2ScoreObjective);

        if (var3 != par2ScoreObjective && var3 != null)
        {
            if (this.func_96552_h(var3) > 0)
            {
                this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3DPacketDisplayScoreboard(par1, par2ScoreObjective));
            }
            else
            {
                this.func_96546_g(var3);
            }
        }

        if (par2ScoreObjective != null)
        {
            if (this.field_96553_b.contains(par2ScoreObjective))
            {
                this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3DPacketDisplayScoreboard(par1, par2ScoreObjective));
            }
            else
            {
                this.func_96549_e(par2ScoreObjective);
            }
        }

        this.func_96551_b();
    }

    public boolean func_151392_a(String p_151392_1_, String p_151392_2_)
    {
        if (super.func_151392_a(p_151392_1_, p_151392_2_))
        {
            ScorePlayerTeam var3 = this.getTeam(p_151392_2_);
            this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(var3, Arrays.asList(new String[] {p_151392_1_}), 3));
            this.func_96551_b();
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Removes the given username from the given ScorePlayerTeam. If the player is not on the team then an
     * IllegalStateException is thrown.
     */
    public void removePlayerFromTeam(String par1Str, ScorePlayerTeam par2ScorePlayerTeam)
    {
        super.removePlayerFromTeam(par1Str, par2ScorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(par2ScorePlayerTeam, Arrays.asList(new String[] {par1Str}), 4));
        this.func_96551_b();
    }

    public void func_96522_a(ScoreObjective par1ScoreObjective)
    {
        super.func_96522_a(par1ScoreObjective);
        this.func_96551_b();
    }

    public void func_96532_b(ScoreObjective par1ScoreObjective)
    {
        super.func_96532_b(par1ScoreObjective);

        if (this.field_96553_b.contains(par1ScoreObjective))
        {
            this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3BPacketScoreboardObjective(par1ScoreObjective, 2));
        }

        this.func_96551_b();
    }

    public void func_96533_c(ScoreObjective par1ScoreObjective)
    {
        super.func_96533_c(par1ScoreObjective);

        if (this.field_96553_b.contains(par1ScoreObjective))
        {
            this.func_96546_g(par1ScoreObjective);
        }

        this.func_96551_b();
    }

    public void func_96523_a(ScorePlayerTeam par1ScorePlayerTeam)
    {
        super.func_96523_a(par1ScorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(par1ScorePlayerTeam, 0));
        this.func_96551_b();
    }

    public void func_96538_b(ScorePlayerTeam par1ScorePlayerTeam)
    {
        super.func_96538_b(par1ScorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(par1ScorePlayerTeam, 2));
        this.func_96551_b();
    }

    public void func_96513_c(ScorePlayerTeam par1ScorePlayerTeam)
    {
        super.func_96513_c(par1ScorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(par1ScorePlayerTeam, 1));
        this.func_96551_b();
    }

    public void func_96547_a(ScoreboardSaveData par1ScoreboardSaveData)
    {
        this.field_96554_c = par1ScoreboardSaveData;
    }

    protected void func_96551_b()
    {
        if (this.field_96554_c != null)
        {
            this.field_96554_c.markDirty();
        }
    }

    public List func_96550_d(ScoreObjective par1ScoreObjective)
    {
        ArrayList var2 = new ArrayList();
        var2.add(new S3BPacketScoreboardObjective(par1ScoreObjective, 0));

        for (int var3 = 0; var3 < 3; ++var3)
        {
            if (this.func_96539_a(var3) == par1ScoreObjective)
            {
                var2.add(new S3DPacketDisplayScoreboard(var3, par1ScoreObjective));
            }
        }

        Iterator var5 = this.func_96534_i(par1ScoreObjective).iterator();

        while (var5.hasNext())
        {
            Score var4 = (Score)var5.next();
            var2.add(new S3CPacketUpdateScore(var4, 0));
        }

        return var2;
    }

    public void func_96549_e(ScoreObjective par1ScoreObjective)
    {
        List var2 = this.func_96550_d(par1ScoreObjective);
        Iterator var3 = this.scoreboardMCServer.getConfigurationManager().playerEntityList.iterator();

        while (var3.hasNext())
        {
            EntityPlayerMP var4 = (EntityPlayerMP)var3.next();
            Iterator var5 = var2.iterator();

            while (var5.hasNext())
            {
                Packet var6 = (Packet)var5.next();
                var4.playerNetServerHandler.sendPacket(var6);
            }
        }

        this.field_96553_b.add(par1ScoreObjective);
    }

    public List func_96548_f(ScoreObjective par1ScoreObjective)
    {
        ArrayList var2 = new ArrayList();
        var2.add(new S3BPacketScoreboardObjective(par1ScoreObjective, 1));

        for (int var3 = 0; var3 < 3; ++var3)
        {
            if (this.func_96539_a(var3) == par1ScoreObjective)
            {
                var2.add(new S3DPacketDisplayScoreboard(var3, par1ScoreObjective));
            }
        }

        return var2;
    }

    public void func_96546_g(ScoreObjective par1ScoreObjective)
    {
        List var2 = this.func_96548_f(par1ScoreObjective);
        Iterator var3 = this.scoreboardMCServer.getConfigurationManager().playerEntityList.iterator();

        while (var3.hasNext())
        {
            EntityPlayerMP var4 = (EntityPlayerMP)var3.next();
            Iterator var5 = var2.iterator();

            while (var5.hasNext())
            {
                Packet var6 = (Packet)var5.next();
                var4.playerNetServerHandler.sendPacket(var6);
            }
        }

        this.field_96553_b.remove(par1ScoreObjective);
    }

    public int func_96552_h(ScoreObjective par1ScoreObjective)
    {
        int var2 = 0;

        for (int var3 = 0; var3 < 3; ++var3)
        {
            if (this.func_96539_a(var3) == par1ScoreObjective)
            {
                ++var2;
            }
        }

        return var2;
    }
}
