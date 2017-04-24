package me.li2.android.tutorial.Gson.ChangeSettingsAccess;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import me.li2.android.tutorial.BasicUI.BasicFragmentContainerActivity;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGD;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 24/04/2017.
 * https://github.com/li2
 */

public class ChangeSettingsAccessActivity extends BasicFragmentContainerActivity {
    private static final String TAG = makeLogTag(ChangeSettingsAccessActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsAccessProvider provider = new SettingsAccessProvider(this);
        LOGD(TAG, "items " + provider.getItems());
    }

    @Override
    protected Fragment createFragment() {
        return null;
    }
}
