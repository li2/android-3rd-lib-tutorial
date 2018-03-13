package me.li2.android.tutorial.Dagger2.Case1;

import javax.inject.Inject;

/**
 * Created by weiyi on 13/3/18.
 * https://github.com/li2
 */

public class Car {
    @Inject
    Engine engine;

    public Car() {
        DaggerCarComponent.builder()
                .carModule(new CarModule())
                .build().inject(this);
    }

    public Engine getEngine() {
        return this.engine;
    }
}
