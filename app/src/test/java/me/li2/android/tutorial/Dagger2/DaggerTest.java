package me.li2.android.tutorial.Dagger2;

import org.junit.Test;

import me.li2.android.tutorial.Dagger2.Case1.Car;

/**
 * Created by weiyi on 13/3/18.
 * https://github.com/li2
 */

public class DaggerTest {

    @Test
    public void test(){
        Car car = new Car();
        car.getEngine().run();
    }
}
