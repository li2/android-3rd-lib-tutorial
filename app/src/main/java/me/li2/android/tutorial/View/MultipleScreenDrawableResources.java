package me.li2.android.tutorial.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;
import me.li2.android.tutorial.R;
import me.li2.android.tutorial.StorageUtils.ResourceUtils;

/**
 * Created by weiyi on 10/04/2017.
 * https://github.com/li2
 */

public class MultipleScreenDrawableResources extends BasicFragmentContainerActivity {
    @Override
    protected String getTitlePrefix() {
        return ViewTutorial.TAG;
    }

    @Override
    protected Fragment createFragment() {
        return new DrawableResourcesFragment();
    }

    @SuppressWarnings("validFragment")
    public static class DrawableResourcesFragment extends Fragment {
        @BindView(R.id.multiple_screens_density_info_view)
        TextView mDensityInfoView;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view  = inflater.inflate(R.layout.fragment_multiple_screens_drawable_resources, container, false);
            ButterKnife.bind(this, view);
            mDensityInfoView.setText(ResourceUtils.getScreenInfo(getActivity()));
            return view;
        }
    }
}
