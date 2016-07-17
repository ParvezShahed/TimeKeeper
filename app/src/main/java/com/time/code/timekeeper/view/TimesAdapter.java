package com.time.code.timekeeper.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.time.code.timekeeper.R;
import com.time.code.timekeeper.model.TimeEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahed on 16/07/2016.
 */
public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.ViewHolder>{
    private List<TimeEntry> mDataset;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextViewInOut;
        public TextView mTextViewTime;
        public ViewHolder(View view) {
            super(view);
            mTextViewInOut = (TextView) view.findViewById(R.id.time_item_in_out);;
            mTextViewTime = (TextView) view.findViewById(R.id.time_item_time);
        }

        @Override
        public void onClick(View v) {
            TimesAdapter.this.onItemClick(getAdapterPosition());
        }
    }

    public TimesAdapter(ArrayList<TimeEntry> dataset) {
        mDataset = dataset;
    }

    public void updateDataset(List<TimeEntry> myDataset){
        mDataset = myDataset;
        notifyDataSetChanged();
    }

    @Override
    public TimesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position % 2 == 0) {
            holder.mTextViewInOut.setText("In");
        } else {
            holder.mTextViewInOut.setText("Out");
        }
        String timeStr = mDataset.get(position).getTime();
        timeStr = timeStr.substring(timeStr.indexOf("T") + 1,timeStr.indexOf("."));
        holder.mTextViewTime.setText(timeStr);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void onItemClick(int position){
    }
}
