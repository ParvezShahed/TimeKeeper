package com.time.code.timekeeper.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time.code.timekeeper.R;
import com.time.code.timekeeper.model.TimeCard;

/**
 * Created by shahed on 16/07/2016.
 */
public class TimesFragment  extends Fragment {
    private RecyclerView mRecyclerView;
    private TimesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static TimeCard mTimeCard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_times, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.times_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mTimeCard != null) {
            mAdapter = new TimesAdapter(mTimeCard.getTimeEntries());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void setTimeCard(TimeCard timeCard){
        mTimeCard = timeCard;
    }
}
