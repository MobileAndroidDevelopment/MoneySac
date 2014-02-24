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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isPwEnabled()) {
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
	}

	private boolean isPwEnabled() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return prefs.getBoolean(key, false);
		
	}

	private boolean checkPassword() {
		return true;
	}

	private void login() {
		if (checkPassword()) {
			startActivity(new Intent(this, MoneySac.class));
		} else {
			Toast.makeText(this, R.string.athenticator_wrong_pw, Toast.LENGTH_SHORT).show();
		}

	}

}
