package me.li2.android.tutorial.StorageUtils;

import android.content.Context;
import android.net.Uri;

/**
 * Created by weiyi on 09/04/2017.
 * https://github.com/li2
 */

public class ResourceUtils {
    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final String FORWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE
                + context.getApplicationContext().getPackageName()
                + FORWARD_SLASH + resourceId);
    }
}
