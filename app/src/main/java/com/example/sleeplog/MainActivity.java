package com.example.sleeplog;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
//import android.support.v7.app.AppCompactActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView timer ;
    Button startButton, pauseButton, resetButton, stopAndLog, settingsButton;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, Hours ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File _sleepLog = new File(this.getFilesDir(), "SleepLog");



        timer = (TextView)findViewById(R.id.Timer);
        startButton = (Button)findViewById(R.id.Start);
        pauseButton = (Button)findViewById(R.id.Stop);
        resetButton = (Button)findViewById(R.id.Reset);
        stopAndLog = (Button)findViewById(R.id.FinishedSleeping);
        settingsButton = (Button)findViewById(R.id.Settings);

        handler = new Handler() ;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                resetButton.setEnabled(false);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                resetButton.setEnabled(true);

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;

                timer.setText("00:00:00");

            }
        });
        final Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
        settingsButton.setOnClickListener(view -> startActivity(settings));
//        settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//               startActivity(settings);
//
//            }
//        });
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Hours = Seconds / 3600;

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            timer.setText("" + String.format("%02d", Hours) + ":" + String.format("%02d", Minutes)
                    + ":" + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }
    };}
