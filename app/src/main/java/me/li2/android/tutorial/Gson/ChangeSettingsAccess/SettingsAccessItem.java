package me.li2.android.tutorial.Gson.ChangeSettingsAccess;

import java.util.ArrayList;

/**
 * Created by weiyi on 24/04/2017.
 * https://github.com/li2
 */

public class SettingsAccessItem {
    public String title;
    public boolean isAdminAccessOnly;
    public ArrayList<SettingsAccessItem> subItems = new ArrayList<>();

    public boolean hasSubitems() {
        return subItems != null && subItems.size() > 0;
    }
}
