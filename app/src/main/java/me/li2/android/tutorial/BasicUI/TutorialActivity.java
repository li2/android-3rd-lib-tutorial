package me.li2.android.tutorial.BasicUI;

import android.os.Bundle;

import me.li2.android.tutorial.BasicUtils.StorageUtils.StorageTutorial;
import me.li2.android.tutorial.Gson.GsonTutorial;
import me.li2.android.tutorial.Picasso.PicassoTutorial;
import me.li2.android.tutorial.Retrofit2.RetrofitTutorial;
import me.li2.android.tutorial.View.ViewTutorial;

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
                ViewTutorial.class,
                GsonTutorial.class,
                StorageTutorial.class,
        };
        return tutorialActivities;
    }
}
