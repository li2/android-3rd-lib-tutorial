package me.li2.android.tutorial.Dagger2.Case1;

import dagger.Module;
import dagger.Provides;

/**
 * Created by weiyi on 13/3/18.
 * https://github.com/li2
 */

@Module
public class CarModule {

    public CarModule() {
    }

    @Provides
    public Engine provideEngine() {
        return new Engine();
    }
}
