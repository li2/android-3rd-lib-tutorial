package me.li2.android.tutorial.Picasso.L2ImageDisplaying;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import me.li2.android.tutorial.BasicUI.MainApplication;
import me.li2.android.tutorial.Picasso.PhotoPageActivity;
import me.li2.android.tutorial.Picasso.PicassoTutorial;
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
    protected String getTitlePrefix() {
        return PicassoTutorial.TAG;
    }

    @Override
    protected int getOptionsMenuRes() {
        return R.menu.layout_type_options;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.layoutType_menuItem_listView_vertical:
                setGalleryFragmentType(GALLERY_FRAGMENT_TYPE_LIST);
                return true;

            case R.id.layoutType_menuItem_gridView:
                setGalleryFragmentType(GALLERY_FRAGMENT_TYPE_GRID);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Fragment createFragment() {
        return new GalleryListFragment();
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
            mListView.setOnScrollListener(new SampleScrollListener(getContext()));
            mListView.setOnItemClickListener(mOnItemClickListener);
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
            mGridView.setOnScrollListener(new SampleScrollListener(getContext()));
            mGridView.setOnItemClickListener(mOnItemClickListener);
            return view;
        }
    }

    private static AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String url = ImagesData.URLS[position];
            if (!TextUtils.isEmpty(url)) {
                Intent intent = PhotoPageActivity.newIntentToLoadPath(MainApplication.getAppContext(), url);
                view.getContext().startActivity(intent);
            }
        }
    };

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

            // Cache Indicators
            Picasso.with(context).setIndicatorsEnabled(true);

            // Logging
            Picasso.with(context).setLoggingEnabled(true);

            // Snapshot
            LOGD(TAG, Picasso.with(context).getSnapshot().toString());
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

            // catch the case that the passed image URL is either null or an empty string
            if (TextUtils.isEmpty(mUrls[position])) {
                // option 1: cancel Picasso request and clear ImageView
                Picasso.with(mContext)
                        .cancelRequest(imageView);
                // this avoids the situation where an incorrect image from a previous ListView item gets displayed.
                // however, it looks weird in the UI, since ImageView might still occupy the space in the UI.
                imageView.setImageDrawable(null);

                // option 2: load placeholder
                Picasso.with(mContext)
                        .load(R.drawable.ic_image_broken)
                        .into(imageView);

            } else {
                Picasso.with(mContext)
                        .load(mUrls[position])
                        .placeholder(R.drawable.ic_android)
                        .tag(this)
                        .error(R.drawable.ic_image_broken)
                        .into(imageView);
            }

            return convertView;
        }
    }
}
