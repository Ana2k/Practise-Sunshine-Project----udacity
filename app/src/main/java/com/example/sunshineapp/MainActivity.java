package com.example.sunshineapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//BEFORE PROCEEDING.
//You are supposed to complete the todos.

public class MainActivity extends AppCompatActivity {
    private Button mCoolButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoolButton = (Button) findViewById(R.id.b_do_sth_cool);
        mEditText = (EditText) findViewById(R.id.et_text_entry);

        mCoolButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                Class destinationActivity = ChildActivity.class;
                Intent childStartActivityIntent = new Intent(context,destinationActivity);
                String textEntered = mEditText.getText().toString();


                //putExtra
                childStartActivityIntent.putExtra(Intent.EXTRA_TEXT,textEntered);
                startActivity(childStartActivityIntent);

            }
        });
    }

}

//TODOs
//see videos of intents and understand them
//implement and run the toy app
//similarly implement the intent in sunshine app.

//FOR THE INITIAL SETUP
//Setup member variables: and their values in corr xml files.
//onClick implement with a cool message