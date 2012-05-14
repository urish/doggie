package org.urish.soundtracker.doggie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DoggieActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("org.urish.soundtracker.doggie.DoggieService");
		startService(serviceIntent);
	}
}