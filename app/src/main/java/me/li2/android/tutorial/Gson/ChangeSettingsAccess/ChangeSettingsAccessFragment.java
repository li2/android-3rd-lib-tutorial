package me.li2.android.tutorial.Gson.ChangeSettingsAccess;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.li2.android.tutorial.R;

/**
 * Created by weiyi on 24/04/2017.
 * https://github.com/li2
 */

public class ChangeSettingsAccessFragment extends Fragment {
    private SettingsAccessAdapter mAdapter;
    private OnSettingsAccessItemClickListener mOnSettingsAccessItemClickListener;

    public interface OnSettingsAccessItemClickListener {
        void onItemClick(SettingsAccessItem item);
        void onCheckedChanged(SettingsAccessItem item, boolean checked);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SettingsAccessAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_access, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.settings_access_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    public void setItems(ArrayList<SettingsAccessItem> items) {
        mAdapter.setItems(items);
    }

    public void setOnSettingsAccessItemClickListener(OnSettingsAccessItemClickListener l) {
        mOnSettingsAccessItemClickListener = l;
    }


    /**
     * Adapter -------------------------------------------------------------------------------------
     */
    private class SettingsAccessAdapter extends RecyclerView.Adapter<SettingsAccessViewHolder> {
        private Context mContext;
        private ArrayList<SettingsAccessItem> mItems;

        public SettingsAccessAdapter(Context context) {
            mContext = context;
        }

        public void setItems(ArrayList<SettingsAccessItem> items) {
            mItems = items;
            notifyDataSetChanged();
        }

        @Override
        public SettingsAccessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.view_settings_access_item, parent, false);
            return new SettingsAccessViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SettingsAccessViewHolder holder, int position) {
            holder.bindSettingsAccessItem(mItems.get(position), position);
        }

        @Override
        public int getItemCount() {
            return (mItems != null) ? mItems.size() : 0;
        }
    }

    /**
     * View Holder ---------------------------------------------------------------------------------
     */
    private class SettingsAccessViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SettingsAccessItem mItem;
        private CheckBox mAdminCheckBox;
        private TextView mTitleView;
        private ImageView mNextArrowView;

        public SettingsAccessViewHolder(View itemView) {
            super(itemView);
            mAdminCheckBox = (CheckBox) itemView.findViewById(R.id.settings_access_only_admin_checkBox);
            mAdminCheckBox.setOnCheckedChangeListener(mOnCheckedChangeListener);
            mTitleView = (TextView) itemView.findViewById(R.id.settings_access_title_textView);
            mNextArrowView = (ImageView) itemView.findViewById(R.id.settings_access_next_arrowView);
            itemView.setOnClickListener(this);
        }

        public void bindSettingsAccessItem(SettingsAccessItem item, int position) {
            if (item != null) {
                mItem = item;
                mAdminCheckBox.setChecked(item.isAdminAccessOnly());
                mTitleView.setText(item.mTitle);
                mNextArrowView.setVisibility(item.hasSubitems() ? View.VISIBLE : View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            if (mItem.hasSubitems()) {
                if (mOnSettingsAccessItemClickListener != null) {
                    mOnSettingsAccessItemClickListener.onItemClick(mItem);
                }
            }
        }

        private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener =
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        if (compoundButton.isPressed() && mOnSettingsAccessItemClickListener != null) {
                            mOnSettingsAccessItemClickListener.onCheckedChanged(mItem, checked);
                        }
                    }
                };
    }
}
