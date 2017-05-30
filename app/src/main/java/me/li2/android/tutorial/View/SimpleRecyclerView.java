package me.li2.android.tutorial.View;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;
import me.li2.android.tutorial.BasicWidget.SimpleRecyclerView.SimpleRecyclerFragment;
import me.li2.android.tutorial.R;

/**
 * Created by weiyi on 17/05/2017.
 * https://github.com/li2
 */

public class SimpleRecyclerView extends BasicFragmentContainerActivity {
    private SimpleRecyclerFragment mRecyclerFragment;

    @Override
    protected String getTitlePrefix() {
        return ViewTutorial.TAG;
    }

    @Override
    protected Fragment createFragment() {
        mRecyclerFragment = new SimpleRecyclerFragment();
        return mRecyclerFragment;
    }

    @Override
    protected int getOptionsMenuRes() {
        return R.menu.layout_type_options;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.layoutType_menuItem_listView_horizontal:
                mRecyclerFragment.setLayout(SimpleRecyclerFragment.LayoutType.LINEAR_HORIZONTAL);
                return true;

            case R.id.layoutType_menuItem_listView_vertical:
                mRecyclerFragment.setLayout(SimpleRecyclerFragment.LayoutType.LINEAR_VERTICAL);
                return true;

            case R.id.layoutType_menuItem_gridView:
                mRecyclerFragment.setLayout(SimpleRecyclerFragment.LayoutType.GRID);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
