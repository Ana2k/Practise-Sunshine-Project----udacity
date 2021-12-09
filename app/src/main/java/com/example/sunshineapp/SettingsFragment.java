package com.example.sunshineapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

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

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_visualiser);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    public void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }

    }
}