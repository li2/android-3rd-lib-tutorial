package me.li2.android.tutorial.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;
import me.li2.android.tutorial.R;
import me.li2.android.tutorial.StorageUtils.ResourceUtils;

/**
 * Created by weiyi on 28/04/2017.
 * https://github.com/li2
 */

public class ImageViewScaleType extends BasicFragmentContainerActivity {
    private static final int IMAGE_HEIGHT_X_1 = 120; // in dp
    private static final int IMAGE_HEIGHT_X_0p5 = 60;
    private static final int IMAGE_HEIGHT_X_1p5 = 180;

    @Override
    protected String getTitlePrefix() {
        return ViewTutorial.TAG;
    }

    @Override
    protected Fragment createFragment() {
        return new ImageViewScaleTypeFragment();
    }

    public void toastScaleType(View view) {
        if (view instanceof ImageView) {
            Toast.makeText(this, "ScaleType is " + ((ImageView) view).getScaleType(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getOptionsMenuRes() {
        return R.menu.image_scaletype_options;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.imageScaleType_menuItem_1_height:
                setImageViewsHeight(IMAGE_HEIGHT_X_1);
                return true;
            case R.id.imageScaleType_menuItem_0p5_height:
                setImageViewsHeight(IMAGE_HEIGHT_X_0p5);
                return true;
            case R.id.imageScaleType_menuItem_1p5_height:
                setImageViewsHeight(IMAGE_HEIGHT_X_1p5);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setImageViewsHeight(int height) {
        if (mFragment != null && mFragment instanceof ImageViewScaleTypeFragment) {
            ((ImageViewScaleTypeFragment) mFragment).setImageViewsHeight(this, height);
        }
    }

    public static class ImageViewScaleTypeFragment extends Fragment {
        private static LinearLayout mLayout;

        public ImageViewScaleTypeFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_image_view_scale_type, container, false);
            mLayout = (LinearLayout) view.findViewById(R.id.imageViewsLayout);
            return view;
        }

        public static void setImageViewsHeight(Context context, int height) { // in dp
            height = ResourceUtils.dpToPixel(context, height);

            for (int i = 0; i < mLayout.getChildCount(); i++) {
                View view = mLayout.getChildAt(i);
                
                if (view instanceof  ImageView) {
                    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, height); // in pixel
                    int margin = ResourceUtils.dpToPixel(context, 4);
                    lp.setMargins(margin, margin, margin, margin);
                    view.setLayoutParams(lp);
                }
            }
        }
    }
}
