package com.example.sleeplog;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class TestSleepSession {
    SleepSession _sleepSessions;

    @Before
    public void setUpSleepSessions(){
        _sleepSessions = new SleepSession(
                Clock.fixed(Instant.parse("2020-06-21T16:00:55.13Z"), ZoneId.of("Pacific/Auckland")));
        _sleepSessions.updateWakeTime(
                Clock.fixed(Instant.parse("2020-06-21T17:36:11.64Z"), ZoneId.of("Pacific/Auckland")));
    }

    @Test
    public void testTimeElapsedString(){
        String timeElapsed = _sleepSessions.timeElapsedString();
        Assert.assertEquals(timeElapsed, "01:35:16");
    }
}