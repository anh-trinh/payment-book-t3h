package com.tnta.t3h.paymentbook;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;

public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String name_saved = prefs.getString("name",null);

        EditTextPreference name = (EditTextPreference) findPreference("name");
        Preference about = (Preference) findPreference("about");
        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                LayoutInflater inflater = SettingActivity.this.getLayoutInflater();

                builder.setView(inflater.inflate(R.layout.about, null)).show();
                return true;
            }
        });

        if (name_saved!=null){
            name.setDefaultValue(name_saved);
        }


    }
}
