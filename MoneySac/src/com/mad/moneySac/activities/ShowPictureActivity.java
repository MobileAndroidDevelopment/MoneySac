package com.mad.moneySac.activities;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.mad.moneySac.R;

public class ShowPictureActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_picture);
		show();
	}

	private void show() {
		String path = getIntent().getStringExtra(EditEntryActivity.IMAGE).substring(7);
		Log.d("path", path);
		Bitmap myBitmap = BitmapFactory.decodeFile(path);

	    ImageView myImage = (ImageView) findViewById(R.id.imageView);
	    myImage.setImageBitmap(myBitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_picture, menu);
		return false;
	}
	
	public void close(View v){
		finish();
	}


}
