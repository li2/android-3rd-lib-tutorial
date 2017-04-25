package me.li2.android.tutorial.Gson.ChangeSettingsAccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

/**
 * Created by weiyi on 24/04/2017.
 * https://github.com/li2
 */

public class SettingsAccessItem {
    private static final String PREF_KEY_PREFIX = "settings_access_provider_";

    private Context mContext;
    private SharedPreferences mPreferences;
    public String title;
    private String mPrefKey;
    private boolean mIsAdminAccessOnly;
    public ArrayList<SettingsAccessItem> subItems = new ArrayList<>();

    public SettingsAccessItem(Context context, String title, String prefKey, boolean defaultAdminAccessValue) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        this.title = title;
        this.mPrefKey = PREF_KEY_PREFIX + prefKey;

        if (!mPreferences.contains(mPrefKey)) {
            mPreferences
                    .edit()
                    .putBoolean(mPrefKey, defaultAdminAccessValue)
                    .apply();
        } else {
            defaultAdminAccessValue = mPreferences.getBoolean(mPrefKey, defaultAdminAccessValue);
        }
        mIsAdminAccessOnly = defaultAdminAccessValue;
    }

    public boolean hasSubitems() {
        return subItems != null && subItems.size() > 0;
    }

    public boolean isAdminAccessOnly() {
        return mIsAdminAccessOnly;
    }

    public void setAdminAccessOnly(boolean adminAccessOnly) {
        mIsAdminAccessOnly = adminAccessOnly;
        mPreferences
                .edit()
                .putBoolean(mPrefKey, adminAccessOnly)
                .apply();
    }
}
