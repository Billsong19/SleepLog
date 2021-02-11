package com.example.sleeplog;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SleepLogDao {
    @Query("SELECT * FROM SleepSessionEntity ORDER BY wake_time DESC")
    List<SleepSessionEntity> getAll();

    @Query("SELECT * FROM SleepSessionEntity WHERE sleepID IN (:sleepIDs)")
    List<SleepSessionEntity> loadAllByIds(int[] sleepIDs);

    @Insert
    void insertAll(SleepSessionEntity... sleepSessions);

    @Delete
    void delete(SleepSessionEntity sleepSession);
}