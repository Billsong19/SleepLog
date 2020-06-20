package com.example.sleeplog;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
/*TODO implement SleepLogActivity, make it list "bedtime -> waketime: duration date of the night"
   parse lines in txt, pass into new SleepSessions, send to Buttons in recyclerview.
*/

public class SleepLogActivity extends AppCompatActivity {
    private static final String TAG = "SleepLogActivity";

    private SleepLog _sleepLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleeplog_main);
        Log.d(TAG, "onCreate: started");
        File _sleepLogFile = new File(this.getFilesDir(), "SleepLog.txt");
        _sleepLog = new SleepLog(_sleepLogFile);
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview");
        RecyclerView recyclerView = findViewById(R.id.sleeplog_recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(_sleepLog, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}