package com.mad.moneySac.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.moneySac.R;

public class AuthenticatorActivity extends Activity {

	private EditText edPassword;
	private ImageView imgCheck;
	private ImageView imgInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!isPwEnabled()) {
			startActivity(new Intent(this, MoneySac.class));
			this.finish();
		}
		setContentView(R.layout.activity_authenticator);
		initViews();
		setListeners();
	}

	private void setListeners() {
		imgCheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
		
		imgInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(AuthenticatorActivity.this, R.string.athenticator_std_pin, Toast.LENGTH_SHORT).show();
			}
		});

		edPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					login();
				}
				return false;
			}
		});
	}

	private void initViews() {
		edPassword = (EditText) findViewById(R.id.ed_pw);
		imgCheck = (ImageView) findViewById(R.id.img_check);
		imgInfo = (ImageView) findViewById(R.id.img_info);
	}

	private boolean isPwEnabled() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return prefs.getBoolean(SettingsActivity.KEY_SAFE_LOGIN, false);
		
	}

	private boolean checkPassword() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String pwTyped = edPassword.getText().toString().trim();
		String pwPrefs = prefs.getString(SettingsActivity.KEY_LOGIN_PIN, "");
		if (pwTyped.equals(pwPrefs)) {
			return true;
		} else {
			return false;
		}
	}

	private void login() {
		if (checkPassword()) {
			startActivity(new Intent(this, MoneySac.class));
			this.finish();
			overridePendingTransition(R.anim.slide_in_to_bottom, R.anim.slide_out_to_bottom);
		} else {
			Toast.makeText(this, R.string.athenticator_wrong_pin, Toast.LENGTH_SHORT).show();
		}

	}

}
