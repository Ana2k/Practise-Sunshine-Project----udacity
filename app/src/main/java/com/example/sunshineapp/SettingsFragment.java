package com.example.sunshineapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
//1)PreferenceScreen preferenceScreen = getPreferenceScreen();

//(2) Create a setPreferenceSummary which takes a Preference and String value as parameters.
// This method should check if the preference is a ListPreference and, if so, find the label
// associated with the value. You can do this by using the findIndexOfValue and getEntries methods
// of Preference.

//(3) Get the preference screen, get the number of preferences and iterate through
// all of the preferences if it is not a checkbox preference, call the setSummary method
// passing in a preference and the value of the preference

// (4) Override onSharedPreferenceChanged and, if it is not a checkbox preference,
// call setPreferenceSummary on the changed preference

//(5) Register and unregister the OnSharedPreferenceChange listener (this class) in
// onCreate and onDestroy respectively.

//#what was this crazy witchcraft code (T_T) ??



public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_visualiser);

        // Get the preference screen, get the number of preferences and iterate through
        // all of the preferences if it is not a checkbox preference, call the setSummary method
        // passing in a preference and the value of the preference
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();

        int count = preferenceScreen.getPreferenceCount();

        // Go through all of the preferences, and set up their preference summary.
        for (int i = 0; i < count; i += 1) {
            Preference currentPreference = preferenceScreen.getPreference(i);
            // You don't need to set up preference summaries for checkbox preferences because
            // they are already set up in xml using summaryOff and summary On
            if (!(currentPreference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(currentPreference.getKey(), "");
                setPreferenceSummary(currentPreference, value);
            }
        }
        //we create an empty preference summary just for
        //the listPreference
    }


    // Override onSharedPreferenceChanged and, if it is not a checkbox preference,
    // call setPreferenceSummary on the changed preference
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Figure out which preference was changed
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
        //we attatch that value to correct preferences
    }

    // This method should check if the preference is a ListPreference and, if so, find the label
    // associated with the value. You can do this by using the findIndexOfValue and getEntries methods
    // of Preference.

    /**
     * Updates the summary for the preference
     *
     * @param preference The preference to be updated
     * @param value      The value that the preference was updated to
     */
    public void setPreferenceSummary(Preference preference, String value) {

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }

        else if (preference instanceof EditTextPreference) {
            preference.setSummary(value);
        }

    }

}