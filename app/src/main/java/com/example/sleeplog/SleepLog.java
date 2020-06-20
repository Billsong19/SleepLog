package com.example.sleeplog;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SleepLog {
    private LinkedList<SleepSession> _sleepSessions = new LinkedList<SleepSession>();
    private Context _context = new MainActivity();
    
    public SleepLog(File file) {
//        File _sleepLogFile = new File(_context.getFilesDir(), "SleepLog.txt");
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine())
            {
                String str = myReader.nextLine();
                Log.d(TAG, "SleepLog: reading line " + str);
                System.out.println(str);
                _sleepSessions.addFirst(SleepSession.parse(str));
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public SleepSession get_sleepSession(int position){
        return _sleepSessions.get(position);
    }

    public int getSize(){
        return _sleepSessions.size();
    }


//Gets list of sleepsessions. Seeing as the whole point of SleepLog is to encapsulate the list,
    //this is probably unwise.
//    public List<SleepSession> get_sleepSession() {
//        return _sleepSessions;
//    }
//
}
