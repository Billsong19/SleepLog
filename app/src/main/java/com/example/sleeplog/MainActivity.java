package com.example.sleeplog;

import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.AppCompactActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;

//TODO make unit tests for timer. Would maybe have to refactor clock logic for
// tests.

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView timer ;
    Button startButton, pauseButton, finishedSleepingButton, resetButton
            , settingsButton, sleepLogButton;
    File _sleepLogFile;
    boolean _timerPaused;
    SleepSession _sleepSession;

    Handler handler;
    long Seconds, Minutes, _hours;
    /*
     UI Button state table, columns=buttons, rows=states
                        Start   Stop    Reset
     unstarted          1       0       0
     ongoing            0       1       0
     pausedOngoing      1       0       1
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get internal file directory, points to/creates SleepLog file
        //AppCompatActivity extends Context so we use it in File constructor param

        _sleepLogFile = new File(this.getFilesDir(), "SleepLog.txt");

        timer = (TextView)findViewById(R.id.Timer);
        startButton = (Button)findViewById(R.id.Start);
        pauseButton = (Button)findViewById(R.id.Stop);
        finishedSleepingButton = (Button)findViewById(R.id.FinishedSleeping);
        resetButton = (Button)findViewById(R.id.Reset);
        settingsButton = (Button)findViewById(R.id.Settings);
        sleepLogButton = (Button)findViewById(R.id.SleepLog);

        if(savedInstanceState != null){
            String previousStartTime = savedInstanceState.getString("Start Time");
            _timerPaused = savedInstanceState.getBoolean("Timer Paused");
            _sleepSession = new SleepSession(previousStartTime);
            if (_timerPaused){
                Log.d(TAG, "onCreate: Start Time " + previousStartTime + " restored in paused state");
            }
            Log.d(TAG, "onCreate: Start Time " + previousStartTime + "restored in unpaused state");

        }

        if (!_timerPaused)
        {
            finishedSleepingButton.setEnabled(false);
        }
        handler = new Handler() ;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: startButton clicked");

                if (_timerPaused==false){
                    _sleepSession = new SleepSession(Clock.systemDefaultZone());
                    Log.d(TAG, "onClick: bedTime reset");
                }

                handler.postDelayed(runnable, 0);

                startButton.setEnabled(false);
                startButton.setText("Resume");
                pauseButton.setEnabled(true);
                finishedSleepingButton.setEnabled(false);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler.removeCallbacks(runnable);

                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                finishedSleepingButton.setEnabled(true);
                _timerPaused = true;
            }
        });

        finishedSleepingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.setText("00:00:00");

                SleepSession sleepSession = new SleepSession(Clock.systemDefaultZone());

                //write the finished sleepSession into SleepLog.txt
                FileWriter writer;
                BufferedWriter bufferedWriter;
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(_sleepLogFile, true));
                    bufferedWriter.write(_sleepSession.getLogString());
                    bufferedWriter.newLine();
                    Log.d(TAG, "onClick: Wrote "+ _sleepSession.getLogString() + " to SleepLog.txt");
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startButton.setEnabled(true);
                _timerPaused=false;
                startButton.setText("Start");
                finishedSleepingButton.setEnabled(false);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                timer.setText("00:00:00");
                startButton.setEnabled(true);
                _timerPaused=false;
                startButton.setText("Start");
                finishedSleepingButton.setEnabled(false);
            }

        });

        final Intent _settings = new Intent(MainActivity.this, SettingsActivity.class);
        settingsButton.setOnClickListener(view -> startActivity(_settings));

        final Intent _sleepLog = new Intent(MainActivity.this, SleepLogActivity.class);
        sleepLogButton.setOnClickListener(view -> startActivity(_sleepLog));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        String previousStartTime = savedInstanceState.getString("Start Time");
        _timerPaused = savedInstanceState.getBoolean("Timer Paused");
        _sleepSession = new SleepSession(previousStartTime);
        if (_timerPaused){
            Log.d(TAG, "onRestoreInstanceState: Start Time " + previousStartTime + " restored in paused state");
        }
        Log.d(TAG, "onRestoreInstanceState: Start Time " + previousStartTime + "restored in unpaused state");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState)
    {
        if (_sleepSession != null) {
            outState.putString("Start Time", _sleepSession.getBedTime().toString());
            outState.putBoolean("Timer Paused", _timerPaused);
            Log.d(TAG, "onSaveInstanceState: " + _sleepSession.getBedTime().toString() + " saved.");
        }
        super.onSaveInstanceState(outState);

    }


    public Runnable runnable = new Runnable() {

        public void run() {

            _sleepSession.updateWakeTime(Clock.systemDefaultZone());

            timer.setText(_sleepSession.timeElapsedString());

            handler.postDelayed(this, 0);
        }
    };
}