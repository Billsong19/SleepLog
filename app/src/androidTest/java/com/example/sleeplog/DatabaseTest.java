package com.example.sleeplog;

import  	androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Test
    public void testTrivialDB()
    {
        LocalDateTime bedTime = LocalDateTime.now();
        LocalDateTime wakeTime = bedTime.plusHours(10);
        SleepSessionEntity sleep = new SleepSessionEntity(bedTime, wakeTime);

        System.out.println(InstrumentationRegistry.getInstrumentation().getContext());
        AppDatabase db = AppDatabase.getInstance(InstrumentationRegistry.getInstrumentation().getContext());
        SleepLogDao sleepdao = db.sleepLogDao();
        List<SleepSessionEntity> sleeps = sleepdao.getAll();

        sleepdao.insertAll(sleep);

        Assert.assertEquals(sleeps.get(sleeps.size()), sleep);

    }
}
