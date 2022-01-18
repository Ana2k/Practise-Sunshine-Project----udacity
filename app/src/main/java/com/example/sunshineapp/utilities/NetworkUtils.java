package com.example.sunshineapp.utilities;

import android.content.Context;
import android.net.Uri;

import com.example.sunshineapp.MainActivity;
import com.example.sunshineapp.data.SunshinePreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    //URLS
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String DYNAMIC_WEATHER_URL = "https://andfun-weather.udacity.com/weather";
    private static final String STATIC_WEATHER_URL = "https://andfun-weather.udacity.com/staticweather";

    private static final String FORECAST_BASE_URL = STATIC_WEATHER_URL;

    /*
     * NOTE: These values only effect responses from OpenWeatherMap, NOT from the fake weather
     * server. They are simply here to allow us to teach you how to build a URL if you were to use
     * a real API.If you want to connect your app to OpenWeatherMap's API, feel free to! However,
     * we are not going to show you how to do so in this course.
     */

    /* The format we want our API to return */
    private static final String format = "json";
    /* The units we want our API to return */
    private static final String units = "metric";
    /* The number of days we want our API to return */
    private static final int numDays = 14;

    final static String QUERY_PARAM = "q";
    final static String LAT_PARAM = "lat";
    final static String LON_PARAM = "lon";
    final static String FORMAT_PARAM = "mode";
    final static String UNITS_PARAM = "units";
    final static String DAYS_PARAM = "cnt";

    /**
     * Retrieves the proper URL to query for the weather data. The reason for both this method as
     * well as {@link #buildUrlWithLocationQuery(String)} is two fold.
     * <p>
     * 1) You should be able to just use one method when you need to create the URL within the
     * app instead of calling both methods.
     * 2) Later in Sunshine, you are going to add an alternate method of allowing the user
     * to select their preferred location. Once you do so, there will be another way to form
     * the URL using a latitude and longitude rather than just a location String. This method
     * will "decide" which URL to build and return it.
     *
     * @param context used to access other Utility methods
     * @return URL to query weather service
     */
    public static URL getUrl(Context context){
        if(SunshinePreferences.isLocationLatLonAvailable(context)){
            double[] preferredCoordinates = SunshinePreferences.getLocationinCoordinates(context);
            double latitude = preferredCoordinates[0];
            double longitude = preferredCoordinates[1];
            return buildUrlWithLatitudeLongitude(longitude,latitude);

        }else{
            String locationQuery = SunshinePreferences.getPreferredWeatherLocation(context);
            return buildUrlWithLocationQuery(locationQuery);
        }
    }
    //implemennt SunshinePreferences post this.


    public static URL buildUrlWithLocationQuery(String locationQuery){
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM,locationQuery)
                .appendQueryParameter(FORMAT_PARAM,format)
                .appendQueryParameter(UNITS_PARAM,units)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlWithLatitudeLongitude(Double latitude, Double longitude){
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(LAT_PARAM,String.valueOf(latitude))
                .appendQueryParameter(LON_PARAM,String.valueOf(longitude))
                .appendQueryParameter(FORMAT_PARAM,format)
                .appendQueryParameter(UNITS_PARAM,units)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                .build();

        URL weatherQueryUrl = null;
        try{
            weatherQueryUrl = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return weatherQueryUrl;
    }

    /**
     * Returns the entire result from HTTP response
     *
     * @param url - fetches HTTP response form
     * @return
     * @throws IOException
     */
    public static String getResponseHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }//samme code as toy_app_network :)
    }
}
/*
 * Sunshine was originally built to use OpenWeatherMap's API. However, we wanted to provide
 * a way to much more easily test the app and provide more varied weather data. After all, in
 * Mountain View (Google's HQ), it gets very boring looking at a forecast of perfectly clear
 * skies at 75°F every day... (UGH!) The solution we came up with was to host our own fake
 * weather server. With this server, there are two URL's you can use. The first (and default)
 * URL will return dynamic weather data. Each time the app refreshes, you will get different,
 * completely random weather data. This is incredibly useful for testing the robustness of your
 * application, as different weather JSON will provide edge cases for some of your methods.
 *
 * If you'd prefer to test with the weather data that you will see in the videos on Udacity,
 * you can do so by setting the FORECAST_BASE_URL to STATIC_WEATHER_URL below.
 */
