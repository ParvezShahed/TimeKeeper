package com.time.code.timekeeper.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.time.code.timekeeper.R;
import com.time.code.timekeeper.model.TimeCard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shahed on 14/07/2016.
 */

public class TimeCardsAdapter extends RecyclerView.Adapter<TimeCardsAdapter.ViewHolder> {
    private List<TimeCard> mDataset;
    OnTimeCardClickListener mClickListemer;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextViewDate;
        public TextView mTextViewTime;
        public ViewHolder(View view) {
            super(view);
            mTextViewDate = (TextView) view.findViewById(R.id.timecard_item_date);
            mTextViewTime = (TextView) view.findViewById(R.id.timecard_item_time);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            TimeCardsAdapter.this.onItemClick(getAdapterPosition());
        }
    }

    public TimeCardsAdapter(OnTimeCardClickListener listener) {
        this.mClickListemer = listener;
        mDataset = new ArrayList<>();
    }

    public void updateDataset(List<TimeCard> myDataset){
        mDataset = myDataset;
        notifyDataSetChanged();
    }

    @Override
    public TimeCardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timecard_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String dateStr = mDataset.get(position).getmOccurrence();
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("EEE, MMM d, ''yy");
        Date date;
        String outDateStr = null;
        try {
             date = sdfInput.parse(dateStr);
             outDateStr = sdfOutput.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mTextViewDate.setText(outDateStr);
        String workingHour = mDataset.get(position).getmTotalWorkedHours();
        if(workingHour == null){
            holder.mTextViewTime.setText("Punched in");
        }else{
            workingHour = workingHour.substring(0, workingHour.indexOf('.'));
            holder.mTextViewTime.setText( workingHour+ "h");
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void onItemClick(int position){
        mClickListemer.onClick(mDataset.get(position));
    }
}
