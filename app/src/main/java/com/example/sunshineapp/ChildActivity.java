package com.example.sunshineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {
    private TextView mDisplayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        mDisplayMessage = (TextView) findViewById(R.id.tv_display);
        //TODOS
        //getIntent and store it first
        Intent intentThatStartedThisActivity = getIntent();
        //check if extra exist if yes then add it.
        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            //getExtra, and from there set the ttext view. :)
            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            mDisplayMessage.setText(textEntered);
        }


    }
}