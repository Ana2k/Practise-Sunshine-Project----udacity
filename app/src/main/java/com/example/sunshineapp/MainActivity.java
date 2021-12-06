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
        }// TODOsdf (4) Iterate backwards through mLifecycleCallbacks, appending each String and a newline to mLifecycleDisplay
        /*
         * Since any updates to the UI we make after onSaveInstanceState (onStop, onDestroy, etc),
         * we use an ArrayList to track if these lifecycle events had occurred. If any of them have
         * occurred, we append their respective name to the TextView.
         *
         * The reason we iterate starting from the back of the ArrayList and ending in the front
         * is that the most recent callbacks are inserted into the front of the ArrayList, so
         * naturally the older callbacks are stored further back. We could have used a Queue to do
         * this, but Java has strange API names for the Queue interface that we thought might be
         * more confusing than this ArrayList solution.
         */
        for(int i= mLifeCycleCallbacks.size()-1;i>=0;i-=1){
            mLifeCycleDisplay.append(mLifeCycleCallbacks.get(i)+"\n");
        }
        mLifeCycleCallbacks.clear();
        // TODOds (5) Clear mLifecycleCallbacks after iterating through it
        //dont know how to do either so...s
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
        mLifeCycleCallbacks.add(0,ON_STOP);
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
        mLifeCycleCallbacks.add(0,ON_DESTROY);
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

