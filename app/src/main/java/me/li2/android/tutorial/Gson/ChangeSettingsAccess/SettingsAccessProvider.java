package me.li2.android.tutorial.Gson.ChangeSettingsAccess;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.li2.android.tutorial.R;
import me.li2.android.tutorial.StorageUtils.ResourceUtils;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 24/04/2017.
 * https://github.com/li2
 */

public class SettingsAccessProvider {
    private static final String TAG = makeLogTag(SettingsAccessProvider.class);
    private Context mContext;
    private ArrayList<SettingsAccessItem> mAllItems = new ArrayList<>();
    private ArrayList<SettingsAccessItem> mChainedItems = new ArrayList<>();

    public SettingsAccessProvider(Context context) {
        mContext = context;
        String settingsString = ResourceUtils.readRawFile(mContext, R.raw.settings_access_data);
        try {
            JSONObject jsonBody = new JSONObject(settingsString);
            JSONObject settingsJsonObject = jsonBody.getJSONObject("settings_access");
            parseItems(mAllItems, settingsJsonObject);
        } catch (JSONException e) {
            LOGE(TAG, "failed to parse R.raw.settings_access_data " + e.getMessage());
            e.printStackTrace();
        }
    }

    // first-in last-out stack
    public synchronized void push(SettingsAccessItem item) {
        if (item != null) {
            mChainedItems.add(item);
        }
    }

    public synchronized SettingsAccessItem pop() {
        if (mChainedItems.isEmpty()) {
            return null;
        }
        SettingsAccessItem item = mChainedItems.get(mChainedItems.size()-1);
        mChainedItems.remove(item);
        return item;
    }

    public SettingsAccessItem getRootItem() {
        if (mAllItems != null && mAllItems.size() > 0) {
            return mAllItems.get(0);
        }
        return null;
    }

    private void parseItems(ArrayList<SettingsAccessItem> items, JSONObject settingJsonObject) throws JSONException {
        SettingsAccessItem item = new SettingsAccessItem();
        items.add(item);
        item.title = settingJsonObject.getString("title");
        item.isAdminAccessOnly = settingJsonObject.getBoolean("is_only_admin_access");

        boolean hasSubItems = settingJsonObject.getBoolean("has_subitems");
        if (hasSubItems) {
            JSONArray settingJsonArray = settingJsonObject.getJSONArray("items");
            for (int i = 0; i < settingJsonArray.length(); i++) {
                parseItems(item.subItems, settingJsonArray.getJSONObject(i));
            }
        } else {
            return;
        }
    }

    private ArrayList<SettingsAccessItem> parseItems(JSONArray settingJsonArray) throws JSONException {
        ArrayList<SettingsAccessItem> items = new ArrayList<>();

        for (int i = 0; i < settingJsonArray.length(); i++) {
            JSONObject settingJsonObject = settingJsonArray.getJSONObject(i);
            SettingsAccessItem item = new SettingsAccessItem();
            items.add(item);
            item.title = settingJsonObject.getString("title");
            item.isAdminAccessOnly = settingJsonObject.getBoolean("is_only_admin_access");

            boolean hasSubItems = settingJsonObject.getBoolean("has_subitems");
            if (hasSubItems) {
                item.subItems = parseItems(settingJsonObject.getJSONArray("items"));
            } else {
                continue;
            }
        }

        return items;
    }
}
