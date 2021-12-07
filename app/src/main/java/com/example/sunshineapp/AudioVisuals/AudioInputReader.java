package com.example.sunshineapp.AudioVisuals;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;

public class AudioInputReader {
    private final VisualiserView mVisualiserView;
    private final Context mContext;
    private MediaPlayer mPlayer;
    private Visualizer mVisualizer;


    public AudioInputReader(VisualiserView visualiserView, Context context) {
        this.mVisualiserView = visualiserView;
        this.mContext = context;
        initVisualiser();
    }

    private void initVisualiser() {
        //Setup media Player
        mPlayer = MediaPlayer.create(mContext, android.R.raw.)
    }
}
