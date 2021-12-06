package com.example.sunshineapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//BEFORE PROCEEDING.

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    /* Constant values for the names of each respective lifecycle callback */
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";

    /*
     * This TextView will contain a running log of every lifecycle callback method called from this
     * Activity. This TextView can be reset to itso default state by clicking the Button labeled
     * "Reset Log"
     */
    private TextView mLifeCycleDisplay;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logAppend(ON_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logAppend(ON_START);
    }
    @Override
    protected void onResume() {
        super.onResume();
        logAppend(ON_RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        logAppend(ON_);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * Logs to the console and appends the lifecycle method name to the TextView so that you can
     * view the series of method callbacks that are called both from the app and from within
     * Android Studio's Logcat.
     *
     * @param lifeCycleEvent The name of the event to be logged.
     */
    private void logAppend(String lifeCycleEvent){
        Log.d(TAG,"Lifecycle Event:"+lifeCycleEvent);
        mLifeCycleDisplay.append(lifeCycleEvent+"\n");
    }
    /**
     * This method resets the contents of the TextView to its default text of "Lifecycle callbacks"
     *
     * @param view The View that was clicked. In this case, it is the Button from our layout.
     */
    public void resetLifeCycleDisplay(View view){
        mLifeCycleDisplay.setText("LifeCycleCallbacks\n");
    }
}

//split the files after app is done??
//basically whatever changes you want to do to ui, you have to do through adapter non other way

