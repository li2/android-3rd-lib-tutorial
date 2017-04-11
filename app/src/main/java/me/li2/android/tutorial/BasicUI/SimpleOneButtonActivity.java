package me.li2.android.tutorial.BasicUI;

import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.li2.android.tutorial.R;

public abstract class SimpleOneButtonActivity extends BasicActivity {

    @BindView(R.id.simple_one_button_id) Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mButton.setText(getButtonText());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_one_button;
    }

    @OnClick(R.id.simple_one_button_id) void onButtonClick() {
        doAction();
    }

    public abstract void doAction();

    protected String getButtonText() {
        return "DO";
    }
}
