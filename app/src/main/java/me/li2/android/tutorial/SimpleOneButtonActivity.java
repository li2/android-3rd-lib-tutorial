package me.li2.android.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class SimpleOneButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_one_button);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.simple_one_button_id) void onButtonClick() {
        doAction();
    }

    public abstract void doAction();
}
