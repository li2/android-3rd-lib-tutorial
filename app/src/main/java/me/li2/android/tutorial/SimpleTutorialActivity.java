package me.li2.android.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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

        /**
         * It's weird if not postDelay, the {@link SimpleListFragment#getActivity()} always return null.
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> titles = new ArrayList<>();
                for (Class c : getTutorialActivities()) {
                    titles.add(c.getSimpleName());
                }
                setListData(titles);
            }
        }, 250);
    }

    @Override
    protected void onSimpleListItemClick(int position) {
        Class[] tutorialActivities = getTutorialActivities();
        if (tutorialActivities != null && tutorialActivities.length > position) {
            startActivity(new Intent(this, tutorialActivities[position]));
        }
    }

    public abstract Class[] getTutorialActivities();
}
