package me.li2.android.tutorial.BasicUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiyi on 28/04/2017.
 * https://github.com/li2
 */

public class ViewUtils {
    /**
     * Get all child views recursively.
     * http://stackoverflow.com/a/18669307/2722270
     * @param view
     * @return
     */
    public static List<View> getAllChildrenBFS(View view) {
        List<View> visited = new ArrayList<>();
        List<View> unvisited = new ArrayList<>();
        unvisited.add(view);

        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            visited.add(child);
            if (!(child instanceof ViewGroup)) continue;
            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i=0; i<childCount; i++) unvisited.add(group.getChildAt(i));
        }

        return visited;
    }

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
