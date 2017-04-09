package me.li2.android.tutorial.BasicUI;

import me.li2.android.tutorial.Picasso.PicassoTutorial;
import me.li2.android.tutorial.Retrofit2.RetrofitTutorial;

public class TutorialActivity extends SimpleTutorialActivity {
    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[]{
                RetrofitTutorial.class,
                PicassoTutorial.class,
        };
        return tutorialActivities;
    }
}
