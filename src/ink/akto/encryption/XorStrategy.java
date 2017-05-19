package ink.akto.encryption;

import ink.akto.Contracts;

import java.io.IOException;

/**
 * Created by Ruben on 08.05.2017.
 */
public class XorStrategy implements Contracts.EncryptionStrategy
{
    @Override
    public byte[] encrypt(byte[] originalBytes, byte[] key)
    {
        byte[] encryptedBytes = new byte[originalBytes.length];
        for (int i = 0; i<originalBytes.length; i++)
        {
            encryptedBytes[i] = (byte) (originalBytes[i] ^ key[i % key.length]);
        }

        return encryptedBytes;
    }

    @Override
    public byte[] decrypt(byte[] encryptedBytes, byte[] key)
    {
        byte[] originalBytes = new byte[encryptedBytes.length];
        for (int i = 0; i<encryptedBytes.length; i++)
        {
            originalBytes[i] = (byte) (encryptedBytes[i] ^ key[i % key.length]);
        }

        return originalBytes;
    }
}
