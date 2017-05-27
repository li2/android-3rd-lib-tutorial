package me.li2.android.tutorial.BasicUtils.StorageUtils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGD;
import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 *
 * Internal storage is private to this App: /data/user/0/me.li2.android.tutorial/files/
 *
 * Created by weiyi on 25/04/2017.
 * https://github.com/li2
 */

public class InternalStorage implements StorageInterface {
    private static final String TAG = makeLogTag(InternalStorage.class);
    private Context mAppContext;

    public InternalStorage(Context context) {
        mAppContext = context.getApplicationContext();
    }

    @Override
    public File createFile(String directoryName, String fileName) {
        try {
            File dir = new File(buildPath(directoryName, ""));
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            LOGD(TAG, "file " + fileName + " path " + file.getPath());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            LOGE(TAG, "error to create file :( " + e.getMessage());
            return null;
        }
    }

    @Override
    public File createFile(String fileName) {
        return createFile("", fileName);
    }

    @Override
    public boolean isFileExist(String directoryName, String fileName) {
        File file = new File(buildPath(directoryName, fileName));
        return file.exists();
    }

    @Override
    public boolean isFileExist(String fileName) {
        return isFileExist("", fileName);
    }

    @Override
    public File getFile(String directoryName, String fileName) {
        return null;
    }

    @Override
    public File getFile(String name) {
        return null;
    }

    @Override
    public String readTextFile(String directoryName, String fileName) {
        String ret = "";
        try {
            FileInputStream inputStream = new FileInputStream(new File(buildPath(directoryName, fileName)));

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            LOGE(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            LOGE(TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }

    @Override
    public String readTextFile(String fileName) {
        return readTextFile("", fileName);
    }

    @Override
    public void writeFile(String directoryName, String fileName, String content) {
        try {
            byte[] bytes = content.getBytes();
            FileOutputStream out = new FileOutputStream(new File(buildPath(directoryName, fileName)));
            out.write(bytes);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeFile(String fileName, String content) {
        writeFile("", fileName, content);
    }

    @Override
    public void copyRawToFile(int rawId, String directoryName, String fileName) {
        InputStream in = mAppContext.getResources().openRawResource(rawId);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(buildPath(directoryName, fileName));
            byte[] buff = new byte[1024];
            int read = 0;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void copyRawToFile(int rawId, String fileName) {
        copyRawToFile(rawId, "", fileName);
    }

    private String buildPath(String directoryName, String fileName) {
        if (directoryName == null || directoryName.isEmpty()) {
            return buildAbsolutePath() + "/" + fileName;
        } else {
            return buildAbsolutePath() + "/" + directoryName + "/" + fileName;
        }
    }

    private String buildAbsolutePath() {
        return mAppContext.getFilesDir().getPath();
    }
}
