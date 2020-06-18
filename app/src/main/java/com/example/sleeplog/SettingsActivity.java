package com.example.sleeplog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    Button button2, button3, button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);

        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
    }
}
