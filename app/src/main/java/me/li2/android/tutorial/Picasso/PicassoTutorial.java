package me.li2.android.tutorial.Picasso;

import me.li2.android.tutorial.BasicUI.SimpleTutorialActivity;
import me.li2.android.tutorial.Picasso.L1ImageLoading.ImageLoading;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 */

public class PicassoTutorial extends SimpleTutorialActivity {
    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[]{
                ImageLoading.class,
        };
        return tutorialActivities;
    }
}
