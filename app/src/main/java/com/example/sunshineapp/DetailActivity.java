package com.example.sunshineapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";
    private TextView mDisplayDetails;
    private String mForecast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDisplayDetails = (TextView) findViewById(R.id.tv_display_details);

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity!=null && intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            mForecast = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            mDisplayDetails.setText(mForecast);
        }
    }
}

//FOR SHARE.
//    String chooserTitle = "Learning How to Share";
//
//
//    //niyati.com/blog/android-sharecompat/
//    //like this blog way more
//    ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(this);
//    Intent intent = intentBuilder
//            .setType(mimeType)
//            .setChooserTitle(chooserTitle)
//            .setText(textToShare)
//            .createChooserIntent();
//
//        if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent);
//                }
//FOR MAP
//String addressString = "1600 Amphitheatre Parkway, Mountain View,CA";
//        Uri.Builder builder= new Uri.Builder();
//        Uri addressUri = builder.scheme("geo")
//                .path("0.0")
//                .appendQueryParameter("q",addressString)
//                .build();
//        //for understanding the above lines:---
//        //Data URI Scheme heading in Android Common Intents developer page
//        showMap(addressUri);