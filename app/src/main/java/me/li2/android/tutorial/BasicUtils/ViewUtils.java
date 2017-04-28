package me.li2.android.tutorial.BasicUtils;

import android.view.View;
import android.view.ViewGroup;

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
}
