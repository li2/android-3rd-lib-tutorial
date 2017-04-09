package me.li2.android.tutorial.Retrofit2.L1GettingStarted;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.li2.android.tutorial.BasicUI.SimpleListActivity;
import me.li2.android.tutorial.Retrofit2.L3CreatingSustainableClient.ServiceGenerator;
import me.li2.android.tutorial.Retrofit2.RetrofitTutorial;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.li2.android.tutorial.BasicUI.LogHelper.LOGE;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 *
 * Refer
 * Initially, Retrofit looks pretty complex. We promise, it’s very clean and pays off if you’re working on
 * a larger project. If you spend a little more time reading a few more tutorials, you’ll soon also love Retrofit.
 *
 * https://futurestud.io/tutorials/retrofit-getting-started-and-android-client
 */

public class GettingStartedActivity extends SimpleListActivity {
    private static final  String TAG = "GettingStartedActivity";

    /*
     TODO App crash because of wrong URL, how to avoid crash even URL is wrong?
     Caused by: java.lang.IllegalArgumentException: Illegal URL: httfps://api.github.com/
     */
    private static final String API_BASE_URL = "https://api.github.com/";
    private static final String USER = "li2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theBasicsOfRetrofit();
    }

    @Override
    protected String getTitlePrefix() {
        return RetrofitTutorial.TAG;
    }

    private void theBasicsOfRetrofit() {
        /*
         use the Builder to set some general options for all requests, i.e. the base URL or converter.
         In most cases requests to a server, and the responses from the server, are not Java objects.
         They're mapped to some language neutral like JSON.
         When using Retrofit 2, you need to add a converter explicitly to the Retrofit object.
         */
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // create a client which points the GitHub API endpoint.
        //GitHubClient client = retrofit.create(GitHubClient.class);

        /**
         * For one requests, above looks fine. But if you have dozens of network requests throughout your app,
         * it'll be a nightmare to manage. With our {@link ServiceGenerator}, you only need a single line:
         */
        GitHubClient client = ServiceGenerator.createService(GitHubClient.class);
        Call<List<GitHubRepo>> call = null;

        try {
            // fetch a list of the GitHub repositories
            call = client.reposForUser(USER);
        } catch (Exception e) {
            // missing GSON converter will cause crash:
            // IllegalArgumentException: Unable to create converter for List<GitHubRepo>
            // IllegalArgumentException: Could not locate ResponseBody converter for List<GitHubRepo>
            e.printStackTrace();
            LOGE(TAG, "error :( fetch a list of GitHub repositories:\n " + e.getMessage());
        }

        if (call == null) {
            return;
        }

        // execute the call asynchronously. get a positive or negative callback.
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                // the network call was a success ss and we got a response
                List<GitHubRepo> repos = response.body();
                // use the repository list and display it
                List<String> names = new ArrayList<>();
                for (GitHubRepo repo : repos) {
                    names.add(repo.getName());
                    Log.d(TAG, "repo " + repo.getName());
                }
                setListData(names);
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(GettingStartedActivity.this, "error :( " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
