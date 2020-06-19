package com.example.sleeplog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
/*TODO implement SleepLogActivity, make it list "bedtime -> waketime: duration date of the night"
   parse lines in txt, pass into new SleepSessions, send to Buttons in recyclerview.
*/
public class SleepLogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleeplog_main);
    }
}