package com.example.sunshineapp;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.example.sunshineapp.AudioVisuals.AudioInputReader;
import com.example.sunshineapp.AudioVisuals.VisualiserView;
//BEFORE PROCEEDING.
//TOdo --
//---crazy amount of code wrt shared preferences in the settingsActivity?
//--||
//--||
//--||
//--||
//-\__/
//--\/
//petitition to change this branch name to "TEMPLATE java mp3 player --visualiser"
//--better pettion = make a seperate repo for only this thats private :)

//udacity part --> https://classroom.udacity.com/courses/ud851/lessons/1392b674-18b6-4636-b36b-da7d37a319e3/concepts/7156d056-641e-491c-9b86-49f5310de0b0
////T0D0 -->https://github.com/udacity/ud851-Exercises/tree/student/Lesson06-Visualizer-Preferences/T06.10-Solution-EditTextPreferenceConstraints/app/src/main/res
//https://github.com/udacity/ud851-Exercises/tree/student/Lesson06-Visualizer-Preferences/T06.10-Solution-EditTextPreferenceConstraints/app/src/main/java/android/example/com/visualizerpreferences

public class VisualiserActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    private static final int MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE = 888;
    private VisualiserView mVisualiserView;
    private AudioInputReader mAudioInputReader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiser);
        mVisualiserView = (VisualiserView) findViewById(R.id.activity_visualizer);
        setupSharedPreference();
        setupPermissions();
    }


    private void setupSharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mVisualiserView.setShowBass(sharedPreferences.getBoolean(getString(R.string.pref_show_bass_key), true));
        mVisualiserView.setShowMid(sharedPreferences.getBoolean(getString(R.string.pref_show_mid_range_key), true));
        mVisualiserView.setShowTreble(sharedPreferences.getBoolean(getString(R.string.pref_show_treble_key), true));

        loadSizeFromSharedPreference(sharedPreferences);
        loadColourFromPreferences(sharedPreferences);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadSizeFromSharedPreference(SharedPreferences sharedPreferences) {
        Float minSize = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_size_key), getString(R.string.pref_size_default)));
        mVisualiserView.setMinSizeScale(minSize);
    }

    private void loadColourFromPreferences(SharedPreferences sharedPreferences) {
        mVisualiserView
                .setColor(sharedPreferences
                        .getString(getString(R.string.pref_color_key)
                                , getString(R.string.pref_color_red_value)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_show_bass_key))) {
            mVisualiserView.setShowBass(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_show_bass_default)));
        } else if (key.equals(getString(R.string.pref_show_mid_range_key))) {
            mVisualiserView.setShowMid(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_show_mid_range_default)));
        } else if (key.equals(getString(R.string.pref_show_treble_key))) {
            mVisualiserView.setShowTreble(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_show_treble_default)));
        } else if (key.equals(getString(R.string.pref_color_key))) {
            loadColourFromPreferences(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_size_key))) {
            loadSizeFromSharedPreference(sharedPreferences);
        }

    }

    /**
     * Below this point is code you do not need to modify; it deals with permissions
     * and starting/cleaning up the AudioInputReader
     **/

    /**
     * onPause Cleanup audio stream
     **/
    @Override
    protected void onPause() {
        super.onPause();
        if (mAudioInputReader != null) {
            mAudioInputReader.shutdown(isFinishing());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAudioInputReader != null) {
            mAudioInputReader.restart();
        }
    }


    private void setupPermissions() {
        // If we don't have the record audio permission...
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // And if we're on SDK M or later...
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Ask again, nicely, for the permissions.
                String[] permissionsWeNeed = new String[]{Manifest.permission.RECORD_AUDIO};
                requestPermissions(permissionsWeNeed, MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE);
            }
        } else {
            // Otherwise, permissions were granted and we are ready to go!
            mAudioInputReader = new AudioInputReader(mVisualiserView, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // The permission was granted! Start up the visualizer!
                    mAudioInputReader = new AudioInputReader(mVisualiserView, this);

                } else {
                    Toast.makeText(getApplicationContext(), R.string.permission_not_granted, Toast.LENGTH_LONG).show();
                    finish();
                    // The permission was denied, so we can show a message why we can't run the app
                    // and then close the app.
                }
            }
            // Other permissions could go down here
        }
    }


}