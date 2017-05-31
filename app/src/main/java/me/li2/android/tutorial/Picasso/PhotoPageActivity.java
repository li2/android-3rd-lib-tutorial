package me.li2.android.tutorial.Picasso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;
import me.li2.android.tutorial.BasicWidget.SimpleRecyclerView.SimpleRecyclerFragment;
import me.li2.android.tutorial.Picasso.L2ImageDisplaying.BlurTransformation;
import me.li2.android.tutorial.Picasso.L2ImageDisplaying.GrayscaleTransformation;
import me.li2.android.tutorial.Picasso.L2ImageDisplaying.ImagesData;
import me.li2.android.tutorial.R;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGD;
import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 22/04/2017.
 * https://github.com/li2
 */

public class PhotoPageActivity extends BasicFragmentContainerActivity {
    private static final String TAG = makeLogTag(PhotoPageActivity.class);

    private static final String EXTRA_KEY_PHOTO_LOAD_FROM_PATH = "extra_key_photo_load_from_path";
    private static final String EXTRA_KEY_PHOTO_LOAD_FROM_URI = "extra_key_photo_load_from_uri";
    private static final String EXTRA_KEY_PHOTO_LOAD_FROM_IMAGE_FILE_PATH = "extra_key_photo_load_from_image_file_path";
    private static final String EXTRA_KEY_PHOTO_LOAD_FROM_DRAWABLE_RESOURCE_ID = "extra_key_photo_load_from_drawable_resource_id";

    private PhotoPageFragment mFragment;

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
    protected int getLayoutResId() {
        return R.layout.activity_photo_page;
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
        mFragment = (PhotoPageFragment) fragment;
        return fragment;
    }

    @Override
    protected String getTitlePrefix() {
        return PicassoTutorial.TAG;
    }

    @Override
    protected int getOptionsMenuRes() {
        return R.menu.image_picasso_options;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mPhotoPath == null) {
            menu.clear();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.imagePicasso_menuItem_noPlaceholder:
                testPicassoNoPlaceHolder();
                return true;

            case R.id.imagePicasso_menuItem_get:
                testPicassoGet();
                return true;

            case R.id.imagePicasso_menuItem_target:
                testPicassoTarget();
                return true;

            case R.id.imagePicasso_menuItem_rotate:
                testPicassoRotation();
                return true;

            case R.id.imagePicasso_menuItem_transformBlur:
                testPicassoTransformation(new BlurTransformation(this));
                return true;

            case R.id.imagePicasso_menuItem_transformGrayscale:
                testPicassoTransformation(new GrayscaleTransformation(Picasso.with(this)));
                return true;

            case R.id.imagePicasso_menuItem_transformBlurAndGrayscale:
                List<Transformation> transformations = new ArrayList<>();
                transformations.add(new BlurTransformation(this));
                transformations.add(new GrayscaleTransformation(Picasso.with(this)));
                testPicassoTransformation(transformations);
                return true;

            case R.id.imagePicasso_menuItem_additionalTransformations:
                if (mTransformFragment == null) {
                    addTransformFragment();
                } else if (!mTransformFragment.isVisible()) {
                    mTransformFragment.show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void testPicassoNoPlaceHolder() {
        LOGD(TAG, "test Picasso .noPlaceHolder()");
        Picasso.with(this).invalidate(ImagesData.URLS[0]);
        Picasso.with(this).invalidate(mPhotoPath);

        final ImageView imageView = mFragment.getPhotoView();
        loadImage(ImagesData.URLS[5], imageView, false, new Callback() {
            @Override
            public void onSuccess() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // load the next image into the same ImageView
                        loadImage(mPhotoPath, imageView, true, null);
                    }
                }, 1000);
            }

            @Override
            public void onError() {}
        });
    }

    private void loadImage(String url, ImageView imageView, boolean noPlaceholder, Callback callback) {
        RequestCreator requestCreator = Picasso.with(this).load(url);
        requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE);

        if (noPlaceholder) {
            /** use case: retain the previous image in place until the second one is loaded,
             * it results in a much smoother experience */
            requestCreator.noPlaceholder();
        } else {
            /** use case: displayed until the image is loaded, to avoid empty ImageView */
            requestCreator.placeholder(R.drawable.ic_android);
        }

        /** use case: displayed if the image cannot be loaded */
        requestCreator.error(R.drawable.ic_image_broken);
        requestCreator.into(imageView, callback);
    }

    private void testPicassoGet() {
        LOGD(TAG, "test Picasso .get()");
        mFragment.clearImage();
        Picasso.with(this).invalidate(mPhotoPath);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = Picasso.with(PhotoPageActivity.this)
                            .load(mPhotoPath)
                            .get();
                    mFragment.updateImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void testPicassoTarget() {
        LOGD(TAG, "test Picasso Target Interface");
        mFragment.clearImage();
        Picasso.with(this).invalidate(mPhotoPath);

        Picasso.with(this)
                .load(mPhotoPath)
                .into(mTarget);
    }

    // Important: always declare the target implementation as a field, not anonymously!
    // The garbage collector could otherwise destroy your target object and you'll never get the bitmap.
    private Target mTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            LOGD(TAG, "Picasso loaded from " + from);
            mFragment.updateImage(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            LOGE(TAG, "Picasso loaded failed");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private int mRotateTimes;

    private void testPicassoRotation() {
        LOGD(TAG, "test Picasso Rotation");
        mRotateTimes = (mRotateTimes %4 ==0) ? 0 : mRotateTimes;
        ++mRotateTimes;

        Picasso.with(this)
                .load(mPhotoPath)
                .rotate(mRotateTimes*90)
                .into(mFragment.getPhotoView());
    }

    private void testPicassoTransformation(Transformation transformation) {
        testPicassoTransformation(Arrays.asList(new Transformation[] {transformation}));
    }

    private void testPicassoTransformation(List<Transformation> transformations) {
        LOGD(TAG, "test Picasso Transformation");
        Picasso.with(this)
                .load(mPhotoPath)
                .transform(transformations)
                .into(mFragment.getPhotoView());
    }


    private SimpleRecyclerFragment mTransformFragment;

    private void addTransformFragment() {
        FragmentManager fm = getSupportFragmentManager();
        SimpleRecyclerFragment fragment =
                (SimpleRecyclerFragment) fm.findFragmentById(R.id.transformFragmentContainer);

        if (fragment == null) {
            String[] dataset = new String[] {"CropCircle"};
            fragment = SimpleRecyclerFragment.newInstance(dataset, SimpleRecyclerFragment.LayoutType.LINEAR_HORIZONTAL);
            fm.beginTransaction().add(R.id.transformFragmentContainer, fragment).commit();
            
            fragment.setOnItemClickListener(new SimpleRecyclerFragment.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mTransformFragment.hide();
                    LOGD(TAG, "onItemClick " + position);
                }
            });
        }

        mTransformFragment = fragment;
    }

    private void removeTransformFragment() {
        if (mTransformFragment != null && mTransformFragment.isAdded()) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().remove(mTransformFragment).commit();
        }
    }
}
