package com.example.sleeplog;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SleepSession {
    private Duration _sleepDuration;
    private LocalDateTime _bedTime, _wakeTime;
    private long _hours, _minutes, _seconds ;

    public SleepSession(LocalDateTime bedTime, LocalDateTime wakeTime) {

        _sleepDuration = Duration.between(bedTime, wakeTime);
        _bedTime = bedTime;
        _wakeTime = wakeTime;
        _hours = (long)_sleepDuration.toHours();
        _minutes = (long)_sleepDuration.minusHours(_seconds).toMinutes();
        _seconds = (long)_sleepDuration.minusHours(_seconds).minusMinutes(_minutes).getSeconds();
    }

    public long get_hours() {
        return _hours;
    }

    public long get_minutes() {
        return _minutes;
    }

    public long get_seconds() {
        return _seconds;
    }

//    public String get_bedTime() {
//        return _bedTime.toString();
//    }
//
//    public String get_wakeTime() {
//        return _wakeTime.toString();
//    }

    public byte[] get_bedBytes() {
        return _bedTime.toString().getBytes();
    }

    public byte[] get_wakeBytes() {
        return _wakeTime.toString().getBytes();
    }

    public byte[] get_byteArray() {
        return (_bedTime.toString() + _wakeTime.toString()).getBytes();
    }

    @Override
    public String toString() {
        return String.format("%02d", _hours) + ":" + String.format("%02d", _minutes)
                + ":" + String.format("%02d", _seconds);
    }

    public static SleepSession parse(String text) {
        //substring it
        return new SleepSession(LocalDateTime.parse(text), LocalDateTime.parse(text));
    }
}
