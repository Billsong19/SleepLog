package com.example.sleeplog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SettingsActivity extends AppCompatActivity {

    Button button2, button3, button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);

        button2 = (Button)findViewById(R.id.button2);

        File _sleepLogFile = new File(this.getFilesDir(), "SleepLog.txt");
        try {
            Scanner myReader = new Scanner(_sleepLogFile);
            String str = myReader.nextLine();
            System.out.println(str);
            button2.setText(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

    }
}
