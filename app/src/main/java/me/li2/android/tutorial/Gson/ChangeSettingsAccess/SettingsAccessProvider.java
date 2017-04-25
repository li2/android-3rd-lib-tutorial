package me.li2.android.tutorial.Gson.ChangeSettingsAccess;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.li2.android.tutorial.R;
import me.li2.android.tutorial.StorageUtils.InternalStorage;
import me.li2.android.tutorial.StorageUtils.ResourceUtils;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * http://www.jsoneditoronline.org/
 *
 * Created by weiyi on 24/04/2017.
 * https://github.com/li2
 */

public class SettingsAccessProvider {
    private static final String TAG = makeLogTag(SettingsAccessProvider.class);
    private static final String SETTINGS_ACCESS_DATA_JSON_FILE = "settings_access_data.json";

    private Context mContext;
    private InternalStorage mStorage;
    private String mJsonString;
    private ArrayList<SettingsAccessItem> mAllItems = new ArrayList<>();
    private ArrayList<SettingsAccessItem> mChainedItems = new ArrayList<>();

    public SettingsAccessProvider(Context context) {
        mContext = context;
        mStorage = new InternalStorage(mContext);
        mJsonString = ResourceUtils.readRawFile(mContext, R.raw.settings_access_data);;
        try {
            JSONObject jsonBody = new JSONObject(mJsonString);
            JSONObject settingsJsonObject = jsonBody.getJSONObject("settings_access");
            parseItems(mAllItems, settingsJsonObject);
        } catch (JSONException e) {
            LOGE(TAG, "failed to parse JSON: " + e.getMessage());
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

    private static final String JSON_OBJECT_KEY_TITLE = "title";
    private static final String JSON_OBJECT_KEY_PREF_KEY = "pref_key";
    private static final String JSON_OBJECT_KEY_ADMIN_ONLY = "is_only_admin_access";
    private static final String JSON_OBJECT_KEY_HAS_SUBITEMS = "has_subitems";
    private static final String JSON_OBJECT_KEY_ITEMS = "items";

    private void parseItems(ArrayList<SettingsAccessItem> items, JSONObject settingJsonObject) throws JSONException {
        String title = "";
        String prefKey = "";
        boolean defaultAdminAccessValue = true;

        if (settingJsonObject.has(JSON_OBJECT_KEY_TITLE)) {
            title = settingJsonObject.getString(JSON_OBJECT_KEY_TITLE);
        }

        if (settingJsonObject.has(JSON_OBJECT_KEY_PREF_KEY)) {
            prefKey = settingJsonObject.getString(JSON_OBJECT_KEY_PREF_KEY);
        }

        if (settingJsonObject.has(JSON_OBJECT_KEY_ADMIN_ONLY)) {
            defaultAdminAccessValue = settingJsonObject.getBoolean(JSON_OBJECT_KEY_ADMIN_ONLY);
        }

        SettingsAccessItem item = new SettingsAccessItem(mContext, title, prefKey, defaultAdminAccessValue);
        items.add(item);

        boolean hasSubItems = settingJsonObject.getBoolean(JSON_OBJECT_KEY_HAS_SUBITEMS);
        if (hasSubItems) {
            JSONArray settingJsonArray = settingJsonObject.getJSONArray(JSON_OBJECT_KEY_ITEMS);
            for (int i = 0; i < settingJsonArray.length(); i++) {
                parseItems(item.subItems, settingJsonArray.getJSONObject(i));
            }
        } else {
            return;
        }
    }

    private String readJsonFile(String fileName) {
        if (!mStorage.isFileExist(fileName)) {
            mStorage.createFile(fileName);
            mStorage.copyRawToFile(R.raw.settings_access_data, fileName);
        }
        return mStorage.readTextFile(fileName);
    }
    
    public void updateItem(SettingsAccessItem item, boolean checked) {
        item.setAdminAccessOnly(checked);
    }
}
