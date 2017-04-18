package me.li2.android.tutorial.Picasso.L2ImageDisplaying;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;
import me.li2.android.tutorial.R;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGD;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 18/04/2017.
 * https://github.com/li2
 */

public class ImageDisplaying extends BasicFragmentContainerActivity {
    private static final String TAG = makeLogTag(ImageDisplaying.class);

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GALLERY_FRAGMENT_TYPE_LIST, GALLERY_FRAGMENT_TYPE_GRID})
    public @interface GalleryFragmentType {}
    private static final int GALLERY_FRAGMENT_TYPE_LIST = 0;
    private static final int GALLERY_FRAGMENT_TYPE_GRID = 1;

    @Override
    protected Fragment createFragment() {
        LOGD(TAG, "Rotate screen to show images with ListView for Portrait, and GridView for Landscape");
        return new GalleryListFragment();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LOGD(TAG, "orientation changed to " + newConfig.orientation);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setGalleryFragmentType(GALLERY_FRAGMENT_TYPE_LIST);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setGalleryFragmentType(GALLERY_FRAGMENT_TYPE_GRID);
        }
    }

    private void setGalleryFragmentType(@GalleryFragmentType int type) {
        switch (type) {
            case GALLERY_FRAGMENT_TYPE_LIST:
                if (!(mFragment instanceof GalleryListFragment)) {
                    LOGD(TAG, "show images with ListView");
                    replaceFragment(new GalleryListFragment());
                }
                break;

            case GALLERY_FRAGMENT_TYPE_GRID:
                if (!(mFragment instanceof GalleryGridFragment)) {
                    LOGD(TAG, "show images with GridView");
                    replaceFragment(new GalleryGridFragment());
                }
                break;
        }
    }

    /**
     * Gallery List Fragment
     */
    public static class GalleryListFragment extends Fragment {
        @BindView(R.id.gallery_listView) ListView mListView;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_gallery_list, container, false);
            ButterKnife.bind(this, view);
            mListView.setAdapter(new GalleryAdapter(getActivity(), ImagesData.URLS));
            return view;
        }
    }

    /**
     * Gallery Grid Fragment
     */
    public static class GalleryGridFragment extends Fragment {
        @BindView(R.id.gallery_gridView) GridView mGridView;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_gallery_grid, container, false);
            ButterKnife.bind(this, view);
            mGridView.setAdapter(new GalleryAdapter(getActivity(), ImagesData.URLS));
            return view;
        }
    }

    /**
     * Gallery Adapter
     */
    private static class GalleryAdapter extends ArrayAdapter {
        private Context mContext;
        private String[] mUrls;

        public GalleryAdapter(Context context, String[] urls) {
            super(context, 0);
            mContext = context;
            mUrls = urls;
        }

        @Override
        public int getCount() {
            if (mUrls != null) {
                return mUrls.length;
            }
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_gallery_item, parent, false);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.gallery_item_imageView);

            Picasso
                    .with(mContext)
                    .load(mUrls[position])
                    .into(imageView);

            return convertView;
        }
    }
}
