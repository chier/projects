package com.pica.environmentandhealth;

import org.apache.cordova.DroidGap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.setIntegerProperty("loadUrlTimeoutValue", 10000);
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		super.loadUrl("file:///android_asset/www/indexData.html");
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		super.loadUrl("file:///android_asset/www/index.html");
//		return true;
//	}

}
