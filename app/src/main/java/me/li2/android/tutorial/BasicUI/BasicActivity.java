package me.li2.android.tutorial.BasicUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

/**
 * Created by weiyi on 09/04/2017.
 * https://github.com/li2
 */

public abstract class BasicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResId() > 0) {
            setContentView(getLayoutResId());
        }
        ButterKnife.bind(this);
        String titlePrefix = getTitlePrefix();
        String title = getClass().getSimpleName();
        if (titlePrefix != null && titlePrefix.length() > 0) {
            title = titlePrefix + "/" + title;
        }
        setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    protected String getTitlePrefix() {
        return "";
    }

    protected int getLayoutResId() {
        return -1;
    }
}
