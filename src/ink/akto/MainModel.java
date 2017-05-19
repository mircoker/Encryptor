package ink.akto;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Created by Ruben on 02.05.2017.
 */
public class MainModel implements Contracts.EncryptorModel
{
    private FileChooser fileChooser;

    private static final int BUFFER_SIZE = 1024 * 4;
    private File rootDir;
    private File encryptedDir;
    private String encryptedDirName = "зашифровано";
    private String decryptedDirName = "расшифровано";
    private String encryptedPrefix = encryptedDirName+"_";

    public MainModel()
    {
        fileChooser = new FileChooser();

        String rootPath = "rootPath";
        try {
            rootPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        rootDir = new File(rootPath.replace(new File(rootPath).getName(), ""));
        encryptedDir = new File(rootDir, encryptedDirName);
    }

    @Override
    public void saveEncrypted(String name, byte[] bytes) throws IOException
    {
        encryptedDir = createDirIfNotExist(new File(rootDir, encryptedDirName));
        File encodedFile = new File(encryptedDir, encryptedPrefix + name);

        FileOutputStream fileOutputStream = new FileOutputStream(encodedFile);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }

    @Override
    public void saveDecrypted(String name, byte[] bytes) throws IOException
    {
        File outDir = createDirIfNotExist(new File(rootDir, decryptedDirName));
        File encodedFile = new File(outDir, name.replace(encryptedPrefix, ""));

        FileOutputStream fileOutputStream = new FileOutputStream(encodedFile);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }

    @Override
    public byte[] getBytes(String file) throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] originalBytes = toByteArray(fileInputStream, BUFFER_SIZE);
        fileInputStream.close();
        return originalBytes;
    }

    @Override
    public File openFileChooser(@NotNull Window window, @Nullable String initDirPath)
    {
        fileChooser.setInitialDirectory(initDirPath==null? null: new File(initDirPath));
        return fileChooser.showOpenDialog(window);
    }

    @Override
    @Nullable
    public File getEncryptedDir()
    {
        return encryptedDir.exists() ? encryptedDir : null;
    }

    private byte[] toByteArray(InputStream is, int bufferSize) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[bufferSize];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        } finally {
            output.close();
        }
    }

    private File createDirIfNotExist(File dir) throws IOException
    {
        if(!dir.exists())
        {
            if(!dir.mkdir()) throw new IOException("Cant create out directory.\n"+dir);
        }
        return dir;
    }
}
