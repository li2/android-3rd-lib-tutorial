package me.li2.android.tutorial.Gson.ChangeSettingsAccess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import java.util.ArrayList;

import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;

import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 24/04/2017.
 * https://github.com/li2
 */

public class ChangeSettingsAccessActivity extends BasicFragmentContainerActivity {
    private static final String TAG = makeLogTag(ChangeSettingsAccessActivity.class);

    private SettingsAccessProvider mDataProvider;
    private ArrayList<SettingsAccessItem> mRootItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataProvider = new SettingsAccessProvider(this);
        mRootItems = mDataProvider.getRootItems();
        updateItems(mRootItems);
    }

    @Override
    protected Fragment createFragment() {
        ChangeSettingsAccessFragment fragment = new ChangeSettingsAccessFragment();
        fragment.setOnSettingsAccessItemClickListener(mOnSettingsAccessItemClickListener);
        return fragment;
    }


    private ChangeSettingsAccessFragment.OnSettingsAccessItemClickListener mOnSettingsAccessItemClickListener =
            new ChangeSettingsAccessFragment.OnSettingsAccessItemClickListener() {
                @Override
                public void onSettingsAccessItemClick(SettingsAccessItem item) {
                    updateItems(item.subItems);
                }
            };

    private void updateItems(ArrayList<SettingsAccessItem> items) {
        if (mFragment != null && mFragment instanceof ChangeSettingsAccessFragment) {
            ((ChangeSettingsAccessFragment) mFragment).setItems(items);
        }
    }
}
