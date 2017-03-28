package me.li2.android.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class SimpleOneButtonActivity extends AppCompatActivity {

    @BindView(R.id.simple_one_button_id) Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_one_button);
        ButterKnife.bind(this);
        setTitle(getClass().getSimpleName());
        mButton.setText(getButtonText());
    }

    @OnClick(R.id.simple_one_button_id) void onButtonClick() {
        doAction();
    }

    public abstract void doAction();

    protected String getButtonText() {
        return "DO";
    }
}
