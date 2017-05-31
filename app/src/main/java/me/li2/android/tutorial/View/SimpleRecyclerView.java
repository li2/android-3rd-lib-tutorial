package me.li2.android.tutorial.View;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;
import me.li2.android.tutorial.BasicWidget.SimpleRecyclerView.SimpleRecyclerFragment;
import me.li2.android.tutorial.R;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGD;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 17/05/2017.
 * https://github.com/li2
 */

public class SimpleRecyclerView extends BasicFragmentContainerActivity {
    private static final String TAG = makeLogTag(SimpleRecyclerView.class);

    @Override
    protected String getTitlePrefix() {
        return ViewTutorial.TAG;
    }

    @Override
    protected Fragment createFragment() {
        return new SimpleRecyclerFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFragment != null && mFragment instanceof SimpleRecyclerFragment) {
            ((SimpleRecyclerFragment) mFragment).setOnItemClickListener(new SimpleRecyclerFragment.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    LOGD(TAG, "onItemClick " + position);
                }
            });
        }
    }

    @Override
    protected int getOptionsMenuRes() {
        return R.menu.layout_type_options;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.layoutType_menuItem_listView_horizontal:
                setRecyclerViewLayout(SimpleRecyclerFragment.LayoutType.LINEAR_HORIZONTAL);
                return true;

            case R.id.layoutType_menuItem_listView_vertical:
                setRecyclerViewLayout(SimpleRecyclerFragment.LayoutType.LINEAR_VERTICAL);
                return true;

            case R.id.layoutType_menuItem_gridView:
                setRecyclerViewLayout(SimpleRecyclerFragment.LayoutType.GRID);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setRecyclerViewLayout(SimpleRecyclerFragment.LayoutType layoutType) {
        if (mFragment != null && mFragment instanceof SimpleRecyclerFragment) {
            ((SimpleRecyclerFragment) mFragment).setLayout(layoutType);
        }
    }
}
