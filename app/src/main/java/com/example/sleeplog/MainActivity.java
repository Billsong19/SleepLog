package com.example.sleeplog;

import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.AppCompactActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

//TODO make unit tests for clock? Would maybe have to encapsulate clock logic for
// tests.

public class MainActivity extends AppCompatActivity {

    TextView timer ;
    Button startButton, pauseButton, resetButton, stopAndLog, settingsButton, sleepLogButton;
    long MillisecondTime, TimeBuff, UpdateTime = 0L ;
    LocalDateTime _startTime;
    Duration _timeElapsed;
    File _sleepLogFile;

    Handler handler;
    long Seconds, Minutes, _hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get internal file directory, points to/creates SleepLog file
        //AppCompatActivity extends Context so we use it in File constructor param

        _sleepLogFile = new File(this.getFilesDir(), "SleepLog.txt");

//        FileOutputStream stream;
//        try {
//            stream = new FileOutputStream(_sleepLogFile);
//            stream.write("HELLO WORLD".getBytes());
//            stream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //read




        timer = (TextView)findViewById(R.id.Timer);
        startButton = (Button)findViewById(R.id.Start);
        pauseButton = (Button)findViewById(R.id.Stop);
        resetButton = (Button)findViewById(R.id.Reset);
        stopAndLog = (Button)findViewById(R.id.FinishedSleeping);
        settingsButton = (Button)findViewById(R.id.Settings);
        sleepLogButton = (Button)findViewById(R.id.SleepLog);


        handler = new Handler() ;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _startTime = LocalDateTime.now();
                handler.postDelayed(runnable, 0);

                resetButton.setEnabled(false);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler.removeCallbacks(runnable);

                resetButton.setEnabled(true);

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.setText("00:00:00");

                SleepSession sleepSession = new SleepSession(_startTime, LocalDateTime.now());
                FileWriter writer;
                BufferedWriter bufferedWriter;
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(_sleepLogFile, true));
                    bufferedWriter.write(sleepSession.getLogString());
                    bufferedWriter.newLine();
                    System.out.println("Wrote " + sleepSession.get_byteArray().toString() +
                            " to SleepLog.txt");
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        final Intent _settings = new Intent(MainActivity.this, SettingsActivity.class);
        settingsButton.setOnClickListener(view -> startActivity(_settings));

        final Intent _sleepLog = new Intent(MainActivity.this, SleepLogActivity.class);
        sleepLogButton.setOnClickListener(view -> startActivity(_sleepLog));
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            SleepSession sleepSession = new SleepSession(_startTime, LocalDateTime.now());

            timer.setText(sleepSession.toString());

            handler.postDelayed(this, 0);
        }
    };}