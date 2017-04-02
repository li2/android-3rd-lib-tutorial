package me.li2.android.tutorial.StorageUtils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import static me.li2.android.tutorial.LogHelper.LOGE;
import static me.li2.android.tutorial.LogHelper.makeLogTag;

/**
 * Created by weiyi on 02/04/2017.
 * https://github.com/li2
 */

public class StorageUtils {
    private static final String TAG = makeLogTag(StorageUtils.class);

    private static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String[] PERMISSIONS_STORAGE = {PERMISSION_WRITE_EXTERNAL_STORAGE};

    public static boolean hasPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, PERMISSION_WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission (Activity activity, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION_WRITE_EXTERNAL_STORAGE)) {
            Log.d(TAG, "Show an explanation to the user *asynchronously*");
        } else {
            Log.d(TAG, "No explanation needed, we can request the permission.");
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, requestCode);
        }
    }

    public static File createExternalFile(String folderName, String fileName) {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folderName;

            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            LOGE(TAG, "error to create file :( " + e.getMessage());
            return null;
        }
    }
}
