package com.example.sleeplog;

import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.AppCompactActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

//TODO make unit tests for timer. Would maybe have to refactor clock logic for
// tests.

//TODO implement observer pattern for timer? Would make for easier user-side ui customisation

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView timer ;
    private Button startButton;
    private Button finishedSleepingButton;
    private Button resetButton;
    private Button settingsButton;
    private Button sleepLogButton;
    private File _sleepLogFile;
    boolean _timerPaused;
//    private SleepSession _sleepSession;
    private SleepSessionEntity _sleepSessionEnt;

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

        //initialise buttons
        timer = (TextView)findViewById(R.id.Timer);
        startButton = (Button)findViewById(R.id.Start);
        finishedSleepingButton = (Button)findViewById(R.id.FinishedSleeping);
        resetButton = (Button)findViewById(R.id.Reset);
        settingsButton = (Button)findViewById(R.id.Settings);
        sleepLogButton = (Button)findViewById(R.id.SleepLog);

        TimerUIState _timerUIState = new TimerUIState(startButton,
                finishedSleepingButton,resetButton);

        handler = new Handler() ;

        if (!_timerPaused)
        {
            finishedSleepingButton.setEnabled(false);
        }


        startButton.setOnClickListener(view ->
                {
                    Log.d(TAG, "onClick: startButton clicked");


//                        _sleepSession = new SleepSession(Clock.systemDefaultZone());
                        _sleepSessionEnt = new SleepSessionEntity(LocalDateTime.now());
                        Log.d(TAG, "onClick: bedTime reset");

                        _timerPaused = false;
                    handler.postDelayed(runnable, 0);

                    _timerUIState.clickStart();

                }
        );

        finishedSleepingButton.setOnClickListener(view ->
                {
                    timer.setText("00:00:00");

                    _sleepSessionEnt.set_wakeTime(LocalDateTime.now());
                    //write the finished sleepSession into SleepLog.txt
//                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(_sleepLogFile,
//                                true));
//                        _sleepSession.writeToFile(bufferedWriter);

                    AppDatabase db = AppDatabase.getInstance(this);
                    SleepLogDao sleepDao = db.sleepLogDao();

                    sleepDao.insertAll(_sleepSessionEnt);
                    _sleepSessionEnt = null;


                    _timerUIState.clickFinishedSleeping();

                    _timerPaused=true;

                }
        );

        resetButton.setOnClickListener(view ->
                {
                    timer.setText("00:00:00");
                    _timerPaused=true;
                    _timerUIState.clickReset();

                    _sleepSessionEnt = null;
                }
        );

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
//            _sleepSession = new SleepSession(previousStartTime);
            handler.postDelayed(runnable, 0);
        }
        if (_timerPaused){
            Log.d(TAG, "onRestoreInstanceState: Start Time " + previousStartTime +
                    " restored in paused state");
        }
        else {
            Log.d(TAG, "onRestoreInstanceState: Start Time " + previousStartTime +
                    "restored in unpaused state");

        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState)
    {
        if (_sleepSessionEnt != null) {
            outState.putString("Start Time", _sleepSessionEnt._bedTime.toString());
            outState.putBoolean("Timer Paused", _timerPaused);
            Log.d(TAG, "onSaveInstanceState: " + _sleepSessionEnt._bedTime.toString() + " saved.");
        }
        super.onSaveInstanceState(outState);

    }


    public Runnable runnable = new Runnable() {

        public void run() {
            if (!_timerPaused){
                long s = _sleepSessionEnt.timeElapsed().getSeconds();
                timer.setText(String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60)));

                handler.postDelayed(this, 0);
            }

        }
    };

    public static class TimerUIState {
        private Button _start, _pause, _finishedSleeping, _reset;
        public TimerUIState(Button start, Button finishedSleeping, Button reset)
        {
            _start = start;
            _finishedSleeping = finishedSleeping;
            _reset = reset;
        }

        public void clickStart()
        {
            _start.setEnabled(false);
//            _start.setText("Resume");
//            _pause.setEnabled(true);
            _reset.setEnabled(true);
            _finishedSleeping.setEnabled(true);
        }

        public void clickPause(){
            _start.setEnabled(true);
//            _pause.setEnabled(false);
            _reset.setEnabled(true);
            _finishedSleeping.setEnabled(true);
        }

        public void clickFinishedSleeping() {
            _start.setEnabled(true);
//            _start.setText("Start");
            _reset.setEnabled(false);
            _finishedSleeping.setEnabled(false);
        }

        public void clickReset() {
            _start.setEnabled(true);
            _start.setText("Start");
            _reset.setEnabled(false);
            _finishedSleeping.setEnabled(false);
        }
    }
}