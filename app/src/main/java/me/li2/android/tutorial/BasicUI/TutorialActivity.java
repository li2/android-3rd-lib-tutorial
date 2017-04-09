package me.li2.android.tutorial.BasicUI;

import android.os.Bundle;

import me.li2.android.tutorial.Picasso.PicassoTutorial;
import me.li2.android.tutorial.Retrofit2.RetrofitTutorial;

public class TutorialActivity extends SimpleTutorialActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[]{
                RetrofitTutorial.class,
                PicassoTutorial.class,
        };
        return tutorialActivities;
    }
}
