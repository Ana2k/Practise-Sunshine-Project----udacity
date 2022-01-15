package com.example.sunshineapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.droidtermsprovider.DroidTermsExampleContract;

import org.w3c.dom.Text;
//BEFORE PROCEEDING.

//--||
//--||
//--||
//-\__/
//--\/
//-- to do and think and do steps from this file
//-- https://classroom.udacity.com/courses/ud851/lessons/b071166b-dec4-4468-b419-422e3ab0194d/concepts/f0ca2615-a88e-485c-96d5-b9060f479e8a
public class MainActivity extends AppCompatActivity {

    // The data from the DroidTermsExample content provider
//    Log.d()
    private Cursor mData;

    // The current state of the app
    private int mCurrentState;

    private Button mButton;

    // This state is when the word definition is hidden and clicking the button will therefore
    // show the definition
    private final int STATE_HIDDEN = 0;

    // This state is when the word definition is shown and clicking the button will therefore
    // advance the app to the next word
    private final int STATE_SHOWN = 1;
    private TextView mWordTextView;
    private TextView mDefniitionTextView;
    private int mWordCols,mDefCols;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWordTextView = findViewById(R.id.tv_word);
        mDefniitionTextView = findViewById(R.id.tv_defenition);
        mButton = findViewById(R.id.button_next);
        new WordFetchTask().execute();
    }

    /**
     * This is called from the layout when the button is clicked and switches between the
     * two app states.
     *
     * @param view The view that was clicked
     */
    public void onButtonClick(View view) {
        switch (mCurrentState) {
            case STATE_HIDDEN:
                showDefinition();
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }

    private void nextWord() {
        //check if the cursor is at the end and then fix it accordingly
        if(mData!=null){
            if(!mData.moveToNext()){
                mData.moveToFirst();
            }
        }
        // Hide the definition TextView
        mDefniitionTextView.setVisibility(View.INVISIBLE);
        //change the button text
        mButton.setText(getString(R.string.show_definition));
                //Get the next word
        mWordTextView.setText(mData.getString(mWordCols));
        mDefniitionTextView.setText(mData.getString(mDefCols));

        mCurrentState = STATE_HIDDEN;

    }

    private void showDefinition() {
        if(mData!=null){
            mDefniitionTextView.setVisibility(View.VISIBLE);
            mButton.setText(getString(R.string.show_definition));
        }
        mCurrentState = STATE_SHOWN;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mData.close();
    } 

    //from network branch of sunshine appp
    private class WordFetchTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mData = cursor;
            //Innitialize anything that you need the cursor for, such as setting up
            // the screen with the first word and setting any other instance variables
            mDefCols = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_DEFINITION);
            mWordCols = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_WORD);

            while(mData.moveToNext()){
                String word,defenition;
                word = mData.getString(mWordCols);
                defenition = mData.getString(mDefCols);
                Log.v("Cursor Example",word+"--"+defenition);
            }
            mData.moveToFirst();
            nextWord();
        }

        @Override
        protected Cursor doInBackground(Void... params) {
            //write the code to access the droidTermsExample return cursor objects
            ContentResolver resolver = getContentResolver();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Cursor cursor = resolver.query(DroidTermsExampleContract.CONTENT_URI, null, null, null);
                return cursor;
            }

            return null;

        }
    }
}

//split the files after app is done??
//basically whatever changes you want to do to ui, you have to do through adapter non other way

