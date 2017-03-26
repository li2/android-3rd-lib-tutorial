package me.li2.android.tutorial.Retrofit2;

import me.li2.android.tutorial.Retrofit2.L1GettingStarted.GettingStartedActivity;
import me.li2.android.tutorial.SimpleTutorialActivity;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 */

public class RetrofitTutorialActivity extends SimpleTutorialActivity {
    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[]{
                GettingStartedActivity.class,
        };
        return tutorialActivities;
    }
}
