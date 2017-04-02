package me.li2.android.tutorial.Retrofit2.L9IntegrateXMLConverter;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * To test with XML converter, we use weather data xml from OpenWeather.
 * https://openweathermap.org/current#current_XML
 *
 * Examples of API calls:
 * JSON http://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1
 * XML http://samples.openweathermap.org/data/2.5/weather?q=London&mode=xml&appid=b1b15e88fa797225412429c1c50c122a1
 *
 * Created by weiyi on 29/03/2017.
 * https://github.com/li2
 */

public interface WeatherService {
    @GET("http://samples.openweathermap.org/data/2.5/weather?q=London&mode=xml&appid=b1b15e88fa797225412429c1c50c122a1")
    Call<Weather> getLondonWeather();
}
