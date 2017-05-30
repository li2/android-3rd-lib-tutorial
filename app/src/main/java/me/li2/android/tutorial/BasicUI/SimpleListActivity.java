package me.li2.android.tutorial.BasicUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by weiyi on 25/03/2017.
 * https://github.com/li2
 */

public abstract class SimpleListActivity extends BasicFragmentContainerActivity {

    private ArrayList<String> mListData;

    protected void onSimpleListItemClick(final int position) {}

    /**
     * For initial data, we can just override initListData().
     *
     * To pass arguments to {@link SimpleListFragment} before it's created and committed,
     * the subclass who extends {@link SimpleListActivity} should override this method to provide:
     * @return the initial list data.
     */
    protected ArrayList<String> initListData() {
        return null;
    }

    /**
     * To update {@link SimpleListFragment} after it's created.
     * @param titles
     */
    protected void setListData(List<String> titles) {
        if (mFragment != null && mFragment instanceof SimpleListFragment) {
            ((SimpleListFragment) mFragment).setListData(titles);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** call this method before create & commit fragment, that's before super.onCreate */
        mListData = initListData();

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /** it's better set listener here */
        if (mFragment != null && mFragment instanceof SimpleListFragment) {
            ((SimpleListFragment) mFragment).setOnSimpleListItemClickListener(new SimpleListFragment.OnSimpleListItemClickListener() {
                @Override
                public void onListItemClick(int position) {
                    onSimpleListItemClick(position);
                }
            });
        }
    }

    @Override
    protected Fragment createFragment() {
        SimpleListFragment fragment;
        if (mListData != null) {
            fragment = SimpleListFragment.newInstance(mListData);
        } else {
            fragment = new SimpleListFragment();
        }
        return fragment;
    }


    /**
     * SimpleListFragment
     */
    public static class SimpleListFragment extends ListFragment {
        private static final String ARG_KEY_TITLES = "arg_key_titles";
        private static final String SIMPLE_DATA_LIST_KEY_TITLE = "title";
        private OnSimpleListItemClickListener mOnSimpleListItemClickListener;

        public static SimpleListFragment newInstance(ArrayList<String> titles) {
            Bundle args = new Bundle();
            args.putStringArrayList(ARG_KEY_TITLES, titles);

            SimpleListFragment fragment = new SimpleListFragment();
            fragment.setArguments(args);
            return fragment;
        }

        public interface OnSimpleListItemClickListener {
            void onListItemClick(final int position);
        }

        public void setOnSimpleListItemClickListener(SimpleListFragment.OnSimpleListItemClickListener l) {
            mOnSimpleListItemClickListener = l;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
            Bundle args = getArguments();
            if (args != null) {
                List<String> titles = args.getStringArrayList(ARG_KEY_TITLES);
                if (titles != null) {
                    setListData(titles);
                }
            }
        }

        public void setListData(List<String> titles) {
            List<Map<String, String>> data = new ArrayList<>();
            for (String title : titles) {
                Map<String, String> item = new HashMap<>();
                item.put(SIMPLE_DATA_LIST_KEY_TITLE, title);
                data.add(item);
            }

            ListAdapter listAdapter = new SimpleAdapter(getActivity(), data, android.R.layout.simple_list_item_1,
                    new String[] {SIMPLE_DATA_LIST_KEY_TITLE}, new int[] {android.R.id.text1});
            setListAdapter(listAdapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            if (mOnSimpleListItemClickListener != null) {
                mOnSimpleListItemClickListener.onListItemClick(position);
            }
        }
    }
}
