package me.li2.android.tutorial.BasicUtils.StorageUtils;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.provider.DocumentFile;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;

import me.li2.android.tutorial.BasicUI.SimpleListActivity;

import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 26/05/2017.
 * https://github.com/li2
 */

public class StorageTutorial extends SimpleListActivity {
    private static final String TAG = makeLogTag(StorageTutorial.class);
    private static final int REQUEST_CODE_FOR_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_TO_OPEN_DOCUMENT_TREE = 2;

    @Override
    protected ArrayList<String> initListData() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Open App Settings Page");
        options.add("Check if has permission");
        options.add("Request permission");
        options.add("getExternalStoragePath(context, removable=false)");
        options.add("getExternalStoragePath(context, removable=true)");
        options.add("getExternalStoragePublicDirectory(type)");
        options.add("getSecondaryExternalStorageDirectory()");
        options.add("Storage Access Framework");
        return options;
    }

    @Override
    protected void onSimpleListItemClick(int position) {
        switch (position) {
            case 0:
                openAppSettingsPage();
                break;

            case 1:
                toastResult(StorageUtils.hasPermission(this) ? "already has permission" : "don't have permission");
                break;

            case 2:
                if (!StorageUtils.hasPermission(this)) {
                    StorageUtils.requestPermission(this, REQUEST_CODE_FOR_STORAGE_PERMISSION);
                } else {
                    toastResult("already has permission");
                }
                break;

            case 3:
                toastResult(StorageUtils.getExternalStoragePath(this, false));
                break;

            case 4:
                toastResult(StorageUtils.getExternalStoragePath(this, true));
                break;

            case 5:
                toastResult(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
                break;

            case 6:
                toastResult(StorageUtils.getSecondaryExternalStorageDirectory());
                break;

            case 7:
                startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), REQUEST_CODE_TO_OPEN_DOCUMENT_TREE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_FOR_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toastResult("permission was granted, yay !");
            } else {
                toastResult("permission denied, boo !");
                StorageUtils.showRequestPermissionRationale(this);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TO_OPEN_DOCUMENT_TREE && resultCode == RESULT_OK) {
            Uri treeUri = data.getData();
            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, treeUri);
            // Grant permission
            grantUriPermission(
                    getPackageName(),
                    treeUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            );

            // Once taken, the permission grant will be remembered across device reboots.
            getContentResolver().takePersistableUriPermission(
                    treeUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            );

            // store the Uri so that can retrieve it on application startup, thus no need to ask again.

            // do anything you want with the files and directories the Uri includes.
            //pickedDir.createDirectory("li2");
            toastResult(pickedDir.getName() + " selected");
        }
    }

    private void openAppSettingsPage() {
        // there is no intent to go directly to the Permissions screen,
        // you must open the application Settings page instead, then click the Permissions item.
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void toastResult(String result) {
        if (TextUtils.isEmpty(result)) {
            result = "null";
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}
