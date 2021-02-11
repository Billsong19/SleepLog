package com.example.sleeplog;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {SleepSessionEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase  extends RoomDatabase {
    public abstract SleepLogDao sleepLogDao();
    private static AppDatabase instance;

//    private AppDatabase(){
//        SleepLogDao();
//    }
    public static AppDatabase getInstance(Context context){
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database-name").allowMainThreadQueries().build();//allowMainThreadQueries not recommended, change to async at some point
        }
        return instance;
    }
}
