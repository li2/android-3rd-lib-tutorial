package me.li2.android.tutorial.BasicUtils.StorageUtils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

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
        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, requestCode);
    }

    public static void showRequestPermissionRationale(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION_WRITE_EXTERNAL_STORAGE)) {
            Log.d(TAG, "Show an explanation to the user *asynchronously*");
            new AlertDialog.Builder(activity)
                    .setTitle("Why we request storage permission?")
                    .setMessage("Because we are testing external storage.")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            Log.d(TAG, "No explanation needed, user might checked the Never ask again box");
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

    /**
     * Get secondary external storage path.
     * <p>
     * NOT work on Android 7.0, the real path is /storage/FA14-53DC, but this method return /mnt/secure/asec,
     * which doesn't exist.
     * <p>
     * System.getenv("SECONDARY_STORAGE") this method only works below Android 6.0, otherwise it returns null.
     * @return
     */
    public static String getSecondaryExternalStorageDirectory() {
        final String MOUNTS_FILE_PATH = "/proc/mounts";
        final String VOLD_FILE_PATH = "/dev/block/vold/";
        final String SAMSUNG_VOLD_FILE_PATH = "/dev/block/vold/public";
        final String SECONDARY_SDCARD_ID = "sd"; // depends on different phone or tablet
        final String SAMSUNG_SECONDARY_SDCARD_ID = "/mnt/media_rw/";//"/storage/0000-006F"; // can't find a way to get Samsung secondary SDCARD path, hard coded temperately
        final String ULMO_USB_ID = "/mnt/usbhost";//"/storage/0000-006F"; // can't find a way to get Samsung secondary SDCARD path, hard coded temperately

        try {
            File mountFile = new File(MOUNTS_FILE_PATH);
            if (mountFile.exists()) {
                Scanner scanner = new Scanner(mountFile);
                final String internalSDCard = Environment.getExternalStorageDirectory().getAbsolutePath();

                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    Log.d(TAG, line);
                    if (line.startsWith(VOLD_FILE_PATH)) {
                        if (line.toLowerCase().indexOf(SAMSUNG_SECONDARY_SDCARD_ID) != -1) {// check Samsung first
                            String[] elements = line.split(" ");
                            if (elements.length >= 2) {
                                if (!line.startsWith(SAMSUNG_VOLD_FILE_PATH)) {// not samsung KNOX device, check CalAmp or generic Samsung devices
                                    if (!elements[1].toLowerCase().contains("extsd") && !elements[1].toLowerCase().contains("usbhost"))
                                        continue;
                                }
                                String secondarySDCardPath = elements[1].replace(SAMSUNG_SECONDARY_SDCARD_ID, "/storage/");
                                ;
                                scanner.close();
                                return secondarySDCardPath;
                            }
                        } else if (line.toLowerCase().indexOf(ULMO_USB_ID) != -1) {// check Ulmo Pro USB support first
                            String[] elements = line.split(" ");
                            if (elements.length >= 2) {
                                String secondarySDCardPath = elements[1];
                                scanner.close();
                                return secondarySDCardPath;
                            }
                        } else if (line.toLowerCase().indexOf(SECONDARY_SDCARD_ID) != -1) {
                            String[] elements = line.split(" ");
                            if ((elements.length >= 2) && !elements[1].equals(internalSDCard)) {
                                scanner.close();
                                return elements[1];
                            }
                        }
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            return "";
        }

        return "";
    }

    /**
     * Get external storage path.
     * @param mContext
     * @param is_removable false to get primary external storage (also named built-in external storage);
     *                     true to get secondary external storage;
     * @return
     */
    public static String getExternalStoragePath(Context mContext, boolean is_removable) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removable == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
