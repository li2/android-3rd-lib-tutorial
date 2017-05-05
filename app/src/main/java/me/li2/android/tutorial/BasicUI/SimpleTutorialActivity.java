package me.li2.android.tutorial.BasicUI;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    protected List<Class> mActivities = new ArrayList<>();

    @Override
    protected void onSimpleListItemClick(int position) {
        Class[] tutorialActivities = mActivities.toArray(new Class[mActivities.size()]);

        if (tutorialActivities != null && tutorialActivities.length > position) {
            startActivity(new Intent(this, tutorialActivities[position]));
        }
    }

    @Override
    protected ArrayList<String> initListData() {
        ArrayList<String> titles = new ArrayList<>();
        Class[] activities = getTutorialActivities();
        mActivities = Arrays.asList(activities);

        Collections.sort(mActivities, new Comparator<Class>() {
            @Override
            public int compare(Class o1, Class o2) {
                return o1.getSimpleName().compareTo(o2.getSimpleName());
            }
        });

        for (Class c : mActivities) {
            titles.add(c.getSimpleName());
        }
        return titles;
    }

    public abstract Class[] getTutorialActivities();
}
