package me.li2.android.tutorial.BasicUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.ButterKnife;
import me.li2.android.tutorial.R;

public abstract class BasicFragmentContainerActivity extends AppCompatActivity {
    protected Fragment mFragment;

    protected abstract Fragment createFragment();

    protected void parseIntent() {}

    protected String getTitlePrefix() {
        return "";
    }

    protected int getLayoutResId() {
        return R.layout.activity_single_fragment;
    }

    protected int getOptionsMenuRes() {
        return R.menu.tutorial_options;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        setContentView(getLayoutResId());
        ButterKnife.bind(this);

        // Set actionbar title
        String titlePrefix = getTitlePrefix();
        String title = getClass().getSimpleName();
        if (titlePrefix != null && titlePrefix.length() > 0) {
            title = titlePrefix + "/" + title;
        }
        setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the FragmentManager.
        FragmentManager fm = getSupportFragmentManager();
        // Ask the FragmentManager for the fragment with a container view ID, 
        // If this fragment is already in the list, the FragmentManager will return it,
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        // Or create a new CrimeFragment,
        if (fragment == null) {
            fragment = createFragment();
            // Create a new fragment transaction, include one add operation in it, and then commit it.
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
            // since commit() doesn't happen immediately, so run this function before
            // findFragmentById() or findFragmentByTag(), then we can get the fragment.
            fm.executePendingTransactions();
            // instead of findFragmentBy..., it's a convenient way to use a filed to hold the committed fragment.
            mFragment = fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(getOptionsMenuRes(), menu);
        return true;
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

    protected void replaceFragment(Fragment newFragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentContainer, newFragment).commit();
        fm.executePendingTransactions();
        mFragment = newFragment;
    }
}
