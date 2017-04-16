package me.li2.android.tutorial.BasicUI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.li2.android.tutorial.R;

public class SimpleOneButtonActivity extends BasicFragmentContainerActivity {
    @Override
    protected Fragment createFragment() {
        return new SimpleOneButtonFragment();
    }

    protected void doAction() {
    }

    protected String getButtonText() {
        return "DO";
    }

    /*
     SimpleOneButtonFragment must be a public static class to be  properly recreated from instance state.
     What is the design logic behind Fragments as static inner classes vs standalone public classes?
     http://stackoverflow.com/questions/22191792#22191792
      */
    @SuppressWarnings("validFragment")
    public static class SimpleOneButtonFragment extends Fragment {
        private SimpleOneButtonActivity mAttachedActivity;
        @BindView(R.id.simple_one_button) Button mButton;

        public SimpleOneButtonFragment() {

        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof SimpleOneButtonActivity) {
                mAttachedActivity = (SimpleOneButtonActivity) context;
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_simple_one_button, container, false);
            ButterKnife.bind(this, view);
            if (mAttachedActivity != null) {
                mButton.setText(mAttachedActivity.getButtonText());
            }
            return view;
        }

        @OnClick(R.id.simple_one_button)
        void onButtonClick() {
            if (mAttachedActivity != null) {
                mAttachedActivity.doAction();
            }
        }
    }
}
