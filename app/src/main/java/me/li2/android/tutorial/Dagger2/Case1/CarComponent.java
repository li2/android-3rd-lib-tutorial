package me.li2.android.tutorial.Dagger2.Case1;

import dagger.Component;

/**
 * Created by weiyi on 13/3/18.
 * https://github.com/li2
 */

@Component
public interface CarComponent {
    void inject(Car car);
}
