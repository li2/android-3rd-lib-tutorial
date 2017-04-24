package me.li2.android.tutorial.Gson;

import me.li2.android.tutorial.BasicUI.SimpleTutorialActivity;
import me.li2.android.tutorial.Gson.ChangeSettingsAccess.ChangeSettingsAccessActivity;

/**
 * Created by weiyi on 24/04/2017.
 * https://github.com/li2
 */

public class GsonTutorial extends SimpleTutorialActivity {
    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[] {
                ChangeSettingsAccessActivity.class,
        };

        return tutorialActivities;
    }
}
