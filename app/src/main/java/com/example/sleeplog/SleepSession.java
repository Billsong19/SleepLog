package com.example.sleeplog;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SleepSession {
    private Duration _sleepDuration;
    private LocalDateTime _bedTime, _wakeTime;
    private long _hours, _minutes, _seconds ;
//    private TemporalUnit ChronoUnit.MINUTE;

//    public SleepSession(LocalDateTime bedTime, LocalDateTime wakeTime) {
//
//        _sleepDuration = Duration.between(bedTime, wakeTime);
//        _bedTime = bedTime;
//        _wakeTime = wakeTime;
//        _hours = (long)_sleepDuration.toHours();
//        _minutes = (long)_sleepDuration.minusHours(_hours).toMinutes();
//        _seconds = (long)_sleepDuration.minusHours(_hours).minusMinutes(_minutes).getSeconds();
//    }

    public String getLogString() {
        return _bedTime.toString() + " " + _wakeTime.toString();
    }

    @Override
    public String toString() {
        return String.format("%02d", _hours) + ":" + String.format("%02d", _minutes)
                + ":" + String.format("%02d", _seconds);
    }

    public static SleepSession parse(String text) {
        //split the string
        String[] dateTimeStrings = text.split(" ");
        SleepSession sleepSession = new SleepSession(dateTimeStrings[0]);
        sleepSession.updateWakeTime(dateTimeStrings[1]);
        return sleepSession;
    }

    public String getTimeDataString() {
        return Long.toString(_hours) + " Hrs" + _bedTime.toLocalTime().truncatedTo(ChronoUnit.MINUTES) + "->" +
                _wakeTime.toLocalTime().truncatedTo(ChronoUnit.MINUTES);
    }

    public String getDateDataString() {
        return _bedTime.toLocalDate().toString().substring(2);
    }

    //refactor attempt
    public SleepSession(Clock clock)
    {
        _bedTime = LocalDateTime.now(clock);
    }

    public SleepSession(String bedTime){
        _bedTime = LocalDateTime.parse(bedTime);
    }

    public void updateWakeTime(Clock clock)
    {
        updateWakeTime(LocalDateTime.now(clock));
    }

    public void updateWakeTime(String wakeTime){
        updateWakeTime(LocalDateTime.parse(wakeTime));
    }

    private void updateWakeTime(LocalDateTime wakeTime) {
        _wakeTime = wakeTime;
        _sleepDuration = Duration.between(_bedTime, _wakeTime);
        _hours = (long)_sleepDuration.toHours();
        _minutes = (long)_sleepDuration.minusHours(_hours).toMinutes();
        _seconds = (long)_sleepDuration.minusHours(_hours).minusMinutes(_minutes).getSeconds();
    }

    public String timeElapsedString()
    {
        return String.format("%02d", _hours) + ":" + String.format("%02d", _minutes)
                + ":" + String.format("%02d", _seconds);
    }

    public LocalDateTime getBedTime(){
        return _bedTime;
    }
}