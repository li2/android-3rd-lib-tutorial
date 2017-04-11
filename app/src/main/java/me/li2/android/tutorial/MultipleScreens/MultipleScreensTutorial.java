package me.li2.android.tutorial.MultipleScreens;

import me.li2.android.tutorial.BasicUI.SimpleTutorialActivity;

/**
 * Created by weiyi on 10/04/2017.
 * https://github.com/li2
 */

public class MultipleScreensTutorial extends SimpleTutorialActivity {
    public static final String TAG = "MultipleScreens";

    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[] {
                DrawableResources.class,
        };
        return tutorialActivities;
    }
}
