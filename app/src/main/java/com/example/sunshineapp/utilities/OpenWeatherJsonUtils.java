package com.example.sunshineapp.utilities;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class OpenWeatherJsonUtils {



    /**METHOD - to returnn web response as a string :)
     *This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param forecastJsonStr JSON response from server
     * @return Array of Strings describing weather data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getSimpleWeatherStringsfromJson(Context context, String forecastJsonStr) throws JSONException{
        /* Weather information. Each day's forecast info is an element of the "list" array */
        final String OWM_LIST = "list";

        /* All temperatures are children of the "temp" object */
        final String OWM_TEMPERATURE = "temp";

        final String OWM_MAX = "max";
        final String OWM_MIN = "min";

        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";

        final String OWM_MESSAGE_CODE = "cod";

        /* String array to hold each day's weather String */
        String[] parsedWeatherData = null;
        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        /* Is there an error? */
        if (forecastJson.has(OWM_MESSAGE_CODE)){
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);
            switch(errorCode){
                case HttpURLConnection
                        .HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        //JSONArray
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);
        parsedWeatherData = new String[weatherArray.length()];

        //From SunshineDateUtils extract date
        long localDate = System.currentTimeMillis();
        long utcDate = SunshineDateUtils.getUTCDateFromLocal(localDate);
        long startDay = SunshineDateUtils.normaliseDate(utcDate);

        for(int i= 0; i<weatherArray.length();i++) {

            String date;
            String highAndLow;

            /* These are the values that will be collected */
            long dateTimeMillis;
            double high;
            double low;
            String description;

            //JSON OBJECT dont forget the tiny i
            JSONObject dayForecast = weatherArray.getJSONObject(i);
            /*
             * We ignore all the datetime values embedded in the JSON and assume that
             * the values are returned in-order by day (which is not guaranteed to be correct).
             */
            dateTimeMillis = startDay + SunshineDateUtils.DAY_IN_MILLIS * i;

            date = SunshineDateUtils.getFriendlyDateString(context, dateTimeMillis, false);

            JSONObject weatherObject = dayForecast
                    .getJSONArray(OWM_WEATHER)
                    .getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            high = temperatureObject.getDouble(OWM_MAX);
            low = temperatureObject.getDouble(OWM_MIN);
            highAndLow = SunshineWeatherUtils.formatHighLows(context, high, low);


            parsedWeatherData[i] = date + "-" + description + "-" + highAndLow;
        }
        return parsedWeatherData;

    }

    /**
     *
     * @param context
     * @param forecastJSonStr - JSON to parse into ContentValues
     * @return  An array of ContentValues parsed from the JSON.
     */
    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJSonStr){
        /*
         * Otherwise, if the day is not today, the format is just the day of the week
         * (e.g "Wednesday")
         */
        return null;
    }
}
