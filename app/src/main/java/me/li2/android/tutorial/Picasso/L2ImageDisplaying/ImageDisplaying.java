package me.li2.android.tutorial.Picasso.L2ImageDisplaying;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;
import me.li2.android.tutorial.R;

/**
 * Created by weiyi on 18/04/2017.
 * https://github.com/li2
 */

public class ImageDisplaying extends BasicFragmentContainerActivity {
    @Override
    protected Fragment createFragment() {
        return new GalleryListFragment();
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
