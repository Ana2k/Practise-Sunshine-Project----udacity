package com.example.sunshineapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;


import java.io.IOException;
import java.net.URL;
//BEFORE PROCEEDING.
//https://developer.android.com/guide/components/intents-common
//for common intents used

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * When open website button is clicked
     * @param v
     */
    public void onClickOpenWebPageButton(View v){
       String webUrl = "https://developer.android.com/guide/components/intents-common";
       openWebPage(webUrl);
    }

    public void onClickOpenAddressMapButton(View v){
        String addressString = "1600 Amphitheatre Parkway, Mountain View,CA";
        Uri.Builder builder= new Uri.Builder();
        Uri addressUri = builder.scheme("geo")
                .path("0.0")
                .appendQueryParameter("q",addressString)
                .build();
        //for understanding the above lines:---
        //Data URI Scheme heading in Android Common Intents developer page
        showMap(addressUri);

    }
    public void onClickShareTextButton(View v)
    {
        String textToShare = "Text t0 share";
        shareText(textToShare);

    }

    public void createYourOwn(View v){
        //StillImageCamera
//        Toast.makeText(getApplicationContext(),"ODO: CREATE YOUR OWN IMPLICIT INTENT",
//                Toast.LENGTH_LONG).show();
        capturePhoto();
    }

    //HELPER FUNCTIONS:--
    //picked from the common intents developer.android page.
    public void openWebPage(String url){
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
    public void showMap(Uri addressUri){
        Intent intent = new  Intent(Intent.ACTION_VIEW);
        intent.setData(addressUri);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
    public void shareText(String textToShare){
        //https://developer.android.com/reference/androidx/core/app/ShareCompat.IntentBuilder

        /* This is just the title of the window that will pop up when we call startActivity */
        String chooserTitle = "Learning How to Share";

        /*
         * You can think of MIME types similarly to file extensions. They aren't the exact same,
         * but MIME types help a computer determine which applications can open which content. For
         * example, if you double click on a .pdf file, you will be presented with a list of
         * programs that can open PDFs. Specifying the MIME type as text/plain has a similar affect
         * on our implicit Intent. With text/plain specified, all apps that can handle text content
         * in some way will be offered when we call startActivity on this particular Intent.
         */
        String mimeType = "text/plain";

        //niyati.com/blog/android-sharecompat/
        //like this blog way more
        ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(this);
        Intent intent = intentBuilder
                .setType(mimeType)
                .setChooserTitle(chooserTitle)
                .setText(textToShare)
                .createChooserIntent();

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        what they wrote
//        ShareCompat.IntentBuilder
//                /* The from method specifies the Context from which this share is coming from */
//                .from(this)
//                .setType(mimeType)
//                .setChooserTitle(title)
//                .setText(textToShare)
//                .startChooser();

    }
    public void capturePhoto(){
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }


}