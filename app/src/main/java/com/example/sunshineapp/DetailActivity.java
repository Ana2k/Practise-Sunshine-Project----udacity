package com.example.sunshineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";
    private TextView mDisplayDetails;
    private String mForecast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDisplayDetails = (TextView) findViewById(R.id.tv_display_details);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity!=null && intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            mForecast = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            mDisplayDetails.setText(mForecast);
        }
    }
}