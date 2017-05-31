/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package me.li2.android.tutorial.BasicWidget.SimpleRecyclerView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.li2.android.tutorial.R;

/**
 * Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager} and a
 * {@link GridLayoutManager}.
 */
public class SimpleRecyclerFragment extends Fragment {

    private static final String TAG = "SimpleRecyclerFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 3;
    private static final int DATASET_COUNT = 21;

    public interface OnItemClickListener {
        void onItemClick(final int position);
    }

    public enum LayoutType {
        GRID,
        LINEAR_HORIZONTAL,
        LINEAR_VERTICAL,
    }

    private RecyclerView mRecyclerView;
    private SimpleRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LayoutType mCurrentLayoutType;
    private String[] mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_simple_recycler_view, container, false);
        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutType = LayoutType.LINEAR_VERTICAL;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutType = (LayoutType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        setLayout(mCurrentLayoutType);

        mAdapter = new SimpleRecyclerAdapter(mDataset);
        // Set SimpleRecyclerAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)

        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutType Type of layout manager to switch to.
     */
    public void setLayout(LayoutType layoutType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutType) {
            case GRID:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutType = LayoutType.GRID;
                break;

            case LINEAR_HORIZONTAL:
            case LINEAR_VERTICAL:
                LinearLayoutManager lm = new LinearLayoutManager(getActivity());
                int orientation = (layoutType == LayoutType.LINEAR_HORIZONTAL)
                        ? LinearLayoutManager.HORIZONTAL
                        : LinearLayoutManager.VERTICAL;
                lm.setOrientation(orientation);
                mLayoutManager = lm;
                mCurrentLayoutType = layoutType;
                break;

            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutType = LayoutType.LINEAR_VERTICAL;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutType);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(onItemClickListener);
        }
    }

    public void setDataset(String[] dataset) {
        mDataset = dataset;
        if (mAdapter != null) {
            mAdapter.setDataSet(dataset);
        }
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        mDataset = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataset[i] = "Element #" + i;
        }
    }
}
