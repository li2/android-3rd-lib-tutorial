package me.li2.android.tutorial.Gson.ChangeSettingsAccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by weiyi on 24/04/2017.
 * https://github.com/li2
 */

public class SettingsAccessItem {
    private static final String PREF_KEY_PREFIX = "settings_access_provider_";
    private static final String JSON_OBJECT_KEY_TITLE = "title";
    private static final String JSON_OBJECT_KEY_PREF_KEY = "pref_key";
    private static final String JSON_OBJECT_KEY_ADMIN_ONLY = "is_only_admin_access";

    private Context mContext;
    private SharedPreferences mPreferences;
    public String mTitle = "";
    private String mPrefKey = "";
    private boolean mIsAdminAccessOnly = true;
    public ArrayList<SettingsAccessItem> mSubItems = new ArrayList<>();


    public SettingsAccessItem(Context context, JSONObject jsonObject) throws JSONException {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        if (jsonObject.has(JSON_OBJECT_KEY_TITLE)) {
            mTitle = jsonObject.getString(JSON_OBJECT_KEY_TITLE);
        }
        if (jsonObject.has(JSON_OBJECT_KEY_PREF_KEY)) {
            mPrefKey = PREF_KEY_PREFIX + jsonObject.getString(JSON_OBJECT_KEY_PREF_KEY);
        }

        boolean defaultAdminAccessValue = true;
        if (jsonObject.has(JSON_OBJECT_KEY_ADMIN_ONLY)) {
            defaultAdminAccessValue = jsonObject.getBoolean(JSON_OBJECT_KEY_ADMIN_ONLY);
        }
        if (!mPreferences.contains(mPrefKey)) {
            mPreferences
                    .edit()
                    .putBoolean(mPrefKey, defaultAdminAccessValue)
                    .apply();
        } else {
            defaultAdminAccessValue = mPreferences.getBoolean(mPrefKey, defaultAdminAccessValue);
        }

        mIsAdminAccessOnly = defaultAdminAccessValue;
        mSubItems = new ArrayList<>();
    }

    public boolean hasSubitems() {
        return mSubItems != null && mSubItems.size() > 0;
    }

    public boolean isAdminAccessOnly() {
        return mIsAdminAccessOnly;
    }

    public void setAdminAccessOnly(boolean adminAccessOnly) {
        if (mIsAdminAccessOnly != adminAccessOnly) {
            mIsAdminAccessOnly = adminAccessOnly;
            mPreferences
                    .edit()
                    .putBoolean(mPrefKey, adminAccessOnly)
                    .apply();
        }
    }
}
