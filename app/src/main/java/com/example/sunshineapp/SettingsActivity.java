package com.example.sunshineapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

public class SettingsActivity extends AppCompatActivity {
    public static final String TAG = SettingsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);
        Log.d(TAG,"onCreate Settings activity was implemented");
        ActionBar actionBar = this.getSupportActionBar();

        //Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            NavUtils.navigateUpFromSameTask(this);
        }
        if(id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
//Then, in the actual SettingsActivity you should override the home button to act like the back button:
//
//        if (id == android.R.id.home) {
//        onBackPressed();
//        }