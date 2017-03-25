package me.li2.android.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 */

public abstract class SimpleTutorialActivity extends SimpleListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> titles = new ArrayList<>();
        for (Class c : getTutorialActivities()) {
            titles.add(c.getSimpleName());
        }
        setListData(titles);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Class[] tutorialActivities = getTutorialActivities();
        if (tutorialActivities != null && tutorialActivities.length > position) {
            startActivity(new Intent(this, tutorialActivities[position]));
        }
    }

    public abstract Class[] getTutorialActivities();
}
