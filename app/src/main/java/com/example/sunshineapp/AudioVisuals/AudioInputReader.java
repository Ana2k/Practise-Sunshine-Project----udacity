package com.example.sunshineapp.AudioVisuals;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Build;

import com.example.sunshineapp.R;

public class AudioInputReader {
    private final VisualiserView mVisualiserView;
    private final Context mContext;
    private MediaPlayer mPlayer;
    private Visualizer mVisualiser;


    public AudioInputReader(VisualiserView visualiserView, Context context) {
        this.mVisualiserView = visualiserView;
        this.mContext = context;
        initVisualiser();
    }

    private void initVisualiser() {
        //Setup media Player
        mPlayer = MediaPlayer.create(mVisualiserView.getContext(), R.raw.htmlthesong);
        mPlayer.setLooping(true);

        //setup visualiser
        //Connect the media player
        mVisualiser = new Visualizer(mPlayer.getAudioSessionId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mVisualiser.setMeasurementMode(Visualizer.MEASUREMENT_MODE_PEAK_RMS);
            mVisualiser.setScalingMode(Visualizer.SCALING_MODE_NORMALIZED);
        }
        // Set the size of the byte array returned for visualization
        mVisualiser.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
        // Whenever audio data is available, update the visualizer view
        mVisualiser.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {

                    @Override
                    public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                        // Do nothing, we are only interested in the FFT (aka fast Fourier transform)
                    }

                    public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRates) {
                        // If the Visualizer is ready and has data, send that data to the VisualizerView
                        if (mVisualiser != null && mVisualiser.getEnabled()) {
                            mVisualiserView.updateFFT(bytes);
                        }

                    }
                },
                Visualizer.getMaxCaptureRate(), false, true);

        mVisualiser.setEnabled(true);
        mPlayer.start();

    }

    public void shutdown(boolean isFinishing){
        if(mPlayer!=null){
            mPlayer.pause();
            if(isFinishing){
                mVisualiser.release();
                mPlayer.release();
                mPlayer = null;
                mVisualiser = null;
            }
        }
        if(mVisualiser!=null){
            mVisualiser.setEnabled(false);
        }

    }

    public void restart(){
        if(mPlayer!=null){
            mPlayer.start();
        }
        mVisualiser.setEnabled(true);
        mVisualiserView.restart();
    }
}
