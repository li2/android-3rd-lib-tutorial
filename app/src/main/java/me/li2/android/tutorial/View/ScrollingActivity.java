package me.li2.android.tutorial.View;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import me.li2.android.tutorial.R;

public class ScrollingActivity extends AppCompatActivity {
    private static final String TAG = ScrollingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);

        // NOTE21: CollapsingToolbarLayout image also shows in status bar
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     NOTE21: To fix issue that users cannot scroll the Google Map vertically with in a scrollable parent view.
     https://stackoverflow.com/a/17317176/2722270
     */

    @BindView(R.id.rootScrollView)
    NestedScrollView mRootScrollView;

    @OnTouch(R.id.transparentInterceptView)
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        Log.d(TAG, "onTouch " + action);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // Disallow the scrollable parent view to intercept touch events.
                mRootScrollView.requestDisallowInterceptTouchEvent(true);
                // Disable touch on transparent view.
                return false;

            case MotionEvent.ACTION_UP:
                // Allow the scrollable parent view to intercept touch events.
                mRootScrollView.requestDisallowInterceptTouchEvent(false);
                return true;

            default:
                return true;
        }
    }
}
