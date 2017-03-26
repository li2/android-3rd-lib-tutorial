package me.li2.android.tutorial.Retrofit2.L1GettingStarted;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 *
 * Describe the API interface.
 */

public interface GitHubClient {

    /**
     * Request the list of repositories for a given user.
     * The @GET annotation declares that this request uses the HTTP GET method.
     * The usage of Retrofit's path parameter replacement functionality: the {user} path will be
     * replaced with given variable values when calling this method.
     * @param user
     * @return
     */
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
}
