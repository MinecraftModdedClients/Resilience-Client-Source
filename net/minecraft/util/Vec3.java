package net.minecraft.util;

public class Vec3
{
    /**
     * A global Vec3Pool that always creates new vectors instead of reusing them and is thread-safe.
     */
    public static final Vec3Pool fakePool = new Vec3Pool(-1, -1);
    public final Vec3Pool myVec3LocalPool;

    /** X coordinate of Vec3D */
    public double xCoord;

    /** Y coordinate of Vec3D */
    public double yCoord;

    /** Z coordinate of Vec3D */
    public double zCoord;
    private static final String __OBFID = "CL_00000612";

    /**
     * Static method for creating a new Vec3D given the three x,y,z values. This is only called from the other static
     * method which creates and places it in the list.
     */
    public static Vec3 createVectorHelper(double par0, double par2, double par4)
    {
        return new Vec3(fakePool, par0, par2, par4);
    }

    protected Vec3(Vec3Pool par1Vec3Pool, double par2, double par4, double par6)
    {
        if (par2 == -0.0D)
        {
            par2 = 0.0D;
        }

        if (par4 == -0.0D)
        {
            par4 = 0.0D;
        }

        if (par6 == -0.0D)
        {
            par6 = 0.0D;
        }

        this.xCoord = par2;
        this.yCoord = par4;
        this.zCoord = par6;
        this.myVec3LocalPool = par1Vec3Pool;
    }

    /**
     * Sets the x,y,z components of the vector as specified.
     */
    protected Vec3 setComponents(double par1, double par3, double par5)
    {
        this.xCoord = par1;
        this.yCoord = par3;
        this.zCoord = par5;
        return this;
    }

    /**
     * Returns a new vector with the result of the specified vector minus this.
     */
    public Vec3 subtract(Vec3 par1Vec3)
    {
        return this.myVec3LocalPool.getVecFromPool(par1Vec3.xCoord - this.xCoord, par1Vec3.yCoord - this.yCoord, par1Vec3.zCoord - this.zCoord);
    }

    /**
     * Normalizes the vector to a length of 1 (except if it is the zero vector)
     */
    public Vec3 normalize()
    {
        double var1 = (double)MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        return var1 < 1.0E-4D ? this.myVec3LocalPool.getVecFromPool(0.0D, 0.0D, 0.0D) : this.myVec3LocalPool.getVecFromPool(this.xCoord / var1, this.yCoord / var1, this.zCoord / var1);
    }

    public double dotProduct(Vec3 par1Vec3)
    {
        return this.xCoord * par1Vec3.xCoord + this.yCoord * par1Vec3.yCoord + this.zCoord * par1Vec3.zCoord;
    }

    /**
     * Returns a new vector with the result of this vector x the specified vector.
     */
    public Vec3 crossProduct(Vec3 par1Vec3)
    {
        return this.myVec3LocalPool.getVecFromPool(this.yCoord * par1Vec3.zCoord - this.zCoord * par1Vec3.yCoord, this.zCoord * par1Vec3.xCoord - this.xCoord * par1Vec3.zCoord, this.xCoord * par1Vec3.yCoord - this.yCoord * par1Vec3.xCoord);
    }

    /**
     * Adds the specified x,y,z vector components to this vector and returns the resulting vector. Does not change this
     * vector.
     */
    public Vec3 addVector(double par1, double par3, double par5)
    {
        return this.myVec3LocalPool.getVecFromPool(this.xCoord + par1, this.yCoord + par3, this.zCoord + par5);
    }

    /**
     * Euclidean distance between this and the specified vector, returned as double.
     */
    public double distanceTo(Vec3 par1Vec3)
    {
        double var2 = par1Vec3.xCoord - this.xCoord;
        double var4 = par1Vec3.yCoord - this.yCoord;
        double var6 = par1Vec3.zCoord - this.zCoord;
        return (double)MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
    }

    /**
     * The square of the Euclidean distance between this and the specified vector.
     */
    public double squareDistanceTo(Vec3 par1Vec3)
    {
        double var2 = par1Vec3.xCoord - this.xCoord;
        double var4 = par1Vec3.yCoord - this.yCoord;
        double var6 = par1Vec3.zCoord - this.zCoord;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }

    /**
     * The square of the Euclidean distance between this and the vector of x,y,z components passed in.
     */
    public double squareDistanceTo(double par1, double par3, double par5)
    {
        double var7 = par1 - this.xCoord;
        double var9 = par3 - this.yCoord;
        double var11 = par5 - this.zCoord;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    /**
     * Returns the length of the vector.
     */
    public double lengthVector()
    {
        return (double)MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
    }

    /**
     * Returns a new vector with x value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Vec3 getIntermediateWithXValue(Vec3 par1Vec3, double par2)
    {
        double var4 = par1Vec3.xCoord - this.xCoord;
        double var6 = par1Vec3.yCoord - this.yCoord;
        double var8 = par1Vec3.zCoord - this.zCoord;

        if (var4 * var4 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double var10 = (par2 - this.xCoord) / var4;
            return var10 >= 0.0D && var10 <= 1.0D ? this.myVec3LocalPool.getVecFromPool(this.xCoord + var4 * var10, this.yCoord + var6 * var10, this.zCoord + var8 * var10) : null;
        }
    }

    /**
     * Returns a new vector with y value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Vec3 getIntermediateWithYValue(Vec3 par1Vec3, double par2)
    {
        double var4 = par1Vec3.xCoord - this.xCoord;
        double var6 = par1Vec3.yCoord - this.yCoord;
        double var8 = par1Vec3.zCoord - this.zCoord;

        if (var6 * var6 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double var10 = (par2 - this.yCoord) / var6;
            return var10 >= 0.0D && var10 <= 1.0D ? this.myVec3LocalPool.getVecFromPool(this.xCoord + var4 * var10, this.yCoord + var6 * var10, this.zCoord + var8 * var10) : null;
        }
    }

    /**
     * Returns a new vector with z value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Vec3 getIntermediateWithZValue(Vec3 par1Vec3, double par2)
    {
        double var4 = par1Vec3.xCoord - this.xCoord;
        double var6 = par1Vec3.yCoord - this.yCoord;
        double var8 = par1Vec3.zCoord - this.zCoord;

        if (var8 * var8 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double var10 = (par2 - this.zCoord) / var8;
            return var10 >= 0.0D && var10 <= 1.0D ? this.myVec3LocalPool.getVecFromPool(this.xCoord + var4 * var10, this.yCoord + var6 * var10, this.zCoord + var8 * var10) : null;
        }
    }

    public String toString()
    {
        return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
    }

    /**
     * Rotates the vector around the x axis by the specified angle.
     */
    public void rotateAroundX(float par1)
    {
        float var2 = MathHelper.cos(par1);
        float var3 = MathHelper.sin(par1);
        double var4 = this.xCoord;
        double var6 = this.yCoord * (double)var2 + this.zCoord * (double)var3;
        double var8 = this.zCoord * (double)var2 - this.yCoord * (double)var3;
        this.xCoord = var4;
        this.yCoord = var6;
        this.zCoord = var8;
    }

    /**
     * Rotates the vector around the y axis by the specified angle.
     */
    public void rotateAroundY(float par1)
    {
        float var2 = MathHelper.cos(par1);
        float var3 = MathHelper.sin(par1);
        double var4 = this.xCoord * (double)var2 + this.zCoord * (double)var3;
        double var6 = this.yCoord;
        double var8 = this.zCoord * (double)var2 - this.xCoord * (double)var3;
        this.xCoord = var4;
        this.yCoord = var6;
        this.zCoord = var8;
    }

    /**
     * Rotates the vector around the z axis by the specified angle.
     */
    public void rotateAroundZ(float par1)
    {
        float var2 = MathHelper.cos(par1);
        float var3 = MathHelper.sin(par1);
        double var4 = this.xCoord * (double)var2 + this.yCoord * (double)var3;
        double var6 = this.yCoord * (double)var2 - this.xCoord * (double)var3;
        double var8 = this.zCoord;
        this.xCoord = var4;
        this.yCoord = var6;
        this.zCoord = var8;
    }
}
