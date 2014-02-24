package com.mad.moneySac.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;

import com.mad.moneySac.R;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String KEY_SAFE_LOGIN = "safe_login";
	public static final boolean SAFE_LOGIN_DEFAULT = false;

	SharedPreferences prefs;
	SwitchPreference prefSafeLogin;

	@Override
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		prefSafeLogin = (SwitchPreference) findPreference(KEY_SAFE_LOGIN);
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(KEY_SAFE_LOGIN)) {
			// Do nothing
		}
		setResult(RESULT_OK);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

}
