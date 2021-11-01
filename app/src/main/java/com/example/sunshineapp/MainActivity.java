package com.example.sunshineapp;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//YOU WERE AT-->
//https://youtu.be/Zim98WIKSYI?t=124
//on site: https://classroom.udacity.com/courses/ud851/lessons/e5d74e43-743c-455e-9a70-7545a2da9783/concepts/6429b4ab-c9ee-45d2-9d7c-d18a8364827d
// and github page: https://github.com/udacity/ud851-Exercises/blob/student/Lesson02-GitHub-Repo-Search/T02.03-Exercise-DisplayUrl/app/src/main/java/com/example/android/datafrominternet/utilities/NetworkUtils.java
//was open

public class MainActivity extends AppCompatActivity {
    EditText mSearchBoxEditText;
    TextView mUrlDisplayTextView,mSearchResultsTextView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItemSelected = item.getItemId();
        Context context = getApplicationContext();
        CharSequence toastMessage = "Menu id inflated ";
        if(idMenuItemSelected == R.id.action_search){
            Toast.makeText(context,toastMessage,Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.url_display_tv);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
    }

   //github repo from https://classroom.udacity.com/courses/ud851/lessons/e5d74e43-743c-455e-9a70-7545a2da9783/concepts/df87e335-ec8d-4383-9e32-78f8e0f982f1
}