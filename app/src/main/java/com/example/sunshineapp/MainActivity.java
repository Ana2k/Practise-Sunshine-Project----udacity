package com.example.sunshineapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

//BEFORE PROCEEDING.

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final ArrayList<String> mLifeCycleCallbacks = new ArrayList<>();
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

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
        mLifeCycleDisplay = (TextView) findViewById(R.id.tv_lifecycle_events_display);

        //check the null , the key then. extract the string from previous state-- variable
        //set this as text in tv
        if (savedInstanceState!=null){
            if(savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)){
                String allPreviousLifeCycleCallbacks = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
                mLifeCycleDisplay.setText(allPreviousLifeCycleCallbacks);
            }
        }

        logAppend(ON_CREATE);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //extract from tv an put the data in outstate-- bundle of savedInstance.
        logAppend(ON_SAVE_INSTANCE_STATE);
        String lifeCycleDisplayTextViewContents = mLifeCycleDisplay.getText().toString();
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY,lifeCycleDisplayTextViewContents);
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
        logAppend(ON_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        logAppend(ON_STOP);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logAppend(ON_RESTART);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logAppend(ON_DESTROY);
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

