package com.example.sleeplog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    //log for debugging
    private static final String TAG = "RecyclerViewAdapter";

    private SleepLog _sleepLog;
    private Context _context;


    public RecyclerViewAdapter(SleepLog sleepLog, Context context) {
        _sleepLog = sleepLog;
        _context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sleeplog_listitem,parent,
                false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder._timeDataView.setText(_sleepLog.get_sleepSession(position).getTimeDataString());
        holder._dateDataView.setText(_sleepLog.get_sleepSession(position).getDateDataString());
    }

    @Override
    public int getItemCount() {
        return _sleepLog.getSize();
    }

    //holds each recyclerview item in memory
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView _timeDataView;
        TextView _dateDataView;
        ConstraintLayout _parentLayout;

        public ViewHolder(View view){
            super(view);
            _timeDataView = view.findViewById(R.id.sleep_time_data);
            _dateDataView = view.findViewById(R.id.sleep_date_data);
            _parentLayout = view.findViewById(R.id.parent_layout);
        }
    }
}
