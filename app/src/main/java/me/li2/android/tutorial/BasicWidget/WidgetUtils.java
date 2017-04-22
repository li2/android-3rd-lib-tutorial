package me.li2.android.tutorial.BasicWidget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by weiyi on 22/04/2017.
 * https://github.com/li2
 */

public class WidgetUtils {
    /**
     * Popup ImageView, give its constructor a non-dialog theme can show ImageView in full screen. and
     * android.R.style.ThemeOverlay_Material_Dark will not change the statusBar's color.
     * http://stackoverflow.com/a/24946375/2722270
     * http://stackoverflow.com/a/10173576/2722270
     *
     * @param context context of attached activity.
     *                Instead of getApplicationContext(), just use ActivityName.this.
     *                android.view.WindowManager$BadTokenException: Unable to add window --
     *                token null is not for an application
     *                at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:85)
     */
    public static ImageView popupImageView(Context context) {
        Dialog builder = new Dialog(context, android.R.style.Theme_Material_Light_DarkActionBar);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.BLACK));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(context);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
        return imageView;
    }
}
