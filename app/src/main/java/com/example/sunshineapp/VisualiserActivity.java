package com.example.sunshineapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sunshineapp.AudioVisuals.AudioInputReader;
import com.example.sunshineapp.AudioVisuals.VisualiserView;
//BEFORE PROCEEDING.

//--||
//--||
//--||
//-\__/
//--\/

public class VisualiserActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE = 888;
    private VisualiserView mVisualiserView;
    private AudioInputReader mAudioInputReader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiser);
        mVisualiserView = (VisualiserView) findViewById(R.id.activity_visualizer);
        defaultSetup();
        setupPermissions();
    }


    private void defaultSetup() {
        mVisualiserView.setShowBass(true);
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
                    Toast.makeText(this, "Permission for audio not granted. Visualizer can't run.", Toast.LENGTH_LONG).show();
                    finish();
                    // The permission was denied, so we can show a message why we can't run the app
                    // and then close the app.
                }
            }
            // Other permissions could go down here
        }
    }
}
// TODO (1) Create a new Empty Activity named SettingsActivity; make sure to generate the
// activity_settings.xml layout file as well and add the activity to the manifest

// TODO (2) Add a new resource folder called menu and create visualizer_menu.xml
// TODO (3) In visualizer_menu.xml create a menu item with a single item. The id should be
// "action_settings", title should be saved in strings.xml, the item should never
// be shown as an action, and orderInCategory should be 100

// TODO (5) Add the menu to the menu bar
// TODO (6) When the "Settings" menu item is pressed, open SettingsActivity