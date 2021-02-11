package com.example.sleeplog;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.time.Duration;
import java.time.LocalDateTime;

public class Converters {
    @TypeConverter
    public static LocalDateTime fromTimestamp(String date) {
        return date == null ? null : LocalDateTime.parse(date);
    }

    @TypeConverter
    public String dateToTimestamp(LocalDateTime date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static Duration fromLong(long dur){
        return Duration.ofSeconds(dur);
    }

    @TypeConverter
    public long durationToLong(Duration dur){
        return dur == null ? null : dur.getSeconds();
    }

}
