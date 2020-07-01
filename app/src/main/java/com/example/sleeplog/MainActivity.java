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
    private TextView timer ;
    private Button startButton, pauseButton, finishedSleepingButton, resetButton
            , settingsButton, sleepLogButton;
    private File _sleepLogFile;
    boolean _timerPaused;
    private SleepSession _sleepSession;

    Handler handler;
    long Seconds, Minutes, _hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get internal file directory, points to/creates SleepLog file
        //AppCompatActivity extends Context so we use it in File constructor param

        _sleepLogFile = new File(this.getFilesDir(), "SleepLog.txt");
        Log.d(TAG, "onCreate: sleeplog file path = " + _sleepLogFile.toString() + ".");

        timer = (TextView)findViewById(R.id.Timer);
        startButton = (Button)findViewById(R.id.Start);
        pauseButton = (Button)findViewById(R.id.Stop);
        finishedSleepingButton = (Button)findViewById(R.id.FinishedSleeping);
        resetButton = (Button)findViewById(R.id.Reset);
        settingsButton = (Button)findViewById(R.id.Settings);
        sleepLogButton = (Button)findViewById(R.id.SleepLog);

        TimerUIState _timerUIState = new TimerUIState(startButton,pauseButton,finishedSleepingButton,resetButton);

        handler = new Handler() ;

        if (!_timerPaused)
        {
            finishedSleepingButton.setEnabled(false);
        }


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: startButton clicked");

                if (_timerPaused==false){
                    _sleepSession = new SleepSession(Clock.systemDefaultZone());
                    Log.d(TAG, "onClick: bedTime reset");
                }

                handler.postDelayed(runnable, 0);

                _timerUIState.clickStart();

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler.removeCallbacks(runnable);

                _timerUIState.clickPause();

                _timerPaused = true;
            }
        });

        finishedSleepingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.setText("00:00:00");

                SleepSession sleepSession = new SleepSession(Clock.systemDefaultZone());

                //write the finished sleepSession into SleepLog.txt

                try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(_sleepLogFile, true));
                _sleepSession.writeToFile(bufferedWriter);
//                    bufferedWriter.write(_sleepSession.getLogString());
//                    bufferedWriter.newLine();
//                    Log.d(TAG, "onClick: Wrote "+ _sleepSession.getLogString() + " to SleepLog.txt");
//                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                _timerUIState.clickFinishedSleeping();
                _timerPaused=false;

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                timer.setText("00:00:00");
                _timerPaused=false;
                _timerUIState.clickReset();
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
        if (previousStartTime!=null) {
            _sleepSession = new SleepSession(previousStartTime);
            handler.postDelayed(runnable, 0);
        }
        if (_timerPaused){
            Log.d(TAG, "onRestoreInstanceState: Start Time " + previousStartTime + " restored in paused state");
        }
        else {
            Log.d(TAG, "onRestoreInstanceState: Start Time " + previousStartTime + "restored in unpaused state");

        }
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