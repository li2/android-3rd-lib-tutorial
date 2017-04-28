package me.li2.android.tutorial.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;
import me.li2.android.tutorial.BasicUtils.ViewUtils;
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
            ((ImageViewScaleTypeFragment) mFragment).setImageViewsHeight(height);
            String info = "Original size 120 x 120dp\nImageView match_parent x " + height + "dp";
            ((ImageViewScaleTypeFragment) mFragment).setInfo(info);
        }
    }

    public static class ImageViewScaleTypeFragment extends Fragment {
        @BindView(R.id.imageScaleType_layoutView)
        View mView;

        @BindView(R.id.imageScaleType_infoView)
        TextView mInfoView;

        public ImageViewScaleTypeFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_image_view_scale_type, container, false);
            ButterKnife.bind(this, view);
            return view;
        }

        public void setImageViewsHeight(int height) {
            List<View> views = ViewUtils.getAllChildrenBFS(mView);

            for (int i = 0; i < views.size(); i++) {
                View view = views.get(i);
                if (view instanceof  ImageView) {
                    view.setLayoutParams(buildLayoutParams(height));
                }
            }
        }

        private LayoutParams buildLayoutParams(int height) { // in dp
            height = ResourceUtils.dpToPixel(getContext(), height);

            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, height); // in pixel
            int margin = ResourceUtils.dpToPixel(getContext(), 4);
            lp.setMargins(margin, margin, margin, margin);
            return lp;
        }

        public void setInfo(String info) {
            mInfoView.setText(info);
        }
    }
}
