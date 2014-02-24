package com.mad.moneySac.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mad.moneySac.R;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String KEY_SAFE_LOGIN = "safe_login";
	public static final String KEY_LOGIN_PIN = "login_pin";
	
	SharedPreferences	prefs;
	SwitchPreference	prefSafeLogin;
	EditTextPreference	prefLoginPin;

	@Override
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		initializePreferences();
	}
	
	/*
	 * Initalisiert die Preferences
	 */
	@SuppressWarnings("deprecation")
	private void initializePreferences() {
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefSafeLogin = (SwitchPreference) findPreference(KEY_SAFE_LOGIN);
		prefLoginPin = (EditTextPreference) findPreference(KEY_LOGIN_PIN);
		
		//Listener, der das TextFeld limitiert
		prefLoginPin.getEditText().setOnKeyListener(
				new EditText.OnKeyListener() {
					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						if (prefLoginPin.getEditText().getText().length() > 7) return true;
						return false;
					}
				});
		
		if (!prefs.getBoolean(KEY_SAFE_LOGIN, false)) prefLoginPin.setEnabled(false);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(KEY_SAFE_LOGIN)) {
			// PIN activated or deactivated
			if (prefs.getBoolean(KEY_SAFE_LOGIN, false)) {
				prefLoginPin.setEnabled(true);
			} else {
				prefLoginPin.setEnabled(false);
			}
		} else if (key.equals(KEY_LOGIN_PIN)) {
			// PIN changed
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
