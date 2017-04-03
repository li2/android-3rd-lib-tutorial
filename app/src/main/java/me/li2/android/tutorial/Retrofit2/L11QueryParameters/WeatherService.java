package me.li2.android.tutorial.Retrofit2.L11QueryParameters;

import java.util.Map;

import me.li2.android.tutorial.Retrofit2.L9IntegrateXMLConverter.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * XML http://samples.openweathermap.org/data/2.5/weather?q=London&mode=xml&appid=b1b15e88fa797225412429c1c50c122a1
 *
 * Created by weiyi on 02/04/2017.
 * https://github.com/li2
 */

public interface WeatherService {
    /*
    Calling this method results in this request url:
     http://samples.openweathermap.org/data/2.5/weather/?q=location&mode=format&appid=apiKey
     */
    @GET("http://samples.openweathermap.org/data/2.5/weather/")
    Call<Weather> getLondonWeather(
            @Query("q") String location,
            @Query("mode") String format,
            @Query("appid") String apiKey);

    /*
    QueryMap annotation is a better solution to work with complex API endpoints having various
     options for query parameters.
     */
    @GET("http://samples.openweathermap.org/data/2.5/weather/")
    Call<Weather> getLondonWeather(@QueryMap Map<String, String> options);
}
