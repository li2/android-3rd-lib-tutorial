package me.li2.android.tutorial.BasicUI;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 */

public abstract class SimpleTutorialActivity extends SimpleListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * ATTENTION: this problem has been solved by overriding initListData(),
         * so NO need to call setListData(List) anymore.
         *
         * The problem:
         * It's weird if not postDelay, the {@link SimpleListFragment#getActivity()} always return null.
         *
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
        */
    }

    @Override
    protected void onSimpleListItemClick(int position) {
        Class[] tutorialActivities = getTutorialActivities();
        if (tutorialActivities != null && tutorialActivities.length > position) {
            startActivity(new Intent(this, tutorialActivities[position]));
        }
    }

    @Override
    protected ArrayList<String> initListData() {
        ArrayList<String> titles = new ArrayList<>();
        for (Class c : getTutorialActivities()) {
            titles.add(c.getSimpleName());
        }
        return titles;
    }

    public abstract Class[] getTutorialActivities();
}
