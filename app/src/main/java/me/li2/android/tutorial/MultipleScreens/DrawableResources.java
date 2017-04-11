package me.li2.android.tutorial.MultipleScreens;

import android.widget.TextView;

import butterknife.BindView;
import me.li2.android.tutorial.BasicUI.BasicActivity;
import me.li2.android.tutorial.R;
import me.li2.android.tutorial.StorageUtils.ResourceUtils;

/**
 * Created by weiyi on 10/04/2017.
 * https://github.com/li2
 */

public class DrawableResources extends BasicActivity {
    @BindView(R.id.multiple_screens_density_info_view)
    TextView mDensityInfoView;

    @Override
    protected String getTitlePrefix() {
        return MultipleScreensTutorial.TAG;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_multiple_screens_drawable_resources;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateScreenInfo();
    }

    private void updateScreenInfo() {
        mDensityInfoView.setText(ResourceUtils.getScreenInfo(this));
    }
}
