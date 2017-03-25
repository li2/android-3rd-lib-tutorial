package me.li2.android.tutorial;

import me.li2.android.tutorial.Retrofit2.RetrofitTutorialActivity;

public class TutorialActivity extends SimpleTutorialActivity {
    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[]{
                RetrofitTutorialActivity.class,
        };
        return tutorialActivities;
    }
}
