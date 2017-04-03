package me.li2.android.tutorial.Retrofit2.L3CreatingSustainableClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Centralize Retrofit client.
 *
 * It's common practice to just use one OkHttpClient instance to reuse open socket connections.
 * We chose to use the static field, and because the httpClient is used throughout this class,
 * we need to make all fields and methods static.
 *
 * Make it rock & enjoy coding!
 *
 * Created by weiyi on 26/03/2017.
 * https://github.com/li2
 */

public class ServiceGenerator {
    private static final String BASE_URL = "https://api.github.com/";

    /*
    MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $
    http://stackoverflow.com/a/36002007/2722270
     */
    private  static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    /*
     Crash:
     com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1 path $

     Reason:
     The GsonConverter thinks it can handle any data and (mistakenly) also accept XML data.

     Dealing with Multiple Converters:
     Make sure to add the SimpleXmlConverter before the GsonConverter, to support both XML and JSON within your app.
     Make sure you specify the special-purpose converters with limited abilities first and general converters (like Gson) last.

     http://disq.us/p/1fuwzgz
     https://speakerdeck.com/jakewharton/making-retrofit-work-for-you-ohio-devfest-2016
      */
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getOkHttpClientBuilder().build())
            ;

    private static Retrofit retrofit = builder.build();

    /**
     * Create a Request Interceptor, and intercept the request on the network layer provided by OkHttp.
     * These headers are passed with every request ! but it seems not work here now.
     *
     * Request header: Headers containing more information about the resource to be fetched or about the client itself.
     * https://developer.mozilla.org/en-US/docs/Glossary/Request_header
     * 
     * @return
     */
    private static Interceptor createRequestInterceptor() {
        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("LI2-Interceptor-Headers-1", "key-value-pair as a list, i")
                        .header("LI2-Interceptor-Headers-2", "key-value-pair as a list, love")
                        .header("LI2-Interceptor-Headers-3", "key-value-pair as a list, programming")
                        .header("LI2-Interceptor-Headers-4", "key-value-pair as a list, &")
                        .header("LI2-Interceptor-Headers-5", "key-value-pair as a list, legela")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };
        return requestInterceptor;
    }

    private static OkHttpClient.Builder getOkHttpClientBuilder() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        // TODO not work !
        builder.addInterceptor(createRequestInterceptor());
        return builder;
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
