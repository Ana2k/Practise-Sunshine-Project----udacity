package com.example.sunshineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mToysListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToysListTextView = (TextView) findViewById(R.id.tv_toy_names);

        String [] toyNames = ToyBox.getToyNames();
        for (String name: toyNames){
            mToysListTextView.append(name+"\n\n");
        }
    }
}