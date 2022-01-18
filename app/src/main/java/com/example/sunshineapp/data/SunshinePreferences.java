package com.example.sunshineapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.sunshineapp.R;

public final class SunshinePreferences {

    /*
     * In order to uniquely pinpoint the location on the map when we launch the
     * map intent, we store the latitude and longitude.
     */
    public static final String PREF_COORD_LAT = "coord_lat";
    public static final String PREF_COORD_LONG = "coord_long";

    private static final String TAG = SunshinePreferences.class.getSimpleName();

    /**
     * Helper method to handle setting location details in Preferences (City Name, Latitude,
     * Longitude)
     *
     * @param context   Context used to get the SharedPreferences
     * @param latitude  The latitude of the city
     * @param longitude The longitude of the city
     */
    static public void setLocationDetails(Context context, double latitude, double longitude) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong(PREF_COORD_LAT, Double.doubleToRawLongBits(latitude));
        editor.putLong(PREF_COORD_LONG, Double.doubleToRawLongBits(longitude));
        editor.apply();
    }

    /**
     * Helper method to handle setting a new location in preferences.  When this happens
     * the database may need to be cleared.
     *
     * @param context         Context used to get the SharedPreferences
     * @param locationSetting The location string used to request updates from the server.
     * @param cityName        A human-readable city name, e.g "Mountain View"
     * @param lat             The latitude of the city
     * @param lon             The longitude of the city
     */
    static public void setLocation(Context context, String locationSetting, String cityName, double lat, double lon) {
        //FUTURE LESSON
    }

    /**
     * @param context
     */
    static public void resetLocationCoordinnates(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(PREF_COORD_LAT);
        editor.remove(PREF_COORD_LONG);
        editor.apply();
    }

    /**
     * @param context
     * @return Location The current user has set in SharedPreferences. Will default to
     * "94043,USA" if SharedPreferences have not been implemented yet.
     */
    public static String getPreferredWeatherLocation(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String keyForLocation = context.getString(R.string.pref_location_key);
        String defaultLocation = context.getString(R.string.pref_location_default);
        Log.d(TAG, "getPreferredWeateherLocationWasCalled");

        return sharedPreferences.getString(keyForLocation, defaultLocation);
    }

    /**
     * Returns true if the user has selected metric temperature display.
     *
     * @param context Context used to get the SharedPreferences
     * @return true If metric display should be used
     */
    public static boolean isMetric(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForUnits = context.getString(R.string.pref_units_key);
        String defaultUnits = context.getString(R.string.pref_units_label);
        String preferredUnits = sharedPreferences.getString(keyForUnits, defaultUnits);
        String metric = context.getString(R.string.pref_units_metric);

        boolean userPrefersMetric;
        if (metric.equals(preferredUnits)) {
            userPrefersMetric = true;
        } else {
            userPrefersMetric = false;
        }
        Log.d(TAG, "isMetric was called");
        return userPrefersMetric;
    }

    /**
     * Returns the location coordinates associated with the location. Note that there is a
     * possibility that these coordinates may not be set, which results in (0,0) being returned.
     * Interestingly, (0,0) is in the middle of the ocean off the west coast of Africa.
     *
     * @param context used to access SharedPreferences
     * @return an array containing the two coordinate values for the user's preferred location
     */
    public static double[] getLocationinCoordinates(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        double[] preferredCoordinates = new double[2];
        /*
         * This is a hack we have to resort to since you can't store doubles in SharedPreferences.
         *
         * Double.doubleToLongBits returns an integer corresponding to the bits of the given
         * IEEE 754 double precision value.
         *
         * Double.longBitsToDouble does the opposite, converting a long (that represents a double)
         * into the double itself.
         */
        preferredCoordinates[0] = Double
                .longBitsToDouble(sharedPreferences.getLong(PREF_COORD_LAT, Double.doubleToRawLongBits(0.0)));
        preferredCoordinates[1] = Double
                .longBitsToDouble(sharedPreferences.getLong(PREF_COORD_LONG, Double.doubleToRawLongBits(0.0)));
        return preferredCoordinates;

    }

    /**
     * Returns true if the latitude and longitude values are available. The latitude and
     * longitude will not be available until the lesson where the PlacePicker API is taught.
     *
     * @param context used to get the SharedPreferences
     * @return true if lat/long are set
     */
    public static boolean isLocationLatLonAvailable(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean spContainLatitude = sharedPreferences.contains(PREF_COORD_LAT);
        boolean spContainLongitude = sharedPreferences.contains(PREF_COORD_LONG);

        boolean spContainBothLatitudeAndLongitude = false;
        if (spContainLatitude && spContainLongitude) {
            spContainBothLatitudeAndLongitude = true;
        }

        return spContainBothLatitudeAndLongitude;
    }

    /**
     * Returns the last time that a notification was shown (in UNIX time)
     *
     * @param context Used to access SharedPreferences
     * @return UNIX time of when the last notification was shown
     */
    public static long getLastNotificationTimeInMillis(Context context) {
        String lastNotificationKey = context.getString(R.string.pref_last_notification);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        /*
         * Here, we retrieve the time in milliseconds when the last notification was shown. If
         * SharedPreferences doesn't have a value for lastNotificationKey, we return 0. The reason
         * we return 0 is because we compare the value returned from this method to the current
         * system time. If the difference between the last notification time and the current time
         * is greater than one day, we will show a notification again. When we compare the two
         * values, we subtract the last notification time from the current system time. If the
         * time of the last notification was 0, the difference will always be greater than the
         * number of milliseconds in a day and we will show another notification.
         */
        long lastNotificationTime = sharedPreferences.getLong(lastNotificationKey,0);
        return lastNotificationTime;
    }

    /**
     * Returns the elapsed time in milliseconds since the last notification was shown. This is used
     * as part of our check to see if we should show another notification when the weather is
     * updated.
     *
     * @param context Used to access SharedPreferences as well as use other utility methods
     * @return Elapsed time in milliseconds since the last notification was shown
     */
    public static long getEllapsedTimeSinceLastNotification(Context context) {
        long lastNotificationTimeMillis = SunshinePreferences.getLastNotificationTimeInMillis(context);
        long timeSinceLastNotification = System.currentTimeMillis()-lastNotificationTimeMillis;
        //NICE!!!!
        return timeSinceLastNotification;
    }
    public static void saveLastNotificationTime(Context context,long timeOfNotifications){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String lastNotificationKey = context.getString(R.string.pref_last_notification);
        editor.putLong(lastNotificationKey,timeOfNotifications);
        editor.apply();
    }

}
