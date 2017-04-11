package me.li2.android.tutorial.StorageUtils;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;

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

    private static DisplayMetrics sDisplayMetrics(Context context) {
        /*
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
        */
        return context.getResources().getDisplayMetrics();
    }

    /**
     * Get screen density.
     * @param context
     * @return in dpi unit: 120, 160, 240, 320, 480, 640
     */
    public static int getScreenDensity(Context context) {
        return (int)(sDisplayMetrics(context).density * 160f);
    }

    public static String getScreenDensityName(Context context) {
        int dpi = sDisplayMetrics(context).densityDpi;

        switch (dpi)
        {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi";

            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi";

            case DisplayMetrics.DENSITY_TV:
            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi";

            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_280:
                return "xhdpi";

            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:
                return "xxhdpi";

            case DisplayMetrics.DENSITY_XXXHIGH:
            case DisplayMetrics.DENSITY_560:
                return "xxxhdpi";
        }

        return "unknown";
    }

    /**
     * Get orientation of the screen.
     * @param context
     * @return May be one of ORIENTATION_LANDSCAPE, ORIENTATION_PORTRAIT.
     */
    public static int getScreenOrientation(Context context) {
        return context.getResources().getConfiguration().orientation;
    }

    /**
     * Get the exact physical pixels per inch of the screen in the X dimension.
     * (In the X/Y dimension means this value will be changed when orientation changed.)
     * @param context
     * @return in pixel unit.
     */
    public static int getScreenWidth(Context context) {
        return sDisplayMetrics(context).widthPixels;
    }

    /**
     * Get the exact physical pixels per inch of the screen in the Y dimension.
     * @param context
     * @return in pixel unit.
     */
    public static int getScreenHeight(Context context) {
        return sDisplayMetrics(context).heightPixels;
    }

    /**
     * Get dp of screen in the X dimension.
     * @param context
     * @return in dp unit.
     */
    public static int getScreenDpWidth(Context context) {
        return (int)(getScreenWidth(context) / sDisplayMetrics(context).density);
    }

    /**
     * Get dp of screen in the Y dimension.
     * @param context
     * @return in dp unit.
     */
    public static int getScreenDpHeight(Context context) {
        return (int)(getScreenHeight(context) / sDisplayMetrics(context).density);
    }

    /**
     * Get available screen width, this value changes when the screen's orientation switches between
     * landscape and portrait to reflect the current actual width that's available for your UI.
     * <p>
     * layout-w<N>dp
     * <p>
     * @param context
     * @return in dp unit
     */
    public static int getScreenAvailableDpWidth(Context context) {
        return getScreenDpWidth(context);
    }

    public static String getScreenInfo(Context context) {
        return "Screen Resolution " + getScreenWidth(context) + "x" + getScreenHeight(context) + "pixel"
                + "\n"
                + "Screen Density=" + getScreenDensity(context) + "dpi / " + getScreenDensityName(context)
                + " / " + getScreenDpWidth(context) + "x" + getScreenDpHeight(context) + "dp"
                + "\n"
                + "Screen Available Width=" + ResourceUtils.getScreenAvailableDpWidth(context) + "dp"
                ;
    }
}
