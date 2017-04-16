package me.li2.android.tutorial.Picasso.L1ImageLoading;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import me.li2.android.tutorial.BasicUI.SimpleListActivity;
import me.li2.android.tutorial.Picasso.PicassoTutorial;
import me.li2.android.tutorial.R;
import me.li2.android.tutorial.StorageUtils.ResourceUtils;

/**
 * Created by weiyi on 09/04/2017.
 * https://github.com/li2
 */

public class ImageLoading extends SimpleListActivity {

    private ArrayList<String> mLoadingOptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingOptions = options();
        /**
         * It's weird if not postDelay, the {@link SimpleListFragment#getActivity()} always return null.
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setListData(mLoadingOptions);
            }
        }, 250);
    }

    @Override
    protected String getTitlePrefix() {
        return PicassoTutorial.TAG;
    }

    private ArrayList<String> options() {
        ArrayList<String> options = new ArrayList<>();
        options.add("From Url");
        options.add("From Resources");
        options.add("From File");
        options.add("From Uri");
        return options;
    }

    @Override
    protected void onSimpleListItemClick(int position) {
        switch (position) {
            case 0:
                loadImageFromURL(URL);
                break;
            case 1:
                loadImageFromResources(imageResourceId);
                break;
            case 2:
                chooseFile();
                break;
            case 3:
                Uri uri = ResourceUtils.resourceIdToUri(this, imageResourceId);
                loadImageFromUri(uri);
                break;
        }
    }

    private static final String URL = "http://i.imgur.com/9gbQ7YR.jpg";

    /**
     * Start an image request using the specified path.
     * <p>
     * This path may be a remote URL, file resource (prefixed with {@code file:}), content resource
     * (prefixed with {@code content:}), or android resource (prefixed with {@code
     * android.resource:}.
     * <p>
     * @param url
     */
    private void loadImageFromURL(String url) {
        Toast.makeText(ImageLoading.this, "Loading Image from URL: " + url, Toast.LENGTH_SHORT).show();
        Picasso.with(this)
                .load(url)
                .into(showImageView());
    }

    private int imageResourceId = R.drawable.multiple_screens_image1_full_dpi;
    
    /**
     * Start an image request using the specified drawable resource ID.
     */
    private void loadImageFromResources(int resourceId) {
        Toast.makeText(ImageLoading.this, "Loading Image from resource ID : " + resourceId, Toast.LENGTH_SHORT).show();
        Picasso.with(this)
                .load(resourceId)
                .into(showImageView());
    }

    /**
     * Start an image request using the specified image file.
     */
    private void loadImageFromFile(File file) {
        Toast.makeText(ImageLoading.this, "Loading Image from File: " + file.getPath(), Toast.LENGTH_SHORT).show();
        Picasso.with(this)
                .load(file)
                .into(showImageView());
    }

    /**
     * Start an image request using the specified URI.
     * @param uri
     */
    private void loadImageFromUri(Uri uri) {
        Toast.makeText(ImageLoading.this, "Loading Image from Uri: " + uri.getPath(), Toast.LENGTH_SHORT).show();
        Picasso.with(this)
                .load(uri)
                .into(showImageView());
    }

    /**
     * Popup ImageView, give its constructor a non-dialog theme can show ImageView in full screen. and
     * android.R.style.ThemeOverlay_Material_Dark will not change the statusBar's color.
     * http://stackoverflow.com/a/24946375/2722270
     * http://stackoverflow.com/a/10173576/2722270
     */
    private ImageView showImageView() {
        Dialog builder = new Dialog(this, android.R.style.Theme_Material_Light_DarkActionBar);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.BLACK));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
        return imageView;
    }

    private static final int REQUEST_FILE = 21;

    private void chooseFile() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(REQUEST_FILE)
                .withFilterDirectories(false)
                .withTitle("Choose an image file")
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FILE && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(filePath);
            loadImageFromFile(file);
        }
    }
}
