package me.li2.android.tutorial.Retrofit2.L9IntegrateXMLConverter;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

import me.li2.android.tutorial.BasicUI.SimpleOneButtonActivity;
import me.li2.android.tutorial.Retrofit2.L3CreatingSustainableClient.ServiceGenerator;
import me.li2.android.tutorial.Retrofit2.RetrofitTutorial;
import me.li2.android.tutorial.BasicUtils.StorageUtils.StorageUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGD;
import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;
import static me.li2.android.tutorial.BasicUI.LogHelper.makeLogTag;

/**
 * Created by weiyi on 29/03/2017.
 * https://github.com/li2
 */

public class IntegrateXMLConverter extends SimpleOneButtonActivity {
    private static final String TAG = makeLogTag(IntegrateXMLConverter.class);
    private static final int PERMISSION_REQUEST_WRITE_STORAGE = 21;
    private Weather mWeather;

    @Override
    protected String getTitlePrefix() {
        return RetrofitTutorial.TAG;
    }

    @Override
    protected String getButtonText() {
        return "Request London Weather from OpenWeather with XML data response";
    }

    @Override
    public void doAction() {
        WeatherService weatherService = ServiceGenerator.createService(WeatherService.class);
        weatherService.getLondonWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                LOGD(TAG, "succeed to get weather info:\n " + response.body().toString());
                mWeather = response.body();
                saveWeather(mWeather);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                LOGE(TAG, "error :( " + t.getMessage(), t);
                /*
                 com.google.gson.JsonSyntaxException: java.lang.IllegalStateException:
                 Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $
                 Android Retrofit 2 multiple converters (Gson & SimpleXML) error
                 */
            }
        });
    }

    private void saveWeather(Weather weather) {
        if (!StorageUtils.hasPermission(this)) {
            StorageUtils.requestPermission(this, PERMISSION_REQUEST_WRITE_STORAGE);
        } else {
            serializeWeather(weather);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LOGD(TAG, "permission was granted, yay !");
                serializeWeather(mWeather);
            } else {
                LOGE(TAG, "permission denied, boo !");
            }
        }
    }

    private void serializeWeather(Weather weather) {
        if (weather == null) {
            return;
        }

        try {
            Serializer serializer = new Persister();
            File file = StorageUtils.createExternalFile("LI2", "LondonWeather.xml");
            serializer.write(weather, file);
            LOGD(TAG, "succeed to serialize weather into file: " + file.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
