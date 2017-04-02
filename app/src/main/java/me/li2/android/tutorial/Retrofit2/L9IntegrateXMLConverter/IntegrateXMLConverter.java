package me.li2.android.tutorial.Retrofit2.L9IntegrateXMLConverter;

import me.li2.android.tutorial.Retrofit2.L3CreatingSustainableClient.ServiceGenerator;
import me.li2.android.tutorial.SimpleOneButtonActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.li2.android.tutorial.LogHelper.LOGD;
import static me.li2.android.tutorial.LogHelper.LOGE;
import static me.li2.android.tutorial.LogHelper.makeLogTag;

/**
 * Created by weiyi on 29/03/2017.
 * https://github.com/li2
 */

public class IntegrateXMLConverter extends SimpleOneButtonActivity {
    private static final String TAG = makeLogTag(IntegrateXMLConverter.class);

    @Override
    public void doAction() {
        WeatherService weatherService = ServiceGenerator.createService(WeatherService.class);
        weatherService.getLondonWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                LOGD(IntegrateXMLConverter.this, TAG, "succeed to get weather info:\n " + response.body().toString());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                LOGE(IntegrateXMLConverter.this, TAG, "error :( " + t.getMessage(), t);
                /*
                 com.google.gson.JsonSyntaxException: java.lang.IllegalStateException:
                 Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $
                 Android Retrofit 2 multiple converters (Gson & SimpleXML) error
                 */
            }
        });
    }
}
