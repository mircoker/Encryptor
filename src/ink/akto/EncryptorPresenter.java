package ink.akto;

import com.sun.istack.internal.Nullable;
import ink.akto.encryption.XorStrategy;
import javafx.stage.Window;

import java.io.*;

/**
 * Created by Ruben on 02.05.2017.
 */
public class EncryptorPresenter implements Contracts.EncryptorPresenter
{
    private Contracts.EncryptorView view;
    private Contracts.EncryptorModel model;
    private Contracts.EncryptionStrategy encryptionStrategy;


    public EncryptorPresenter(Contracts.EncryptorView encryptorView)
    {
        view = encryptorView;
        model = new MainModel();
        setEncryptionStrategy(new XorStrategy());
    }

    public EncryptorPresenter(Contracts.EncryptorView encryptorView, Contracts.EncryptionStrategy strategy)
    {
        this(encryptorView);
        setEncryptionStrategy(strategy);
    }

    @Override
    public void encrypt(@Nullable String file, String key)
    {
        if(file!=null)
        {
            try
            {
                validatePassword(key.getBytes());
                model.saveEncrypted(new File(file).getName(),
                        encryptionStrategy.encrypt(model.getBytes(file), key.getBytes()));
            }
                catch (Exception e)
            {
                e.printStackTrace();
                view.showError(e.toString());
            }
        }
    }

    @Override
    public void decrypt(@Nullable String file, String key)
    {
        if(file!=null)
        {
            try
            {
                validatePassword(key.getBytes());
                model.saveDecrypted(new File(file).getName(),
                        encryptionStrategy.decrypt(model.getBytes(file), key.getBytes()));
            }
                catch (Exception e) {
                    e.printStackTrace();
                    view.showError(e.toString());
            }
        }
    }

    private void validatePassword(byte[] key) throws Exception
    {
        if(key.length<1) throw new Exception("Password is empty");
    }

    @Override
    public void setEncryptionStrategy(Contracts.EncryptionStrategy strategy)
    {
        encryptionStrategy = strategy;
    }

    @Override
    public String openFileChooser(Window window, String initDirPath) {
        return model.openFileChooser(window, initDirPath).getPath();
    }

    @Override
    public String getEncryptedDir() {
        return model.getEncryptedDir().getPath();
    }
}
