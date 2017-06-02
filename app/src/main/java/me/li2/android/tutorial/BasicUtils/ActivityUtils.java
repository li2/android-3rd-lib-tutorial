package me.li2.android.tutorial.BasicUtils;

import android.app.Activity;
import android.os.Build;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by weiyi on 02/06/2017.
 * https://github.com/li2
 */

public class ActivityUtils {
    /**
     * Hide the navigation bar.
     * https://developer.android.com/training/system-ui/navigation.html#40
     * @param weakReference
     */
    public static void hideNavigationBar(WeakReference<Activity> weakReference) {
        Activity activity = weakReference.get();
        if (activity == null) {
            return;
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
