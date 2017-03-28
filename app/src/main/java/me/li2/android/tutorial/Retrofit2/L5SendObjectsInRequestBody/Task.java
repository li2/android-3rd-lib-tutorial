package me.li2.android.tutorial.Retrofit2.L5SendObjectsInRequestBody;

/**
 * Created by weiyi on 27/03/2017.
 * https://github.com/li2
 */

public class Task {
    private long id;
    private String text;

    public Task(long id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "" + id + ", " + text;
    }
}
