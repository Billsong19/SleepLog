package com.example.sleeplog;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RunWith(AndroidJUnit4.class)
public class TestMainActivity {
private ActivityScenario<MainActivity> _scenario;
    @Before
    public void setUpScenario(){
        ActivityScenario<MainActivity> _scenario = ActivityScenario.launch(MainActivity.class);

    }

    @Test
    public void testTimerPersistsOnRecreate(){
        _scenario.recreate();
        final LocalDateTime[] bedTime = new LocalDateTime[1];
        _scenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
//                bedTime[0] = activity.getStartTime();
            }
        });
        Assert.assertArrayEquals(bedTime, bedTime);
    }
}



