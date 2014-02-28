package com.mad.moneySac.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.mad.moneySac.R;

/**
 * Dialog zur Anzeige eines Bildes.
 */
public class ShowPictureActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_picture);
		showImage();
	}

	private void showImage() {
		String path = getIntent().getStringExtra(EditEntryActivity.IMAGE).substring(7);
		Log.d("path", path);
		Bitmap myBitmap = BitmapFactory.decodeFile(path);

	    ImageView myImage = (ImageView) findViewById(R.id.imageView);
	    myImage.setImageBitmap(myBitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.show_picture, menu);
		return false;
	}
	
	public void close(View v){
		finish();
	}
}
