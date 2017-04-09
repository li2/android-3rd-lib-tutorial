package me.li2.android.tutorial.Retrofit2;

import me.li2.android.tutorial.Retrofit2.L11QueryParameters.QueryParameters;
import me.li2.android.tutorial.Retrofit2.L14SendDataFormUrlencoded.SendDataFormUrlencoded;
import me.li2.android.tutorial.Retrofit2.L15UploadFiles.UploadFile;
import me.li2.android.tutorial.Retrofit2.L1GettingStarted.GettingStartedActivity;
import me.li2.android.tutorial.Retrofit2.L5SendObjectsInRequestBody.SendObjectsInRequestBody;
import me.li2.android.tutorial.Retrofit2.L9IntegrateXMLConverter.IntegrateXMLConverter;
import me.li2.android.tutorial.BasicUI.SimpleTutorialActivity;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 */

public class RetrofitTutorial extends SimpleTutorialActivity {
    public static final String TAG = "Retrofit";

    @Override
    public Class[] getTutorialActivities() {
        Class[] tutorialActivities = new Class[]{
                GettingStartedActivity.class,
                SendObjectsInRequestBody.class,
                IntegrateXMLConverter.class,
                QueryParameters.class,
                SendDataFormUrlencoded.class,
                UploadFile.class,
        };
        return tutorialActivities;
    }
}
