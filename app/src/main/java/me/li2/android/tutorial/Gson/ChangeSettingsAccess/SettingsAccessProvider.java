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

    private Context mContext;
    private InternalStorage mStorage;
    private String mJsonString;
    private ArrayList<SettingsAccessItem> mAllItems = new ArrayList<>();
    private SettingsAccessItem mCurrentItem;

    public SettingsAccessProvider(Context context) {
        mContext = context;
        mStorage = new InternalStorage(mContext);
        mJsonString = ResourceUtils.readRawFile(mContext, R.raw.settings_access_data);;
        try {
            JSONObject jsonBody = new JSONObject(mJsonString);
            JSONObject settingsJsonObject = jsonBody.getJSONObject("settings_access");
            parseItems(null, mAllItems, settingsJsonObject);
        } catch (JSONException e) {
            LOGE(TAG, "failed to parse JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public SettingsAccessItem getRootItem() {
        if (mAllItems != null && mAllItems.size() > 0) {
            return mAllItems.get(0);
        }
        return null;
    }

    public SettingsAccessItem getPrevItem() {
        if (mCurrentItem != null) {
            return mCurrentItem.mParentItem;
        }
        return null;
    }

    public void setCurrentItem(SettingsAccessItem currentItem) {
        mCurrentItem = currentItem;
    }

    // should be same with Json file
    private static final String JSON_OBJECT_KEY_HAS_SUBITEMS = "has_subitems";
    private static final String JSON_OBJECT_KEY_ITEMS = "items";

    private void parseItems(SettingsAccessItem parentItem, ArrayList<SettingsAccessItem> items, JSONObject settingJsonObject)
            throws JSONException {
        SettingsAccessItem item = new SettingsAccessItem(mContext, settingJsonObject);
        item.mParentItem = parentItem;
        items.add(item);

        boolean hasSubItems = settingJsonObject.getBoolean(JSON_OBJECT_KEY_HAS_SUBITEMS);
        if (hasSubItems) {
            JSONArray settingJsonArray = settingJsonObject.getJSONArray(JSON_OBJECT_KEY_ITEMS);
            for (int i = 0; i < settingJsonArray.length(); i++) {
                // recursion
                parseItems(item, item.mSubItems, settingJsonArray.getJSONObject(i));
            }
        } else {
            // exit recursion if has no subitems
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

    /**
     * Update item checked status.
     */
    public void updateItem(SettingsAccessItem selectedSubItem, boolean checked) {
        // update itself
        selectedSubItem.setAdminAccessOnly(checked);

        // update parent-items
        updateParentItemsCheckedStatus(selectedSubItem);

        // update sub-items
        updateSubitemsCheckedStatus(selectedSubItem);
    }

    /**
     * Uncheck any subitem belong to the checked high-level item will lead the high-level item unchecked.
     * Check all subitems belong ....... unchecked .................. lead ................... checked.
     * @param item
     */
    private void updateParentItemsCheckedStatus(SettingsAccessItem item) {
        SettingsAccessItem parentItem = item.mParentItem;
        if (parentItem != null) {
            if (parentItem.hasSubitems()) {
                boolean allSubChecked = true;
                for (SettingsAccessItem subitem : parentItem.mSubItems) {
                    if (!subitem.isAdminAccessOnly()) {
                        allSubChecked = false;
                        break;
                    }
                }
                parentItem.setAdminAccessOnly(allSubChecked);
            }
            // recursion
            updateParentItemsCheckedStatus(parentItem);
        } else {
            // exit recursion if has no parent
            return;
        }
    }

    /**
     * Check / Uncheck the high level item will make it's sub-items all checked / unchecked.
     * @param item
     */
    private void updateSubitemsCheckedStatus(SettingsAccessItem item) {
        if (item.hasSubitems()) {
            for (SettingsAccessItem subitem : item.mSubItems) {
                subitem.setAdminAccessOnly(item.isAdminAccessOnly());
                // recursion
                updateSubitemsCheckedStatus(subitem);
            }
        } else {
            // exit recursion if has no subitems
            return;
        }
    }
}
