package me.li2.android.tutorial.Picasso.L1ImageLoading;

import android.content.Intent;
import android.net.Uri;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.ArrayList;

import me.li2.android.tutorial.BasicUI.SimpleListActivity;
import me.li2.android.tutorial.BasicUtils.ResourceUtils;
import me.li2.android.tutorial.Picasso.PhotoPageActivity;
import me.li2.android.tutorial.Picasso.PicassoTutorial;
import me.li2.android.tutorial.R;

/**
 * Created by weiyi on 09/04/2017.
 * https://github.com/li2
 */

public class ImageLoading extends SimpleListActivity {
    @Override
    protected String getTitlePrefix() {
        return PicassoTutorial.TAG;
    }

    @Override
    protected ArrayList<String> initListData() {
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
