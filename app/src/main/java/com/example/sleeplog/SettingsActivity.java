package com.example.sleeplog;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class SettingsActivity extends AppCompatActivity {

    Button button2, button3, button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);

        button2 = (Button)findViewById(R.id.button2);

        button3 = (Button)findViewById(R.id.button3);
        button3.setText("Add 10h sleep");
        button3.setOnClickListener(view -> {
            System.out.println("HI");
            AppDatabase db = AppDatabase.getInstance(this);
            SleepLogDao sleepDao = db.sleepLogDao();
            List<SleepSessionEntity> sleeps = sleepDao.getAll();

            sleeps.forEach(sleepsesh-> System.out.println(sleepsesh._bedTime.toString()));
            sleepDao.insertAll(new SleepSessionEntity(LocalDateTime.now(), LocalDateTime.now().plusHours(10)));
        });
        button4 = (Button)findViewById(R.id.button4);
    }
}