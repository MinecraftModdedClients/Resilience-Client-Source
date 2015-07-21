package net.minecraft.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptManager
{
    private static final String __OBFID = "CL_00001483";

    /**
     * Generate a new shared secret AES key from a secure random source
     */
    public static SecretKey createNewSharedKey()
    {
        try
        {
            KeyGenerator var0 = KeyGenerator.getInstance("AES");
            var0.init(128);
            return var0.generateKey();
        }
        catch (NoSuchAlgorithmException var1)
        {
            throw new Error(var1);
        }
    }

    public static KeyPair createNewKeyPair()
    {
        try
        {
            KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
            var0.initialize(1024);
            return var0.generateKeyPair();
        }
        catch (NoSuchAlgorithmException var1)
        {
            var1.printStackTrace();
            System.err.println("Key pair generation failed!");
            return null;
        }
    }

    /**
     * Compute a serverId hash for use by sendSessionRequest()
     */
    public static byte[] getServerIdHash(String par0Str, PublicKey par1PublicKey, SecretKey par2SecretKey)
    {
        try
        {
            return digestOperation("SHA-1", new byte[][] {par0Str.getBytes("ISO_8859_1"), par2SecretKey.getEncoded(), par1PublicKey.getEncoded()});
        }
        catch (UnsupportedEncodingException var4)
        {
            var4.printStackTrace();
            return null;
        }
    }

    /**
     * Compute a message digest on arbitrary byte[] data
     */
    private static byte[] digestOperation(String par0Str, byte[] ... par1ArrayOfByte)
    {
        try
        {
            MessageDigest var2 = MessageDigest.getInstance(par0Str);
            byte[][] var3 = par1ArrayOfByte;
            int var4 = par1ArrayOfByte.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                byte[] var6 = var3[var5];
                var2.update(var6);
            }

            return var2.digest();
        }
        catch (NoSuchAlgorithmException var7)
        {
            var7.printStackTrace();
            return null;
        }
    }

    /**
     * Create a new PublicKey from encoded X.509 data
     */
    public static PublicKey decodePublicKey(byte[] par0ArrayOfByte)
    {
        try
        {
            X509EncodedKeySpec var1 = new X509EncodedKeySpec(par0ArrayOfByte);
            KeyFactory var2 = KeyFactory.getInstance("RSA");
            return var2.generatePublic(var1);
        }
        catch (NoSuchAlgorithmException var3)
        {
            ;
        }
        catch (InvalidKeySpecException var4)
        {
            ;
        }

        System.err.println("Public key reconstitute failed!");
        return null;
    }

    /**
     * Decrypt shared secret AES key using RSA private key
     */
    public static SecretKey decryptSharedKey(PrivateKey par0PrivateKey, byte[] par1ArrayOfByte)
    {
        return new SecretKeySpec(decryptData(par0PrivateKey, par1ArrayOfByte), "AES");
    }

    /**
     * Encrypt byte[] data with RSA public key
     */
    public static byte[] encryptData(Key par0Key, byte[] par1ArrayOfByte)
    {
        return cipherOperation(1, par0Key, par1ArrayOfByte);
    }

    /**
     * Decrypt byte[] data with RSA private key
     */
    public static byte[] decryptData(Key par0Key, byte[] par1ArrayOfByte)
    {
        return cipherOperation(2, par0Key, par1ArrayOfByte);
    }

    /**
     * Encrypt or decrypt byte[] data using the specified key
     */
    private static byte[] cipherOperation(int par0, Key par1Key, byte[] par2ArrayOfByte)
    {
        try
        {
            return createTheCipherInstance(par0, par1Key.getAlgorithm(), par1Key).doFinal(par2ArrayOfByte);
        }
        catch (IllegalBlockSizeException var4)
        {
            var4.printStackTrace();
        }
        catch (BadPaddingException var5)
        {
            var5.printStackTrace();
        }

        System.err.println("Cipher data failed!");
        return null;
    }

    /**
     * Creates the Cipher Instance.
     */
    private static Cipher createTheCipherInstance(int par0, String par1Str, Key par2Key)
    {
        try
        {
            Cipher var3 = Cipher.getInstance(par1Str);
            var3.init(par0, par2Key);
            return var3;
        }
        catch (InvalidKeyException var4)
        {
            var4.printStackTrace();
        }
        catch (NoSuchAlgorithmException var5)
        {
            var5.printStackTrace();
        }
        catch (NoSuchPaddingException var6)
        {
            var6.printStackTrace();
        }

        System.err.println("Cipher creation failed!");
        return null;
    }

    public static Cipher func_151229_a(int p_151229_0_, Key p_151229_1_)
    {
        try
        {
            Cipher var2 = Cipher.getInstance("AES/CFB8/NoPadding");
            var2.init(p_151229_0_, p_151229_1_, new IvParameterSpec(p_151229_1_.getEncoded()));
            return var2;
        }
        catch (GeneralSecurityException var3)
        {
            throw new RuntimeException(var3);
        }
    }
}
