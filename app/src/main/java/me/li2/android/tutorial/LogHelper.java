package me.li2.android.tutorial;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by weiyi on 30/03/2017.
 * https://github.com/li2
 */

public class LogHelper {
    private static final String LOG_PREFIX = "li2_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /** Don't use this when obfuscating class names! */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGD(Context context, String tag, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        Log.d(tag, text.toString());
    }

    public static void LOGW(Context context, String tag, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        Log.w(tag, text.toString());
    }

    public static void LOGE(Context context, String tag, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        Log.e(tag, text.toString());
    }

    public static void LOGE(Context context, String tag, CharSequence text, Throwable t) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        Log.e(tag, text.toString());
        t.printStackTrace();
    }
}
