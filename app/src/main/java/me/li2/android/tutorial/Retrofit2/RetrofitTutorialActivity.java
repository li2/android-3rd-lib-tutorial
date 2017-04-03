package me.li2.android.tutorial.Retrofit2;

import me.li2.android.tutorial.Retrofit2.L11QueryParameters.QueryParameters;
import me.li2.android.tutorial.Retrofit2.L1GettingStarted.GettingStartedActivity;
import me.li2.android.tutorial.Retrofit2.L5SendObjectsInRequestBody.SendObjectsInRequestBody;
import me.li2.android.tutorial.Retrofit2.L9IntegrateXMLConverter.IntegrateXMLConverter;
import me.li2.android.tutorial.BasicUI.SimpleTutorialActivity;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 */

public class RetrofitTutorialActivity extends SimpleTutorialActivity {
    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[]{
                GettingStartedActivity.class,
                SendObjectsInRequestBody.class,
                IntegrateXMLConverter.class,
                QueryParameters.class,
        };
        return tutorialActivities;
    }
}
