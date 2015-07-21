package net.minecraft.client.model;

public class ModelPig extends ModelQuadruped
{
    private static final String __OBFID = "CL_00000849";

    public ModelPig()
    {
        this(0.0F);
    }

    public ModelPig(float par1)
    {
        super(6, par1);
        this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, par1);
        this.field_78145_g = 4.0F;
    }
}
