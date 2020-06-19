package com.example.sleeplog;

public class LogEntry {
    private SleepSession _sleepSession;
    
    public LogEntry(SleepSession sleepSession) {
        _sleepSession = sleepSession;
    }

    public SleepSession get_sleepSession() {
        return _sleepSession;
    }

    //Outputs the ByteArray needed to write to SleepLog.txt
//    public byte[] toByteArray(){
//        return _sleepSession.get();
//    }
}
