package me.li2.android.tutorial.View;

import me.li2.android.tutorial.BasicUI.SimpleTutorialActivity;

/**
 * Created by weiyi on 10/04/2017.
 * https://github.com/li2
 */

public class ViewTutorial extends SimpleTutorialActivity {
    public static final String TAG = "View";

    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[] {
                ImageViewScaleType.class,
                MultipleScreenDrawableResources.class,
                NotificationTest.class,
        };
        return tutorialActivities;
    }
}
