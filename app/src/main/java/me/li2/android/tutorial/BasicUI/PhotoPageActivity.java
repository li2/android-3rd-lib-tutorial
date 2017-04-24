package me.li2.android.tutorial.BasicUI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 22/04/2017.
 * https://github.com/li2
 */

public class PhotoPageActivity extends BasicFragmentContainerActivity{
    private static final String TAG = makeLogTag(PhotoPageActivity.class);

    private static final String EXTRA_KEY_PHOTO_LOAD_FROM_PATH = "extra_key_photo_load_from_path";
    private static final String EXTRA_KEY_PHOTO_LOAD_FROM_URI = "extra_key_photo_load_from_uri";
    private static final String EXTRA_KEY_PHOTO_LOAD_FROM_IMAGE_FILE_PATH = "extra_key_photo_load_from_image_file_path";
    private static final String EXTRA_KEY_PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID = "extra_key_photo_load_from_drawable_resource_id";

    public String mPhotoPath;
    public Uri mPhotoUri;
    public String mPhotoFilePath;
    public int mPhotoResourceId;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PHOTO_LOAD_FROM_PATH, PHOTO_LOAD_FROM_URI, PHOTO_LOAD_FROM_IMAGE_FILE, PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID})
    public @interface PhotoLoadFrom {}
    public static final int PHOTO_LOAD_FROM_PATH = 1;
    public static final int PHOTO_LOAD_FROM_URI = 2;
    public static final int PHOTO_LOAD_FROM_IMAGE_FILE = 3;
    public static final int PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID = 4;


    public static Intent newIntentToLoadPath(Context packageContent, String path) {
        Intent intent = new Intent(packageContent, PhotoPageActivity.class);
        intent.putExtra(EXTRA_KEY_PHOTO_LOAD_FROM_PATH, path);
        return intent;
    }

    public static Intent newIntentToLoadUri(Context packageContent, Uri uri) {
        Intent intent = new Intent(packageContent, PhotoPageActivity.class);
        intent.putExtra(EXTRA_KEY_PHOTO_LOAD_FROM_URI, uri.toString());
        return intent;
    }

    public static Intent newIntentToLoadFile(Context packageContent, String filePath) {
        Intent intent = new Intent(packageContent, PhotoPageActivity.class);
        intent.putExtra(EXTRA_KEY_PHOTO_LOAD_FROM_IMAGE_FILE_PATH, filePath);
        return intent;
    }

    public static Intent newIntentToLoadResource(Context packageContent, int resourceId) {
        Intent intent = new Intent(packageContent, PhotoPageActivity.class);
        intent.putExtra(EXTRA_KEY_PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID, resourceId);
        return intent;
    }


    @Override
    protected void parseIntent() {
        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(EXTRA_KEY_PHOTO_LOAD_FROM_PATH)) {
                mPhotoPath = intent.getStringExtra(EXTRA_KEY_PHOTO_LOAD_FROM_PATH);
            }

            if (intent.hasExtra(EXTRA_KEY_PHOTO_LOAD_FROM_URI)) {
                mPhotoUri = Uri.parse(intent.getStringExtra(EXTRA_KEY_PHOTO_LOAD_FROM_URI));
            }

            if (intent.hasExtra(EXTRA_KEY_PHOTO_LOAD_FROM_IMAGE_FILE_PATH)) {
                mPhotoFilePath = intent.getStringExtra(EXTRA_KEY_PHOTO_LOAD_FROM_IMAGE_FILE_PATH);
            }

            if (intent.hasExtra(EXTRA_KEY_PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID)) {
                mPhotoResourceId = intent.getIntExtra(EXTRA_KEY_PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID, 0);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set StatusBar background color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));
        }

        // set ActionBar background color
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.black));
    }

    @Override
    protected Fragment createFragment() {
        Fragment fragment = new PhotoPageFragment();
        if (mPhotoPath != null) {
            fragment = PhotoPageFragment.newPathInstance(mPhotoPath);
        } else if (mPhotoUri != null) {
            fragment = PhotoPageFragment.newUriInstance(mPhotoUri);
        } else if (mPhotoFilePath != null) {
            fragment = PhotoPageFragment.newFileInstance(mPhotoFilePath);
        } else if (mPhotoResourceId > 0) {
            fragment = PhotoPageFragment.newResourceInstance(mPhotoResourceId);
        }

        return fragment;
    }
}
