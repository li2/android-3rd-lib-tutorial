package me.li2.android.tutorial;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 */

public abstract class SimpleListActivity extends ListActivity {

    private static final String KEY_TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(this.getClass().getSimpleName());
    }

    public void setListData(List<String> titles) {
        List<Map<String, String>> data = new ArrayList<>();
        for (String title : titles) {
            Map<String, String> item = new HashMap<>();
            item.put(KEY_TITLE, title);
            data.add(item);
        }

        ListAdapter listAdapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1,
                new String[] {KEY_TITLE}, new int[] {android.R.id.text1});
        setListAdapter(listAdapter);
    }
}
