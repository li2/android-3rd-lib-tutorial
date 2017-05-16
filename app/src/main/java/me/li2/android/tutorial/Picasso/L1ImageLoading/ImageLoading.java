package me.li2.android.tutorial.Picasso.L1ImageLoading;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.ArrayList;

import me.li2.android.tutorial.Picasso.PhotoPageActivity;
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
        mLoadingOptions = buildOptions();
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

    private ArrayList<String> buildOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add("From Url");
        options.add("From Resources");
        options.add("From File");
        options.add("From Uri");
        return options;
    }

    @Override
    protected void onSimpleListItemClick(int position) {
        Intent intent = null;

        switch (position) {
            case 0:
                intent = PhotoPageActivity.newIntentToLoadPath(ImageLoading.this, URL);
                break;
            case 1:
                intent = PhotoPageActivity.newIntentToLoadResource(ImageLoading.this, imageResourceId);
                break;
            case 2:
                chooseFile();
                break;
            case 3:
                Uri uri = ResourceUtils.resourceIdToUri(this, imageResourceId);
                intent = PhotoPageActivity.newIntentToLoadUri(ImageLoading.this, uri);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    private static final String URL = "http://i.imgur.com/9gbQ7YR.jpg";
    private static final int imageResourceId = R.drawable.multiple_screens_image1_full_dpi;
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
            startActivity(PhotoPageActivity.newIntentToLoadFile(ImageLoading.this, filePath));
        }
    }
}
