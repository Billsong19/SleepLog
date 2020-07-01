package com.example.sleeplog;

import android.widget.Button;

/*
     UI Button state table, columns=buttons, rows=states
                        Start   Stop    Reset/FinishedSleeping
     unstarted          1       0       0
     ongoing            0       1       0
     pausedOngoing      1       0       1
     */

public class TimerUIState {
    private Button _start, _pause, _finishedSleeping, _reset;
    public TimerUIState(Button start, Button pause, Button finishedSleeping, Button reset)
    {
        _start = start;
        _pause = pause;
        _finishedSleeping = finishedSleeping;
        _reset = reset;
    }

    public void clickStart()
    {
        _start.setEnabled(false);
        _start.setText("Resume");
        _pause.setEnabled(true);
        _reset.setEnabled(false);
        _finishedSleeping.setEnabled(false);
    }

    public void clickPause(){
        _start.setEnabled(true);
        _pause.setEnabled(false);
        _reset.setEnabled(true);
        _finishedSleeping.setEnabled(true);
    }

    public void clickFinishedSleeping() {
        _start.setEnabled(true);
        _start.setText("Start");
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
