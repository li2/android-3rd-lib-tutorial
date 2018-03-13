package me.li2.android.tutorial.Dagger2.Case1;

import javax.inject.Inject;

/**
 * Created by weiyi on 13/3/18.
 * https://github.com/li2
 */

public class Engine {
    @Inject
    public Engine() {

    }

    public void run() {
        System.out.println("Engine is running");
    }
}
