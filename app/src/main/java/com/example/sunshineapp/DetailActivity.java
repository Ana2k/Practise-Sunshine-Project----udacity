package com.example.sunshineapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail,menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createForecastIntent());
        return true;
    }

    private Intent createForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setText(mForecast+FORECAST_SHARE_HASHTAG)
                .createChooserIntent();
        return shareIntent;
    }
    //idk what but sth to do here.

}

