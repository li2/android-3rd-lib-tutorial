package me.li2.android.tutorial.Retrofit2.L14SendDataFormUrlencoded;

import java.util.ArrayList;

import me.li2.android.tutorial.BasicUI.SimpleOneButtonActivity;
import me.li2.android.tutorial.R;
import me.li2.android.tutorial.Retrofit2.L3CreatingSustainableClient.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGD;
import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;


/**
 * Created by weiyi on 06/04/2017.
 * https://github.com/li2
 */

public class SendDataFormUrlencoded extends SimpleOneButtonActivity {
    private static final String TAG = makeLogTag(SendDataFormUrlencoded.class);

    @Override
    protected String getButtonText() {
        return "Send Data Form-Urlencoded";
    }

    private int mIndex;

    @Override
    public void doAction() {
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        Call<String> call;

        mIndex++;
        if (mIndex%2 == 0) {
            call = taskService.createTask("Assign task to li2: Research Retrofit form encoded requests");
        } else {
            ArrayList<String> titles = new ArrayList<>();
            titles.add("Task1: Research Retrofit form encoded requests");
            titles.add("Task2: Research Retrofit form Encoded Requests Using an Array of Values");
            call = taskService.createTasks(titles);
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                /** more details refer to this image: */
                int image1 = R.drawable.retrofit_send_data_form_url_encoded;
                int image2 = R.drawable.retrofit_send_data_form_url_encoded_array;
                LOGD(TAG, "succeed to send task " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LOGE(TAG, "error :( " + t.getMessage(), t);
                /**
                 * TODO
                 * The http://requestb.in/xvnl60xv site has received the post parameters,
                 * but onFailure still been called:
                 *
                 * org.xmlpull.v1.XmlPullParserException: Unexpected token (
                 * position:TEXT ok@1:3 in okhttp3.ResponseBody$BomAwareReader@22955a7e)
                 */
            }
        });
    }
}
