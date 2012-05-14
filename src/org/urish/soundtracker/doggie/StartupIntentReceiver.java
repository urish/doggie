package org.urish.soundtracker.doggie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("org.urish.soundtracker.doggie.DoggieService");
		context.startService(serviceIntent);
	}
}
