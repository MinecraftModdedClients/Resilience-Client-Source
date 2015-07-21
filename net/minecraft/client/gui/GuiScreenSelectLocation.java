package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiScreenSelectLocation
{
    private final Minecraft field_148368_a;
    private int field_148363_g;
    private int field_148375_h;
    protected int field_148366_b;
    protected int field_148367_c;
    private int field_148376_i;
    private int field_148373_j;
    protected final int field_148364_d;
    private int field_148374_k;
    private int field_148371_l;
    protected int field_148365_e;
    protected int field_148362_f;
    private float field_148372_m = -2.0F;
    private float field_148369_n;
    private float field_148370_o;
    private int field_148381_p = -1;
    private long field_148380_q;
    private boolean field_148379_r = true;
    private boolean field_148378_s;
    private int field_148377_t;
    private static final String __OBFID = "CL_00000785";

    public GuiScreenSelectLocation(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6)
    {
        this.field_148368_a = par1Minecraft;
        this.field_148363_g = par2;
        this.field_148375_h = par3;
        this.field_148366_b = par4;
        this.field_148367_c = par5;
        this.field_148364_d = par6;
        this.field_148373_j = 0;
        this.field_148376_i = par2;
    }

    public void func_148346_a(int p_148346_1_, int p_148346_2_, int p_148346_3_, int p_148346_4_)
    {
        this.field_148363_g = p_148346_1_;
        this.field_148375_h = p_148346_2_;
        this.field_148366_b = p_148346_3_;
        this.field_148367_c = p_148346_4_;
        this.field_148373_j = 0;
        this.field_148376_i = p_148346_1_;
    }

    protected abstract int func_148355_a();

    protected abstract void func_148352_a(int var1, boolean var2);

    protected abstract boolean func_148356_a(int var1);

    protected abstract boolean func_148349_b(int var1);

    protected int func_148351_b()
    {
        return this.func_148355_a() * this.field_148364_d + this.field_148377_t;
    }

    protected abstract void func_148358_c();

    protected abstract void func_148348_a(int var1, int var2, int var3, int var4, Tessellator var5);

    protected void func_148354_a(int p_148354_1_, int p_148354_2_, Tessellator p_148354_3_) {}

    protected void func_148359_a(int p_148359_1_, int p_148359_2_) {}

    protected void func_148353_b(int p_148353_1_, int p_148353_2_) {}

    private void func_148361_h()
    {
        int var1 = this.func_148347_d();

        if (var1 < 0)
        {
            var1 /= 2;
        }

        if (this.field_148370_o < 0.0F)
        {
            this.field_148370_o = 0.0F;
        }

        if (this.field_148370_o > (float)var1)
        {
            this.field_148370_o = (float)var1;
        }
    }

    public int func_148347_d()
    {
        return this.func_148351_b() - (this.field_148367_c - this.field_148366_b - 4);
    }

    public void func_148357_a(GuiButton p_148357_1_)
    {
        if (p_148357_1_.enabled)
        {
            if (p_148357_1_.id == this.field_148374_k)
            {
                this.field_148370_o -= (float)(this.field_148364_d * 2 / 3);
                this.field_148372_m = -2.0F;
                this.func_148361_h();
            }
            else if (p_148357_1_.id == this.field_148371_l)
            {
                this.field_148370_o += (float)(this.field_148364_d * 2 / 3);
                this.field_148372_m = -2.0F;
                this.func_148361_h();
            }
        }
    }

    public void func_148350_a(int p_148350_1_, int p_148350_2_, float p_148350_3_)
    {
        this.field_148365_e = p_148350_1_;
        this.field_148362_f = p_148350_2_;
        this.func_148358_c();
        int var4 = this.func_148355_a();
        int var5 = this.func_148360_g();
        int var6 = var5 + 6;
        int var9;
        int var10;
        int var11;
        int var13;
        int var19;

        if (Mouse.isButtonDown(0))
        {
            if (this.field_148372_m == -1.0F)
            {
                boolean var7 = true;

                if (p_148350_2_ >= this.field_148366_b && p_148350_2_ <= this.field_148367_c)
                {
                    int var8 = this.field_148363_g / 2 - 110;
                    var9 = this.field_148363_g / 2 + 110;
                    var10 = p_148350_2_ - this.field_148366_b - this.field_148377_t + (int)this.field_148370_o - 4;
                    var11 = var10 / this.field_148364_d;

                    if (p_148350_1_ >= var8 && p_148350_1_ <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4)
                    {
                        boolean var12 = var11 == this.field_148381_p && Minecraft.getSystemTime() - this.field_148380_q < 250L;
                        this.func_148352_a(var11, var12);
                        this.field_148381_p = var11;
                        this.field_148380_q = Minecraft.getSystemTime();
                    }
                    else if (p_148350_1_ >= var8 && p_148350_1_ <= var9 && var10 < 0)
                    {
                        this.func_148359_a(p_148350_1_ - var8, p_148350_2_ - this.field_148366_b + (int)this.field_148370_o - 4);
                        var7 = false;
                    }

                    if (p_148350_1_ >= var5 && p_148350_1_ <= var6)
                    {
                        this.field_148369_n = -1.0F;
                        var19 = this.func_148347_d();

                        if (var19 < 1)
                        {
                            var19 = 1;
                        }

                        var13 = (int)((float)((this.field_148367_c - this.field_148366_b) * (this.field_148367_c - this.field_148366_b)) / (float)this.func_148351_b());

                        if (var13 < 32)
                        {
                            var13 = 32;
                        }

                        if (var13 > this.field_148367_c - this.field_148366_b - 8)
                        {
                            var13 = this.field_148367_c - this.field_148366_b - 8;
                        }

                        this.field_148369_n /= (float)(this.field_148367_c - this.field_148366_b - var13) / (float)var19;
                    }
                    else
                    {
                        this.field_148369_n = 1.0F;
                    }

                    if (var7)
                    {
                        this.field_148372_m = (float)p_148350_2_;
                    }
                    else
                    {
                        this.field_148372_m = -2.0F;
                    }
                }
                else
                {
                    this.field_148372_m = -2.0F;
                }
            }
            else if (this.field_148372_m >= 0.0F)
            {
                this.field_148370_o -= ((float)p_148350_2_ - this.field_148372_m) * this.field_148369_n;
                this.field_148372_m = (float)p_148350_2_;
            }
        }
        else
        {
            while (!this.field_148368_a.gameSettings.touchscreen && Mouse.next())
            {
                int var16 = Mouse.getEventDWheel();

                if (var16 != 0)
                {
                    if (var16 > 0)
                    {
                        var16 = -1;
                    }
                    else if (var16 < 0)
                    {
                        var16 = 1;
                    }

                    this.field_148370_o += (float)(var16 * this.field_148364_d / 2);
                }
            }

            this.field_148372_m = -1.0F;
        }

        this.func_148361_h();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        Tessellator var18 = Tessellator.instance;
        this.field_148368_a.getTextureManager().bindTexture(Gui.optionsBackground);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var17 = 32.0F;
        var18.startDrawingQuads();
        var18.setColorOpaque_I(2105376);
        var18.addVertexWithUV((double)this.field_148373_j, (double)this.field_148367_c, 0.0D, (double)((float)this.field_148373_j / var17), (double)((float)(this.field_148367_c + (int)this.field_148370_o) / var17));
        var18.addVertexWithUV((double)this.field_148376_i, (double)this.field_148367_c, 0.0D, (double)((float)this.field_148376_i / var17), (double)((float)(this.field_148367_c + (int)this.field_148370_o) / var17));
        var18.addVertexWithUV((double)this.field_148376_i, (double)this.field_148366_b, 0.0D, (double)((float)this.field_148376_i / var17), (double)((float)(this.field_148366_b + (int)this.field_148370_o) / var17));
        var18.addVertexWithUV((double)this.field_148373_j, (double)this.field_148366_b, 0.0D, (double)((float)this.field_148373_j / var17), (double)((float)(this.field_148366_b + (int)this.field_148370_o) / var17));
        var18.draw();
        var9 = this.field_148363_g / 2 - 92 - 16;
        var10 = this.field_148366_b + 4 - (int)this.field_148370_o;

        if (this.field_148378_s)
        {
            this.func_148354_a(var9, var10, var18);
        }

        int var14;

        for (var11 = 0; var11 < var4; ++var11)
        {
            var19 = var10 + var11 * this.field_148364_d + this.field_148377_t;
            var13 = this.field_148364_d - 4;

            if (var19 <= this.field_148367_c && var19 + var13 >= this.field_148366_b)
            {
                int var15;

                if (this.field_148379_r && this.func_148349_b(var11))
                {
                    var14 = this.field_148363_g / 2 - 110;
                    var15 = this.field_148363_g / 2 + 110;
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    var18.startDrawingQuads();
                    var18.setColorOpaque_I(0);
                    var18.addVertexWithUV((double)var14, (double)(var19 + var13 + 2), 0.0D, 0.0D, 1.0D);
                    var18.addVertexWithUV((double)var15, (double)(var19 + var13 + 2), 0.0D, 1.0D, 1.0D);
                    var18.addVertexWithUV((double)var15, (double)(var19 - 2), 0.0D, 1.0D, 0.0D);
                    var18.addVertexWithUV((double)var14, (double)(var19 - 2), 0.0D, 0.0D, 0.0D);
                    var18.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                if (this.field_148379_r && this.func_148356_a(var11))
                {
                    var14 = this.field_148363_g / 2 - 110;
                    var15 = this.field_148363_g / 2 + 110;
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    var18.startDrawingQuads();
                    var18.setColorOpaque_I(8421504);
                    var18.addVertexWithUV((double)var14, (double)(var19 + var13 + 2), 0.0D, 0.0D, 1.0D);
                    var18.addVertexWithUV((double)var15, (double)(var19 + var13 + 2), 0.0D, 1.0D, 1.0D);
                    var18.addVertexWithUV((double)var15, (double)(var19 - 2), 0.0D, 1.0D, 0.0D);
                    var18.addVertexWithUV((double)var14, (double)(var19 - 2), 0.0D, 0.0D, 0.0D);
                    var18.setColorOpaque_I(0);
                    var18.addVertexWithUV((double)(var14 + 1), (double)(var19 + var13 + 1), 0.0D, 0.0D, 1.0D);
                    var18.addVertexWithUV((double)(var15 - 1), (double)(var19 + var13 + 1), 0.0D, 1.0D, 1.0D);
                    var18.addVertexWithUV((double)(var15 - 1), (double)(var19 - 1), 0.0D, 1.0D, 0.0D);
                    var18.addVertexWithUV((double)(var14 + 1), (double)(var19 - 1), 0.0D, 0.0D, 0.0D);
                    var18.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                }

                this.func_148348_a(var11, var9, var19, var13, var18);
            }
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        byte var20 = 4;
        this.func_148345_b(0, this.field_148366_b, 255, 255);
        this.func_148345_b(this.field_148367_c, this.field_148375_h, 255, 255);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV((double)this.field_148373_j, (double)(this.field_148366_b + var20), 0.0D, 0.0D, 1.0D);
        var18.addVertexWithUV((double)this.field_148376_i, (double)(this.field_148366_b + var20), 0.0D, 1.0D, 1.0D);
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV((double)this.field_148376_i, (double)this.field_148366_b, 0.0D, 1.0D, 0.0D);
        var18.addVertexWithUV((double)this.field_148373_j, (double)this.field_148366_b, 0.0D, 0.0D, 0.0D);
        var18.draw();
        var18.startDrawingQuads();
        var18.setColorRGBA_I(0, 255);
        var18.addVertexWithUV((double)this.field_148373_j, (double)this.field_148367_c, 0.0D, 0.0D, 1.0D);
        var18.addVertexWithUV((double)this.field_148376_i, (double)this.field_148367_c, 0.0D, 1.0D, 1.0D);
        var18.setColorRGBA_I(0, 0);
        var18.addVertexWithUV((double)this.field_148376_i, (double)(this.field_148367_c - var20), 0.0D, 1.0D, 0.0D);
        var18.addVertexWithUV((double)this.field_148373_j, (double)(this.field_148367_c - var20), 0.0D, 0.0D, 0.0D);
        var18.draw();
        var19 = this.func_148347_d();

        if (var19 > 0)
        {
            var13 = (this.field_148367_c - this.field_148366_b) * (this.field_148367_c - this.field_148366_b) / this.func_148351_b();

            if (var13 < 32)
            {
                var13 = 32;
            }

            if (var13 > this.field_148367_c - this.field_148366_b - 8)
            {
                var13 = this.field_148367_c - this.field_148366_b - 8;
            }

            var14 = (int)this.field_148370_o * (this.field_148367_c - this.field_148366_b - var13) / var19 + this.field_148366_b;

            if (var14 < this.field_148366_b)
            {
                var14 = this.field_148366_b;
            }

            var18.startDrawingQuads();
            var18.setColorRGBA_I(0, 255);
            var18.addVertexWithUV((double)var5, (double)this.field_148367_c, 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double)var6, (double)this.field_148367_c, 0.0D, 1.0D, 1.0D);
            var18.addVertexWithUV((double)var6, (double)this.field_148366_b, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double)var5, (double)this.field_148366_b, 0.0D, 0.0D, 0.0D);
            var18.draw();
            var18.startDrawingQuads();
            var18.setColorRGBA_I(8421504, 255);
            var18.addVertexWithUV((double)var5, (double)(var14 + var13), 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double)var6, (double)(var14 + var13), 0.0D, 1.0D, 1.0D);
            var18.addVertexWithUV((double)var6, (double)var14, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double)var5, (double)var14, 0.0D, 0.0D, 0.0D);
            var18.draw();
            var18.startDrawingQuads();
            var18.setColorRGBA_I(12632256, 255);
            var18.addVertexWithUV((double)var5, (double)(var14 + var13 - 1), 0.0D, 0.0D, 1.0D);
            var18.addVertexWithUV((double)(var6 - 1), (double)(var14 + var13 - 1), 0.0D, 1.0D, 1.0D);
            var18.addVertexWithUV((double)(var6 - 1), (double)var14, 0.0D, 1.0D, 0.0D);
            var18.addVertexWithUV((double)var5, (double)var14, 0.0D, 0.0D, 0.0D);
            var18.draw();
        }

        this.func_148353_b(p_148350_1_, p_148350_2_);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    protected int func_148360_g()
    {
        return this.field_148363_g / 2 + 124;
    }

    private void func_148345_b(int p_148345_1_, int p_148345_2_, int p_148345_3_, int p_148345_4_)
    {
        Tessellator var5 = Tessellator.instance;
        this.field_148368_a.getTextureManager().bindTexture(Gui.optionsBackground);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var6 = 32.0F;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(4210752, p_148345_4_);
        var5.addVertexWithUV(0.0D, (double)p_148345_2_, 0.0D, 0.0D, (double)((float)p_148345_2_ / var6));
        var5.addVertexWithUV((double)this.field_148363_g, (double)p_148345_2_, 0.0D, (double)((float)this.field_148363_g / var6), (double)((float)p_148345_2_ / var6));
        var5.setColorRGBA_I(4210752, p_148345_3_);
        var5.addVertexWithUV((double)this.field_148363_g, (double)p_148345_1_, 0.0D, (double)((float)this.field_148363_g / var6), (double)((float)p_148345_1_ / var6));
        var5.addVertexWithUV(0.0D, (double)p_148345_1_, 0.0D, 0.0D, (double)((float)p_148345_1_ / var6));
        var5.draw();
    }
}
