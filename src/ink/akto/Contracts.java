package ink.akto;

import com.sun.istack.internal.Nullable;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ruben on 02.05.2017.
 */
public interface Contracts
{
    interface EncryptorView
    {
        void showError(String error);
        String getKey();
    }

    interface EncryptorPresenter
    {
        void encrypt(@Nullable String file, String key);
        void decrypt(@Nullable String file, String key);
//        void encrypt(CallbackWithEntity<File, File> callback, byte[] key) throws Exception;
//        void decrypt(CallbackWithEntity<File, File> callback, byte[] key) throws Exception;
        void setEncryptionStrategy(EncryptionStrategy strategy);
        String openFileChooser(Window window, String initDirPath);
        String getEncryptedDir();
    }

    interface EncryptorModel
    {
        File getEncryptedDir();
        void saveEncrypted(String name, byte[] bytes) throws IOException;
        void saveDecrypted(String name, byte[] bytes) throws IOException;
        byte[] getBytes(String file) throws IOException;
        File openFileChooser(Window window, String initDirPath);
    }

    interface EncryptionStrategy
    {
        byte[] encrypt(byte[] originalBytes, byte[] key);
        byte[] decrypt(byte[] encryptedBytes, byte[] key);
    }

    interface CallbackWithEntity<Entity, Result>
    {
        Entity get();
        void onEnd(Result result);
    }
}
