package com.example.sunshineapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.droidtermsprovider.DroidTermsExampleContract;
//BEFORE PROCEEDING.

//--||
//--||
//--||
//-\__/
//--\/

public class MainActivity extends AppCompatActivity {

    // The data from the DroidTermsExample content provider
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mButton.setText(getString(R.string.show_defintion));
        mCurrentState = STATE_HIDDEN;
    }

    private void showDefinition() {
        mButton.setText(getString(R.string.next_word));
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

