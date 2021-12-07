package com.example.sunshineapp.AudioVisuals;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;

public class VisualiserView {

    // These constants determine how much of a percentage of the audio frequencies each shape
    // represents. For example, the bass circle represents the bottom 10% of the frequencies.
    private static final float SEGMENT_SIZE = 100.f;
    private static final float BASS_SEGMENT_SIZE = 10.f / SEGMENT_SIZE;
    private static final float MID_SEGMENT_SIZE = 30.f / SEGMENT_SIZE;
    private static final float TREBLE_SEGMENT_SIZE = 60.f / SEGMENT_SIZE;

    // The minimum size of the shape, by default, before scaling
    private static final float MIN_SIZE_DEFAULT = 50;

    // This multiplier is used to make the frequency jumps a little more visually pronounced
    private static final float BASS_MULTIPLIER = 1.5f;
    private static final float MID_MULTIPLIER = 3;
    private static final float TREBLE_MULTIPLIER = 5;

    private static final float REVOLUTIONS_PER_SECOND = .3f;

    // Controls the Size of the circle each shape makes
    private static final float RADIUS_BASS = 20 / 100.f;
    private static final float RADIUS_MID = 60 / 100.f;
    private static final float RADIUS_TREBLE = 90 / 100.f;

    // The shapes
    private final TrailedShape mBassCircle;
    private final TrailedShape mMidSquare;
    private final TrailedShape mTrebleTriangle;

    // The array which keeps the current fft bytes
    private byte[] mBytes;

    // The time when the animation started
    private long mStartTime;

    // Numbers representing the current average of all the values in the bass, mid and treble range
    // in the fft
    private float bass;
    private float mid;
    private float treble;

    // Determines whether each of these should be shown
    private boolean showBass;
    private boolean showMid;
    private boolean showTreble;

    @ColorInt
    private int backgroundColor;


    public VisualiserView(Context context, AttributeSet attrs) {
        super(context,attrs);
        mBytes = null;
        TrailedShape.setMinSize(MIN_SIZE_DEFAULT);

        // Create each of the shapes and define how they are drawn on screen
        // Make bass circle
        mBassCircle = new TrailedShape(BASS_MULTIPLIER){

        }

    }
}
