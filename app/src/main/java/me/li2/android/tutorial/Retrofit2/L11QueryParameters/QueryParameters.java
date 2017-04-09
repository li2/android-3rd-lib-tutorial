package me.li2.android.tutorial.Retrofit2.L11QueryParameters;

import java.util.HashMap;
import java.util.Map;

import me.li2.android.tutorial.BasicUI.SimpleOneButtonActivity;
import me.li2.android.tutorial.Retrofit2.L3CreatingSustainableClient.ServiceGenerator;
import me.li2.android.tutorial.Retrofit2.L9IntegrateXMLConverter.Weather;
import me.li2.android.tutorial.Retrofit2.RetrofitTutorial;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGD;
import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 03/04/2017.
 * https://github.com/li2
 */

public class QueryParameters extends SimpleOneButtonActivity {
    private static final String TAG = makeLogTag(QueryParameters.class);

    private static final String QUERY_PARAM_LOCATION = "London";
    private static final String QUERY_PARAM_FORMAT = "xml";
    private static final String QUERY_PARAM_APIKEY = "b1b15e88fa797225412429c1c50c122a1";

    private long mQueryTimes;

    @Override
    protected String getTitlePrefix() {
        return RetrofitTutorial.TAG;
    }

    @Override
    protected String getButtonText() {
        return "Query Parameters";
    }

    @Override
    public void doAction() {
        WeatherService weatherService = ServiceGenerator.createService(WeatherService.class);
        Call<Weather> call;

        mQueryTimes++;

        if (mQueryTimes %2 == 0) {
            LOGD(TAG, "test multiple query parameters");
            call = weatherService.getLondonWeather(QUERY_PARAM_LOCATION, QUERY_PARAM_FORMAT, QUERY_PARAM_APIKEY);
        } else {
            LOGD(TAG, "test QueryMap");
            Map<String, String> options = new HashMap<>();
            options.put("q", QUERY_PARAM_LOCATION);
            // TODO if comment put("mode", ...) line, onFailure will be called:
            // RuntimeException: org.xmlpull.v1.XmlPullParserException: Unexpected token (position:TEXT {"coord":{"lon":...
            options.put("mode", QUERY_PARAM_FORMAT);
            options.put("appid", QUERY_PARAM_APIKEY);
            call = weatherService.getLondonWeather(options);
        }

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                LOGD(TAG, "succeed to get weather info:\n " + response.body().toString());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                LOGE(TAG, "error :( " + t.getMessage(), t);
            }
        });
    }
}
