package org.urish.soundtracker.doggie;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DoggieService extends Service {
	private static final String TAG = DoggieActivity.class.getName();

	private static final String MONITORED_PACKAGE_NAME = "com.qualcomm.QCARSamples.FrameMarkers";
	private static final String MONITORED_ACTIVITY_NAME = ".FrameMarkers";

	private ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(1);

	@Override
	public void onCreate() {
		super.onCreate();
		scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				monitorProcesses();
			}
		}, 0, 15, TimeUnit.SECONDS);
		Toast.makeText(this, "Doggie Service Created", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.w(TAG, "Service died !");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public void monitorProcesses() {
		ActivityManager actvityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List<RunningTaskInfo> procInfos = actvityManager.getRunningTasks(1000);
		boolean found = false;
		for (RunningTaskInfo taskInfo : procInfos) {
			if (taskInfo.baseActivity.getPackageName().equals(MONITORED_PACKAGE_NAME)) {
				Log.i(TAG, "Hello " + taskInfo.numRunning);
				if (taskInfo.numRunning > 0) {
					found = true;
				}
			}
		}
		if (!found) {
			Log.w(TAG, "Process seems to have died, restarting");
			Intent i = new Intent(Intent.ACTION_MAIN);
			i.addCategory(Intent.CATEGORY_LAUNCHER);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			i.setComponent(new ComponentName(MONITORED_PACKAGE_NAME, MONITORED_PACKAGE_NAME + MONITORED_ACTIVITY_NAME));
			startActivity(i);
		} else {
			Log.d(TAG, "Process found");
		}
	}
}
