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

public abstract class SimpleListActivity extends BasicActivity {

    private SimpleListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.simple_list_fragment_container_id);

        if (fragment == null) {
            fragment = new SimpleListFragment();
            fm.beginTransaction().add(R.id.simple_list_fragment_container_id, fragment).commit();
        }

        mFragment = (SimpleListFragment) fragment;
        mFragment.setOnSimpleListItemClickListener(new SimpleListFragment.OnSimpleListItemClickListener() {
            @Override
            public void onListItemClick(int position) {
                onSimpleListItemClick(position);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_list;
    }

    protected void onSimpleListItemClick(final int position) {
    }

    public void setListData(List<String> titles) {
        if (mFragment != null) {
            mFragment.setListData(titles);
        }
    }
}
