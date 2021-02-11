package com.example.sleeplog;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

@Entity
public class SleepSessionEntity {
    @PrimaryKey(autoGenerate = true)
    public int sleepID;

    @ColumnInfo(name = "bed_time")
    public LocalDateTime _bedTime;

    @ColumnInfo(name = "wake_time")
    public LocalDateTime _wakeTime;

    @ColumnInfo(name = "duration")
    public Duration _duration;

    public SleepSessionEntity(LocalDateTime bedTime, LocalDateTime wakeTime){
        _bedTime = bedTime;
        _wakeTime = wakeTime;
        _duration = Duration.between(bedTime, wakeTime);
    }

    @Ignore
    public SleepSessionEntity(LocalDateTime bedTime)
    {
        _bedTime = bedTime;
    }

    public void set_wakeTime(LocalDateTime wakeTime){
        _wakeTime = wakeTime;
        _duration = Duration.between(_bedTime, wakeTime);
    }

    public Duration timeElapsed(){
        return Duration.between(_bedTime, LocalDateTime.now());
    }
}
