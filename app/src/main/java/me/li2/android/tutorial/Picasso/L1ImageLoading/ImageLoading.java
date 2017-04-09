package me.li2.android.tutorial.Picasso.L1ImageLoading;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

import me.li2.android.tutorial.BasicUI.SimpleListActivity;
import me.li2.android.tutorial.BasicUI.SimpleListFragment;
import me.li2.android.tutorial.Picasso.PicassoTutorial;

/**
 * Created by weiyi on 09/04/2017.
 * https://github.com/li2
 */

public class ImageLoading extends SimpleListActivity {

    private ArrayList<String> mLoadingOptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingOptions = options();
        /**
         * It's weird if not postDelay, the {@link SimpleListFragment#getActivity()} always return null.
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setListData(mLoadingOptions);
            }
        }, 250);
    }

    @Override
    protected String getTitlePrefix() {
        return PicassoTutorial.TAG;
    }

    private ArrayList<String> options() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Url");
        options.add("Resources");
        options.add("File");
        options.add("Uri");
        return options;
    }

    @Override
    protected void onSimpleListItemClick(int position) {
        Toast.makeText(ImageLoading.this, "Loading Image from " + mLoadingOptions.get(position), Toast.LENGTH_SHORT).show();
    }
}
