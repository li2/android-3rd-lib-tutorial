package me.li2.android.tutorial.Retrofit2.L1GettingStarted;

/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 *
 * Describe the object model.
 * This class comprises required class properties to map the response data.
 * Retrofit makes sure the server response gets mapped correctly.
 */
public class GitHubRepo {
    private int id;
    private String name;

    public GitHubRepo() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
