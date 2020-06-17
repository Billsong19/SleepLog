package com.example.sleeplog;

import android.os.Handler;
import android.os.SystemClock;
//import android.support.v7.app.AppCompactActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView timer ;
    Button start, pause, reset, stopAndLog;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, Hours ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView)findViewById(R.id.Timer);
        start = (Button)findViewById(R.id.Start);
        pause = (Button)findViewById(R.id.Stop);
        reset = (Button)findViewById(R.id.Reset);
        stopAndLog = (Button)findViewById(R.id.FinishedSleeping);

        handler = new Handler() ;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
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

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Hours = Seconds / 3600;

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            timer.setText("" + Hours + ":" + Minutes + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };}
