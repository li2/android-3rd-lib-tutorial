package me.li2.android.tutorial.BasicUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import me.li2.android.tutorial.R;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 */

public class SimpleListActivity extends BasicFragmentContainerActivity {

    private SimpleListFragment mListFragment;

    protected void onSimpleListItemClick(final int position) {
    }

    protected void setListData(List<String> titles) {
        if (mListFragment != null) {
            mListFragment.setListData(titles);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        mListFragment = (SimpleListFragment) fm.findFragmentById(R.id.fragmentContainer);
        if (mListFragment != null) {
            mListFragment.setOnSimpleListItemClickListener(new SimpleListFragment.OnSimpleListItemClickListener() {
                @Override
                public void onListItemClick(int position) {
                    onSimpleListItemClick(position);
                }
            });
        }
    }

    @Override
    protected Fragment createFragment() {
        return new SimpleListFragment();
    }
}
