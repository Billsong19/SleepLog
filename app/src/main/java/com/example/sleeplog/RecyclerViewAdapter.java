package com.example.sleeplog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    //log for debugging
    private static final String TAG = "RecyclerViewAdapter";

//    private SleepLog _sleepLog;
    private Context _context;
    private List<SleepSessionEntity> _sleepLog;

    public RecyclerViewAdapter(Context context) {
        Log.d(TAG, "RecyclerViewAdapter: created");
        _context = context;

        AppDatabase db = AppDatabase.getInstance(_context);
        SleepLogDao sleepDao = db.sleepLogDao();
        _sleepLog = sleepDao.getAll();
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

        //set textviews in ViewHolder holder at position to their appropriate values.
        SleepSessionEntity sleepSesh = _sleepLog.get(position);
        String durString = String.format("%02d", sleepSesh._duration.toHours());
        String bedString = sleepSesh._bedTime.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString();
        String wakeString = sleepSesh._wakeTime.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString();

        holder._timeDataView.setText(durString + "Hrs  " + bedString + "=>" + wakeString);
        holder._dateDataView.setText(sleepSesh._wakeTime.toLocalDate().minusDays(1).toString().substring(2));
        holder._deleteButton.setOnClickListener(view->
            {
                AppDatabase db = AppDatabase.getInstance(_context);
                SleepLogDao sleepDao = db.sleepLogDao();
                sleepDao.delete(sleepSesh);
                _sleepLog.remove(sleepSesh);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());
            });
    }

    @Override
    public int getItemCount() {
        return _sleepLog.size();
    }

    //holds each recyclerview item in memory
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView _timeDataView;
        TextView _dateDataView;
        ImageButton _deleteButton;
        ConstraintLayout _parentLayout;


        public ViewHolder(View view){
            super(view);
            _timeDataView = view.findViewById(R.id.sleep_time_data);
            _dateDataView = view.findViewById(R.id.sleep_date_data);
            _deleteButton = view.findViewById(R.id.delete_button);
            _parentLayout = view.findViewById(R.id.parent_layout);

        }
    }
}
