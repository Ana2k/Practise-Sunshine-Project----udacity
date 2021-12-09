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
//https://classroom.udacity.com/courses/ud851/lessons/1392b674-18b6-4636-b36b-da7d37a319e3/concepts/eb405b65-4ad2-4672-845f-3fb7b64c9238
//THE LINK TO DO
//--||
//--||
//--||
//-\__/
//--\/
//petitition to change this branch name to "TEMPLATE java mp3 player --visualiser"
//--better pettion = make a seperate repo for only this thats private :)


public class VisualiserActivity extends AppCompatActivity {

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
//        Get a reference to the default shared preferences from the PreferenceManager class
//        Get the value of the show_bass checkbox preference and use it to call setShowBass
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mVisualiserView.setShowBass(sharedPreferences.getBoolean(getString(R.string.show_bass),true));

        mVisualiserView.setShowMid(true);
        mVisualiserView.setShowTreble(true);
        mVisualiserView.setMinSizeScale(1);
        mVisualiserView.setColor(getString(R.string.pref_color_red_value));
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
                    Toast.makeText(getApplicationContext(), R.string.permission, Toast.LENGTH_LONG).show();
                    finish();
                    // The permission was denied, so we can show a message why we can't run the app
                    // and then close the app.
                }
            }
            // Other permissions could go down here
        }
    }
}