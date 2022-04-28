package com.supremegamers.darkmattersettings;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;


public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

        FilePreferences filePreferences;

        SeekBarPreference alphaSeekbar;
        SeekBarPreference radiusSeekbar;
        ListPreference alignList;
        SwitchPreference ramSwitch;
        SwitchPreference dataSwitch;



        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

            filePreferences = new FilePreferences();

            initViews();
            updatePrefs();

        }


        public void initViews() {

            alphaSeekbar = findPreference("alpha");
            radiusSeekbar = findPreference("radius");
            alignList = findPreference("dock");
            ramSwitch = findPreference("ram");
            dataSwitch = findPreference("data");

        }

        public void updatePrefs() {

            alphaSeekbar.setValue(filePreferences.getInt("alpha",120));
            radiusSeekbar.setValue(filePreferences.getInt("radius",6));
            alignList.setValue(filePreferences.getString("dock","1"));
            ramSwitch.setChecked(filePreferences.getBoolean("ram",true));
            dataSwitch.setChecked(filePreferences.getBoolean("data",true));

        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            switch (key) {

                case "alpha":
                    filePreferences.putInt(key, sharedPreferences.getInt(key, 120));
                    break;

                case "radius":
                    filePreferences.putInt(key, sharedPreferences.getInt(key, 6));
                    break;

                case "ram":
                    filePreferences.putBoolean(key, sharedPreferences.getBoolean(key, true));
                    break;

                case "dock":
                    filePreferences.putString(key, sharedPreferences.getString(key, "1"));

                    Intent dock_intent = new Intent("com.android.systemui.CDockSec");
                    getContext().sendBroadcast(dock_intent);
                    break;

                case "data":
                    filePreferences.putBoolean(key, sharedPreferences.getBoolean(key, true));

                    Intent data_intent = new Intent("com.android.systemui.TrafficHolder");
                    getContext().sendBroadcast(data_intent);
                    break;
            }

        }

    }
}